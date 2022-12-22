package plus.hutool.media.document.pdf;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import plus.hutool.core.io.FileUtils;
import plus.hutool.core.lang.Asserts;
import plus.hutool.core.lang.ExceptionUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * PDF 工具
 *
 * @author bianyun
 * @date 2022/12/07
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class PdfUtils {
    private static final String DEFAULT_IMAGE_FORMAT = "png";
    private static final String DEFAULT_IMAGE_SUFFIX = StrUtil.DOT + DEFAULT_IMAGE_FORMAT;
    private static final String PDF_FILE_SUFFIX = ".pdf";
    private static final String DEFAULT_OUTPUT_SUBDIR_NAME = "images";
    private static final int FIRST_PAGE_INDEX = 0;

    private PdfUtils() {}

    /**
     * 将 PDF文件的每一页提取成图片（默认提取到 PDF文件所在目录的 images 目录下）
     *
     * @param pdfFilePath PDF文件路径
     */
    public static void extractPdfPagesToImages(String pdfFilePath) {
        Asserts.notBlank(pdfFilePath, "PDF文件路径不能为空");
        File defaultTargetOutputDir = resolveDefaultTargetOutputDir(pdfFilePath);

        List<byte[]> imageRawData = extractPdfPagesToRawImageDataList(pdfFilePath);
        writeRawImageDataToFiles(imageRawData, defaultTargetOutputDir);

        extractPdfPagesToImages(pdfFilePath, defaultTargetOutputDir);
    }

    /**
     * 将 PDF文件的每一页提取成图片
     *
     * @param pdfFilePath     PDF文件路径
     * @param imagesOutputDir 提取的输出目录
     */
    public static void extractPdfPagesToImages(String pdfFilePath, File imagesOutputDir) {
        Asserts.notBlank(pdfFilePath, "PDF文件路径不能为空");
        List<byte[]> imageRawData = extractPdfPagesToRawImageDataList(pdfFilePath);
        writeRawImageDataToFiles(imageRawData, imagesOutputDir);
    }

    /**
     * 将 PDF文件的每一页提取成图片原始数据（byte[]格式）
     *
     * @param pdfFilePathStr PDF文件路径
     * @return 图片原始数据列表
     */
    public static List<byte[]> extractPdfPagesToRawImageDataList(String pdfFilePathStr) {
        Asserts.notBlank(pdfFilePathStr, "PDF文件路径不能为空");
        File pdfFile = FileUtil.file(pdfFilePathStr);
        return extractPdfPagesToRawImageDataList(pdfFile);
    }

    /**
     * 将 PDF文件的每一页提取成图片原始数据（byte[]格式）
     *
     * @param pdfFilePath PDF文件路径
     * @return 图片原始数据列表
     */
    public static List<byte[]> extractPdfPagesToRawImageDataList(Path pdfFilePath) {
        return extractPdfPagesToRawImageDataList(pdfFilePath.toFile());
    }

    /**
     * 将 PDF文件的每一页提取成图片原始数据（byte[]格式）
     *
     * @param pdfFile PDF文件
     * @return 图片原始数据列表
     */
    public static List<byte[]> extractPdfPagesToRawImageDataList(File pdfFile) {
        Asserts.notNull(pdfFile, "PDF文件路径不能为空");
        Asserts.isTrue(pdfFile.exists() && pdfFile.isFile(), "PDF文件不存在或者不是文件: {}", pdfFile.getAbsolutePath());
        return extractPdfPagesToRawImageDataList(FileUtil.getInputStream(pdfFile));
    }

    /**
     * 将 PDF文件的每一页提取成图片原始数据（byte[]格式）
     *
     * @param pdfInputStream PDF文件输入流
     * @return 图片原始数据列表
     */
    public static List<byte[]> extractPdfPagesToRawImageDataList(InputStream pdfInputStream) {
        Asserts.notNull(pdfInputStream, "PDF文件输入流不能为 null");

        try (PDDocument document = PDDocument.load(pdfInputStream)) {
            int pageNum = document.getNumberOfPages();

            List<byte[]> resultList = new ArrayList<>(pageNum);
            for (int i = 0; i < pageNum; i++) {
                resultList.add(null);
            }

            final List<File> singlePageFiles = splitToSinglePageFiles(document);

            IntStream.range(0, pageNum).parallel().forEach(index -> {
                File singlePageFile = singlePageFiles.get(index);
                try (PDDocument doc = PDDocument.load(singlePageFile);
                     ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                    PDFRenderer renderer = new PDFRenderer(doc);
                    BufferedImage image = renderer.renderImage(FIRST_PAGE_INDEX);
                    ImageIO.write(image, DEFAULT_IMAGE_FORMAT, out);
                    resultList.set(index, out.toByteArray());
                } catch (IOException e) {
                    throw new IORuntimeException(e);
                }
            });
            return resultList;
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    /**
     * 将 PDF 文档分割成单页的 PDF文件列表
     *
     * @param document PDF 文档
     * @return 单页的 PDF文件列表
     */
    public static List<File> splitToSinglePageFiles(PDDocument document) {
        return splitToSinglePageFiles(document, new Splitter());
    }

    /**
     * 将 PDF 文档分割成单页的 PDF文件列表
     *
     * @param document PDF 文档
     * @param splitter 分割器
     * @return 单页的 PDF文件列表
     */
    public static List<File> splitToSinglePageFiles(PDDocument document, Splitter splitter) {
        List<File> resultList = new ArrayList<>();

        try {
            List<PDDocument> docs = splitter.split(document);
            for (int i = 0; i < docs.size(); i++) {
                PDDocument doc = docs.get(i);
                File tmpFile = FileUtils.createFileUnderRandomTempDir(i + PDF_FILE_SUFFIX);
                doc.save(tmpFile);
                resultList.add(tmpFile);
                doc.close();
            }
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }

        return resultList;
    }

    private static void writeRawImageDataToFiles(List<byte[]> rawData, File targetDir) {
        if (!targetDir.exists()) {
            FileUtil.mkdir(targetDir);
        } else if (!targetDir.isDirectory()) {
            throw ExceptionUtils.illegalArgumentException("提取的目标路径不是目录: {}", targetDir.getAbsolutePath());
        }

        int size = rawData.size();
        IntStream.range(0, size).parallel().forEach(index -> {
            byte[] datum = rawData.get(index);
            File targetFile = FileUtil.file(targetDir, index + DEFAULT_IMAGE_SUFFIX);

            InputStream input = new ByteArrayInputStream(datum);
            OutputStream out = FileUtil.getOutputStream(targetFile);
            IoUtil.copy(input, out);

            IoUtil.close(input);
            IoUtil.close(out);
        });
    }

    private static File resolveDefaultTargetOutputDir(String pdfFilePath) {
        String parentDir = FileUtil.getParent(pdfFilePath, 1);
        return Paths.get(parentDir, DEFAULT_OUTPUT_SUBDIR_NAME).toFile();
    }

}

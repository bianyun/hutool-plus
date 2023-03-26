package plus.hutool.media.document.pdf;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.io.input.BrokenInputStream;
import org.apache.commons.io.input.NullInputStream;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import plus.hutool.core.io.FileUtils;
import plus.hutool.media.image.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static plus.hutool.media.test.UnitTestUtils.resolveTestFile;

class PdfUtilsTest {

    @Test
    void testExtractPdfPagesToImages1() {
        final File pdfFile = resolveTestFile("test.pdf");
        final String pdfFilePath = pdfFile.getAbsolutePath();

        PdfUtils.extractPdfPagesToImages(pdfFilePath);

        final File targetImagesDir = FileUtil.file(pdfFile.getParentFile(), "images");
        assertThat(targetImagesDir).isDirectory().exists();

        final List<String> filenameList = FileUtil.listFileNames(targetImagesDir.getAbsolutePath());
        assertThat(filenameList).hasSize(22);
        assertThat(filenameList).contains("0.png");
        assertThat(filenameList).contains("21.png");

        final File firstImageFile = FileUtil.file(targetImagesDir, filenameList.get(0));
        assertThat(firstImageFile).isFile().exists();
        assertThat(ImageUtils.getImageWidth(firstImageFile)).isGreaterThan(0);

        FileUtil.del(targetImagesDir);
    }

    @Test
    void testExtractPdfPagesToImages2() {
        final File targetImagesDir = FileUtils.createDirUnderRandomTempDir("images");

        final File pdfFile = resolveTestFile("test.pdf");
        final String pdfFilePath = pdfFile.getAbsolutePath();

        PdfUtils.extractPdfPagesToImages(pdfFilePath, targetImagesDir);
        assertThat(targetImagesDir).isDirectory().exists();

        final List<String> filenameList = FileUtil.listFileNames(targetImagesDir.getAbsolutePath());
        assertThat(filenameList).hasSize(22);
        assertThat(filenameList).contains("0.png");
        assertThat(filenameList).contains("21.png");

        final File firstImageFile = FileUtil.file(targetImagesDir, filenameList.get(0));
        assertThat(firstImageFile).isFile().exists();
        assertThat(ImageUtils.getImageWidth(firstImageFile)).isGreaterThan(0);

        FileUtil.del(targetImagesDir.getParentFile());

        File illegalTargetDir = FileUtil.createTempFile("hutool", null, null, true);
        assertThatThrownBy(() -> PdfUtils.extractPdfPagesToImages(pdfFilePath, illegalTargetDir))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(StrUtil.format("提取的目标路径不是目录: {}", illegalTargetDir.getAbsolutePath()));
        FileUtil.del(illegalTargetDir);
    }

    @Test
    void testExtractPdfPagesToRawImageDataList1() {
        final File pdfFile = resolveTestFile("test.pdf");
        final List<byte[]> result = PdfUtils.extractPdfPagesToRawImageDataList(pdfFile.getAbsolutePath());

        assertThat(result).hasSize(22);
        assertThat(result).allMatch(byteArray -> byteArray.length > 0);
    }

    @Test
    void testExtractPdfPagesToRawImageDataList2() {
        final Path pdfFilePath = resolveTestFile("test.pdf").toPath();
        final List<byte[]> result = PdfUtils.extractPdfPagesToRawImageDataList(pdfFilePath);

        assertThat(result).hasSize(22);
        assertThat(result).allMatch(byteArray -> byteArray.length > 0);
    }

    @Test
    void testExtractPdfPagesToRawImageDataList3() {
        final File pdfFile = resolveTestFile("test.pdf");
        final List<byte[]> result = PdfUtils.extractPdfPagesToRawImageDataList(pdfFile);

        assertThat(result).hasSize(22);
        assertThat(result).allMatch(byteArray -> byteArray.length > 0);

        final File fileNotExists = new File("X:/path_of_non-existing_file");
        assertThatThrownBy(() -> PdfUtils.extractPdfPagesToRawImageDataList(fileNotExists))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("PDF文件不存在或者不是文件: %s", fileNotExists.getAbsolutePath());

        File tempDir = FileUtils.createDirUnderRandomTempDir("tempDir");
        assertThatThrownBy(() -> PdfUtils.extractPdfPagesToRawImageDataList(tempDir))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("PDF文件不存在或者不是文件: %s", tempDir.getAbsolutePath());
        FileUtil.del(tempDir.getParentFile());
    }

    @Test
    void testExtractPdfPagesToRawImageDataList4() {
        final File pdfFile = resolveTestFile("test.pdf");
        final List<byte[]> result = PdfUtils.extractPdfPagesToRawImageDataList(FileUtil.getInputStream(pdfFile));

        assertThat(result).hasSize(22);
        assertThat(result).allMatch(byteArray -> byteArray.length > 0);
    }

    @Test
    void testExtractPdfPagesToRawImageDataList4_ImageIoWriteThrowsException() {
        final File pdfFile = resolveTestFile("test.pdf");

        try (MockedStatic<ImageIO> imageIoStatic = Mockito.mockStatic(ImageIO.class)) {
            imageIoStatic.when(() -> ImageIO.write(any(RenderedImage.class), any(String.class), any(OutputStream.class)))
                    .thenThrow(IOException.class);

            assertThatThrownBy(() -> PdfUtils.extractPdfPagesToRawImageDataList(FileUtil.getInputStream(pdfFile)))
                    .isInstanceOf(IORuntimeException.class)
                    .hasCauseExactlyInstanceOf(IOException.class);
        }
    }

    @Test
    void testExtractPdfPagesToRawImageDataList4_EmptyPdfInputStream() {
        assertThatThrownBy(() -> PdfUtils.extractPdfPagesToRawImageDataList(new NullInputStream()))
                .isInstanceOf(IORuntimeException.class)
                .hasMessage("IOException: Error: End-of-File, expected line at offset 0")
                .hasCauseExactlyInstanceOf(IOException.class)
                .hasRootCauseMessage("Error: End-of-File, expected line at offset 0");
    }

    @Test
    void testExtractPdfPagesToRawImageDataList4_BrokenPdfInputStream() {
        assertThatThrownBy(() -> PdfUtils.extractPdfPagesToRawImageDataList(new BrokenInputStream()))
                .isInstanceOf(IORuntimeException.class)
                .hasMessage("IOException: Broken input stream")
                .hasCauseExactlyInstanceOf(IOException.class)
                .hasRootCauseMessage("Broken input stream");
    }

    @Test
    void testSplitToSinglePageFilesWhenExceptionThrown() throws IOException {
        Splitter splitter = mock(Splitter.class);
        Mockito.when(splitter.split(Mockito.any(PDDocument.class))).thenThrow(IOException.class);

        final File pdfFile = resolveTestFile("test.pdf");
        try (PDDocument pdDocument = PDDocument.load(pdfFile)) {
            assertThatThrownBy(() -> PdfUtils.splitToSinglePageFiles(pdDocument, splitter))
                    .isInstanceOf(IORuntimeException.class)
                    .hasMessage("IOException: null")
                    .hasCauseExactlyInstanceOf(IOException.class)
                    .hasRootCauseMessage(null);
        }
    }
}

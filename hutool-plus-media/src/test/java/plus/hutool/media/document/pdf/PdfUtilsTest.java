package plus.hutool.media.document.pdf;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import org.apache.commons.io.input.BrokenInputStream;
import org.apache.commons.io.input.NullInputStream;
import org.junit.jupiter.api.Test;
import plus.hutool.core.io.FileUtils;
import plus.hutool.media.image.ImageUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PdfUtilsTest {

    @Test
    void testExtractPdfPagesToImages1() {
        final File pdfFile = FileUtil.file("classpath:testFiles/documents/test.pdf");
        final String pdfFilePath = pdfFile.getAbsolutePath();

        PdfUtils.extractPdfPagesToImages(pdfFilePath);

        final File targetImagesDir = FileUtil.file(pdfFile.getParentFile(), "images");
        assertThat(targetImagesDir).isDirectory().exists();

        final List<String> filenameList = FileUtil.listFileNames(targetImagesDir.getAbsolutePath());
        assertThat(filenameList).hasSize(22);

        final File firstImageFile = FileUtil.file(targetImagesDir, filenameList.get(0));
        assertThat(firstImageFile).isFile().exists().hasName("0.png");
        assertThat(ImageUtils.getImageWidth(firstImageFile)).isGreaterThan(0);

        FileUtil.del(targetImagesDir);
    }

    @Test
    void testExtractPdfPagesToImages2() {
        final File targetImagesDir = FileUtils.createDirUnderRandomTempDir("images");

        final File pdfFile = FileUtil.file("classpath:testFiles/documents/test.pdf");
        final String pdfFilePath = pdfFile.getAbsolutePath();

        PdfUtils.extractPdfPagesToImages(pdfFilePath, targetImagesDir);
        assertThat(targetImagesDir).isDirectory().exists();

        final List<String> filenameList = FileUtil.listFileNames(targetImagesDir.getAbsolutePath());
        assertThat(filenameList).hasSize(22);

        final File firstImageFile = FileUtil.file(targetImagesDir, filenameList.get(0));
        assertThat(firstImageFile).isFile().exists().hasName("0.png");
        assertThat(ImageUtils.getImageWidth(firstImageFile)).isGreaterThan(0);

        FileUtil.del(targetImagesDir.getParentFile());
    }

    @Test
    void testExtractPdfPagesToRawImageDataList1() {
        final File pdfFile = FileUtil.file("classpath:testFiles/documents/test.pdf");
        final List<byte[]> result = PdfUtils.extractPdfPagesToRawImageDataList(pdfFile.getAbsolutePath());

        assertThat(result).hasSize(22);
        assertThat(result).allMatch(byteArray -> byteArray.length > 0);
    }

    @Test
    void testExtractPdfPagesToRawImageDataList2() {
        final Path pdfFilePath = FileUtil.file("classpath:testFiles/documents/test.pdf").toPath();
        final List<byte[]> result = PdfUtils.extractPdfPagesToRawImageDataList(pdfFilePath);

        assertThat(result).hasSize(22);
        assertThat(result).allMatch(byteArray -> byteArray.length > 0);
    }

    @Test
    void testExtractPdfPagesToRawImageDataList3() {
        final File pdfFile = FileUtil.file("classpath:testFiles/documents/test.pdf");
        final List<byte[]> result = PdfUtils.extractPdfPagesToRawImageDataList(pdfFile);

        assertThat(result).hasSize(22);
        assertThat(result).allMatch(byteArray -> byteArray.length > 0);
    }

    @Test
    void testExtractPdfPagesToRawImageDataList4() {
        final File pdfFile = FileUtil.file("classpath:testFiles/documents/test.pdf");
        final List<byte[]> result = PdfUtils.extractPdfPagesToRawImageDataList(FileUtil.getInputStream(pdfFile));

        assertThat(result).hasSize(22);
        assertThat(result).allMatch(byteArray -> byteArray.length > 0);
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
}

package plus.hutool.media.misc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import org.apache.commons.io.input.BrokenInputStream;
import org.apache.commons.io.input.NullInputStream;
import org.apache.tika.exception.ZeroByteFileException;
import org.junit.jupiter.api.Test;
import plus.hutool.media.exception.TikaParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TikaUtilsTest {

    @Test
    void testDetectMediaType1() {
        Path cebFile = FileUtil.file("classpath:testFiles/documents/test.ceb").toPath();
        Path docFile = FileUtil.file("classpath:testFiles/documents/test.doc").toPath();
        Path docxFile = FileUtil.file("classpath:testFiles/documents/test.docx").toPath();
        Path xlsFile = FileUtil.file("classpath:testFiles/documents/test.xls").toPath();
        Path xlsxFile = FileUtil.file("classpath:testFiles/documents/test.xlsx").toPath();
        Path pdfFile = FileUtil.file("classpath:testFiles/documents/test.pdf").toPath();
        Path bmpFile = FileUtil.file("classpath:testFiles/images/test.bmp").toPath();
        Path jpegFile = FileUtil.file("classpath:testFiles/images/test.jpg").toPath();
        Path pngFile = FileUtil.file("classpath:testFiles/images/test.png").toPath();

        assertThat(TikaUtils.detectMediaType(cebFile)).isEqualTo(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        assertThat(TikaUtils.detectMediaType(docFile)).isEqualTo(MediaType.APPLICATION_MS_WORD_VALUE);
        assertThat(TikaUtils.detectMediaType(docxFile)).isEqualTo(MediaType.APPLICATION_OOXML_DOCUMENT_VALUE);
        assertThat(TikaUtils.detectMediaType(xlsFile)).isEqualTo(MediaType.APPLICATION_MS_EXCEL_VALUE);
        assertThat(TikaUtils.detectMediaType(xlsxFile)).isEqualTo(MediaType.APPLICATION_OOXML_SHEET_VALUE);
        assertThat(TikaUtils.detectMediaType(pdfFile)).isEqualTo(MediaType.APPLICATION_PDF_VALUE);
        assertThat(TikaUtils.detectMediaType(bmpFile)).isEqualTo(MediaType.IMAGE_BMP_VALUE);
        assertThat(TikaUtils.detectMediaType(jpegFile)).isEqualTo(MediaType.IMAGE_JPEG_VALUE);
        assertThat(TikaUtils.detectMediaType(pngFile)).isEqualTo(MediaType.IMAGE_PNG_VALUE);
    }

    @Test
    void testDetectMediaType2() {
        File cebFile = FileUtil.file("classpath:testFiles/documents/test.ceb");
        File docFile = FileUtil.file("classpath:testFiles/documents/test.doc");
        File docxFile = FileUtil.file("classpath:testFiles/documents/test.docx");
        File xlsFile = FileUtil.file("classpath:testFiles/documents/test.xls");
        File xlsxFile = FileUtil.file("classpath:testFiles/documents/test.xlsx");
        File pdfFile = FileUtil.file("classpath:testFiles/documents/test.pdf");
        File bmpFile = FileUtil.file("classpath:testFiles/images/test.bmp");
        File jpegFile = FileUtil.file("classpath:testFiles/images/test.jpg");
        File pngFile = FileUtil.file("classpath:testFiles/images/test.png");

        assertThat(TikaUtils.detectMediaType(cebFile)).isEqualTo(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        assertThat(TikaUtils.detectMediaType(docFile)).isEqualTo(MediaType.APPLICATION_MS_WORD_VALUE);
        assertThat(TikaUtils.detectMediaType(docxFile)).isEqualTo(MediaType.APPLICATION_OOXML_DOCUMENT_VALUE);
        assertThat(TikaUtils.detectMediaType(xlsFile)).isEqualTo(MediaType.APPLICATION_MS_EXCEL_VALUE);
        assertThat(TikaUtils.detectMediaType(xlsxFile)).isEqualTo(MediaType.APPLICATION_OOXML_SHEET_VALUE);
        assertThat(TikaUtils.detectMediaType(pdfFile)).isEqualTo(MediaType.APPLICATION_PDF_VALUE);
        assertThat(TikaUtils.detectMediaType(bmpFile)).isEqualTo(MediaType.IMAGE_BMP_VALUE);
        assertThat(TikaUtils.detectMediaType(jpegFile)).isEqualTo(MediaType.IMAGE_JPEG_VALUE);
        assertThat(TikaUtils.detectMediaType(pngFile)).isEqualTo(MediaType.IMAGE_PNG_VALUE);

        final File pngFileNotExists = FileUtil.file("classpath:testFiles/images/not_exists.png");
        assertThatThrownBy(() -> TikaUtils.detectMediaType(pngFileNotExists))
                .isInstanceOf(IORuntimeException.class)
                .hasMessageStartingWith("FileNotFoundException: ")
                .hasCauseExactlyInstanceOf(FileNotFoundException.class);
    }

    @Test
    void testDetectMediaType3() {
        InputStream cebFileInputStream = FileUtil.getInputStream("classpath:testFiles/documents/test.ceb");
        InputStream docFileInputStream = FileUtil.getInputStream("classpath:testFiles/documents/test.doc");
        InputStream docxFileInputStream = FileUtil.getInputStream("classpath:testFiles/documents/test.docx");
        InputStream xlsFileInputStream = FileUtil.getInputStream("classpath:testFiles/documents/test.xls");
        InputStream xlsxFileInputStream = FileUtil.getInputStream("classpath:testFiles/documents/test.xlsx");
        InputStream pdfFileInputStream = FileUtil.getInputStream("classpath:testFiles/documents/test.pdf");
        InputStream bmpFileInputStream = FileUtil.getInputStream("classpath:testFiles/images/test.bmp");
        InputStream jpegFileInputStream = FileUtil.getInputStream("classpath:testFiles/images/test.jpg");
        InputStream pngFileInputStream = FileUtil.getInputStream("classpath:testFiles/images/test.png");

        assertThat(TikaUtils.detectMediaType(cebFileInputStream, "test.ceb")).isEqualTo(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        assertThat(TikaUtils.detectMediaType(docFileInputStream, "test.doc")).isEqualTo(MediaType.APPLICATION_MS_WORD_VALUE);
        assertThat(TikaUtils.detectMediaType(docxFileInputStream, "test.docx")).isEqualTo(MediaType.APPLICATION_OOXML_DOCUMENT_VALUE);
        assertThat(TikaUtils.detectMediaType(xlsFileInputStream, "test.xls")).isEqualTo(MediaType.APPLICATION_MS_EXCEL_VALUE);
        assertThat(TikaUtils.detectMediaType(xlsxFileInputStream, "test.xlsx")).isEqualTo(MediaType.APPLICATION_OOXML_SHEET_VALUE);
        assertThat(TikaUtils.detectMediaType(pdfFileInputStream, "test.pdf")).isEqualTo(MediaType.APPLICATION_PDF_VALUE);
        assertThat(TikaUtils.detectMediaType(bmpFileInputStream, "test.bmp")).isEqualTo(MediaType.IMAGE_BMP_VALUE);
        assertThat(TikaUtils.detectMediaType(jpegFileInputStream, "test.jpg")).isEqualTo(MediaType.IMAGE_JPEG_VALUE);
        assertThat(TikaUtils.detectMediaType(pngFileInputStream, "test.png")).isEqualTo(MediaType.IMAGE_PNG_VALUE);
    }

    @Test
    void testDetectMediaType3_EmptyStream() {
        assertThat(TikaUtils.detectMediaType(new NullInputStream(), "")).isEqualTo(MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    @Test
    void testDetectMediaType3_BrokenStream() {
        assertThatThrownBy(() -> TikaUtils.detectMediaType(new BrokenInputStream(), ""))
                .isExactlyInstanceOf(IORuntimeException.class)
                .hasCauseInstanceOf(IOException.class)
                .hasRootCauseMessage("Broken input stream");
    }

    @Test
    void testDetectMediaType4() {
        assertThat(TikaUtils.detectMediaType("test.doc")).isEqualTo(MediaType.APPLICATION_MS_WORD_VALUE);
        assertThat(TikaUtils.detectMediaType("test.docx")).isEqualTo(MediaType.APPLICATION_OOXML_DOCUMENT_VALUE);
        assertThat(TikaUtils.detectMediaType("test.xls")).isEqualTo(MediaType.APPLICATION_MS_EXCEL_VALUE);
        assertThat(TikaUtils.detectMediaType("test.xlsx")).isEqualTo(MediaType.APPLICATION_OOXML_SHEET_VALUE);
        assertThat(TikaUtils.detectMediaType("test.jpeg")).isEqualTo(MediaType.IMAGE_JPEG_VALUE);
        assertThat(TikaUtils.detectMediaType("test.png")).isEqualTo(MediaType.IMAGE_PNG_VALUE);
        assertThat(TikaUtils.detectMediaType("test.ceb")).isEqualTo(MediaType.APPLICATION_OCTET_STREAM_VALUE);
    }

    @Test
    void testParseToString1() {
        Path txtFilePath = FileUtil.file("classpath:testFiles/documents/test.txt").toPath();
        assertThat(TikaUtils.parseToString(txtFilePath).trim()).isEqualTo("This is a text file for test.");
    }

    @Test
    void testParseToString2() {
        File txtFile = FileUtil.file("classpath:testFiles/documents/test.txt");
        assertThat(TikaUtils.parseToString(txtFile).trim()).isEqualTo("This is a text file for test.");
    }

    @Test
    void testParseToString3() {
        InputStream inputStream = FileUtil.getInputStream("classpath:testFiles/documents/test.txt");
        assertThat(TikaUtils.parseToString(inputStream).trim()).isEqualTo("This is a text file for test.");
    }

    @Test
    void testParseToString3_EmptyStream() {
        assertThatThrownBy(() -> TikaUtils.parseToString(new NullInputStream()))
                .isExactlyInstanceOf(TikaParseException.class)
                .hasMessage("Tika 解析文档出错")
                .hasCauseInstanceOf(ZeroByteFileException.class)
                .hasRootCauseMessage("InputStream must have > 0 bytes");
    }

    @Test
    void testParseToString3_BrokenStream() {
        assertThatThrownBy(() -> TikaUtils.parseToString(new BrokenInputStream()))
                .isInstanceOf(IORuntimeException.class)
                .hasMessage("IOException: Broken input stream")
                .hasCauseInstanceOf(IOException.class)
                .hasRootCauseMessage("Broken input stream");
    }
}

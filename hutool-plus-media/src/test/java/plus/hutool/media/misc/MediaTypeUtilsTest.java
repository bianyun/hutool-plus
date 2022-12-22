package plus.hutool.media.misc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import com.documents4j.api.DocumentType;
import org.apache.commons.io.input.BrokenInputStream;
import org.apache.commons.io.input.NullInputStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class MediaTypeUtilsTest {

    @Test
    void testIsAnyOf() {
        assertThat(MediaTypeUtils.isAnyOf(MediaType.TEXT_PLAIN, MediaType.TEXT_CSS)).isFalse();
        assertThat(MediaTypeUtils.isAnyOf(MediaType.TEXT_PLAIN, MediaType.TEXT_CSS, MediaType.TEXT_HTML)).isFalse();
        assertThat(MediaTypeUtils.isAnyOf(MediaType.TEXT_PLAIN, MediaType.TEXT_CSS, MediaType.TEXT_PLAIN)).isTrue();
        assertThat(MediaTypeUtils.isAnyOf(MediaType.TEXT_CSS, MediaType.TEXT_CSS, MediaType.TEXT_PLAIN)).isTrue();
    }

    @Test
    void testDetectMediaType1() {
        File cebFile = FileUtil.file("classpath:testFiles/documents/test.ceb");
        File docFile = FileUtil.file("classpath:testFiles/documents/test.doc");
        File docxFile = FileUtil.file("classpath:testFiles/documents/test.docx");
        File xlsFile = FileUtil.file("classpath:testFiles/documents/test.xls");
        File xlsxFile = FileUtil.file("classpath:testFiles/documents/test.xlsx");
        File pdfFile = FileUtil.file("classpath:testFiles/documents/test.pdf");
        File bmpFile = FileUtil.file("classpath:testFiles/images/test.bmp");
        File jpegFile = FileUtil.file("classpath:testFiles/images/test.jpg");
        File pngFile = FileUtil.file("classpath:testFiles/images/test.png");

        assertThat(MediaTypeUtils.detectMediaType(cebFile)).isEqualTo(MediaType.APPLICATION_CEB);
        assertThat(MediaTypeUtils.detectMediaType(docFile)).isEqualTo(MediaType.APPLICATION_MS_WORD);
        assertThat(MediaTypeUtils.detectMediaType(docxFile)).isEqualTo(MediaType.APPLICATION_OOXML_DOCUMENT);
        assertThat(MediaTypeUtils.detectMediaType(xlsFile)).isEqualTo(MediaType.APPLICATION_MS_EXCEL);
        assertThat(MediaTypeUtils.detectMediaType(xlsxFile)).isEqualTo(MediaType.APPLICATION_OOXML_SHEET);
        assertThat(MediaTypeUtils.detectMediaType(pdfFile)).isEqualTo(MediaType.APPLICATION_PDF);
        assertThat(MediaTypeUtils.detectMediaType(bmpFile)).isEqualTo(MediaType.IMAGE_BMP);
        assertThat(MediaTypeUtils.detectMediaType(jpegFile)).isEqualTo(MediaType.IMAGE_JPEG);
        assertThat(MediaTypeUtils.detectMediaType(pngFile)).isEqualTo(MediaType.IMAGE_PNG);
    }

    @Test
    void testDetectMediaType2() {
        InputStream cebFileInputStream = FileUtil.getInputStream("classpath:testFiles/documents/test.ceb");
        InputStream docFileInputStream = FileUtil.getInputStream("classpath:testFiles/documents/test.doc");
        InputStream docxFileInputStream = FileUtil.getInputStream("classpath:testFiles/documents/test.docx");
        InputStream xlsFileInputStream = FileUtil.getInputStream("classpath:testFiles/documents/test.xls");
        InputStream xlsxFileInputStream = FileUtil.getInputStream("classpath:testFiles/documents/test.xlsx");
        InputStream pdfFileInputStream = FileUtil.getInputStream("classpath:testFiles/documents/test.pdf");
        InputStream bmpFileInputStream = FileUtil.getInputStream("classpath:testFiles/images/test.bmp");
        InputStream jpegFileInputStream = FileUtil.getInputStream("classpath:testFiles/images/test.jpg");
        InputStream pngFileInputStream = FileUtil.getInputStream("classpath:testFiles/images/test.png");

        assertThat(MediaTypeUtils.detectMediaType(cebFileInputStream, "test.ceb")).isEqualTo(MediaType.APPLICATION_OCTET_STREAM);
        assertThat(MediaTypeUtils.detectMediaType(docFileInputStream, "test.doc")).isEqualTo(MediaType.APPLICATION_MS_WORD);
        assertThat(MediaTypeUtils.detectMediaType(docxFileInputStream, "test.docx")).isEqualTo(MediaType.APPLICATION_OOXML_DOCUMENT);
        assertThat(MediaTypeUtils.detectMediaType(xlsFileInputStream, "test.xls")).isEqualTo(MediaType.APPLICATION_MS_EXCEL);
        assertThat(MediaTypeUtils.detectMediaType(xlsxFileInputStream, "test.xlsx")).isEqualTo(MediaType.APPLICATION_OOXML_SHEET);
        assertThat(MediaTypeUtils.detectMediaType(pdfFileInputStream, "test.pdf")).isEqualTo(MediaType.APPLICATION_PDF);
        assertThat(MediaTypeUtils.detectMediaType(bmpFileInputStream, "test.bmp")).isEqualTo(MediaType.IMAGE_BMP);
        assertThat(MediaTypeUtils.detectMediaType(jpegFileInputStream, "test.jpg")).isEqualTo(MediaType.IMAGE_JPEG);
        assertThat(MediaTypeUtils.detectMediaType(pngFileInputStream, "test.png")).isEqualTo(MediaType.IMAGE_PNG);
    }

    @Test
    void testDetectMediaType3() {
        File textFile = FileUtil.file("classpath:testFiles/text/text");
        File txtFile = FileUtil.file("classpath:testFiles/text/test.txt");
        File jsFile = FileUtil.file("classpath:testFiles/text/test.js");
        File htmlFile = FileUtil.file("classpath:testFiles/text/test.html");
        File jsonFile = FileUtil.file("classpath:testFiles/text/test.json");
        File abcFile = FileUtil.file("classpath:testFiles/text/test.abc");

        assertThat(MediaTypeUtils.detectMediaType(textFile)).isEqualTo(MediaType.TEXT_PLAIN);
        assertThat(MediaTypeUtils.detectMediaType(txtFile)).isEqualTo(MediaType.TEXT_PLAIN);
        assertThat(MediaTypeUtils.detectMediaType(jsFile)).isEqualTo(MediaType.TEXT_JAVASCRIPT);
        assertThat(MediaTypeUtils.detectMediaType(htmlFile)).isEqualTo(MediaType.TEXT_HTML);
        assertThat(MediaTypeUtils.detectMediaType(jsonFile)).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(MediaTypeUtils.detectMediaType(abcFile)).isEqualTo(MediaType.TEXT_PLAIN);
    }

    @Test
    void testDetectMediaType2_EmptyInputStream() {
        final InputStream nullInputStream = new NullInputStream();
        assertThat(MediaTypeUtils.detectMediaType(nullInputStream, "")).isEqualTo(MediaType.APPLICATION_OCTET_STREAM);
    }

    @Test
    void testDetectMediaType2_BrokenInputStream() {
        assertThatThrownBy(() -> MediaTypeUtils.detectMediaType(new BrokenInputStream(), ""))
                .isExactlyInstanceOf(IORuntimeException.class)
                .hasCauseInstanceOf(IOException.class)
                .hasRootCauseMessage("Broken input stream");
    }

    @Test
    void testFromFullType() {
        assertThat(MediaTypeUtils.fromFullType("text/plain")).isEqualTo(MediaType.TEXT_PLAIN);
        assertThat(MediaTypeUtils.fromFullType("type/example")).isEqualTo(MediaType.of("type", "example"));
        assertThatThrownBy(() -> MediaTypeUtils.fromFullType("illegal_full_type"))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not a legal */* media type: illegal_full_type");
        assertThatThrownBy(() -> MediaTypeUtils.fromFullType("illegal_full_type/"))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Not a legal */* media type: illegal_full_type/");
    }

    @Test
    void testBuildSupportedMediaTypes() {
        assertThat(MediaTypeUtils.buildSupportedMediaTypes()).isEqualTo(Collections.emptyList());
        assertThat(MediaTypeUtils.buildSupportedMediaTypes(MediaType.TEXT_PLAIN))
                .isEqualTo(Collections.singletonList(MediaType.TEXT_PLAIN_VALUE));
        assertThat(MediaTypeUtils.buildSupportedMediaTypes(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON))
                .isEqualTo(Arrays.asList(MediaType.TEXT_PLAIN_VALUE, MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    void testCheckIfMediaTypeSupported() {
        List<String> supportedMediaTypes = MediaTypeUtils.buildSupportedMediaTypes(
                MediaType.APPLICATION_MS_WORD,
                MediaType.APPLICATION_OOXML_DOCUMENT,
                MediaType.APPLICATION_PDF,
                MediaType.IMAGE_JPEG);


        final String fileMediaType = "application/octet-stream";
        assertThatThrownBy(() -> MediaTypeUtils.checkIfMediaTypeSupported(fileMediaType, "test.ceb", supportedMediaTypes))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("不支持的媒体类型: .ceb(application/octet-stream)");

        assertThatThrownBy(() -> MediaTypeUtils.checkIfMediaTypeSupported(fileMediaType, "test", supportedMediaTypes))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("不支持的媒体类型: application/octet-stream");

        final String fileMediaType2 = MediaType.IMAGE_PNG_VALUE;
        assertThatThrownBy(() -> MediaTypeUtils.checkIfMediaTypeSupported(fileMediaType2, "test.png", supportedMediaTypes))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("不支持的媒体类型: .png(image/png)");

        final String fileMediaType3 = MediaType.IMAGE_JPEG_VALUE;
        Assertions.assertDoesNotThrow(() -> MediaTypeUtils.checkIfMediaTypeSupported(fileMediaType3, "test.jpg", supportedMediaTypes));
    }

    @Test
    void testFromDocumentType() {
        assertThat(MediaTypeUtils.fromDocumentType(DocumentType.DOC)).isEqualTo(MediaType.APPLICATION_MS_WORD);
        assertThat(MediaTypeUtils.fromDocumentType(DocumentType.DOC).getDefaultFileExtension()).isEqualTo("doc");

        assertThat(MediaTypeUtils.fromDocumentType(DocumentType.DOCX)).isEqualTo(MediaType.APPLICATION_OOXML_DOCUMENT);
        assertThat(MediaTypeUtils.fromDocumentType(DocumentType.DOCX).getDefaultFileExtension()).isEqualTo("docx");

        assertThat(MediaTypeUtils.fromDocumentType(DocumentType.XLS)).isEqualTo(MediaType.APPLICATION_MS_EXCEL);
        assertThat(MediaTypeUtils.fromDocumentType(DocumentType.XLS).getDefaultFileExtension()).isEqualTo("xls");

        assertThat(MediaTypeUtils.fromDocumentType(DocumentType.XLSX)).isEqualTo(MediaType.APPLICATION_OOXML_SHEET);
        assertThat(MediaTypeUtils.fromDocumentType(DocumentType.XLSX).getDefaultFileExtension()).isEqualTo("xlsx");

        assertThat(MediaTypeUtils.fromDocumentType(DocumentType.PDF)).isEqualTo(MediaType.APPLICATION_PDF);
        assertThat(MediaTypeUtils.fromDocumentType(DocumentType.PDF).getDefaultFileExtension()).isEqualTo("pdf");
    }
}

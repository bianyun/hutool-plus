package plus.hutool.media.content.type;

import cn.hutool.core.io.IORuntimeException;
import org.apache.commons.io.input.BrokenInputStream;
import org.apache.commons.io.input.NullInputStream;
import org.junit.jupiter.api.Test;
import plus.hutool.media.content.type.internal.MainMediaType;
import plus.hutool.media.exception.IllegalFullMediaTypeStrException;
import plus.hutool.media.exception.IllegalMainMediaTypeException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static plus.hutool.media.test.UnitTestUtils.resolveTestFile;
import static plus.hutool.media.test.UnitTestUtils.resolveTestFileInputStream;

@SuppressWarnings("SpellCheckingInspection")
class MediaTypeUtilsTest {

    @Test
    void testDetectMediaTypeFromArchiveFile() {
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.7z"))).isEqualTo(MediaType.APPLICATION_7Z);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.tar.bz2"))).isEqualTo(MediaType.APPLICATION_BZIP2);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.tar.gz"))).isEqualTo(MediaType.APPLICATION_GZIP);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.jar"))).isEqualTo(MediaType.APPLICATION_JAR);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.rar"))).isEqualTo(MediaType.APPLICATION_RAR);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.tar"))).isEqualTo(MediaType.APPLICATION_TAR);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.tgz"))).isEqualTo(MediaType.APPLICATION_GZIP);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.xz"))).isEqualTo(MediaType.APPLICATION_XZ);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.zip"))).isEqualTo(MediaType.APPLICATION_ZIP);
    }

    @Test
    void testDetectMediaTypeFromAudioFile() {
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.aac"))).isEqualTo(MediaType.AUDIO_AAC);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.ac3"))).isEqualTo(MediaType.AUDIO_AC3);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.aiff"))).isEqualTo(MediaType.AUDIO_AIFF);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.amr"))).isEqualTo(MediaType.AUDIO_AMR);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.ape"))).isEqualTo(MediaType.AUDIO_APE);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.flac"))).isEqualTo(MediaType.AUDIO_FLAC);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.m4a"))).isEqualTo(MediaType.AUDIO_MP4);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.m4b"))).isEqualTo(MediaType.AUDIO_MP4);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.m4r"))).isEqualTo(MediaType.AUDIO_MP4);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.mka"))).isEqualTo(MediaType.AUDIO_MKA);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.mp2"))).isEqualTo(MediaType.AUDIO_MPEG);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.mp3"))).isEqualTo(MediaType.AUDIO_MPEG);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.ogg"))).isEqualTo(MediaType.AUDIO_OGG);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.wav"))).isEqualTo(MediaType.AUDIO_WAV);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.wma"))).isEqualTo(MediaType.AUDIO_WMA);
    }

    @Test
    void testDetectMediaTypeFromDocumentFile() {
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.ceb"))).isEqualTo(MediaType.APPLICATION_CEB);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.css"))).isEqualTo(MediaType.TEXT_CSS);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.csv"))).isEqualTo(MediaType.TEXT_CSV);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.doc"))).isEqualTo(MediaType.APPLICATION_DOC);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.docx"))).isEqualTo(MediaType.APPLICATION_DOCX);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.html"))).isEqualTo(MediaType.TEXT_HTML);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.js"))).isEqualTo(MediaType.TEXT_JAVASCRIPT);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.json"))).isEqualTo(MediaType.APPLICATION_JSON);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.md"))).isEqualTo(MediaType.TEXT_MARKDOWN);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.mhtml"))).isEqualTo(MediaType.APPLICATION_MHTML);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.odp"))).isEqualTo(MediaType.APPLICATION_ODP);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.ods"))).isEqualTo(MediaType.APPLICATION_ODS);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.odt"))).isEqualTo(MediaType.APPLICATION_ODT);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.ofd"))).isEqualTo(MediaType.APPLICATION_OFD);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.pdf"))).isEqualTo(MediaType.APPLICATION_PDF);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.ppt"))).isEqualTo(MediaType.APPLICATION_PPT);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.pptx"))).isEqualTo(MediaType.APPLICATION_PPTX);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.rtf"))).isEqualTo(MediaType.APPLICATION_RTF);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.txt"))).isEqualTo(MediaType.TEXT_PLAIN);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.wps"))).isEqualTo(MediaType.APPLICATION_DOC);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.xls"))).isEqualTo(MediaType.APPLICATION_XLS);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.xlsx"))).isEqualTo(MediaType.APPLICATION_XLSX);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.xml"))).isEqualTo(MediaType.APPLICATION_XML);

        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.c"))).isEqualTo(MediaType.TEXT_C_SOURCE);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.cnf"))).isEqualTo(MediaType.TEXT_CNF);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.cpp"))).isEqualTo(MediaType.TEXT_CPP_SOURCE);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.h"))).isEqualTo(MediaType.TEXT_C_HEADER);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.ini"))).isEqualTo(MediaType.TEXT_INI);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.java"))).isEqualTo(MediaType.TEXT_JAVA_SOURCE);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.py"))).isEqualTo(MediaType.TEXT_PYTHON);
    }

    @Test
    void testDetectMediaTypeFromImageFile() {
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.bmp"))).isEqualTo(MediaType.IMAGE_BMP);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.gif"))).isEqualTo(MediaType.IMAGE_GIF);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.ico"))).isEqualTo(MediaType.IMAGE_ICO);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.iff"))).isEqualTo(MediaType.IMAGE_IFF);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.jp2"))).isEqualTo(MediaType.IMAGE_JP2);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.jpeg"))).isEqualTo(MediaType.IMAGE_JPEG);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.jpg"))).isEqualTo(MediaType.IMAGE_JPEG);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.pcx"))).isEqualTo(MediaType.IMAGE_PCX);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.png"))).isEqualTo(MediaType.IMAGE_PNG);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.psd"))).isEqualTo(MediaType.IMAGE_PSD);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.ras"))).isEqualTo(MediaType.IMAGE_RAS);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.rsb"))).isEqualTo(MediaType.IMAGE_RSB);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.sgi"))).isEqualTo(MediaType.IMAGE_SGI);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.tga"))).isEqualTo(MediaType.IMAGE_TGA);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.tif"))).isEqualTo(MediaType.IMAGE_TIFF);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.tiff"))).isEqualTo(MediaType.IMAGE_TIFF);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.wbmp"))).isEqualTo(MediaType.IMAGE_WBMP);
    }

    @Test
    void testDetectMediaTypeFromVideoFile() {
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.3g2"))).isEqualTo(MediaType.VIDEO_3G2);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.3gp"))).isEqualTo(MediaType.VIDEO_3GP);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.asf"))).isEqualTo(MediaType.VIDEO_ASF);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.avi"))).isEqualTo(MediaType.VIDEO_AVI);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.f4v"))).isEqualTo(MediaType.VIDEO_F4V);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.flv"))).isEqualTo(MediaType.VIDEO_FLV);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.m4v"))).isEqualTo(MediaType.VIDEO_M4V);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.mkv"))).isEqualTo(MediaType.VIDEO_MKV);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.mov"))).isEqualTo(MediaType.VIDEO_MOV);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.mp4"))).isEqualTo(MediaType.VIDEO_MP4);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.mpg"))).isEqualTo(MediaType.VIDEO_MPEG);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.mxf"))).isEqualTo(MediaType.APPLICATION_MXF);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.ogv"))).isEqualTo(MediaType.VIDEO_OGG);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.rm"))).isEqualTo(MediaType.APPLICATION_RM);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.rmvb"))).isEqualTo(MediaType.APPLICATION_RMVB);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.swf"))).isEqualTo(MediaType.APPLICATION_SWF);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.webm"))).isEqualTo(MediaType.VIDEO_WEBM);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("test.wmv"))).isEqualTo(MediaType.VIDEO_WMV);
    }

    @Test
    void testDetectMediaTypeFromInputStream() {
        InputStream txtFileInputStream = resolveTestFileInputStream("test.txt");
        assertThat(MediaTypeUtils.detectMediaType(txtFileInputStream, "test.txt")).isEqualTo(MediaType.TEXT_PLAIN);

        InputStream audioFileInputStream = resolveTestFileInputStream("test.mp3");
        assertThat(MediaTypeUtils.detectMediaType(audioFileInputStream, "test.mp3")).isEqualTo(MediaType.AUDIO_MPEG);

        InputStream videoFileInputStream = resolveTestFileInputStream("test.avi");
        assertThat(MediaTypeUtils.detectMediaType(videoFileInputStream, "test.avi")).isEqualTo(MediaType.VIDEO_AVI);

        InputStream imageFileInputStream = resolveTestFileInputStream("test.jpg");
        assertThat(MediaTypeUtils.detectMediaType(imageFileInputStream, "test.jpg")).isEqualTo(MediaType.IMAGE_JPEG);

        InputStream docFileInputStream = resolveTestFileInputStream("test.doc");
        assertThat(MediaTypeUtils.detectMediaType(docFileInputStream, "test.doc")).isEqualTo(MediaType.APPLICATION_DOC);
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
    void testBuildSupportedMediaTypes() {
        assertThat(MediaTypeUtils.buildSupportedMediaTypes()).isEmpty();
        assertThat(MediaTypeUtils.buildSupportedMediaTypes(MediaType.TEXT_PLAIN))
                .containsExactly(MediaType.TEXT_PLAIN.strValue());
        assertThat(MediaTypeUtils.buildSupportedMediaTypes(MediaType.TEXT_PLAIN, MediaType.APPLICATION_JSON))
                .containsExactly(MediaType.TEXT_PLAIN.strValue(), MediaType.APPLICATION_JSON.strValue());
    }

    @Test
    void testCheckIfMediaTypeSupported() {
        List<String> supportedMediaTypes = MediaTypeUtils.buildSupportedMediaTypes(
                MediaType.APPLICATION_MS_WORD,
                MediaType.APPLICATION_OOXML_DOCUMENT,
                MediaType.APPLICATION_PDF);

        assertThatCode(() -> MediaTypeUtils.checkIfMediaTypeSupported("application/pdf", "test.pdf", supportedMediaTypes))
                .doesNotThrowAnyException();

        final String fileMediaType = "application/octet-stream";
        assertThatThrownBy(() -> MediaTypeUtils.checkIfMediaTypeSupported(fileMediaType, "test.ceb", supportedMediaTypes))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("不支持的媒体类型: .ceb(application/octet-stream)");

        assertThatThrownBy(() -> MediaTypeUtils.checkIfMediaTypeSupported(fileMediaType, "test", supportedMediaTypes))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("不支持的媒体类型: application/octet-stream");

        final String fileMediaType2 = MediaType.IMAGE_PNG.strValue();
        assertThatThrownBy(() -> MediaTypeUtils.checkIfMediaTypeSupported(fileMediaType2, "test.png", supportedMediaTypes))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("不支持的媒体类型: .png(image/png)");
    }

    @Test
    void testParseFromFullTypeStr() {
        assertThat(MediaTypeUtils.parseFromFullTypeStr("text/plain")).isEqualTo(MediaType.TEXT_PLAIN);
        assertThat(MediaTypeUtils.parseFromFullTypeStr("text/x-abc")).isEqualTo(MediaType.of(MainMediaType.TEXT, "x-abc"));

        assertThatThrownBy(() -> MediaTypeUtils.parseFromFullTypeStr("wrong_format_full_type"))
                .isExactlyInstanceOf(IllegalFullMediaTypeStrException.class)
                .hasMessage("Not a legal full media type string with format [mainType/subType]: wrong_format_full_type");

        assertThatThrownBy(() -> MediaTypeUtils.parseFromFullTypeStr("/wrong_format_full_type"))
                .isExactlyInstanceOf(IllegalFullMediaTypeStrException.class)
                .hasMessage("Not a legal full media type string with format [mainType/subType]: /wrong_format_full_type");

        assertThatThrownBy(() -> MediaTypeUtils.parseFromFullTypeStr("wrong_format_full_type/"))
                .isExactlyInstanceOf(IllegalFullMediaTypeStrException.class)
                .hasMessage("Not a legal full media type string with format [mainType/subType]: wrong_format_full_type/");

        assertThatThrownBy(() -> MediaTypeUtils.parseFromFullTypeStr("abc/def"))
                .isExactlyInstanceOf(IllegalMainMediaTypeException.class)
                .hasMessage("Not a legal main media type string: abc");
    }

    @Test
    void testDetectMediaTypeWhenFileDamagedOrExtensionWronglyChanged() {
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFileInputStream("test.ceb"), "test.zzz"))
                .isEqualTo(MediaType.APPLICATION_OCTET_STREAM);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFileInputStream("test.ceb"), "test.ceb"))
                .isEqualTo(MediaType.APPLICATION_CEB);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("damaged.docx")))
                .isEqualTo(MediaType.APPLICATION_DOCX);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("actually_pdf.docx")))
                .isEqualTo(MediaType.APPLICATION_PDF);
        assertThat(MediaTypeUtils.detectMediaType(resolveTestFile("actually_text.7z")))
                .isEqualTo(MediaType.TEXT_PLAIN);
    }

}

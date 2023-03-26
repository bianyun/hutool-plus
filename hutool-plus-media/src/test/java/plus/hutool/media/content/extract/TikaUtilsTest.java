package plus.hutool.media.content.extract;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.io.input.BrokenInputStream;
import org.apache.commons.io.input.NullInputStream;
import org.apache.tika.exception.ZeroByteFileException;
import org.junit.jupiter.api.Test;
import plus.hutool.media.exception.TikaParseException;

import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static plus.hutool.media.test.UnitTestUtils.resolveTestFile;

class TikaUtilsTest {

    private static final String SHORT_TEXT_FILE_CONTENT = "This is a short text file for test.";

    @Test
    void testParseToString1() {
        assertThat(TikaUtils.parseToString(resolveTestFile("test_short.txt").toPath()).trim())
                .isEqualTo(SHORT_TEXT_FILE_CONTENT);
    }

    @Test
    void testParseToString2() {
        assertThat(TikaUtils.parseToString(resolveTestFile("test_short.txt")).trim())
                .isEqualTo(SHORT_TEXT_FILE_CONTENT);
    }

    @Test
    void testParseToString3() {
        InputStream inputStream = FileUtil.getInputStream(resolveTestFile("test_short.txt"));
        assertThat(TikaUtils.parseToString(inputStream).trim()).isEqualTo(SHORT_TEXT_FILE_CONTENT);
    }

    @Test
    void testParseToString3_EmptyStream() {
        assertThatThrownBy(() -> TikaUtils.parseToString(new NullInputStream()))
                .isInstanceOf(TikaParseException.class)
                .hasMessage("文档解析出错")
                .hasCauseInstanceOf(ZeroByteFileException.class)
                .hasRootCauseMessage("InputStream must have > 0 bytes");
    }

    @Test
    void testParseToString3_BrokenStream() {
        assertThatThrownBy(() -> TikaUtils.parseToString(new BrokenInputStream()))
                .isInstanceOf(TikaParseException.class)
                .hasMessage("文档解析出错")
                .hasCauseInstanceOf(IOException.class)
                .hasRootCauseMessage("Broken input stream");
    }

    @Test
    void testAllSupportedDocFilesParsing() {
        assertThat(TikaUtils.parseToString(resolveTestFile("test.ceb"))).isEmpty();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.css"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.csv"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.doc"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.docx"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.html"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.js"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.json"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.md"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.mhtml"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.odp"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.ods"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.odt"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.ofd"))).isEmpty();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.pdf"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.pps"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.ppsx"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.ppt"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.pptx"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.rtf"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.sh"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.sql"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.txt"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.wps"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.xls"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.xlsx"))).isNotBlank();
        assertThat(TikaUtils.parseToString(resolveTestFile("test.xml"))).isNotBlank();
    }
}

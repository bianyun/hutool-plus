package plus.hutool.media.converter.file;

import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;
import plus.hutool.core.io.FileUtils;
import plus.hutool.media.content.type.MediaType;
import plus.hutool.media.content.type.MediaTypeUtils;
import plus.hutool.media.exception.UnsupportedFileConversionSrcMediaTypeException;
import plus.hutool.media.test.UnitTestUtils;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static plus.hutool.media.test.UnitTestUtils.resolveTestFile;

class FileConvertUtilsTest {

    @Test
    void testConversionToPdfSuccessful() {
        assertConversionToPdfSuccessful(resolveTestFile("test.css"));
        assertConversionToPdfSuccessful(resolveTestFile("test.doc"));
        assertConversionToPdfSuccessful(resolveTestFile("test.docx"));
        assertConversionToPdfSuccessful(resolveTestFile("test.html"));
        assertConversionToPdfSuccessful(resolveTestFile("test.js"));
        assertConversionToPdfSuccessful(resolveTestFile("test.json"));
        assertConversionToPdfSuccessful(resolveTestFile("test.md"));
        assertConversionToPdfSuccessful(resolveTestFile("test.odt"));
        assertConversionToPdfSuccessful(resolveTestFile("test.ofd"));
        assertConversionToPdfSuccessful(resolveTestFile("test.pdf"));
        assertConversionToPdfSuccessful(resolveTestFile("test.pps"));
        assertConversionToPdfSuccessful(resolveTestFile("test.ppsx"));
        assertConversionToPdfSuccessful(resolveTestFile("test.ppt"));
        assertConversionToPdfSuccessful(resolveTestFile("test.pptx"));
        assertConversionToPdfSuccessful(resolveTestFile("test.rtf"));
        assertConversionToPdfSuccessful(resolveTestFile("test.sh"));
        assertConversionToPdfSuccessful(resolveTestFile("test.sql"));
        assertConversionToPdfSuccessful(resolveTestFile("test.txt"));
        assertConversionToPdfSuccessful(resolveTestFile("test.wps"));
        assertConversionToPdfSuccessful(resolveTestFile("test.xls"));
        assertConversionToPdfSuccessful(resolveTestFile("test.xlsx"));
        assertConversionToPdfSuccessful(resolveTestFile("test.xml"));
    }

    @Test
    void testConversionToPdfFailed() {
        assertConversionToPdfFailed(resolveTestFile("test.ceb"));
        assertConversionToPdfFailed(resolveTestFile("test.csv"));
        assertConversionToPdfFailed(resolveTestFile("test.mhtml"));
        assertConversionToPdfFailed(resolveTestFile("test.odp"));
        assertConversionToPdfFailed(resolveTestFile("test.ods"));
    }

    @Test
    void testConvertFileToPdf2() {
        final File srcFile = resolveTestFile("test.txt");

        final File pdfFileInSameDir = FileConvertUtils.convertFileToPdf(srcFile, true);
        assertThat(pdfFileInSameDir).exists().isFile().hasExtension("pdf");
        assertThat(pdfFileInSameDir.getParentFile()).isEqualTo(srcFile.getParentFile());
        FileUtil.del(pdfFileInSameDir);

        final File pdfFileNotInSameDir = FileConvertUtils.convertFileToPdf(srcFile, false);
        assertThat(pdfFileNotInSameDir).exists().isFile().hasExtension("pdf");
        assertThat(pdfFileNotInSameDir.getParentFile()).isNotEqualTo(srcFile.getParentFile());
        FileUtil.del(pdfFileNotInSameDir);
    }

    private static void assertConversionToPdfFailed(File srcFile) {
        MediaType srcMediaType = MediaTypeUtils.detectMediaType(srcFile);
        MediaType destMediaType = MediaType.APPLICATION_PDF;

        assertThatThrownBy(() -> FileConvertUtils.convertFileToPdf(srcFile))
                .isInstanceOf(UnsupportedFileConversionSrcMediaTypeException.class)
                .hasMessage("不支持如下格式的文件转换: [%s => %s]", srcMediaType, destMediaType);
    }

    private static void assertConversionToPdfSuccessful(File srcFile) {
        File destPdfFile = FileConvertUtils.convertFileToPdf(srcFile);

        if (FileUtils.hasExtension(srcFile, "pdf")) {
            assertThat(srcFile == destPdfFile).isTrue();
        } else {
            assertThat(destPdfFile).isFile().exists().hasExtension("pdf");
            assertThat(MediaTypeUtils.detectMediaType(destPdfFile)).isEqualTo(MediaType.APPLICATION_PDF);

            if (UnitTestUtils.CLEAN_UP_RESULT_FILE) {
                FileUtil.del(destPdfFile);
            }
        }
    }
}

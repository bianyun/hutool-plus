package plus.hutool.media.document.convert;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.documents4j.api.DocumentType;
import com.documents4j.util.OsUtils;
import org.junit.jupiter.api.Test;
import plus.hutool.core.io.FileUtils;

import java.io.File;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static plus.hutool.media.misc.MediaType.Value.PDF;

class DocumentConvertUtilsTest {

    @Test
    void testConvert1() {
        if (OsUtils.isWindows()) {
            Path docFile = FileUtil.file("classpath:testFiles/documents/test.doc").toPath();
            final File result = DocumentConvertUtils.convert(docFile, DocumentType.DOC, DocumentType.PDF);
            assertThat(result).exists().isFile().hasExtension(PDF);
            FileUtil.del(result);
        }
    }

    @Test
    void testConvert2() {
        if (OsUtils.isWindows()) {
            Path docFile = FileUtil.file("classpath:testFiles/documents/test.doc").toPath();
            final File result = DocumentConvertUtils.convert(docFile, DocumentType.DOC, DocumentType.PDF, 5);
            assertThat(result).exists().isFile().hasExtension(PDF);
            FileUtil.del(result);
        }
    }

    @Test
    void testConvert3() {
        if (OsUtils.isWindows()) {
            File docFile = FileUtil.file("classpath:testFiles/documents/test.doc");
            final File result = DocumentConvertUtils.convert(docFile, DocumentType.DOC, DocumentType.PDF);
            assertThat(result).exists().isFile().hasExtension(PDF);
            FileUtil.del(result);

            File nonExistsFile = FileUtil.file("classpath:testFiles/documents/nonExists.doc");
            assertThatThrownBy(() -> DocumentConvertUtils.convert(nonExistsFile, DocumentType.DOC, DocumentType.PDF))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(StrUtil.format("文件必须存在: {}", nonExistsFile.getAbsolutePath()));

            File dirFile = FileUtils.createDirUnderRandomTempDir("test.doc");
            assertThatThrownBy(() -> DocumentConvertUtils.convert(dirFile, DocumentType.DOC, DocumentType.PDF))
                    .isExactlyInstanceOf(IllegalArgumentException.class)
                    .hasMessage(StrUtil.format("文件必须存在: {}", dirFile.getAbsolutePath()));
            FileUtil.del(dirFile);
        }
    }

    @Test
    void testConvert4() {
        if (OsUtils.isWindows()) {
            File docFile = FileUtil.file("classpath:testFiles/documents/test.doc");
            final File result = DocumentConvertUtils.convert(docFile, DocumentType.DOC, DocumentType.PDF, 5);
            assertThat(result).exists().isFile().hasExtension(PDF);
            FileUtil.del(result);
        }
    }
}

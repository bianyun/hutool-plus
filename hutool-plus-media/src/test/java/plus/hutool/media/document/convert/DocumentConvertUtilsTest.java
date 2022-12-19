package plus.hutool.media.document.convert;

import cn.hutool.core.io.FileUtil;
import com.documents4j.api.DocumentType;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;
import static plus.hutool.media.misc.MediaType.Value.PDF;

class DocumentConvertUtilsTest {

    @Test
    void testConvert1() {
        Path docFile = FileUtil.file("classpath:testFiles/documents/test.doc").toPath();
        final File result = DocumentConvertUtils.convert(docFile, DocumentType.DOC, DocumentType.PDF);
        assertThat(result).exists().isFile().hasExtension(PDF);
        FileUtil.del(result);
    }

    @Test
    void testConvert2() {
        Path docFile = FileUtil.file("classpath:testFiles/documents/test.doc").toPath();
        final File result = DocumentConvertUtils.convert(docFile, DocumentType.DOC, DocumentType.PDF, 5);
        assertThat(result).exists().isFile().hasExtension(PDF);
        FileUtil.del(result);
    }

    @Test
    void testConvert3() {
        File docFile = FileUtil.file("classpath:testFiles/documents/test.doc");
        final File result = DocumentConvertUtils.convert(docFile, DocumentType.DOC, DocumentType.PDF);
        assertThat(result).exists().isFile().hasExtension(PDF);
        FileUtil.del(result);
    }

    @Test
    void testConvert4() {
        File docFile = FileUtil.file("classpath:testFiles/documents/test.doc");
        final File result = DocumentConvertUtils.convert(docFile, DocumentType.DOC, DocumentType.PDF, 5);
        assertThat(result).exists().isFile().hasExtension(PDF);
        FileUtil.del(result);
    }
}

package plus.hutool.media.converter.file.impl.pdf;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import plus.hutool.core.iterable.collection.CollUtils;
import plus.hutool.media.content.type.MediaType;
import plus.hutool.media.exception.FileConversionException;
import plus.hutool.media.converter.file.FileConvertConfig;

import java.io.File;
import java.util.Set;

/**
 * 文件转换器 - (doc, docx, odt, rtf, txt, md, html) ==> pdf
 *
 * @author bianyun
 * @date 2023/2/14
 */
@SuppressWarnings({"unused", "JavadocDeclaration"})
public class CommonDocToPdfConverter extends AbstractToPdfConverter {
    @Override
    public Set<MediaType> getSupportedSrcMediaTypes() {
        return CollUtils.unmodifiableSet(
                MediaType.TEXT_CSS,
                MediaType.APPLICATION_DOC,
                MediaType.APPLICATION_DOCX,
                MediaType.TEXT_HTML,
                MediaType.TEXT_JAVASCRIPT,
                MediaType.APPLICATION_JSON,
                MediaType.TEXT_MARKDOWN,
                MediaType.APPLICATION_ODT,
                MediaType.APPLICATION_RTF,
                MediaType.APPLICATION_SH,
                MediaType.APPLICATION_SQL,
                MediaType.TEXT_PLAIN,
                MediaType.APPLICATION_WPS,
                MediaType.APPLICATION_XML
        );
    }

    @Override
    protected void doConvert(File srcFile, File destFile, FileConvertConfig config) {
        try {
            Document doc = new Document(srcFile.getAbsolutePath());
            doc.save(destFile.getAbsolutePath(), SaveFormat.PDF);
        } catch (Exception e) {
            throw new FileConversionException("文件转换出错", e);
        }
    }
}

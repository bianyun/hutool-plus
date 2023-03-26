package plus.hutool.media.converter.file.impl.pdf;

import org.ofdrw.converter.ConvertHelper;
import plus.hutool.core.iterable.collection.CollUtils;
import plus.hutool.media.content.type.MediaType;
import plus.hutool.media.exception.FileConversionException;
import plus.hutool.media.converter.file.FileConvertConfig;

import java.io.File;
import java.util.Set;

/**
 * 文件转换器 - ofd ==> pdf
 *
 * @author bianyun
 * @date 2023/2/14
 */
@SuppressWarnings({"unused", "JavadocDeclaration"})
public class OfdToPdfConverter extends AbstractToPdfConverter {
    @Override
    public Set<MediaType> getSupportedSrcMediaTypes() {
        return CollUtils.unmodifiableSet(MediaType.APPLICATION_OFD);
    }

    @Override
    protected void doConvert(File srcFile, File destFile, FileConvertConfig config) {
        try {
            ConvertHelper.toPdf(srcFile.toPath(), destFile);
        } catch (Exception e) {
            throw new FileConversionException("文件转换出错", e);
        }
    }
}

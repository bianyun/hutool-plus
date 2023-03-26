package plus.hutool.media.converter.file.impl;

import plus.hutool.core.io.FileUtils;
import plus.hutool.core.lang.Asserts;
import plus.hutool.media.content.type.MediaType;
import plus.hutool.media.content.type.MediaTypeUtils;
import plus.hutool.media.converter.file.FileConvertConfig;
import plus.hutool.media.converter.file.FileConverter;

import java.io.File;

/**
 * 文件转换器基类
 *
 * @author bianyun
 * @date 2023/2/14
 */
@SuppressWarnings("JavadocDeclaration")
public abstract class AbstractFileConverter implements FileConverter {

    /**
     * 文件转换器具体实现类的转换逻辑
     *
     * @param srcFile  来源文件
     * @param destFile 目标文件
     * @param config   文件转换配置
     */
    @SuppressWarnings("unused")
    protected abstract void doConvert(File srcFile, File destFile, FileConvertConfig config);

    @Override
    public File convert(File srcFile, FileConvertConfig config) {
        Asserts.isTrue(FileUtils.fileExists(srcFile), "文件转换的来源文件不存在: {}", srcFile.getAbsolutePath());

        MediaType srcMediaType = MediaTypeUtils.detectMediaType(srcFile);
        MediaType destMediaType = getDestMediaType();

        Asserts.isTrue(getSupportedSrcMediaTypes().contains(srcMediaType),
                "本文件转换器[{}] 不支持来源文件的媒体类型: [{}]", this.getClass().getSimpleName(),  srcMediaType);

        File destFile = config.resolveDestFileBeforeConversion(srcFile, destMediaType);

        doConvert(srcFile, destFile, config);

        return destFile;
    }

}

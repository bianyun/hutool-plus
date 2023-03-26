package plus.hutool.media.converter.file;


import plus.hutool.media.content.type.MediaType;

import java.io.File;
import java.util.Set;

/**
 * 文件转换器接口
 *
 * @author bianyun
 * @date 2023/2/14
 */
@SuppressWarnings({"SameReturnValue", "JavadocDeclaration"})
public interface FileConverter {

    /**
     * 获取本文件转换器支持的来源文件媒体类型集合
     */
    Set<MediaType> getSupportedSrcMediaTypes();

    /**
     * 获取本文件转换器的目标文件媒体类型
     */
    MediaType getDestMediaType();

    /**
     * 转换文件
     *
     * @param srcFile 来源文件
     * @param config 文件转换配置
     * @return 转换后的目标文件
     */
    File convert(File srcFile, FileConvertConfig config);

}

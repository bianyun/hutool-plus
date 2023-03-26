package plus.hutool.media.converter.file;


import plus.hutool.media.content.type.MediaType;
import plus.hutool.media.content.type.MediaTypeUtils;
import plus.hutool.media.exception.UnsupportedFileConversionSrcMediaTypeException;

import java.io.File;
import java.util.Set;

import static plus.hutool.media.converter.file.FileConverterRegistry.getSupportedSrcMediaTypesByDestType;

/**
 * 文件转换工具类
 *
 * @author bianyun
 * @date 2023/2/15
 */
@SuppressWarnings("JavadocDeclaration")
public abstract class FileConvertUtils {
    private FileConvertUtils() {}

    /**
     * 将文件转换为 PDF 文件
     *
     * @param srcFile 来源文件
     * @return 转换后的 PDF 文件
     * @throws UnsupportedFileConversionSrcMediaTypeException 如果不存在支持来源文件媒体类型的转换器
     */
    public static File convertFileToPdf(File srcFile) {
        return convertFileToPdf(srcFile, new FileConvertConfig(false));
    }

    /**
     * saveDestFileInSameDirAsSrcFile
     * 将文件转换为 PDF 文件
     *
     * @param srcFile 来源文件
     * @param saveDestFileInSameDirAsSrcFile 是否将转换后的文件存储到来源文件的相同目录下
     * @return 转换后的 PDF 文件
     * @throws UnsupportedFileConversionSrcMediaTypeException 如果不存在支持来源文件媒体类型的转换器
     */
    public static File convertFileToPdf(File srcFile, boolean saveDestFileInSameDirAsSrcFile) {
        return convertFileToPdf(srcFile, new FileConvertConfig(saveDestFileInSameDirAsSrcFile));
    }

    /**
     * 将文件转换为 PDF 文件
     *
     * @param srcFile 来源文件
     * @param config  文件转换配置
     * @return 转换后的 PDF 文件
     * @throws UnsupportedFileConversionSrcMediaTypeException 如果不存在支持来源文件媒体类型的转换器
     */
    public static File convertFileToPdf(File srcFile, FileConvertConfig config) {
        MediaType srcMediaType = MediaTypeUtils.detectMediaType(srcFile);
        MediaType destMediaType = MediaType.APPLICATION_PDF;

        if (srcMediaType.equals(destMediaType)) {
            return srcFile;
        }

        Set<MediaType> supportedSrcMediaTypes = getSupportedSrcMediaTypesByDestType(destMediaType);
        if (!supportedSrcMediaTypes.contains(srcMediaType)) {
            throw new UnsupportedFileConversionSrcMediaTypeException(srcMediaType, destMediaType);
        }

        FileConverter converter = FileConverterRegistry.lookup(srcMediaType, destMediaType);
        return converter.convert(srcFile, config);
    }

}

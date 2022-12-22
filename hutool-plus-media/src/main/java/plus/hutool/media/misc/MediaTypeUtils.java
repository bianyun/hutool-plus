package plus.hutool.media.misc;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.documents4j.api.DocumentType;
import plus.hutool.core.io.FileUtils;
import plus.hutool.core.lang.Asserts;
import plus.hutool.core.lang.ExceptionUtils;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 媒体类型工具
 *
 * @author bianyun
 * @date 2022/12/08
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class MediaTypeUtils {

    private static final Map<String, MediaType> OBSOLETED_TYPES_MAP = MapUtil.<String, MediaType>builder()
            .put("text/json", MediaType.APPLICATION_JSON)
            .put("application/javascript", MediaType.TEXT_JAVASCRIPT)
            .put("application/vnd.oasis.opendocument.database", MediaType.APPLICATION_OPENDOCUMENT_BASE)
            .build();

    private MediaTypeUtils() {}

    /**
     * 判断 媒体类型 {@link MediaType} 对象是否是指定的媒体类型中的任意一个
     *
     * @param lhsType    媒体类型 {@link MediaType} 对象
     * @param first      第一个媒体类型对象
     * @param remainings 剩余的所有媒体对象（变长参数）
     * @return 媒体类型 {@link MediaType} 对象是否是指定的媒体类型中的任意一个
     */
    public static boolean isAnyOf(MediaType lhsType, MediaType first, MediaType... remainings) {
        if (lhsType.equals(first)) {
            return true;
        }

        for (MediaType type : remainings) {
            if (lhsType.equals(type)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测媒体类型 {@link MediaType}
     *
     * @param file 待检测的文件
     * @return 媒体类型
     */
    public static MediaType detectMediaType(File file) {
        String mediaTypeValue = TikaUtils.detectMediaType(file);
        Asserts.notBlank(mediaTypeValue, "文件的媒体类型不能为空: {}", file.getAbsolutePath());

        if (OBSOLETED_TYPES_MAP.containsKey(mediaTypeValue)) {
            return OBSOLETED_TYPES_MAP.get(mediaTypeValue);
        } else if (mediaTypeValue.equals(MediaType.APPLICATION_OCTET_STREAM_VALUE)
                || mediaTypeValue.equals(MediaType.TEXT_PLAIN_VALUE)) {
            String fileExtension = FileUtils.getFileExtension(file.getName());
            if (StrUtil.isNotBlank(fileExtension)) {
                MediaType result = MediaType.getOneByFileExtension(fileExtension);
                if (result != null) {
                    return result;
                }
            }
        }
        return fromFullType(mediaTypeValue);
    }

    public static MediaType detectMediaType(InputStream inputStream, String filename) {
        String mediaTypeValue = TikaUtils.detectMediaType(inputStream, filename);
        return fromFullType(mediaTypeValue);
    }

    /**
     * 从完整类型字符串创建媒体类型 {@link MediaType} 对象
     *
     * @param fullType 完整类型字符串
     * @return 媒体类型 {@link MediaType} 对象
     */
    public static MediaType fromFullType(String fullType) {
        int separator = fullType.indexOf('/');
        if (separator == -1 || fullType.length() == separator + 1) {
            throw new IllegalArgumentException("Not a legal */* media type: " + fullType);
        } else {
            String type = fullType.substring(0, separator);
            String subtype = fullType.substring(separator + 1);
            return MediaType.of(type, subtype);
        }
    }

    /**
     * 构建支持的媒体类型列表
     *
     * @param mediaTypes {@link MediaType}
     * @return 支持的媒体类型字符串列表
     */
    public static List<String> buildSupportedMediaTypes(MediaType... mediaTypes) {
        List<String> resultList = new ArrayList<>();
        for (MediaType type : mediaTypes) {
            resultList.add(type.toString());
        }
        return resultList;
    }

    /**
     * 检查指定的媒体类型是否被支持
     *
     * @param fileMediaType       媒体类型
     * @param originalFilename    原始文件名
     * @param supportedMediaTypes 支持的媒体类型
     */
    public static void checkIfMediaTypeSupported(String fileMediaType,
                                                 String originalFilename,
                                                 List<String> supportedMediaTypes) {
        if (!supportedMediaTypes.contains(fileMediaType)) {
            String filenameExtension = FileUtils.getFileExtensionWithPrefixDot(originalFilename);

            String fileType;
            if (StrUtil.isNotBlank(filenameExtension)) {
                fileType = StrUtil.format("{}({})", filenameExtension, fileMediaType);
            } else {
                fileType = fileMediaType;
            }
            throw ExceptionUtils.illegalArgumentException("不支持的媒体类型: {}", fileType);
        }
    }

    public static MediaType fromDocumentType(DocumentType documentType) {
        return MediaType.of(documentType.getType(), documentType.getSubtype());
    }
}

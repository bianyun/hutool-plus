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

    public static MediaType detectMediaType(File file) {
        String mediaTypeValue = TikaUtils.detectMediaType(file);
        Asserts.notBlank(mediaTypeValue, "文件的媒体类型不能为空: {}", file.getAbsolutePath());

        if (OBSOLETED_TYPES_MAP.containsKey(mediaTypeValue)) {
            return OBSOLETED_TYPES_MAP.get(mediaTypeValue);
        } else if (mediaTypeValue.equals(MediaType.APPLICATION_OCTET_STREAM_VALUE) ||
                mediaTypeValue.equals(MediaType.TEXT_PLAIN_VALUE)) {
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

    public static List<String> buildSupportedMediaTypes(MediaType... mediaTypes) {
        List<String> resultList = new ArrayList<>();
        for (MediaType type : mediaTypes) {
            resultList.add(type.toString());
        }
        return resultList;
    }

    public static void checkIfMediaTypeSupported(String fileMediaType, String originalFilename, List<String> supportedMediaTypes) {
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

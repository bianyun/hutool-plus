package plus.hutool.media.content.type;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.tika.Tika;
import plus.hutool.core.io.FileUtils;
import plus.hutool.core.lang.Asserts;
import plus.hutool.core.lang.ExceptionUtils;
import plus.hutool.media.content.type.internal.MainMediaType;
import plus.hutool.media.content.type.internal.MediaTypeAliasMap;
import plus.hutool.media.exception.IllegalFullMediaTypeStrException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static plus.hutool.core.iterable.collection.ArrayUtils.toSet;
import static plus.hutool.media.content.type.MediaType.APPLICATION_MHTML;
import static plus.hutool.media.content.type.MediaType.APPLICATION_OCTET_STREAM;
import static plus.hutool.media.content.type.MediaType.APPLICATION_OFD;
import static plus.hutool.media.content.type.MediaType.APPLICATION_RM;
import static plus.hutool.media.content.type.MediaType.APPLICATION_RMVB;
import static plus.hutool.media.content.type.MediaType.APPLICATION_ZIP;
import static plus.hutool.media.content.type.MediaType.AUDIO_AIFF;
import static plus.hutool.media.content.type.MediaType.AUDIO_OGG;
import static plus.hutool.media.content.type.MediaType.AUDIO_VORBIS;
import static plus.hutool.media.content.type.MediaType.IMAGE_IFF;
import static plus.hutool.media.content.type.MediaType.MULTIPART_RELATED;
import static plus.hutool.media.content.type.MediaType.TEXT_PLAIN;
import static plus.hutool.media.content.type.MediaType.VIDEO_F4V;
import static plus.hutool.media.content.type.MediaType.VIDEO_QUICKTIME;

/**
 * 媒体类型工具类
 *
 * @author bianyun
 * @date 2022/12/08
 */
@SuppressWarnings({"SpellCheckingInspection", "JavadocDeclaration"})
public abstract class MediaTypeUtils {
    private static final MediaTypeAliasMap DEPRECATED_ALIAS_MAP = MediaTypeAliasMap.INSTANCE
            .addAlias(MediaType.APPLICATION_GZIP, "application/x-gzip")
            .addAlias(MediaType.APPLICATION_RAR, "application/x-rar-compressed")
            .addAlias(MediaType.APPLICATION_TAR, "application/x-gtar")
            .addAlias(MediaType.APPLICATION_SQL, "text/x-sql")
            .addAlias(MediaType.TEXT_JAVASCRIPT, "application/javascript")
            .addAlias(MediaType.TEXT_MARKDOWN, "text/x-web-markdown")
            .addAlias(MediaType.TEXT_C_SOURCE, "text/x-csrc")
            .addAlias(MediaType.TEXT_CPP_SOURCE, "text/x-c++src")
            .addAlias(MediaType.TEXT_C_HEADER, "text/x-chdr")
            .addAlias(MediaType.IMAGE_PCX, "image/vnd.zbrush.pcx")
            .addAlias(MediaType.AUDIO_AAC, "audio/x-aac");

    private static final Map<MediaType, Set<MediaType>> TIKA_FALSE_RESULT_TO_TRUE_TYPES_MAP =
            MapUtil.<MediaType, Set<MediaType>>builder()
                    .put(MULTIPART_RELATED, toSet(APPLICATION_MHTML))
                    .put(APPLICATION_RM, toSet(APPLICATION_RMVB))
                    .put(APPLICATION_ZIP, toSet(APPLICATION_OFD))
                    .put(AUDIO_AIFF, toSet(IMAGE_IFF))
                    .put(AUDIO_VORBIS, toSet(AUDIO_OGG))
                    .put(VIDEO_QUICKTIME, toSet(VIDEO_F4V))
                    .build();

    private MediaTypeUtils() {}

    /**
     * 探测文件的媒体类型 {@link MediaType}
     *
     * @param file 文件对象
     * @return 文件的媒体类型 {@link MediaType}
     */
    public static MediaType detectMediaType(File file) {
        String mediaTypeParsedByTika = detectMediaTypeByTika(file);
        Asserts.notBlank(mediaTypeParsedByTika, "文件的媒体类型不能为空: {}", file.getAbsolutePath());
        return doDetectMediaType(mediaTypeParsedByTika, file.getName());
    }

    /**
     * 根据文件的 {@link InputStream} 和 文件名探测文件的媒体类型 {@link MediaType}
     *
     * @param inputStream 文件的 {@link InputStream} 对象
     * @param filename 文件名
     * @return 文件的媒体类型 {@link MediaType}
     */
    public static MediaType detectMediaType(InputStream inputStream, String filename) {
        String mediaTypeParsedByTika = detectMediaTypeByTika(inputStream, filename);
        return doDetectMediaType(mediaTypeParsedByTika, filename);
    }

    /**
     * 根据 {@link MediaType} 对象的变长数组 构建支持的媒体类型列表（字符串列表格式）
     *
     * @param mediaTypes {@link MediaType} 对象的变长数组
     * @return 媒体类型列表（字符串列表格式）
     */
    public static List<String> buildSupportedMediaTypes(MediaType... mediaTypes) {
        List<String> resultList = new ArrayList<>();
        for (MediaType type : mediaTypes) {
            resultList.add(type.strValue());
        }
        return resultList;
    }

    /**
     * 校验是否支持指定的媒体类型
     *
     * @param mediaType 媒体类型
     * @param filename 文件名
     * @param supportedMediaTypes 支持的媒体类型列表
     */
    public static void checkIfMediaTypeSupported(String mediaType, String filename, List<String> supportedMediaTypes) {
        if (!supportedMediaTypes.contains(mediaType)) {
            String filenameExtension = FileUtils.getFileExtensionWithPrefixDot(filename);

            String typeMsg;
            if (StrUtil.isNotBlank(filenameExtension)) {
                typeMsg = StrUtil.format("{}({})", filenameExtension, mediaType);
            } else {
                typeMsg = mediaType;
            }
            throw ExceptionUtils.illegalArgumentException("不支持的媒体类型: {}", typeMsg);
        }
    }

    /**
     * 从完整的媒体类型字符串（格式：MainMediaType/SubMediaType）解析出媒体类型 {@link MediaType}
     *
     * @param fullType 完整的媒体类型字符串
     * @return 媒体类型 {@link MediaType}
     */
    static MediaType parseFromFullTypeStr(String fullType) {
        int separatorIndex = fullType.indexOf('/');
        if (separatorIndex == -1 || separatorIndex == 0 || separatorIndex == fullType.length() - 1) {
            throw new IllegalFullMediaTypeStrException(fullType);
        } else {
            String mainTypeStr = fullType.substring(0, separatorIndex);
            String subTypeStr = fullType.substring(separatorIndex + 1);

            MainMediaType mainType = MainMediaType.of(mainTypeStr);
            return MediaType.of(mainType, subTypeStr);
        }
    }

    private static MediaType doDetectMediaType(String fullMediaTypeStr, String filename) {
        String normalizedFullMediaTypeStr = ReUtil.delAll("\\s*;.*", fullMediaTypeStr).toLowerCase();

        if (DEPRECATED_ALIAS_MAP.isDeprecated(normalizedFullMediaTypeStr)) {
            return DEPRECATED_ALIAS_MAP.getNormalizedMediaType(normalizedFullMediaTypeStr);
        }

        MediaType result = parseFromFullTypeStr(normalizedFullMediaTypeStr);

        Set<MediaType> needReviewedTikaDetectedMediaTypes = toSet(TEXT_PLAIN, APPLICATION_OCTET_STREAM);
        needReviewedTikaDetectedMediaTypes.addAll(TIKA_FALSE_RESULT_TO_TRUE_TYPES_MAP.keySet());

        if (result.isAnyOf(needReviewedTikaDetectedMediaTypes)) {
            String fileExtension = FileUtils.getFileExtension(filename);

            if (StrUtil.isNotBlank(fileExtension) && !result.containsFileExtension(fileExtension)) {
                MediaType typeGetByExt = MediaType.getOneByFileExtension(fileExtension);
                if (typeGetByExt != null) {
                    if (TIKA_FALSE_RESULT_TO_TRUE_TYPES_MAP.containsKey(result)) {
                        // Tika 在解析某些特定类型的二进制文件时会出错，如果根据文件后缀查询出的媒体类型
                        // 在预先总结出的TIKA错误结果到正确结果集合的映射MAP中能查找到映射项，则使用查询
                        // 到缓存中的可能更正确的媒体类型
                        Set<MediaType> mediaTypes = TIKA_FALSE_RESULT_TO_TRUE_TYPES_MAP.get(result);
                        if (mediaTypes.contains(typeGetByExt)) {
                            result = typeGetByExt;
                        }
                    } else if (result.equals(TEXT_PLAIN) && typeGetByExt.getMainType() == MainMediaType.TEXT) {
                        // 当 Tika 解析出来为 text/plain 时，说明文件是文本类文件。如果根据文件名后缀从预定义媒体
                        // 类型缓存里查询到的 MediaType 的主类型也是文本类型时，则使用此查询到的更具体的媒体类型
                        result = typeGetByExt;
                    } else if (result.equals(APPLICATION_OCTET_STREAM)) {
                        // 当 Tika 解析出来为 application/octet-stream 时，说明文件一定是二进制文件。从预定义媒体
                        // 类型缓存里根据后缀名查询到更具体的媒体类型
                        result = typeGetByExt;
                    }
                }
            }
        }

        return result;
    }

    private static String detectMediaTypeByTika(File file) {
        try {
            return new Tika().detect(file);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    private static String detectMediaTypeByTika(InputStream stream, String filename) {
        try {
            return new Tika().detect(stream, filename);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

}

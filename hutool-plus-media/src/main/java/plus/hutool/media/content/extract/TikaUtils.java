package plus.hutool.media.content.extract;

import cn.hutool.core.io.FileUtil;
import org.apache.tika.Tika;
import plus.hutool.core.iterable.collection.CollUtils;
import plus.hutool.media.content.type.MediaType;
import plus.hutool.media.exception.TikaParseException;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.Set;

/**
 * Tika 工具
 *
 * @author bianyun
 * @date 2022/12/08
 */
@SuppressWarnings("JavadocDeclaration")
public abstract class TikaUtils {
    @SuppressWarnings("unused")
    public static final Set<MediaType> SUPPORTED_MEDIA_TYPES_FOR_PARSE_STRING =
            CollUtils.unmodifiableSet(
                    MediaType.TEXT_CSS,
                    MediaType.TEXT_CSV,
                    MediaType.APPLICATION_DOC,
                    MediaType.APPLICATION_DOCX,
                    MediaType.TEXT_HTML,
                    MediaType.TEXT_JAVASCRIPT,
                    MediaType.APPLICATION_JSON,
                    MediaType.TEXT_MARKDOWN,
                    MediaType.APPLICATION_MHTML,
                    MediaType.APPLICATION_ODP,
                    MediaType.APPLICATION_ODS,
                    MediaType.APPLICATION_ODT,
                    MediaType.APPLICATION_PDF,
                    MediaType.APPLICATION_PPS,
                    MediaType.APPLICATION_PPSX,
                    MediaType.APPLICATION_PPT,
                    MediaType.APPLICATION_PPTX,
                    MediaType.APPLICATION_RTF,
                    MediaType.APPLICATION_SH,
                    MediaType.APPLICATION_SQL,
                    MediaType.TEXT_PLAIN,
                    MediaType.APPLICATION_WPS,
                    MediaType.APPLICATION_XLS,
                    MediaType.APPLICATION_XLSX,
                    MediaType.APPLICATION_XML
            );

    private TikaUtils() {
    }

    /**
     * 将文档文件解析为字符串
     *
     * @param path 文档文件的 {@link Path} 对象
     * @return 解析出的字符串
     */
    public static String parseToString(Path path) {
        return parseToString(FileUtil.getInputStream(path));
    }

    /**
     * 将文档文件解析为字符串
     *
     * @param file 文档文件
     * @return 解析出的字符串
     */
    public static String parseToString(File file) {
        return parseToString(FileUtil.getInputStream(file));
    }

    /**
     * 将文档文件的 {@link InputStream} 对象解析为字符串
     *
     * @param stream 文档文件的 {@link InputStream} 对象
     * @return 解析出的字符串
     */
    public static String parseToString(InputStream stream) {
        try {
            return new Tika().parseToString(stream);
        } catch (Exception e) {
            throw new TikaParseException("文档解析出错", e);
        }
    }
}

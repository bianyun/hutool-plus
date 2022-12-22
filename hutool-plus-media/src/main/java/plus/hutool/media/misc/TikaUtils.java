package plus.hutool.media.misc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import plus.hutool.media.exception.TikaParseException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * Tika 工具
 *
 * @author bianyun
 * @date 2022/12/08
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class TikaUtils {
    private TikaUtils() {
    }

    public static String detectMediaType(Path path) {
        return detectMediaType(path.toFile());
    }

    /**
     * 探测媒体类型（字符串格式）
     *
     * @param file 带探测的文件
     * @return 媒体类型（字符串格式）
     */
    public static String detectMediaType(File file) {
        try {
            return new Tika().detect(file);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }

    }

    /**
     * 探测媒体类型（字符串格式）
     *
     * @param stream   输入流
     * @param filename 文件名
     * @return 媒体类型（字符串格式）
     */
    public static String detectMediaType(InputStream stream, String filename) {
        try {
            return new Tika().detect(stream, filename);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    public static String detectMediaType(String filename) {
        return new Tika().detect(filename);
    }

    public static String parseToString(Path path) {
        return parseToString(FileUtil.getInputStream(path));
    }

    public static String parseToString(File file) {
        return parseToString(FileUtil.getInputStream(file));
    }

    /**
     * 将输入流解析成字符串
     *
     * @param stream 输入流
     * @return 结果字符串
     */
    public static String parseToString(InputStream stream) {
        try {
            return new Tika().parseToString(stream);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        } catch (TikaException e) {
            throw new TikaParseException("Tika 解析文档出错", e);
        }
    }
}

package plus.hutool.media.misc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

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
    private TikaUtils() {}

    public static String detectMediaType(Path path) {
        return detectMediaType(path.toFile());
    }

    public static String detectMediaType(File file) {
        try {
            return new Tika().detect(file);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }

    }

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

    public static String parseToString(InputStream stream) {
        try {
            return new Tika().parseToString(stream);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        } catch (TikaException e) {
            throw new RuntimeException("文档解析出错", e);
        }
    }
}

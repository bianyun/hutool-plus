package plus.hutool.core.lang;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;
import plus.hutool.core.lang.exception.UnsupportedClientRequestException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;

/**
 * 异常工具类
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class ExceptionUtils {
    private ExceptionUtils() {}

    public static IllegalArgumentException illegalArgumentException(String msgTemplate, Object... args) {
        return new IllegalArgumentException(StrUtil.format(msgTemplate, args));
    }

    public static DateTimeParseException dateTimeParseException(String msgTemplate, Object... args) {
        return new DateTimeParseException(StrUtil.format(msgTemplate, args), StrUtil.EMPTY, 0);
    }

    public static IORuntimeException ioRuntimeException(IOException e, String msgTemplate, Object... args) {
        return new IORuntimeException(StrUtil.format(msgTemplate, args), e);
    }

    public static RuntimeException runtimeException(Throwable cause, String msgTemplate, Object... args) {
        return new RuntimeException(StrUtil.format(msgTemplate, args), cause);
    }

    public static IllegalStateException illegalStateException(String msgTemplate, Object... args) {
        return new IllegalStateException(StrUtil.format(msgTemplate, args));
    }

    public static FileNotFoundException fileNotFoundException(String msgTemplate, Object... args) {
        return new FileNotFoundException(StrUtil.format(msgTemplate, args));
    }

    public static UnsupportedClientRequestException unsupportedClientRequestException(String msgTemplate,
                                                                                      Object... args) {
        return new UnsupportedClientRequestException(StrUtil.format(msgTemplate, args));
    }

    @SuppressWarnings("UnusedReturnValue")
    public static <T> T unreachableButCompilerNeedsThis() {
        throw new AssertionError("this code should never be reached");
    }
}

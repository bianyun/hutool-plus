package plus.hutool.core.lang;

import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import plus.hutool.core.lang.exception.UnsupportedClientRequestException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.format.DateTimeParseException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExceptionUtilsTest {

    @Test
    void testIllegalArgumentException() {
        assertThatThrownBy(() -> { throw ExceptionUtils.illegalArgumentException("errorMsg: {}", "params"); })
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(StrUtil.format("errorMsg: {}", "params"));
    }

    @Test
    void testDateTimeParseException() {
        assertThatThrownBy(() -> { throw ExceptionUtils.dateTimeParseException("errorMsg: {}", "params"); })
                .isInstanceOf(DateTimeParseException.class)
                .hasMessage(StrUtil.format("errorMsg: {}", "params"));
    }

    @Test
    void testIoRuntimeException() {
        final String rootCauseMsg = "rootCauseMsg";
        final String ioExceptionMsg = "ioExceptionMsg";
        final Throwable rootCause = new Exception(rootCauseMsg);
        final IOException ioException = new IOException(ioExceptionMsg, rootCause);

        assertThatThrownBy(() -> { throw ExceptionUtils.ioRuntimeException(ioException, "errorMsg: {}", "params"); })
                .isInstanceOf(IORuntimeException.class)
                .hasMessage(StrUtil.format("errorMsg: {}", "params"))
                .hasCauseExactlyInstanceOf(IOException.class)
                .hasRootCauseExactlyInstanceOf(Exception.class)
                .hasRootCauseMessage(rootCauseMsg);
    }

    @Test
    void testRuntimeException() {
        final String rootCauseMsg = "rootCause";
        final Throwable rootCause = new Exception(rootCauseMsg);

        assertThatThrownBy(() -> { throw ExceptionUtils.runtimeException(rootCause, "errorMsg: {}", "params"); })
                .isInstanceOf(RuntimeException.class).hasMessage(StrUtil.format("errorMsg: {}", "params"))
                .hasCauseExactlyInstanceOf(Exception.class)
                .hasRootCauseMessage(rootCauseMsg);
    }

    @Test
    void testFileNotFoundException() {
        assertThatThrownBy(() -> { throw ExceptionUtils.fileNotFoundException("errorMsg: {}", "params"); })
                .isInstanceOf(FileNotFoundException.class)
                .hasMessage(StrUtil.format("errorMsg: {}", "params"));
    }

    @Test
    void testIllegalStateException() {
        assertThatThrownBy(() -> { throw ExceptionUtils.illegalStateException("errorMsg: {}", "params"); })
                .isInstanceOf(IllegalStateException.class)
                .hasMessage(StrUtil.format("errorMsg: {}", "params"));
    }

    @Test
    void testUnsupportedClientRequestException() {
        assertThatThrownBy(() -> { throw ExceptionUtils.unsupportedClientRequestException("errorMsg: {}", "params"); })
                .isInstanceOf(UnsupportedClientRequestException.class)
                .hasMessage(StrUtil.format("errorMsg: {}", "params"));
    }

    @Test
    void testUnreachableButCompilerNeedsThis() {
        assertThatThrownBy(ExceptionUtils::unreachableButCompilerNeedsThis).isInstanceOf(AssertionError.class);
    }

    @Test
    void testNewException() {
        assertThat(ExceptionUtils.newException(RuntimeException.class, new Exception("cause exception"), "Hello {}", "world"))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("Hello world")
                .hasCauseExactlyInstanceOf(Exception.class)
                .hasRootCauseMessage("cause exception");
        assertThat(ExceptionUtils.newException(RuntimeException.class, "Hello {}", "world"))
                .isExactlyInstanceOf(RuntimeException.class)
                .hasMessage("Hello world")
                .hasNoCause();
    }

}

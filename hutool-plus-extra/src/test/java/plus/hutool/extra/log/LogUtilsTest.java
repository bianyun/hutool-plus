package plus.hutool.extra.log;

import cn.hutool.core.util.StrUtil;
import nl.altindag.log.LogCaptor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plus.hutool.core.lang.ExceptionUtils;
import plus.hutool.core.lang.function.TriFunction;

import static org.assertj.core.api.Assertions.assertThat;

class LogUtilsTest {

    @Test
    void testDefaultProgressDescFormatFunc() {
        final TriFunction<String, String, Long, String> formatFunc = LogUtils.defaultProgressDescFormatFunc("进度");

        assertThat(formatFunc).isInstanceOf(TriFunction.class);
        assertThat(formatFunc.apply("20.00%", " 20", 100L)).isEqualTo("进度: 20.00% |  20/100");
    }

    @Test
    void testResolveProgressDesc1() {
        assertThat(LogUtils.resolveProgressDesc("进度",   5L, 100L)).isEqualTo("进度:  5.00% |   5/100");
        assertThat(LogUtils.resolveProgressDesc("进度",  20L, 100L)).isEqualTo("进度: 20.00% |  20/100");
        assertThat(LogUtils.resolveProgressDesc("进度",  99L, 100L)).isEqualTo("进度: 99.00% |  99/100");
        assertThat(LogUtils.resolveProgressDesc("进度", 100L, 100L)).isEqualTo("进度: 100.0% | 100/100");
    }

    @Test
    void testResolveProgressDesc2() {
        final TriFunction<String, String, Long, String> formatFunc = (paddedPercentage, paddedHandledNumStr, totalNum) ->
                StrUtil.format("【{}: {} | {}/{}】", "处理进度", paddedPercentage, paddedHandledNumStr, totalNum);

        assertThat(LogUtils.resolveProgressDesc(formatFunc,   5L, 100L)).isEqualTo("【处理进度:  5.00% |   5/100】");
        assertThat(LogUtils.resolveProgressDesc(formatFunc,  20L, 100L)).isEqualTo("【处理进度: 20.00% |  20/100】");
        assertThat(LogUtils.resolveProgressDesc(formatFunc,  99L, 100L)).isEqualTo("【处理进度: 99.00% |  99/100】");
        assertThat(LogUtils.resolveProgressDesc(formatFunc, 100L, 100L)).isEqualTo("【处理进度: 100.0% | 100/100】");
    }

    @Test
    void testLog() {
        try (LogCaptor logCaptor = LogCaptor.forClass(FooService.class)) {
            logCaptor.setLogLevelToTrace();
            FooService fooService = new FooService();
            fooService.sayHello();

            assertThat(logCaptor.getLogs()).hasSize(5);
            assertThat(logCaptor.getTraceLogs()).hasSize(1).containsExactly("=== [trace log] -【Hi, this is a trace log with logPrefix '[trace log]'】");
            assertThat(logCaptor.getDebugLogs()).hasSize(1).containsExactly("=== [debug log] - Hi, this is a debug log with logPrefix '[debug log]'");
            assertThat(logCaptor.getInfoLogs()).hasSize(1).containsExactly("=== [info log] - Hi, this is a info log with logPrefix '[info log]'");
            assertThat(logCaptor.getWarnLogs()).hasSize(1).containsExactly("===【warn log】- Hi, this is a warn log with logPrefix '【warn log】'");
            assertThat(logCaptor.getErrorLogs()).hasSize(1).containsExactly("===【error log】- Hi, this is a error log with logPrefix '【error log】'");

            logCaptor.clearLogs();

            logCaptor.setLogLevelToInfo();
            fooService = new FooService();
            fooService.logException();

            assertThat(logCaptor.getLogs()).hasSize(4);
            assertThat(logCaptor.getTraceLogs()).isEmpty();
            assertThat(logCaptor.getDebugLogs()).isEmpty();
            assertThat(logCaptor.getInfoLogs()).hasSize(1).containsExactly("=== [info log] - Hi, this is a info log with logPrefix '[info log]'");
            assertThat(logCaptor.getWarnLogs()).hasSize(1).containsExactly("===【warn log】- Hi, this is a warn log with logPrefix '【warn log】'");
            assertThat(logCaptor.getErrorLogs()).hasSize(2)
                    .containsExactly("===【error log】- Hi, this is a error log with logPrefix '【error log】'",
                            "===【error log】- Hi, this is a error log with logPrefix '【error log】', and with stacktrace");

            logCaptor.clearLogs();


            logCaptor.disableLogs();
            fooService = new FooService();
            fooService.logException();

            assertThat(logCaptor.getLogs()).hasSize(0);
            assertThat(logCaptor.getTraceLogs()).isEmpty();
            assertThat(logCaptor.getDebugLogs()).isEmpty();
            assertThat(logCaptor.getInfoLogs()).isEmpty();
            assertThat(logCaptor.getWarnLogs()).isEmpty();
            assertThat(logCaptor.getErrorLogs()).isEmpty();

            logCaptor.clearLogs();
        }
    }

    private static class FooService {
        private static final Logger log = LoggerFactory.getLogger(FooService.class);

        static final String LOG_PREFIX_TRACE = "[trace log]";
        static final String LOG_PREFIX_DEBUG = "[debug log]";
        static final String LOG_PREFIX_INFO = "[info log]";
        static final String LOG_PREFIX_WARN = "【warn log】";
        static final String LOG_PREFIX_ERROR = "【error log】";

        public void sayHello() {
            LogUtils.logTrace(log, LOG_PREFIX_TRACE, "【Hi, this is a trace log with logPrefix '{}'】", LOG_PREFIX_TRACE);
            LogUtils.logDebug(log, LOG_PREFIX_DEBUG, "Hi, this is a debug log with logPrefix '{}'", LOG_PREFIX_DEBUG);
            LogUtils.logInfo(log, LOG_PREFIX_INFO, "Hi, this is a info log with logPrefix '{}'", LOG_PREFIX_INFO);
            LogUtils.logWarn(log, LOG_PREFIX_WARN, "Hi, this is a warn log with logPrefix '{}'", LOG_PREFIX_WARN);
            LogUtils.logError(log, LOG_PREFIX_ERROR, "Hi, this is a error log with logPrefix '{}'", LOG_PREFIX_ERROR);
        }

        public void logException() {
            LogUtils.logTrace(log, LOG_PREFIX_TRACE, "【Hi, this is a trace log with logPrefix '{}'】", LOG_PREFIX_TRACE);
            LogUtils.logDebug(log, LOG_PREFIX_DEBUG, "Hi, this is a debug log with logPrefix '{}'", LOG_PREFIX_DEBUG);
            LogUtils.logInfo(log, LOG_PREFIX_INFO, "Hi, this is a info log with logPrefix '{}'", LOG_PREFIX_INFO);
            LogUtils.logWarn(log, LOG_PREFIX_WARN, "Hi, this is a warn log with logPrefix '{}'", LOG_PREFIX_WARN);
            LogUtils.logError(log, LOG_PREFIX_ERROR, "Hi, this is a error log with logPrefix '{}'", LOG_PREFIX_ERROR);

            try {
                throw ExceptionUtils.illegalArgumentException("illegal arguments");
            } catch (Exception e) {
                LogUtils.logError(log, LOG_PREFIX_ERROR, e, "Hi, this is a error log with logPrefix '{}', and with stacktrace", LOG_PREFIX_ERROR);
            }
        }
    }
}

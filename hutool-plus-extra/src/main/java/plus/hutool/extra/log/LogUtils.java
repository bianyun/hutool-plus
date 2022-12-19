package plus.hutool.extra.log;

import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import plus.hutool.core.lang.function.TriFunction;
import plus.hutool.core.math.NumberUtils;
import plus.hutool.core.text.string.StrUtils;

/**
 * 日志工具
 *
 * @author bianyun
 * @date 2022/12/06
 */
@SuppressWarnings({"JavadocDeclaration", "unused", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class LogUtils {
    private LogUtils() {}

    private static final String LOG_MSG_TEMPLATE = "==={}-{}";

    public static void logError(Logger log, String logPrefix, String msgTemplate, Object... params) {
        if (log.isErrorEnabled()) {
            log.error(LOG_MSG_TEMPLATE, resolveLogPrefix(logPrefix), resolveMsg(msgTemplate, params));
        }
    }

    public static void logError(Logger log, String logPrefix, Throwable t, String msgTemplate, Object... params) {
        if (log.isErrorEnabled()) {
            log.error(StrUtil.format(LOG_MSG_TEMPLATE, resolveLogPrefix(logPrefix), resolveMsg(msgTemplate, params)), t);
        }
    }

    public static void logWarn(Logger log, String logPrefix, String msgTemplate, Object... params) {
        if (log.isWarnEnabled()) {
            log.warn(LOG_MSG_TEMPLATE, resolveLogPrefix(logPrefix), resolveMsg(msgTemplate, params));
        }
    }

    public static void logInfo(Logger log, String logPrefix, String msgTemplate, Object... params) {
        if (log.isInfoEnabled()) {
            log.info(LOG_MSG_TEMPLATE, resolveLogPrefix(logPrefix), resolveMsg(msgTemplate, params));
        }
    }

    public static void logDebug(Logger log, String logPrefix, String msgTemplate, Object... params) {
        if (log.isDebugEnabled()) {
            log.debug(LOG_MSG_TEMPLATE, resolveLogPrefix(logPrefix), resolveMsg(msgTemplate, params));
        }
    }

    public static void logTrace(Logger log, String logPrefix, String msgTemplate, Object... params) {
        if (log.isTraceEnabled()) {
            log.trace(LOG_MSG_TEMPLATE, resolveLogPrefix(logPrefix), resolveMsg(msgTemplate, params));
        }
    }

    /**
     * 默认的进度描述的格式化函数
     *
     * @param descPrefix 进度描述的前缀
     * @return 进度描述的格式化函数
     */
    public static TriFunction<String, String, Long, String> defaultProgressDescFormatFunc(String descPrefix) {
        return (paddedPercentage, paddedHandledNumStr, totalNum) ->
                StrUtil.format("{}: {} | {}/{}", descPrefix, paddedPercentage, paddedHandledNumStr, totalNum);
    }

    /**
     * 根据 已处理数量 和 总数量 解析出进度描述
     *
     * @param descPrefix 进度描述的前缀
     * @param handledNum 已处理数量
     * @param totalNum   总数量
     * @return 进度描述
     */
    public static String resolveProgressDesc(String descPrefix, long handledNum, long totalNum) {
        TriFunction<String, String, Long, String> formatFunc = defaultProgressDescFormatFunc(descPrefix);
        return resolveProgressDesc(formatFunc, handledNum, totalNum);
    }

    /**
     * 根据 已处理数量 和 总数量 解析出进度描述
     *
     * @param formatFunc 进度描述的格式化函数
     * @param handledNum 已处理数量
     * @param totalNum   总数量
     * @return 进度描述
     */
    public static String resolveProgressDesc(TriFunction<String, String, Long, String> formatFunc,
                                             long handledNum, long totalNum) {
        int widthOfTotalNum = NumberUtils.resolveNumberWidth(totalNum);
        String paddedHandledNumStr = StrUtil.padPre(Long.toString(handledNum), widthOfTotalNum, StrUtils.SPACE);
        int scale = handledNum < totalNum ? 2 : 1;
        String percentage = NumberUtils.divToPercent(handledNum, totalNum, scale, true);
        String paddedPercentage = StrUtil.padPre(percentage, 6, StrUtils.SPACE);
        return formatFunc.apply(paddedPercentage, paddedHandledNumStr, totalNum);
    }

    private static String resolveLogPrefix(String logPrefix) {
        if (!logPrefix.trim().startsWith(StrUtils.CHN_OPEN_BRACKET)) {
            logPrefix = StrUtils.SPACE + logPrefix;
        }

        if (!logPrefix.trim().endsWith(StrUtils.CHN_CLOSE_BRACKET)) {
            logPrefix = logPrefix + StrUtils.SPACE;
        }
        return logPrefix;
    }

    private static String resolveMsg(String msgTemplate, Object... params) {
        String msg = StrUtil.format(msgTemplate, params);
        if (!msg.trim().startsWith(StrUtils.CHN_OPEN_BRACKET)) {
            msg = StrUtils.SPACE + msg;
        }
        return msg;
    }
}

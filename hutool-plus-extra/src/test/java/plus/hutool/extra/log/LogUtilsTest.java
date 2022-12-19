package plus.hutool.extra.log;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
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
}

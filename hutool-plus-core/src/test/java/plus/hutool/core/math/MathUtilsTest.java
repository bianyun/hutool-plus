package plus.hutool.core.math;

import cn.hutool.core.collection.CollUtil;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class MathUtilsTest {

    @Test
    void testCalcMedian1() {
        final List<BigDecimal> nums = CollUtil.newArrayList(
                new BigDecimal("-10000"),
                new BigDecimal("2.0"),
                new BigDecimal("3.0"),
                new BigDecimal("5.0"),
                new BigDecimal("6.0"),
                new BigDecimal("8.0"),
                new BigDecimal("99.0")
        );
        assertThat(MathUtils.calcMedian(nums, 2)).isEqualTo(new BigDecimal("5.00"));

        nums.add(new BigDecimal("100"));
        assertThat(MathUtils.calcMedian(nums, 2)).isEqualTo(new BigDecimal("5.50"));
    }

    @Test
    void testCalcMedian2() {
        BigDecimal[] nums = new BigDecimal[] {
                new BigDecimal("-10000"),
                new BigDecimal("2.0"),
                new BigDecimal("3.0"),
                new BigDecimal("5.0"),
                new BigDecimal("6.0"),
                new BigDecimal("8.0"),
                new BigDecimal("99.0"),
        };
        assertThat(MathUtils.calcMedian(nums, 2)).isEqualTo(new BigDecimal("5.00"));

        nums = new BigDecimal[]{
                new BigDecimal("-10000"),
                new BigDecimal("2.0"),
                new BigDecimal("3.0"),
                new BigDecimal("5.0"),
                new BigDecimal("6.0"),
                new BigDecimal("8.0"),
                new BigDecimal("99.0"),
                new BigDecimal("100.0"),
        };
        assertThat(MathUtils.calcMedian(nums, 2)).isEqualTo(new BigDecimal("5.50"));
    }
}

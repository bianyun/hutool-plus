package plus.hutool.core.math;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class NumberUtilsTest {

    @Test
    void testIsGreaterThanZero() {
        assertThat(NumberUtils.isGreaterThanZero(-1)).isFalse();
        assertThat(NumberUtils.isGreaterThanZero(Integer.MIN_VALUE)).isFalse();
        assertThat(NumberUtils.isGreaterThanZero(Long.MIN_VALUE)).isFalse();

        assertThat(NumberUtils.isGreaterThanZero(0)).isFalse();
        assertThat(NumberUtils.isGreaterThanZero(0.0)).isFalse();
        assertThat(NumberUtils.isGreaterThanZero(BigInteger.ZERO)).isFalse();
        assertThat(NumberUtils.isGreaterThanZero(BigDecimal.ZERO)).isFalse();
        assertThat(NumberUtils.isGreaterThanZero(new BigDecimal("0.00"))).isFalse();

        assertThat(NumberUtils.isGreaterThanZero(1)).isTrue();
        assertThat(NumberUtils.isGreaterThanZero(1.0)).isTrue();
        assertThat(NumberUtils.isGreaterThanZero(BigInteger.ONE)).isTrue();
        assertThat(NumberUtils.isGreaterThanZero(BigDecimal.ONE)).isTrue();
        assertThat(NumberUtils.isGreaterThanZero(Integer.MAX_VALUE)).isTrue();
        assertThat(NumberUtils.isGreaterThanZero(Long.MAX_VALUE)).isTrue();
    }

    @Test
    void testIsGreaterThanOrEqualToZero() {
        assertThat(NumberUtils.isGreaterThanOrEqualToZero(-1)).isFalse();
        assertThat(NumberUtils.isGreaterThanOrEqualToZero(Integer.MIN_VALUE)).isFalse();
        assertThat(NumberUtils.isGreaterThanOrEqualToZero(Long.MIN_VALUE)).isFalse();
        assertThat(NumberUtils.isGreaterThanOrEqualToZero(new BigDecimal("-0.01"))).isFalse();

        assertThat(NumberUtils.isGreaterThanOrEqualToZero(0)).isTrue();
        assertThat(NumberUtils.isGreaterThanOrEqualToZero(0.0)).isTrue();
        assertThat(NumberUtils.isGreaterThanOrEqualToZero(BigInteger.ZERO)).isTrue();
        assertThat(NumberUtils.isGreaterThanOrEqualToZero(BigDecimal.ZERO)).isTrue();
        assertThat(NumberUtils.isGreaterThanOrEqualToZero(new BigDecimal("0.00"))).isTrue();

        assertThat(NumberUtils.isGreaterThanOrEqualToZero(1)).isTrue();
        assertThat(NumberUtils.isGreaterThanOrEqualToZero(1.0)).isTrue();
        assertThat(NumberUtils.isGreaterThanOrEqualToZero(BigInteger.ONE)).isTrue();
        assertThat(NumberUtils.isGreaterThanOrEqualToZero(BigDecimal.ONE)).isTrue();
        assertThat(NumberUtils.isGreaterThanOrEqualToZero(Integer.MAX_VALUE)).isTrue();
        assertThat(NumberUtils.isGreaterThanOrEqualToZero(Long.MAX_VALUE)).isTrue();
    }

    @Test
    void testIsLessThanZero() {
        assertThat(NumberUtils.isLessThanZero(-1)).isTrue();
        assertThat(NumberUtils.isLessThanZero(Integer.MIN_VALUE)).isTrue();
        assertThat(NumberUtils.isLessThanZero(Long.MIN_VALUE)).isTrue();
        assertThat(NumberUtils.isLessThanZero(new BigDecimal("-0.01"))).isTrue();

        assertThat(NumberUtils.isLessThanZero(0)).isFalse();
        assertThat(NumberUtils.isLessThanZero(0.0)).isFalse();
        assertThat(NumberUtils.isLessThanZero(BigInteger.ZERO)).isFalse();
        assertThat(NumberUtils.isLessThanZero(BigDecimal.ZERO)).isFalse();
        assertThat(NumberUtils.isLessThanZero(new BigDecimal("0.00"))).isFalse();

        assertThat(NumberUtils.isLessThanZero(1)).isFalse();
        assertThat(NumberUtils.isLessThanZero(1.0)).isFalse();
        assertThat(NumberUtils.isLessThanZero(BigInteger.ONE)).isFalse();
        assertThat(NumberUtils.isLessThanZero(BigDecimal.ONE)).isFalse();
        assertThat(NumberUtils.isLessThanZero(Integer.MAX_VALUE)).isFalse();
        assertThat(NumberUtils.isLessThanZero(Long.MAX_VALUE)).isFalse();
    }

    @Test
    void testIsLessThanOrEqualToZero() {
        assertThat(NumberUtils.isLessThanOrEqualToZero(-1)).isTrue();
        assertThat(NumberUtils.isLessThanOrEqualToZero(Integer.MIN_VALUE)).isTrue();
        assertThat(NumberUtils.isLessThanOrEqualToZero(Long.MIN_VALUE)).isTrue();
        assertThat(NumberUtils.isLessThanOrEqualToZero(new BigDecimal("-0.01"))).isTrue();

        assertThat(NumberUtils.isLessThanOrEqualToZero(0)).isTrue();
        assertThat(NumberUtils.isLessThanOrEqualToZero(0.0)).isTrue();
        assertThat(NumberUtils.isLessThanOrEqualToZero(BigInteger.ZERO)).isTrue();
        assertThat(NumberUtils.isLessThanOrEqualToZero(BigDecimal.ZERO)).isTrue();
        assertThat(NumberUtils.isLessThanOrEqualToZero(new BigDecimal("0.00"))).isTrue();

        assertThat(NumberUtils.isLessThanOrEqualToZero(1)).isFalse();
        assertThat(NumberUtils.isLessThanOrEqualToZero(1.0)).isFalse();
        assertThat(NumberUtils.isLessThanOrEqualToZero(BigInteger.ONE)).isFalse();
        assertThat(NumberUtils.isLessThanOrEqualToZero(BigDecimal.ONE)).isFalse();
        assertThat(NumberUtils.isLessThanOrEqualToZero(Integer.MAX_VALUE)).isFalse();
        assertThat(NumberUtils.isLessThanOrEqualToZero(Long.MAX_VALUE)).isFalse();
    }

    @Test
    void testIsEqualTo() {
        assertThat(NumberUtils.isEqualTo(new BigDecimal("0.00"), new BigDecimal("0.00"))).isTrue();
        assertThat(NumberUtils.isEqualTo(0, BigInteger.ZERO)).isTrue();
        assertThat(NumberUtils.isEqualTo(0, BigDecimal.ZERO)).isTrue();
        assertThat(NumberUtils.isEqualTo(0, new BigDecimal("0.00"))).isTrue();
        assertThat(NumberUtils.isEqualTo(0, BigInteger.valueOf(0))).isTrue();

        assertThat(NumberUtils.isEqualTo(1, 1.0)).isTrue();
        assertThat(NumberUtils.isEqualTo(1, 1.0f)).isTrue();
        assertThat(NumberUtils.isEqualTo(1, BigInteger.ONE)).isTrue();
        assertThat(NumberUtils.isEqualTo(1, BigDecimal.ONE)).isTrue();

        assertThat(NumberUtils.isEqualTo(Integer.MAX_VALUE, Long.MAX_VALUE)).isFalse();
    }

    @Test
    void testIsLessThan() {
        assertThat(NumberUtils.isLessThan(new BigDecimal("0.00"), new BigDecimal("0.00"))).isFalse();
        assertThat(NumberUtils.isLessThan(0, BigInteger.ZERO)).isFalse();
        assertThat(NumberUtils.isLessThan(0, BigDecimal.ZERO)).isFalse();
        assertThat(NumberUtils.isLessThan(0, new BigDecimal("0.00"))).isFalse();
        assertThat(NumberUtils.isLessThan(0, BigInteger.valueOf(0))).isFalse();

        assertThat(NumberUtils.isLessThan(new BigDecimal("0.00"), new BigDecimal("1.00"))).isTrue();
        assertThat(NumberUtils.isLessThan(0, BigInteger.ONE)).isTrue();
        assertThat(NumberUtils.isLessThan(0, BigDecimal.ONE)).isTrue();
        assertThat(NumberUtils.isLessThan(0, new BigDecimal("1.00"))).isTrue();
        assertThat(NumberUtils.isLessThan(0, BigInteger.valueOf(1))).isTrue();
        assertThat(NumberUtils.isLessThan(Integer.MAX_VALUE, Long.MAX_VALUE)).isTrue();

        assertThat(NumberUtils.isLessThan(new BigDecimal("0.00"), new BigDecimal("-1.00"))).isFalse();
        assertThat(NumberUtils.isLessThan(0, BigInteger.valueOf(-1))).isFalse();
        assertThat(NumberUtils.isLessThan(0, BigDecimal.valueOf(-1.0))).isFalse();
        assertThat(NumberUtils.isLessThan(0, new BigDecimal("-1.00"))).isFalse();
    }

    @Test
    void testIsLessThanOrEqualTo() {
        assertThat(NumberUtils.isLessThanOrEqualTo(new BigDecimal("0.00"), new BigDecimal("0.00"))).isTrue();
        assertThat(NumberUtils.isLessThanOrEqualTo(0, BigInteger.ZERO)).isTrue();
        assertThat(NumberUtils.isLessThanOrEqualTo(0, BigDecimal.ZERO)).isTrue();
        assertThat(NumberUtils.isLessThanOrEqualTo(0, new BigDecimal("0.00"))).isTrue();
        assertThat(NumberUtils.isLessThanOrEqualTo(0, BigInteger.valueOf(0))).isTrue();

        assertThat(NumberUtils.isLessThanOrEqualTo(new BigDecimal("0.00"), new BigDecimal("1.00"))).isTrue();
        assertThat(NumberUtils.isLessThanOrEqualTo(0, BigInteger.ONE)).isTrue();
        assertThat(NumberUtils.isLessThanOrEqualTo(0, BigDecimal.ONE)).isTrue();
        assertThat(NumberUtils.isLessThanOrEqualTo(0, new BigDecimal("1.00"))).isTrue();
        assertThat(NumberUtils.isLessThanOrEqualTo(0, BigInteger.valueOf(1))).isTrue();
        assertThat(NumberUtils.isLessThanOrEqualTo(Integer.MAX_VALUE, Long.MAX_VALUE)).isTrue();

        assertThat(NumberUtils.isLessThanOrEqualTo(new BigDecimal("0.00"), new BigDecimal("-1.00"))).isFalse();
        assertThat(NumberUtils.isLessThanOrEqualTo(0, BigInteger.valueOf(-1))).isFalse();
        assertThat(NumberUtils.isLessThanOrEqualTo(0, BigDecimal.valueOf(-1.0))).isFalse();
        assertThat(NumberUtils.isLessThanOrEqualTo(0, new BigDecimal("-1.00"))).isFalse();
    }

    @Test
    void testIsGreaterThan() {
        assertThat(NumberUtils.isGreaterThan(new BigDecimal("0.00"), new BigDecimal("0.00"))).isFalse();
        assertThat(NumberUtils.isGreaterThan(0, BigInteger.ZERO)).isFalse();
        assertThat(NumberUtils.isGreaterThan(0, BigDecimal.ZERO)).isFalse();
        assertThat(NumberUtils.isGreaterThan(0, new BigDecimal("0.00"))).isFalse();
        assertThat(NumberUtils.isGreaterThan(0, BigInteger.valueOf(0))).isFalse();

        assertThat(NumberUtils.isGreaterThan(new BigDecimal("0.00"), new BigDecimal("1.00"))).isFalse();
        assertThat(NumberUtils.isGreaterThan(0, BigInteger.ONE)).isFalse();
        assertThat(NumberUtils.isGreaterThan(0, BigDecimal.ONE)).isFalse();
        assertThat(NumberUtils.isGreaterThan(0, new BigDecimal("1.00"))).isFalse();
        assertThat(NumberUtils.isGreaterThan(0, BigInteger.valueOf(1))).isFalse();
        assertThat(NumberUtils.isGreaterThan(Integer.MAX_VALUE, Long.MAX_VALUE)).isFalse();

        assertThat(NumberUtils.isGreaterThan(new BigDecimal("0.00"), new BigDecimal("-1.00"))).isTrue();
        assertThat(NumberUtils.isGreaterThan(0, BigInteger.valueOf(-1))).isTrue();
        assertThat(NumberUtils.isGreaterThan(0, BigDecimal.valueOf(-1.0))).isTrue();
        assertThat(NumberUtils.isGreaterThan(0, new BigDecimal("-1.00"))).isTrue();
    }

    @Test
    void testIsGreaterThanOrEqualTo() {
        assertThat(NumberUtils.isGreaterThanOrEqualTo(new BigDecimal("0.00"), new BigDecimal("0.00"))).isTrue();
        assertThat(NumberUtils.isGreaterThanOrEqualTo(0, BigInteger.ZERO)).isTrue();
        assertThat(NumberUtils.isGreaterThanOrEqualTo(0, BigDecimal.ZERO)).isTrue();
        assertThat(NumberUtils.isGreaterThanOrEqualTo(0, new BigDecimal("0.00"))).isTrue();
        assertThat(NumberUtils.isGreaterThanOrEqualTo(0, BigInteger.valueOf(0))).isTrue();

        assertThat(NumberUtils.isGreaterThanOrEqualTo(new BigDecimal("0.00"), new BigDecimal("1.00"))).isFalse();
        assertThat(NumberUtils.isGreaterThanOrEqualTo(0, BigInteger.ONE)).isFalse();
        assertThat(NumberUtils.isGreaterThanOrEqualTo(0, BigDecimal.ONE)).isFalse();
        assertThat(NumberUtils.isGreaterThanOrEqualTo(0, new BigDecimal("1.00"))).isFalse();
        assertThat(NumberUtils.isGreaterThanOrEqualTo(0, BigInteger.valueOf(1))).isFalse();
        assertThat(NumberUtils.isGreaterThanOrEqualTo(Integer.MAX_VALUE, Long.MAX_VALUE)).isFalse();

        assertThat(NumberUtils.isGreaterThanOrEqualTo(new BigDecimal("0.00"), new BigDecimal("-1.00"))).isTrue();
        assertThat(NumberUtils.isGreaterThanOrEqualTo(0, BigInteger.valueOf(-1))).isTrue();
        assertThat(NumberUtils.isGreaterThanOrEqualTo(0, BigDecimal.valueOf(-1.0))).isTrue();
        assertThat(NumberUtils.isGreaterThanOrEqualTo(0, new BigDecimal("-1.00"))).isTrue();
    }

    @Test
    void testHasNonZeroFractionalPart() {
        assertThat(NumberUtils.hasNonZeroFractionalPart(new BigDecimal("0.00"))).isFalse();
        assertThat(NumberUtils.hasNonZeroFractionalPart(new BigDecimal("1.20"))).isTrue();
        assertThat(NumberUtils.hasNonZeroFractionalPart(1.23f)).isTrue();
        assertThat(NumberUtils.hasNonZeroFractionalPart(1.2345678)).isTrue();
        assertThat(NumberUtils.hasNonZeroFractionalPart(1.0)).isFalse();
        assertThat(NumberUtils.hasNonZeroFractionalPart(BigInteger.ZERO)).isFalse();
        assertThat(NumberUtils.hasNonZeroFractionalPart(BigInteger.ONE)).isFalse();
        assertThat(NumberUtils.hasNonZeroFractionalPart(BigDecimal.ZERO)).isFalse();
        assertThat(NumberUtils.hasNonZeroFractionalPart(BigDecimal.ONE)).isFalse();
    }

    @Test
    void testRemoveInsignificantTrailingZeros() {
        assertThat(NumberUtils.removeInsignificantTrailingZeros(new BigDecimal("0.00"))).isEqualTo("0");
        assertThat(NumberUtils.removeInsignificantTrailingZeros(new BigDecimal("1.230"))).isEqualTo("1.23");
        assertThat(NumberUtils.removeInsignificantTrailingZeros(new BigDecimal("1.2305"))).isEqualTo("1.2305");
        assertThat(NumberUtils.removeInsignificantTrailingZeros(0.00)).isEqualTo("0");
        assertThat(NumberUtils.removeInsignificantTrailingZeros(1.230)).isEqualTo("1.23");
        assertThat(NumberUtils.removeInsignificantTrailingZeros(1.2305)).isEqualTo("1.2305");
    }

    @Test
    void testToBigDecimal1() {
        assertThat(NumberUtils.toBigDecimal(null)).isEqualTo(BigDecimal.ZERO);
        assertThat(NumberUtils.toBigDecimal(0)).isEqualTo(BigDecimal.ZERO);
        assertThat(NumberUtils.toBigDecimal(1)).isEqualTo(BigDecimal.ONE);
        assertThat(NumberUtils.toBigDecimal(1.23)).isEqualTo(new BigDecimal("1.23"));
        assertThat(NumberUtils.toBigDecimal(Integer.MIN_VALUE)).isEqualTo(BigDecimal.valueOf(Integer.MIN_VALUE));
        assertThat(NumberUtils.toBigDecimal(Integer.MAX_VALUE)).isEqualTo(BigDecimal.valueOf(Integer.MAX_VALUE));
        assertThat(NumberUtils.toBigDecimal(Long.MIN_VALUE)).isEqualTo(BigDecimal.valueOf(Long.MIN_VALUE));
        assertThat(NumberUtils.toBigDecimal(Long.MAX_VALUE)).isEqualTo(BigDecimal.valueOf(Long.MAX_VALUE));
    }

    @Test
    void testToBigDecimal2() {
        assertThat(NumberUtils.toBigDecimal(null, 0)).isEqualTo(BigDecimal.ZERO);
        assertThat(NumberUtils.toBigDecimal(null, 1)).isEqualTo(BigDecimal.ZERO);
        assertThat(NumberUtils.toBigDecimal(123456, 0)).isEqualTo(BigDecimal.valueOf(123456));
        assertThat(NumberUtils.toBigDecimal(123456, 1)).isEqualTo(new BigDecimal("123456.0"));
        assertThat(NumberUtils.toBigDecimal(123456, 2)).isEqualTo(new BigDecimal("123456.00"));
        assertThat(NumberUtils.toBigDecimal(1.23456, 0)).isEqualTo(BigDecimal.ONE);
        assertThat(NumberUtils.toBigDecimal(1.23456, 1)).isEqualTo(new BigDecimal("1.2"));
        assertThat(NumberUtils.toBigDecimal(1.23456, 2)).isEqualTo(new BigDecimal("1.23"));
        assertThat(NumberUtils.toBigDecimal(1.23456, 3)).isEqualTo(new BigDecimal("1.235"));
        assertThat(NumberUtils.toBigDecimal(1.23456, 4)).isEqualTo(new BigDecimal("1.2346"));
    }

    @Test
    void testToIntegralBigDecimal() {
        assertThat(NumberUtils.toIntegralBigDecimal(0)).isEqualTo(BigDecimal.ZERO);
        assertThat(NumberUtils.toIntegralBigDecimal(0.12345)).isEqualTo(BigDecimal.ZERO);
        assertThat(NumberUtils.toIntegralBigDecimal(0.12345)).isEqualTo(new BigDecimal("0"));
        assertThat(NumberUtils.toIntegralBigDecimal(0.12345)).isNotEqualTo(new BigDecimal("0.0"));
        assertThat(NumberUtils.toIntegralBigDecimal(12.3456)).isEqualTo(new BigDecimal("12"));
        assertThat(NumberUtils.toIntegralBigDecimal(12.3456)).isNotEqualTo(new BigDecimal("12.3456"));
        assertThat(NumberUtils.toIntegralBigDecimal(12.3456)).isNotEqualTo(new BigDecimal("12.0"));
    }

    @Test
    void testToBigInteger() {
        assertThat(NumberUtils.toBigInteger(0.12345)).isEqualTo(BigInteger.ZERO);
        assertThat(NumberUtils.toBigInteger(-1.12345)).isEqualTo(BigInteger.valueOf(-1));

        assertThat(NumberUtils.toBigInteger(-123)).isEqualTo(new BigInteger("-123"));
        assertThat(NumberUtils.toBigInteger(123)).isEqualTo(new BigInteger("123"));
        assertThat(NumberUtils.toBigInteger(123)).isEqualTo(BigInteger.valueOf(123));
        assertThat(NumberUtils.toBigInteger(123.456)).isEqualTo(BigInteger.valueOf(123));
    }

    @Test
    void testResolveApproximateValueStr() {
        assertThat(NumberUtils.resolveApproximateValueAsStr(123.012345, 0, 4, false)).isEqualTo("123");
        assertThat(NumberUtils.resolveApproximateValueAsStr(123.012345, 1, 4, false)).isEqualTo("123.0");
        assertThat(NumberUtils.resolveApproximateValueAsStr(123.012345, 1, 4, true)).isEqualTo("123");
        assertThat(NumberUtils.resolveApproximateValueAsStr(123.012345, 2, 4, false)).isEqualTo("123.01");
        assertThat(NumberUtils.resolveApproximateValueAsStr(123.012345, 3, 4, false)).isEqualTo("123.012");
        assertThat(NumberUtils.resolveApproximateValueAsStr(123.012345, 4, 4, false)).isEqualTo("123.0123");

        assertThat(NumberUtils.resolveApproximateValueAsStr(0.10345678, 2, 4, false)).isEqualTo("0.1035");
        assertThat(NumberUtils.resolveApproximateValueAsStr(0.00047656, 2, 4, false)).isEqualTo("0.0005");
        assertThat(NumberUtils.resolveApproximateValueAsStr(0.00057656, 2, 4, false)).isEqualTo("0.0006");
        assertThat(NumberUtils.resolveApproximateValueAsStr(0.00087656, 2, 4, false)).isEqualTo("0.0009");
        assertThat(NumberUtils.resolveApproximateValueAsStr(0.00097656, 2, 4, false)).isEqualTo("0.0010");
        assertThat(NumberUtils.resolveApproximateValueAsStr(0.00097656, 2, 4, true)).isEqualTo("0.001");
    }

    @Test
    void testResolveApproximateValue() {
        assertThat(NumberUtils.resolveApproximateValue(123, 0, 4)).isEqualTo(new BigDecimal("123"));
        assertThat(NumberUtils.resolveApproximateValue(123, 1, 4)).isEqualTo(new BigDecimal("123"));

        assertThat(NumberUtils.resolveApproximateValue(123.012345, 0, 4)).isEqualTo(new BigDecimal("123"));
        assertThat(NumberUtils.resolveApproximateValue(123.012345, 1, 4)).isEqualTo(new BigDecimal("123.0"));
        assertThat(NumberUtils.resolveApproximateValue(123.012345, 2, 4)).isEqualTo(new BigDecimal("123.01"));
        assertThat(NumberUtils.resolveApproximateValue(123.012345, 3, 4)).isEqualTo(new BigDecimal("123.012"));
        assertThat(NumberUtils.resolveApproximateValue(123.012345, 4, 4)).isEqualTo(new BigDecimal("123.0123"));

        assertThat(NumberUtils.resolveApproximateValue(0.10345678, 2, 4)).isEqualTo(new BigDecimal("0.1035"));
        assertThat(NumberUtils.resolveApproximateValue(0.00047656, 2, 4)).isEqualTo(new BigDecimal("0.0005"));
        assertThat(NumberUtils.resolveApproximateValue(0.00057656, 2, 4)).isEqualTo(new BigDecimal("0.0006"));
        assertThat(NumberUtils.resolveApproximateValue(0.00087656, 2, 4)).isEqualTo(new BigDecimal("0.0009"));
        assertThat(NumberUtils.resolveApproximateValue(0.00097656, 2, 4)).isEqualTo(new BigDecimal("0.0010"));

        assertThatThrownBy(() -> NumberUtils.resolveApproximateValue(123, -1, 4))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("小数位数必须大于等于零");
        assertThatThrownBy(() -> NumberUtils.resolveApproximateValue(123, 3, 1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("最小小数位数[minScale] 必须小于等于 最大小数位数[maxScale]");
    }

    @Test
    void testIsBetweenOpenInterval() {
        assertThat(NumberUtils.isBetweenOpenInterval(1, 0, 2)).isTrue();
        assertThat(NumberUtils.isBetweenOpenInterval(-1, -2, 2)).isTrue();
        assertThat(NumberUtils.isBetweenOpenInterval(Integer.MIN_VALUE, Long.MIN_VALUE, Long.MAX_VALUE)).isTrue();
        assertThat(NumberUtils.isBetweenOpenInterval(1.1, 0.2, 2.3)).isTrue();
        assertThat(NumberUtils.isBetweenOpenInterval(-1.1, -2.2, 2.3)).isTrue();

        assertThat(NumberUtils.isBetweenOpenInterval(1, 0, 1)).isFalse();
        assertThat(NumberUtils.isBetweenOpenInterval(1, 1, 2)).isFalse();
        assertThat(NumberUtils.isBetweenOpenInterval(Integer.MIN_VALUE, Integer.MIN_VALUE, 0)).isFalse();
        assertThat(NumberUtils.isBetweenOpenInterval(Long.MIN_VALUE, Long.MIN_VALUE, 0)).isFalse();
        assertThat(NumberUtils.isBetweenOpenInterval(0, 0, Integer.MAX_VALUE)).isFalse();
        assertThat(NumberUtils.isBetweenOpenInterval(0, 0, Long.MAX_VALUE)).isFalse();

        assertThat(NumberUtils.isBetweenOpenInterval(Integer.MAX_VALUE, 0, Integer.MAX_VALUE)).isFalse();
        assertThat(NumberUtils.isBetweenOpenInterval(Long.MAX_VALUE, 0, Long.MAX_VALUE)).isFalse();
    }

    @Test
    void testIsBetweenClosedInterval() {
        assertThat(NumberUtils.isBetweenClosedInterval(1, 0, 2)).isTrue();
        assertThat(NumberUtils.isBetweenClosedInterval(1, 2, 3)).isFalse();
        assertThat(NumberUtils.isBetweenClosedInterval(1, -1, 0)).isFalse();
        assertThat(NumberUtils.isBetweenClosedInterval(-1, -2, 2)).isTrue();
        assertThat(NumberUtils.isBetweenClosedInterval(Integer.MIN_VALUE, Long.MIN_VALUE, Long.MAX_VALUE)).isTrue();
        assertThat(NumberUtils.isBetweenClosedInterval(1.1, 0.2, 2.3)).isTrue();
        assertThat(NumberUtils.isBetweenClosedInterval(-1.1, -2.2, 2.3)).isTrue();

        assertThat(NumberUtils.isBetweenClosedInterval(1, 0, 1)).isTrue();
        assertThat(NumberUtils.isBetweenClosedInterval(1, 1, 2)).isTrue();
        assertThat(NumberUtils.isBetweenClosedInterval(Integer.MIN_VALUE, Integer.MIN_VALUE, 0)).isTrue();
        assertThat(NumberUtils.isBetweenClosedInterval(Long.MIN_VALUE, Long.MIN_VALUE, 0)).isTrue();
        assertThat(NumberUtils.isBetweenClosedInterval(0, 0, Integer.MAX_VALUE)).isTrue();
        assertThat(NumberUtils.isBetweenClosedInterval(0, 0, Long.MAX_VALUE)).isTrue();

        assertThat(NumberUtils.isBetweenClosedInterval(Integer.MAX_VALUE, 0, Integer.MAX_VALUE)).isTrue();
        assertThat(NumberUtils.isBetweenClosedInterval(Long.MAX_VALUE, 0, Long.MAX_VALUE)).isTrue();
    }

    @Test
    void testIsBetweenLeftOpenRightClosedInterval() {
        assertThat(NumberUtils.isBetweenLeftOpenRightClosedInterval(1, 0, 2)).isTrue();
        assertThat(NumberUtils.isBetweenLeftOpenRightClosedInterval(1, 0, 1)).isTrue();
        assertThat(NumberUtils.isBetweenLeftOpenRightClosedInterval(1, 1, 2)).isFalse();
        assertThat(NumberUtils.isBetweenLeftOpenRightClosedInterval(3, 1, 2)).isFalse();
        assertThat(NumberUtils.isBetweenLeftOpenRightClosedInterval(-1, -2, 2)).isTrue();
        assertThat(NumberUtils.isBetweenLeftOpenRightClosedInterval(Integer.MIN_VALUE, Long.MIN_VALUE, Long.MAX_VALUE)).isTrue();
        assertThat(NumberUtils.isBetweenLeftOpenRightClosedInterval(1.1, 0.2, 2.3)).isTrue();
        assertThat(NumberUtils.isBetweenLeftOpenRightClosedInterval(-1.1, -2.2, 2.3)).isTrue();

        assertThat(NumberUtils.isBetweenLeftOpenRightClosedInterval(1, 0, 1)).isTrue();
        assertThat(NumberUtils.isBetweenLeftOpenRightClosedInterval(1, 1, 2)).isFalse();
        assertThat(NumberUtils.isBetweenLeftOpenRightClosedInterval(Integer.MIN_VALUE, Integer.MIN_VALUE, 0)).isFalse();
        assertThat(NumberUtils.isBetweenLeftOpenRightClosedInterval(Long.MIN_VALUE, Long.MIN_VALUE, 0)).isFalse();
        assertThat(NumberUtils.isBetweenLeftOpenRightClosedInterval(0, 0, Integer.MAX_VALUE)).isFalse();
        assertThat(NumberUtils.isBetweenLeftOpenRightClosedInterval(0, 0, Long.MAX_VALUE)).isFalse();

        assertThat(NumberUtils.isBetweenLeftOpenRightClosedInterval(Integer.MAX_VALUE, 0, Integer.MAX_VALUE)).isTrue();
        assertThat(NumberUtils.isBetweenLeftOpenRightClosedInterval(Long.MAX_VALUE, 0, Long.MAX_VALUE)).isTrue();
    }

    @Test
    void testIsBetweenLeftClosedRightOpenInterval() {
        assertThat(NumberUtils.isBetweenLeftClosedRightOpenInterval(1, 0, 2)).isTrue();
        assertThat(NumberUtils.isBetweenLeftClosedRightOpenInterval(-1, -2, 2)).isTrue();
        assertThat(NumberUtils.isBetweenLeftClosedRightOpenInterval(Integer.MIN_VALUE, Long.MIN_VALUE, Long.MAX_VALUE)).isTrue();
        assertThat(NumberUtils.isBetweenLeftClosedRightOpenInterval(1.1, 0.2, 2.3)).isTrue();
        assertThat(NumberUtils.isBetweenLeftClosedRightOpenInterval(-1.1, -2.2, 2.3)).isTrue();

        assertThat(NumberUtils.isBetweenLeftClosedRightOpenInterval(1, 0, 1)).isFalse();
        assertThat(NumberUtils.isBetweenLeftClosedRightOpenInterval(1, 1, 2)).isTrue();
        assertThat(NumberUtils.isBetweenLeftClosedRightOpenInterval(Integer.MIN_VALUE, Integer.MIN_VALUE, 0)).isTrue();
        assertThat(NumberUtils.isBetweenLeftClosedRightOpenInterval(Long.MIN_VALUE, Long.MIN_VALUE, 0)).isTrue();
        assertThat(NumberUtils.isBetweenLeftClosedRightOpenInterval(0, 0, Integer.MAX_VALUE)).isTrue();
        assertThat(NumberUtils.isBetweenLeftClosedRightOpenInterval(0, 0, Long.MAX_VALUE)).isTrue();

        assertThat(NumberUtils.isBetweenLeftClosedRightOpenInterval(Integer.MAX_VALUE, 0, Integer.MAX_VALUE)).isFalse();
        assertThat(NumberUtils.isBetweenLeftClosedRightOpenInterval(Long.MAX_VALUE, 0, Long.MAX_VALUE)).isFalse();
    }

    @Test
    void testGetAbsBitLength() {
        assertThat(NumberUtils.getAbsBitLength(0)).isEqualTo(0);
        assertThat(NumberUtils.getAbsBitLength(1)).isEqualTo(1);
        assertThat(NumberUtils.getAbsBitLength(2)).isEqualTo(2);
        assertThat(NumberUtils.getAbsBitLength(3)).isEqualTo(2);
        assertThat(NumberUtils.getAbsBitLength(4)).isEqualTo(3);
        assertThat(NumberUtils.getAbsBitLength(5)).isEqualTo(3);
        assertThat(NumberUtils.getAbsBitLength(6)).isEqualTo(3);
        assertThat(NumberUtils.getAbsBitLength(7)).isEqualTo(3);
        assertThat(NumberUtils.getAbsBitLength(8)).isEqualTo(4);
        assertThat(NumberUtils.getAbsBitLength(15)).isEqualTo(4);
        assertThat(NumberUtils.getAbsBitLength(16)).isEqualTo(5);
        assertThat(NumberUtils.getAbsBitLength(31)).isEqualTo(5);
        assertThat(NumberUtils.getAbsBitLength(32)).isEqualTo(6);
        assertThat(NumberUtils.getAbsBitLength(63)).isEqualTo(6);
        assertThat(NumberUtils.getAbsBitLength(64)).isEqualTo(7);
        assertThat(NumberUtils.getAbsBitLength(127)).isEqualTo(7);
        assertThat(NumberUtils.getAbsBitLength(128)).isEqualTo(8);

        assertThat(NumberUtils.getAbsBitLength(-1)).isEqualTo(1);
        assertThat(NumberUtils.getAbsBitLength(-2)).isEqualTo(2);
        assertThat(NumberUtils.getAbsBitLength(Integer.MAX_VALUE)).isEqualTo(31);
        assertThat(NumberUtils.getAbsBitLength(Integer.MIN_VALUE)).isEqualTo(32);
    }

    @Test
    void testDivToPercent() {
        assertThat(NumberUtils.divToPercent(1, 1, 0, true)).isEqualTo(NumberUtils.ONE_HUNDRED_PERCENT);
        assertThat(NumberUtils.divToPercent(1, 1, 0, false)).isEqualTo(NumberUtils.ONE_HUNDRED_PERCENT);
        assertThat(NumberUtils.divToPercent(1, 1, 1, true)).isEqualTo(NumberUtils.ONE_HUNDRED_PERCENT_WITH_SCALE_ONE);
        assertThat(NumberUtils.divToPercent(1, 1, 2, true)).isEqualTo(NumberUtils.ONE_HUNDRED_PERCENT_WITH_SCALE_TWO);
        assertThat(NumberUtils.divToPercent(1, 1, 1, false)).isEqualTo(NumberUtils.ONE_HUNDRED_PERCENT);

        assertThat(NumberUtils.divToPercent(1, 2, 0, true)).isEqualTo("50%");
        assertThat(NumberUtils.divToPercent(1, 2, 0, false)).isEqualTo("50%");
        assertThat(NumberUtils.divToPercent(1, 2, 1, true)).isEqualTo("50.0%");
        assertThat(NumberUtils.divToPercent(1, 2, 1, false)).isEqualTo("50%");
        assertThat(NumberUtils.divToPercent(1, 2, 2, true)).isEqualTo("50.00%");
        assertThat(NumberUtils.divToPercent(1, 2, 2, false)).isEqualTo("50%");

        assertThat(NumberUtils.divToPercent(1, 3, 0, true)).isEqualTo("33%");
        assertThat(NumberUtils.divToPercent(1, 3, 0, false)).isEqualTo("33%");
        assertThat(NumberUtils.divToPercent(1, 3, 1, true)).isEqualTo("33.3%");
        assertThat(NumberUtils.divToPercent(1, 3, 1, false)).isEqualTo("33.3%");
        assertThat(NumberUtils.divToPercent(1, 3, 2, true)).isEqualTo("33.33%");
        assertThat(NumberUtils.divToPercent(1, 3, 2, false)).isEqualTo("33.33%");
        assertThat(NumberUtils.divToPercent(1, 3, 3, true)).isEqualTo("33.333%");
        assertThat(NumberUtils.divToPercent(1, 3, 3, false)).isEqualTo("33.333%");

        assertThat(NumberUtils.divToPercent(1, 4, 0, true)).isEqualTo("25%");
        assertThat(NumberUtils.divToPercent(1, 4, 0, false)).isEqualTo("25%");
        assertThat(NumberUtils.divToPercent(1, 4, 1, true)).isEqualTo("25.0%");
        assertThat(NumberUtils.divToPercent(1, 4, 1, false)).isEqualTo("25%");
        assertThat(NumberUtils.divToPercent(1, 4, 2, true)).isEqualTo("25.00%");
        assertThat(NumberUtils.divToPercent(1, 4, 2, false)).isEqualTo("25%");
        assertThat(NumberUtils.divToPercent(1, 4, 3, true)).isEqualTo("25.000%");
        assertThat(NumberUtils.divToPercent(1, 4, 3, false)).isEqualTo("25%");

        assertThat(NumberUtils.divToPercent(1, 6, 0, true)).isEqualTo("17%");
        assertThat(NumberUtils.divToPercent(1, 6, 0, false)).isEqualTo("17%");
        assertThat(NumberUtils.divToPercent(1, 6, 1, true)).isEqualTo("16.7%");
        assertThat(NumberUtils.divToPercent(1, 6, 1, false)).isEqualTo("16.7%");
        assertThat(NumberUtils.divToPercent(1, 6, 2, true)).isEqualTo("16.67%");
        assertThat(NumberUtils.divToPercent(1, 6, 2, false)).isEqualTo("16.67%");
        assertThat(NumberUtils.divToPercent(1, 6, 3, true)).isEqualTo("16.667%");
        assertThat(NumberUtils.divToPercent(1, 6, 3, false)).isEqualTo("16.667%");

        assertThat(NumberUtils.divToPercent(0, 0, 0, true)).isEqualTo("-");

        assertThat(NumberUtils.divToPercent(-1, 1, 0, true)).isEqualTo("-100%");
        assertThat(NumberUtils.divToPercent(-1, 1, 0, false)).isEqualTo("-100%");
        assertThat(NumberUtils.divToPercent(-1, 1, 1, true)).isEqualTo("-100.0%");
        assertThat(NumberUtils.divToPercent(-1, 1, 1, false)).isEqualTo("-100%");

        assertThat(NumberUtils.divToPercent(999999, 1000000, 0, true)).isEqualTo("99%");
        assertThat(NumberUtils.divToPercent(999999, 1000000, 1, true)).isEqualTo("99.9%");
        assertThat(NumberUtils.divToPercent(999999, 1000000, 2, true)).isEqualTo("99.99%");
        assertThat(NumberUtils.divToPercent(999999, 1000000, 3, true)).isEqualTo("99.999%");
        assertThat(NumberUtils.divToPercent(999999, 1000000, 4, true)).isEqualTo("99.9999%");
        assertThat(NumberUtils.divToPercent(999999, 1000000, 5, true)).isEqualTo("99.99990%");
        assertThat(NumberUtils.divToPercent(999999, 1000000, 5, false)).isEqualTo("99.9999%");

        assertThat(NumberUtils.divToPercent(0, 1000000, 0, true)).isEqualTo("0%");
        assertThat(NumberUtils.divToPercent(1, 1000000, 0, true)).isEqualTo("0%");
        assertThat(NumberUtils.divToPercent(1, 1000000, 1, true)).isEqualTo("0.1%");
        assertThat(NumberUtils.divToPercent(1, 1000000, 2, true)).isEqualTo("0.01%");
        assertThat(NumberUtils.divToPercent(1, 1000000, 3, true)).isEqualTo("0.001%");
        assertThat(NumberUtils.divToPercent(1, 1000000, 4, true)).isEqualTo("0.0001%");
        assertThat(NumberUtils.divToPercent(1, 1000000, 5, true)).isEqualTo("0.00010%");
        assertThat(NumberUtils.divToPercent(1, 1000000, 5, false)).isEqualTo("0.0001%");
    }

    @Test
    void testResolveNumberWidth() {
        assertThat(NumberUtils.resolveNumberWidth(1)).isEqualTo(1);
        assertThat(NumberUtils.resolveNumberWidth(123)).isEqualTo(3);
        assertThat(NumberUtils.resolveNumberWidth(0.12345)).isEqualTo(7);
        assertThat(NumberUtils.resolveNumberWidth(new BigDecimal("0.00"))).isEqualTo(4);
    }
}

package plus.hutool.core.math;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.lang.Nullable;
import plus.hutool.core.lang.Asserts;
import plus.hutool.core.text.regex.PatternPools;
import plus.hutool.core.text.string.StrUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * 数值工具类
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class NumberUtils {
    private NumberUtils() {}

    public static final String ONE_HUNDRED_PERCENT = "100%";
    public static final String ONE_HUNDRED_PERCENT_WITH_SCALE_ONE = "100.0%";
    public static final String ONE_HUNDRED_PERCENT_WITH_SCALE_TWO = "100.00%";
    public static final String NINETY_NINE_PERCENT = "99%";
    public static final String ZERO_PERCENT = "0%";

    /**
     * 判断数值是否大于零
     *
     * @param val 数值
     * @return {@link BigDecimal} 类型的值是否大于零
     */
    public static boolean isGreaterThanZero(Number val) {
        return toBigDecimal(val).compareTo(BigDecimal.ZERO) > 0;
    }

    /**
     * 判断数值是否大于等于零
     *
     * @param val 数值
     * @return {@link BigDecimal} 类型的值是否大于等于零
     */
    public static boolean isGreaterThanOrEqualToZero(Number val) {
        return toBigDecimal(val).compareTo(BigDecimal.ZERO) >= 0;
    }

    /**
     * 判断数值是否小于零
     *
     * @param val 数值
     * @return {@link BigDecimal} 类型的值是否小于零
     */
    public static boolean isLessThanZero(Number val) {
        return toBigDecimal(val).compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * 判断数值是否小于等于零
     *
     * @param val 数值
     * @return {@link BigDecimal} 类型的值是否小于等于零
     */
    public static boolean isLessThanOrEqualToZero(Number val) {
        return toBigDecimal(val).compareTo(BigDecimal.ZERO) <= 0;
    }

    /**
     * 比较大小，参数1 < 参数2 返回true
     *
     * @param num1 数值1
     * @param num2 数值1
     * @return 是否小于
     */
    public static boolean isEqualTo(Number num1, Number num2) {
        return toBigDecimal(num1).compareTo(toBigDecimal(num2)) == 0;
    }

    /**
     * 比较大小，参数1 < 参数2 返回true
     *
     * @param num1 数值1
     * @param num2 数值1
     * @return 是否小于
     */
    public static boolean isLessThan(Number num1, Number num2) {
        return NumberUtil.isLess(toBigDecimal(num1), toBigDecimal(num2));
    }

    /**
     * 比较大小，参数1 <= 参数2 返回true
     *
     * @param num1 数值1
     * @param num2 数值1
     * @return 是否小于等于
     */
    public static boolean isLessThanOrEqualTo(Number num1, Number num2) {
        return NumberUtil.isLessOrEqual(toBigDecimal(num1), toBigDecimal(num2));
    }

    /**
     * 比较大小，参数1 > 参数2 返回true
     *
     * @param num1 数值1
     * @param num2 数值1
     * @return 是否大于
     */
    public static boolean isGreaterThan(Number num1, Number num2) {
        return NumberUtil.isGreater(toBigDecimal(num1), toBigDecimal(num2));
    }

    /**
     * 比较大小，参数 1>= 参数2 返回true
     *
     * @param num1 数值1
     * @param num2 数值1
     * @return 是否大于等于
     */
    public static boolean isGreaterThanOrEqualTo(Number num1, Number num2) {
        return NumberUtil.isGreaterOrEqual(toBigDecimal(num1), toBigDecimal(num2));
    }

    /**
     * 判断数值是否有非零的小数部分
     *
     * @param val 数值
     * @return 数值是否有非零的小数部分
     */
    public static boolean hasNonZeroFractionalPart(Number val) {
        try {
            //noinspection ResultOfMethodCallIgnored
            toBigDecimal(val).toBigIntegerExact();
            return false;
        } catch (ArithmeticException e) {
            return true;
        }
    }

    /**
     * 将数值转换为字符串时，将小数部分末尾为0的部分给去除掉
     * <p>
     * 举例如下：
     * </p>
     * <pre>
     *  123.00   ==>  123
     *  123.400  ==>  123.4
     *  123.405  ==>  123.405
     * </pre>
     *
     * @param num 数值
     * @return 将值为零的小数部分给去除掉之后的字符串表示
     */
    public static String removeInsignificantTrailingZeros(Number num) {
        BigDecimal val = toBigDecimal(num);
        if (hasNonZeroFractionalPart(val)) {
            return ReUtil.delLast(PatternPools.TRAILING_CONTINUOUS_ZEROS, val.toPlainString());
        } else {
            return val.toBigInteger().toString();
        }
    }

    /**
     * 将数值转换为 {@link BigDecimal} 格式
     *
     * @param val 数值
     * @return 转换为 {@link BigDecimal} 格式后的数值
     */
    public static BigDecimal toBigDecimal(@Nullable Number val) {
        if (val == null) {
            return BigDecimal.ZERO;
        } else if (val instanceof BigDecimal) {
            return (BigDecimal) val;
        } else  {
            return new BigDecimal(val.toString());
        }
    }

    /**
     * 将数值转换为 {@link BigDecimal} 格式
     *
     * @param val 数值
     * @param scale 小数位数
     * @return 转换为 {@link BigDecimal} 格式后的数值
     */
    public static BigDecimal toBigDecimal(@Nullable Number val, int scale) {
        return null == val ? BigDecimal.ZERO : new BigDecimal(val.toString()).setScale(scale, RoundingMode.HALF_UP);
    }

    /**
     * 将数值转换为 {@link BigDecimal} 格式的整数值
     *
     * @param val 数值
     * @return 转换为 {@link BigDecimal} 格式后的整数值
     */
    public static BigDecimal toIntegralBigDecimal(@Nullable Number val) {
        BigDecimal result = NumberUtils.toBigDecimal(val);
        if (result.scale() != 0) {
            result = result.setScale(0, RoundingMode.HALF_UP);
        }
        return result;
    }

    /**
     * 将数值转换为 {@link BigInteger} 格式
     *
     * @param val 数值
     * @return 转换为 {@link BigInteger} 格式后的数值
     */
    public static BigInteger toBigInteger(@Nullable Number val) {
        return toBigDecimal(val).toBigInteger();
    }

    /**
     * 将数值四舍五入后转为字符串（
     *
     * <p>转换示例</p>
     *
     * <pre>
     *   resolveApproximateValueAsStr(123.0123, 0, 4, false) = "123"
     *   resolveApproximateValueAsStr(123.0123, 1, 4, false) = "123.0"
     *   resolveApproximateValueAsStr(123.0123, 1, 4, true)  = "123"
     *   resolveApproximateValueAsStr(123.0123, 2, 4, false) = "123.01"
     *   resolveApproximateValueAsStr(123.0123, 3, 4, false) = "123.012"
     *   resolveApproximateValueAsStr(123.0123, 4, 4, false) = "123.0123"
     *
     *   resolveApproximateValueAsStr(0.10345678, 2, 4, false) = "0.1035"
     *   resolveApproximateValueAsStr(0.00047656, 2, 4, false) = "0.0005"
     *   resolveApproximateValueAsStr(0.00057656, 2, 4, false) = "0.0006"
     *   resolveApproximateValueAsStr(0.00087656, 2, 4, false) = "0.0009"
     *   resolveApproximateValueAsStr(0.00097656, 2, 4, false) = "0.0010"
     *   resolveApproximateValueAsStr(0.00097656, 2, 4, true)  = "0.001"
     * </pre>
     *
     * @param num      数值
     * @param minScale 最小小数位数（值大于1时设置为此值）
     * @param maxScale 最大小数位数（值小于1时设置为此值）
     * @param cleanUnnecessaryZeros 是否清理不必要的零（比如 小数末尾的0）
     * @return 字符串表示的数值
     */
    public static String resolveApproximateValueAsStr(Number num, int minScale, int maxScale,
                                                      boolean cleanUnnecessaryZeros) {
        Number value = resolveApproximateValue(num, minScale, maxScale);
        return cleanUnnecessaryZeros ? removeInsignificantTrailingZeros(value) : value.toString();
    }

    /**
     * 将数值四舍五入后转为字符串
     *
     * <p>转换示例</p>
     *
     * <pre>
     *      resolveApproximateStringValue(123.012345, 0, 4)  =  123
     *      resolveApproximateStringValue(123.012345, 1, 4)  =  123.0
     *      resolveApproximateStringValue(123.012345, 2, 4)  =  123.01"
     *      resolveApproximateStringValue(123.012345, 3, 4)  =  123.012"
     *      resolveApproximateStringValue(123.012345, 4, 4)  =  123.0123"
     *
     *      resolveApproximateStringValue(0.10345678, 2, 4)  =  "0.10"
     *      resolveApproximateStringValue(0.00047656, 2, 4)  =  "0.0005"
     *      resolveApproximateStringValue(0.00057656, 2, 4)  =  "0.0006"
     *      resolveApproximateStringValue(0.00087656, 2, 4)  =  "0.0009"
     *      resolveApproximateStringValue(0.00097656, 2, 4)  =  "0.0010"
     * </pre>
     *
     * @param num      数值
     * @param minScale 最小小数位数（值大于1时设置为此值）
     * @param maxScale 最大小数位数（值小于1时设置为此值）
     * @return 字符串表示的数值
     */
    public static Number resolveApproximateValue(Number num, int minScale, int maxScale) {
        Asserts.isTrue(minScale >= 0, "小数位数必须大于等于零");
        Asserts.isTrue(minScale <= maxScale, "最小小数位数[minScale] 必须小于等于 最大小数位数[maxScale]");

        BigDecimal val = NumberUtils.toBigDecimal(num);
        if (val.scale() == 0) {
            return val;
        }

        BigDecimal valScaleChanged = val.setScale(maxScale, RoundingMode.HALF_UP);
        if (NumberUtils.isGreaterThan(valScaleChanged.abs(), 1) && minScale < maxScale) {
            valScaleChanged = val.setScale(minScale, RoundingMode.HALF_UP);
        }

        return valScaleChanged;
    }

    /**
     * 判断数值是否位于开区间 (minVal, maxVal) 内
     *
     * @param val 待比较的数值
     * @param minVal 开区间左端点值
     * @param maxVal 开区间右端点值
     * @return 数值是否位于开区间 (minVal, maxVal) 内
     */
    public static boolean isBetweenOpenInterval(Number val, Number minVal, Number maxVal) {
        return NumberUtils.isGreaterThan(val, minVal) && NumberUtils.isLessThan(val, maxVal);
    }

    /**
     * 判断数值是否位于闭区间 [minVal, maxVal] 内
     *
     * @param val    待比较的数值
     * @param minVal 闭区间左端点值
     * @param maxVal 闭区间右端点值
     * @return 数值是否位于闭区间 [minVal, maxVal] 内
     */
    public static boolean isBetweenClosedInterval(Number val, Number minVal, Number maxVal) {
        return NumberUtils.isGreaterThanOrEqualTo(val, minVal) && NumberUtils.isLessThanOrEqualTo(val, maxVal);
    }

    /**
     * 判断数值是否位于左开右闭区间 (minVal, maxVal] 内
     *
     * @param val    待比较的数值
     * @param minVal 左开右闭区间左端点值
     * @param maxVal 左开右闭区间右端点值
     * @return 数值是否位于左开右闭区间 (minVal, maxVal] 内
     */
    public static boolean isBetweenLeftOpenRightClosedInterval(Number val, Number minVal, Number maxVal) {
        return NumberUtils.isGreaterThan(val, minVal) && NumberUtils.isLessThanOrEqualTo(val, maxVal);
    }

    /**
     * 判断数值是否位于左闭右开区间 [minVal, maxVal) 内
     *
     * @param val    待比较的数值
     * @param minVal 左闭右开区间左端点值
     * @param maxVal 左闭右开区间右端点值
     * @return 数值是否位于左闭右开区间 [minVal, maxVal) 内
     */
    public static boolean isBetweenLeftClosedRightOpenInterval(Number val, Number minVal, Number maxVal) {
        return NumberUtils.isGreaterThanOrEqualTo(val, minVal) && NumberUtils.isLessThan(val, maxVal);
    }

    /**
     * 获取长整型数值的最小二进制补码表示中的位数，不包括符号位。
     *
     * @param value 整数值
     * @return 长整型数值的最小二进制补码表示中的位数，不包括符号位。
     */
    public static int getAbsBitLength(Number value) {
        return NumberUtils.toBigInteger(value).abs().bitLength();
    }

    /**
     * 将两个数相除得到的结果转成百分比字符串，小数采用四舍五入方式
     *
     * @param dividend               被除数
     * @param divisor                除数
     * @param scale                  保留的小数位数
     * @param useFixedFractionDigits 是否使用固定的小数位数（当小数部分为 0 时，是否显示小数部分）
     * @return 百分比字符串
     */
    public static String divToPercent(Number dividend, Number divisor, int scale, boolean useFixedFractionDigits) {
        if (isEqualTo(divisor, 0)) {
            return StrUtils.DASH;
        }

        BigDecimal divResult = NumberUtil.div(dividend, divisor);
        boolean negative = isLessThan(divResult, 0);

        String result = NumberUtils.formatPercent(divResult.abs().doubleValue(), scale, useFixedFractionDigits);
        BigDecimal absDividend = toBigDecimal(dividend).abs();
        BigDecimal absDivisor = toBigDecimal(divisor).abs();

        if (ReUtil.isMatch(PatternPools.ONE_HUNDRED_PERCENT, result) && isLessThan(absDividend, absDivisor)) {
            if (scale > 0) {
                result = StrUtil.format("99.{}%", StrUtil.repeat(StrUtils.NUM_9, scale));
            } else {
                result = NINETY_NINE_PERCENT;
            }
        } else if (ReUtil.isMatch(PatternPools.ZERO_PERCENT, result) && isGreaterThanZero(absDividend)) {
            if (scale > 0) {
                result = StrUtil.format("0.{}1%", StrUtil.repeat(StrUtils.NUM_0, scale - 1));
            } else {
                result = ZERO_PERCENT;
            }
        }

        if (negative) {
            result = StrUtils.DASH + result;
        }

        return result;
    }

    /**
     * 解析数组的宽度
     *
     * @param num 数值
     * @return 数值的宽度
     */
    public static int resolveNumberWidth(Number num) {
        return String.valueOf(num).length();
    }

    private static String formatPercent(double number, int scale, boolean useFixedFractionDigits) {
        final NumberFormat format = NumberFormat.getPercentInstance();
        format.setMaximumFractionDigits(scale);
        if (useFixedFractionDigits) {
            format.setMinimumFractionDigits(scale);
        }

        return format.format(number);
    }
}

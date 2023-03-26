package plus.hutool.core.math;

import cn.hutool.core.util.ArrayUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.List;

/**
 * 数学运算工具类
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings({"unused", "JavadocDeclaration"})
public abstract class MathUtils {
    private MathUtils() {}

    /**
     * 计算中位数
     *
     * @param valueList 待计算的数值集合
     * @return 计算出的中位数
     */
    public static BigDecimal calcMedian(List<BigDecimal> valueList, int scale) {
        return calcMedian(ArrayUtil.toArray(valueList, BigDecimal.class), scale);
    }

    /**
     * 计算中位数
     *
     * @param valueArray 待计算的数值数组（BigDecimal[]类型）
     * @return 计算出的中位数
     */
    public static BigDecimal calcMedian(BigDecimal[] valueArray, int scale) {
        Arrays.sort(valueArray);
        BigDecimal median;
        int length = valueArray.length;

        if (length % 2 == 0) {
            median = valueArray[length / 2].add(valueArray[length / 2 - 1])
                    .divide(BigDecimal.valueOf(2), scale, RoundingMode.HALF_UP);
        } else {
            median = valueArray[length / 2];
        }

        return median.setScale(scale, RoundingMode.HALF_UP);
    }
}

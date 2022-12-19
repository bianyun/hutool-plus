package plus.hutool.core.measure.util;

import cn.hutool.core.util.ArrayUtil;
import plus.hutool.core.iterable.collection.CollUtils;
import plus.hutool.core.iterable.map.MapUtils;
import plus.hutool.core.math.NumberUtils;
import plus.hutool.core.measure.quantity.FileSize;
import plus.hutool.core.text.string.StrUtils;
import tech.units.indriya.function.MultiplyConverter;
import tech.units.indriya.quantity.Quantities;
import tech.units.indriya.unit.BaseUnit;
import tech.units.indriya.unit.TransformedUnit;

import javax.measure.Quantity;
import javax.measure.Unit;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static plus.hutool.core.math.NumberUtils.resolveApproximateValue;

/**
 * 文件大小工具类
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class FileSizeUtils {
    private FileSizeUtils() {}

    public static final int FILE_SIZE_CONVERSION_FACTOR = 1024;

    public static final Unit<FileSize> BYTE = new BaseUnit<>("B", "Byte");
    public static final Unit<FileSize> KB = buildUnit("KB", "KibiByte", BYTE);
    public static final Unit<FileSize> MB = buildUnit("MB", "MebiByte", KB);
    public static final Unit<FileSize> GB = buildUnit("GB", "GibiByte", MB);
    public static final Unit<FileSize> TB = buildUnit("TB", "TebiByte", GB);
    public static final Unit<FileSize> PB = buildUnit("PB", "PebiByte", TB);
    public static final Unit<FileSize> EB = buildUnit("EB", "ExbiByte", PB);
    public static final Unit<FileSize> ZB = buildUnit("ZB", "ZebiByte", EB);
    public static final Unit<FileSize> YB = buildUnit("YB", "YobiByte", ZB);

    private static final Map<String, Unit<FileSize>> FILE_SIZE_UNIT_MAP =
            MapUtils.buildUnmodifiableMap(Unit::getSymbol, BYTE, KB, MB, GB, TB, PB, EB, ZB, YB);
    private static final List<Unit<FileSize>> FILE_SIZE_UNIT_LIST =
            CollUtils.unmodifiableList(BYTE, KB, MB, GB, TB, PB, EB, ZB, YB);
    private static final String[] FILE_SIZE_UNIT_SYMBOL_ARRAY =
            FILE_SIZE_UNIT_LIST.stream().map(Unit::getSymbol).toArray(String[]::new);

    /**
     * 获取完整的 文件大小单位列表
     *
     * @return 类型为 {@code List<Unit<FileSize>>} 的文件大小单位列表
     */
    public static List<Unit<FileSize>> fullFileSizeUnitList() {
        return FILE_SIZE_UNIT_LIST;
    }

    /**
     * 获取完整的 文件大小单位映射表MAP
     *
     * @return 类型为 {@code Map<String, Unit<FileSize>>} 的文件大小单位映射表MAP
     */
    public static Map<String, Unit<FileSize>> fullFileSizeUnitMap() {
        return FILE_SIZE_UNIT_MAP;
    }

    /**
     * 获取完整的 文件大小单位符号名数组
     *
     * @return 文件大小单位符号名数组
     */
    public static String[] fullFileSizeUnitSymbolArray() {
        return FILE_SIZE_UNIT_SYMBOL_ARRAY;
    }

    /**
     * 根据文件大小符号名获取 类型为 {@code Unit<FileSize>} 的文件大小单位
     *
     * @param unitSymbol 文件大小符号名(B, KB, MB, GB, TB, PB, EB, ZB, YB)
     * @return 类型为 {@code Unit<FileSize>} 的文件大小单位
     */
    public static Unit<FileSize> getFileSizeUnitBySymbol(String unitSymbol) {
        return FILE_SIZE_UNIT_MAP.get(unitSymbol.toUpperCase());
    }

    /**
     * 根据字节数解析出最合适的文件大小单位
     *
     * @param bytes 字节数
     * @return 类型为 {@code Unit<FileSize>} 的文件大小单位
     */
    public static Unit<FileSize> resolveAppropriateFileSizeUnitFromBytes(Number bytes) {
        int bitLen = NumberUtils.getAbsBitLength(bytes);
        int listSize = FILE_SIZE_UNIT_LIST.size();
        int index = Math.min((bitLen - 1) / 10, listSize - 1);
        return FILE_SIZE_UNIT_LIST.get(index);
    }

    /**
     * 根据字节数解析出最合适的文件大小单位的符号
     *
     * @param bytes 字节数
     * @return 文件大小单位的符号（B, KB, MB, GB, TB, PB, EB, ZB, YB）
     */
    public static String resolveAppropriateFileSizeUnitSymbolFromBytes(Number bytes) {
        return resolveAppropriateFileSizeUnitFromBytes(bytes).getSymbol();
    }

    /**
     * 获取单位规范化后的文件大小（用字符串数组表示，第一个元素为值，第二个元素为单位）
     * <p>
     * 转换示例如下：
     * </p>
     * <pre>
     *     normalizeFileSizeToStrArray(123456789, FileSizeUnitDef.KB) ==> ["117.74", "GB"]
     *     normalizeFileSizeToStrArray(123.45678, FileSizeUnitDef.MB) ==> ["123.46", "MB"]
     *     normalizeFileSizeToStrArray(0.0123456, FileSizeUnitDef.PB) ==> ["12.64", "TB"]
     *     normalizeFileSizeToStrArray(0.0123456, FileSizeUnitDef.KB) ==> ["13", "B"]
     * </pre>
     *
     * @param val  文件大小的值
     * @param unit 文件大小的单位
     * @return 单位规范化后的文件大小（用字符串数组表示，第一个元素为值，第二个元素为单位）
     */
    public static String[] normalizeFileSizeToStrArray(Number val, Unit<FileSize> unit) {
        Quantity<FileSize> quantity = normalizeFileSize(val, unit);
        String appropriateValue = NumberUtils.resolveApproximateValueAsStr(quantity.getValue(), 2, 4, true);
        return new String[]{appropriateValue, quantity.getUnit().getSymbol()};
    }

    /**
     * 获取单位规范化后的文件大小（用字符串表示: 值 单位）
     * <p>
     * 转换示例如下：
     * </p>
     * <pre>
     *     normalizeFileSizeToStr(123456789, FileSizeUnitDef.KB) ==> "117.74 GB"
     *     normalizeFileSizeToStr(123.45678, FileSizeUnitDef.MB) ==> "123.46 MB"
     *     normalizeFileSizeToStr(0.0123456, FileSizeUnitDef.PB) ==> "12.64 TB"
     *     normalizeFileSizeToStr(0.0123456, FileSizeUnitDef.KB) ==> "13 B"
     * </pre>
     *
     * @param val  文件大小的值
     * @param unit 文件大小的单位
     * @return 单位规范化后的文件大小（用字符串表示: 值 单位）
     */
    public static String normalizeFileSizeToStr(Number val, Unit<FileSize> unit) {
        return ArrayUtil.join(normalizeFileSizeToStrArray(val, unit), StrUtils.SPACE);
    }

    /**
     * 获取单位规范化后的文件大小
     *
     * @param val  文件大小的值
     * @param unit 文件大小的单位
     * @return 单位规范化后的文件大小
     */
    public static Quantity<FileSize> normalizeFileSize(Number val, Unit<FileSize> unit) {
        Quantity<FileSize> result = Quantities.getQuantity(val, unit);

        if (unit.equals(BYTE)) {
            Unit<FileSize> targetUnit = resolveAppropriateFileSizeUnitFromBytes(val);
            return result.to(targetUnit);
        }

        if (NumberUtils.isBetweenLeftClosedRightOpenInterval(val, 1, FILE_SIZE_CONVERSION_FACTOR)) {
            return assureConvertedBytesValueIsIntegral(result);
        }

        List<Unit<FileSize>> unitList = fullFileSizeUnitList();
        int index = unitList.indexOf(unit);

        if (NumberUtils.isLessThan(val, 1)) {
            for (int i = index - 1; i >= 0; i--) {
                Unit<FileSize> candidateUnit = unitList.get(i);
                result = result.to(candidateUnit);
                if (NumberUtils.isBetweenLeftClosedRightOpenInterval(result.getValue(), 1, FILE_SIZE_CONVERSION_FACTOR)) {
                    break;
                }
            }
        } else {
            for (int i = index + 1; i < unitList.size(); i++) {
                Unit<FileSize> candidateUnit = unitList.get(i);
                result = result.to(candidateUnit);
                if (NumberUtils.isBetweenLeftClosedRightOpenInterval(result.getValue(), 1, FILE_SIZE_CONVERSION_FACTOR)) {
                    break;
                }
            }
        }

        return assureConvertedBytesValueIsIntegral(result);
    }

    /**
     * 文件大小换算
     *
     * @param val        文件大小的值
     * @param srcUnit    换算来源文件大小的单位
     * @param targetUnit 换算目标文件大小的单位
     * @return 换算后的文件大小
     */
    public static Quantity<FileSize> convertFileSize(Number val, Unit<FileSize> srcUnit, Unit<FileSize> targetUnit) {
        Quantity<FileSize> quantity = Quantities.getQuantity(val, srcUnit);
        return assureConvertedBytesValueIsIntegral(quantity.to(targetUnit));
    }

    /**
     * 将文件大小的数值转换为指定的单位
     *
     * @param value      文件大小的数值
     * @param srcUnit    源单位
     * @param targetUnit 目标单位
     * @param scale      小数点位数
     * @return 单位转换后的数值
     */
    public static Number convertFileSizeValueToSpecifiedUnit(long value,
                                                             Unit<FileSize> srcUnit,
                                                             Unit<FileSize> targetUnit,
                                                             int scale) {
        if (Objects.equals(srcUnit, targetUnit)) {
            return value;
        }

        Number convertedValue = Quantities.getQuantity(value, srcUnit).to(targetUnit).getValue();
        return resolveApproximateValue(convertedValue, scale, scale);
    }


    /**
     * 文件大小换算
     *
     * @param val        文件大小的值
     * @param srcUnit    换算来源文件大小的单位
     * @param targetUnit 换算目标文件大小的单位
     * @return 换算后的文件大小字符串
     */
    public static String convertFileSizeToStr(Number val, Unit<FileSize> srcUnit, Unit<FileSize> targetUnit) {
        Quantity<FileSize> quantity = convertFileSize(val, srcUnit, targetUnit);
        String valueStr = NumberUtils.resolveApproximateValueAsStr(quantity.getValue(), 2, 4, true);
        return String.join(StrUtils.SPACE, valueStr, quantity.getUnit().getSymbol());
    }

    /**
     * 对文件大小值进行转换，使得其转换成字节数后为整数
     *
     * @param quantity {@link Quantity}&lt;{@link FileSize}&gt; 类型的文件大小
     * @return 转换后的文件大小
     */
    private static Quantity<FileSize> assureConvertedBytesValueIsIntegral(Quantity<FileSize> quantity) {
        Number value = quantity.getValue();
        Unit<FileSize> unit = quantity.getUnit();

        if (!NumberUtils.hasNonZeroFractionalPart(value)) {
            return quantity;
        }

        if (unit.equals(BYTE)) {
            Number integralVal = NumberUtils.toIntegralBigDecimal(value);
            return Quantities.getQuantity(integralVal, BYTE);
        } else {
            Quantity<FileSize> byteSize = quantity.to(BYTE);
            if (!NumberUtils.hasNonZeroFractionalPart(byteSize.getValue())) {
                return quantity;
            } else {
                Number integralBytesValue = NumberUtils.toIntegralBigDecimal(byteSize.getValue());
                return Quantities.getQuantity(integralBytesValue, BYTE).to(unit);
            }
        }
    }

    private static Unit<FileSize> buildUnit(String symbol, String name, Unit<FileSize> parentUnit) {
        MultiplyConverter multiplyConverter = MultiplyConverter.ofRational(FILE_SIZE_CONVERSION_FACTOR, 1);
        return new TransformedUnit<>(symbol, name, parentUnit, BYTE, multiplyConverter);
    }
}

package plus.hutool.core.datetime;

import cn.hutool.core.util.StrUtil;
import org.springframework.lang.Nullable;
import plus.hutool.core.iterable.collection.ArrayUtils;
import plus.hutool.core.lang.Asserts;
import plus.hutool.core.lang.ExceptionUtils;
import plus.hutool.core.math.NumberUtils;
import plus.hutool.core.text.string.StrUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalQuery;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static cn.hutool.core.text.StrPool.*;

/**
 * 日期时间工具类
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings({"unused", "JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class DateTimeUtils {
    private DateTimeUtils() {}

    public static final long HOURS_PER_DAY = TimeUnit.DAYS.toHours(1);
    public static final long MINUTES_PER_DAY = TimeUnit.DAYS.toMinutes(1);
    public static final long SECONDS_PER_DAY = TimeUnit.DAYS.toSeconds(1);
    public static final long MILLIS_PER_DAY = TimeUnit.DAYS.toMillis(1);
    public static final long NANOS_PER_DAY = TimeUnit.DAYS.toNanos(1);

    public static final long MINUTES_PER_HOUR = TimeUnit.HOURS.toMinutes(1);
    public static final long SECONDS_PER_HOUR = TimeUnit.HOURS.toSeconds(1);
    public static final long MILLIS_PER_HOUR = TimeUnit.HOURS.toMillis(1);
    public static final long MICROS_PER_HOUR = TimeUnit.HOURS.toMicros(1);
    public static final long NANOS_PER_HOUR = TimeUnit.HOURS.toNanos(1);

    public static final String ERROR_MSG_TPL_CONVERT_CHRONO_UNIT_TO_DURATION = "不支持将 ChronoUnit[{}] 转换为 Duration";

    public static final long NANOS_PER_MILLIS = TimeUnit.MILLISECONDS.toNanos(1);

    private static final Map<String, DateTimeFormatter> FORMATTER_CACHE = new ConcurrentHashMap<>(16);

    private static final DateTimeFormatter[] PRESET_FORMATTERS_FOR_PARSING_YEARMONTH;
    private static final DateTimeFormatter[] PRESET_FORMATTERS_FOR_PARSING_LOCALDATE;
    private static final DateTimeFormatter[] PRESET_FORMATTERS_FOR_PARSING_LOCALDATETIME;
    private static final DateTimeFormatter[] PRESET_FORMATTERS_FOR_PARSING_LOCALTIME;

    static {
        PRESET_FORMATTERS_FOR_PARSING_YEARMONTH = new DateTimeFormatter[]{
                fromPattern("yyyy-M"),
                fromPattern("yyyy/M"),
                fromPattern("yyyy.M"),
                fromPattern("yyyy_M"),
                fromPattern("yyyy,M"),
                fromPattern("yyyy M"),
                fromPattern("yyyyMM"),
        };
    }

    static {
        PRESET_FORMATTERS_FOR_PARSING_LOCALDATE = new DateTimeFormatter[]{
                fromPattern("yyyy-M-d"),
                fromPattern("yyyy/M/d"),
                fromPattern("yyyy.M.d"),
                fromPattern("yyyy_M_d"),
                fromPattern("yyyy,M,d"),
                fromPattern("yyyy M d"),
                DateTimeFormatter.BASIC_ISO_DATE,
                DateTimeFormatter.ISO_DATE,
        };
    }

    static {
        //noinspection SpellCheckingInspection
        PRESET_FORMATTERS_FOR_PARSING_LOCALDATETIME = new DateTimeFormatter[]{
                DateTimeFormatter.ISO_DATE_TIME,
                fromPattern("yyyy-M-d H:m:s"),
                fromPattern("yyyy-M-d H:m:s.SSS"),
                fromPattern("yyyy/M/d H:m:s"),
                fromPattern("yyyy/M/d H:m:s.SSS"),
                fromPattern("yyyy.M.d H:m:s"),
                fromPattern("yyyy.M.d H:m:s.SSS"),
                fromPattern("yyyy_M_d H:m:s"),
                fromPattern("yyyy_M_d H:m:s.SSS"),
                fromPattern("yyyy,M,d H:m:s"),
                fromPattern("yyyy,M,d H:m:s.SSS"),
                fromPattern("yyyy M d H:m:s"),
                fromPattern("yyyy M d H:m:s.SSS"),
                fromPattern("yyyyMMdd H:m:s"),
                fromPattern("yyyyMMdd H:m:s.SSS"),
                fromPattern("yyyyMMdd H m s"),
                fromPattern("yyyyMMdd H m s SSS"),
                fromPattern("yyyyMMdd HHmmss"),
                fromPattern("yyyyMMddHHmmss"),
                fromPattern("yyyyMMdd HHmmss SSS"),
                fromPattern("yyyyMMdd HHmmssSSS"),
                fromPattern("yyyyMMddHHmmss SSS"),
                fromPattern("yyyyMMddHHmmssSSS"),
                fromPattern("yyyy M d H m s"),
                fromPattern("yyyy M d H m s SSS"),
        };
    }

    static {
        //noinspection SpellCheckingInspection
        PRESET_FORMATTERS_FOR_PARSING_LOCALTIME = new DateTimeFormatter[]{
                fromPattern("H:m:s"),
                fromPattern("H:m:s.SSS"),
                fromPattern("H m s"),
                fromPattern("H m s SSS"),
                fromPattern("HHmmss"),
                fromPattern("HHmmss SSS"),
                fromPattern("HHmmssSSS"),
                DateTimeFormatter.ISO_TIME,
        };
    }

    /**
     * 将 {@link ChronoUnit} 为单位的持续时间转换为 {@link Duration} 格式
     *
     * @param amount 数值
     * @param unit   单位
     * @return {@link Duration} 格式的持续时间
     */
    public static Duration convertChronoUnitToDuration(long amount, ChronoUnit unit) {
        try {
            return Duration.of(amount, unit);
        } catch (UnsupportedTemporalTypeException e) {
            throw new UnsupportedTemporalTypeException(
                    StrUtil.format(ERROR_MSG_TPL_CONVERT_CHRONO_UNIT_TO_DURATION, unit));
        }
    }

    /**
     * 将 {@link Duration} 格式的持续时间转换为其它单位的持续时间
     *
     * @param duration   {@link Duration} 格式的持续时间
     * @param targetUnit 转换的目标持续时间单位
     * @param scale      小数位数
     * @return 其它单位的持续时间（字符串表示的数值）
     */
    public static String convertDuration(Duration duration, ChronoUnit targetUnit, int scale) {
        return convertDuration(duration, targetUnit, scale, false);
    }

    /**
     * 将 {@link Duration} 格式的持续时间转换为其它单位的持续时间
     *
     * @param duration           {@link Duration} 格式的持续时间
     * @param targetUnit         转换的目标持续时间单位
     * @param scale              小数位数
     * @param fixedDecimalLength 是否固定小数位数的长度
     * @return 其它单位的持续时间（字符串表示的数值）
     */
    public static String convertDuration(Duration duration, ChronoUnit targetUnit, int scale, boolean fixedDecimalLength) {
        long nanos = duration.toNanos();
        long nanosPerUnit = convertChronoUnitToDuration(1, targetUnit).toNanos();

        if (!fixedDecimalLength && (nanos % nanosPerUnit == 0)) {
            scale = 0;
        }

        BigDecimal val = BigDecimal.valueOf(nanos).divide(BigDecimal.valueOf(nanosPerUnit), scale, RoundingMode.HALF_UP);

        boolean cleanUnnecessaryZeros = !fixedDecimalLength;
        return NumberUtils.resolveApproximateValueAsStr(val, scale, scale, cleanUnnecessaryZeros);
    }

    /**
     * 将 {@link OffsetDateTime} 格式的日期时间转换为 {@link LocalDateTime} 格式的日期时间
     *
     * @param offsetDateTime {@link OffsetDateTime} 格式的日期时间
     * @return {@link LocalDateTime} 格式的日期时间
     */
    public static LocalDateTime convertOffsetDateTimeToLocalDateTime(String offsetDateTime) {
        return OffsetDateTime.parse(offsetDateTime)
                .atZoneSameInstant(ZoneId.systemDefault())
                .toLocalDateTime();
    }


    /**
     * 将日期字符串（会自动解析常见的日期字符串）转换为格式为 yyyyMMdd 的整数
     *
     * @param dateStr 日期格式的字符串
     * @return 整数
     * @throws DateTimeParseException 如果日期字符串格式错误
     */
    @Nullable
    public static Integer dateStrToInteger(@Nullable String dateStr) {
        if (StrUtil.isBlank(dateStr)) {
            return null;
        }
        LocalDate localDate = DateTimeUtils.parseLocalDate(dateStr);
        dateStr = DateTimeFormatter.BASIC_ISO_DATE.format(localDate);
        return Integer.valueOf(dateStr);
    }

    /**
     * 将 {@link Duration} 格式的持续时间 转换为更易读的形如 '1h 23m 45.678s' 的格式
     *
     * @param duration 持续时间
     * @return 更易读的时间格式
     */
    public static String durationToMoreReadableFormat(Duration duration) {
        return durationToMoreReadableFormat(duration, false);
    }

    /**
     * 将 {@link Duration} 格式的持续时间 转换为更易读的形如 '1h 23m 45.678s' 的格式
     *
     * @param duration                 持续时间
     * @param fixedSecondsScaleToThree 是否将秒数的小数部分长度固定为 3
     * @return 更易读的时间格式
     */
    public static String durationToMoreReadableFormat(Duration duration, boolean fixedSecondsScaleToThree) {
        duration = downPrecisionToMillis(duration);

        String formatOfIso8601 = duration.toString().substring(2);

        if (formatOfIso8601.contains(StrUtils.UPPERCASE_LETTER_H) &&
                !formatOfIso8601.contains(StrUtils.UPPERCASE_LETTER_M)) {
            formatOfIso8601 = StrUtil.replace(formatOfIso8601, StrUtils.UPPERCASE_LETTER_H, "H0M");
        }

        if (formatOfIso8601.contains(StrUtils.UPPERCASE_LETTER_M) &&
                !formatOfIso8601.contains(StrUtils.UPPERCASE_LETTER_S)) {
            formatOfIso8601 = StrUtil.replace(formatOfIso8601, StrUtils.UPPERCASE_LETTER_M, "M0S");
        }

        String result = formatOfIso8601;
        if (fixedSecondsScaleToThree) {
            boolean onlyContainingSecondsPart = !formatOfIso8601.contains("M");
            String secondsPart;
            if (onlyContainingSecondsPart) {
                secondsPart = StrUtil.subBefore(formatOfIso8601, "S", true);
            } else {
                secondsPart = StrUtil.subBetween(formatOfIso8601, "M", "S");
            }
            double secondsAsDouble = Double.parseDouble(secondsPart);
            String zeroPaddedSeconds = new DecimalFormat("0.000").format(secondsAsDouble);

            if (onlyContainingSecondsPart) {
                result = StrUtil.format("{}S", zeroPaddedSeconds);
            } else {
                String partBeforeMin = StrUtil.subBefore(formatOfIso8601, "M", false);
                result = StrUtil.format("{}M{}S", partBeforeMin, zeroPaddedSeconds);
            }
        }
        result = result.replaceAll("(\\d[HMS])(?!$)", "$1 ").toLowerCase();
        return result;
    }

    /**
     * 将 {@link Duration} 的精度调小为 毫秒级（即：丢失其包含的纳秒级的数值）
     *
     * @param duration {@link Duration} 格式的持续时间
     * @return 精度调小为毫秒级之后的 {@link Duration} 格式的持续时间
     */
    public static Duration downPrecisionToMillis(Duration duration) {
        long secs = duration.getSeconds();
        long nanos = 1000_000 * Math.round(1.0 * duration.getNano() / 1000_000);
        return Duration.ofSeconds(secs, nanos);
    }

    /**
     * 将持续时间 转换为更易读的形如 '1h 23m 45.678s' 的格式
     *
     * @param amount 持续时间大小
     * @param unit   持续时间单位
     * @return 更易读的持续时间（字符串）
     */
    public static String durationToMoreReadableFormat(long amount, ChronoUnit unit) {
        Duration duration = convertChronoUnitToDuration(amount, unit);
        return durationToMoreReadableFormat(duration);
    }

    /**
     * 将持续时间 转换为更易读的形如 '1h 23m 45.678s' 的格式
     *
     * @param amount   持续时间大小
     * @param timeUnit 持续时间单位
     * @return 更易读的持续时间（字符串）
     */
    public static String durationToMoreReadableFormat(long amount, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(amount);
        Duration duration = Duration.ofNanos(nanos);
        return durationToMoreReadableFormat(duration);
    }

    /**
     * 根据 模式字符串 获取日期时间格式化器（会忽略模式字符串两端的空白，有缓存）
     *
     * @param pattern 模式字符串
     * @return 日期时间格式化器
     */
    public static DateTimeFormatter fromPattern(String pattern) {
        Asserts.notBlank(pattern, "日期时间格式化器模板字符串不能为空");

        DateTimeFormatter dtf = FORMATTER_CACHE.get(pattern.trim());
        if (FORMATTER_CACHE.get(pattern) == null) {
            dtf = DateTimeFormatter.ofPattern(pattern);
            FORMATTER_CACHE.putIfAbsent(pattern, dtf);
        }
        return dtf;
    }

    /**
     * 获取指定起止日期范围内的日期数组（日期用整数表示，格式为 yyyyMMdd）
     *
     * @param start 起始日期
     * @param end   结束日期
     * @return 指定起止日期范围内的日期数组
     */
    public static int[] getAllDatesInRangeAsArray(int start, int end) {
        if (start == end) {
            return new int[]{start};
        } else if (start > end) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }

        LocalDate startDate = parseLocalDate(start);
        LocalDate endDateExclusive = parseLocalDate(end).plusDays(1);

        long numOfDaysBetween = ChronoUnit.DAYS.between(startDate, endDateExclusive);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfDaysBetween)
                .map(i -> localDateToInt(startDate.plusDays(i))).toArray();
    }

    /**
     * 获取指定起止日期范围内的日期列表（日期用整数表示，格式为 yyyyMMdd）
     *
     * @param begin 起始日期
     * @param end   结束日期
     * @return 指定起止日期范围内的日期列表
     */
    public static List<Integer> getAllDatesInRangeAsList(int begin, int end) {
        return Arrays.stream(getAllDatesInRangeAsArray(begin, end)).boxed().collect(Collectors.toList());
    }

    /**
     * 获取指定起止年月范围内的年月数组（年月用整数表示，格式为 yyyyMM）
     *
     * @param begin 起始年月
     * @param end   结束年月
     * @return 指定起止年月范围内的年月数组
     */
    public static int[] getAllYearMonthsInRangeAsArray(int begin, int end) {
        if (begin == end) {
            return new int[]{begin};
        } else if (begin > end) {
            return ArrayUtils.EMPTY_INT_ARRAY;
        }

        YearMonth startYearMonth = parseYearMonth(begin);
        YearMonth endYearMonthExclusive = parseYearMonth(end).plusMonths(1);

        long numOfYearMonthsBetween = ChronoUnit.MONTHS.between(startYearMonth, endYearMonthExclusive);
        return IntStream.iterate(0, i -> i + 1)
                .limit(numOfYearMonthsBetween)
                .map(i -> yearMonthToInt(startYearMonth.plusMonths(i))).toArray();
    }

    /**
     * 获取指定起止年月范围内的年月列表（年月用整数表示，格式为 yyyyMM）
     *
     * @param begin 起始年月
     * @param end   结束年月
     * @return 指定起止年月范围内的年月列表
     */
    public static List<Integer> getAllYearMonthsInRangeAsList(int begin, int end) {
        return Arrays.stream(getAllYearMonthsInRangeAsArray(begin, end)).boxed().collect(Collectors.toList());
    }

    /**
     * 获取指定起止年份范围内的年份数组（年份用整数表示，格式为 yyyy）
     *
     * @param begin 起始年份
     * @param end   结束年份
     * @return 指定起止年份范围内的年份数组
     */
    public static int[] getAllYearsInRangeAsArray(int begin, int end) {
        return IntStream.rangeClosed(begin, end).toArray();
    }

    /**
     * 获取指定起止年份范围内的年份列表（年份用整数表示，格式为 yyyy）
     *
     * @param begin 起始年份
     * @param end   结束年份
     * @return 指定起止年份范围内的年份数组
     */
    public static List<Integer> getAllYearsInRangeAsList(int begin, int end) {
        return IntStream.rangeClosed(begin, end).boxed().collect(Collectors.toList());
    }

    /**
     * 将 {@link LocalDate} 转换为整数（格式为 yyyyMMdd）
     *
     * @param localDate 日期
     * @return 整数表示的日期（格式为 yyyyMMdd）
     */
    public static int localDateToInt(LocalDate localDate) {
        String dateStr = fromPattern("yyyyMMdd").format(localDate);
        return Integer.parseInt(dateStr);
    }

    /**
     * 将毫秒数转换为更易读的形如 '1h 23m 45.678s' 的格式
     *
     * @param milliseconds 毫秒数
     * @return 更易读的时间格式
     */
    public static String millisToMoreReadableFormat(long milliseconds) {
        return millisToMoreReadableFormat(milliseconds, false);
    }

    /**
     * 将毫秒数转换为更易读的形如 '1h 23m 45.678s' 格式
     *
     * @param milliseconds             毫秒数
     * @param fixedSecondsScaleToThree 是否将秒数的小数部分长度固定为 3
     * @return 更易读的时间格式
     */
    public static String millisToMoreReadableFormat(long milliseconds, boolean fixedSecondsScaleToThree) {
        return durationToMoreReadableFormat(Duration.ofMillis(milliseconds), fixedSecondsScaleToThree);
    }

    /**
     * 将毫秒数转换为秒数字符串（以单位 s 结尾）
     *
     * @param millis 毫秒数
     * @param scale  小数位数
     * @return 秒数字符串
     */
    public static String millisToSecondsStr(long millis, int scale) {
        return BigDecimal.valueOf(millis)
                .divide(BigDecimal.valueOf(1000), scale, RoundingMode.HALF_EVEN)
                .toPlainString() + "s";
    }

    /**
     * 将纳秒数转换为更易读的形如 '1h 23m 45.678s' 的格式
     *
     * @param nanoseconds 毫秒数
     * @return 更易读的时间格式
     */
    public static String nanosToMoreReadableFormat(long nanoseconds) {
        return nanosToMoreReadableFormat(nanoseconds, false);
    }

    /**
     * 将纳秒数转换为更易读的形如 '1h 23m 45.678s' 格式
     *
     * @param nanoseconds              毫秒数
     * @param fixedSecondsScaleToThree 是否将秒数的小数部分长度固定为 3
     * @return 更易读的时间格式
     */
    public static String nanosToMoreReadableFormat(long nanoseconds, boolean fixedSecondsScaleToThree) {
        long milliseconds = TimeUnit.NANOSECONDS.toMillis(nanoseconds);
        return millisToMoreReadableFormat(milliseconds, fixedSecondsScaleToThree);
    }

    /**
     * 将整数表示的日期（格式为 yyyyMMdd）解析为 {@link LocalDate}
     *
     * @param dateAsInt 整数表示的日期（格式为 yyyyMMdd）
     * @return 解析出的 LocalDate
     * @throws DateTimeParseException 如果整型日期格式错误
     */
    public static LocalDate parseLocalDate(int dateAsInt) {
        String dateStr = String.valueOf(dateAsInt);
        return LocalDate.parse(dateStr, fromPattern("yyyyMMdd"));
    }

    /**
     * 将字符串表示的日期转换为 {@link LocalDate}，示例如下：
     * <pre>
     *     parseLocalDate("2022-01-10")   ==>   2022-01-10
     *     parseLocalDate("2022-10-1")    ==>   2022-10-01
     *     parseLocalDate("2022-1-01")    ==>   2022-01-01
     *     parseLocalDate("2022-1-1")     ==>   2022-01-01
     *
     *     parseLocalDate("2022/01/10")   ==>   2022-01-10
     *     parseLocalDate("2022/10/1")    ==>   2022-10-01
     *     parseLocalDate("2022/1/01")    ==>   2022-01-01
     *     parseLocalDate("2022/1/1")     ==>   2022-01-01
     *
     *     parseLocalDate("2022.01.10")   ==>   2022-01-10
     *     parseLocalDate("2022.10.1")    ==>   2022-10-01
     *     parseLocalDate("2022.1.01")    ==>   2022-01-01
     *     parseLocalDate("2022.1.1")     ==>   2022-01-01
     *
     *     parseLocalDate("2022_01_10")   ==>   2022-01-10
     *     parseLocalDate("2022_10_1")    ==>   2022-10-01
     *     parseLocalDate("2022_1_01")    ==>   2022-01-01
     *     parseLocalDate("2022_1_1")     ==>   2022-01-01
     *
     *     parseLocalDate("2022,01,10")   ==>   2022-01-10
     *     parseLocalDate("2022,10,1")    ==>   2022-10-01
     *     parseLocalDate("2022,1,01")    ==>   2022-01-01
     *     parseLocalDate("2022,1,1")     ==>   2022-01-01
     *
     *     parseLocalDate("2022 01 10")   ==>   2022-01-10
     *     parseLocalDate("2022 10 1")    ==>   2022-10-01
     *     parseLocalDate("2022 1 01")    ==>   2022-01-01
     *     parseLocalDate("2022 1 1")     ==>   2022-01-01
     *
     *     parseLocalDate("20220110")     ==>   2022-01-10
     *     parseLocalDate("20221010")     ==>   2022-10-10
     * </pre>
     *
     * @param str 字符串表示的日期
     * @return {@link LocalDate} 格式的日期
     * @throws DateTimeParseException 如果日期字符串格式错误
     */
    public static LocalDate parseLocalDate(String str) {
        return doParse("日期", str, PRESET_FORMATTERS_FOR_PARSING_LOCALDATE, LocalDate::from);
    }

    /**
     * 将字符串表示的日期时间转换为 {@link LocalDateTime}，示例如下：
     *
     * <p>注意：如果存在毫秒值，其必须是3个数字</p>
     * <pre>
     *     parseLocalDateTime("2022-01-10 8:30:5")       ==>   2022-01-10 08:30:05
     *     parseLocalDateTime("2022-10-1  8:30:5")       ==>   2022-10-01 08:30:05
     *     parseLocalDateTime("2022-1-01  8:30:5.012")   ==>   2022-01-01 08:30:05.012
     *     parseLocalDateTime("2022-1-1   8:30:5.012")   ==>   2022-01-01 08:30:05.012
     *
     *     parseLocalDateTime("2022/01/10 8:30:5")       ==>   2022-01-10 08:30:05
     *     parseLocalDateTime("2022/10/1  8:30:5")       ==>   2022-10-01 08:30:05
     *     parseLocalDateTime("2022/1/01  8:30:5.012")   ==>   2022-01-01 08:30:05.012
     *     parseLocalDateTime("2022/1/1   8:30:5.012")   ==>   2022-01-01 08:30:05.012
     *
     *     parseLocalDateTime("2022.01.10 8:30:5")       ==>   2022-01-10 08:30:05
     *     parseLocalDateTime("2022.10.1  8:30:5")       ==>   2022-10-01 08:30:05
     *     parseLocalDateTime("2022.1.01  8:30:5.012")   ==>   2022-01-01 08:30:05.012
     *     parseLocalDateTime("2022.1.1   8:30:5.012")   ==>   2022-01-01 08:30:05.012
     *
     *     parseLocalDateTime("2022_01_10 8:30:5")       ==>   2022-01-10 08:30:05
     *     parseLocalDateTime("2022_10_1  8:30:5")       ==>   2022-10-01 08:30:05
     *     parseLocalDateTime("2022_1_01  8:30:5.012")   ==>   2022-01-01 08:30:05.012
     *     parseLocalDateTime("2022_1_1   8:30:5.012")   ==>   2022-01-01 08:30:05.012
     *
     *     parseLocalDateTime("2022,01,10 8:30:5")       ==>   2022-01-10 08:30:05
     *     parseLocalDateTime("2022,10,1  8:30:5")       ==>   2022-10-01 08:30:05
     *     parseLocalDateTime("2022,1,01  8:30:5.012")   ==>   2022-01-01 08:30:05.012
     *     parseLocalDateTime("2022,1,1   8:30:5.012")   ==>   2022-01-01 08:30:05.012
     *
     *     parseLocalDateTime("2022 01 10 8:30:5")       ==>   2022-01-10 08:30:05
     *     parseLocalDateTime("2022 10 1  8:30:5")       ==>   2022-10-01 08:30:05
     *     parseLocalDateTime("2022 1 01  8:30:5.012")   ==>   2022-01-01 08:30:05.012
     *     parseLocalDateTime("2022 1 1   8:30:5.012")   ==>   2022-01-01 08:30:05.012
     *
     *     parseLocalDateTime("20220110 8:30:5")         ==>   2022-01-01 08:30:05
     *     parseLocalDateTime("20220110 8:30:5.012")     ==>   2022-01-01 08:30:05.012
     *
     *     parseLocalDateTime("20220110 8 30 5")         ==>   2022-01-01 08:30:05
     *     parseLocalDateTime("20220110 8 30 5 012")     ==>   2022-01-01 08:30:05.012
     *
     *     parseLocalDateTime("20220110 083005")         ==>   2022-01-01 08:30:05
     *     parseLocalDateTime("20220110083005")          ==>   2022-01-01 08:30:05
     *     parseLocalDateTime("20220110 083005 012")     ==>   2022-01-01 08:30:05.012
     *     parseLocalDateTime("20220110 083005012")      ==>   2022-01-01 08:30:05.012
     *     parseLocalDateTime("20220110083005 012")      ==>   2022-01-01 08:30:05.012
     *     parseLocalDateTime("20220110083005012")       ==>   2022-01-01 08:30:05.012
     *
     *     parseLocalDateTime("2022 1 10 8 30 5")        ==>   2022-01-01 08:30:05
     *     parseLocalDateTime("2022 1 10 8 30 5 012")    ==>   2022-01-01 08:30:05.012
     * </pre>
     *
     * @param str 字符串表示的日期时间
     * @return {@link LocalDateTime} 格式的日期时间
     * @throws DateTimeParseException 如果日期时间字符串格式错误
     */
    public static LocalDateTime parseLocalDateTime(String str) {
        return doParse("日期时间", str, PRESET_FORMATTERS_FOR_PARSING_LOCALDATETIME, LocalDateTime::from);
    }

    /**
     * 将字符串表示的时间转换为 {@link LocalTime}，示例如下：
     * <pre>
     *     parseLocalDate("08:30:05")       ==>   08:30:05
     *     parseLocalDate("08:30:05.012")   ==>   08:30:05.012
     *
     *     parseLocalDate("8:30:5")         ==>   08:30:05
     *     parseLocalDate("8:30:5.012")     ==>   08:30:05.012
     *
     *     parseLocalDate("08 30 05")       ==>   08:30:05
     *     parseLocalDate("08 30 05 012")   ==>   08:30:05.012
     *
     *     parseLocalDate("8 30 5")         ==>   08:30:05
     *     parseLocalDate("8 30 5 012")     ==>   08:30:05.012
     *
     *     parseLocalDate("083005")         ==>   08:30:05
     *     parseLocalDate("083005 012")     ==>   08:30:05.012
     *     parseLocalDate("083005012")      ==>   08:30:05.012
     * </pre>
     *
     * @param str 字符串表示的时间
     * @return {@link LocalTime} 时间
     * @throws DateTimeParseException 如果时间字符串格式错误
     */
    public static LocalTime parseLocalTime(String str) {
        return doParse("时间", str, PRESET_FORMATTERS_FOR_PARSING_LOCALTIME, LocalTime::from);
    }

    /**
     * 将整数表示的年月（格式为 yyyyMM）解析为 {@link YearMonth}
     *
     * @param yearMonth 整数表示的年月（格式为 yyyyMM）
     * @return 解析出的 YearMonth
     * @throws DateTimeParseException 如果整数年月格式错误
     */
    public static YearMonth parseYearMonth(int yearMonth) {
        String yearMonthStr = String.valueOf(yearMonth);
        return YearMonth.parse(yearMonthStr, fromPattern("yyyyMM"));
    }

    /**
     * 将字符串表示的年月转换为 {@link YearMonth}，示例如下：
     * <pre>
     *     parseYearMonth("2022-01")   ==>   2022-01
     *     parseYearMonth("2022-1")    ==>   2022-01
     *     parseYearMonth("2022-10")   ==>   2022-10
     *
     *     parseYearMonth("2022/01")   ==>   2022-01
     *     parseYearMonth("2022/1")    ==>   2022-01
     *     parseYearMonth("2022/10")   ==>   2022-10
     *
     *     parseYearMonth("2022.01")   ==>   2022-01
     *     parseYearMonth("2022.1")    ==>   2022-01
     *     parseYearMonth("2022.10")   ==>   2022-10
     *
     *     parseYearMonth("2022_01")   ==>   2022-01
     *     parseYearMonth("2022_1")    ==>   2022-01
     *     parseYearMonth("2022_10")   ==>   2022-10
     *
     *     parseYearMonth("2022,01")   ==>   2022-01
     *     parseYearMonth("2022,1")    ==>   2022-01
     *     parseYearMonth("2022,10")   ==>   2022-10
     *
     *     parseYearMonth("2022 01")   ==>   2022-01
     *     parseYearMonth("2022 1")    ==>   2022-01
     *     parseYearMonth("2022 10")   ==>   2022-10
     *
     *     parseYearMonth("202201")    ==>   2022-01
     *
     * </pre>
     *
     * @param str 字符串表示的年月
     * @return {@link YearMonth} 年月
     * @throws DateTimeParseException 如果年月字符串格式错误
     */
    public static YearMonth parseYearMonth(String str) {
        return doParse("年月", str, PRESET_FORMATTERS_FOR_PARSING_YEARMONTH, YearMonth::from);
    }

    /**
     * 将秒数转换为更易读的形如 '1h 23m 45.678s' 的格式
     *
     * @param seconds 秒数
     * @return 更易读的时间格式
     */
    public static String secondsToMoreReadableFormat(long seconds) {
        return durationToMoreReadableFormat(Duration.ofSeconds(seconds));
    }

    /**
     * 将日期范围按照指定的数量平均分割为大致相等长度的日期区间
     *
     * @param beginDate         开始日期
     * @param endDate           结束日期
     * @param expectedSplitsNum 期望分割的数量（实际返回的数量可能有微调）
     * @return 日期范围列表
     */
    public static List<LocalDateRange> splitEvenly(LocalDate beginDate, LocalDate endDate, int expectedSplitsNum) {
        return splitEvenly(LocalDateRange.of(beginDate, endDate), expectedSplitsNum);
    }

    /**
     * 将日期范围按照指定的数量平均分割为大致相等长度的日期区间
     *
     * @param dateRange         日期范围
     * @param expectedSplitsNum 期望分割的数量（实际返回的数量可能有微调）
     * @return 日期范围列表
     */
    public static List<LocalDateRange> splitEvenly(LocalDateRange dateRange, int expectedSplitsNum) {
        LocalDate beginDate = dateRange.getBeginDate();
        LocalDate endDate = dateRange.getEndDate();

        if (beginDate.isEqual(endDate)) {
            return Collections.singletonList(dateRange);
        }

        List<LocalDateRange> resultList = new ArrayList<>();
        int totalDays = dateRange.numOfDays();

        int splitsNum = expectedSplitsNum > totalDays ? totalDays : Math.max(1, expectedSplitsNum);
        int stepDays = (int) Math.ceil(totalDays * 1.0 / splitsNum) - 1;
        int startIndexOfAdjustStepSize = totalDays % splitsNum - 1;

        int i = 0;
        for (LocalDate beginDateOfSplit = beginDate, endDateOfSplit = beginDateOfSplit.plusDays(stepDays);; i++) {
            if (i == startIndexOfAdjustStepSize) {
                stepDays -= 1;
            }

            if (i == splitsNum - 1) {
                resultList.add(LocalDateRange.of(beginDateOfSplit, endDate));
                break;
            }

            LocalDateRange dateRangeOfSplit = LocalDateRange.of(beginDateOfSplit, endDateOfSplit);
            resultList.add(dateRangeOfSplit);

            beginDateOfSplit = endDateOfSplit.plusDays(1);
            endDateOfSplit = beginDateOfSplit.plusDays(stepDays);
        }

        return resultList;
    }

    /**
     * 将 {@link LocalDate} 转换为 {@link Date} 类型的日期
     *
     * @param localDate {@link LocalDate} 类型的日期
     * @return {@link Date} 类型的日期
     */
    public static Date toLegacyDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将 {@link LocalDateTime} 转换为 {@link Date} 类型的日期时间
     *
     * @param localDateTime {@link LocalDateTime} 类型的日期时间
     * @return {@link Date} 类型的日期时间
     */
    public static Date toLegacyDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 将 {@link Date} 转换为 {@link LocalDate}
     *
     * @param date {@link Date} 类型的日期时间
     * @return {@link LocalDate} 类型的日期
     */
    @Nullable
    public static LocalDate toLocalDate(@Nullable Date date) {
        return date == null ? null : new java.sql.Date(date.getTime()).toLocalDate();
    }

    /**
     * 将整型数值表示的日期转换为字符串格式的日期（yyyy-MM-dd 格式）
     *
     * @param dateAsInteger 整型格式的日期（yyyyMMdd 格式）
     * @return 字符串格式的日期
     */
    @Nullable
    public static String toLocalDateStr(@Nullable Integer dateAsInteger) {
        return toLocalDateStr(dateAsInteger, "yyyy-MM-dd");
    }

    /**
     * 将整型数值表示的日期转换为字符串格式的日期
     *
     * @param dateAsInteger 整型(Integer)格式的日期（yyyyMMdd 格式），可为空
     * @param outputPattern 输出的日期格式字符串
     * @return 字符串格式的日期
     */
    @Nullable
    public static String toLocalDateStr(@Nullable Integer dateAsInteger, @Nullable String outputPattern) {
        if (dateAsInteger == null) {
            return null;
        }

        LocalDate localDate = parseLocalDate(dateAsInteger);
        if (StrUtil.isBlank(outputPattern)) {
            outputPattern = "yyyyMMdd";
        }

        return localDate.format(fromPattern(outputPattern));
    }

    /**
     * 将 {@link Date} 转换为 {@link LocalDateTime}
     *
     * @param dateTime {@link Date} 类型的日期时间
     * @return {@link LocalDateTime} 类型的日期时间
     */
    @Nullable
    public static LocalDateTime toLocalDateTime(@Nullable Date dateTime) {
        return dateTime == null ? null : new java.sql.Timestamp(dateTime.getTime()).toLocalDateTime();
    }

    /**
     * 将 {@link YearMonth} 转换为整数（格式为 yyyyMM）
     *
     * @param yearMonth 年月
     * @return 整数表示的年月（格式为 yyyyMM）
     */
    public static int yearMonthToInt(YearMonth yearMonth) {
        String yearMonthStr = fromPattern("yyyyMM").format(yearMonth);
        return Integer.parseInt(yearMonthStr);
    }

    /**
     * 获取当前时刻的日期时间字符串
     *
     * @return 当前时刻的日期时间字符串
     */
    public static String now() {
        return now(false);
    }

    /**
     * 获取当前时刻的日期时间字符串
     *
     * @param withMillis 是否带上毫秒
     * @return 当前时刻的日期时间字符串
     */
    public static String now(boolean withMillis) {
        String pattern = withMillis ? "yyyy-MM-dd HH:mm:ss.SSS" : "yyyy-MM-dd HH:mm:ss";
        return LocalDateTime.now().format(fromPattern(pattern));
    }

    /**
     * 获取今天的日期字符串
     *
     * @return 今天的日期字符串
     */
    public static String today() {
        return LocalDate.now().format(fromPattern("yyyy-MM-dd"));
    }

    private static <T> T doParse(String type, String str, DateTimeFormatter[] candidateFormatters, TemporalQuery<T> query) {
        if (StrUtil.isBlank(str)) {
            throw ExceptionUtils.dateTimeParseException("{}字符串不能为空", type);
        }

        String cleanedStr = StrUtils.replaceAllWhiteSpacesToOneSpace(str.trim());
        cleanedStr = StrUtils.removeWhiteSpacesAroundDelimiters(cleanedStr, DASHED, SLASH, DOT, COLON);

        for (DateTimeFormatter formatter : candidateFormatters) {
            try {
                return formatter.parse(cleanedStr, query);
            } catch (DateTimeParseException e) {
                // deliberately ignore exception
            }
        }

        throw ExceptionUtils.dateTimeParseException("无法识别的{}字符串：[{}]", type, str);
    }
}

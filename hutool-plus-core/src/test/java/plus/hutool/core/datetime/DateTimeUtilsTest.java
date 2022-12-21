package plus.hutool.core.datetime;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import plus.hutool.core.iterable.collection.ArrayUtils;

import java.lang.reflect.Constructor;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static plus.hutool.core.datetime.DateTimeUtils.ERROR_MSG_TPL_CONVERT_CHRONO_UNIT_TO_DURATION;

class DateTimeUtilsTest {

    @Test
    void testPrivateConstructor() throws Exception {
        Constructor<DateTimeUtils> constructor = DateTimeUtils.class.getDeclaredConstructor();
        assertThat(constructor.isAccessible()).isFalse();
        assertThatThrownBy(constructor::newInstance);
    }

    @Test
    void testConvertChronoUnitToDuration() {
        assertThat(DateTimeUtils.convertChronoUnitToDuration(123, ChronoUnit.NANOS)).isEqualTo(Duration.ofNanos(123));
        assertThat(DateTimeUtils.convertChronoUnitToDuration(123, ChronoUnit.MICROS)).isEqualTo(Duration.of(123, ChronoUnit.MICROS));
        assertThat(DateTimeUtils.convertChronoUnitToDuration(123, ChronoUnit.MILLIS)).isEqualTo(Duration.ofMillis(123));
        assertThat(DateTimeUtils.convertChronoUnitToDuration(123, ChronoUnit.SECONDS)).isEqualTo(Duration.ofSeconds(123));
        assertThat(DateTimeUtils.convertChronoUnitToDuration(123, ChronoUnit.MINUTES)).isEqualTo(Duration.ofMinutes(123));
        assertThat(DateTimeUtils.convertChronoUnitToDuration(123, ChronoUnit.HOURS)).isEqualTo(Duration.ofHours(123));
        assertThat(DateTimeUtils.convertChronoUnitToDuration(123, ChronoUnit.HALF_DAYS)).isEqualTo(Duration.of(123, ChronoUnit.HALF_DAYS));
        assertThat(DateTimeUtils.convertChronoUnitToDuration(123, ChronoUnit.DAYS)).isEqualTo(Duration.ofDays(123));

        assertThatThrownBy(() -> DateTimeUtils.convertChronoUnitToDuration(1, ChronoUnit.WEEKS)).isInstanceOf(UnsupportedTemporalTypeException.class);
        assertThatThrownBy(() -> DateTimeUtils.convertChronoUnitToDuration(1, ChronoUnit.MONTHS)).isInstanceOf(UnsupportedTemporalTypeException.class);
        assertThatThrownBy(() -> DateTimeUtils.convertChronoUnitToDuration(1, ChronoUnit.YEARS)).isInstanceOf(UnsupportedTemporalTypeException.class);
        assertThatThrownBy(() -> DateTimeUtils.convertChronoUnitToDuration(1, ChronoUnit.CENTURIES)).isInstanceOf(UnsupportedTemporalTypeException.class);

        assertThatThrownBy(() -> DateTimeUtils.convertChronoUnitToDuration(1, ChronoUnit.WEEKS)).isInstanceOf(UnsupportedTemporalTypeException.class)
                .hasMessage(StrUtil.format(ERROR_MSG_TPL_CONVERT_CHRONO_UNIT_TO_DURATION, ChronoUnit.WEEKS));
    }

    @Test
    void testConvertDuration1() {
        final Duration duration = Duration.ofNanos(123_234_560_123_456L);

        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.NANOS, 4)).isEqualTo("123234560123456");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.MICROS, 4)).isEqualTo("123234560123.456");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.MILLIS, 4)).isEqualTo("123234560.1235");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.SECONDS, 4)).isEqualTo("123234.5601");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.SECONDS, 3)).isEqualTo("123234.56");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.SECONDS, 2)).isEqualTo("123234.56");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.SECONDS, 1)).isEqualTo("123234.6");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.MINUTES, 2)).isEqualTo("2053.91");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.HOURS, 2)).isEqualTo("34.23");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.HALF_DAYS, 2)).isEqualTo("2.85");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.DAYS, 2)).isEqualTo("1.43");

        assertThatThrownBy(() -> DateTimeUtils.convertDuration(duration, ChronoUnit.WEEKS, 2)).isInstanceOf(UnsupportedTemporalTypeException.class);
        assertThatThrownBy(() -> DateTimeUtils.convertDuration(duration, ChronoUnit.MONTHS, 2)).isInstanceOf(UnsupportedTemporalTypeException.class);
        assertThatThrownBy(() -> DateTimeUtils.convertDuration(duration, ChronoUnit.YEARS, 2)).isInstanceOf(UnsupportedTemporalTypeException.class);
        assertThatThrownBy(() -> DateTimeUtils.convertDuration(duration, ChronoUnit.CENTURIES, 2)).isInstanceOf(UnsupportedTemporalTypeException.class);
    }

    @Test
    void testConvertDuration2() {
        final Duration duration = Duration.ofNanos(123_234_560_120_000L);

        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.NANOS, 4, true)).isEqualTo("123234560120000.0000");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.MICROS, 4, true)).isEqualTo("123234560120.0000");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.MILLIS, 4, true)).isEqualTo("123234560.1200");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.SECONDS, 4, true)).isEqualTo("123234.5601");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.SECONDS, 3, true)).isEqualTo("123234.560");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.SECONDS, 2, true)).isEqualTo("123234.56");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.SECONDS, 1, true)).isEqualTo("123234.6");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.MINUTES, 2, true)).isEqualTo("2053.91");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.HOURS, 2, true)).isEqualTo("34.23");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.HALF_DAYS, 2, true)).isEqualTo("2.85");
        assertThat(DateTimeUtils.convertDuration(duration, ChronoUnit.DAYS, 2, true)).isEqualTo("1.43");

        assertThatThrownBy(() -> DateTimeUtils.convertDuration(duration, ChronoUnit.WEEKS, 2, true)).isInstanceOf(UnsupportedTemporalTypeException.class);
        assertThatThrownBy(() -> DateTimeUtils.convertDuration(duration, ChronoUnit.MONTHS, 2, true)).isInstanceOf(UnsupportedTemporalTypeException.class);
        assertThatThrownBy(() -> DateTimeUtils.convertDuration(duration, ChronoUnit.YEARS, 2, true)).isInstanceOf(UnsupportedTemporalTypeException.class);
        assertThatThrownBy(() -> DateTimeUtils.convertDuration(duration, ChronoUnit.CENTURIES, 2, true)).isInstanceOf(UnsupportedTemporalTypeException.class);
    }

    @Test
    void testConvertOffsetDateTimeToLocalDateTime() {
        assertThat(DateTimeUtils.convertOffsetDateTimeToLocalDateTime("2020-01-01T11:12:13Z"))
                .isEqualTo(OffsetDateTime.parse("2020-01-01T11:12:13Z").atZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime());
        assertThatThrownBy(() -> DateTimeUtils.convertOffsetDateTimeToLocalDateTime("2020-01-40T11:12:13Z")).isInstanceOf(DateTimeParseException.class);
    }

    @Test
    void testDateStrToInteger() {
        assertThat(DateTimeUtils.dateStrToInteger(null)).isEqualTo(null);
        assertThat(DateTimeUtils.dateStrToInteger("")).isEqualTo(null);
        assertThat(DateTimeUtils.dateStrToInteger("  ")).isEqualTo(null);
        assertThat(DateTimeUtils.dateStrToInteger("  \t \n")).isEqualTo(null);
        assertThat(DateTimeUtils.dateStrToInteger("20200101")).isEqualTo(20200101);
        assertThat(DateTimeUtils.dateStrToInteger("2020-01-01")).isEqualTo(20200101);
        assertThat(DateTimeUtils.dateStrToInteger("2020/01/01")).isEqualTo(20200101);
        assertThat(DateTimeUtils.dateStrToInteger("2020.01.01")).isEqualTo(20200101);
        assertThat(DateTimeUtils.dateStrToInteger("2020-1-1")).isEqualTo(20200101);
        assertThat(DateTimeUtils.dateStrToInteger("2020/1/1")).isEqualTo(20200101);
        assertThat(DateTimeUtils.dateStrToInteger("2020.1.1")).isEqualTo(20200101);

        assertThatThrownBy(() -> DateTimeUtils.dateStrToInteger("hello")).isInstanceOf(DateTimeParseException.class);
        assertThatThrownBy(() -> DateTimeUtils.dateStrToInteger("2020-01-40")).isInstanceOf(DateTimeParseException.class);
    }

    @Test
    void testDurationToMoreReadableFormat1() {
        assertThat(DateTimeUtils.durationToMoreReadableFormat(Duration.ofMillis(0))).isEqualTo("0s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(Duration.ofMillis(2458120))).isEqualTo("40m 58.12s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(Duration.ofMillis(7258120))).isEqualTo("2h 0m 58.12s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(Duration.ofMillis(94258120))).isEqualTo("26h 10m 58.12s");

        assertThat(DateTimeUtils.durationToMoreReadableFormat(Duration.ofMillis(0), true)).isEqualTo("0.000s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(Duration.ofMillis(2458120), true)).isEqualTo("40m 58.120s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(Duration.ofMillis(7258120), true)).isEqualTo("2h 0m 58.120s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(Duration.ofMillis(94258120), true)).isEqualTo("26h 10m 58.120s");
    }

    @Test
    void testDurationToMoreReadableFormat2() {
        assertThat(DateTimeUtils.durationToMoreReadableFormat(2458125, ChronoUnit.NANOS)).isEqualTo("0.002s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(2558125, ChronoUnit.NANOS)).isEqualTo("0.003s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(2458125, ChronoUnit.MICROS)).isEqualTo("2.458s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(2458125, ChronoUnit.MILLIS)).isEqualTo("40m 58.125s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(2458125, ChronoUnit.SECONDS)).isEqualTo("682h 48m 45s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(11, ChronoUnit.MINUTES)).isEqualTo("11m 0s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(11, ChronoUnit.HOURS)).isEqualTo("11h 0m 0s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(11, ChronoUnit.HALF_DAYS)).isEqualTo("132h 0m 0s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(11, ChronoUnit.DAYS)).isEqualTo("264h 0m 0s");

        assertThatThrownBy(() -> DateTimeUtils.durationToMoreReadableFormat(1, ChronoUnit.WEEKS)).isInstanceOf(UnsupportedTemporalTypeException.class);
        assertThatThrownBy(() -> DateTimeUtils.durationToMoreReadableFormat(1, ChronoUnit.MONTHS)).isInstanceOf(UnsupportedTemporalTypeException.class);
        assertThatThrownBy(() -> DateTimeUtils.durationToMoreReadableFormat(1, ChronoUnit.YEARS)).isInstanceOf(UnsupportedTemporalTypeException.class);
        assertThatThrownBy(() -> DateTimeUtils.durationToMoreReadableFormat(1, ChronoUnit.CENTURIES)).isInstanceOf(UnsupportedTemporalTypeException.class);
    }

    @Test
    void testDurationToMoreReadableFormat3() {
        assertThat(DateTimeUtils.durationToMoreReadableFormat(2458125, TimeUnit.NANOSECONDS)).isEqualTo("0.002s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(2458125, TimeUnit.MICROSECONDS)).isEqualTo("2.458s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(2458125, TimeUnit.MILLISECONDS)).isEqualTo("40m 58.125s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(2458125, TimeUnit.SECONDS)).isEqualTo("682h 48m 45s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(11, TimeUnit.MINUTES)).isEqualTo("11m 0s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(11, TimeUnit.HOURS)).isEqualTo("11h 0m 0s");
        assertThat(DateTimeUtils.durationToMoreReadableFormat(11, TimeUnit.DAYS)).isEqualTo("264h 0m 0s");
    }

    @Test
    void testFromPattern() {
        final DateTimeFormatter formatter1 = DateTimeUtils.fromPattern("yyyy/MM/dd");
        final DateTimeFormatter formatter2 = DateTimeUtils.fromPattern("yyyy/MM/dd");

        assertThat(LocalDate.parse("2022/01/23", formatter1)).isEqualTo(LocalDate.of(2022, 1, 23));
        assertThat(formatter1 == formatter2).isTrue();
    }

    @Test
    void testGetAllDatesInRangeAsArray() {
        assertThat(DateTimeUtils.getAllDatesInRangeAsArray(20200101, 20200101)).isEqualTo(new int[]{20200101});
        assertThat(DateTimeUtils.getAllDatesInRangeAsArray(20200101, 20200105))
                .isEqualTo(new int[]{20200101, 20200102, 20200103, 20200104, 20200105});

        assertThat(DateTimeUtils.getAllDatesInRangeAsArray(20200101, 20191230)).isEmpty();
    }

    @Test
    void testGetAllDatesInRangeAsList() {
        assertThat(DateTimeUtils.getAllDatesInRangeAsList(20200101, 20200101)).isEqualTo(Collections.singletonList(20200101));
        assertThat(DateTimeUtils.getAllDatesInRangeAsList(20200101, 20200105))
                .isEqualTo(Arrays.asList(20200101, 20200102, 20200103, 20200104, 20200105));

        assertThat(DateTimeUtils.getAllDatesInRangeAsList(20200101, 20191230)).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetAllYearMonthsInRangeAsArray() {
        assertThat(DateTimeUtils.getAllYearMonthsInRangeAsArray(202001, 202001)).isEqualTo(new int[]{202001});
        assertThat(DateTimeUtils.getAllYearMonthsInRangeAsArray(202001, 202005))
                .isEqualTo(new int[]{202001, 202002, 202003, 202004, 202005});

        assertThat(DateTimeUtils.getAllYearMonthsInRangeAsArray(202001, 201912)).isEmpty();
    }

    @Test
    void testGetAllYearMonthsInRangeAsList() {
        assertThat(DateTimeUtils.getAllYearMonthsInRangeAsList(202001, 202001)).isEqualTo(Collections.singletonList(202001));
        assertThat(DateTimeUtils.getAllYearMonthsInRangeAsList(202001, 202005))
                .isEqualTo(Arrays.asList(202001, 202002, 202003, 202004, 202005));

        assertThat(DateTimeUtils.getAllYearMonthsInRangeAsList(202001, 201912)).isEmpty();
    }

    @Test
    void testGetAllYearsInRangeAsArray() {
        assertThat(DateTimeUtils.getAllYearsInRangeAsArray(2020, 2020)).isEqualTo(new int[]{2020});
        assertThat(DateTimeUtils.getAllYearsInRangeAsArray(2021, 2025))
                .isEqualTo(new int[]{2021, 2022, 2023, 2024, 2025});

        assertThat(DateTimeUtils.getAllYearsInRangeAsArray(2020, 2019)).isEqualTo(ArrayUtils.EMPTY_INT_ARRAY);
    }

    @Test
    void testGetAllYearsInRangeAsList() {
        assertThat(DateTimeUtils.getAllYearsInRangeAsList(2020, 2020)).isEqualTo(Collections.singletonList(2020));
        assertThat(DateTimeUtils.getAllYearsInRangeAsList(2021, 2025))
                .isEqualTo(Arrays.asList(2021, 2022, 2023, 2024, 2025));

        assertThat(DateTimeUtils.getAllYearsInRangeAsList(2020, 2019)).isEmpty();
    }

    @Test
    void testLocalDateToInt() {
        assertThat(DateTimeUtils.localDateToInt(LocalDate.of(2020, 1, 1))).isEqualTo(20200101);
    }

    @Test
    void testMillisToMoreReadableFormat() {
        assertThat(DateTimeUtils.millisToMoreReadableFormat(0)).isEqualTo("0s");
        assertThat(DateTimeUtils.millisToMoreReadableFormat(2458125)).isEqualTo("40m 58.125s");
        assertThat(DateTimeUtils.millisToMoreReadableFormat(7258125)).isEqualTo("2h 0m 58.125s");
        assertThat(DateTimeUtils.millisToMoreReadableFormat(94258125)).isEqualTo("26h 10m 58.125s");
    }

    @Test
    void testMillisToSecondsStr() {
        assertThat(DateTimeUtils.millisToSecondsStr(0, 3)).isEqualTo("0.000s");
        assertThat(DateTimeUtils.millisToSecondsStr(2458125, 3)).isEqualTo("2458.125s");
        assertThat(DateTimeUtils.millisToSecondsStr(7258125, 3)).isEqualTo("7258.125s");
        assertThat(DateTimeUtils.millisToSecondsStr(94258125, 3)).isEqualTo("94258.125s");
    }

    @Test
    void testNanosToMoreReadableFormat() {
        assertThat(DateTimeUtils.nanosToMoreReadableFormat(0)).isEqualTo("0s");
        assertThat(DateTimeUtils.nanosToMoreReadableFormat(2458125000000L)).isEqualTo("40m 58.125s");
        assertThat(DateTimeUtils.nanosToMoreReadableFormat(2458125800000L)).isEqualTo("40m 58.125s");
        assertThat(DateTimeUtils.nanosToMoreReadableFormat(7258125000000L)).isEqualTo("2h 0m 58.125s");
        assertThat(DateTimeUtils.nanosToMoreReadableFormat(7258125800000L)).isEqualTo("2h 0m 58.125s");
        assertThat(DateTimeUtils.nanosToMoreReadableFormat(94258125000000L)).isEqualTo("26h 10m 58.125s");
        assertThat(DateTimeUtils.nanosToMoreReadableFormat(94258125800000L)).isEqualTo("26h 10m 58.125s");
    }

    @Test
    void testParseLocalDate1() {
        assertThat(DateTimeUtils.parseLocalDate(20200101)).isEqualTo(LocalDate.of(2020, 1, 1));
        assertThatThrownBy(() -> DateTimeUtils.parseLocalDate(20200140)).isInstanceOf(DateTimeParseException.class);
    }

    @Test
    void testParseLocalDate2() {
        assertThat(DateTimeUtils.parseLocalDate("20200101")).isEqualTo(LocalDate.of(2020, 1, 1));
        assertThat(DateTimeUtils.parseLocalDate("2020-01-01")).isEqualTo(LocalDate.of(2020, 1, 1));
        assertThat(DateTimeUtils.parseLocalDate("2020/01/01")).isEqualTo(LocalDate.of(2020, 1, 1));
        assertThat(DateTimeUtils.parseLocalDate("2020.01.01")).isEqualTo(LocalDate.of(2020, 1, 1));
        assertThat(DateTimeUtils.parseLocalDate("2020-1-1")).isEqualTo(LocalDate.of(2020, 1, 1));
        assertThat(DateTimeUtils.parseLocalDate("2020/1/1")).isEqualTo(LocalDate.of(2020, 1, 1));
        assertThat(DateTimeUtils.parseLocalDate("2020.1.1")).isEqualTo(LocalDate.of(2020, 1, 1));

        assertThatThrownBy(() -> DateTimeUtils.parseLocalDate("")).isInstanceOf(DateTimeParseException.class);
        assertThatThrownBy(() -> DateTimeUtils.parseLocalDate("2020-01-40")).isInstanceOf(DateTimeParseException.class);
    }

    @Test
    void testParseLocalDateTime() {
        assertThat(DateTimeUtils.parseLocalDateTime("2020-01-01 10:11:12")).isEqualTo(LocalDateTime.of(2020, 1, 1, 10, 11, 12));

        assertThatThrownBy(() -> DateTimeUtils.parseLocalDate("")).isInstanceOf(DateTimeParseException.class);
        assertThatThrownBy(() -> DateTimeUtils.parseLocalDate("2020-01-01 30:11:12")).isInstanceOf(DateTimeParseException.class);
    }

    @Test
    void testParseLocalTime() {
        assertThat(DateTimeUtils.parseLocalTime("10:11:12")).isEqualTo(LocalTime.of(10, 11, 12));

        assertThatThrownBy(() -> DateTimeUtils.parseLocalDate("")).isInstanceOf(DateTimeParseException.class);
        assertThatThrownBy(() -> DateTimeUtils.parseLocalDate("30:11:12")).isInstanceOf(DateTimeParseException.class);
    }

    @Test
    void testParseYearMonth1() {
        assertThat(DateTimeUtils.parseYearMonth(202001)).isEqualTo(YearMonth.of(2020, Month.JANUARY));
        assertThatThrownBy(() -> DateTimeUtils.parseYearMonth(202013)).isInstanceOf(DateTimeParseException.class);
    }

    @Test
    void testParseYearMonth2() {
        assertThat(DateTimeUtils.parseYearMonth("202001")).isEqualTo(YearMonth.of(2020, Month.JANUARY));
        assertThat(DateTimeUtils.parseYearMonth("2020-01")).isEqualTo(YearMonth.of(2020, Month.JANUARY));
        assertThat(DateTimeUtils.parseYearMonth("2020/01")).isEqualTo(YearMonth.of(2020, Month.JANUARY));
        assertThat(DateTimeUtils.parseYearMonth("2020.01")).isEqualTo(YearMonth.of(2020, Month.JANUARY));

        assertThatThrownBy(() -> DateTimeUtils.parseYearMonth("")).isInstanceOf(DateTimeParseException.class);
        assertThatThrownBy(() -> DateTimeUtils.parseYearMonth("2020-13")).isInstanceOf(DateTimeParseException.class);
    }

    @Test
    void testSecondsToMoreReadableFormat() {
        assertThat(DateTimeUtils.secondsToMoreReadableFormat(0)).isEqualTo("0s");
        assertThat(DateTimeUtils.secondsToMoreReadableFormat(2458)).isEqualTo("40m 58s");
        assertThat(DateTimeUtils.secondsToMoreReadableFormat(7258)).isEqualTo("2h 0m 58s");
        assertThat(DateTimeUtils.secondsToMoreReadableFormat(94258)).isEqualTo("26h 10m 58s");
    }

    @Test
    void testSplitEvenly1() {
        final LocalDate localDate_2020_01_01 = DateTimeUtils.parseLocalDate("2020-01-01");
        final LocalDate localDate_2020_01_02 = DateTimeUtils.parseLocalDate("2020-01-02");
        final LocalDate localDate_2020_01_10 = DateTimeUtils.parseLocalDate("2020-01-10");

        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_01, -1))
                .hasSameElementsAs(Collections.singletonList(LocalDateRange.of("2020-01-01", "2020-01-01")));
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_01, 0))
                .hasSameElementsAs(Collections.singletonList(LocalDateRange.of("2020-01-01", "2020-01-01")));
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_01, 1))
                .hasSameElementsAs(Collections.singletonList(LocalDateRange.of("2020-01-01", "2020-01-01")));
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_01, 2))
                .hasSameElementsAs(Collections.singletonList(LocalDateRange.of("2020-01-01", "2020-01-01")));

        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_02, -1))
                .hasSameElementsAs(Collections.singletonList(LocalDateRange.of("2020-01-01", "2020-01-02")));
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_02, 0))
                .hasSameElementsAs(Collections.singletonList(LocalDateRange.of("2020-01-01", "2020-01-02")));
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_02, 1))
                .hasSameElementsAs(Collections.singletonList(LocalDateRange.of("2020-01-01", "2020-01-02")));
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_02, 2))
                .hasSameElementsAs(Arrays.asList(
                        LocalDateRange.of("2020-01-01", "2020-01-01"),
                        LocalDateRange.of("2020-01-02", "2020-01-02"))
                );
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_02, 3))
                .hasSameElementsAs(Arrays.asList(
                        LocalDateRange.of("2020-01-01", "2020-01-01"),
                        LocalDateRange.of("2020-01-02", "2020-01-02"))
                );

        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_10, 5))
                .hasSameElementsAs(Arrays.asList(
                                LocalDateRange.of("2020-01-01", "2020-01-02"),
                                LocalDateRange.of("2020-01-03", "2020-01-04"),
                                LocalDateRange.of("2020-01-05", "2020-01-06"),
                                LocalDateRange.of("2020-01-07", "2020-01-08"),
                                LocalDateRange.of("2020-01-09", "2020-01-10")
                        )
                );
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_10, 10))
                .hasSameElementsAs(Arrays.asList(
                                LocalDateRange.of("2020-01-01", "2020-01-01"),
                                LocalDateRange.of("2020-01-02", "2020-01-02"),
                                LocalDateRange.of("2020-01-03", "2020-01-03"),
                                LocalDateRange.of("2020-01-04", "2020-01-04"),
                                LocalDateRange.of("2020-01-05", "2020-01-05"),
                                LocalDateRange.of("2020-01-06", "2020-01-06"),
                                LocalDateRange.of("2020-01-07", "2020-01-07"),
                                LocalDateRange.of("2020-01-08", "2020-01-08"),
                                LocalDateRange.of("2020-01-09", "2020-01-09"),
                                LocalDateRange.of("2020-01-10", "2020-01-10")
                        )
                );
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_10, 20))
                .hasSameElementsAs(Arrays.asList(
                                LocalDateRange.of("2020-01-01", "2020-01-01"),
                                LocalDateRange.of("2020-01-02", "2020-01-02"),
                                LocalDateRange.of("2020-01-03", "2020-01-03"),
                                LocalDateRange.of("2020-01-04", "2020-01-04"),
                                LocalDateRange.of("2020-01-05", "2020-01-05"),
                                LocalDateRange.of("2020-01-06", "2020-01-06"),
                                LocalDateRange.of("2020-01-07", "2020-01-07"),
                                LocalDateRange.of("2020-01-08", "2020-01-08"),
                                LocalDateRange.of("2020-01-09", "2020-01-09"),
                                LocalDateRange.of("2020-01-10", "2020-01-10")
                        )
                );

        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_10, 1)).hasSize(1);
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_10, 2)).hasSize(2);
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_10, 3)).hasSize(3);
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_10, 4)).hasSize(4);
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_10, 5)).hasSize(5);
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_10, 6)).hasSize(6);
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_10, 7)).hasSize(7);
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_10, 8)).hasSize(8);
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_10, 9)).hasSize(9);
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_10, 10)).hasSize(10);
        assertThat(DateTimeUtils.splitEvenly(localDate_2020_01_01, localDate_2020_01_10, 11)).hasSize(10);
    }

    @Test
    void testSplitEvenly2() {
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-01"), -1))
                .hasSameElementsAs(Collections.singletonList(LocalDateRange.of("2020-01-01", "2020-01-01")));

        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-01"), 0))
                .hasSameElementsAs(Collections.singletonList(LocalDateRange.of("2020-01-01", "2020-01-01")));

        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-01"), 1))
                .hasSameElementsAs(Collections.singletonList(LocalDateRange.of("2020-01-01", "2020-01-01")));

        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-01"), 2))
                .hasSameElementsAs(Collections.singletonList(LocalDateRange.of("2020-01-01", "2020-01-01")));

        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-02"), -1))
                .hasSameElementsAs(Collections.singletonList(LocalDateRange.of("2020-01-01", "2020-01-02")));
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-02"), 0))
                .hasSameElementsAs(Collections.singletonList(LocalDateRange.of("2020-01-01", "2020-01-02")));
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-02"), 1))
                .hasSameElementsAs(Collections.singletonList(LocalDateRange.of("2020-01-01", "2020-01-02")));
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-02"), 2))
                .hasSameElementsAs(Arrays.asList(
                        LocalDateRange.of("2020-01-01", "2020-01-01"),
                        LocalDateRange.of("2020-01-02", "2020-01-02"))
                );
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-02"), 3))
                .hasSameElementsAs(Arrays.asList(
                        LocalDateRange.of("2020-01-01", "2020-01-01"),
                        LocalDateRange.of("2020-01-02", "2020-01-02"))
                );

        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-10"), 5))
                .hasSameElementsAs(Arrays.asList(
                                LocalDateRange.of("2020-01-01", "2020-01-02"),
                                LocalDateRange.of("2020-01-03", "2020-01-04"),
                                LocalDateRange.of("2020-01-05", "2020-01-06"),
                                LocalDateRange.of("2020-01-07", "2020-01-08"),
                                LocalDateRange.of("2020-01-09", "2020-01-10")
                        )
                );
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-10"), 10))
                .hasSameElementsAs(Arrays.asList(
                                LocalDateRange.of("2020-01-01", "2020-01-01"),
                                LocalDateRange.of("2020-01-02", "2020-01-02"),
                                LocalDateRange.of("2020-01-03", "2020-01-03"),
                                LocalDateRange.of("2020-01-04", "2020-01-04"),
                                LocalDateRange.of("2020-01-05", "2020-01-05"),
                                LocalDateRange.of("2020-01-06", "2020-01-06"),
                                LocalDateRange.of("2020-01-07", "2020-01-07"),
                                LocalDateRange.of("2020-01-08", "2020-01-08"),
                                LocalDateRange.of("2020-01-09", "2020-01-09"),
                                LocalDateRange.of("2020-01-10", "2020-01-10")
                        )
                );
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-10"), 20))
                .hasSameElementsAs(Arrays.asList(
                                LocalDateRange.of("2020-01-01", "2020-01-01"),
                                LocalDateRange.of("2020-01-02", "2020-01-02"),
                                LocalDateRange.of("2020-01-03", "2020-01-03"),
                                LocalDateRange.of("2020-01-04", "2020-01-04"),
                                LocalDateRange.of("2020-01-05", "2020-01-05"),
                                LocalDateRange.of("2020-01-06", "2020-01-06"),
                                LocalDateRange.of("2020-01-07", "2020-01-07"),
                                LocalDateRange.of("2020-01-08", "2020-01-08"),
                                LocalDateRange.of("2020-01-09", "2020-01-09"),
                                LocalDateRange.of("2020-01-10", "2020-01-10")
                        )
                );

        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-10"), 1)).hasSize(1);
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-10"), 2)).hasSize(2);
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-10"), 3)).hasSize(3);
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-10"), 4)).hasSize(4);
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-10"), 5)).hasSize(5);
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-10"), 6)).hasSize(6);
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-10"), 7)).hasSize(7);
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-10"), 8)).hasSize(8);
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-10"), 9)).hasSize(9);
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-10"), 10)).hasSize(10);
        assertThat(DateTimeUtils.splitEvenly(LocalDateRange.of("2020-01-01", "2020-01-10"), 11)).hasSize(10);
    }

    @Test
    void testToLegacyDate1() {
        assertThat(DateTimeUtils.toLegacyDate(LocalDate.of(2020, 1, 1)))
                .isEqualTo(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
    }

    @Test
    void testToLegacyDate2() {
        assertThat(DateTimeUtils.toLegacyDate(LocalDateTime.of(2020, 1, 1, 10, 11, 12)))
                .isEqualTo(new GregorianCalendar(2020, Calendar.JANUARY, 1, 10, 11, 12).getTime());
    }

    @Test
    void testToLocalDate() {
        assertThat(DateTimeUtils.toLocalDate(null)).isNull();
        assertThat(DateTimeUtils.toLocalDate(new GregorianCalendar(2020, Calendar.JANUARY, 1, 10, 11, 12).getTime()))
                .isEqualTo(LocalDate.of(2020, 1, 1));
    }

    @Test
    void testToLocalDateStr1() {
        assertThat(DateTimeUtils.toLocalDateStr(null)).isNull();
        assertThat(DateTimeUtils.toLocalDateStr(20200101)).isEqualTo("2020-01-01");
        assertThat(DateTimeUtils.toLocalDateStr(20200101, "yyyy/MM/dd")).isEqualTo("2020/01/01");
        assertThatThrownBy(() -> DateTimeUtils.toLocalDateStr(20200140)).isInstanceOf(DateTimeParseException.class);
    }

    @Test
    void testToLocalDateStr2() {
        assertThat(DateTimeUtils.toLocalDateStr(null, "yyyy/MM/dd")).isNull();
        assertThat(DateTimeUtils.toLocalDateStr(20200101)).isEqualTo("2020-01-01");
        assertThat(DateTimeUtils.toLocalDateStr(20200101, null)).isEqualTo("20200101");
        assertThat(DateTimeUtils.toLocalDateStr(20200101, "yyyy/M/d")).isEqualTo("2020/1/1");
        assertThatThrownBy(() -> DateTimeUtils.toLocalDateStr(20200140, "yyyy/MM/dd")).isInstanceOf(DateTimeParseException.class);
    }

    @Test
    void testToLocalDateTime() {
        assertThat(DateTimeUtils.toLocalDateTime(null)).isNull();
        assertThat(DateTimeUtils.toLocalDateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1, 10, 11, 12).getTime()))
                .isEqualTo(LocalDateTime.of(2020, 1, 1, 10, 11, 12));
    }

    @Test
    void testYearMonthToInt() {
        assertThat(DateTimeUtils.yearMonthToInt(YearMonth.of(2020, Month.JANUARY))).isEqualTo(202001);
    }

    @Test
    void testDownPrecisionToMillis() {
        final Duration duration1 = Duration.ofNanos(2458123L);
        final Duration result1 = DateTimeUtils.downPrecisionToMillis(duration1);
        assertThat(result1.getSeconds()).isEqualTo(duration1.getSeconds());
        assertThat(result1.getNano()).isNotEqualTo(duration1.getNano());
        assertThat(result1.toMillis()).isEqualTo(2L);

        final Duration duration2 = Duration.ofNanos(2558123L);
        final Duration result2 = DateTimeUtils.downPrecisionToMillis(duration2);
        assertThat(result2.getSeconds()).isEqualTo(duration2.getSeconds());
        assertThat(result2.getNano()).isNotEqualTo(duration2.getNano());
        assertThat(result2.toMillis()).isEqualTo(3L);
    }

    @Test
    void testNow() {
        LocalDateTime now = LocalDateTime.parse("2022-11-29 23:56:12.258", DateTimeUtils.fromPattern("yyyy-MM-dd HH:mm:ss.SSS"));
        try (MockedStatic<LocalDateTime> utilities = Mockito.mockStatic(LocalDateTime.class)) {
            utilities.when(LocalDateTime::now).thenReturn(now);
            assertThat(DateTimeUtils.now(true)).isEqualTo("2022-11-29 23:56:12.258");
            assertThat(DateTimeUtils.now(false)).isEqualTo("2022-11-29 23:56:12");
            assertThat(DateTimeUtils.now()).isEqualTo("2022-11-29 23:56:12");
        }
    }

    @Test
    void testToday() {
        LocalDate today = LocalDate.parse("2022-11-29", DateTimeUtils.fromPattern("yyyy-MM-dd"));
        try (MockedStatic<LocalDate> utilities = Mockito.mockStatic(LocalDate.class)) {
            utilities.when(LocalDate::now).thenReturn(today);
            assertThat(DateTimeUtils.today()).isEqualTo("2022-11-29");
        }
    }
}

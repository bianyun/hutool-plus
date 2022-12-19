package plus.hutool.core.datetime;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class LocalDateRangeTest {

    @Test
    void testLocalDateRange() {
        final LocalDate localDate_2020_01_01 = LocalDate.of(2020, 1, 1);
        final LocalDate localDate_2020_01_05 = LocalDate.of(2020, 1, 5);
        final LocalDate localDate_2020_01_08 = LocalDate.of(2020, 1, 8);

        final LocalDateRange dateRange1_20200101_20200105 = LocalDateRange.of(localDate_2020_01_01, localDate_2020_01_05);
        final LocalDateRange dateRange2_20200101_20200105 = LocalDateRange.of("2020-01-01", "2020-01-05");
        final LocalDateRange dateRange3_20200102_20200103 = LocalDateRange.of("2020-01-02", "2020-01-03");
        final LocalDateRange dateRange4_20200103_20200106 = LocalDateRange.of("2020-01-03", "2020-01-06");

        assertThat(dateRange1_20200101_20200105.containsDate(localDate_2020_01_01)).isTrue();
        assertThat(dateRange1_20200101_20200105.containsDate(localDate_2020_01_05)).isTrue();
        assertThat(dateRange1_20200101_20200105.containsDate(localDate_2020_01_08)).isFalse();

        assertThat(dateRange1_20200101_20200105.containsDateRange(dateRange2_20200101_20200105)).isTrue();
        assertThat(dateRange1_20200101_20200105.containsDateRange(dateRange3_20200102_20200103)).isTrue();
        assertThat(dateRange1_20200101_20200105.containsDateRange(dateRange4_20200103_20200106)).isFalse();

        assertThat(dateRange1_20200101_20200105).isEqualTo(dateRange2_20200101_20200105);
        assertThat(dateRange1_20200101_20200105).isEqualTo(LocalDateRange.of("2020-01-01", "2020-01-05"));
        assertThat(dateRange1_20200101_20200105).isNotEqualTo(dateRange3_20200102_20200103);
        assertThat(dateRange1_20200101_20200105).isEqualTo(dateRange1_20200101_20200105);
        //noinspection AssertBetweenInconvertibleTypes
        assertThat(dateRange1_20200101_20200105).isNotEqualTo("123");

        assertThat(dateRange1_20200101_20200105.numOfDays()).isEqualTo(5);
        assertThat(dateRange1_20200101_20200105.toString()).isEqualTo("LocalDateRange[2020-01-01 ~ 2020-01-05 | days: 5]");
        assertThat(dateRange1_20200101_20200105.hashCode()).isEqualTo(Objects.hash(localDate_2020_01_01, localDate_2020_01_05));
        assertThat(dateRange1_20200101_20200105.getBeginDate()).isEqualTo(localDate_2020_01_01);
        assertThat(dateRange1_20200101_20200105.getEndDate()).isEqualTo(localDate_2020_01_05);
    }

}

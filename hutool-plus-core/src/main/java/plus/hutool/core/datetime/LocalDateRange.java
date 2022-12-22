package plus.hutool.core.datetime;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import plus.hutool.core.lang.Asserts;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * 日期范围
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings("JavadocDeclaration")
@Getter
public class LocalDateRange implements Serializable {
    private static final long serialVersionUID = -2146179868723523808L;

    private final LocalDate beginDate;
    private final LocalDate endDate;

    private LocalDateRange(LocalDate beginDate, LocalDate endDate) {
        this.beginDate = beginDate;
        this.endDate = endDate;
    }

    public static LocalDateRange of(LocalDate beginDate, LocalDate endDate) {
        Asserts.isTrue(!beginDate.isAfter(endDate), "开始日期不能大于结束日期");
        return new LocalDateRange(beginDate, endDate);
    }

    /**
     * 根据起止日期字符串创建日期范围对象
     *
     * @param beginDateStr 起始日期
     * @param endDateStr   结束日期
     * @return 日期范围对象
     */
    public static LocalDateRange of(String beginDateStr, String endDateStr) {
        LocalDate beginDate = DateTimeUtils.parseLocalDate(beginDateStr);
        LocalDate endDate = DateTimeUtils.parseLocalDate(endDateStr);
        return of(beginDate, endDate);
    }

    public boolean containsDate(LocalDate date) {
        return !(date.isBefore(this.beginDate) || date.isAfter(this.endDate));
    }

    public boolean containsDateRange(LocalDateRange dateRange) {
        return !this.beginDate.isAfter(dateRange.getBeginDate())
                && !this.endDate.isBefore(dateRange.getEndDate());
    }

    @Override
    public String toString() {
        String beginDateStr = beginDate.format(DateTimeFormatter.ISO_DATE);
        String endDateStr = endDate.format(DateTimeFormatter.ISO_DATE);
        return StrUtil.format("LocalDateRange[{} ~ {} | days: {}]", beginDateStr, endDateStr, numOfDays());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LocalDateRange)) {
            return false;
        }
        LocalDateRange that = (LocalDateRange) o;
        return this.beginDate.equals(that.beginDate) && this.endDate.equals(that.endDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beginDate, endDate);
    }

    public int numOfDays() {
        return (int) (ChronoUnit.DAYS.between(beginDate, endDate) + 1);
    }
}

package plus.hutool.core.measure.util;

import org.junit.jupiter.api.Test;
import tech.units.indriya.function.RationalNumber;

import java.math.BigInteger;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static plus.hutool.core.measure.util.FileSizeUtils.*;

class FileSizeUtilsTest {

    @Test
    void testNormalizeFileSizeToStringArray() {
        assertThat(FileSizeUtils.normalizeFileSizeToStrArray(123456789, BYTE)).isEqualTo(new String[]{"117.74", "MB"});
        assertThat(FileSizeUtils.normalizeFileSizeToStrArray(123456789, KB)).isEqualTo(new String[]{"117.74", "GB"});
        assertThat(FileSizeUtils.normalizeFileSizeToStrArray(123.45678, MB)).isEqualTo(new String[]{"123.46", "MB"});
        assertThat(FileSizeUtils.normalizeFileSizeToStrArray(0.0123456, PB)).isEqualTo(new String[]{"12.64", "TB"});
        assertThat(FileSizeUtils.normalizeFileSizeToStrArray(0.0123456, KB)).isEqualTo(new String[]{"13", "B"});
    }

    @Test
    void testNormalizeFileSizeToString() {
        assertThat(FileSizeUtils.normalizeFileSizeToStr(123456789, KB)).isEqualTo("117.74 GB");
        assertThat(FileSizeUtils.normalizeFileSizeToStr(123.45678, MB)).isEqualTo("123.46 MB");
        assertThat(FileSizeUtils.normalizeFileSizeToStr(0.0123456, PB)).isEqualTo("12.64 TB");
        assertThat(FileSizeUtils.normalizeFileSizeToStr(0.0123456, KB)).isEqualTo("13 B");
    }

    @Test
    void testNormalizeFileSize() {
        assertThat(FileSizeUtils.normalizeFileSize(123456789, KB).getValue()).isEqualTo(
                RationalNumber.of(123456789, 1024 * 1024));
        assertThat(FileSizeUtils.normalizeFileSize(123456789, KB).getUnit()).isEqualTo(GB);

        assertThat(FileSizeUtils.normalizeFileSize(123.45678, MB).getValue()).isEqualTo(
                RationalNumber.of(Math.round(123.45678 * 1024 * 1024), 1048576));
        assertThat(FileSizeUtils.normalizeFileSize(123.45678, MB).getUnit()).isEqualTo(MB);

        assertThat(FileSizeUtils.normalizeFileSize(0.0123456, PB).getValue()).isEqualTo(
                RationalNumber.of(Math.round(0.0123456 * 1024 * 1024 * 1024 * 1024 * 1024), 1024L * 1024 * 1024 * 1024));
        assertThat(FileSizeUtils.normalizeFileSize(0.0123456, PB).getUnit()).isEqualTo(TB);

        assertThat(FileSizeUtils.normalizeFileSize(0.0123456, KB).getValue()).isEqualTo(13);
        assertThat(FileSizeUtils.normalizeFileSize(0.0123456, KB).getUnit()).isEqualTo(BYTE);

    }

    @Test
    void testConvertFileSize() {
        assertThat(FileSizeUtils.convertFileSize(1, KB, BYTE).getValue().intValue()).isEqualTo(1024);
        assertThat(FileSizeUtils.convertFileSize(1, MB, BYTE).getValue().intValue()).isEqualTo(1048576);
        assertThat(FileSizeUtils.convertFileSize(1, GB, BYTE).getValue().intValue()).isEqualTo(1073741824);
        assertThat(FileSizeUtils.convertFileSize(1, TB, BYTE).getValue().longValue()).isEqualTo(1099511627776L);
        assertThat(FileSizeUtils.convertFileSize(1, PB, BYTE).getValue().longValue()).isEqualTo(1125899906842624L);
        assertThat(FileSizeUtils.convertFileSize(1, EB, BYTE).getValue().longValue()).isEqualTo(1152921504606846976L);
        assertThat(FileSizeUtils.convertFileSize(1, ZB, BYTE).getValue()).isEqualTo(new BigInteger("1180591620717411303424"));
        assertThat(FileSizeUtils.convertFileSize(1, YB, BYTE).getValue()).isEqualTo(new BigInteger("1208925819614629174706176"));

        assertThat(FileSizeUtils.convertFileSize(1, BYTE, KB).getValue()).isEqualTo(RationalNumber.of(1, 1024));
        assertThat(FileSizeUtils.convertFileSize(1, KB, KB).getValue().intValue()).isEqualTo(1);
        assertThat(FileSizeUtils.convertFileSize(1, MB, KB).getValue().intValue()).isEqualTo(1024);
        assertThat(FileSizeUtils.convertFileSize(1, GB, KB).getValue().intValue()).isEqualTo(1048576);
        assertThat(FileSizeUtils.convertFileSize(1, TB, KB).getValue().intValue()).isEqualTo(1073741824);
        assertThat(FileSizeUtils.convertFileSize(1, PB, KB).getValue().longValue()).isEqualTo(1099511627776L);
        assertThat(FileSizeUtils.convertFileSize(1, EB, KB).getValue().longValue()).isEqualTo(1125899906842624L);
        assertThat(FileSizeUtils.convertFileSize(1, ZB, KB).getValue().longValue()).isEqualTo(1152921504606846976L);
        assertThat(FileSizeUtils.convertFileSize(1, YB, KB).getValue()).isEqualTo(new BigInteger("1180591620717411303424"));

        assertThat(FileSizeUtils.convertFileSize(1, BYTE, MB).getValue()).isEqualTo(RationalNumber.of(1, 1048576));
        assertThat(FileSizeUtils.convertFileSize(1, KB, MB).getValue()).isEqualTo(RationalNumber.of(1, 1024));
        assertThat(FileSizeUtils.convertFileSize(1, MB, MB).getValue().intValue()).isEqualTo(1);
        assertThat(FileSizeUtils.convertFileSize(1, GB, MB).getValue().intValue()).isEqualTo(1024);
        assertThat(FileSizeUtils.convertFileSize(1, TB, MB).getValue().intValue()).isEqualTo(1048576);
        assertThat(FileSizeUtils.convertFileSize(1, PB, MB).getValue().intValue()).isEqualTo(1073741824);
        assertThat(FileSizeUtils.convertFileSize(1, EB, MB).getValue().longValue()).isEqualTo(1099511627776L);
        assertThat(FileSizeUtils.convertFileSize(1, ZB, MB).getValue().longValue()).isEqualTo(1125899906842624L);
        assertThat(FileSizeUtils.convertFileSize(1, YB, MB).getValue().longValue()).isEqualTo(1152921504606846976L);

        assertThat(FileSizeUtils.convertFileSize(1, BYTE, GB).getValue()).isEqualTo(RationalNumber.of(1, 1073741824));
        assertThat(FileSizeUtils.convertFileSize(1, KB, GB).getValue()).isEqualTo(RationalNumber.of(1, 1048576));
        assertThat(FileSizeUtils.convertFileSize(1, MB, GB).getValue()).isEqualTo(RationalNumber.of(1, 1024));
        assertThat(FileSizeUtils.convertFileSize(1, GB, GB).getValue().intValue()).isEqualTo(1);
        assertThat(FileSizeUtils.convertFileSize(1, TB, GB).getValue().intValue()).isEqualTo(1024);
        assertThat(FileSizeUtils.convertFileSize(1, PB, GB).getValue().intValue()).isEqualTo(1048576);
        assertThat(FileSizeUtils.convertFileSize(1, EB, GB).getValue().intValue()).isEqualTo(1073741824);
        assertThat(FileSizeUtils.convertFileSize(1, ZB, GB).getValue().longValue()).isEqualTo(1099511627776L);
        assertThat(FileSizeUtils.convertFileSize(1, YB, GB).getValue().longValue()).isEqualTo(1125899906842624L);

        assertThat(FileSizeUtils.convertFileSize(1, BYTE, TB).getValue()).isEqualTo(RationalNumber.of(1, 1099511627776L));
        assertThat(FileSizeUtils.convertFileSize(1, KB, TB).getValue()).isEqualTo(RationalNumber.of(1, 1073741824));
        assertThat(FileSizeUtils.convertFileSize(1, MB, TB).getValue()).isEqualTo(RationalNumber.of(1, 1048576));
        assertThat(FileSizeUtils.convertFileSize(1, GB, TB).getValue()).isEqualTo(RationalNumber.of(1, 1024));
        assertThat(FileSizeUtils.convertFileSize(1, TB, TB).getValue().intValue()).isEqualTo(1);
        assertThat(FileSizeUtils.convertFileSize(1, PB, TB).getValue().intValue()).isEqualTo(1024);
        assertThat(FileSizeUtils.convertFileSize(1, EB, TB).getValue().intValue()).isEqualTo(1048576);
        assertThat(FileSizeUtils.convertFileSize(1, ZB, TB).getValue().intValue()).isEqualTo(1073741824);
        assertThat(FileSizeUtils.convertFileSize(1, YB, TB).getValue().longValue()).isEqualTo(1099511627776L);

        assertThat(FileSizeUtils.convertFileSize(1, BYTE, PB).getValue()).isEqualTo(RationalNumber.of(1, 1125899906842624L));
        assertThat(FileSizeUtils.convertFileSize(1, KB, PB).getValue()).isEqualTo(RationalNumber.of(1, 1099511627776L));
        assertThat(FileSizeUtils.convertFileSize(1, MB, PB).getValue()).isEqualTo(RationalNumber.of(1, 1073741824));
        assertThat(FileSizeUtils.convertFileSize(1, GB, PB).getValue()).isEqualTo(RationalNumber.of(1, 1048576));
        assertThat(FileSizeUtils.convertFileSize(1, TB, PB).getValue()).isEqualTo(RationalNumber.of(1, 1024));
        assertThat(FileSizeUtils.convertFileSize(1, PB, PB).getValue().intValue()).isEqualTo(1);
        assertThat(FileSizeUtils.convertFileSize(1, EB, PB).getValue().intValue()).isEqualTo(1024);
        assertThat(FileSizeUtils.convertFileSize(1, ZB, PB).getValue().intValue()).isEqualTo(1048576);
        assertThat(FileSizeUtils.convertFileSize(1, YB, PB).getValue().intValue()).isEqualTo(1073741824);

        assertThat(FileSizeUtils.convertFileSize(1, BYTE, EB).getValue()).isEqualTo(RationalNumber.of(1, 1152921504606846976L));
        assertThat(FileSizeUtils.convertFileSize(1, KB, EB).getValue()).isEqualTo(RationalNumber.of(1, 1125899906842624L));
        assertThat(FileSizeUtils.convertFileSize(1, MB, EB).getValue()).isEqualTo(RationalNumber.of(1, 1099511627776L));
        assertThat(FileSizeUtils.convertFileSize(1, GB, EB).getValue()).isEqualTo(RationalNumber.of(1, 1073741824));
        assertThat(FileSizeUtils.convertFileSize(1, TB, EB).getValue()).isEqualTo(RationalNumber.of(1, 1048576));
        assertThat(FileSizeUtils.convertFileSize(1, PB, EB).getValue()).isEqualTo(RationalNumber.of(1, 1024));
        assertThat(FileSizeUtils.convertFileSize(1, EB, EB).getValue().intValue()).isEqualTo(1);
        assertThat(FileSizeUtils.convertFileSize(1, ZB, EB).getValue().intValue()).isEqualTo(1024);
        assertThat(FileSizeUtils.convertFileSize(1, YB, EB).getValue().intValue()).isEqualTo(1048576);

        assertThat(FileSizeUtils.convertFileSize(1, BYTE, ZB).getValue()).isEqualTo(
                RationalNumber.of(BigInteger.ONE, new BigInteger("1180591620717411303424")));
        assertThat(FileSizeUtils.convertFileSize(1, KB, ZB).getValue()).isEqualTo(RationalNumber.of(1, 1152921504606846976L));
        assertThat(FileSizeUtils.convertFileSize(1, MB, ZB).getValue()).isEqualTo(RationalNumber.of(1, 1125899906842624L));
        assertThat(FileSizeUtils.convertFileSize(1, GB, ZB).getValue()).isEqualTo(RationalNumber.of(1, 1099511627776L));
        assertThat(FileSizeUtils.convertFileSize(1, TB, ZB).getValue()).isEqualTo(RationalNumber.of(1, 1073741824));
        assertThat(FileSizeUtils.convertFileSize(1, PB, ZB).getValue()).isEqualTo(RationalNumber.of(1, 1048576));
        assertThat(FileSizeUtils.convertFileSize(1, EB, ZB).getValue()).isEqualTo(RationalNumber.of(1, 1024));
        assertThat(FileSizeUtils.convertFileSize(1, ZB, ZB).getValue().intValue()).isEqualTo(1);
        assertThat(FileSizeUtils.convertFileSize(1, YB, ZB).getValue().intValue()).isEqualTo(1024);

        assertThat(FileSizeUtils.convertFileSize(1, BYTE, YB).getValue()).isEqualTo(
                RationalNumber.of(BigInteger.ONE, new BigInteger("1208925819614629174706176")));
        assertThat(FileSizeUtils.convertFileSize(1, KB, YB).getValue()).isEqualTo(
                RationalNumber.of(BigInteger.ONE, new BigInteger("1180591620717411303424")));
        assertThat(FileSizeUtils.convertFileSize(1, MB, YB).getValue()).isEqualTo(RationalNumber.of(1, 1152921504606846976L));
        assertThat(FileSizeUtils.convertFileSize(1, GB, YB).getValue()).isEqualTo(RationalNumber.of(1, 1125899906842624L));
        assertThat(FileSizeUtils.convertFileSize(1, TB, YB).getValue()).isEqualTo(RationalNumber.of(1, 1099511627776L));
        assertThat(FileSizeUtils.convertFileSize(1, PB, YB).getValue()).isEqualTo(RationalNumber.of(1, 1073741824));
        assertThat(FileSizeUtils.convertFileSize(1, EB, YB).getValue()).isEqualTo(RationalNumber.of(1, 1048576));
        assertThat(FileSizeUtils.convertFileSize(1, ZB, YB).getValue()).isEqualTo(RationalNumber.of(1, 1024));
        assertThat(FileSizeUtils.convertFileSize(1, YB, YB).getValue().intValue()).isEqualTo(1);
    }

    @Test
    void testConvertFileSizeValueToSpecifiedUnit() {
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(1, BYTE, BYTE, 2).doubleValue()).isEqualTo(1);
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(500, BYTE, KB, 2).doubleValue()).isEqualTo(0.49);
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(50000, BYTE, KB, 2).doubleValue()).isEqualTo(48.83);

        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(5000, KB, MB, 0).doubleValue()).isEqualTo(5);
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(5000, KB, MB, 1).doubleValue()).isEqualTo(4.9);
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(5000, KB, MB, 2).doubleValue()).isEqualTo(4.88);
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(5000, KB, GB, 4).doubleValue()).isEqualTo(0.0048);

        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(500, MB, GB, 2).doubleValue()).isEqualTo(0.49);
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(500, MB, GB, 3).doubleValue()).isEqualTo(0.488);
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(500, GB, TB, 2).doubleValue()).isEqualTo(0.49);
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(500, GB, TB, 3).doubleValue()).isEqualTo(0.488);
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(500, TB, PB, 2).doubleValue()).isEqualTo(0.49);
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(500, TB, PB, 3).doubleValue()).isEqualTo(0.488);
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(500, PB, EB, 2).doubleValue()).isEqualTo(0.49);
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(500, PB, EB, 3).doubleValue()).isEqualTo(0.488);
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(500, EB, ZB, 2).doubleValue()).isEqualTo(0.49);
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(500, EB, ZB, 3).doubleValue()).isEqualTo(0.488);
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(500, ZB, YB, 2).doubleValue()).isEqualTo(0.49);
        assertThat(FileSizeUtils.convertFileSizeValueToSpecifiedUnit(500, ZB, YB, 3).doubleValue()).isEqualTo(0.488);
    }

    @Test
    void testConvertFileSizeToStr() {
        assertThat(FileSizeUtils.convertFileSizeToStr(500, BYTE, KB)).isEqualTo("0.4883 KB");
        assertThat(FileSizeUtils.convertFileSizeToStr(50000, BYTE, KB)).isEqualTo("48.83 KB");

        assertThat(FileSizeUtils.convertFileSizeToStr(5000, KB, MB)).isEqualTo("4.88 MB");
        assertThat(FileSizeUtils.convertFileSizeToStr(5000, KB, GB)).isEqualTo("0.0048 GB");

        assertThat(FileSizeUtils.convertFileSizeToStr(500, MB, GB)).isEqualTo("0.4883 GB");
        assertThat(FileSizeUtils.convertFileSizeToStr(900, MB, GB)).isEqualTo("0.8789 GB");
        assertThat(FileSizeUtils.convertFileSizeToStr(2000, MB, GB)).isEqualTo("1.95 GB");

        assertThat(FileSizeUtils.convertFileSizeToStr(500, GB, TB)).isEqualTo("0.4883 TB");
        assertThat(FileSizeUtils.convertFileSizeToStr(900, GB, TB)).isEqualTo("0.8789 TB");
        assertThat(FileSizeUtils.convertFileSizeToStr(2000, GB, TB)).isEqualTo("1.95 TB");

        assertThat(FileSizeUtils.convertFileSizeToStr(500, TB, PB)).isEqualTo("0.4883 PB");
        assertThat(FileSizeUtils.convertFileSizeToStr(900, TB, PB)).isEqualTo("0.8789 PB");
        assertThat(FileSizeUtils.convertFileSizeToStr(2000, TB, PB)).isEqualTo("1.95 PB");

        assertThat(FileSizeUtils.convertFileSizeToStr(500, PB, EB)).isEqualTo("0.4883 EB");
        assertThat(FileSizeUtils.convertFileSizeToStr(900, PB, EB)).isEqualTo("0.8789 EB");
        assertThat(FileSizeUtils.convertFileSizeToStr(2000, PB, EB)).isEqualTo("1.95 EB");

        assertThat(FileSizeUtils.convertFileSizeToStr(500, EB, ZB)).isEqualTo("0.4883 ZB");
        assertThat(FileSizeUtils.convertFileSizeToStr(900, EB, ZB)).isEqualTo("0.8789 ZB");
        assertThat(FileSizeUtils.convertFileSizeToStr(2000, EB, ZB)).isEqualTo("1.95 ZB");

        assertThat(FileSizeUtils.convertFileSizeToStr(500, ZB, YB)).isEqualTo("0.4883 YB");
        assertThat(FileSizeUtils.convertFileSizeToStr(900, ZB, YB)).isEqualTo("0.8789 YB");
        assertThat(FileSizeUtils.convertFileSizeToStr(2000, ZB, YB)).isEqualTo("1.95 YB");
        assertThat(FileSizeUtils.convertFileSizeToStr(20000, ZB, YB)).isEqualTo("19.53 YB");
    }

    @Test
    void testFullFileSizeUnitList() {
        assertThat(FileSizeUtils.fullFileSizeUnitList())
                .hasSize(9)
                .hasSameElementsAs(Arrays.asList(BYTE, KB, MB, GB, TB, PB, EB, ZB, YB))
                .isUnmodifiable();
    }

    @Test
    void testFullFileSizeUnitMap() {
        assertThat(FileSizeUtils.fullFileSizeUnitMap())
                .hasSize(9)
                .containsKeys("B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB")
                .containsValues(BYTE, KB, MB, GB, TB, PB, EB, ZB, YB);
        assertThat(FileSizeUtils.fullFileSizeUnitMap().values()).isUnmodifiable();
    }

    @Test
    void testFullFileSizeUnitSymbolArray() {
        assertThat(FileSizeUtils.fullFileSizeUnitSymbolArray())
                .isEqualTo(new String[]{"B", "KB", "MB", "GB", "TB", "PB", "EB", "ZB", "YB"});
    }

    @Test
    void testGetFileSizeUnitBySymbol() {
        assertThat(FileSizeUtils.getFileSizeUnitBySymbol("B")).isEqualTo(FileSizeUtils.BYTE);
        assertThat(FileSizeUtils.getFileSizeUnitBySymbol("KB")).isEqualTo(FileSizeUtils.KB);
        assertThat(FileSizeUtils.getFileSizeUnitBySymbol("MB")).isEqualTo(FileSizeUtils.MB);
        assertThat(FileSizeUtils.getFileSizeUnitBySymbol("GB")).isEqualTo(FileSizeUtils.GB);
        assertThat(FileSizeUtils.getFileSizeUnitBySymbol("TB")).isEqualTo(FileSizeUtils.TB);
        assertThat(FileSizeUtils.getFileSizeUnitBySymbol("PB")).isEqualTo(FileSizeUtils.PB);
        assertThat(FileSizeUtils.getFileSizeUnitBySymbol("EB")).isEqualTo(FileSizeUtils.EB);
        assertThat(FileSizeUtils.getFileSizeUnitBySymbol("ZB")).isEqualTo(FileSizeUtils.ZB);
        assertThat(FileSizeUtils.getFileSizeUnitBySymbol("YB")).isEqualTo(FileSizeUtils.YB);
    }

    @Test
    void testResolveAppropriateFileSizeUnitFromBytes() {
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(123)).isEqualTo(BYTE);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(1023)).isEqualTo(BYTE);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(1024)).isEqualTo(KB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(1048575)).isEqualTo(KB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(1048576)).isEqualTo(MB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(1073741823)).isEqualTo(MB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(1073741824)).isEqualTo(GB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(1099511627775L)).isEqualTo(GB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(1099511627776L)).isEqualTo(TB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(1125899906842623L)).isEqualTo(TB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(1125899906842624L)).isEqualTo(PB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(1152921504606846975L)).isEqualTo(PB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(1152921504606846976L)).isEqualTo(EB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(new BigInteger("1180591620717411303423"))).isEqualTo(EB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(new BigInteger("1180591620717411303424"))).isEqualTo(ZB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(new BigInteger("1208925819614629174706175"))).isEqualTo(ZB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(new BigInteger("1208925819614629174706176"))).isEqualTo(YB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(new BigInteger("1237940039285380274899124223"))).isEqualTo(YB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(new BigInteger("1237940039285380274899124224"))).isEqualTo(YB);
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitFromBytes(new BigInteger("12379400392853802748991242240000"))).isEqualTo(YB);
    }

    @Test
    void testResolveAppropriateFileSizeUnitSymbolFromBytes() {
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(123)).isEqualTo("B");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(1023)).isEqualTo("B");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(1024)).isEqualTo("KB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(1048575)).isEqualTo("KB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(1048576)).isEqualTo("MB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(1073741823)).isEqualTo("MB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(1073741824)).isEqualTo("GB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(1099511627775L)).isEqualTo("GB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(1099511627776L)).isEqualTo("TB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(1125899906842623L)).isEqualTo("TB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(1125899906842624L)).isEqualTo("PB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(1152921504606846975L)).isEqualTo("PB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(1152921504606846976L)).isEqualTo("EB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(new BigInteger("1180591620717411303423"))).isEqualTo("EB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(new BigInteger("1180591620717411303424"))).isEqualTo("ZB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(new BigInteger("1208925819614629174706175"))).isEqualTo("ZB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(new BigInteger("1208925819614629174706176"))).isEqualTo("YB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(new BigInteger("1237940039285380274899124223"))).isEqualTo("YB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(new BigInteger("1237940039285380274899124224"))).isEqualTo("YB");
        assertThat(FileSizeUtils.resolveAppropriateFileSizeUnitSymbolFromBytes(new BigInteger("12379400392853802748991242240000"))).isEqualTo("YB");
    }
}

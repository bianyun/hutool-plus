package plus.hutool.core.text.string;

import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;

class StrUtilsTest {

    @Test
    void testToUpperCase() {
        assertThat(StrUtils.toUpperCase(null)).isNull();
        assertThat(StrUtils.toUpperCase("")).isEqualTo("");
        assertThat(StrUtils.toUpperCase("abc")).isEqualTo("ABC");
        assertThat(StrUtils.toUpperCase("Abc")).isEqualTo("ABC");
        assertThat(StrUtils.toUpperCase("ABC")).isEqualTo("ABC");
    }

    @Test
    void testToLowerCase() {
        assertThat(StrUtils.toLowerCase(null)).isNull();
        assertThat(StrUtils.toLowerCase("")).isEqualTo("");
        assertThat(StrUtils.toLowerCase("abc")).isEqualTo("abc");
        assertThat(StrUtils.toLowerCase("Abc")).isEqualTo("abc");
        assertThat(StrUtils.toLowerCase("ABC")).isEqualTo("abc");
    }

    @Test
    void testIsNeitherBlankNorLiterallyNull() {
        assertThat(StrUtils.isNeitherBlankNorLiterallyNull(null)).isFalse();
        assertThat(StrUtils.isNeitherBlankNorLiterallyNull("")).isFalse();
        assertThat(StrUtils.isNeitherBlankNorLiterallyNull("   ")).isFalse();
        assertThat(StrUtils.isNeitherBlankNorLiterallyNull("\t  ")).isFalse();
        assertThat(StrUtils.isNeitherBlankNorLiterallyNull("\n  ")).isFalse();
        assertThat(StrUtils.isNeitherBlankNorLiterallyNull("null")).isFalse();
        assertThat(StrUtils.isNeitherBlankNorLiterallyNull("Null")).isFalse();
        assertThat(StrUtils.isNeitherBlankNorLiterallyNull("NULL")).isFalse();

        assertThat(StrUtils.isNeitherBlankNorLiterallyNull("nul")).isTrue();
    }

    @Test
    void testIsLiterallyNull() {
        assertThat(StrUtils.isLiterallyNull(null)).isFalse();
        assertThat(StrUtils.isLiterallyNull("null")).isTrue();
        assertThat(StrUtils.isLiterallyNull("Null")).isTrue();
        assertThat(StrUtils.isLiterallyNull("NULL")).isTrue();
    }

    @Test
    void testIsNotLiterallyNull() {
        assertThat(StrUtils.isNotLiterallyNull(null)).isTrue();
        assertThat(StrUtils.isNotLiterallyNull("")).isTrue();
        assertThat(StrUtils.isNotLiterallyNull("null")).isFalse();
        assertThat(StrUtils.isNotLiterallyNull("Null")).isFalse();
        assertThat(StrUtils.isNotLiterallyNull("NULL")).isFalse();
    }

    @Test
    void testIsAllBlank() {
        assertThat(StrUtils.isAllBlank(Arrays.asList("", "\t", "\n", "  \t  \n  "))).isTrue();
        assertThat(StrUtils.isAllBlank(Arrays.asList("", "\t", "\n", "  \t  \n  ", "null"))).isFalse();
    }

    @Test
    void testIsNotAllBlank() {
        assertThat(StrUtils.isNotAllBlank(Arrays.asList("", "\t", "\n", "  \t  \n  "))).isFalse();
        assertThat(StrUtils.isNotAllBlank(Arrays.asList("", "\t", "\n", "  \t  \n  ", "null"))).isTrue();
    }

    @Test
    void testIsAnyBlank1() {
        assertThat(StrUtils.isAnyBlank(Arrays.asList("", "\t", "\n", "  \t  \n  "))).isTrue();
        assertThat(StrUtils.isAnyBlank(Arrays.asList("", "\t", "\n", "  \t  \n  ", "null"))).isTrue();
        assertThat(StrUtils.isAnyBlank(Arrays.asList("abc", "null"))).isFalse();
    }

    @Test
    void testIsAnyBlank2() {
        assertThat(StrUtils.isAnyBlank()).isTrue();
        assertThat(StrUtils.isAnyBlank(StrUtils.NUL)).isTrue();
        assertThat(StrUtils.isAnyBlank("", "\t", "\n", "  \t  \n  ")).isTrue();
        assertThat(StrUtils.isAnyBlank("", "\t", "\n", "  \t  \n  ", "null")).isTrue();
        assertThat(StrUtils.isAnyBlank("abc", "null")).isFalse();
    }

    @Test
    void testReplaceAllWhiteSpacesToOneSpace() {
        assertThat(StrUtils.replaceAllWhiteSpacesToOneSpace("hello  \t   world  \n")).isEqualTo("hello world ");
        assertThat(StrUtils.replaceAllWhiteSpacesToOneSpace("  hello  \t   world  \n  ")).isEqualTo(" hello world ");
    }

    @Test
    void testContainsAny() {
        assertThat(StrUtils.containsAny("hello  \t   world  \n", Arrays.asList("hello", "\n", "\t"))).isTrue();
        assertThat(StrUtils.containsAny("hello  \t   world  \n", Arrays.asList("hello1", "\\n", "\\t"))).isFalse();
    }

    @Test
    void testJoin() {
        assertThat(StrUtils.join("^_^", Collections.emptyList())).isEqualTo("");
        assertThat(StrUtils.join("^_^", Arrays.asList("hello", "world"))).isEqualTo("hello^_^world");
    }

    @Test
    void testPartiallyDesensitize() {
        assertThat(StrUtils.partiallyDesensitize("", 2)).isEqualTo("");
        assertThat(StrUtils.partiallyDesensitize("\t", 2)).isEqualTo("\t");
        assertThat(StrUtils.partiallyDesensitize("420583199510101234", 2)).isEqualTo("4****************4");
        assertThat(StrUtils.partiallyDesensitize("420583199510101234", 3)).isEqualTo("42***************4");
        assertThat(StrUtils.partiallyDesensitize("420583199510101234", 4)).isEqualTo("42**************34");
        assertThat(StrUtils.partiallyDesensitize("420583199510101234", 5)).isEqualTo("420*************34");
        assertThat(StrUtils.partiallyDesensitize("420583199510101234", 6)).isEqualTo("420************234");
        assertThat(StrUtils.partiallyDesensitize("420583199510101234", 7)).isEqualTo("4205***********234");
        assertThat(StrUtils.partiallyDesensitize("420583199510101234", 8)).isEqualTo("4205**********1234");
        assertThat(StrUtils.partiallyDesensitize("420583199510101234", 9)).isEqualTo("42058*********1234");
        assertThat(StrUtils.partiallyDesensitize("420583199510101234", 10)).isEqualTo("42058*********1234");
        assertThat(StrUtils.partiallyDesensitize("420583199510101234", 11)).isEqualTo("42058*********1234");
        assertThat(StrUtils.partiallyDesensitize("420583199510101234", 12)).isEqualTo("42058*********1234");
    }

    @Test
    void testDesensitizeMiddlePart() {
        assertThat(StrUtils.desensitizeMiddlePart("420583199510101234")).isEqualTo("42058*********1234");
        assertThat(StrUtils.desensitizeMiddlePart("4205831995101012345")).isEqualTo("42058**********2345");
        assertThat(StrUtils.desensitizeMiddlePart("42058319951010123456")).isEqualTo("42058**********23456");
        assertThat(StrUtils.desensitizeMiddlePart("12345678901234567890123456789012")).isEqualTo("12345678****************56789012");
        assertThat(StrUtils.desensitizeMiddlePart("123456789012345678901234567890123456")).isEqualTo("12345678********************90123456");
    }

    @Test
    void testLenOfHansStr() {
        assertThat(StrUtils.lenOfHansStr("hello")).isEqualTo(5);
        assertThat(StrUtils.lenOfHansStr("hello你好")).isEqualTo(9);
        assertThat(StrUtils.lenOfHansStr("早上好")).isEqualTo(6);
    }

    @Test
    void testMaxLenOfHansStr() {
        assertThat(StrUtils.maxLenOfHansStr(Arrays.asList("hello", "hello, 你好", "大家早上好"))).isEqualTo(11);
    }

    @Test
    void testRemoveWhiteSpacesAroundDelimiters() {
        assertThat(StrUtils.removeWhiteSpacesAroundDelimiters("2022 - 11  - 29 13 :  12: 25", "-", ":"))
                .isEqualTo("2022-11-29 13:12:25");
        assertThat(StrUtils.removeWhiteSpacesAroundDelimiters("1  , 2, 3.  4, 5", ",")).isEqualTo("1,2,3.  4,5");
        assertThat(StrUtils.removeWhiteSpacesAroundDelimiters("1  , 2, 3.  4, 5", ",", ".")).isEqualTo("1,2,3.4,5");
    }

    @Test
    void testAddPrefixIfPredicateSatisfied() {
        final Predicate<CharSequence> predicate =
                str -> !StrUtil.startWithAny(str, StrUtils.SPACE, StrUtils.CHN_OPEN_BRACKET);

        assertThat(StrUtils.addPrefixIfPredicateSatisfied("标题", " ", predicate)).isEqualTo(" 标题");
        assertThat(StrUtils.addPrefixIfPredicateSatisfied("【标题", " ", predicate)).isEqualTo("【标题");
        assertThat(StrUtils.addPrefixIfPredicateSatisfied(" 标题", " ", predicate)).isEqualTo(" 标题");
    }

    @Test
    void testAddSuffixIfPredicateSatisfied() {
        final Predicate<CharSequence> predicate =
                str -> !StrUtil.endWithAny(str, StrUtils.SPACE, StrUtils.CHN_CLOSE_BRACKET);

        assertThat(StrUtils.addSuffixIfPredicateSatisfied("末尾", " ", predicate)).isEqualTo("末尾 ");
        assertThat(StrUtils.addSuffixIfPredicateSatisfied("末尾】", " ", predicate)).isEqualTo("末尾】");
        assertThat(StrUtils.addSuffixIfPredicateSatisfied("末尾 ", " ", predicate)).isEqualTo("末尾 ");
    }
}

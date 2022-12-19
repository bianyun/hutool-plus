package plus.hutool.core.text.regex;

import org.junit.jupiter.api.Test;
import plus.hutool.core.text.string.StrUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@SuppressWarnings("SpellCheckingInspection")
class ReUtilsTest {

    private static final String[] STR_ARRAY = new String[] {
            "一、概述",
            "二、总体方案",
            "三、详细设计",
            "1.1 小节1.1",
            "1.2 小节1.2",
            "2.11 小节2.11"
    };

    private static final List<String> LINES = Arrays.asList(STR_ARRAY);

    @Test
    void testBuildPatternList() {
        assertThat(ReUtils.buildPatternList()).isEqualTo(Collections.emptyList());

        final List<Pattern> patterns = ReUtils.buildPatternList("(hello)(.*)", "([hH]ello world)(.*)", ".+(hutool plus)");
        assertThat(patterns).hasSize(3);
        assertThat(patterns.get(0).pattern()).isEqualTo("(hello)(.*)");
        assertThat(patterns.get(1).pattern()).isEqualTo("([hH]ello world)(.*)");
        assertThat(patterns.get(2).pattern()).isEqualTo(".+(hutool plus)");
    }

    @Test
    void testBuildPatternListByWrapStr() {
        List<Pattern> patterns = ReUtils.buildPatternListByWrapStr("prefix", "suffix", "[regexes1]", "[regexes2]");
        assertThat(patterns).hasSize(2);
        assertThat(patterns.get(0).pattern()).isEqualTo("prefix[regexes1]suffix");
        assertThat(patterns.get(1).pattern()).isEqualTo("prefix[regexes2]suffix");
    }

    @Test
    void testBuildPatternWithWrapStr() {
        assertThat(ReUtils.buildPatternWithWrapStr("prefix", "suffix", "[regexes]").pattern())
                .isEqualTo("prefix[regexes]suffix");
    }

    @Test
    void testExtractByFirstMatchedPattern1() {
        List<Pattern> patterns = ReUtils.buildPatternList("(hello).*", "(hello world).*", ".+(hutool plus)");

        assertThat(ReUtils.extractByFirstMatchedPattern(patterns, "Hello world")).isEqualTo(StrUtils.EMPTY);
        assertThat(ReUtils.extractByFirstMatchedPattern(patterns, "hello world")).isEqualTo("hello");
        assertThat(ReUtils.extractByFirstMatchedPattern(patterns, "Hello world, hutool plus")).isEqualTo("hutool plus");
        assertThat(ReUtils.extractByFirstMatchedPattern(patterns, "hutool plus")).isEqualTo(StrUtils.EMPTY);
    }

    @Test
    void testExtractByFirstMatchedPattern2() {
        List<Pattern> patterns = ReUtils.buildPatternList("(hello)(.*)", "([hH]ello world)(.*)", ".+(hutool plus)");

        assertThat(ReUtils.extractByFirstMatchedPattern(patterns, "hello world, hutool plus", "$1")).isEqualTo("hello");
        assertThat(ReUtils.extractByFirstMatchedPattern(patterns, "hello world, hutool plus", "$2")).isEqualTo(" world, hutool plus");
        assertThat(ReUtils.extractByFirstMatchedPattern(patterns, "Hello world, hutool plus", "$1")).isEqualTo("Hello world");
        assertThat(ReUtils.extractByFirstMatchedPattern(patterns, "Hello world, hutool plus", "$2")).isEqualTo(", hutool plus");
        assertThat(ReUtils.extractByFirstMatchedPattern(patterns, "Hello world, hutool plus", "$1-$2")).isEqualTo("Hello world-, hutool plus");
        assertThat(ReUtils.extractByFirstMatchedPattern(patterns, "hutool plus", "$1")).isEqualTo(StrUtils.EMPTY);
    }

    @Test
    void testExtractFirstGroup() {
        assertThat(ReUtils.extractFirstGroup(Pattern.compile("([hH]ello world)(.*)"), "Hello world, hutool plus"))
                .isEqualTo("Hello world");
        assertThat(ReUtils.extractFirstGroup(Pattern.compile("[hH]ello world(.*)"), "Hello world, hutool plus"))
                .isEqualTo(", hutool plus");
    }

    @Test
    void testExtractBySpecifiedGroupNumber() {
        assertThat(ReUtils.extractBySpecifiedGroupNumber(
                Pattern.compile("([hH]ello world)(.*)"), "Hello world, hutool plus", 1)).isEqualTo("Hello world");
        assertThat(ReUtils.extractBySpecifiedGroupNumber(
                Pattern.compile("([hH]ello world)(.*)"), "Hello world, hutool plus", 2)).isEqualTo(", hutool plus");
    }

    @Test
    void testParseArrayItemsToMap() {
        Pattern pattern1 = Pattern.compile("([一二三])、(.*)");
        Pattern pattern2 = Pattern.compile("(\\d+[.]\\d+)\\s+(.*)");

        assertThat(ReUtils.parseArrayItemsToMap(STR_ARRAY, pattern1)).hasSize(3);
        assertThat(ReUtils.parseArrayItemsToMap(STR_ARRAY, pattern1)).containsKeys("一", "二", "三");
        assertThat(ReUtils.parseArrayItemsToMap(STR_ARRAY, pattern1)).containsValues("概述", "总体方案", "详细设计");

        assertThat(ReUtils.parseArrayItemsToMap(STR_ARRAY, pattern2)).hasSize(3);
        assertThat(ReUtils.parseArrayItemsToMap(STR_ARRAY, pattern2)).containsKeys("1.1", "1.2", "2.11");
        assertThat(ReUtils.parseArrayItemsToMap(STR_ARRAY, pattern2)).containsValues("小节1.1", "小节1.2", "小节2.11");
    }

    @Test
    void testConvertAllMatchesToMap() {
        final String str = "[一、概述] aaa;; \n[二、总体方案] bbb;; \n[三、详细设计] ccc;; ";

        final Map<String, String> result = ReUtils.convertAllMatchesToMap(str, Pattern.compile("\\[([一二三])、(.*)]"));
        assertThat(result).hasSize(3);
        assertThat(result).containsKeys("一", "二", "三");
        assertThat(result).containsValues("概述", "总体方案", "详细设计");
    }

    @Test
    void testIndexOfFirstMatchedLine1() {
        assertThat(ReUtils.indexOfFirstMatchedLine(LINES, Pattern.compile("([一二三])、(.*)"))).isEqualTo(0);
        assertThat(ReUtils.indexOfFirstMatchedLine(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"))).isEqualTo(3);
    }

    @Test
    void testIndexOfFirstMatchedLine2() {
        assertThat(ReUtils.indexOfFirstMatchedLine(LINES, Pattern.compile("([一二三])、(.*)"), 1)).isEqualTo(1);
        assertThat(ReUtils.indexOfFirstMatchedLine(LINES, Pattern.compile("([一二三])、(.*)"), 3)).isEqualTo(-1);
        assertThat(ReUtils.indexOfFirstMatchedLine(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 4)).isEqualTo(4);
    }

    @Test
    void testIndexOfFirstMatchedLine3() {
        assertThat(ReUtils.indexOfFirstMatchedLine(LINES, Pattern.compile("([一二三])、(.*)"), 1, 2)).isEqualTo(1);
        assertThat(ReUtils.indexOfFirstMatchedLine(LINES, Pattern.compile("([一二三])、(.*)"), 3, 4)).isEqualTo(-1);
        assertThat(ReUtils.indexOfFirstMatchedLine(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 4, 4)).isEqualTo(-1);
        assertThat(ReUtils.indexOfFirstMatchedLine(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 4, 5)).isEqualTo(4);
        assertThat(ReUtils.indexOfFirstMatchedLine(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 5, 6)).isEqualTo(5);
    }

    @Test
    void testIndexesOfAllMatchedLines1() {
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("([一二三])、(.*)")))
                .hasSize(3).contains(0, 1, 2);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)")))
                .hasSize(3).contains(3, 4, 5);
    }

    @Test
    void testIndexesOfAllMatchedLines2() {
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("([一二三])、(.*)"), 0))
                .hasSize(3).contains(0, 1, 2);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("([一二三])、(.*)"), 1))
                .hasSize(2).contains(1, 2);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("([一二三])、(.*)"), 2))
                .hasSize(1).contains(2);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("([一二三])、(.*)"), 3)).isEmpty();

        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 0))
                .hasSize(3).contains(3, 4, 5);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 1))
                .hasSize(3).contains(3, 4, 5);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 2))
                .hasSize(3).contains(3, 4, 5);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 3))
                .hasSize(3).contains(3, 4, 5);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 4))
                .hasSize(2).contains(4, 5);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 5))
                .hasSize(1).contains(5);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 6)).isEmpty();
    }

    @Test
    void testIndexesOfAllMatchedLines3() {
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("([一二三])、(.*)"), 0, 1))
                .hasSize(1).contains(0);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("([一二三])、(.*)"), 0, 2))
                .hasSize(2).contains(0, 1);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("([一二三])、(.*)"), 0, 3))
                .hasSize(3).contains(0, 1, 2);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("([一二三])、(.*)"), 1, 2))
                .hasSize(1).contains(1);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("([一二三])、(.*)"), 1, 3))
                .hasSize(2).contains(1, 2);

        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("([一二三])、(.*)"), 0, 5))
                .hasSize(3).contains(0, 1, 2);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("([一二三])、(.*)"), 1, 5))
                .hasSize(2).contains(1, 2);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("([一二三])、(.*)"), 2, 5))
                .hasSize(1).contains(2);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("([一二三])、(.*)"), 3, 5)).isEmpty();

        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 0))
                .hasSize(3).contains(3, 4, 5);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 1))
                .hasSize(3).contains(3, 4, 5);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 2))
                .hasSize(3).contains(3, 4, 5);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 3))
                .hasSize(3).contains(3, 4, 5);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 4))
                .hasSize(2).contains(4, 5);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 5))
                .hasSize(1).contains(5);
        assertThat(ReUtils.indexesOfAllMatchedLines(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)"), 6)).isEmpty();
    }

    @Test
    void testGetMatchedLines() {
        assertThat(ReUtils.getMatchedLines(LINES, Pattern.compile("regex"))).isEqualTo(Collections.emptyList());

        assertThat(ReUtils.getMatchedLines(LINES, Pattern.compile("([一二三])、(.*)")))
                .hasSize(3).contains("一、概述", "二、总体方案", "三、详细设计");
        assertThat(ReUtils.getMatchedLines(LINES, Pattern.compile("(\\d+[.]\\d+)\\s+(.*)")))
                .hasSize(3).contains("1.1 小节1.1", "1.2 小节1.2", "2.11 小节2.11");
    }
}

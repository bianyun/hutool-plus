package plus.hutool.core.text.regex;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import plus.hutool.core.text.string.StrUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 正则表达式工具类
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class ReUtils {

    private static final int INDEX_NOT_FOUND = -1;

    /**
     * 正则表达式匹配的分组1
     */
    public static final String REGEX_GROUP_1 = "$1";

    /**
     * 正则表达式匹配的分组2
     */
    public static final String REGEX_GROUP_2 = "$2";

    private ReUtils() {}

    /**
     * 根据正则表达式列表构建 正则 {@link Pattern} 列表
     *
     * @param regexes 正则表达式列表
     * @return 正则表达式 {@link Pattern} 列表
     */
    public static List<Pattern> buildPatternList(String... regexes) {
        List<Pattern> resultList = new ArrayList<>();
        for (String regex : regexes) {
            resultList.add(Pattern.compile(regex));
        }
        return Collections.unmodifiableList(resultList);
    }

    /**
     * 根据字符串数组构建 正则 {@link Pattern} 列表，字符串列表中的每个字符串被前缀和后缀包装后作为正则表达式
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @param str 被包装的字符串数组
     * @return 正则 {@link Pattern} 列表
     */
    public static List<Pattern> buildPatternListByWrapStr(String prefix, String suffix, String... str) {
        List<Pattern> resultList = new ArrayList<>();
        for (String regex : str) {
            resultList.add(buildPatternWithWrapStr(prefix, suffix, regex));
        }
        return Collections.unmodifiableList(resultList);
    }

    /**
     * 将字符串两边分别用前缀和后缀包装后再构建为正则 {@link Pattern}
     *
     * @param prefix 前缀
     * @param suffix 后缀
     * @param str 被包装的字符串
     * @return 正则 {@link Pattern}
     */
    public static Pattern buildPatternWithWrapStr(String prefix, String suffix, String str) {
        return Pattern.compile(StrUtil.wrap(str, prefix, suffix));
    }

    /**
     * 按照正则表达式 {@link Pattern} 列表依次匹配，若匹配则按照默认模板格式（匹配的第一个分组）替换后返回
     *
     * @param candidatePatterns 正则表达式 {@link Pattern} 列表
     * @param content           待解析的字符串
     * @return 根据指定的模板替换后的内容
     */
    public static String extractByFirstMatchedPattern(List<Pattern> candidatePatterns, CharSequence content) {
        return extractByFirstMatchedPattern(candidatePatterns, content, ReUtils.REGEX_GROUP_1);
    }

    /**
     * 按照正则表达式 {@link Pattern} 列表依次匹配，若匹配则按照指定的模板格式替换后返回
     *
     * @param candidatePatterns 正则表达式 {@link Pattern} 列表
     * @param content           待解析的字符串
     * @param template          模板（示例：$1$2）
     * @return 根据指定的模板替换后的内容
     */
    public static String extractByFirstMatchedPattern(List<Pattern> candidatePatterns,
                                                      CharSequence content,
                                                      String template) {
        for (Pattern pattern : candidatePatterns) {
            if (ReUtil.isMatch(pattern, content)) {
                return ReUtil.extractMulti(pattern, content, template);
            }
        }
        return StrUtils.EMPTY;
    }

    /**
     * 根据正则表达式 {@link Pattern} 提取匹配到的第一个分组的内容
     *
     * @param pattern 正则表达式 {@link Pattern}
     * @param content 待解析的字符串
     * @return 匹配到的第一个分组的内容
     */
    public static String extractFirstGroup(Pattern pattern, CharSequence content) {
        return ReUtil.extractMulti(pattern, content, REGEX_GROUP_1);
    }

    /**
     * 根据正则表达式 {@link Pattern} 提取匹配到的指定序号分组的内容
     *
     * @param pattern     正则表达式 {@link Pattern}
     * @param content     待解析的字符串
     * @param groupNumber 分组序号
     * @return 匹配到的指定序号分组的内容
     */
    public static String extractBySpecifiedGroupNumber(Pattern pattern, CharSequence content, int groupNumber) {
        return ReUtil.extractMulti(pattern, content, buildGroupTemplate(groupNumber));
    }

    /**
     * 解析字符串数组为 key-value的Map格式
     *
     * @param items       字符串数组
     * @param itemPattern 数组项匹配的正则表达式 {@link Pattern} (group1->key, group2->value)
     * @return key-value的Map格式
     */
    public static Map<String, String> parseArrayItemsToMap(String[] items, Pattern itemPattern) {
        Map<String, String> resultMap = new LinkedHashMap<>();

        for (String item : items) {
            if (ReUtil.isMatch(itemPattern, item)) {
                String key = ReUtil.extractMulti(itemPattern, item, REGEX_GROUP_1);
                String value = ReUtil.extractMulti(itemPattern, item, REGEX_GROUP_2);
                resultMap.put(key, value);
            }
        }

        return resultMap;
    }

    /**
     * 将字符串中根据 正则表达式 {@link Pattern} 匹配的多个结果转换为 key-value的 {@link Map} 格式
     *
     * @param input   待解析的字符串
     * @param pattern 正则表达式 Pattern (group1->key, group2->value)
     * @return key-value的Map格式
     */
    public static Map<String, String> convertAllMatchesToMap(CharSequence input, Pattern pattern) {
        String[] items = ReUtil.findAllGroup0(pattern, input).toArray(new String[0]);
        return parseArrayItemsToMap(items, pattern);
    }

    /**
     * 获取字符串列表中第一个能匹配正则表达式 {@link Pattern} 的字符串的索引序号
     *
     * @param lines        字符串列表
     * @param regexPattern 正则表达式 {@link Pattern}
     * @return 第一个匹配上的字符串在列表中的索引序号，未匹配到则返回 -1
     */
    public static int indexOfFirstMatchedLine(List<String> lines, Pattern regexPattern) {
        return indexOfFirstMatchedLine(lines, regexPattern, 0, lines.size());
    }

    /**
     * 获取字符串列表中从指定索引序号开始，第一个能匹配正则表达式 {@link Pattern} 的字符串的索引序号
     *
     * @param lines        字符串列表
     * @param regexPattern 正则表达式 {@link Pattern}
     * @param startIndex   起始的索引序号
     * @return 第一个匹配上的字符串在列表中的索引序号，未匹配到则返回 -1
     */
    public static int indexOfFirstMatchedLine(List<String> lines, Pattern regexPattern, int startIndex) {
        return indexOfFirstMatchedLine(lines, regexPattern, startIndex, lines.size());
    }

    /**
     * 获取字符串列表中从指定的起止索引序号之间，第一个能匹配正则表达式 {@link Pattern} 的字符串的索引序号
     *
     * @param lines        字符串列表
     * @param regexPattern 正则表达式 {@link Pattern}
     * @param startIndex   起始的索引序号
     * @param endIndex     结束的索引序号（不包含此序号对应的字符串）
     * @return 第一个匹配上的字符串在列表中的索引序号，未匹配到则返回 -1
     */
    public static int indexOfFirstMatchedLine(List<String> lines, Pattern regexPattern, int startIndex, int endIndex) {
        int start = Math.max(0, startIndex);
        int end = Math.min(lines.size(), endIndex);

        if (start >= end) {
            return INDEX_NOT_FOUND;
        }

        for (int i = start; i < end; i++) {
            String line = lines.get(i);
            if (ReUtil.isMatch(regexPattern, line)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 获取字符串列表中所有能匹配正则表达式 {@link Pattern} 的字符串的索引序号列表
     *
     * @param lines        字符串列表
     * @param regexPattern 正则表达式 {@link Pattern}
     * @return 所有能匹配正则表达式 Pattern 的字符串的索引序号列表
     */
    public static List<Integer> indexesOfAllMatchedLines(List<String> lines, Pattern regexPattern) {
        return indexesOfAllMatchedLines(lines, regexPattern, 0, lines.size());
    }

    /**
     * 获取字符串列表中从指定的索引序号开始，所有能匹配正则表达式 {@link Pattern} 的字符串的索引序号列表
     *
     * @param lines        字符串列表
     * @param regexPattern 正则表达式 {@link Pattern}
     * @param startIndex   起始的索引序号
     * @return 所有能匹配正则表达式 Pattern 的字符串的索引序号列表
     */
    public static List<Integer> indexesOfAllMatchedLines(List<String> lines, Pattern regexPattern, int startIndex) {
        return indexesOfAllMatchedLines(lines, regexPattern, startIndex, lines.size());
    }

    /**
     * 获取字符串列表中从指定的起止索引序号之间，所有能匹配正则表达式 {@link Pattern} 的字符串的索引序号列表
     *
     * @param lines        字符串列表
     * @param regexPattern 正则表达式 {@link Pattern}
     * @param startIndex   起始的索引序号
     * @param endIndex     结束的索引序号（不包含此序号对应的字符串）
     * @return 所有能匹配正则表达式 Pattern 的字符串的索引序号列表
     */
    public static List<Integer> indexesOfAllMatchedLines(List<String> lines,
                                                         Pattern regexPattern,
                                                         int startIndex,
                                                         int endIndex) {
        int start = Math.max(0, startIndex);
        int end = Math.min(lines.size(), endIndex);

        if (start >= end) {
            return Collections.emptyList();
        }

        List<Integer> resultList = new ArrayList<>();
        for (int i = start; i < end; i++) {
            String line = lines.get(i);
            if (ReUtil.isMatch(regexPattern, line)) {
                resultList.add(i);
            }
        }
        return resultList;
    }

    /**
     * 获取字符串列表中所有能匹配正则表达式 {@link Pattern} 的字符串的列表
     *
     * @param lines        字符串列表
     * @param regexPattern 正则表达式 {@link Pattern}
     * @return 所有能匹配正则表达式 Pattern 的字符串的列表
     */
    public static List<String> getMatchedLines(List<String> lines, Pattern regexPattern) {
        return lines.stream().filter(line -> ReUtil.isMatch(regexPattern, line)).collect(Collectors.toList());
    }

    private static String buildGroupTemplate(int groupNumber) {
        return "$" + groupNumber;
    }
}

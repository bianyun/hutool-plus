package plus.hutool.core.text.string;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import plus.hutool.core.lang.annotation.Nullable;

import java.util.Collection;
import java.util.function.Predicate;

import static plus.hutool.core.text.regex.RegexPools.REGEX_ZERO_OR_MORE_WHITE_SPACES;


/**
 * Utility for operations on String objects.
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings("JavadocDeclaration")
public abstract class StrUtils implements InvisibleStrs, AsciiAlphaNumericStrs,
        PrintableAsciiSymbolStrs, SimplifiedChineseSymbolStrs {
    private StrUtils() {}

    /**
     * 将字符序列 {@link CharSequence} 转换为大写的字符串（字符序列为 null 时直接返回 null）
     *
     * @param charSequence 字符序列
     * @return 转换后的大写字符串
     */
    @Nullable
    public static String toUpperCase(@Nullable CharSequence charSequence) {
        if (StrUtil.isBlank(charSequence)) {
            return charSequence == null ? null : charSequence.toString();
        }

        return charSequence.toString().toUpperCase();
    }

    /**
     * 将字符序列 {@link CharSequence} 转换为小写的字符串（字符序列为 null 时直接返回 null）
     *
     * @param charSequence 字符序列
     * @return 转换后的小写字符串
     */
    @Nullable
    public static String toLowerCase(@Nullable CharSequence charSequence) {
        if (StrUtil.isBlank(charSequence)) {
            return charSequence == null ? null : charSequence.toString();
        }

        return charSequence.toString().toLowerCase();
    }

    /**
     * 判断字符串是否不是空白，同时也不是 NULL字符串
     *
     * <p>
     * 空白的定义如下： <br>
     * 1、null <br>
     * 2、不可见字符（如空格、换行符）<br>
     * 3、空字符串""
     * </p>
     * <p>
     * NULL字符串的定义如下： <br>
     * [NULL字符串].toLowerCase() == "null"
     * </p>
     *
     * @param str 被检测的字符串
     * @return 是否不是空白，同时也不是 NULL字符串
     */
    public static boolean isNeitherBlankNorLiterallyNull(@Nullable CharSequence str) {
        return StrUtil.isNotBlank(str) && isNotLiterallyNull(str);
    }

    /**
     * 判断字符串是否是 NULL字符串
     * <p>
     * NULL字符串的定义如下： <br>
     * [NULL字符串].toLowerCase() == "null"
     * </p>
     *
     * @param str 被检测的字符串
     * @return 是否是 NULL字符串
     */
    public static boolean isLiterallyNull(@Nullable CharSequence str) {
        return StrUtil.equalsIgnoreCase(StrUtil.NULL, str);
    }

    /**
     * 判断字符串是否不是 NULL字符串
     * <p>
     * NULL字符串的定义如下： <br>
     * [NULL字符串].toLowerCase() == "null"
     * </p>
     *
     * @param str 被检测的字符串
     * @return 是否不是 NULL字符串
     */
    public static boolean isNotLiterallyNull(@Nullable CharSequence str) {
        return !isLiterallyNull(str);
    }

    /**
     * 判断字符串容器（{@link Collection}&lt;{@link String}&gt;）中的字符串是否全部为 空白
     *
     * <p>
     * 空白的定义如下： <br>
     * 1、null <br>
     * 2、不可见字符（如空格、换行符）<br>
     * 3、空字符串""
     * </p>
     *
     * @param strs 字符串容器
     * @return 字符串容器（Collection[String]）中的字符串是否全部为空白
     */
    public static boolean isAllBlank(Collection<String> strs) {
        return StrUtil.isAllBlank(strs.toArray(new CharSequence[]{}));
    }

    /**
     * 判断字符串容器（{@link Collection}&lt;{@link String}&gt;）中的字符串是否不是全部为空白（即至少有一个字符串不是空白）
     *
     * <p>
     * 空白的定义如下： <br>
     * 1、null <br>
     * 2、不可见字符（如空格、换行符）<br>
     * 3、空字符串""
     * </p>
     *
     * @param strs 字符串容器
     * @return 字符串容器（Collection[String]）中的字符串是否不是全部为空白
     */
    public static boolean isNotAllBlank(Collection<String> strs) {
        return !isAllBlank(strs);
    }

    /**
     * 判断字符串容器（{@link Collection}&lt;{@link String}&gt;）中的字符串是否存在任意一个字符串为空白
     *
     * <p>
     * 空白的定义如下： <br>
     * 1、null <br>
     * 2、不可见字符（如空格、换行符）<br>
     * 3、空字符串""
     * </p>
     *
     * @param strs 字符串容器
     * @return 字符串容器（{@link Collection}&lt;{@link String}&gt;）中的字符串是否存在任意一个字符串为空白
     */
    public static boolean isAnyBlank(Collection<String> strs) {
        return isAnyBlank(strs.toArray(new CharSequence[]{}));
    }

    /**
     * 判断字符序列变长参数列表中的字符序列是否存在任意一个字符序列为空白
     *
     * <p>
     * 空白的定义如下： <br>
     * 1、null <br>
     * 2、不可见字符（如空格、换行符）<br>
     * 3、空字符串""
     * </p>
     *
     * @param charSequences 字符序列 {@link CharSequence} 类型的变长参数列表
     * @return 字符序列变长参数列表中的字符序列是否存在任意一个字符序列为空白
     */
    public static boolean isAnyBlank(CharSequence... charSequences) {
        if (ArrayUtil.isEmpty(charSequences)) {
            return true;
        }

        for (CharSequence charSequence : charSequences) {
            if (StrUtil.isBlank(charSequence)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将字符串中间出现的多个空白替换为一个空格
     *
     * <p>示例：
     * <pre>
     *     replaceAllWhiteSpacesToOneSpace("12  2        3     4") ==>  "12 2 3 4"
     *     replaceAllWhiteSpacesToOneSpace("   12  2     3   4  ") ==>  " 12 2 3 4 "
     * </pre>
     *
     * @param str 待处理的字符串
     * @return 处理完的字符串
     */
    public static String replaceAllWhiteSpacesToOneSpace(String str) {
        return str.replaceAll("\\s+", SPACE);
    }

    /**
     * 判断字符串中是否包含指定字符串列表中的任意一个字符串
     *
     * @param str      指定字符串
     * @param testStrs 需要检查的字符串列表
     * @return 是否包含任意一个字符串
     */
    public static boolean containsAny(CharSequence str, Collection<? extends CharSequence> testStrs) {
        return StrUtil.containsAny(str, testStrs.toArray(new CharSequence[0]));
    }

    /**
     * 对字符串中间部分进行脱敏（即头尾各留一部分不脱敏）
     *
     * <p> 注1：对字符串进行脱敏的部分长度，最少为字符串长度的一半</p>
     * <p> 注2：不脱敏部分的长度 = Math.min(strLen/2, maxExposedPartLen) </p>
     *
     * @param str               需要脱敏的字符串
     * @param maxExposedPartLen 不脱敏部分的最大长度
     * @return 已脱敏的字符串
     */
    public static String partiallyDesensitize(final String str, final int maxExposedPartLen) {
        if (StrUtil.isBlank(str)) {
            return str;
        }

        int strLen = str.length();
        int newMaxExposedPartLen = Math.max(maxExposedPartLen, 0);
        int exposedPartLen = Math.min(strLen / 2, newMaxExposedPartLen);

        int desensitizedPartLen = strLen - exposedPartLen;
        String desensitizedPart = StrUtil.repeat(StrUtils.ASTERISK, desensitizedPartLen);

        String headExposedPart;
        String tailExposedPart;
        if (exposedPartLen % 2 == 0) {
            headExposedPart = StrUtil.subPre(str, exposedPartLen / 2);
            tailExposedPart = StrUtil.subSufByLength(str, exposedPartLen / 2);
        } else {
            headExposedPart = StrUtil.subPre(str, (exposedPartLen + 1) / 2);
            tailExposedPart = StrUtil.subSufByLength(str, (exposedPartLen - 1) / 2);
        }

        return headExposedPart + desensitizedPart + tailExposedPart;
    }

    /**
     * 对字符串中间部分进行脱敏（即头尾各留一部分不脱敏）
     *
     * <p> 注1：对字符串进行脱敏的部分长度，最少为字符串长度的一半</p>
     * <p> 注2：头尾部分不脱敏直接显示的字符串长度最长共16个字符</p>
     *
     * @param str 需要脱敏的字符串
     * @return 已脱敏的字符串
     */
    public static String desensitizeMiddlePart(String str) {
        return partiallyDesensitize(str, 16);
    }

    /**
     * 获取包含汉字的字符串长度（汉字的长度按长度 2 计算，其它字符按 1 计算）
     *
     * @param str 待计算长度的字符串
     * @return 包含汉字的字符串长度
     */
    @SuppressWarnings({"checkstyle:AvoidEscapedUnicodeCharacters", "UnnecessaryUnicodeEscape"})
    public static int lenOfHansStr(String str) {
        int valueLength = 0;
        String chinese = "[\u0391-\uFFE5]";
        for (int i = 0; i < str.length(); i++) {
            String temp = str.substring(i, i + 1);
            if (temp.matches(chinese)) {
                valueLength += 2;
            } else {
                valueLength += 1;
            }
        }
        return valueLength;
    }

    /**
     * 计算字符串集合中包含汉字的字符串长度（汉字的长度按长度 2 计算，其它字符按 1 计算）的最大值
     *
     * @param strs 字符串集合
     * @return 字符串集合中包含汉字的字符串长度的最大值
     */
    public static int maxLenOfHansStr(Collection<String> strs) {
        return strs.stream().mapToInt(StrUtils::lenOfHansStr).max().orElse(0);
    }

    /**
     * 去除字符串中所有指定的分隔符两边的空白
     *
     * @param str 待处理的字符串
     * @param delimiters 分隔符变长数组
     * @return 去除字符串中指定的分隔符两边的空白之后的字符串
     */
    public static String removeWhiteSpacesAroundDelimiters(final String str, final String... delimiters) {
        String tempStr = str;
        for (String delimiter : delimiters) {
            if (tempStr.contains(delimiter)) {
                String regex = StrUtil.wrap(ReUtil.escape(delimiter), REGEX_ZERO_OR_MORE_WHITE_SPACES);
                tempStr = tempStr.replaceAll(regex, delimiter);
            }
        }
        return tempStr;
    }

    /**
     * 如果给定字符串满足指定条件，则在开头补充 prefix
     *
     * @param str       字符串
     * @param prefix    前缀
     * @param predicate 字符串判断条件
     * @return 补充后的字符串
     */
    public static String addPrefixIfPredicateSatisfied(CharSequence str,
                                                       CharSequence prefix,
                                                       Predicate<CharSequence> predicate) {
        if (predicate.test(str)) {
            return StrUtil.format("{}{}", prefix, str);
        } else {
            return str.toString();
        }
    }

    /**
     * 如果给定字符串满足指定条件，则在末尾补充 suffix
     *
     * @param str       字符串
     * @param suffix    后缀
     * @param predicate 字符串判断条件
     * @return 补充后的字符串
     */
    public static String addSuffixIfPredicateSatisfied(CharSequence str,
                                                       CharSequence suffix,
                                                       Predicate<CharSequence> predicate) {
        if (predicate.test(str)) {
            return StrUtil.format("{}{}", str, suffix);
        } else {
            return str.toString();
        }
    }

    // ------------------------------------------------------
    // 搬运自 hutool
    // ------------------------------------------------------

    /**
     * 以 conjunction 为分隔符将多个对象转换为字符串
     *
     * @param <T>         元素类型
     * @param conjunction 分隔符 {@link StrPool#COMMA}
     * @param iterable    集合
     * @return 连接后的字符串
     * @see CollUtil#join(Iterable, CharSequence)
     * @since hutool-5.6.6
     */
    public static <T> String join(CharSequence conjunction, Iterable<T> iterable) {
        return CollUtil.join(iterable, conjunction);
    }
}

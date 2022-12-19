package plus.hutool.core.text.regex;

import cn.hutool.core.lang.RegexPool;

/**
 * 常见的正则表达式 Pattern 字符串
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings("JavadocDeclaration")
public interface RegexPools extends RegexPool {

    String REGEX_TRAILING_CONTINUOUS_ZEROS = "(0+)$";

    String REGEX_ZERO_OR_MORE_WHITE_SPACES = "\\s*";

    String REGEX_ONE_HUNDRED_PERCENT = "^100([.]0+)?%$";

    String REGEX_ZERO_PERCENT = "^0([.]0+)?%$";
}

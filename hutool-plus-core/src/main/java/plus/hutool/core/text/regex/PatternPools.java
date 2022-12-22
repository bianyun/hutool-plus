package plus.hutool.core.text.regex;

import cn.hutool.core.lang.PatternPool;

import java.util.regex.Pattern;

/**
 * 常见正则表达式 Pattern
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class PatternPools extends PatternPool {

    public static final Pattern TRAILING_CONTINUOUS_ZEROS = Pattern.compile(RegexPools.REGEX_TRAILING_CONTINUOUS_ZEROS);
    public static final Pattern ONE_HUNDRED_PERCENT = Pattern.compile(RegexPools.REGEX_ONE_HUNDRED_PERCENT);
    public static final Pattern ZERO_PERCENT = Pattern.compile(RegexPools.REGEX_ZERO_PERCENT);

    private PatternPools() {}
}

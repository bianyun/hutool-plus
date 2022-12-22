package plus.hutool.core.iterable.collection;

import cn.hutool.core.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 数组工具类
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class ArrayUtils {
    public static final int[] EMPTY_INT_ARRAY = new int[0];

    private ArrayUtils() {
    }

    /**
     * 判断字符串数组是否包含指定字符串列表中的任意一个字符串（不区分大小写）
     * <p>示例 example:</p>
     * <pre>
     *   final String[] strs = new String[] {"Hi bro", "What's up"};
     *
     *   containsAnyIgnoreCase(strs) == false
     *   containsAnyIgnoreCase(strs, "hello", "world") == false
     *   containsAnyIgnoreCase(strs, "Hi", "bro")      == false
     *   containsAnyIgnoreCase(strs, "What's", "up")   == false
     *   containsAnyIgnoreCase(strs, "Hi  bro")        == false
     *   containsAnyIgnoreCase(strs, "HI Bro", "what") == true
     *   containsAnyIgnoreCase(strs, "HI BRO", "up")   == true
     * </pre>
     *
     * @param strs     字符串数组
     * @param testStrs 需要检查的字符串列表
     * @return 是否包含任意一个字符串（不区分大小写）
     */
    public static boolean containsAnyIgnoreCase(CharSequence[] strs, CharSequence... testStrs) {
        for (CharSequence value : testStrs) {
            if (ArrayUtil.containsIgnoreCase(strs, value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将指定的第一个元素对象和类型为变长参数数组的其余元素转换为 {@link List} 类型的列表
     *
     * @param firstElem      第一个元素对象
     * @param remainingElems 类型为变长参数数组的其余元素
     * @param <T>            元素对象的类型
     * @return {@link List} 类型的列表
     */
    @SafeVarargs
    public static <T> List<T> toList(T firstElem, T... remainingElems) {
        List<T> resultList = new ArrayList<>();
        resultList.add(firstElem);
        if (ArrayUtil.isNotEmpty(remainingElems)) {
            resultList.addAll(Arrays.asList(remainingElems));
        }

        return resultList;
    }

    /**
     * 将变长参数数组转换为 {@link Set} 对象
     *
     * @param args 变长参数数组
     * @param <T>  数组中对象的类型
     * @return {@link Set} 对象
     */
    @SafeVarargs
    public static <T> Set<T> toSet(T... args) {
        return new HashSet<>(Arrays.asList(args));
    }
}

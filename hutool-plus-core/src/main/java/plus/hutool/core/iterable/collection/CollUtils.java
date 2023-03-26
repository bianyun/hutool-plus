package plus.hutool.core.iterable.collection;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import plus.hutool.core.lang.Asserts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 集合工具类
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings("JavadocDeclaration")
public abstract class CollUtils {
    private CollUtils() {}

    /**
     * 将字符串分割成字符串（并裁剪两边的空白字符）集合
     *
     * @param str       待分割的字符串
     * @param separator 分隔符
     * @return 字符串集合
     */
    public static Set<String> splitTrimToSet(String str, String separator) {
        return new HashSet<>(StrUtil.splitTrim(str, separator));
    }

    /**
     * 构建元素不可变的列表（已去除重复值）
     *
     * @param firstElem      第一个元素
     * @param remainingElems 剩余元素数组
     * @param <T>            元素类型
     * @return 元素不可变的列表
     */
    @SafeVarargs
    public static <T> List<T> unmodifiableList(T firstElem, T... remainingElems) {
        if (remainingElems.length == 0) {
            return Collections.unmodifiableList(ArrayUtils.toList(firstElem));
        }

        Set<T> set = CollUtil.newLinkedHashSet(firstElem);
        Collections.addAll(set, remainingElems);

        List<T> tempList = new ArrayList<>(set);
        return Collections.unmodifiableList(tempList);
    }

    /**
     * 构建元素不可变的列表
     *
     * @param removeDuplicates 是否去重
     * @param elemArray        元素数组
     * @param <T>              元素类型
     * @return 元素不可变的列表
     */
    public static <T> List<T> unmodifiableList(boolean removeDuplicates, T[] elemArray) {
        Asserts.isTrue(elemArray.length > 0, "元素数组长度必须大于 0");
        if (elemArray.length == 1) {
            return Collections.unmodifiableList(ArrayUtils.toList(elemArray[0]));
        }

        List<T> tempList = new ArrayList<>();
        if (removeDuplicates) {
            Set<T> set = CollUtil.newLinkedHashSet(elemArray);
            tempList.addAll(set);
        } else {
            tempList.addAll(Arrays.asList(elemArray));
        }

        return Collections.unmodifiableList(tempList);
    }

    /**
     * 构建元素不可变的集合
     *
     * @param firstElem      第一个元素
     * @param remainingElems 剩余元素数组
     * @param <T>            元素类型
     * @return 元素不可变的列表
     */
    @SafeVarargs
    public static <T> Set<T> unmodifiableSet(T firstElem, T... remainingElems) {
        if (remainingElems.length == 0) {
            return Collections.unmodifiableSet(ArrayUtils.toSet(firstElem));
        }

        Set<T> set = CollUtil.newHashSet(firstElem);
        Collections.addAll(set, remainingElems);
        return Collections.unmodifiableSet(set);
    }

    /**
     * 将不同集合中的元素抽取后组成新的元素不可变的集合
     *
     * @param firstSet      第一个集合
     * @param remainingSets 剩余元素集合
     * @param <T>           元素类型
     * @return 元素不可变的列表
     */
    @SafeVarargs
    public static <T> Set<T> mergeToUnmodifiableSet(Collection<T> firstSet, Collection<T>... remainingSets) {
        if (remainingSets.length == 0) {
            return Collections.unmodifiableSet(new HashSet<>(firstSet));
        }

        Set<T> set = CollUtil.newHashSet(firstSet);

        for (Collection<T> remainingSet : remainingSets) {
            set.addAll(remainingSet);
        }

        return Collections.unmodifiableSet(set);
    }
}

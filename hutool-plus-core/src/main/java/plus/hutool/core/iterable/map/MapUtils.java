package plus.hutool.core.iterable.map;

import plus.hutool.core.iterable.enums.EnumUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * MAP工具类
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings("JavadocDeclaration")
public abstract class MapUtils {
    private MapUtils() {}

    /**
     * 根据给定的元素对象变长数组构建不可变的MAP
     *
     * @param keyGetter      从对象元素中获取 MAP 键值的 getter 方法
     * @param firstElem      第一个对象元素
     * @param remainingElems 剩余的对象元素
     * @param <K>            MAP 的键类型
     * @param <V>            MAP 的值类型
     * @return 不可变的MAP
     */
    @SafeVarargs
    public static <K extends Comparable<? super K>, V> Map<K, V> buildUnmodifiableMap(Function<V, K> keyGetter,
                                                                                      V firstElem,
                                                                                      V... remainingElems) {
        Map<K, V> map = new LinkedHashMap<>(remainingElems.length + 1);
        return buildUnmodifiableMap(keyGetter, map, firstElem, remainingElems);
    }

    /**
     * 根据给定的元素对象变长数组构建不可变的MAP（内层 MAP 对象作为参数传入）
     *
     * @param keyGetter      从对象元素中获取 MAP 键值的 getter 方法
     * @param map            内层 MAP 对象
     * @param firstElem      第一个对象元素
     * @param remainingElems 剩余的对象元素
     * @param <K>            MAP 的键类型
     * @param <V>            MAP 的值类型
     * @return 不可变的MAP
     */
    @SafeVarargs
    public static <K extends Comparable<? super K>, V> Map<K, V> buildUnmodifiableMap(Function<V, K> keyGetter,
                                                                                      Map<K, V> map,
                                                                                      V firstElem,
                                                                                      V... remainingElems) {
        K key = keyGetter.apply(firstElem);
        if (remainingElems.length == 0) {
            return Collections.singletonMap(key, firstElem);
        }

        map.clear();
        map.put(key, firstElem);
        Arrays.stream(remainingElems).forEach(item -> map.put(keyGetter.apply(item), item));
        return Collections.unmodifiableMap(map);
    }

    /**
     * 从 参数MAP 中获取枚举类型参数的值，参数名称约定为
     *
     * <pre>
     *     paramName = StrUtils.lowerFirst(enumClass.getSimpleName())
     * </pre>
     *
     * @param paramsMap 参数MAP
     * @param enumClass 枚举类
     * @param <E>       枚举类的泛型类型
     * @return 参数的值
     * @throws ClassCastException 当返回对象无法转换为返回类型时
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> E getEnumValue(Map<String, Object> paramsMap, Class<E> enumClass) {
        String paramName = EnumUtils.defaultParamName(enumClass);
        return (E) paramsMap.get(paramName);
    }

    /**
     * 从 参数MAP 中根据参数名称获取参数值，如果获取不到，则返回默认值
     *
     * @param paramsMap    参数MAP
     * @param paramName    参数的名称
     * @param defaultValue 参数的默认值
     * @param <T>          参数值的泛型类型
     * @return 参数的值
     * @throws ClassCastException 当返回对象无法转换为返回类型时
     */
    @SuppressWarnings("unchecked")
    public static <T> T getParamValue(Map<String, Object> paramsMap, String paramName, T defaultValue) {
        T result = (T) paramsMap.get(paramName);
        return result == null ? defaultValue : result;
    }

    /**
     * 从 参数MAP 中根据参数名称获取参数值
     *
     * @param paramsMap 参数MAP
     * @param paramName 参数的名称
     * @param <T>       参数值的泛型类型
     * @return 参数的值
     * @throws ClassCastException 当返回对象无法转换为返回类型时
     */
    @SuppressWarnings("unchecked")
    public static <T> T getParamValue(Map<String, Object> paramsMap, String paramName) {
        return (T) paramsMap.get(paramName);
    }

    /**
     * 获取参数MAP 构造器
     *
     * @return 参数MAP 构造器
     */
    public static ParamsMapBuilder paramsMapBuilder() {
        return new ParamsMapBuilder(new HashMap<>(4));
    }

    /**
     * 获取参数MAP 构造器（指定内层的 Map实现）
     *
     * @param paramsMap 内层的 Map实现类的对象
     * @return 参数MAP 构造器
     */
    public static ParamsMapBuilder paramsMapBuilder(Map<String, Object> paramsMap) {
        return new ParamsMapBuilder(paramsMap);
    }
}

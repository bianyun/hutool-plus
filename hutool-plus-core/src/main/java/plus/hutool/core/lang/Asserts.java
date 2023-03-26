package plus.hutool.core.lang;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import plus.hutool.core.lang.annotation.Nullable;

import java.util.Map;
import java.util.function.Supplier;

/**
 * 断言
 * <p>断言某些对象或值是否符合规定，否则抛出异常。经常用于做变量检查。</p>
 * <p>参考自 hutool {@link Assert}, 在其基础上做了精简和优化，同时配合 Spring 的 {@code @NonNullApi} 使用</p>
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings("JavadocDeclaration")
public abstract class Asserts {

    static final String TEMPLATE_VALUE_MUST_BE_BETWEEN_AND = "The value must be between {} and {}.";

    private Asserts() {}

    /**
     * 断言是否为真，如果为 {@code false} 抛出给定的异常<br>
     *
     * <pre class="code">
     * Asserts.isTrue(i > 0, () -> new RuntimeException("errorMsg"));
     * </pre>
     *
     * @param <X>        异常类型
     * @param expression 布尔值
     * @param supplier   指定断言不通过时抛出的异常
     * @throws X if expression is {@code false}
     */
    public static <X extends Throwable> void isTrue(boolean expression, Supplier<? extends X> supplier) throws X {
        if (!expression) {
            throw supplier.get();
        }
    }

    /**
     * 断言是否为真，如果为 {@code false} 抛出 {@code IllegalArgumentException} 异常<br>
     *
     * <pre class="code">
     * Asserts.isTrue(i > 0, "The value must be greater than zero");
     * </pre>
     *
     * @param expression       布尔值
     * @param errorMsgTemplate 错误抛出异常附带的消息模板，变量用{}代替
     * @param params           参数列表
     * @throws IllegalArgumentException if expression is {@code false}
     */
    public static void isTrue(boolean expression, String errorMsgTemplate, Object... params)
            throws IllegalArgumentException {
        isTrue(expression, () -> new IllegalArgumentException(StrUtil.format(errorMsgTemplate, params)));
    }

    /**
     * 断言是否为假，如果为 {@code true} 抛出指定类型异常<br>
     * 并使用指定的函数获取错误信息返回
     * <pre class="code">
     *  Asserts.isFalse(i > 0, () -> new RuntimeException("errorMsg"));
     * </pre>
     *
     * @param <X>           异常类型
     * @param expression    布尔值
     * @param errorSupplier 指定断言不通过时抛出的异常
     * @throws X if expression is {@code false}
     */
    public static <X extends Throwable> void isFalse(boolean expression, Supplier<X> errorSupplier) throws X {
        if (expression) {
            throw errorSupplier.get();
        }
    }

    /**
     * 断言是否为假，如果为 {@code true} 抛出 {@code IllegalArgumentException} 异常<br>
     *
     * <pre class="code">
     * Asserts.isFalse(i >= 0, "The value must not be negative");
     * </pre>
     *
     * @param expression       布尔值
     * @param errorMsgTemplate 错误抛出异常附带的消息模板，变量用{}代替
     * @param params           参数列表
     * @throws IllegalArgumentException if expression is {@code false}
     */
    public static void isFalse(boolean expression, String errorMsgTemplate, Object... params)
            throws IllegalArgumentException {
        isFalse(expression, () -> new IllegalArgumentException(StrUtil.format(errorMsgTemplate, params)));
    }

    /**
     * 断言对象是否为{@code null} ，如果不为{@code null} 抛出指定类型异常
     * 并使用指定的函数获取错误信息返回
     * <pre class="code">
     * Asserts.isNull(object, () -> new RuntimeException("errorMsg"));
     * </pre>
     *
     * @param <X>           异常类型
     * @param object        被检查的对象
     * @param errorSupplier 错误抛出异常附带的消息生产接口
     * @throws X if the object is not {@code null}
     */
    public static <X extends Throwable> void isNull(@Nullable Object object, Supplier<X> errorSupplier) throws X {
        if (null != object) {
            throw errorSupplier.get();
        }
    }

    /**
     * 断言对象是否为{@code null} ，如果不为{@code null} 抛出{@link IllegalArgumentException} 异常
     *
     * <p><pre class="code">
     *   Asserts.isNull(object, "The value must be null");
     * </pre>
     *
     * @param object           被检查的对象
     * @param errorMsgTemplate 消息模板，变量使用{}表示
     * @param params           参数列表
     * @throws IllegalArgumentException if the object is not {@code null}
     */
    public static void isNull(@Nullable Object object, String errorMsgTemplate, Object... params)
            throws IllegalArgumentException {
        isNull(object, () -> new IllegalArgumentException(StrUtil.format(errorMsgTemplate, params)));
    }

    /**
     * 断言对象是否不为{@code null} ，如果为{@code null} 抛出指定类型异常
     * 并使用指定的函数获取错误信息返回
     * <pre class="code">
     * Asserts.notNull(object, () -> new RuntimeException("errorMsg"));
     * </pre>
     *
     * @param <T>           被检查对象泛型类型
     * @param <X>           异常类型
     * @param object        被检查对象
     * @param errorSupplier 错误抛出异常附带的消息生产接口
     * @return 被检查后的对象
     * @throws X if the object is {@code null}
     */
    public static <T, X extends Throwable> T notNull(@Nullable T object, Supplier<X> errorSupplier) throws X {
        if (null == object) {
            throw errorSupplier.get();
        }
        return object;
    }

    /**
     * 断言对象是否不为{@code null} ，如果为{@code null} 抛出{@link IllegalArgumentException} 异常
     *
     * <p><pre class="code">
     *   Asserts.notNull(clazz, "The class must not be null");
     * </pre>
     *
     * @param <T>              被检查对象泛型类型
     * @param object           被检查对象
     * @param errorMsgTemplate 错误消息模板，变量使用{}表示
     * @param params           参数
     * @return 被检查后的对象
     * @throws IllegalArgumentException if the object is {@code null}
     */
    public static <T> T notNull(@Nullable T object, String errorMsgTemplate, Object... params)
            throws IllegalArgumentException {
        return notNull(object, () -> new IllegalArgumentException(StrUtil.format(errorMsgTemplate, params)));
    }

    /**
     * 检查给定字符串是否为空白（null、空串或只包含空白符），为空抛出自定义异常。
     * 并使用指定的函数获取错误信息返回
     * <pre class="code">
     * Asserts.notBlank(text, () -> new RuntimeException("errorMsg"));
     * </pre>
     *
     * @param <X>              异常类型
     * @param <T>              字符串类型
     * @param text             被检查字符串
     * @param errorMsgSupplier 错误抛出异常附带的消息生产接口
     * @return 非空字符串
     * @throws X 被检查字符串为空白
     */
    public static <T extends CharSequence, X extends Throwable> T notBlank(@Nullable T text,
                                                                           Supplier<X> errorMsgSupplier) throws X {
        if (StrUtil.isBlank(text)) {
            throw errorMsgSupplier.get();
        }
        return text;
    }

    /**
     * 检查给定字符串是否为空白（null、空串或只包含空白符），为空抛出 {@link IllegalArgumentException}
     *
     * <pre class="code">
     * Asserts.notBlank(name, "Name must not be blank");
     * </pre>
     *
     * @param <T>              字符串类型
     * @param text             被检查字符串
     * @param errorMsgTemplate 错误消息模板，变量使用{}表示
     * @param params           参数
     * @return 非空字符串
     * @throws IllegalArgumentException 被检查字符串为空白
     */
    public static <T extends CharSequence> T notBlank(@Nullable T text, String errorMsgTemplate, Object... params)
            throws IllegalArgumentException {
        return notBlank(text, () -> new IllegalArgumentException(StrUtil.format(errorMsgTemplate, params)));
    }

    /**
     * 断言给定数组是否包含元素，数组必须不为 {@code null} 且至少包含一个元素
     * 并使用指定的函数获取错误信息返回
     *
     * <p><pre class="code">
     *   Asserts.notEmptyArray(array, () -> new RuntimeException("errorMsg"));
     * </pre>
     *
     * @param <T>           数组元素类型
     * @param <X>           异常类型
     * @param array         被检查的数组
     * @param errorSupplier 错误抛出异常附带的消息生产接口
     * @return 被检查的数组
     * @throws X if the object array is {@code null} or has no elements
     */
    public static <T, X extends Throwable> T[] notEmptyArray(@Nullable T[] array, Supplier<X> errorSupplier) throws X {
        if (ArrayUtil.isEmpty(array)) {
            throw errorSupplier.get();
        }
        return array;
    }

    /**
     * 断言给定数组是否包含元素，数组必须不为 {@code null} 且至少包含一个元素
     * <pre class="code">
     *   Asserts.notEmptyArray(array, "The array must have elements");
     * </pre>
     *
     * @param <T>              数组元素类型
     * @param array            被检查的数组
     * @param errorMsgTemplate 异常时的消息模板
     * @param params           参数列表
     * @return 被检查的数组
     * @throws IllegalArgumentException if the object array is {@code null} or has no elements
     */
    public static <T> T[] notEmptyArray(@Nullable T[] array,
                                        String errorMsgTemplate,
                                        Object... params) throws IllegalArgumentException {
        return notEmptyArray(array, () -> new IllegalArgumentException(StrUtil.format(errorMsgTemplate, params)));
    }

    /**
     * 断言给定集合非空, 并使用指定的函数获取错误信息返回
     * <pre class="code">
     * Asserts.notEmptyArray(collection, () -> new RuntimeException("errorMsg"));
     * </pre>
     *
     * @param <E>           集合元素类型
     * @param <T>           集合类型
     * @param <X>           异常类型
     * @param collection    被检查的集合
     * @param errorSupplier 错误抛出异常附带的消息生产接口
     * @return 非空集合
     * @throws X if the collection is {@code null} or has no elements
     */
    public static <E, T extends Iterable<E>, X extends Throwable> T notEmpty(@Nullable T collection,
                                                                             Supplier<X> errorSupplier) throws X {
        if (CollUtil.isEmpty(collection)) {
            throw errorSupplier.get();
        }
        return collection;
    }

    /**
     * 断言给定集合非空
     *
     * <p><pre class="code">
     *   Asserts.notEmptyArray(collection, "Collection must have elements");
     * </pre>
     *
     * @param <E>              集合元素类型
     * @param <T>              集合类型
     * @param collection       被检查的集合
     * @param errorMsgTemplate 异常时的消息模板
     * @param params           参数列表
     * @return 非空集合
     * @throws IllegalArgumentException if the collection is {@code null} or has no elements
     */
    public static <E, T extends Iterable<E>> T notEmpty(@Nullable T collection,
                                                        String errorMsgTemplate,
                                                        Object... params) throws IllegalArgumentException {
        return notEmpty(collection, () -> new IllegalArgumentException(StrUtil.format(errorMsgTemplate, params)));
    }

    /**
     * 断言给定Map非空
     * 并使用指定的函数获取错误信息返回
     * <pre class="code">
     * Asserts.notEmptyArray(map, () -> new RuntimeException("errorMsg"));
     * </pre>
     *
     * @param <K>           Key类型
     * @param <V>           Value类型
     * @param <T>           Map类型
     * @param <X>           异常类型
     * @param map           被检查的Map
     * @param errorSupplier 错误抛出异常附带的消息生产接口
     * @return 被检查的Map
     * @throws X if the map is {@code null} or has no entries
     */
    public static <K, V, T extends Map<K, V>, X extends Throwable> T notEmptyMap(@Nullable T map,
                                                                                 Supplier<X> errorSupplier) throws X {
        if (MapUtil.isEmpty(map)) {
            throw errorSupplier.get();
        }
        return map;
    }

    /**
     * 断言给定Map非空
     *
     * <p><pre class="code">
     *   Asserts.notEmptyArray(map, "Map must have entries");
     * </pre>
     *
     * @param <K>              Key类型
     * @param <V>              Value类型
     * @param <T>              Map类型
     * @param map              被检查的Map
     * @param errorMsgTemplate 异常时的消息模板
     * @param params           参数列表
     * @return 被检查的Map
     * @throws IllegalArgumentException if the map is {@code null} or has no entries
     */
    public static <K, V, T extends Map<K, V>> T notEmptyMap(@Nullable T map,
                                                            String errorMsgTemplate,
                                                            Object... params) throws IllegalArgumentException {
        return notEmptyMap(map, () -> new IllegalArgumentException(StrUtil.format(errorMsgTemplate, params)));
    }

    /**
     * 断言给定对象是否是给定类的实例
     *
     * <p><pre class="code">
     *   Asserts.instanceOf(Foo.class, foo);
     * </pre>
     *
     * @param <T>  被检查对象泛型类型
     * @param type 被检查对象匹配的类型
     * @param obj  被检查对象
     * @return 被检查的对象
     * @throws IllegalArgumentException if the object is not an instance of clazz
     * @see Class#isInstance(Object)
     */
    public static <T> T isInstanceOf(@Nullable Class<?> type, T obj) {
        return isInstanceOf(type, obj, "Object [{}] is not instanceof [{}]", obj, type);
    }

    /**
     * 断言给定对象是否是给定类的实例
     *
     * <p><pre class="code">
     *   Asserts.instanceOf(Foo.class, foo, "foo must be an instance of class Foo");
     * </pre>
     *
     * @param <T>              被检查对象泛型类型
     * @param type             被检查对象匹配的类型
     * @param obj              被检查对象
     * @param errorMsgTemplate 异常时的消息模板
     * @param params           参数列表
     * @return 被检查对象
     * @throws IllegalArgumentException if the object is not an instance of clazz
     * @see Class#isInstance(Object)
     */
    public static <T> T isInstanceOf(@Nullable Class<?> type, T obj, String errorMsgTemplate, Object... params)
            throws IllegalArgumentException {
        notNull(type, "Type to check against must not be null");
        if (!type.isInstance(obj)) {
            throw new IllegalArgumentException(StrUtil.format(errorMsgTemplate, params));
        }
        return obj;
    }

    /**
     * 断言 {@code superType.isAssignableFrom(subType)} 是否为 {@code true}.
     *
     * <p><pre class="code">
     *   Asserts.isAssignable(Number.class, myClass);
     * </pre>
     *
     * @param superType 需要检查的父类或接口
     * @param subType   需要检查的子类
     * @throws IllegalArgumentException 如果子类非继承父类，抛出此异常
     */
    public static void isAssignable(@Nullable Class<?> superType, @Nullable Class<?> subType)
            throws IllegalArgumentException {
        isAssignable(superType, subType, "{} is not assignable to {})", subType, superType);
    }

    /**
     * 断言 {@code superType.isAssignableFrom(subType)} 是否为 {@code true}.
     *
     * <p><pre class="code">
     *   Asserts.isAssignable(Number.class, myClass, "myClass must can be assignable to class Number");
     * </pre>
     *
     * @param superType        需要检查的父类或接口
     * @param subType          需要检查的子类
     * @param errorMsgTemplate 异常时的消息模板
     * @param params           参数列表
     * @throws IllegalArgumentException 如果子类非继承父类，抛出此异常
     */
    public static void isAssignable(@Nullable Class<?> superType,
                                    @Nullable Class<?> subType,
                                    String errorMsgTemplate, Object... params) throws IllegalArgumentException {
        notNull(superType, "Type to check against must not be null");
        if (subType == null || !superType.isAssignableFrom(subType)) {
            throw new IllegalArgumentException(StrUtil.format(errorMsgTemplate, params));
        }
    }

    /**
     * 检查值是否在指定范围内
     *
     * @param value            值
     * @param min              最小值（包含）
     * @param max              最大值（包含）
     * @return 经过检查后的值
     */
    public static <N extends Number & Comparable<N>> N checkBetween(N value, N min, N max) {
        return checkBetween(value, min, max, TEMPLATE_VALUE_MUST_BE_BETWEEN_AND, min, max);
    }

    /**
     * 检查值是否在指定范围内
     *
     * @param value            值
     * @param min              最小值（包含）
     * @param max              最大值（包含）
     * @param errorMsgTemplate 异常信息模板，类似于"aa{}bb{}cc"
     * @param params           异常信息参数，用于替换"{}"占位符
     * @return 经过检查后的值
     */
    public static <N extends Number & Comparable<N>> N checkBetween(N value,
                                                                    N min,
                                                                    N max,
                                                                    String errorMsgTemplate,
                                                                    Object... params) {
        return checkBetween(value, min, max,
                () -> new IllegalArgumentException(StrUtil.format(errorMsgTemplate, params)));
    }

    /**
     * 检查值是否在指定范围内
     *
     * @param <X>           异常类型
     * @param value         值
     * @param min           最小值（包含）
     * @param max           最大值（包含）
     * @param errorSupplier 错误抛出异常附带的消息生产接口
     * @return 经过检查后的值
     * @throws X if value is out of bound
     */
    @SuppressWarnings("checkstyle:Indentation")
    public static <X extends Throwable, N extends Number & Comparable<N>>
    N checkBetween(N value, N min, N max, Supplier<? extends X> errorSupplier) throws X {
        if (value.compareTo(min) < 0 || value.compareTo(max) > 0) {
            throw errorSupplier.get();
        }

        return value;
    }

}

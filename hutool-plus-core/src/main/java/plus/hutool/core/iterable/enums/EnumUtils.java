package plus.hutool.core.iterable.enums;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.lang.Nullable;
import plus.hutool.core.iterable.collection.ArrayUtils;
import plus.hutool.core.lang.ReflectUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 枚举工具类
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class EnumUtils {
    private EnumUtils() {}

    private static final String ENUM_CLASS_INTERNAL_FIELD_NAME = "name";
    private static final String ENUM_CLASS_INTERNAL_FIELD_ORDINAL = "ordinal";

    /**
     * 获得枚举类中所有的非静态属性名<br>
     * 除用户自定义的属性名，也包括“name”属性，例如：
     *
     * <pre>
     *   getNonStaticPropNames(FileTypeEnum.class) == ["name", "code", "label"]
     * </pre>
     *
     * @param enumClass 枚举类
     * @return 非静态属性名列表
     */
    public static List<String> getNonStaticPropNames(Class<? extends Enum<?>> enumClass) {
        final Set<String> fieldNames = new LinkedHashSet<>();
        final Field[] fields = ReflectUtil.getFields(enumClass);
        String name;

        fieldNames.add(ENUM_CLASS_INTERNAL_FIELD_NAME);
        for (Field field : fields) {
            name = field.getName();
            if (field.getType().isEnum() || ENUM_CLASS_INTERNAL_FIELD_ORDINAL.equals(name)
                    || ReflectUtils.isStaticField(field)) {
                continue;
            }
            fieldNames.add(name);
        }
        return new ArrayList<>(fieldNames);
    }

    /**
     * 获取枚举类中所有常量的非静态属性名到属性值的映射的列表
     * <p>示例代码和返回结果参见 </p>
     * {@link EnumUtils#getEnumPropNameToPropValueMapList(Class, boolean, Collection)}
     *
     * @param enumClass          枚举类
     * @param lowerCaseNameValue 是否将枚举类属性 [name] 的值小写
     * @return 枚举类中所有常量的非静态属性名到属性值的映射的列表
     */
    public static List<Map<String, Object>> getEnumPropNameToPropValueMapList(Class<? extends Enum<?>> enumClass,
                                                                              boolean lowerCaseNameValue) {
        return getEnumPropNameToPropValueMapList(enumClass, lowerCaseNameValue, Collections.emptyList());
    }

    /**
     * 获取枚举类中所有常量的指定的属性名到属性值的映射
     * <p>示例代码和返回结果参见 </p>
     * {@link EnumUtils#getEnumPropNameToPropValueMapList(Class, boolean, Collection)}
     *
     * @param enumClass                枚举类
     * @param lowerCaseNameValue       是否将枚举类属性 [name] 的值小写
     * @param specifiedField           第一个额外指定的属性名
     * @param remainingSpecifiedFields 余下的其它额外指定的属性名
     * @return 枚举类中除指定的需过滤掉的属性名以外所有常量的属性名到属性值的映射的列表
     */
    public static List<Map<String, Object>> getEnumPropNameToPropValueMapList(Class<? extends Enum<?>> enumClass,
                                                                              boolean lowerCaseNameValue,
                                                                              String specifiedField,
                                                                              String... remainingSpecifiedFields) {
        List<String> specifiedFields = ArrayUtils.toList(specifiedField, remainingSpecifiedFields);
        return getEnumPropNameToPropValueMapList(enumClass, lowerCaseNameValue, specifiedFields);
    }

    /**
     * 获取枚举类中所有常量的指定的属性名到属性值的映射 <br>
     * <p>
     * 如果需要参数 specifiedExtraFieldNames 来指定属性名，
     * 则无须包含内置的 "name" 属性（即：内置的 "name"属性一定会出现在结果中），
     * 为空则表明不指定属性，直接获取所有非静态属性
     * </p>
     * <p>
     * 例如，对于下面定义的枚举类 FileTypeEnum，
     * <pre>
     * public enum FileTypeEnum {
     *     DOC(1, "文档"),
     *     IMAGE(2, "图像"),
     *     VIDEO(3, "视频");
     *
     *     private final int code;
     *     private final String label;
     *
     *     FileTypeEnum(int code, String label) {
     *         this.code = code;
     *         this.label = label;
     *     }
     * }
     * </pre>
     * <p>
     * 调用 getEnumPropNameToPropValueMapList(FileTypeEnum.class, false, Arrays.asList("label"))
     * 的结果转为JSON 字符串后为：
     *
     * <pre>
     * [
     *     {
     *         "name": "DOC",
     *         "label": "文档"
     *     }, {
     *         "name": "IMAGE",
     *         "label": "图像"
     *     }, {
     *         "name": "VIDEO",
     *         "label": "视频"
     *     }
     * ]
     * </pre>
     * </p>
     *
     * @param enumClass          枚举类
     * @param lowerCaseNameValue 是否将枚举类属性 [name] 的值小写
     * @param specifiedFields    除了内置的 "name" 属性以外，额外指定的属性名集合
     * @return 枚举类中除指定的需过滤掉的属性名以外所有常量的属性名到属性值的映射的列表
     */
    public static List<Map<String, Object>> getEnumPropNameToPropValueMapList(Class<? extends Enum<?>> enumClass,
                                                                              boolean lowerCaseNameValue,
                                                                              Collection<String> specifiedFields) {
        List<Map<String, Object>> result = new ArrayList<>();
        List<String> involvedFieldNames = getNonStaticPropNames(enumClass);

        if (CollUtil.isNotEmpty(specifiedFields)) {
            Predicate<String> involvedFieldNamePredicate = fieldName ->
                    fieldName.equals(ENUM_CLASS_INTERNAL_FIELD_NAME) || specifiedFields.contains(fieldName);
            involvedFieldNames = involvedFieldNames.stream().filter(involvedFieldNamePredicate).collect(Collectors.toList());
        }

        for (Enum<?> enumConst : enumClass.getEnumConstants()) {
            Map<String, Object> fieldNameToFieldValueMap = new LinkedHashMap<>();
            for (String fieldName : involvedFieldNames) {
                Object fieldValue = ReflectUtil.getFieldValue(enumConst, fieldName);
                if (fieldName.equals(ENUM_CLASS_INTERNAL_FIELD_NAME) && lowerCaseNameValue) {
                    fieldValue = fieldValue.toString().toLowerCase();
                }
                fieldNameToFieldValueMap.putIfAbsent(fieldName, fieldValue);
            }
            result.add(fieldNameToFieldValueMap);
        }

        return result;
    }


    /**
     * 获取枚举类的第一个值
     *
     * @param enumClass 枚举类
     * @param <E> 枚举类类型
     * @return 枚举类的第一个值
     */
    @Nullable
    public static <E extends Enum<E>> E getFirstEnumValue(Class<E> enumClass) {
        E[] enumConsts = enumClass.getEnumConstants();
        if (enumConsts == null || enumConsts.length == 0) {
            return null;
        } else {
            return enumConsts[0];
        }
    }

    /**
     * 获取枚举类型对象的作为参数传递时的默认参数名
     * <p>
     * 枚举类型对象的默认参数名称约定为：
     * <pre>
     *     paramName = StrUtils.lowerFirst(enumClass.getSimpleName())
     * </pre>
     *
     * @param enumClass 枚举类
     * @return 枚举类型参数的默认参数名称
     */
    public static String defaultParamName(Class<? extends Enum<?>> enumClass) {
        return StrUtil.lowerFirst(enumClass.getSimpleName());
    }

    /**
     * 判断枚举常量是否是指定的枚举常量列表中的任意一个
     *
     * @param lhs        待检查的枚举常量
     * @param first      第一个枚举常量
     * @param remainings 剩余的枚举常量变长参数列表
     * @return 当前枚举常量是否是指定的枚举常量列表中的任意一个
     */
    @SafeVarargs
    public static <E extends Enum<E>> boolean isAnyOf(E lhs, E first, E... remainings) {
        if (lhs == first) {
            return true;
        }

        if (remainings.length > 0) {
            for (E other : remainings) {
                if (lhs == other) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 判断枚举常量是否不是指定的枚举常量列表中的任意一个
     *
     * @param lhs        待检查的枚举常量
     * @param first      第一个枚举常量
     * @param remainings 剩余的枚举常量变长参数列表
     * @return 枚举常量是否不是指定的枚举常量列表中的任意一个
     */
    @SafeVarargs
    public static <E extends Enum<E>> boolean notAnyOf(E lhs, E first, E... remainings) {
        return !isAnyOf(lhs, first, remainings);
    }

}

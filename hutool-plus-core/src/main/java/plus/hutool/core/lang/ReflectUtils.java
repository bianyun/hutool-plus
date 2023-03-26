package plus.hutool.core.lang;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import plus.hutool.core.lang.annotation.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.stream.IntStream;

/**
 * 反射工具类
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings("JavadocDeclaration")
public abstract class ReflectUtils {
    private ReflectUtils() {}

    /**
     * 判断 {@link Field} 是否为 static 字段
     *
     * @param field 字段
     * @return 是否为 static 字段
     */
    public static boolean isStaticField(Field field) {
        return Modifier.isStatic(field.getModifiers());
    }

    /**
     * 判断 {@link Field} 是否为 final 字段
     *
     * @param field 字段
     * @return 是否为 final 字段
     */
    public static boolean isFinalField(Field field) {
        return Modifier.isFinal(field.getModifiers());
    }

    /**
     * 判断 {@link Field} 是否为 static final 字段
     *
     * @param field 字段
     * @return 是否为 static final 字段
     */
    public static boolean isStaticFinalField(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers);
    }

    /**
     * 判断 {@link Field} 是否为 是 final 但不是 static 的字段
     *
     * @param field 字段
     * @return 是否为 是 final 但不是 static 的字段
     */
    public static boolean isFinalButNotStaticField(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isFinal(modifiers) && !Modifier.isStatic(modifiers);
    }

    /**
     * 判断 {@link Field} 是否为 是 static 但不是 final 的字段
     *
     * @param field 字段
     * @return 是否为 是 static 但不是 final 的字段
     */
    public static boolean isStaticButNotFinalField(Field field) {
        int modifiers = field.getModifiers();
        return !Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers);
    }

    /**
     * 获取目标类定义中指定泛型参数名的类型
     *
     * @param targetClazz          目标类
     * @param genericTypeParamName 泛型参数名
     * @return 泛型参数的类型
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> Class<T> getGenericParameterClass(Class<?> targetClazz, String genericTypeParamName) {
        Asserts.notBlank(genericTypeParamName, "泛型参数名不能为空");

        if (targetClazz.isInterface() && targetClazz.getGenericInterfaces().length == 0) {
            return null;
        }

        if (!(targetClazz.getGenericSuperclass() instanceof ParameterizedType)
                && targetClazz.getGenericInterfaces().length == 0) {
            return null;
        }

        ParameterizedType parameterizedType;
        Class<T> result = null;
        if (targetClazz.getGenericSuperclass() instanceof ParameterizedType) {
            parameterizedType = (ParameterizedType) targetClazz.getGenericSuperclass();
            result = (Class<T>) getClassOfTypeParameter(parameterizedType, genericTypeParamName);
        }

        if (result == null) {
            for (Type type : targetClazz.getGenericInterfaces()) {
                if (type instanceof ParameterizedType) {
                    parameterizedType = (ParameterizedType) type;
                    result = (Class<T>) getClassOfTypeParameter(parameterizedType, genericTypeParamName);

                    if (result != null) {
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * 设置类的 static final 字段的值
     *
     * @param clazz 类
     * @param fieldName 字段名
     * @param value 字段的值
     */
    public static void setStaticFinalFieldValue(Class<?> clazz, String fieldName, Object value) {
        Field field = ClassUtil.getDeclaredField(clazz, fieldName);
        Asserts.notNull(field, "类[{}] 中没有定义字段: {}", clazz.getName(), fieldName);

        setStaticFinalFieldValue(field, value);
    }

    /**
     * 设置 static final 字段的值
     *
     * @param field 字段
     * @param value 字段的值
     */
    public static void setStaticFinalFieldValue(Field field, Object value) {
        Asserts.notNull(field, "字段不能为空");

        Class<?> declaringClass = field.getDeclaringClass();
        Asserts.isTrue(isStaticFinalField(field), "类[{}] 中的字段[{}] 没有 static final 限定符",
                declaringClass.getName(), field.getName());

        ReflectUtil.removeFinalModify(field);
        ReflectUtil.setFieldValue(declaringClass, field, value);
    }

    /**
     * 设置对象的 final 字段的值
     *
     * @param obj 对象
     * @param fieldName 字段名
     * @param value 字段的值
     */
    public static void setObjectFinalFieldValue(Object obj, String fieldName, Object value) {
        Asserts.notNull(obj, "对象不能为空");

        Class<?> clazz = obj.getClass();
        String className = clazz.getName();
        Field field = ClassUtil.getDeclaredField(clazz, fieldName);

        Asserts.notNull(field, "类[{}] 中没有定义字段: {}", className, fieldName);
        Asserts.isTrue(isFinalField(field), "类[{}] 中的字段[{}] 没有 final 限定符", className, fieldName);
        Asserts.isTrue(!isStaticField(field), "类[{}] 中的字段[{}] 不能有 static 限定符", className, fieldName);

        ReflectUtil.removeFinalModify(field);
        ReflectUtil.setFieldValue(obj, field, value);
    }

    @Nullable
    private static Class<?> getClassOfTypeParameter(ParameterizedType parameterizedType, String genericTypeParamName) {
        try {
            int genericParamIndex = resolveGenericParamIndex(parameterizedType, genericTypeParamName);
            return ((Class<?>) parameterizedType.getActualTypeArguments()[genericParamIndex]);
        } catch (Exception e) {
            return null;
        }
    }

    private static int resolveGenericParamIndex(ParameterizedType parameterizedType, String genericTypeParamName) {
        Class<?> rawType = ((Class<?>) parameterizedType.getRawType());
        TypeVariable<?>[] typeVars = rawType.getTypeParameters();

        return IntStream.range(0, typeVars.length)
                .filter(i -> typeVars[i].getName().equals(genericTypeParamName)).findFirst()
                .orElseThrow(() -> ExceptionUtils.illegalArgumentException(
                        "类[{}] 中没有定义泛型参数[{}]", rawType, genericTypeParamName));
    }
}

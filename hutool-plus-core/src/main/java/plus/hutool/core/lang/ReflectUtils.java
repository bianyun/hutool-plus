package plus.hutool.core.lang;

import org.springframework.lang.Nullable;

import java.lang.reflect.*;
import java.util.stream.IntStream;

/**
 * 反射工具类
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
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

        if (!(targetClazz.getGenericSuperclass() instanceof ParameterizedType) &&
                targetClazz.getGenericInterfaces().length == 0) {
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

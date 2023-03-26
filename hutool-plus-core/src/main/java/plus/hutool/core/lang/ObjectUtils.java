package plus.hutool.core.lang;

/**
 * 对象工具类
 *
 * @author bianyun
 * @date 2023/2/16
 */
@SuppressWarnings("JavadocDeclaration")
public abstract class ObjectUtils {
    private ObjectUtils() {}

    /**
     * 判断对象是否是另一组对象中的任意一个
     *
     * @param lhs 判断的目标对象
     * @param rhsFirst 另一组对象的第一个对象
     * @param rhsRemainings 另一组对象的其余对象
     * @param <T> 对象参数类型
     * @return 对象是否是另一组对象中的任意一个
     */
    @SafeVarargs
    public static <T> boolean isAnyOf(T lhs, T rhsFirst, T... rhsRemainings) {
        if (lhs.equals(rhsFirst)) {
            return true;
        }

        for (T type : rhsRemainings) {
            if (lhs.equals(type)) {
                return true;
            }
        }
        return false;
    }

}

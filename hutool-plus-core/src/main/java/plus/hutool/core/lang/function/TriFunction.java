package plus.hutool.core.lang.function;

/**
 * Represents a function that accepts three arguments and produces a result.
 *
 * <p>Copied from <code>org.elasticsearch.common.TriFunction</code></p>
 *
 * @author bianyun
 * @date 2022/12/06
 *
 * @param <S> the type of the first argument
 * @param <T> the type of the second argument
 * @param <U> the type of the third argument
 * @param <R> the return type
 */
@SuppressWarnings("JavadocDeclaration")
@FunctionalInterface
public interface TriFunction<S, T, U, R> {
    /**
     * Applies this function to the given arguments.
     *
     * @param s the first function argument
     * @param t the second function argument
     * @param u the third function argument
     * @return the result
     */
    R apply(S s, T t, U u);
}

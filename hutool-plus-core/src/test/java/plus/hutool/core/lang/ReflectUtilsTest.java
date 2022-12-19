package plus.hutool.core.lang;

import cn.hutool.core.util.ReflectUtil;
import org.junit.jupiter.api.Test;
import plus.hutool.core.test.enums.TestEnum1;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class ReflectUtilsTest {

    @Test
    void testIsStaticField() {
        assertThat(ReflectUtils.isStaticField(ReflectUtil.getField(String.class, "serialVersionUID"))).isTrue();
        assertThat(ReflectUtils.isStaticField(ReflectUtil.getField(Integer.class, "MIN_VALUE"))).isTrue();
        assertThat(ReflectUtils.isStaticField(ReflectUtil.getField(File.class, "path"))).isFalse();
        assertThat(ReflectUtils.isStaticField(ReflectUtil.getField(File.class, "status"))).isFalse();
    }

    @Test
    void testIsFinalField() {
        assertThat(ReflectUtils.isFinalField(ReflectUtil.getField(String.class, "serialVersionUID"))).isTrue();
        assertThat(ReflectUtils.isFinalField(ReflectUtil.getField(Integer.class, "MIN_VALUE"))).isTrue();
        assertThat(ReflectUtils.isFinalField(ReflectUtil.getField(File.class, "path"))).isTrue();
        assertThat(ReflectUtils.isFinalField(ReflectUtil.getField(File.class, "status"))).isFalse();
    }

    @Test
    void testIsStaticFinalField() {
        assertThat(ReflectUtils.isStaticFinalField(ReflectUtil.getField(String.class, "serialVersionUID"))).isTrue();
        assertThat(ReflectUtils.isStaticFinalField(ReflectUtil.getField(Integer.class, "MIN_VALUE"))).isTrue();
        assertThat(ReflectUtils.isStaticFinalField(ReflectUtil.getField(File.class, "path"))).isFalse();
        assertThat(ReflectUtils.isStaticFinalField(ReflectUtil.getField(File.class, "status"))).isFalse();
    }

    @Test
    void testGetGenericParameterClass() {
        assertThat(ReflectUtils.getGenericParameterClass(SubClass.class, "K")).isEqualTo(String.class);
        assertThat(ReflectUtils.getGenericParameterClass(SubClass.class, "V")).isEqualTo(TestEnum1.class);
        assertThat(ReflectUtils.getGenericParameterClass(SubClass.class, "E")).isNull();
        assertThat(ReflectUtils.getGenericParameterClass(SubInterface.class, "S")).isEqualTo(String.class);
        assertThat(ReflectUtils.getGenericParameterClass(SubInterface.class, "T")).isEqualTo(Integer.class);
        assertThat(ReflectUtils.getGenericParameterClass(SubInterface.class, "X")).isNull();

        assertThat(ReflectUtils.getGenericParameterClass(Integer.class, "T")).isEqualTo(Integer.class);
        assertThat(ReflectUtils.getGenericParameterClass(Integer.class, "S")).isNull();

        assertThat(ReflectUtils.getGenericParameterClass(BaseInterface.class, "S")).isNull();
        assertThat(ReflectUtils.getGenericParameterClass(BaseInterface.class, "T")).isNull();
        assertThat(ReflectUtils.getGenericParameterClass(AbstractGenericBaseClass.class, "K")).isNull();
        assertThat(ReflectUtils.getGenericParameterClass(AbstractGenericBaseClass.class, "V")).isNull();
    }

    @SuppressWarnings("unused")
    private interface BaseInterface<S, T> {
        S baseMethod(T t);
    }

    @SuppressWarnings("unused")
    private interface SubInterface extends BaseInterface<String, Integer> {
        void subMethod();
    }

    @SuppressWarnings("unused")
    private static abstract class AbstractGenericBaseClass<K, V extends Enum<V>> {
        private final K k;
        private final V v;

        public K getK() {
            return k;
        }

        public V getV() {
            return v;
        }

        public AbstractGenericBaseClass(K k, V v) {
            this.k = k;
            this.v = v;
        }
    }

    @SuppressWarnings("unused")
    private static class SubClass extends AbstractGenericBaseClass<String, TestEnum1> {

        public SubClass(String s, TestEnum1 testEnum1) {
            super(s, testEnum1);
        }
    }
}

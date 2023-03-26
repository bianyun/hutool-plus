package plus.hutool.core.lang;

import cn.hutool.core.util.ReflectUtil;
import org.junit.jupiter.api.Test;
import plus.hutool.core.test.enums.TestEnum1;

import java.io.File;
import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
        assertThat(ReflectUtils.isStaticFinalField(ReflectUtil.getField(SubClass.class, "staticStringField"))).isFalse();
    }

    @Test
    void testIsFinalButNotStaticField() {
        assertThat(ReflectUtils.isFinalButNotStaticField(ReflectUtil.getField(String.class, "serialVersionUID"))).isFalse();
        assertThat(ReflectUtils.isFinalButNotStaticField(ReflectUtil.getField(Integer.class, "MIN_VALUE"))).isFalse();
        assertThat(ReflectUtils.isFinalButNotStaticField(ReflectUtil.getField(File.class, "path"))).isTrue();
        assertThat(ReflectUtils.isFinalButNotStaticField(ReflectUtil.getField(SubClass.class, "staticStringField"))).isFalse();
    }

    @Test
    void testIsStaticButNotFinalField() {
        assertThat(ReflectUtils.isStaticButNotFinalField(ReflectUtil.getField(String.class, "serialVersionUID"))).isFalse();
        assertThat(ReflectUtils.isStaticButNotFinalField(ReflectUtil.getField(Integer.class, "MIN_VALUE"))).isFalse();
        assertThat(ReflectUtils.isStaticButNotFinalField(ReflectUtil.getField(String.class, "hash"))).isFalse();
        assertThat(ReflectUtils.isStaticButNotFinalField(ReflectUtil.getField(SubClass.class, "staticStringField"))).isTrue();
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

    @Test
    void testSetStaticFinalFieldValue1() {
        assertThat(TestClass.getStaticFinalString()).isEqualTo("hello");

        String staticFinalFieldName = "STATIC_FINAL_STRING";
        ReflectUtils.setStaticFinalFieldValue(TestClass.class, staticFinalFieldName, "changed value");
        assertThat(ReflectUtil.getFieldValue(TestClass.class, staticFinalFieldName)).isEqualTo("changed value");
    }

    @Test
    void testSetStaticFinalFieldValue2() {
        assertThat(TestClass.getStaticFinalString()).isEqualTo("hello");

        String staticFinalFieldName = "STATIC_FINAL_STRING";
        Field field = ReflectUtil.getField(TestClass.class, staticFinalFieldName);

        ReflectUtils.setStaticFinalFieldValue(field, "changed value 2");
        assertThat(ReflectUtil.getStaticFieldValue(field)).isEqualTo("changed value 2");
    }

    @Test
    void testSetObjectFinalFieldValue() {
        TestClass testObj = new TestClass("world");
        assertThat(testObj.getFinalBuNotStaticStr()).isEqualTo("world");

        String finalFieldName = "finalBuNotStaticStr";
        ReflectUtils.setObjectFinalFieldValue(testObj, finalFieldName, "changed value 3");
        assertThat(testObj.getFinalBuNotStaticStr()).isEqualTo("changed value 3");

        assertThatThrownBy(() -> ReflectUtils.setObjectFinalFieldValue(testObj, "STATIC_FINAL_STRING", "new value"))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("类[%s] 中的字段[%s] 不能有 static 限定符",
                        TestClass.class.getName(), "STATIC_FINAL_STRING");
    }

    @SuppressWarnings("unused")
    private interface NonParameterizedInterface {
        default void doNothing() {}
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
    private static class SubClass extends AbstractGenericBaseClass<String, TestEnum1> implements NonParameterizedInterface {
        static String staticStringField = "abc";

        public SubClass(String s, TestEnum1 testEnum1) {
            super(s, testEnum1);
        }
    }
}

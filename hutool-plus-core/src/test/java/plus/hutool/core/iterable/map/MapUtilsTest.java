package plus.hutool.core.iterable.map;

import org.junit.jupiter.api.Test;
import plus.hutool.core.iterable.enums.EnumUtils;
import plus.hutool.core.test.enums.TestEnum1;
import plus.hutool.core.test.enums.TestEnum3;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class MapUtilsTest {

    @Test
    void testGetParamValue1() {
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("strParam", "strValue");
        paramsMap.put("intParam", 1);
        paramsMap.put("longParam", Long.MAX_VALUE);
        paramsMap.put("doubleParam", 1.23456789);
        paramsMap.put("floatParam", 1.234f);

        String strValue = MapUtils.getParamValue(paramsMap, "strParam");
        int intValue = MapUtils.getParamValue(paramsMap, "intParam");
        long longValue = MapUtils.getParamValue(paramsMap, "longParam");
        double doubleValue = MapUtils.getParamValue(paramsMap, "doubleParam");
        float floatValue = MapUtils.getParamValue(paramsMap, "floatParam");

        assertThat(strValue).isEqualTo("strValue");
        assertThat(intValue).isEqualTo(1);
        assertThat(longValue).isEqualTo(Long.MAX_VALUE);
        assertThat(doubleValue).isEqualTo(1.23456789);
        assertThat(floatValue).isEqualTo(1.234f);
    }

    @Test
    void testGetParamValue1_ThrowsClassCastException() {
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("strParam", "strValue");

        assertThatThrownBy(() -> System.out.println(((int) MapUtils.getParamValue(paramsMap, "strParam"))))
                .isInstanceOf(ClassCastException.class);
    }

    @Test
    void testGetParamValue2() {
        final Map<String, Object> paramsMap = new HashMap<>();

        assertThat(MapUtils.getParamValue(paramsMap, "strParam", "strValue")).isEqualTo("strValue");
        assertThat(MapUtils.getParamValue(paramsMap, "intParam", 1)).isEqualTo(1);
        assertThat(MapUtils.getParamValue(paramsMap, "longParam", Long.MAX_VALUE)).isEqualTo(Long.MAX_VALUE);
        assertThat(MapUtils.getParamValue(paramsMap, "doubleParam", 1.23456789)).isEqualTo(1.23456789);
        assertThat(MapUtils.getParamValue(paramsMap, "floatParam", 1.234f)).isEqualTo(1.234f);
    }

    @Test
    void testGetParamValue2_ThrowsClassCastException() {
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("strParam", 1);

        assertThatThrownBy(() -> System.out.println(MapUtils.getParamValue(paramsMap, "strParam", "defaultValue")))
                .isInstanceOf(ClassCastException.class);
    }

    @Test
    void testGetEnumValue() {
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(EnumUtils.defaultParamName(TestEnum1.class), TestEnum1.ENUM_CONST_2);

        assertThat(MapUtils.getEnumValue(paramsMap, TestEnum1.class)).isEqualTo(TestEnum1.ENUM_CONST_2);
    }

    @Test
    void testGetEnumValue_ThrowsClassCastException() {
        final Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put(EnumUtils.defaultParamName(TestEnum1.class), TestEnum3.ENUM_CONST_1);

        assertThatThrownBy(() -> MapUtils.getEnumValue(paramsMap, TestEnum1.class)).isInstanceOf(ClassCastException.class);
    }

    @Test
    void testParamsMapBuilder1() {
        assertThat(MapUtils.paramsMapBuilder()).isExactlyInstanceOf(ParamsMapBuilder.class);
        assertThat(MapUtils.paramsMapBuilder()).isNotEqualTo(MapUtils.paramsMapBuilder());
    }

    @Test
    void testParamsMapBuilder2() {
        final Map<String, Object> paramsMap = new HashMap<>();
        assertThat(MapUtils.paramsMapBuilder(paramsMap)).isExactlyInstanceOf(ParamsMapBuilder.class);
        assertThat(MapUtils.paramsMapBuilder(paramsMap)).isNotEqualTo(MapUtils.paramsMapBuilder(paramsMap));
    }

    @Test
    void testBuildUnmodifiableMap1() {
        final Function<TestEnum1, String> keyGetter = TestEnum1::getLabel;
        final Map<String, TestEnum1> result = MapUtils.buildUnmodifiableMap(keyGetter,
                TestEnum1.ENUM_CONST_1, TestEnum1.ENUM_CONST_2, TestEnum1.ENUM_CONST_3);

        assertThat(result)
                .hasSize(3)
                .containsKey(TestEnum1.ENUM_CONST_1.getLabel())
                .containsKey(TestEnum1.ENUM_CONST_2.getLabel())
                .containsKey(TestEnum1.ENUM_CONST_3.getLabel())
                .containsValues(TestEnum1.ENUM_CONST_1, TestEnum1.ENUM_CONST_2, TestEnum1.ENUM_CONST_3);

        final Map<String, TestEnum1> result2 = MapUtils.buildUnmodifiableMap(keyGetter, TestEnum1.ENUM_CONST_1);
        assertThat(result2)
                .hasSize(1)
                .containsKey(TestEnum1.ENUM_CONST_1.getLabel())
                .containsValue(TestEnum1.ENUM_CONST_1);

        assertThatThrownBy(() -> result2.put("newKey", TestEnum1.ENUM_CONST_2))
                .isExactlyInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void testBuildUnmodifiableMap2() {
        final Function<TestEnum1, String> keyGetter = TestEnum1::getLabel;
        final Map<String, TestEnum1> map = new HashMap<>();
        final Map<String, TestEnum1> result = MapUtils.buildUnmodifiableMap(keyGetter, map,
                TestEnum1.ENUM_CONST_1, TestEnum1.ENUM_CONST_2, TestEnum1.ENUM_CONST_3);

        assertThat(result)
                .hasSize(3)
                .containsKey(TestEnum1.ENUM_CONST_1.getLabel())
                .containsKey(TestEnum1.ENUM_CONST_2.getLabel())
                .containsKey(TestEnum1.ENUM_CONST_3.getLabel())
                .containsValues(TestEnum1.ENUM_CONST_1, TestEnum1.ENUM_CONST_2, TestEnum1.ENUM_CONST_3);

    }
}

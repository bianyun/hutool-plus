package plus.hutool.core.iterable.enums;

import org.junit.jupiter.api.Test;
import plus.hutool.core.test.enums.TestEnum1;
import plus.hutool.core.test.enums.TestEnum2;
import plus.hutool.core.test.enums.TestEnum3;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class EnumUtilsTest {

    @Test
    void testGetNonStaticPropNames() {
        assertThat(EnumUtils.getNonStaticPropNames(TestEnum1.class)).isEqualTo(Arrays.asList("name", "label", "code"));
    }

    @Test
    void testGetEnumPropNameToPropValueMapList1() {
        final List<Map<String, Object>> result1 = EnumUtils.getEnumPropNameToPropValueMapList(TestEnum1.class, false);
        assertThat(result1).hasSize(3);
        assertThat(result1.get(0)).hasSize(3);
        assertThat(result1.get(0).get("name")).isEqualTo("ENUM_CONST_1");
        assertThat(result1.get(0).get("label")).isEqualTo("const_1");
        assertThat(result1.get(0).get("code")).isEqualTo(1);

        final List<Map<String, Object>> result2 = EnumUtils.getEnumPropNameToPropValueMapList(TestEnum1.class, true);
        assertThat(result2).hasSize(3);
        assertThat(result2.get(0)).hasSize(3);
        assertThat(result2.get(0).get("name")).isEqualTo("enum_const_1");
        assertThat(result2.get(0).get("label")).isEqualTo("const_1");
        assertThat(result2.get(0).get("code")).isEqualTo(1);
    }

    @Test
    void testGetEnumPropNameToPropValueMapList2() {
        final List<Map<String, Object>> result1 = EnumUtils.getEnumPropNameToPropValueMapList(TestEnum1.class, false, "label");
        assertThat(result1).hasSize(3);
        assertThat(result1.get(0)).hasSize(2);
        assertThat(result1.get(0).get("name")).isEqualTo("ENUM_CONST_1");
        assertThat(result1.get(0).get("label")).isEqualTo("const_1");
        assertThat(result1.get(0).get("code")).isNull();

        final List<Map<String, Object>> result2 = EnumUtils.getEnumPropNameToPropValueMapList(TestEnum1.class, true, "code");
        assertThat(result2).hasSize(3);
        assertThat(result2.get(0)).hasSize(2);
        assertThat(result2.get(0).get("name")).isEqualTo("enum_const_1");
        assertThat(result2.get(0).get("label")).isNull();
        assertThat(result2.get(0).get("code")).isEqualTo(1);
    }

    @Test
    void testGetEnumPropNameToPropValueMapList3() {
        final List<Map<String, Object>> result1 = EnumUtils.getEnumPropNameToPropValueMapList(
                TestEnum1.class, false, Collections.singleton("label"));
        assertThat(result1).hasSize(3);
        assertThat(result1.get(0)).hasSize(2);
        assertThat(result1.get(0).get("name")).isEqualTo("ENUM_CONST_1");
        assertThat(result1.get(0).get("label")).isEqualTo("const_1");
        assertThat(result1.get(0).get("code")).isNull();

        final List<Map<String, Object>> result2 = EnumUtils.getEnumPropNameToPropValueMapList(
                TestEnum1.class, true, Arrays.asList("code", "label"));
        assertThat(result2).hasSize(3);
        assertThat(result2.get(0)).hasSize(3);
        assertThat(result2.get(0).get("name")).isEqualTo("enum_const_1");
        assertThat(result2.get(0).get("label")).isEqualTo("const_1");
        assertThat(result2.get(0).get("code")).isEqualTo(1);
    }

    @Test
    void testGetFirstEnumValue() {
        assertThat(EnumUtils.getFirstEnumValue(TestEnum1.class)).isEqualTo(TestEnum1.ENUM_CONST_1);
        assertThat(EnumUtils.getFirstEnumValue(TestEnum1.class)).isNotNull();
        assertThat(Objects.requireNonNull(EnumUtils.getFirstEnumValue(TestEnum1.class)).getLabel()).isEqualTo("const_1");
        assertThat(Objects.requireNonNull(EnumUtils.getFirstEnumValue(TestEnum1.class)).getCode()).isEqualTo(1);

        assertThat(EnumUtils.getFirstEnumValue(TestEnum2.class)).isNull();
        assertThat(EnumUtils.getFirstEnumValue(TestEnum3.class)).isNotNull();

        assertThat(TestEnum3.ENUM_CONST_1.getLabel()).isEqualTo("test_enum3_const_1");
        assertThat(TestEnum3.ENUM_CONST_1.getCode()).isEqualTo(1);
    }

    @Test
    void testDefaultParamName() {
        assertThat(EnumUtils.defaultParamName(TestEnum1.class)).isEqualTo("testEnum1");
    }

    @Test
    void testIsAnyOf() {
        assertThat(EnumUtils.isAnyOf(TestEnum1.ENUM_CONST_1, TestEnum1.ENUM_CONST_2, TestEnum1.ENUM_CONST_3)).isFalse();
        assertThat(EnumUtils.isAnyOf(TestEnum1.ENUM_CONST_1, TestEnum1.ENUM_CONST_2, TestEnum1.ENUM_CONST_1)).isTrue();
    }

    @Test
    void testNotAnyOf() {
        assertThat(EnumUtils.notAnyOf(TestEnum1.ENUM_CONST_1, TestEnum1.ENUM_CONST_2, TestEnum1.ENUM_CONST_3)).isTrue();
        assertThat(EnumUtils.notAnyOf(TestEnum1.ENUM_CONST_1, TestEnum1.ENUM_CONST_1, TestEnum1.ENUM_CONST_2)).isFalse();
        assertThat(EnumUtils.notAnyOf(TestEnum1.ENUM_CONST_1, TestEnum1.ENUM_CONST_2, TestEnum1.ENUM_CONST_1)).isFalse();
    }

}

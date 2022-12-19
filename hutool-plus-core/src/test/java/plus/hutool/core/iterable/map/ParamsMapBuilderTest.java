package plus.hutool.core.iterable.map;

import org.junit.jupiter.api.Test;
import plus.hutool.core.iterable.enums.EnumUtils;
import plus.hutool.core.test.enums.TestEnum1;
import plus.hutool.core.test.enums.TestEnum3;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ParamsMapBuilderTest {

    @Test
    void testParamsMapBuilder() {
        ParamsMapBuilder builder = new ParamsMapBuilder(new HashMap<>(4));

        assertThat(builder.putParamValue("strParam", "strValue")).isEqualTo(builder);
        assertThat(builder.putParamValue("intParam", 1)).isEqualTo(builder);
        assertThat(builder.putParamValue("longParam", Long.MAX_VALUE)).isEqualTo(builder);
        assertThat(builder.putParamValue("doubleParam", 1.23456789)).isEqualTo(builder);
        assertThat(builder.putParamValue("floatParam", 1.234f)).isEqualTo(builder);
        assertThat(builder.putEnumValue(TestEnum1.ENUM_CONST_1)).isEqualTo(builder);
        assertThat(builder.putEnumValue(TestEnum3.ENUM_CONST_2)).isEqualTo(builder);

        Map<String, Object> paramsMap = builder.build();

        assertThat(paramsMap.get("strParam")).isEqualTo("strValue");
        assertThat(paramsMap.get("intParam")).isEqualTo(1);
        assertThat(paramsMap.get("longParam")).isEqualTo(Long.MAX_VALUE);
        assertThat(paramsMap.get("doubleParam")).isEqualTo(1.23456789);
        assertThat(paramsMap.get("floatParam")).isEqualTo(1.234f);
        assertThat(paramsMap.get(EnumUtils.defaultParamName(TestEnum1.class))).isEqualTo(TestEnum1.ENUM_CONST_1);
        assertThat(paramsMap.get(EnumUtils.defaultParamName(TestEnum3.class))).isEqualTo(TestEnum3.ENUM_CONST_2);
    }

}

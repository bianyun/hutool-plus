package plus.hutool.spring5.core.converter;

import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;
import plus.hutool.spring5.test.enums.TestEnum1;

import static org.assertj.core.api.Assertions.assertThat;

class StringToEnumConverterFactoryTest {

    @Test
    void testGetConverter() {
        final Converter<String, TestEnum1> converter = new StringToEnumConverterFactory().getConverter(TestEnum1.class);

        assertThat(converter.convert("ENUM_CONST_1")).isEqualTo(TestEnum1.ENUM_CONST_1);
        assertThat(converter.convert("enum_const_1")).isEqualTo(TestEnum1.ENUM_CONST_1);
    }
}

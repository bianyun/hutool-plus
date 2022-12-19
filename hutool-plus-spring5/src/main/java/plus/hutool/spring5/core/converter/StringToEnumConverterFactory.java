package plus.hutool.spring5.core.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;


/**
 * 通用的字符串转换成枚举类型的转换器工厂
 *
 * <p>用于在 Controller 中将 请求参数中 字符串格式的枚举值自动转换为枚举类型</p>
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings("JavadocDeclaration")
public class StringToEnumConverterFactory implements ConverterFactory<String, Enum<?>> {

    @SuppressWarnings({"unchecked", "rawtypes"})
    @Override
    public <T extends Enum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverter(targetType);
    }

    private static class StringToEnumConverter<T extends Enum<T>> implements Converter<String, T> {

        private final Class<T> enumType;

        public StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            return Enum.valueOf(this.enumType, source.trim().toUpperCase());
        }
    }

}

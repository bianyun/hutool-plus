package plus.hutool.spring5.test.enums;

import plus.hutool.core.iterable.enums.LabelProvidedEnum;

/**
 * 测试用的枚举类1
 *
 * @author bianyun
 * @date 2022/11/28
 */
@SuppressWarnings({"JavadocDeclaration", "unused"})
public enum TestEnum3 implements LabelProvidedEnum {
    ENUM_CONST_1("test_enum3_const_1", 1),
    ENUM_CONST_2("test_enum3_const_2", 2),
    ENUM_CONST_3("test_enum3_const_3", 3);

    private final String label;
    private final Integer code;

    public String getLabel() {
        return label;
    }

    public Integer getCode() {
        return code;
    }

    TestEnum3(String label, Integer code) {
        this.label = label;
        this.code = code;
    }
}

package plus.hutool.spring5.test.enums;

/**
 * 测试用的枚举类1
 *
 * @author bianyun
 * @date 2022/11/28
 */
@SuppressWarnings({"JavadocDeclaration", "unused"})
public enum TestEnum1 {
    ENUM_CONST_1("const_1", 1),
    ENUM_CONST_2("const_2", 2),
    ENUM_CONST_3("const_3", 3);

    private final String label;
    private final Integer code;

    public String getLabel() {
        return label;
    }

    public Integer getCode() {
        return code;
    }

    TestEnum1(String label, Integer code) {
        this.label = label;
        this.code = code;
    }
}

package plus.hutool.core.lang;

@SuppressWarnings("unused")
public class TestClass {
    private static final String STATIC_FINAL_STRING = "hello";

    private final String finalBuNotStaticStr;

    public TestClass(String finalBuNotStaticStr) {
        this.finalBuNotStaticStr = finalBuNotStaticStr;
    }

    static String getStaticFinalString() {
        return STATIC_FINAL_STRING;
    }

    public String getFinalBuNotStaticStr() {
        return finalBuNotStaticStr;
    }
}

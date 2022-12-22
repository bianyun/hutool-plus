package plus.hutool.core.lang;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import plus.hutool.core.iterable.collection.ArrayUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static plus.hutool.core.lang.Asserts.TEMPLATE_VALUE_MUST_BE_BETWEEN_AND;

@SuppressWarnings("ConstantConditions")
class AssertsTest {

    @Test
    void testIsTrue1() {
        Asserts.isTrue(true, () -> new RuntimeException("message"));
        assertThatThrownBy(() -> Asserts.isTrue(false, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
    }

    @Test
    void testIsTrue2() {
        Asserts.isTrue(true, "errorMsg: {}", "params");
        assertThatThrownBy(() -> Asserts.isTrue(false, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
    }

    @Test
    void testIsFalse1() {
        Asserts.isFalse(false, () -> new RuntimeException("message"));
        assertThatThrownBy(() -> Asserts.isFalse(true, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
    }

    @Test
    void testIsFalse2() {
        Asserts.isFalse(false, "errorMsg: {}", "params");
        assertThatThrownBy(() -> Asserts.isFalse(true, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
    }

    @Test
    void testIsNull1() {
        Asserts.isNull(null, () -> new RuntimeException("message"));
        assertThatThrownBy(() -> Asserts.isNull("object", () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
    }

    @Test
    void testIsNull2() {
        Asserts.isNull(null, "errorMsg: {}", "params");
        assertThatThrownBy(() -> Asserts.isNull("object", "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
    }


    @Test
    void testNotNull1() {
        assertThat(Asserts.notNull("object", () -> new RuntimeException("message"))).isEqualTo("object");
        assertThatThrownBy(() -> Asserts.notNull(null, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
    }

    @Test
    void testNotNull2() {
        assertThat(Asserts.notNull("object", "errorMsg: {}", "params")).isEqualTo("object");
        assertThatThrownBy(() -> Asserts.notNull(null, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
    }

    @Test
    void testNotBlank1() {
        assertThat(Asserts.notBlank("text", () -> new RuntimeException("message"))).isEqualTo("text");
        assertThatThrownBy(() -> Asserts.notBlank("  ", () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
    }

    @Test
    void testNotBlank2() {
        assertThat(Asserts.notBlank("text", "errorMsg: {}", "params")).isEqualTo("text");
        assertThatThrownBy(() -> Asserts.notBlank("  ", "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
    }

    @Test
    void testNotEmpty1() {
        final String[] strArray = new String[]{"item1", "item2"};
        final Integer[] intArray = new Integer[]{1, 2};

        assertThat(Asserts.notEmptyArray(strArray, () -> new RuntimeException("message"))).isEqualTo(strArray);
        assertThat(Asserts.notEmptyArray(intArray, () -> new RuntimeException("message"))).isEqualTo(intArray);

        assertThatThrownBy(() -> Asserts.notEmptyArray(new String[0], () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
        assertThatThrownBy(() -> Asserts.notEmptyArray(new String[]{}, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
        assertThatThrownBy(() -> Asserts.notEmptyArray(new Integer[0], () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
        assertThatThrownBy(() -> Asserts.notEmptyArray(new Integer[]{}, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
    }

    @Test
    void testNotEmpty2() {
        final String[] strArray = new String[]{"item1", "item2"};
        final Integer[] intArray = new Integer[]{1, 2};

        assertThat(Asserts.notEmptyArray(strArray, "errorMsg: {}", "params")).isEqualTo(strArray);
        assertThat(Asserts.notEmptyArray(intArray, "errorMsg: {}", "params")).isEqualTo(intArray);

        assertThatThrownBy(() -> Asserts.notEmptyArray(null, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
        assertThatThrownBy(() -> Asserts.notEmptyArray(new String[0], "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
        assertThatThrownBy(() -> Asserts.notEmptyArray(new String[]{}, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
        assertThatThrownBy(() -> Asserts.notEmptyArray(new Integer[0], "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
        assertThatThrownBy(() -> Asserts.notEmptyArray(new Integer[]{}, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
    }

    @Test
    void testNotEmpty3() {
        final Set<String> strSet = ArrayUtils.toSet("hello", "world");
        final List<String> strList = Arrays.asList("hello", "world");

        assertThat(Asserts.notEmpty(strSet, () -> new RuntimeException("message"))).isEqualTo(strSet);
        assertThat(Asserts.notEmpty(strList, () -> new RuntimeException("message"))).isEqualTo(strList);

        final Set<String> emptyStrSet = Collections.emptySet();
        final List<String> emptyStrList = Collections.emptyList();

        assertThatThrownBy(() -> Asserts.notEmpty(null, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
        assertThatThrownBy(() -> Asserts.notEmpty(emptyStrSet, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
        assertThatThrownBy(() -> Asserts.notEmpty(emptyStrList, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
    }

    @Test
    void testNotEmpty4() {
        final Set<String> strSet = ArrayUtils.toSet("hello", "world");
        final List<String> strList = Arrays.asList("hello", "world");

        assertThat(Asserts.notEmpty(strSet, "errorMsg: {}", "params")).isEqualTo(strSet);
        assertThat(Asserts.notEmpty(strList, "errorMsg: {}", "params")).isEqualTo(strList);

        final Set<String> emptyStrSet = Collections.emptySet();
        final List<String> emptyStrList = Collections.emptyList();

        assertThatThrownBy(() -> Asserts.notEmpty(null, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
        assertThatThrownBy(() -> Asserts.notEmpty(emptyStrSet, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
        assertThatThrownBy(() -> Asserts.notEmpty(emptyStrList, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
    }

    @Test
    void testNotEmpty5() {
        final Map<String, String> strToStrMap = MapUtil.<String, String>builder()
                .put("key1", "value1")
                .put("key2", "value2")
                .build();
        final Map<String, Integer> strToIntMap = MapUtil.<String, Integer>builder()
                .put("key1", 1)
                .put("key2", 2)
                .build();

        assertThat(Asserts.notEmptyMap(strToStrMap, () -> new RuntimeException("message"))).isEqualTo(strToStrMap);
        assertThat(Asserts.notEmptyMap(strToIntMap, () -> new RuntimeException("message"))).isEqualTo(strToIntMap);

        final Map<String, String> emptyStrToStrMap = Collections.emptyMap();
        final Map<String, Integer> emptyStrToIntMap = Collections.emptyMap();

        assertThatThrownBy(() -> Asserts.notEmptyMap(null, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
        assertThatThrownBy(() -> Asserts.notEmptyMap(emptyStrToStrMap, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
        assertThatThrownBy(() -> Asserts.notEmptyMap(emptyStrToIntMap, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
    }

    @Test
    void testNotEmpty6() {
        final Map<String, String> strToStrMap = MapUtil.<String, String>builder()
                .put("key1", "value1")
                .put("key2", "value2")
                .build();
        final Map<String, Integer> strToIntMap = MapUtil.<String, Integer>builder()
                .put("key1", 1)
                .put("key2", 2)
                .build();

        assertThat(Asserts.notEmptyMap(strToStrMap, "errorMsg: {}", "params")).isEqualTo(strToStrMap);
        assertThat(Asserts.notEmptyMap(strToIntMap, "errorMsg: {}", "params")).isEqualTo(strToIntMap);

        final Map<String, String> emptyStrToStrMap = Collections.emptyMap();
        final Map<String, Integer> emptyStrToIntMap = Collections.emptyMap();

        assertThatThrownBy(() -> Asserts.notEmptyMap(null, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
        assertThatThrownBy(() -> Asserts.notEmptyMap(emptyStrToStrMap, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
        assertThatThrownBy(() -> Asserts.notEmptyMap(emptyStrToIntMap, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
    }

    @Test
    void testIsInstanceOf1() {
        assertThat(Asserts.isInstanceOf(String.class, "str")).isEqualTo("str");
        assertThatThrownBy(() -> Asserts.isInstanceOf(Integer.class, "obj")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testIsInstanceOf2() {
        String paramName = "name";
        String paramValue = "Sheldon";
        assertThat(Asserts.isInstanceOf(String.class, paramValue, "参数[{}] 的类型必须为 String", paramName)).isEqualTo(paramValue);
        assertThatThrownBy(() -> Asserts.isInstanceOf(String.class, 123, "参数[{}] 的类型必须为 String", paramName))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(StrUtil.format("参数[{}] 的类型必须为 String", paramName));
    }

    @Test
    void testIsAssignable1() {
        Asserts.isAssignable(String.class, String.class);
        Asserts.isAssignable(Number.class, Integer.class);
        Asserts.isAssignable(Number.class, Long.class);
        Asserts.isAssignable(Number.class, BigInteger.class);
        Asserts.isAssignable(Number.class, BigDecimal.class);

        assertThatThrownBy(() -> Asserts.isAssignable(String.class, Integer.class)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Asserts.isAssignable(String.class, Long.class)).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> Asserts.isAssignable(Integer.class, BigInteger.class)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void testIsAssignable2() {
        Asserts.isAssignable(String.class, String.class, "errorMsg: {}", "params");
        Asserts.isAssignable(Number.class, Integer.class, "errorMsg: {}", "params");
        Asserts.isAssignable(Number.class, Long.class, "errorMsg: {}", "params");
        Asserts.isAssignable(Number.class, BigInteger.class, "errorMsg: {}", "params");
        Asserts.isAssignable(Number.class, BigDecimal.class, "errorMsg: {}", "params");

        assertThatThrownBy(() -> Asserts.isAssignable(String.class, Integer.class, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
        assertThatThrownBy(() -> Asserts.isAssignable(String.class, null, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
        assertThatThrownBy(() -> Asserts.isAssignable(String.class, Long.class, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
        assertThatThrownBy(() -> Asserts.isAssignable(Integer.class, BigInteger.class, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
    }

    @Test
    void testCheckBetween1() {
        assertThat(Asserts.checkBetween(1, 0, 2)).isEqualTo(1);
        assertThat(Asserts.checkBetween(1, 0, 1)).isEqualTo(1);
        assertThat(Asserts.checkBetween(1, 1, 2)).isEqualTo(1);
        assertThat(Asserts.checkBetween(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE)).isEqualTo(Integer.MIN_VALUE);
        assertThat(Asserts.checkBetween(Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE)).isEqualTo(Integer.MAX_VALUE);

        assertThat(Asserts.checkBetween(1L, 0L, 2L)).isEqualTo(1L);
        assertThat(Asserts.checkBetween(1L, 0L, 1L)).isEqualTo(1L);
        assertThat(Asserts.checkBetween(1L, 1L, 2L)).isEqualTo(1L);
        assertThat(Asserts.checkBetween(Long.MIN_VALUE, Long.MIN_VALUE, Long.MAX_VALUE)).isEqualTo(Long.MIN_VALUE);
        assertThat(Asserts.checkBetween(Long.MAX_VALUE, Long.MIN_VALUE, Long.MAX_VALUE)).isEqualTo(Long.MAX_VALUE);

        assertThat(Asserts.checkBetween(1.0f, 0.0f, 2.0f)).isEqualTo(1.0f);
        assertThat(Asserts.checkBetween(1.0f, 0.0f, 1.0f)).isEqualTo(1.0f);
        assertThat(Asserts.checkBetween(1.0f, 1.0f, 2.0f)).isEqualTo(1.0f);

        assertThat(Asserts.checkBetween(1.0, 0.0, 2.0)).isEqualTo(1.0);
        assertThat(Asserts.checkBetween(1.0, 0.0, 1.0)).isEqualTo(1.0);
        assertThat(Asserts.checkBetween(1.0, 1.0, 2.0)).isEqualTo(1.0);

        assertThat(Asserts.checkBetween(BigInteger.ONE, BigInteger.ZERO, BigInteger.TEN)).isEqualTo(BigInteger.ONE);
        assertThat(Asserts.checkBetween(BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE)).isEqualTo(BigInteger.ONE);
        assertThat(Asserts.checkBetween(BigInteger.ONE, BigInteger.ONE,  BigInteger.TEN)).isEqualTo(BigInteger.ONE);

        assertThat(Asserts.checkBetween(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.TEN)).isEqualTo(BigDecimal.ONE);
        assertThat(Asserts.checkBetween(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE)).isEqualTo(BigDecimal.ONE);
        assertThat(Asserts.checkBetween(BigDecimal.ONE, BigDecimal.ONE,  BigDecimal.TEN)).isEqualTo(BigDecimal.ONE);

        assertThatThrownBy(() -> Asserts.checkBetween(1, 2, 3))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(StrUtil.format(TEMPLATE_VALUE_MUST_BE_BETWEEN_AND, 2, 3));

        assertThatThrownBy(() -> Asserts.checkBetween(1L, 2L, 3L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(StrUtil.format(TEMPLATE_VALUE_MUST_BE_BETWEEN_AND, 2L, 3L));

        assertThatThrownBy(() -> Asserts.checkBetween(1.0f, 2.0f, 3.0f))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(StrUtil.format(TEMPLATE_VALUE_MUST_BE_BETWEEN_AND, 2.0f, 3.0f));

        assertThatThrownBy(() -> Asserts.checkBetween(1.0, 2.0, 3.0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(StrUtil.format(TEMPLATE_VALUE_MUST_BE_BETWEEN_AND, 2.0, 3.0));

        assertThatThrownBy(() -> Asserts.checkBetween(BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(StrUtil.format(TEMPLATE_VALUE_MUST_BE_BETWEEN_AND, BigInteger.ONE, BigInteger.TEN));

        assertThatThrownBy(() -> Asserts.checkBetween(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(StrUtil.format(TEMPLATE_VALUE_MUST_BE_BETWEEN_AND, BigDecimal.ONE, BigDecimal.TEN));
    }

    @Test
    void testCheckBetween2() {
        assertThat(Asserts.checkBetween(1, 0, 2, () -> new RuntimeException("message"))).isEqualTo(1);
        assertThat(Asserts.checkBetween(1, 0, 1, () -> new RuntimeException("message"))).isEqualTo(1);
        assertThat(Asserts.checkBetween(1, 1, 2, () -> new RuntimeException("message"))).isEqualTo(1);
        assertThat(Asserts.checkBetween(Integer.MIN_VALUE, Integer.MIN_VALUE,
                Integer.MAX_VALUE, () -> new RuntimeException("message"))).isEqualTo(Integer.MIN_VALUE);
        assertThat(Asserts.checkBetween(Integer.MAX_VALUE, Integer.MIN_VALUE,
                Integer.MAX_VALUE, () -> new RuntimeException("message"))).isEqualTo(Integer.MAX_VALUE);

        assertThat(Asserts.checkBetween(1L, 0L, 2L, () -> new RuntimeException("message"))).isEqualTo(1L);
        assertThat(Asserts.checkBetween(1L, 0L, 1L, () -> new RuntimeException("message"))).isEqualTo(1L);
        assertThat(Asserts.checkBetween(1L, 1L, 2L, () -> new RuntimeException("message"))).isEqualTo(1L);
        assertThat(Asserts.checkBetween(Long.MIN_VALUE, Long.MIN_VALUE,
                Long.MAX_VALUE, () -> new RuntimeException("message"))).isEqualTo(Long.MIN_VALUE);
        assertThat(Asserts.checkBetween(Long.MAX_VALUE, Long.MIN_VALUE,
                Long.MAX_VALUE, () -> new RuntimeException("message"))).isEqualTo(Long.MAX_VALUE);

        assertThat(Asserts.checkBetween(1.0f, 0.0f, 2.0f, () -> new RuntimeException("message"))).isEqualTo(1.0f);
        assertThat(Asserts.checkBetween(1.0f, 0.0f, 1.0f, () -> new RuntimeException("message"))).isEqualTo(1.0f);
        assertThat(Asserts.checkBetween(1.0f, 1.0f, 2.0f, () -> new RuntimeException("message"))).isEqualTo(1.0f);

        assertThat(Asserts.checkBetween(1.0, 0.0, 2.0, () -> new RuntimeException("message"))).isEqualTo(1.0);
        assertThat(Asserts.checkBetween(1.0, 0.0, 1.0, () -> new RuntimeException("message"))).isEqualTo(1.0);
        assertThat(Asserts.checkBetween(1.0, 1.0, 2.0, () -> new RuntimeException("message"))).isEqualTo(1.0);

        assertThat(Asserts.checkBetween(BigInteger.ONE, BigInteger.ZERO, BigInteger.TEN,
                () -> new RuntimeException("message"))).isEqualTo(BigInteger.ONE);
        assertThat(Asserts.checkBetween(BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE,
                () -> new RuntimeException("message"))).isEqualTo(BigInteger.ONE);
        assertThat(Asserts.checkBetween(BigInteger.ONE, BigInteger.ONE,  BigInteger.TEN,
                () -> new RuntimeException("message"))).isEqualTo(BigInteger.ONE);

        assertThat(Asserts.checkBetween(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.TEN,
                () -> new RuntimeException("message"))).isEqualTo(BigDecimal.ONE);
        assertThat(Asserts.checkBetween(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE,
                () -> new RuntimeException("message"))).isEqualTo(BigDecimal.ONE);
        assertThat(Asserts.checkBetween(BigDecimal.ONE, BigDecimal.ONE,  BigDecimal.TEN,
                () -> new RuntimeException("message"))).isEqualTo(BigDecimal.ONE);

        assertThatThrownBy(() -> Asserts.checkBetween(1, 2, 3, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
        assertThatThrownBy(() -> Asserts.checkBetween(4, 2, 3, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");

        assertThatThrownBy(() -> Asserts.checkBetween(1L, 2L, 3L, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");

        assertThatThrownBy(() -> Asserts.checkBetween(1.0f, 2.0f, 3.0f, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");

        assertThatThrownBy(() -> Asserts.checkBetween(1.0, 2.0, 3.0, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");

        assertThatThrownBy(() -> Asserts.checkBetween(BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");

        assertThatThrownBy(() -> Asserts.checkBetween(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN, () -> new RuntimeException("message")))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("message");
    }

    @Test
    void testCheckBetween3() {
        assertThat(Asserts.checkBetween(1, 0, 2, "errorMsg: {}", "params")).isEqualTo(1);
        assertThat(Asserts.checkBetween(1, 0, 1, "errorMsg: {}", "params")).isEqualTo(1);
        assertThat(Asserts.checkBetween(1, 1, 2, "errorMsg: {}", "params")).isEqualTo(1);
        assertThat(Asserts.checkBetween(Integer.MIN_VALUE, Integer.MIN_VALUE,
                Integer.MAX_VALUE, "errorMsg: {}", "params")).isEqualTo(Integer.MIN_VALUE);
        assertThat(Asserts.checkBetween(Integer.MAX_VALUE, Integer.MIN_VALUE,
                Integer.MAX_VALUE, "errorMsg: {}", "params")).isEqualTo(Integer.MAX_VALUE);

        assertThat(Asserts.checkBetween(1L, 0L, 2L, "errorMsg: {}", "params")).isEqualTo(1L);
        assertThat(Asserts.checkBetween(1L, 0L, 1L, "errorMsg: {}", "params")).isEqualTo(1L);
        assertThat(Asserts.checkBetween(1L, 1L, 2L, "errorMsg: {}", "params")).isEqualTo(1L);
        assertThat(Asserts.checkBetween(Long.MIN_VALUE, Long.MIN_VALUE,
                Long.MAX_VALUE, "errorMsg: {}", "params")).isEqualTo(Long.MIN_VALUE);
        assertThat(Asserts.checkBetween(Long.MAX_VALUE, Long.MIN_VALUE,
                Long.MAX_VALUE, "errorMsg: {}", "params")).isEqualTo(Long.MAX_VALUE);

        assertThat(Asserts.checkBetween(1.0f, 0.0f, 2.0f, "errorMsg: {}", "params")).isEqualTo(1.0f);
        assertThat(Asserts.checkBetween(1.0f, 0.0f, 1.0f, "errorMsg: {}", "params")).isEqualTo(1.0f);
        assertThat(Asserts.checkBetween(1.0f, 1.0f, 2.0f, "errorMsg: {}", "params")).isEqualTo(1.0f);

        assertThat(Asserts.checkBetween(1.0, 0.0, 2.0, "errorMsg: {}", "params")).isEqualTo(1.0);
        assertThat(Asserts.checkBetween(1.0, 0.0, 1.0, "errorMsg: {}", "params")).isEqualTo(1.0);
        assertThat(Asserts.checkBetween(1.0, 1.0, 2.0, "errorMsg: {}", "params")).isEqualTo(1.0);

        assertThat(Asserts.checkBetween(BigInteger.ONE, BigInteger.ZERO, BigInteger.TEN, "errorMsg: {}", "params")).isEqualTo(BigInteger.ONE);
        assertThat(Asserts.checkBetween(BigInteger.ONE, BigInteger.ZERO, BigInteger.ONE, "errorMsg: {}", "params")).isEqualTo(BigInteger.ONE);
        assertThat(Asserts.checkBetween(BigInteger.ONE, BigInteger.ONE,  BigInteger.TEN, "errorMsg: {}", "params")).isEqualTo(BigInteger.ONE);

        assertThat(Asserts.checkBetween(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.TEN, "errorMsg: {}", "params")).isEqualTo(BigDecimal.ONE);
        assertThat(Asserts.checkBetween(BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ONE, "errorMsg: {}", "params")).isEqualTo(BigDecimal.ONE);
        assertThat(Asserts.checkBetween(BigDecimal.ONE, BigDecimal.ONE,  BigDecimal.TEN, "errorMsg: {}", "params")).isEqualTo(BigDecimal.ONE);

        assertThatThrownBy(() -> Asserts.checkBetween(1, 2, 3, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");

        assertThatThrownBy(() -> Asserts.checkBetween(1L, 2L, 3L, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");

        assertThatThrownBy(() -> Asserts.checkBetween(1.0f, 2.0f, 3.0f, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");

        assertThatThrownBy(() -> Asserts.checkBetween(1.0, 2.0, 3.0, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");

        assertThatThrownBy(() -> Asserts.checkBetween(BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");

        assertThatThrownBy(() -> Asserts.checkBetween(BigDecimal.ZERO, BigDecimal.ONE, BigDecimal.TEN, "errorMsg: {}", "params"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("errorMsg: params");
    }
}

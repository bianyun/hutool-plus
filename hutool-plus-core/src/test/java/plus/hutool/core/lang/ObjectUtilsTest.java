package plus.hutool.core.lang;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ObjectUtilsTest {

    @Test
    void testIsAnyOf() {
        assertThat(ObjectUtils.isAnyOf("hello", "hi", "hey")).isFalse();
        assertThat(ObjectUtils.isAnyOf("hello", "world", "hello")).isTrue();
        assertThat(ObjectUtils.isAnyOf("hello", "hello", "world")).isTrue();
    }
}

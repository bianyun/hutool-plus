package plus.hutool.core.iterable.collection;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayUtilsTest {

    @Test
    void testContainsAnyIgnoreCase() {
        final String[] strs = new String[]{"Hi bro", "What's up"};
        assertThat(ArrayUtils.containsAnyIgnoreCase(strs)).isFalse();
        assertThat(ArrayUtils.containsAnyIgnoreCase(strs, "hello", "world")).isFalse();
        assertThat(ArrayUtils.containsAnyIgnoreCase(strs, "Hi", "bro")).isFalse();
        assertThat(ArrayUtils.containsAnyIgnoreCase(strs, "What's", "up")).isFalse();
        assertThat(ArrayUtils.containsAnyIgnoreCase(strs, "Hi  bro")).isFalse();
        assertThat(ArrayUtils.containsAnyIgnoreCase(strs, "HI Bro", "what")).isTrue();
        assertThat(ArrayUtils.containsAnyIgnoreCase(strs, "HI BRO", "up")).isTrue();
    }

    @Test
    void testToSet() {
        assertThat(ArrayUtils.toSet()).isEqualTo(Collections.emptySet());
        assertThat(ArrayUtils.toSet("hello", "world", "hello")).isEqualTo(new HashSet<>(Arrays.asList("hello", "world")));

        String[] arr = new String[]{"hello", "world", "hello"};
        assertThat(ArrayUtils.toSet(arr)).isEqualTo(new HashSet<>(Arrays.asList("hello", "world")));
    }

    @Test
    void testToList() {
        assertThat(ArrayUtils.toList("hello")).isEqualTo(Collections.singletonList("hello"));
        assertThat(ArrayUtils.toList("hello", "world")).isEqualTo(Arrays.asList("hello", "world"));

        String[] arr = new String[]{"hello", "world"};
        assertThat(ArrayUtils.toList("hello", arr)).isEqualTo(Arrays.asList("hello", "hello", "world"));
    }
}

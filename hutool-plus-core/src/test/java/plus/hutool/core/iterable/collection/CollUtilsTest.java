package plus.hutool.core.iterable.collection;

import org.junit.jupiter.api.Test;
import plus.hutool.core.text.string.StrUtils;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CollUtilsTest {

    @Test
    void testSplitTrimToSet() {
        assertThat(CollUtils.splitTrimToSet("  hello  world  ", StrUtils.SPACE))
                .containsExactlyInAnyOrder("hello", "world");
        assertThat(CollUtils.splitTrimToSet("hello world", "o"))
                .containsExactlyInAnyOrder("hell", "w", "rld");
    }

    @Test
    void testUnmodifiableList() {
        assertThat(CollUtils.unmodifiableList("hello"))
                .containsExactly("hello")
                .isUnmodifiable();
        assertThat(CollUtils.unmodifiableList("hello", "hello", "world"))
                .containsExactly("hello", "world")
                .isUnmodifiable();
        assertThat(CollUtils.unmodifiableList("hello", "world", "hello"))
                .containsExactly("hello", "world")
                .isUnmodifiable();

        assertThat(CollUtils.unmodifiableList(true, new String[]{"hello"}))
                .containsExactly("hello")
                .isUnmodifiable();
        assertThat(CollUtils.unmodifiableList(true, new String[]{"hello", "hello"}))
                .containsExactly("hello")
                .isUnmodifiable();
        assertThat(CollUtils.unmodifiableList(true, new String[]{"hello", "hello", "world"}))
                .containsExactly("hello", "world")
                .isUnmodifiable();
        assertThat(CollUtils.unmodifiableList(true, new String[]{"hello", "world", "hello"}))
                .containsExactly("hello", "world")
                .isUnmodifiable();
        assertThat(CollUtils.unmodifiableList(true, new String[]{"world", "hello", "world"}))
                .containsExactly("world", "hello")
                .isUnmodifiable();

        assertThat(CollUtils.unmodifiableList(false, new String[] {"hello", "hello"}))
                .containsExactly("hello", "hello")
                .isUnmodifiable();
        assertThat(CollUtils.unmodifiableList(false, new String[]{"world", "hello", "world"}))
                .containsExactly("world", "hello", "world")
                .isUnmodifiable();

        assertThatThrownBy(() -> CollUtils.unmodifiableList(true, new String[0]))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("元素数组长度必须大于 0");
    }

    @Test
    void testUnmodifiableSet() {
        assertThat(CollUtils.unmodifiableSet("hello"))
                .containsExactly("hello")
                .isUnmodifiable();
        assertThat(CollUtils.unmodifiableSet("hello", "world"))
                .containsExactlyInAnyOrder("hello", "world")
                .isUnmodifiable();
    }

    @Test
    void testMergeToUnmodifiableSet() {
        Set<String> set1 = CollUtils.unmodifiableSet("hello", "world");
        Set<String> set2 = CollUtils.unmodifiableSet("hello", "buddy");
        List<String> list1 = CollUtils.unmodifiableList("good", "morning");
        List<String> list2 = CollUtils.unmodifiableList("java", "kotlin");

        assertThat(CollUtils.mergeToUnmodifiableSet(set1))
                .containsExactlyInAnyOrder("hello", "world")
                .isUnmodifiable();
        assertThat(CollUtils.mergeToUnmodifiableSet(set1, set2))
                .containsExactlyInAnyOrder("hello", "world", "buddy")
                .isUnmodifiable();
        assertThat(CollUtils.mergeToUnmodifiableSet(set1, list1))
                .containsExactlyInAnyOrder("hello", "world", "good", "morning")
                .isUnmodifiable();
        assertThat(CollUtils.mergeToUnmodifiableSet(set1, set2, list1, list2))
                .containsExactlyInAnyOrder("hello", "world", "buddy", "good", "morning", "java", "kotlin")
                .isUnmodifiable();
    }
}

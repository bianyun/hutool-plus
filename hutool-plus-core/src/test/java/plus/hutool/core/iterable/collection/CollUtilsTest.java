package plus.hutool.core.iterable.collection;

import cn.hutool.core.collection.CollUtil;
import org.junit.jupiter.api.Test;
import plus.hutool.core.text.string.StrUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CollUtilsTest {

    @Test
    void testSplitTrimToSet() {
        assertThat(CollUtils.splitTrimToSet("  hello  world  ", StrUtils.SPACE)).isEqualTo(ArrayUtils.toSet("hello", "world"));
        assertThat(CollUtils.splitTrimToSet("hello world", "o")).isEqualTo(ArrayUtils.toSet("hell", "w", "rld"));
    }

    @Test
    void testUnmodifiableList() {
        assertThat(CollUtils.unmodifiableList("hello")).isEqualTo(Collections.singletonList("hello"));
        assertThat(CollUtils.unmodifiableList("hello", "hello")).isEqualTo(Collections.singletonList("hello"));
        assertThat(CollUtils.unmodifiableList(true, new String[]{"hello"})).isEqualTo(Collections.singletonList("hello"));
        assertThat(CollUtils.unmodifiableList(true, new String[]{"hello", "hello"})).isEqualTo(Collections.singletonList("hello"));
        assertThat(CollUtils.unmodifiableList(false, new String[] {"hello", "hello"})).isEqualTo(CollUtil.toList("hello", "hello"));

        final List<String> list = CollUtils.unmodifiableList("hello", "world");
        assertThat(list).isEqualTo(Arrays.asList("hello", "world"));
        assertThatThrownBy(() -> list.add("java")).isExactlyInstanceOf(UnsupportedOperationException.class);
        assertThatThrownBy(() -> list.remove("hello")).isExactlyInstanceOf(UnsupportedOperationException.class);

        assertThatThrownBy(() -> CollUtils.unmodifiableList(true, new String[0]))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("元素数组长度必须大于 0");
    }
}

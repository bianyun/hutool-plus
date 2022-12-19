package plus.hutool.core.io;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OssUtilsTest {

    @Test
    void testNormalizeDirPathInOss() {
        assertThat(OssUtils.normalizeDirPathInOss("dir/path/in/Oss")).isEqualTo("dir/path/in/Oss");
        assertThat(OssUtils.normalizeDirPathInOss("/dir//path////in/Oss/")).isEqualTo("dir/path/in/Oss");
    }
}

package plus.hutool.spring5.core;

import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

class ResourceUtilsTest {

    @Test
    void testLoadClassPathFileAsSpringResource() {
        final Resource result1 = ResourceUtils.loadClassPathFileAsSpringResource("classpath:testFiles/images/test.jpg");

        assertThat(result1).isNotNull();
        assertThat(result1.isFile()).isTrue();
        assertThat(result1.getFilename()).isEqualTo("test.jpg");
        assertThat(result1.exists()).isTrue();

        final Resource result2 = ResourceUtils.loadClassPathFileAsSpringResource("testFiles/images/test.jpg");

        assertThat(result2).isNotNull();
        assertThat(result2.isFile()).isTrue();
        assertThat(result2.getFilename()).isEqualTo("test.jpg");
        assertThat(result2.exists()).isTrue();
    }

    @Test
    void testLoadFileAsSpringResource() {
        final File file = FileUtil.file("classpath:testFiles/images/test.jpg");
        final Resource result = ResourceUtils.loadFileAsSpringResource(file);

        assertThat(result).isNotNull();
        assertThat(result.isFile()).isTrue();
        assertThat(result.getFilename()).isEqualTo("test.jpg");
        assertThat(result.exists()).isTrue();
    }
}

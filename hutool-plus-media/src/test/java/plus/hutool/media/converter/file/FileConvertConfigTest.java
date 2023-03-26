package plus.hutool.media.converter.file;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import plus.hutool.media.content.type.MediaType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class FileConvertConfigTest {

    @Test
    void testResolveDestFileBeforeConversion() {
        FileConvertConfig config = new FileConvertConfig(true);

        File testFile = Mockito.mock(File.class);
        File parentDir = Mockito.mock(File.class);

        when(testFile.getParentFile()).thenReturn(parentDir);
        when(testFile.getName()).thenReturn("test.txt");
        when(parentDir.canWrite()).thenReturn(false);

        File destFile = config.resolveDestFileBeforeConversion(testFile, MediaType.APPLICATION_PDF);
        assertThat(destFile).isFile().hasExtension("pdf");

    }

    @Test
    void testAddCustomConfig() {
        FileConvertConfig config = new FileConvertConfig();
        config.setSaveDestFileInSameDirAsSrcFile(false);

        Map<String, Object> configMap = new HashMap<>();
        configMap.put("key1", "value1");
        configMap.put("key2", "value2");
        config.setCustomConfigs(configMap);

        config.addCustomConfig("key2", "value22");
        config.addCustomConfig("key3", "value3");
        config.addCustomConfig("key4", "value4");

        assertThat(config.getCustomConfigs()).hasSize(4);
        assertThat(config.getCustomConfigs().get("key1")).isEqualTo("value1");
        assertThat(config.getCustomConfig("key2")).isEqualTo("value22");
        assertThat(config.getCustomConfig("key3")).isEqualTo("value3");
        assertThat(config.getCustomConfig("key4")).isEqualTo("value4");

    }

}

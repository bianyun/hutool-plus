package plus.hutool.core.io;

import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;
import plus.hutool.core.text.string.StrUtils;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static plus.hutool.core.io.FileUtils.RANDOM_DIRNAME_LENGTH;
import static plus.hutool.core.io.FileUtils.RANDOM_FILENAME_LENGTH;

class FileUtilsTest {

    @Test
    void testGetFilenameExtension() {
        assertThat(FileUtils.getFileExtension("picture.jpg")).isEqualTo("jpg");
        assertThat(FileUtils.getFileExtension("picture.JPEG")).isEqualTo("jpeg");
        assertThat(FileUtils.getFileExtension("picture.assets.png")).isEqualTo("png");

        assertThat(FileUtils.getFileExtension("picture")).isEqualTo(StrUtils.EMPTY);
        assertThat(FileUtils.getFileExtension(null)).isEqualTo(StrUtils.EMPTY);
        assertThat(FileUtils.getFileExtension(StrUtils.EMPTY)).isEqualTo(StrUtils.EMPTY);
    }

    @Test
    void testGetFilenameExtensionWithPrefixDot() {
        assertThat(FileUtils.getFileExtensionWithPrefixDot("picture.jpg")).isEqualTo(".jpg");
        assertThat(FileUtils.getFileExtensionWithPrefixDot("picture.JPEG")).isEqualTo(".jpeg");
        assertThat(FileUtils.getFileExtensionWithPrefixDot("picture.assets.png")).isEqualTo(".png");

        assertThat(FileUtils.getFileExtensionWithPrefixDot("picture")).isEqualTo(StrUtils.EMPTY);
        assertThat(FileUtils.getFileExtensionWithPrefixDot(null)).isEqualTo(StrUtils.EMPTY);
        assertThat(FileUtils.getFileExtensionWithPrefixDot(StrUtils.EMPTY)).isEqualTo(StrUtils.EMPTY);
    }

    @Test
    void testCreateRandomNamedTempFile() {
        File tempFile = FileUtils.createRandomNamedTempFile("jpg");
        assertThat(tempFile).isFile().exists().hasExtension("jpg").hasFileName(tempFile.getName());
        assertThat(tempFile.getName()).hasSize(RANDOM_FILENAME_LENGTH + 4);
        FileUtil.del(tempFile);

        File tempFile2 = FileUtils.createRandomNamedTempFile(null);
        assertThat(tempFile2).isFile().exists().hasNoExtension().hasFileName(tempFile2.getName());
        assertThat(tempFile2.getName()).hasSize(RANDOM_FILENAME_LENGTH);
        FileUtil.del(tempFile2);
    }

    @Test
    void testCreateFileUnderRandomTempDir() {
        String filename = "filename.ext";
        final File file = FileUtils.createFileUnderRandomTempDir(filename);
        final File parentDir = file.getParentFile();

        assertThat(file).isFile().exists().hasFileName(filename);
        assertThat(parentDir).isDirectory().exists();
        assertThat(parentDir.getName()).hasSize(RANDOM_DIRNAME_LENGTH);

        FileUtil.del(file);
        FileUtil.del(parentDir);
    }

    @Test
    void testCreateDirUnderRandomTempDir() {
        String dirname = "dirname";
        final File dir = FileUtils.createDirUnderRandomTempDir(dirname);
        final File parentDir = dir.getParentFile();

        assertThat(dir).isDirectory().exists().hasFileName(dirname);
        assertThat(parentDir).isDirectory().exists();
        assertThat(parentDir.getName()).hasSize(RANDOM_DIRNAME_LENGTH);

        FileUtil.del(dir);
        FileUtil.del(parentDir);
    }
}

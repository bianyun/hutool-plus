package plus.hutool.core.io;

import cn.hutool.core.io.FileUtil;
import org.junit.jupiter.api.Test;
import plus.hutool.core.text.string.StrUtils;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static plus.hutool.core.io.FileUtils.RANDOM_DIRNAME_LENGTH;
import static plus.hutool.core.io.FileUtils.RANDOM_FILENAME_LENGTH;

class FileUtilsTest {

    @Test
    void testGetFilenameExtension() {
        assertThat(FileUtils.getFileExtension("picture.jpg")).isEqualTo("jpg");
        assertThat(FileUtils.getFileExtension("picture.JPEG")).isEqualTo("jpeg");
        assertThat(FileUtils.getFileExtension("picture.assets.png")).isEqualTo("png");
        assertThat(FileUtils.getFileExtension("picture")).isEqualTo(StrUtils.EMPTY);
        assertThat(FileUtils.getFileExtension("   ")).isEqualTo(StrUtils.EMPTY);
        assertThat(FileUtils.getFileExtension(StrUtils.EMPTY)).isEqualTo(StrUtils.EMPTY);

        assertThat(FileUtils.getFileExtension(new File("picture.jpg"))).isEqualTo("jpg");
        assertThat(FileUtils.getFileExtension(new File("picture.JPEG"))).isEqualTo("jpeg");
        assertThat(FileUtils.getFileExtension(new File("picture.assets.png"))).isEqualTo("png");
        assertThat(FileUtils.getFileExtension(new File("picture"))).isEqualTo(StrUtils.EMPTY);
        assertThat(FileUtils.getFileExtension(new File("   "))).isEqualTo(StrUtils.EMPTY);
        assertThat(FileUtils.getFileExtension(new File(StrUtils.EMPTY))).isEqualTo(StrUtils.EMPTY);
    }

    @Test
    void testGetFilenameExtensionWithPrefixDot() {
        assertThat(FileUtils.getFileExtensionWithPrefixDot("picture.jpg")).isEqualTo(".jpg");
        assertThat(FileUtils.getFileExtensionWithPrefixDot("picture.JPEG")).isEqualTo(".jpeg");
        assertThat(FileUtils.getFileExtensionWithPrefixDot("picture.assets.png")).isEqualTo(".png");
        assertThat(FileUtils.getFileExtensionWithPrefixDot("picture")).isEqualTo(StrUtils.EMPTY);
        assertThat(FileUtils.getFileExtensionWithPrefixDot("   ")).isEqualTo(StrUtils.EMPTY);
        assertThat(FileUtils.getFileExtensionWithPrefixDot(StrUtils.EMPTY)).isEqualTo(StrUtils.EMPTY);

        assertThat(FileUtils.getFileExtensionWithPrefixDot(new File("picture.jpg"))).isEqualTo(".jpg");
        assertThat(FileUtils.getFileExtensionWithPrefixDot(new File("picture.JPEG"))).isEqualTo(".jpeg");
        assertThat(FileUtils.getFileExtensionWithPrefixDot(new File("picture.assets.png"))).isEqualTo(".png");
        assertThat(FileUtils.getFileExtensionWithPrefixDot(new File("picture"))).isEqualTo(StrUtils.EMPTY);
        assertThat(FileUtils.getFileExtensionWithPrefixDot(new File("   "))).isEqualTo(StrUtils.EMPTY);
        assertThat(FileUtils.getFileExtensionWithPrefixDot(new File(StrUtils.EMPTY))).isEqualTo(StrUtils.EMPTY);
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

    @Test
    void testGetFilenameWithoutExtension() {
        final File file = new File("filename.txt");
        final String result = FileUtils.getFilenameWithoutExtension(file);

        assertThat(result).isEqualTo("filename");
    }

    @Test
    void testRemoveFileExtension() {
        assertThat(FileUtils.removeFileExtension("filename.txt")).isEqualTo("filename");
        assertThat(FileUtils.removeFileExtension("filename")).isEqualTo("filename");
    }

    @Test
    void testDirExists1() {
        assertThat(FileUtils.dirExists("/dirPathNotExists/")).isFalse();
    }

    @Test
    void testDirExists2() {
        File tmpFile = FileUtils.createFileUnderRandomTempDir("tempFile");
        assertThat(FileUtils.dirExists(tmpFile)).isFalse();
        assertThat(FileUtils.dirExists(new File("/dirPathNotExists/"))).isFalse();
        assertThat(FileUtils.dirExists(tmpFile.getParentFile())).isTrue();
        FileUtil.del(tmpFile.getParentFile());
    }

    @Test
    void testFileExists1() {
        assertThat(FileUtils.fileExists("/filePathNotExists")).isFalse();
    }

    @Test
    void testFileExists2() {
        File tmpFile = FileUtils.createFileUnderRandomTempDir("tempFile");
        assertThat(FileUtils.fileExists(tmpFile)).isTrue();
        assertThat(FileUtils.fileExists(tmpFile.getParentFile())).isFalse();
        assertThat(FileUtils.fileExists(new File("/filePathNotExists"))).isFalse();
        FileUtil.del(tmpFile.getParentFile());
    }

    @Test
    void testHasExtension() {
        assertThat(FileUtils.hasExtension(new File("test.txt"), "txt")).isTrue();
        assertThat(FileUtils.hasExtension(new File("test.txt"), "TXT")).isTrue();
        assertThat(FileUtils.hasExtension(new File("test.txt"), "Txt")).isTrue();
        assertThat(FileUtils.hasExtension(new File("test.txt"), ".txt")).isTrue();
        assertThat(FileUtils.hasExtension(new File("test.txt"), ".TXT")).isTrue();
        assertThat(FileUtils.hasExtension(new File("test.txt"), ".Txt")).isTrue();

        assertThat(FileUtils.hasExtension(new File("test.txt"), "abc")).isFalse();
        assertThat(FileUtils.hasExtension(new File("test"), "abc")).isFalse();

        assertThatThrownBy(() -> FileUtils.hasExtension(new File("test.txt"), ""))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("文件名后缀不能为空白");
    }
}

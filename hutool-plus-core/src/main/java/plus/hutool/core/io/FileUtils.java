package plus.hutool.core.io;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.system.SystemUtil;
import plus.hutool.core.lang.annotation.Nullable;
import plus.hutool.core.lang.Asserts;
import plus.hutool.core.text.string.StrUtils;

import java.io.File;
import java.nio.file.Paths;

/**
 * 文件工具类
 *
 * @author bianyun
 * @date 2022/11/27
 */
@SuppressWarnings("JavadocDeclaration")
public abstract class FileUtils {

    public static final String TEMP_DIR = SystemUtil.getUserInfo().getTempDir();
    @SuppressWarnings("unused")
    public static final String USER_DIR = SystemUtil.getUserInfo().getCurrentDir();

    static final int RANDOM_FILENAME_LENGTH = 16;
    static final int RANDOM_DIRNAME_LENGTH = 6;

    private FileUtils() {}

    /**
     * 获取文件扩展名（不含点号）
     *
     * @param file 文件
     * @return 文件扩展名
     */
    public static String getFileExtension(File file) {
        return getFileExtension(file.getName());
    }

    /**
     * 获取文件扩展名（不含点号）
     *
     * @param fileName 文件名
     * @return 文件扩展名
     */
    public static String getFileExtension(String fileName) {
        if (StrUtil.isBlank(fileName) || !fileName.contains(StrUtils.DOT)) {
            return StrUtils.EMPTY;
        }
        int lastDotIndex = fileName.lastIndexOf(StrUtils.DOT);
        return fileName.substring(lastDotIndex + 1).toLowerCase();
    }

    /**
     * 获取文件扩展名（含扩展名前面的点号）
     *
     * @param file 文件
     * @return 文件扩展名
     */
    public static String getFileExtensionWithPrefixDot(File file) {
        return getFileExtensionWithPrefixDot(file.getName());
    }

    /**
     * 获取文件扩展名（含扩展名前面的点号）
     *
     * @param fileName 文件名
     * @return 文件扩展名
     */
    public static String getFileExtensionWithPrefixDot(String fileName) {
        if (StrUtil.isBlank(fileName) || !fileName.contains(StrUtils.DOT)) {
            return StrUtils.EMPTY;
        }
        int lastDotIndex = fileName.lastIndexOf(StrUtils.DOT);
        return fileName.substring(lastDotIndex).toLowerCase();
    }

    /**
     * 创建随机命名的临时文件（位于java.io.tmpdir 变量指向的临时目录中）
     *
     * @param fileExtension 文件扩展名（不含点号，可为空）
     * @return 随机命名的临时文件
     */
    public static File createRandomNamedTempFile(@Nullable String fileExtension) {
        String tempFileName = RandomUtil.randomString(RANDOM_FILENAME_LENGTH);
        if (StrUtil.isNotBlank(fileExtension)) {
            tempFileName += StrUtils.DOT + fileExtension;
        }
        File result = Paths.get(TEMP_DIR, tempFileName).toFile();
        FileUtil.touch(result);
        return result;
    }

    /**
     * 根据指定的文件名在临时目录中的随机命名的子目录中创建文件
     *
     * @param filename 文件名
     * @return 创建的文件
     */
    public static File createFileUnderRandomTempDir(String filename) {
        String randomDirName = RandomUtil.randomString(RANDOM_DIRNAME_LENGTH);
        File result = Paths.get(TEMP_DIR, randomDirName, filename).toFile();
        FileUtil.mkParentDirs(result);
        FileUtil.touch(result);
        return result;
    }

    /**
     * 根据指定的目录名在临时目录中的随机命名的子目录中创建目录
     *
     * @param dirname 目录名
     * @return 创建的目录
     */
    public static File createDirUnderRandomTempDir(String dirname) {
        String randomDirName = RandomUtil.randomString(RANDOM_DIRNAME_LENGTH);
        File result = Paths.get(TEMP_DIR, randomDirName, dirname).toFile();
        FileUtil.mkParentDirs(result);
        FileUtil.mkdir(result);
        return result;
    }

    /**
     * 获取文件名（不包含后缀名）
     *
     * @param file 文件
     * @return 文件名（不包含后缀名）
     */
    public static String getFilenameWithoutExtension(File file) {
        return removeFileExtension(file.getName());
    }

    /**
     * 去除文件名中的后缀名
     *
     * @param fileName 文件名
     * @return 文件名去除后缀名（包括前面的 .）后的剩余部分
     */
    public static String removeFileExtension(String fileName) {
        int pos = fileName.lastIndexOf(".");
        return pos == -1 ? fileName : fileName.substring(0, pos);
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return 文件是否存在
     */
    public static boolean fileExists(String filePath) {
        return fileExists(new File(filePath));
    }

    /**
     * 判断文件是否存在
     *
     * @param file 文件
     * @return 文件是否存在
     */
    public static boolean fileExists(File file) {
        return file.exists() && file.isFile();
    }

    /**
     * 判断目录是否存在
     *
     * @param dirPath 目录路径
     * @return 目录是否存在
     */
    public static boolean dirExists(String dirPath) {
        return fileExists(new File(dirPath));
    }

    /**
     * 判断目录是否存在
     *
     * @param dir 目录
     * @return 目录是否存在
     */
    public static boolean dirExists(File dir) {
        return dir.exists() && dir.isDirectory();
    }

    /**
     * 判断文件是否拥有指定的文件名后缀(不区分大小写, 后缀名开头的点号有无均可）
     *
     * @param file 文件
     * @param fileExtension 文件名后缀
     * @return 文件是否拥有指定的文件名后缀
     */
    public static boolean hasExtension(File file, String fileExtension) {
        Asserts.notBlank(fileExtension, "文件名后缀不能为空白");

        String fileExtWithoutLeadingDot = StrUtil.removePrefix(fileExtension, StrUtils.DOT);

        String resolvedFileExt = FileUtils.getFileExtension(file);
        return resolvedFileExt.equalsIgnoreCase(fileExtWithoutLeadingDot);
    }
}

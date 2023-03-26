package plus.hutool.media.converter.file;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plus.hutool.core.io.FileUtils;
import plus.hutool.media.content.type.MediaType;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * 文件转换配置
 *
 * @author bianyun
 * @date 2023/2/14
 */
@SuppressWarnings({"unused", "JavadocDeclaration"})
public class FileConvertConfig {

    private static final Logger log = LoggerFactory.getLogger(FileConvertConfig.class);

    /**
     * 是否将转换目标文件存储到来源文件的相同目录下（默认为否）
     */
    private boolean saveDestFileInSameDirAsSrcFile = false;

    /**
     * 自定义配置信息(Map 格式)
     */
    private Map<String, Object> customConfigs = new HashMap<>();

    public FileConvertConfig() {}

    public FileConvertConfig(boolean saveDestFileInSameDirAsSrcFile) {
        this.saveDestFileInSameDirAsSrcFile = saveDestFileInSameDirAsSrcFile;
    }

    /**
     * 解析文件转换器的目标文件（转换前）
     *
     * @param srcFile 来源文件
     * @param destMediaType  目标文件的媒体类型
     * @return 文件转换器的目标文件（转换前）
     */
    public File resolveDestFileBeforeConversion(File srcFile, MediaType destMediaType) {
        String destFilename = getDestFileName(srcFile, destMediaType);
        File parentDir = srcFile.getParentFile();

        boolean needWarn = false;
        if (this.isSaveDestFileInSameDirAsSrcFile()) {
            if (parentDir.canWrite()) {
                return FileUtil.file(parentDir, destFilename);
            } else {
                needWarn = true;
            }
        }

        File result = FileUtils.createFileUnderRandomTempDir(destFilename);
        String destFilePath = result.getAbsolutePath();
        if (needWarn) {
            log.warn("来源文件所在目录没有写入权限，转换后的目标文件改为存放到如下路径: {}", destFilePath);
        }

        return result;
    }

    /**
     * 添加一项自定义配置
     *
     * @param key 配置的 key
     * @param value 配置的值
     */
    public void addCustomConfig(String key, Object value) {
        this.getCustomConfigs().put(key, value);
    }

    /**
     * 根据 key 获取一个自定义配置项的值
     *
     * @param key 配置的 key
     * @return 配置的值
     */
    public Object getCustomConfig(String key) {
        return this.getCustomConfigs().get(key);
    }

    public boolean isSaveDestFileInSameDirAsSrcFile() {
        return saveDestFileInSameDirAsSrcFile;
    }

    public void setSaveDestFileInSameDirAsSrcFile(boolean saveDestFileInSameDirAsSrcFile) {
        this.saveDestFileInSameDirAsSrcFile = saveDestFileInSameDirAsSrcFile;
    }

    public Map<String, Object> getCustomConfigs() {
        return customConfigs;
    }

    public void setCustomConfigs(Map<String, Object> customConfigs) {
        this.customConfigs = customConfigs;
    }

    /**
     * 获取文件转换器目标文件的文件名
     *
     * @param srcFile 来源文件
     * @return 目标文件的文件名
     */
    private static String getDestFileName(File srcFile, MediaType destMediaType) {
        String filenameWithoutExtension = FileUtils.getFilenameWithoutExtension(srcFile);
        String destFileExtension = destMediaType.getDefaultFileExtension();
        return StrUtil.format("{}.{}", filenameWithoutExtension, destFileExtension);
    }
}

package plus.hutool.core.io;

import cn.hutool.core.util.StrUtil;
import plus.hutool.core.text.string.StrUtils;

/**
 * OSS 工具类
 *
 * @author bianyun
 * @date 2022/11/29
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class OssUtils {

    /**
     * 规范 OSS 中的目录路径
     *
     * @param dirPathInOss OSS 目录路径
     * @return 规范后的 OSS 目录路径
     */
    public static String normalizeDirPathInOss(final String dirPathInOss) {
        String newDirPathInOss = dirPathInOss;
        newDirPathInOss = newDirPathInOss.replaceAll("\\s*/{2,}\\s*", StrUtils.SLASH);
        newDirPathInOss = StrUtil.removePrefix(newDirPathInOss, StrUtils.SLASH);
        newDirPathInOss = StrUtil.removeSuffix(newDirPathInOss, StrUtils.SLASH);
        return newDirPathInOss;
    }

    private OssUtils() {}
}

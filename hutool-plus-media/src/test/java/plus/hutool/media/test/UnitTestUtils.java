package plus.hutool.media.test;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IORuntimeException;
import cn.hutool.core.lang.Console;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.tika.Tika;
import plus.hutool.core.io.FileUtils;
import plus.hutool.core.iterable.collection.CollUtils;
import plus.hutool.media.content.type.MediaTypeUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 单元测试工具类
 *
 * @author bianyun
 * @date 2023/2/16
 */
@SuppressWarnings("JavadocDeclaration")
public abstract class UnitTestUtils {
    /**
     * 约定的单元测试用的测试文件根目录的名称（位于类路径根目录下）
     */
    private static final String TEST_FILES_ROOT_DIRNAME = "test-files-for-unit-test";

    /**
     * 是否清理单元测试生成的结果文件
     */
    public static final boolean CLEAN_UP_RESULT_FILE = true;

    /**
     * 文件类别 - 归档类文件(zip, rar, tar, jar, 7z, gz, tgz, bz2, xz, ...)
     */
    private static final String FILE_CATEGORY_ARCHIVE = "archive";

    /**
     * 文件类别 - 音频类文件(mp3, wav, m4a, ogg, wma, ...)
     */
    private static final String FILE_CATEGORY_AUDIO = "audio";

    /**
     * 文件类别 - 文档类文件(doc, docx, xls, xlsx, ppt, pptx, txt, html, pdf, ofd, ...)
     */
    private static final String FILE_CATEGORY_DOCUMENT = "document";

    /**
     * 文件类别 - 图像类文件(jpg, png, gif, bmp, ico, ...)
     */
    private static final String FILE_CATEGORY_IMAGE = "image";

    /**
     * 文件类别 - 视频类文件(avi, mp4, mkv, mov, rmvb, flv, ...)
     */
    private static final String FILE_CATEGORY_VIDEO = "video";

    /**
     * 文件类别 - 其它类文件（后缀名不在上述类别的其它文件）
     */
    private static final String FILE_CATEGORY_OTHER = "other";

    private static final Set<String> ARCHIVE_FILE_EXTENSIONS = CollUtils.unmodifiableSet(
            "7z", "bz2", "bz", "gz", "rar",
            "tar", "tgz", "xz", "jar", "zip"
    );

    private static final Set<String> AUDIO_FILE_EXTENSIONS = CollUtils.unmodifiableSet(
            "aac", "ac3", "aiff", "amr", "ape",
            "flac", "m4a", "m4b", "m4r", "m4p",
            "mka", "mp1", "mp2", "mp3", "oga",
            "ogg", "opus", "spx", "wav", "wma"
    );

    private static final Set<String> DOCUMENT_FILE_EXTENSIONS = CollUtils.unmodifiableSet(
            "ceb", "css", "csv", "doc", "docx",
            "html", "js", "json", "md", "mhtml",
            "odp", "ods", "odt", "ofd", "pdf",
            "ppt", "pps", "pptx", "ppsx", "rtf",
            "sh", "sql", "txt", "wps", "xls",
            "xlsx", "xml"
    );

    @SuppressWarnings("SpellCheckingInspection")
    private static final Set<String> IMAGE_FILE_EXTENSIONS = CollUtils.unmodifiableSet(
            "bmp", "gif", "ico", "iff", "jp2",
            "jpeg", "jpg", "pcx", "png", "psd",
            "ras", "rsb", "sgi", "tga", "tif",
            "tiff", "wbmp"
    );

    private static final Set<String> VIDEO_FILE_EXTENSIONS = CollUtils.unmodifiableSet(
            "3g2", "3gp", "asf", "avi", "f4v",
            "flv", "m4v", "mkv", "mov", "mp4",
            "mpg", "mxf", "ogv", "rm", "rmvb",
            "swf", "webm", "wmv"
    );

    private static final Map<String, Set<String>> MAP_OF_FILE_CATEGORY_TO_EXTENSIONS =
            MapUtil.<String, Set<String>>builder()
                    .put(FILE_CATEGORY_ARCHIVE, ARCHIVE_FILE_EXTENSIONS)
                    .put(FILE_CATEGORY_DOCUMENT, DOCUMENT_FILE_EXTENSIONS)
                    .put(FILE_CATEGORY_IMAGE, IMAGE_FILE_EXTENSIONS)
                    .put(FILE_CATEGORY_VIDEO, VIDEO_FILE_EXTENSIONS)
                    .put(FILE_CATEGORY_AUDIO, AUDIO_FILE_EXTENSIONS)
                    .build();

    private static final Map<String, String> MAP_OF_FILE_EXT_TO_FILE_CATEGORY = new HashMap<>(64);

    static {
        MAP_OF_FILE_CATEGORY_TO_EXTENSIONS.forEach(
                (fileCategory, fileExtensions) ->
                        fileExtensions.forEach(fileExt -> MAP_OF_FILE_EXT_TO_FILE_CATEGORY.put(fileExt, fileCategory)));
    }

    /**
     * 根据文件名解析出测试文件的类路径（按照约定的路径和存放方式）
     *
     * @param testFilename 测试文件的文件名
     * @return 测试文件的类路径
     */
    public static String resolveTestFileClassPath(String testFilename) {
        String fileExt = FileUtils.getFileExtension(testFilename);
        String fileCategory = resolveFileCategory(fileExt);

        if (FILE_CATEGORY_OTHER.equals(fileCategory)) {
            return StrUtil.format("classpath:{}/{}/{}", TEST_FILES_ROOT_DIRNAME, fileCategory, testFilename);
        } else {
            return StrUtil.format("classpath:{}/{}/{}/{}", TEST_FILES_ROOT_DIRNAME, fileCategory, fileExt, testFilename);
        }
    }

    /**
     * 根据文件名解析出测试文件对象（按照约定的路径和存放方式）
     *
     * @param testFilename 测试文件的文件名
     * @return 测试文件对象
     */
    public static File resolveTestFile(String testFilename) {
        String fileClassPath = resolveTestFileClassPath(testFilename);
        return FileUtil.file(fileClassPath);
    }

    /**
     * 根据文件名解析出测试文件的 {@link InputStream}（按照约定的路径和存放方式）
     *
     * @param testFilename 测试文件的文件名
     * @return 测试文件的 {@link InputStream}
     */
    public static InputStream resolveTestFileInputStream(String testFilename) {
        return FileUtil.getInputStream(resolveTestFile(testFilename));
    }

    /**
     * 打印用 Tika 解析出的测试文件的媒体类型
     *
     * @param filename 测试文件的文件名
     */
    @SuppressWarnings("unused")
    public static void printContentTypeDetectedByTika(String filename) {
        File testFile = resolveTestFile(filename);
        Console.log("{}: \t\t{}", filename, detectMediaTypeByTika(testFile));
    }

    /**
     * 打印用工具类 {@link MediaTypeUtils} 探测出的媒体类型
     *
     * @param filename 测试文件的文件名
     */
    @SuppressWarnings("unused")
    public static void printDetectedMediaType(String filename) {
        File testFile = resolveTestFile(filename);
        Console.log("{}: \t\t{}", filename, MediaTypeUtils.detectMediaType(testFile));
    }

    private static String detectMediaTypeByTika(File file) {
        try {
            return new Tika().detect(file);
        } catch (IOException e) {
            throw new IORuntimeException(e);
        }
    }

    private static String resolveFileCategory(String fileExtension) {
        String result = MAP_OF_FILE_EXT_TO_FILE_CATEGORY.get(fileExtension);
        if (result == null) {
            result = FILE_CATEGORY_OTHER;
        }
        return result;
    }
}

package plus.hutool.media.document.convert;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.thread.ThreadUtil;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import plus.hutool.core.io.FileUtils;
import plus.hutool.core.lang.Asserts;
import plus.hutool.media.misc.MediaType;
import plus.hutool.media.misc.MediaTypeUtils;
import plus.hutool.media.misc.TikaUtils;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 文档转换工具
 *
 * @author bianyun
 * @date 2022/12/06
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class DocumentConvertUtils {
    private static final int DEFAULT_PROCESS_TIMEOUT_SECONDS = 30;
    private static final int MAX_PROCESS_TIMEOUT_SECONDS = 1800;

    private DocumentConvertUtils() {
    }

    public static File convert(Path path, DocumentType srcDocType, DocumentType targetDocType) {
        return convert(path.toFile(), srcDocType, targetDocType, DEFAULT_PROCESS_TIMEOUT_SECONDS);
    }

    public static File convert(Path path, DocumentType srcDocType, DocumentType targetDocType, int processTimeoutSeconds) {
        return convert(path.toFile(), srcDocType, targetDocType, processTimeoutSeconds);
    }

    public static File convert(File srcFile, DocumentType srcDocType, DocumentType targetDocType) {
        return convert(srcFile, srcDocType, targetDocType, DEFAULT_PROCESS_TIMEOUT_SECONDS);
    }

    public static File convert(File srcFile, DocumentType srcDocType, DocumentType targetDocType, int processTimeoutSeconds) {
        String srcFilePath = srcFile.getAbsolutePath();
        Asserts.isTrue(srcFile.exists() && srcFile.isFile(), "文件必须存在: {}", srcFilePath);

        String mediaTypeValue = TikaUtils.detectMediaType(srcFile);
        Asserts.notBlank("文件的媒体类型(MediaType) 不能为空: {}", srcFilePath);

        DocumentType parsedDocType = new DocumentType(mediaTypeValue);
        Asserts.isTrue(parsedDocType.equals(srcDocType), "来源文件的媒体类型[{}] 与指定的来源文件媒体类型[{}] 不符",
                parsedDocType, srcDocType);

        MediaType mediaType = MediaTypeUtils.fromDocumentType(targetDocType);
        String targetFileExtension = mediaType.getDefaultFileExtension();
        File targetFile = FileUtils.createRandomNamedTempFile(targetFileExtension);
        File parentDir = targetFile.getParentFile();
        int resolvedTimeoutSeconds = Math.max(DEFAULT_PROCESS_TIMEOUT_SECONDS, processTimeoutSeconds);
        resolvedTimeoutSeconds = Math.min(resolvedTimeoutSeconds, MAX_PROCESS_TIMEOUT_SECONDS);

        IConverter converter = LocalConverter.builder()
                .baseFolder(parentDir)
                .workerPool(20, 25, 2, TimeUnit.SECONDS)
                .processTimeout(resolvedTimeoutSeconds, TimeUnit.SECONDS)
                .build();
        Future<Boolean> future = converter
                .convert(FileUtil.getInputStream(srcFile)).as(srcDocType)
                .to(targetFile).as(targetDocType)
                .prioritizeWith(1000)
                .schedule();
        while (!future.isDone()) {
            ThreadUtil.safeSleep(100);
        }
        converter.shutDown();
        return targetFile;
    }
}

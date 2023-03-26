package plus.hutool.media.converter.file;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import plus.hutool.core.lang.Asserts;
import plus.hutool.media.content.type.MediaType;
import plus.hutool.media.exception.FileConverterRegistryKeyNotFoundException;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 文件转换器注册表
 *
 * @author bianyun
 * @date 2023/2/14
 */
@SuppressWarnings("JavadocDeclaration")
public abstract class FileConverterRegistry {
    private static final Logger log = LoggerFactory.getLogger(FileConverterRegistry.class);
    private static final Map<FileConverterRegistryKey, FileConverter> REGISTRY_MAP = new HashMap<>();
    private static final Map<MediaType, Set<MediaType>> SUPPORTED_SRC_MEDIA_TYPES_MAP = new HashMap<>();

    static {
        init();
        printRegistryInfo();
    }

    private FileConverterRegistry() {

    }

    /**
     * 查询文件转换器实例
     *
     * @param srcMediaType 文件转换的来源媒体类型
     * @param destMediaType 文件转换的目标媒体类型
     * @return 查询到的文件转换器实例
     * @throws FileConverterRegistryKeyNotFoundException 当注册表中查询不到文件转换器实例时
     */
    public static FileConverter lookup(MediaType srcMediaType, MediaType destMediaType) {
        Asserts.notNull(srcMediaType, "来源媒体类型不能为 null");
        Asserts.notNull(destMediaType, "目标媒体类型不能为 null");

        FileConverterRegistryKey key = FileConverterRegistryKey.of(srcMediaType, destMediaType);
        FileConverter result = REGISTRY_MAP.get(key);
        if (result == null) {
            throw new FileConverterRegistryKeyNotFoundException(srcMediaType, destMediaType);
        }

        return result;
    }

    public static Set<MediaType> getSupportedSrcMediaTypesByDestType(MediaType destMediaType) {
        return Collections.unmodifiableSet(SUPPORTED_SRC_MEDIA_TYPES_MAP.get(destMediaType));
    }

    /**
     * 文件转换器注册表Key
     */
    @SuppressWarnings("unused")
    public static class FileConverterRegistryKey {
        private final MediaType srcMediaType;

        private final MediaType destMediaType;

        private FileConverterRegistryKey(MediaType srcMediaType, MediaType destMediaType) {
            this.srcMediaType = srcMediaType;
            this.destMediaType = destMediaType;
        }

        public static FileConverterRegistryKey of(MediaType srcMediaType, MediaType destMediaType) {
            return new FileConverterRegistryKey(srcMediaType, destMediaType);
        }

        public MediaType getSrcMediaType() {
            return srcMediaType;
        }

        public MediaType getDestMediaType() {
            return destMediaType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof FileConverterRegistryKey)) {
                return false;
            }
            FileConverterRegistryKey that = (FileConverterRegistryKey) o;
            return this.srcMediaType.equals(that.srcMediaType) && this.destMediaType.equals(that.destMediaType);
        }

        @Override
        public int hashCode() {
            return Objects.hash(srcMediaType, destMediaType);
        }

        @Override
        public String toString() {
            return StrUtil.format("[{} ==> {}]", srcMediaType, destMediaType);
        }
    }

    private static void init() {
        String fileConverterPackageName = FileConverter.class.getPackage().getName();
        Set<Class<?>> clazzSet = ClassUtil.scanPackageBySuper(fileConverterPackageName, FileConverter.class).stream()
                .filter(clazz -> !ClassUtil.isAbstractOrInterface(clazz) && !clazz.isAnonymousClass())
                .collect(Collectors.toSet());

        for (Class<?> clazz : clazzSet) {
            register((FileConverter) ReflectUtil.newInstance(clazz));
        }
    }

    @SuppressWarnings("unused")
    private static void printRegistryInfo() {
        SUPPORTED_SRC_MEDIA_TYPES_MAP.forEach((destMediaType, srcMediaTypes) -> {
            String str = srcMediaTypes.stream().map(srcMediaType -> {
                FileConverterRegistryKey key = FileConverterRegistryKey.of(srcMediaType, destMediaType);
                FileConverter converter = REGISTRY_MAP.get(key);
                return StrUtil.format("\n\t {}: \n\t\t\t {}\n", srcMediaType, converter.getClass().getName());
            }).sorted().collect(Collectors.joining());
            log.debug("[conversion to {}]: {}\n\n", destMediaType, str);
        });
    }

    /**
     * 注册文件转换器
     *
     * @param converter 文件转换器实例
     */
    private static void register(FileConverter converter) {
        Set<MediaType> srcMediaTypeSet = converter.getSupportedSrcMediaTypes();
        MediaType destMediaType = converter.getDestMediaType();

        srcMediaTypeSet.forEach(srcMediaType -> {
            FileConverterRegistryKey key = FileConverterRegistryKey.of(srcMediaType, destMediaType);
            FileConverter oldValue = REGISTRY_MAP.get(key);
            if (oldValue == null) {
                REGISTRY_MAP.putIfAbsent(key, converter);

                Set<MediaType> supportedSrcMediaTypes =
                        SUPPORTED_SRC_MEDIA_TYPES_MAP.computeIfAbsent(destMediaType, k -> new HashSet<>());
                supportedSrcMediaTypes.add(srcMediaType);
            } else {
                Console.error("[警告] 无法将文件转换器[{}] \n\t 注册到 Key: {}, \n\t 原因: 此 Key 在注册表中已经注册了文件转换器 [{}]\n",
                        converter.getClass().getName(), key, oldValue.getClass().getName());
            }
        });
    }
}

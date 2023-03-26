package plus.hutool.media.converter.file;

import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import org.junit.jupiter.api.Test;
import plus.hutool.core.iterable.collection.CollUtils;
import plus.hutool.media.content.type.MediaType;
import plus.hutool.media.converter.file.FileConverterRegistry.FileConverterRegistryKey;
import plus.hutool.media.converter.file.impl.pdf.CommonDocToPdfConverter;
import plus.hutool.media.exception.FileConverterRegistryKeyNotFoundException;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileConverterRegistryTest {

    @Test
    void testLookup() {
        final MediaType srcMediaType = MediaType.APPLICATION_DOC;
        final MediaType destMediaType = MediaType.APPLICATION_PDF;

        final FileConverter result = FileConverterRegistry.lookup(srcMediaType, destMediaType);
        assertThat(result).isExactlyInstanceOf(CommonDocToPdfConverter.class);
    }

    @Test
    void testLookup_ThrowsFileConverterRegistryKeyNotFoundException() {
        final MediaType srcMediaType = MediaType.APPLICATION_DOC;
        final MediaType destMediaType = MediaType.APPLICATION_XLS;

        assertThatThrownBy(() -> FileConverterRegistry.lookup(srcMediaType, destMediaType))
                .isInstanceOf(FileConverterRegistryKeyNotFoundException.class);
    }

    @Test
    void testGetSupportedSrcMediaTypesByDestType() {
        final MediaType destMediaType = MediaType.APPLICATION_PDF;

        final Set<MediaType> result = FileConverterRegistry.getSupportedSrcMediaTypesByDestType(destMediaType);
        assertThat(result).isNotEmpty().containsAnyOf(MediaType.APPLICATION_DOC, MediaType.APPLICATION_OFD);
    }

    @Test
    void testFileConverterRegistryKey() {
        final MediaType srcMediaType = MediaType.APPLICATION_DOC;
        final MediaType destMediaType = MediaType.APPLICATION_PDF;

        FileConverterRegistryKey key = FileConverterRegistryKey.of(srcMediaType, destMediaType);
        assertThat(key.getSrcMediaType()).isEqualTo(srcMediaType);
        assertThat(key.getDestMediaType()).isEqualTo(destMediaType);

        assertThat(key).isEqualTo(key);
        assertThat(key.toString()).isEqualTo(StrUtil.format("[{} ==> {}]", srcMediaType, destMediaType));
        assertThat(key).isEqualTo(FileConverterRegistryKey.of(srcMediaType, destMediaType));
        assertThat(key).isNotEqualTo(FileConverterRegistryKey.of(srcMediaType, MediaType.APPLICATION_DOCX));
        assertThat(key).isNotEqualTo(FileConverterRegistryKey.of(MediaType.APPLICATION_DOCX, srcMediaType));
        //noinspection AssertBetweenInconvertibleTypes
        assertThat(key).isNotEqualTo(new HashMap<>());
    }

    @Test
    void testInit() {
        Method registerMethod = ReflectUtil.getMethod(FileConverterRegistry.class, "init");
        registerMethod.setAccessible(true);
        ReflectUtil.invoke(FileConverterRegistry.class, registerMethod);
    }

    @Test
    void testRegister() {
        Method registerMethod = ReflectUtil.getMethod(FileConverterRegistry.class, "register", FileConverter.class);
        registerMethod.setAccessible(true);

        FileConverter keyDuplicatedConverter = new FileConverter() {

            @Override
            public Set<MediaType> getSupportedSrcMediaTypes() {
                return CollUtils.unmodifiableSet(MediaType.APPLICATION_DOC);
            }

            @Override
            public MediaType getDestMediaType() {
                return MediaType.APPLICATION_PDF;
            }

            @Override
            public File convert(File srcFile, FileConvertConfig config) {
                throw new UnsupportedOperationException();
            }
        };

        ReflectUtil.invoke(FileConverterRegistry.class, registerMethod, keyDuplicatedConverter);
    }

}

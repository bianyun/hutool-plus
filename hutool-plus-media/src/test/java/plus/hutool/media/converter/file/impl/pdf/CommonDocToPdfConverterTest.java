package plus.hutool.media.converter.file.impl.pdf;

import org.junit.jupiter.api.Test;
import plus.hutool.media.converter.file.FileConvertConfig;
import plus.hutool.media.exception.FileConversionException;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommonDocToPdfConverterTest {

    @Test
    void testDoConvert() {
        final File srcFile = new File("nonExistingFile");
        final File destFile = new File("destFile");
        final FileConvertConfig config = new FileConvertConfig(false);

        CommonDocToPdfConverter converter = new CommonDocToPdfConverter();
        assertThatThrownBy(() -> converter.doConvert(srcFile, destFile, config))
                .isExactlyInstanceOf(FileConversionException.class);
    }
}

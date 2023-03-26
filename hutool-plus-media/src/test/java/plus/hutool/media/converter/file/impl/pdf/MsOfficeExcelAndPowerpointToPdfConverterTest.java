package plus.hutool.media.converter.file.impl.pdf;

import com.itextpdf.pdfoffice.OfficeConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import plus.hutool.core.io.FileUtils;
import plus.hutool.media.converter.file.FileConvertConfig;
import plus.hutool.media.exception.FileConversionException;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static plus.hutool.media.test.UnitTestUtils.resolveTestFile;

class MsOfficeExcelAndPowerpointToPdfConverterTest {

    private MsOfficeExcelAndPowerpointToPdfConverter testedConverter;

    @BeforeEach
    void setUp() {
        testedConverter = new MsOfficeExcelAndPowerpointToPdfConverter();
    }

    @Test
    void testDoConvert() {
        try (MockedStatic<OfficeConverter> staticOfficeConverter = Mockito.mockStatic(OfficeConverter.class)) {
            staticOfficeConverter
                    .when(() -> OfficeConverter.convertOfficeSpreadsheetToPdf(any(File.class), any(File.class)))
                    .thenThrow(RuntimeException.class);
            assertThatThrownBy(() -> testedConverter.doConvert(
                    resolveTestFile("test.xls"), FileUtils.createRandomNamedTempFile("pdf"), new FileConvertConfig()))
                    .isExactlyInstanceOf(FileConversionException.class)
                    .hasMessage("文件转换出错");
        }
    }
}

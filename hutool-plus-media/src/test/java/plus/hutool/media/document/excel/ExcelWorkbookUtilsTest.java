package plus.hutool.media.document.excel;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static plus.hutool.media.test.UnitTestUtils.resolveTestFile;

class ExcelWorkbookUtilsTest {

    @Test
    void testGetAllSheetNames() {
        File xlsFile = resolveTestFile("user.xls");
        File xlsxFile = resolveTestFile("user.xlsx");

        try (Workbook xlsWorkbook = ExcelWorkbookUtils.getWorkbook(xlsFile)) {
            assertThat(ExcelWorkbookUtils.getAllSheetNames(xlsWorkbook))
                    .hasSameElementsAs(Arrays.asList("工作表1", "工作表2"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Workbook xlsxWorkbook = ExcelWorkbookUtils.getWorkbook(xlsxFile)) {
            assertThat(ExcelWorkbookUtils.getAllSheetNames(xlsxWorkbook))
                    .hasSameElementsAs(Arrays.asList("工作表1", "工作表2"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetWorkbookFromXlsFile() {
        File xlsFile = resolveTestFile("user.xls");
        try (Workbook xlsWorkbook = ExcelWorkbookUtils.getWorkbook(xlsFile)) {
            assertThat(xlsWorkbook).isNotNull();
            assertThat(xlsWorkbook.getNumberOfSheets()).isEqualTo(2);
            assertThat(xlsWorkbook.getSheetAt(0).getSheetName()).isEqualTo("工作表1");
            assertThat(xlsWorkbook.getSheetAt(1).getSheetName()).isEqualTo("工作表2");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetWorkbookFromXlsxFile() {
        File xlsxFile = resolveTestFile("user.xlsx");
        try (Workbook xlsxWorkbook = ExcelWorkbookUtils.getWorkbook(xlsxFile)) {
            assertThat(xlsxWorkbook).isNotNull();
            assertThat(xlsxWorkbook.getNumberOfSheets()).isEqualTo(2);
            assertThat(xlsxWorkbook.getSheetAt(0).getSheetName()).isEqualTo("工作表1");
            assertThat(xlsxWorkbook.getSheetAt(1).getSheetName()).isEqualTo("工作表2");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

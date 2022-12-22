package plus.hutool.media.document.excel;

import cn.hutool.core.io.FileUtil;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ExcelWorkbookUtilsTest {

    @Test
    void testGetAllSheetNames() {
        File xlsFile = FileUtil.file("classpath:testFiles/documents/test.xls");
        File xlsxFile = FileUtil.file("classpath:testFiles/documents/test.xlsx");

        try (Workbook xlsWorkbook = ExcelWorkbookUtils.getWorkbook(xlsFile)) {
            assertThat(ExcelWorkbookUtils.getAllSheetNames(xlsWorkbook)).hasSameElementsAs(Arrays.asList("工作表1", "工作表2", "工作表3"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (Workbook xlsxWorkbook = ExcelWorkbookUtils.getWorkbook(xlsxFile)) {
            assertThat(ExcelWorkbookUtils.getAllSheetNames(xlsxWorkbook)).hasSameElementsAs(Arrays.asList("工作表1", "工作表2"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetWorkbookFromXlsFile() {
        File xlsFile = FileUtil.file("classpath:testFiles/documents/test.xls");
        try (Workbook xlsWorkbook = ExcelWorkbookUtils.getWorkbook(xlsFile)) {
            assertThat(xlsWorkbook).isNotNull();
            assertThat(xlsWorkbook.getNumberOfSheets()).isEqualTo(3);
            assertThat(xlsWorkbook.getSheetAt(0).getSheetName()).isEqualTo("工作表1");
            assertThat(xlsWorkbook.getSheetAt(1).getSheetName()).isEqualTo("工作表2");
            assertThat(xlsWorkbook.getSheetAt(2).getSheetName()).isEqualTo("工作表3");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testGetWorkbookFromXlsxFile() {
        File xlsxFile = FileUtil.file("classpath:testFiles/documents/test.xlsx");
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

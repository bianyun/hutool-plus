package plus.hutool.media.document.excel;

import cn.hutool.poi.excel.ExcelUtil;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel工作簿 {@link Workbook} 工具
 *
 * @author bianyun
 * @date 2022/12/06
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class ExcelWorkbookUtils {

    private ExcelWorkbookUtils() {}

    public static List<String> getAllSheetNames(Workbook workbook) {
        List<String> resultSet = new ArrayList<>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            resultSet.add(workbook.getSheetAt(i).getSheetName().trim());
        }

        return resultSet;
    }

    public static Workbook getWorkbook(File excelFile) {
        return ExcelUtil.getReader(excelFile).getWorkbook();
    }

}

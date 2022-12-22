package plus.hutool.media.document.excel;

import cn.hutool.poi.excel.ExcelUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Excel 工作簿 {@link Workbook} 工具
 *
 * @author bianyun
 * @date 2022/12/06
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class ExcelWorkbookUtils {

    private ExcelWorkbookUtils() {}

    /**
     * 获取 Excel 工作簿 {@link Workbook} 中所有工作表 {@link Sheet} 的名称列表
     *
     * @param workbook 工作簿
     * @return 所有工作表 {@link Sheet} 的名称列表
     */
    public static List<String> getAllSheetNames(Workbook workbook) {
        List<String> resultSet = new ArrayList<>();
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            resultSet.add(workbook.getSheetAt(i).getSheetName().trim());
        }

        return resultSet;
    }

    /**
     * 获取 Excel 文件的工作簿 {@link Workbook} 对象
     *
     * @param excelFile Excel 文件
     * @return 工作簿 {@link Workbook} 对象
     */
    public static Workbook getWorkbook(File excelFile) {
        return ExcelUtil.getReader(excelFile).getWorkbook();
    }

}

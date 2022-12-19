package plus.hutool.media.document.excel;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.RowUtil;
import cn.hutool.poi.excel.editors.TrimEditor;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import plus.hutool.core.text.string.StrUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Excel 工作表 {@link Sheet} 工具
 *
 * @author bianyun
 * @date 2022/12/06
 */
@SuppressWarnings({"JavadocDeclaration", "AlibabaAbstractClassShouldStartWithAbstractNaming"})
public abstract class ExcelWorksheetUtils {
    private static final String HEADER_SUFFIX_DATE = "日期";

    private ExcelWorksheetUtils() {}

    public static List<String> readHeaders(Sheet sheet) {
        Row headerRow = RowUtil.getOrCreateRow(sheet, 0);
        return RowUtil.readRow(headerRow, new TrimEditor()).stream()
                .map(Object::toString).filter(StrUtil::isNotBlank).collect(Collectors.toList());
    }

    public static List<Map<String, String>> readAllData(Sheet sheet) {
        return readAllData(sheet, Collections.emptyMap());
    }

    public static List<Map<String, String>> readAllData(Sheet sheet, Map<String, String> headerAlias) {
        try (ExcelReader reader = new ExcelReader(sheet)) {
            if (CollUtil.isNotEmpty(headerAlias)) {
                reader.setHeaderAlias(headerAlias);
            }

            return reader.readAll().stream().map(row -> {
                Map<String, String> newRow = new LinkedHashMap<>();
                row.forEach((key, value) -> {
                    if (StrUtil.isNotBlank(key)) {
                        newRow.put(key, toStr(key, value));
                    }
                });
                return newRow;
            }).filter(row -> StrUtils.isNotAllBlank(row.values())).collect(Collectors.toList());
        }
    }

    private static String toStr(String key, Object cellValue) {
        String strValue;
        if (cellValue instanceof DateTime) {
            strValue = convertDateTimeToStr(key, (DateTime)cellValue);
        } else {
            strValue = String.valueOf(cellValue).trim();
        }

        return strValue;
    }

    private static String convertDateTimeToStr(String header, DateTime value) {
        if (header.endsWith(HEADER_SUFFIX_DATE)) {
            return value.toString("yyyy-MM-dd");
        }

        int hours = value.getField(DateField.HOUR);
        int minutes = value.getField(DateField.MINUTE);
        int seconds = value.getField(DateField.SECOND);

        if (hours == 0 && minutes == 0 && seconds == 0) {
            return value.toString("yyyy-MM-dd");
        } else if (seconds == 0) {
            return value.toString("yyyy-MM-dd HH:mm");
        } else {
            return value.toString("yyyy-MM-dd HH:mm:ss");
        }
    }
}

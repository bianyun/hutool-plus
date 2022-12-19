package plus.hutool.media.document.excel;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.map.MapUtil;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ExcelWorksheetUtilsTest {

    @Test
    void testReadHeaders() {
        final File xlsFile = FileUtil.file("classpath:testFiles/documents/test.xls");
        final Workbook workbook = ExcelWorkbookUtils.getWorkbook(xlsFile);

        final Sheet sheet1 = workbook.getSheetAt(0);
        final Sheet sheet2 = workbook.getSheetAt(1);
        assertThat(sheet1.getSheetName()).isEqualTo("工作表1");
        assertThat(sheet2.getSheetName()).isEqualTo("工作表2");

        assertThat(ExcelWorksheetUtils.readHeaders(sheet1)).isEqualTo(Arrays.asList("工号", "姓名", "用户名", "密码", "登录时间"));
        assertThat(ExcelWorksheetUtils.readHeaders(sheet2)).isEqualTo(Arrays.asList("ID", "系统名称", "系统编码", "存储编码", "创建日期"));
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Test
    void testReadAllData1() {
        final File xlsFile = FileUtil.file("classpath:testFiles/documents/test.xls");
        final Workbook workbook = ExcelWorkbookUtils.getWorkbook(xlsFile);

        final Sheet sheet1 = workbook.getSheetAt(0);
        final Sheet sheet2 = workbook.getSheetAt(1);
        assertThat(sheet1.getSheetName()).isEqualTo("工作表1");
        assertThat(sheet2.getSheetName()).isEqualTo("工作表2");

        final List<Map<String, String>> result1 = ExcelWorksheetUtils.readAllData(sheet1);
        assertThat(result1).hasSize(6);
        assertThat(result1.get(0)).containsKeys("工号", "姓名", "用户名", "密码", "登录时间");
        assertThat(result1.get(0).get("工号")).isEqualTo("10001");
        assertThat(result1.get(0).get("姓名")).isEqualTo("郭靖");
        assertThat(result1.get(0).get("用户名")).isEqualTo("guojing");
        assertThat(result1.get(0).get("密码")).isEqualTo("gj123");
        assertThat(result1.get(0).get("登录时间")).isEqualTo("2022-12-14 09:15:24");
        assertThat(result1.get(5)).containsKeys("工号", "姓名", "用户名", "密码");
        assertThat(result1.get(5)).containsValues("10006", "陆小凤", "luxiaofeng", "lxf123");

        final List<Map<String, String>> result2 = ExcelWorksheetUtils.readAllData(sheet2);
        assertThat(result2).hasSize(5);
        assertThat(result2.get(0)).containsKeys("ID", "系统名称", "系统编码", "存储编码", "创建日期");
        assertThat(result2.get(0).get("ID")).isEqualTo("10001");
        assertThat(result2.get(0).get("系统名称")).isEqualTo("营销");
        assertThat(result2.get(0).get("系统编码")).isEqualTo("99012");
        assertThat(result2.get(0).get("存储编码")).isEqualTo("oss");
        assertThat(result2.get(0).get("创建日期")).isEqualTo("2018-05-01");
        assertThat(result2.get(4)).containsKeys("ID", "系统名称", "系统编码", "存储编码", "创建日期");
        assertThat(result2.get(4)).containsValues("10005", "大数据平台", "99016", "hdfs", "2018-05-05");
    }

    @SuppressWarnings("SpellCheckingInspection")
    @Test
    void testReadAllData2() {
        final File xlsFile = FileUtil.file("classpath:testFiles/documents/test.xls");
        final Workbook workbook = ExcelWorkbookUtils.getWorkbook(xlsFile);

        final Sheet sheet1 = workbook.getSheetAt(0);
        final Sheet sheet2 = workbook.getSheetAt(1);
        assertThat(sheet1.getSheetName()).isEqualTo("工作表1");
        assertThat(sheet2.getSheetName()).isEqualTo("工作表2");

        final Map<String, String> headerAlias1 = MapUtil.<String, String>builder()
                .put("工号", "employeeId")
                .put("姓名", "name")
                .put("用户名", "username")
                .put("密码", "password")
                .put("登录时间", "loginTime")
                .build();
        final List<Map<String, String>> result1 = ExcelWorksheetUtils.readAllData(sheet1, headerAlias1);

        assertThat(result1).hasSize(6);
        assertThat(result1.get(0)).containsKeys("employeeId", "name", "username", "password", "loginTime");
        assertThat(result1.get(0).get("employeeId")).isEqualTo("10001");
        assertThat(result1.get(0).get("name")).isEqualTo("郭靖");
        assertThat(result1.get(0).get("username")).isEqualTo("guojing");
        assertThat(result1.get(0).get("password")).isEqualTo("gj123");
        assertThat(result1.get(0).get("loginTime")).isEqualTo("2022-12-14 09:15:24");
        assertThat(result1.get(5)).containsKeys("employeeId", "name", "username", "password");
        assertThat(result1.get(5)).containsValues("10006", "陆小凤", "luxiaofeng", "lxf123");

        final Map<String, String> headerAlias2 = MapUtil.<String, String>builder()
                .put("ID", "id")
                .put("系统名称", "sysName")
                .put("系统编码", "sysCode")
                .put("存储编码", "storeCode")
                .put("创建日期", "createdDate")
                .build();
        final List<Map<String, String>> result2 = ExcelWorksheetUtils.readAllData(sheet2, headerAlias2);
        assertThat(result2).hasSize(5);
        assertThat(result2.get(0)).containsKeys("id", "sysName", "sysCode", "storeCode", "createdDate");
        assertThat(result2.get(0).get("id")).isEqualTo("10001");
        assertThat(result2.get(0).get("sysName")).isEqualTo("营销");
        assertThat(result2.get(0).get("sysCode")).isEqualTo("99012");
        assertThat(result2.get(0).get("storeCode")).isEqualTo("oss");
        assertThat(result2.get(0).get("createdDate")).isEqualTo("2018-05-01");
        assertThat(result2.get(4)).containsKeys("id", "sysName", "sysCode", "storeCode", "createdDate");
        assertThat(result2.get(4)).containsValues("10005", "大数据平台", "99016", "hdfs", "2018-05-05");
    }
}

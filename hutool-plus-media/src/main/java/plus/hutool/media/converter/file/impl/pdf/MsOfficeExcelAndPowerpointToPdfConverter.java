package plus.hutool.media.converter.file.impl.pdf;

import cn.hutool.core.util.ClassUtil;
import com.itextpdf.commons.actions.ProductNameConstant;
import com.itextpdf.pdfoffice.OfficeConverter;
import plus.hutool.core.iterable.collection.CollUtils;
import plus.hutool.core.lang.ReflectUtils;
import plus.hutool.media.content.type.MediaType;
import plus.hutool.media.content.type.MediaTypeUtils;
import plus.hutool.media.converter.file.FileConvertConfig;
import plus.hutool.media.exception.FileConversionException;

import java.io.File;
import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

/**
 * 文件转换器 - (xls, xlsx, ppt, pptx) ==> pdf
 *
 * @author bianyun
 * @date 2023/2/14
 */
@SuppressWarnings({"unused", "JavadocDeclaration"})
public class MsOfficeExcelAndPowerpointToPdfConverter extends AbstractToPdfConverter {
    static {
        crackPdfOffice();
    }

    @Override
    public Set<MediaType> getSupportedSrcMediaTypes() {
        return CollUtils.unmodifiableSet(
                MediaType.APPLICATION_XLS,
                MediaType.APPLICATION_XLSX,
                MediaType.APPLICATION_PPT,
                MediaType.APPLICATION_PPTX,
                MediaType.APPLICATION_PPS,
                MediaType.APPLICATION_PPSX
        );
    }

    private static void crackPdfOffice() {
        Field productNamesField = ClassUtil.getDeclaredField(ProductNameConstant.class, "PRODUCT_NAMES");
        Set<String> oldProductNames = ProductNameConstant.PRODUCT_NAMES;
        Set<String> newProductNames = new HashSet<>(oldProductNames);
        newProductNames.add("pdfOffice");

        ReflectUtils.setStaticFinalFieldValue(productNamesField, newProductNames);
    }

    @Override
    protected void doConvert(File srcFile, File destFile, FileConvertConfig config) {
        MediaType srcMediaType = MediaTypeUtils.detectMediaType(srcFile);
        try {
            if (srcMediaType.isAnyOf(MediaType.APPLICATION_XLS, MediaType.APPLICATION_XLSX)) {
                OfficeConverter.convertOfficeSpreadsheetToPdf(srcFile, destFile);
            } else {
                OfficeConverter.convertOfficePresentationToPdf(srcFile, destFile);
            }
        } catch (Exception e) {
            throw new FileConversionException("文件转换出错", e);
        }
    }

}

package com.coderman.admin.utils;

import com.aspose.cells.*;
import lombok.extern.slf4j.Slf4j;

import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class PdfUtil {

    static {
        try (InputStream licenseStream = PdfUtil.class.getResourceAsStream("/aspose_cell_license.xml")) {
            License license = new License();
            license.setLicense(licenseStream);
            log.info("Aspose License 注册成功，到期日为: {}", License.getSubscriptionExpireDate());
        } catch (Exception ex) {
            log.error("Aspose License 注册失败: {}", ex.getMessage(), ex);
        }
    }

    public static void excel2pdf(InputStream inputStream, OutputStream outputStream) throws Exception {
        try {
            Workbook workbook = new Workbook(inputStream);

            PdfSaveOptions pdfOptions = new PdfSaveOptions();
            pdfOptions.setOnePagePerSheet(true);

            Style borderStyle = workbook.createStyle();
            borderStyle.setBorder(BorderType.TOP_BORDER, CellBorderType.THIN, Color.getBlack());
            borderStyle.setBorder(BorderType.BOTTOM_BORDER, CellBorderType.THIN, Color.getBlack());
            borderStyle.setBorder(BorderType.LEFT_BORDER, CellBorderType.THIN, Color.getBlack());
            borderStyle.setBorder(BorderType.RIGHT_BORDER, CellBorderType.THIN, Color.getBlack());
            borderStyle.setHorizontalAlignment(TextAlignmentType.CENTER);

            StyleFlag styleFlag = new StyleFlag();
            styleFlag.setBorders(true);

            for (int i = 0; i < workbook.getWorksheets().getCount(); i++) {
                Worksheet sheet = workbook.getWorksheets().get(i);
                if (!sheet.isVisible()) {
                    sheet.clearComments();
                    continue;
                }

                Cells cells = sheet.getCells();
                cells.deleteBlankRows();
                cells.deleteBlankColumns();
                sheet.autoFitColumns();
                sheet.autoFitRows();
                cells.applyStyle(borderStyle, styleFlag);

                PageSetup setup = sheet.getPageSetup();
                setup.setFitToPagesWide(1);
                setup.setFitToPagesTall(0);
            }

            workbook.save(outputStream, pdfOptions);
            outputStream.flush();
            log.info("✅ Excel 转 PDF 成功！");
        } catch (Exception e) {
            log.error("❌ Excel 转 PDF 失败：{}", e.getMessage(), e);
            throw e;
        }
    }
}

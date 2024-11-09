package com.coderman.admin.utils;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.AbstractColumnWidthStyleStrategy;
import com.coderman.api.exception.BusinessException;
import com.coderman.service.util.HttpContextUtil;
import org.apache.poi.ss.usermodel.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：zhangyukang
 * @date ：2024/11/04 15:52
 */
public class EasyExcelUtils {

    /**
     * 导出excel
     * @param clazz 类型
     * @param list 数据
     * @param fileName 文件名
     * @param <T> 类型
     */
    public static <T>void exportExcel(Class<T> clazz , List<T> list, String fileName){

        HttpServletResponse response = HttpContextUtil.getHttpServletResponse();

        String encodedFileName;
        try {
            encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.toString());
            response.setHeader("Content-disposition", "attachment;filename=" + encodedFileName);
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("utf-8");
            EasyExcel.write(response.getOutputStream(), clazz)
                    .useDefaultStyle(false)
                    // 样式修改
                    .registerWriteHandler(EasyExcelUtils.buildDefStyle())
                    // 宽度自适应
                    .registerWriteHandler(new AbstractColumnWidthStyleStrategy() {

                        private static final int MAX_COLUMN_WIDTH = 25;
                        private final Map<Integer, Map<Integer, Integer>> cache = MapUtils.newHashMapWithExpectedSize(8);
                        @Override
                        protected void setColumnWidth(WriteSheetHolder writeSheetHolder, List<WriteCellData<?>> cellDataList, Cell cell,
                                                      Head head,
                                                      Integer relativeRowIndex, Boolean isHead) {
                            boolean needSetWidth = isHead || !org.apache.commons.collections4.CollectionUtils.isEmpty(cellDataList);
                            if (!needSetWidth) {
                                return;
                            }
                            Map<Integer, Integer> maxColumnWidthMap = cache.computeIfAbsent(
                                    writeSheetHolder.getSheetNo(),
                                    k -> new HashMap<>(16)
                            );
                            Integer columnWidth = dataLength(cellDataList, cell, isHead);
                            if (columnWidth < 0) {
                                return;
                            }
                            if (columnWidth > MAX_COLUMN_WIDTH) {
                                columnWidth = MAX_COLUMN_WIDTH;
                            }
                            Integer maxColumnWidth = maxColumnWidthMap.get(cell.getColumnIndex());
                            if (maxColumnWidth == null || columnWidth > maxColumnWidth) {
                                maxColumnWidthMap.put(cell.getColumnIndex(), columnWidth);
                                writeSheetHolder.getSheet().setColumnWidth(cell.getColumnIndex(), columnWidth * 256);
                            }
                        }

                        private Integer dataLength(List<WriteCellData<?>> cellDataList, Cell cell, Boolean isHead) {
                            if (isHead) {
                                return cell.getStringCellValue().getBytes().length;
                            }
                            WriteCellData<?> cellData = cellDataList.get(0);
                            CellDataTypeEnum type = cellData.getType();
                            if (type == null) {
                                return -1;
                            }
                            switch (type) {
                                case STRING:
                                    return cellData.getStringValue().getBytes().length;
                                case BOOLEAN:
                                    return cellData.getBooleanValue().toString().getBytes().length;
                                case NUMBER:
                                    return cellData.getNumberValue().toString().getBytes().length;
                                default:
                                    return -1;
                            }
                        }
                    })
                    .sheet()
                    .doWrite(list);
        } catch (Exception e) {
            throw new BusinessException("导出文件失败:"+ e.getMessage());
        }
    }


    /**
     * 表头样式
     */
    private static WriteFont buildHeadWriteFont() {
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("宋体");
        headWriteFont.setFontHeightInPoints((short) 10);
        headWriteFont.setBold(false);
        headWriteFont.setColor(IndexedColors.WHITE.getIndex());
        return headWriteFont;
    }


    private static WriteCellStyle defCellStyle() {
        WriteCellStyle style = new WriteCellStyle();
        //默认都是居中
        style.setHorizontalAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        style.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());

        style.setWriteFont(buildHeadWriteFont());
        return style;
    }


    private static WriteCellStyle buildHeadStyle() {
        WriteCellStyle style = defCellStyle();
        // 头部居中
        style.setHorizontalAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        style.setWriteFont(buildHeadWriteFont());
        return style;
    }

    private static WriteCellStyle buildContentStyle() {
        WriteCellStyle style = defCellStyle();
        // 内容左对齐
        style.setHorizontalAlignment(HorizontalAlignment.LEFT);
        style.setWriteFont(new WriteFont());
        //设置 自动不换行
        style.setWrapped(false);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // 内容区域字体设置
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontName("Arial");
        contentWriteFont.setFontHeightInPoints((short) 10);
        contentWriteFont.setColor(IndexedColors.BLACK.getIndex());
        style.setWriteFont(contentWriteFont);
        return style;
    }


    public static HorizontalCellStyleStrategy buildDefStyle() {
        WriteCellStyle headStyle = buildHeadStyle();
        WriteCellStyle contentStyle = buildContentStyle();
        return new HorizontalCellStyleStrategy(headStyle, contentStyle);
    }

}
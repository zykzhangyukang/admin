package com.coderman.admin.vo.func;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.*;
import com.alibaba.excel.enums.BooleanEnum;
import com.alibaba.excel.enums.poi.BorderStyleEnum;
import com.alibaba.excel.enums.poi.FillPatternTypeEnum;
import com.alibaba.excel.enums.poi.HorizontalAlignmentEnum;
import com.alibaba.excel.enums.poi.VerticalAlignmentEnum;
import com.coderman.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：zhangyukang
 * @date ：2024/11/20 14:36
 */
@EqualsAndHashCode(callSuper = true)
@Data
@HeadRowHeight(30)
@HeadStyle(fillBackgroundColor= 55, horizontalAlignment = HorizontalAlignmentEnum.CENTER,
        borderLeft = BorderStyleEnum.THIN, borderRight = BorderStyleEnum.THIN, borderTop = BorderStyleEnum.THIN, borderBottom = BorderStyleEnum.THIN)
@HeadFontStyle(fontHeightInPoints = 9, bold = BooleanEnum.TRUE)
@ContentStyle(verticalAlignment = VerticalAlignmentEnum.CENTER,
        wrapped = BooleanEnum.TRUE,
        fillForegroundColor = 1,
        fillPatternType = FillPatternTypeEnum.SOLID_FOREGROUND,
        bottomBorderColor = 0, borderBottom = BorderStyleEnum.THIN,
        topBorderColor = 0, borderTop = BorderStyleEnum.THIN,
        leftBorderColor = 0, borderLeft = BorderStyleEnum.THIN,
        rightBorderColor = 0, borderRight = BorderStyleEnum.THIN)
@ContentFontStyle(fontHeightInPoints = 9)
public class FuncExcelVO extends BaseModel {

    @ExcelProperty(value = "ID")
    @ExcelIgnore
    private Integer funcId;

    @ExcelProperty(value = "父ID")
    @ExcelIgnore
    private Integer parentId;

    @ExcelProperty(value = "功能名称")
    @ColumnWidth(value = 20)
    private String funcName;

    @ExcelProperty(value = "功能key")
    @ColumnWidth(value = 20)
    private String funcKey;

    @ExcelProperty(value = "资源列表")
    @ColumnWidth(value = 40)
    private String rescUrlList;

    @ExcelProperty(value = "父级功能")
    @ColumnWidth(value = 20)
    private String parentFuncName;

    @ExcelProperty(value = "父级功能key")
    @ColumnWidth(value = 20)
    private String parentFuncKey;

    @ExcelProperty(value = "功能类型")
    @ColumnWidth(value = 20)
    private String funcType;

    @ExcelProperty(value = "是否隐藏")
    @ColumnWidth(value = 20)
    private String hide;
}

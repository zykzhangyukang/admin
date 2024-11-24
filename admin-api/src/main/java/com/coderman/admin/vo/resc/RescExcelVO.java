package com.coderman.admin.vo.resc;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
 * @author ：zhangyukang
 * @date ：2024/11/20 14:36
 */
@Data
public class RescExcelVO {

    @ExcelProperty(value = "资源名称")
    private String rescName;

    @ExcelProperty(value = "资源url")
    private String rescUrl;

    @ExcelProperty(value = "资源所属系统")
    private String rescDomain;

    @ExcelProperty(value = "请求方式")
    private String methodType;

    @ExcelProperty(value = "创建时间")
    @ColumnWidth(value = 20)
    private Date createTime;

    @ExcelProperty(value = "更新时间")
    @ColumnWidth(value = 20)
    private Date updateTime;

}

package com.coderman.admin.vo.role;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
 * @author ：zhangyukang
 * @date ：2024/11/20 14:36
 */
@Data
public class RoleExcelVO {


    @ExcelProperty(value = "角色名称")
    private String roleName;

    @ExcelProperty(value = "角色描述")
    private String roleDesc;

    @ExcelProperty(value = "创建时间")
    @ColumnWidth(value = 20)
    private Date createTime;

    @ExcelProperty(value = "更新时间")
    @ColumnWidth(value = 20)
    private Date updateTime;
}

package com.coderman.admin.vo.user;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.util.Date;

/**
 * @author ：zhangyukang
 * @date ：2024/11/04 15:27
 */
@Data
public class UserExcelVO {

    @ExcelProperty(value = "用户账号")
    private String username;

    @ExcelProperty(value = "真实名称")
    private String realName;

    @ExcelProperty(value = "部门")
    private String deptName;

    @ExcelProperty(value = "头像地址")
    private String avatar;

    @ExcelProperty(value = "邮箱地址")
    private String email;

    @ExcelProperty(value = "联系方式")
    private String phone;

    @ExcelProperty(value = "状态")
    private Integer userStatus;

    @ExcelProperty(value = "创建时间")
    @ColumnWidth(value = 20)
    private Date createTime;
}

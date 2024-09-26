package com.coderman.admin.dto.user;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户更新DTO
 * @author zhangyukang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserUpdateDTO extends BaseModel {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "真实名称")
    private String realName;


    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "联系方式")
    private String phone;

    @ApiModelProperty(value = "部门id")
    private Integer deptId;

    @ApiModelProperty(value = "状态")
    private Integer userStatus;
}

package com.coderman.admin.model.role;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * This is the base record class for table: auth_role_func
 * Generated by MyBatis Generator.
 * @author MyBatis Generator
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ApiModel(value="RoleFuncModel", description = "auth_role_func 实体类")
public class RoleFuncModel extends BaseModel {
    

    @ApiModelProperty(value = "主键")
    private Integer roleFuncId;

    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    @ApiModelProperty(value = "功能id")
    private Integer funcId;
}
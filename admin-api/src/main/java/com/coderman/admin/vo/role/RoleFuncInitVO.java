package com.coderman.admin.vo.role;

import com.coderman.admin.vo.func.FuncTreeVO;
import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class RoleFuncInitVO extends BaseModel {

    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "角色描述")
    private String roleDesc;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "功能树")
    private List<FuncTreeVO> allTreeList;

    @ApiModelProperty(value = "用户列表")
    private List<RoleUserVO> userList;

    @ApiModelProperty(value = "已分配的功能id")
    private List<Integer> funcIdList;

}

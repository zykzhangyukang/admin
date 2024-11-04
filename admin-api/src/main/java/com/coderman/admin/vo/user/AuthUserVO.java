package com.coderman.admin.vo.user;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 当前登入用户信息
 * @author zhangyukang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuthUserVO extends BaseModel {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "部门名称")
    private String deptName;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "部门编号")
    private Integer deptId;

    @ApiModelProperty(value = "真实名称")
    private String realName;

    @ApiModelProperty(value = "访问令牌")
    private String accessToken;

    @ApiModelProperty(value = "刷新令牌")
    private String refreshToken;

    @ApiModelProperty(value = "会话过期时间")
    private Long expiredTime;

    @ApiModelProperty(value = "资源id")
    private List<Integer> rescIdList;

    @ApiModelProperty(value = "角色")
    private List<String> roleList;
}

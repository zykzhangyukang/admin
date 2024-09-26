package com.coderman.admin.dto.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 用户分配功能DTO
 * @author ：zhangyukang
 * @date ：2024/09/26 14:36
 */
@Data
public class UserFuncUpdateDTO {

    @ApiModelProperty(value = "用户id")
    private Integer userId;

    @ApiModelProperty(value = "功能id集合")
    private List<Integer> funcIdList;
}

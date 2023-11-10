package com.coderman.admin.vo.func;

import com.coderman.admin.model.func.FuncModel;
import com.coderman.admin.vo.user.UserVO;
import com.coderman.admin.vo.resc.RescVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author coderman
 * @Title: 功能
 * @date 2022/3/1915:35
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FuncVO extends FuncModel {

    @ApiModelProperty(value = "用户信息")
    private List<UserVO> userVOList;

    @ApiModelProperty(value = "资源信息")
    private List<RescVO> rescVOList;
}

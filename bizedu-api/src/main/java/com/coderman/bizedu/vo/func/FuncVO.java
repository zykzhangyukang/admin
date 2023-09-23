package com.coderman.bizedu.vo.func;

import com.coderman.bizedu.model.func.FuncModel;
import com.coderman.bizedu.vo.resc.RescVO;
import com.coderman.bizedu.vo.user.UserVO;
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

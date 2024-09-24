package com.coderman.admin.vo.func;

import com.coderman.admin.model.func.FuncModel;
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

    @ApiModelProperty(value = "资源信息")
    private List<FuncRescVO> rescVOList;
}

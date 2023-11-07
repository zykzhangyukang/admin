package com.coderman.bizedu.dto.sync;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangyukang
 */
@Data
public class CallbackRepeatDTO implements Serializable {

    @ApiModelProperty(value = "目标项目")
    private String destProject;

    @ApiModelProperty(value = "uuid集合")
    private List<String> uuidList;
}

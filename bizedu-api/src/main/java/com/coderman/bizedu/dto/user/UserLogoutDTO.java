package com.coderman.bizedu.dto.user;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：zhangyukang
 * @date ：2023/09/28 11:20
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserLogoutDTO extends BaseModel {

    @ApiModelProperty(value = "权限令牌")
    private String token;

}

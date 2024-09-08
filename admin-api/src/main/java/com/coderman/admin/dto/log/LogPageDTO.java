package com.coderman.admin.dto.log;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：zhangyukang
 * @date ：2023/09/27 17:22
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LogPageDTO extends BaseModel {

    @ApiModelProperty(value = "当前页")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize;

    @ApiModelProperty(value = "日志模块")
    private String logModule;

    @ApiModelProperty(value = "日志等级")
    private String logLevel;

    @ApiModelProperty(value = "账号")
    private String username;

    @ApiModelProperty(value = "用户名")
    private String realName;

    @ApiModelProperty(value = "IP地址")
    private String ipAddress;

    @ApiModelProperty(value = "排序类型")
    private String sortType;

    @ApiModelProperty(value = "排序字段")
    private String sortField;
}

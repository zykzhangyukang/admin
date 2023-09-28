package com.coderman.bizedu.dto.catalog;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：zhangyukang
 * @date ：2023/09/28 14:12
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CatalogUpdateDTO extends BaseModel {

    @ApiModelProperty(value = "分类id")
    private Integer catalogId;

    @ApiModelProperty(value = "分类名称")
    private String catalogName;
}

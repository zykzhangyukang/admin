package com.coderman.bizedu.dto.catalog;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author ：zhangyukang
 * @date ：2023/09/28 14:05
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CatalogSaveDTO extends BaseModel {

    @ApiModelProperty(value = "分类名称")
    private String catalogName;

    @ApiModelProperty(value = "父级分类")
    private Integer parentId;
}

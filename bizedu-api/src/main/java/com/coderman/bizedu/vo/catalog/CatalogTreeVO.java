package com.coderman.bizedu.vo.catalog;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author ：zhangyukang
 * @date ：2023/09/18 17:34
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CatalogTreeVO extends BaseModel {

    @ApiModelProperty(value = "分类id")
    private Integer catalogId;

    @ApiModelProperty(value = "分类名称")
    private String catalogName;

    @ApiModelProperty(value = "父级分类")
    private Integer parentId;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "子级分类")
    private List<CatalogTreeVO> children;
}

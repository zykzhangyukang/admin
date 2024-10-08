package com.coderman.admin.dto.func;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyukang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FuncSaveDTO extends BaseModel {

    @ApiModelProperty("功能名称")
    private String funcName;

    @ApiModelProperty("功能key")
    private String funcKey;

    @ApiModelProperty("功能类型(目录/功能)")
    private String funcType;

    @ApiModelProperty("功能排序")
    private Integer funcSort;

    @ApiModelProperty("是否隐藏")
    private Integer hide;

    @ApiModelProperty("父级功能id")
    private Integer parentId;


}

package com.coderman.admin.dto.func;

import com.coderman.api.model.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.List;

/**
 * @author zhangyukang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FuncPageDTO extends BaseModel {

    @ApiModelProperty(value = "当前页")
    private Integer currentPage;

    @ApiModelProperty(value = "每页显示条数")
    private Integer pageSize;

    @ApiModelProperty(value = "资源url")
    private String rescUrl;

    @ApiModelProperty("功能名称")
    private String funcName;

    @ApiModelProperty("功能key")
    private String funcKey;

    @ApiModelProperty("功能类型(目录/功能)")
    private String funcType;

    @ApiModelProperty(value = "菜单显示状态")
    private Integer hide;

    @ApiModelProperty("父级功能id")
    private Integer parentId;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "排序类型")
    private String sortType;

    @ApiModelProperty(value = "排序字段")
    private String sortField;

    @ApiModelProperty(value = "勾选的id")
    private List<Integer> idList;
}

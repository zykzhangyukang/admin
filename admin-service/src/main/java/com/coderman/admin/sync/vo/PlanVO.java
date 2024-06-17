package com.coderman.admin.sync.vo;

import com.coderman.admin.sync.model.PlanModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zhangyukang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PlanVO extends PlanModel {

    private String description;
}

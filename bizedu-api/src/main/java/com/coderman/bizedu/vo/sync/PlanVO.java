package com.coderman.bizedu.vo.sync;

import com.coderman.bizedu.model.sync.PlanModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Administrator
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PlanVO extends PlanModel {

    private String description;
}

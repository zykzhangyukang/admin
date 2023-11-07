package com.coderman.bizedu.sync.plan.meta;

import com.coderman.api.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Map;

/**
 * @author zhangyukang
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ColumnMeta extends BaseModel {

    private String src;

    private String dest;

    private String type;

    private Map<String,String> converts;
}

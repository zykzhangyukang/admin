package com.coderman.admin.sync.plan.meta;

import lombok.Data;

/**
 * @author zhangyukang
 */
@Data
public class CallbackMeta {

    private String project;

    private String desc;

    public CallbackMeta() {
    }

    public CallbackMeta(String project, String desc) {
        this.project = project;
        this.desc = desc;
    }
}

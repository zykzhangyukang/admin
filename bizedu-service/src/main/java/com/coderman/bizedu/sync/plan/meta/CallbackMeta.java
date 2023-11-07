package com.coderman.bizedu.sync.plan.meta;

import lombok.Data;

/**
 * @author Administrator
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

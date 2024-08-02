package com.coderman.admin.utils;

public enum ProjectEnum {

    /**
     * 后台系统
     */
    ADMIN("admin"),

    /**
     * 后台sync系统
     */
    ADMIN_SYNC("admin_sync");

    private String key;


    ProjectEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

}

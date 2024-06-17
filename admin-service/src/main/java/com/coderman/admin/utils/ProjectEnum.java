package com.coderman.admin.utils;

public enum ProjectEnum {

    /**
     * 后台系统
     */
    ADMIN("admin"),

    /**
     * 社区系统
     */
    CLUB("club");


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

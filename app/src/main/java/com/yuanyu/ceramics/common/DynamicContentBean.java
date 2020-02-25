package com.yuanyu.ceramics.common;

public class DynamicContentBean {
    private String content;
    private int flag;
    private String id;

    public DynamicContentBean(String content, int flag, String id) {
        this.content = content;
        this.flag = flag;
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public int getFlag() {
        return flag;
    }

    public String getId() {
        return id;
    }
}

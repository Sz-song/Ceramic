package com.yuanyu.ceramics.center_circle.release;

public class DraftsBean {
    private int type;
    private String time;
    private String content;
    private String id;

    public DraftsBean(int type, String time, String content, String id) {
        this.type = type;
        this.time = time;
        this.content = content;
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

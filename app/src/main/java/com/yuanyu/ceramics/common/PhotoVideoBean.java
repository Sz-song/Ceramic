package com.yuanyu.ceramics.common;



public class PhotoVideoBean {
    private String url;
    private int type;//0 添加图片  1 添加视频 2 图片 3 视频

    public PhotoVideoBean(String url, int type) {
        this.url = url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

package com.yuanyu.ceramics.common;

public class FenleiTypeBean {
    private String type;
    private boolean isChoose;

    public FenleiTypeBean(String type)
    {
        this.type = type;
        isChoose = false;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isChoose() {
        return isChoose;
    }

    public void setChoose(boolean choose) {
        isChoose = choose;
    }
}

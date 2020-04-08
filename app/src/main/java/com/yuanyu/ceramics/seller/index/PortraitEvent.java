package com.yuanyu.ceramics.seller.index;

public class PortraitEvent {
    private int code;
    private String portrait;

    public PortraitEvent(int code, String portrait) {
        this.code = code;
        this.portrait = portrait;
    }

    public int getCode() {
        return code;
    }

    public String getPortrait() {
        return portrait;
    }
}

package com.yuanyu.ceramics.personal_index.fans_focus;

public class FocusAndFansBean {
    private String id;
    private String name;
    private String portrait;
    private String txt;
    private int isfocus;
    private int fans_num;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getTxt() {
        return txt;
    }


    public int getFans_num() {
        return fans_num;
    }

    public int isIsfocus() {
        return isfocus;
    }

    public void setIsfocus(int isfocus) {
        this.isfocus = isfocus;
    }

    public void setFans_num(int fans_num) {
        this.fans_num = fans_num;
    }
}

package com.yuanyu.ceramics.search;

public class SearchMasterBean {
    private int useraccountid;
    private String portrait;
    private String name;
    private String fans_num;
    private String focus_num;
    private String introduce;
    private boolean isfollowed;

    public int getUseraccountid() {
        return useraccountid;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getName() {
        return name;
    }

    public String getFans_num() {
        return fans_num;
    }

    public String getFocus_num() {
        return focus_num;
    }

    public String getIntroduce() {
        return introduce;
    }

    public boolean isIsfollowed() {
        return isfollowed;
    }

    public void setIsfollowed(boolean isfollowed) {
        this.isfollowed = isfollowed;
    }
}

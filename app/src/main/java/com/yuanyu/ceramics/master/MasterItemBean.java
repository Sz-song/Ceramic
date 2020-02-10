package com.yuanyu.ceramics.master;

import java.util.List;

public class MasterItemBean {
    private int id;
    private String portrait;
    private String name;
    private String introduce;
    private List<String> tag;

    public int getId() { return id;}

    public String getPortrait() {
        return portrait;
    }

    public String getName() {
        return name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public List<String> getTag() {
        return tag;
    }
}

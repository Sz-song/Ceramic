package com.yuanyu.ceramics.fenlei;

public class ClassifyBean {
    private String classify;
    private boolean select;

    ClassifyBean(String classify, boolean select) {
        this.classify = classify;
        this.select = select;
    }

    public String getClassify() {
        return classify;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }
}


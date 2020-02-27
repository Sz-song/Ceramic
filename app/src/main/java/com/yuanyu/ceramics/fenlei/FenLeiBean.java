package com.yuanyu.ceramics.fenlei;

public class FenLeiBean {
    private String name;
    private boolean isSelect;

    public FenLeiBean(String name, boolean isSelect) {
        this.name = name;
        this.isSelect = isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getName() {
        return name;
    }

    public boolean isSelect() {
        return isSelect;
    }

}

package com.yuanyu.ceramics.mycoins;

public class MyCoinsDetailBean {
    private String id;
    private String source;//(金币来源)
    private String time;
    private int num;//(正数表示增加，负数表示减少（兑换）);

    public String getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getTime() {
        return time;
    }

    public int getNum() {
        return num;
    }
}

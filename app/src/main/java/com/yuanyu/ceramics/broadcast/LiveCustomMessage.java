package com.yuanyu.ceramics.broadcast;

public class LiveCustomMessage {
    private String id;
    private String msg;
    private int type;

    public LiveCustomMessage(String id, String msg, int type) {
        this.id = id;
        this.msg = msg;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    public int getType() {
        return type;
    }
}

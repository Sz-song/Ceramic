package com.yuanyu.ceramics.broadcast.pull;

public class LiveChatBean {
    private String useraccountid;
    private String uickname;
    private String message;
    private int type;

    public LiveChatBean(String useraccountid, String uickname, String message, int type) {
        this.useraccountid = useraccountid;
        this.uickname = uickname;
        this.message = message;
        this.type = type;
    }

    public String getUseraccountid() {
        return useraccountid;
    }

    public String getUickname() {
        return uickname;
    }

    public String getMessage() {
        return message;
    }

    public int getType() {
        return type;
    }
}

package com.yuanyu.ceramics.broadcast.pull;

public class LiveChatBean {
    private String useraccountid;
    private String uickname;
    private String message;

    public LiveChatBean(String useraccountid, String uickname, String message) {
        this.useraccountid = useraccountid;
        this.uickname = uickname;
        this.message = message;
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
}

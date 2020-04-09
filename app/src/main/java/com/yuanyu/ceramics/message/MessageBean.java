package com.yuanyu.ceramics.message;

public class MessageBean {
    private String  useraccountid;
    private String  icon;
    private String  nickname;
    private long  time;
    private String  lastMsg;
    private long unreadnum;


    public String getUseraccountid() {
        return useraccountid;
    }

    public long getUnreadnum() {
        return unreadnum;
    }

    public void setUnreadnum(long unreadnum) {
        this.unreadnum = unreadnum;
    }

    public String getIcon() {
        return icon;
    }

    public String getNickname() {
        return nickname;
    }



    public String getLastMsg() {
        return lastMsg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setLastMsg(String lastMsg) {
        this.lastMsg = lastMsg;
    }

    public void setUseraccountid(String useraccountid) {
        this.useraccountid = useraccountid;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

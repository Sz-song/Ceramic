package com.yuanyu.ceramics.chat;

class ChatEntity {
    private String useraccountid;//发送人id
    private String sendname;//发送人昵称
    private String msg;//消息内容
    private String avatar;//发送人头像
    private long time;//发送时间
    private int type;//消息类型

    public ChatEntity(String useraccountid, String sendname, String msg, String avatar, long time, int type) {
        this.useraccountid = useraccountid;
        this.sendname = sendname;
        this.msg = msg;
        this.avatar = avatar;
        this.time = time;
        this.type = type;
    }

    public String getUseraccountid() {
        return useraccountid;
    }

    public String getSendname() {
        return sendname;
    }

    public String getMsg() {
        return msg;
    }

    public String getAvatar() {
        return avatar;
    }

    public long getTime() {
        return time;
    }

    public int getType() {
        return type;
    }
}

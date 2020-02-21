package com.yuanyu.ceramics.common.view.mypicker;

public class CourierTimeBean {
    private String time;
    private boolean ispass;

    public CourierTimeBean(String time, boolean ispass) {
        this.time = time;
        this.ispass = ispass;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isIspass() {
        return ispass;
    }

    public void setIspass(boolean ispass) {
        this.ispass = ispass;
    }
}

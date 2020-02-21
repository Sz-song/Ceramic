package com.yuanyu.ceramics.utils;

import java.util.Date;

public class TimeUtils {
    public static String CountTime(String timestr) {
        try {
            long time = Long.parseLong(timestr + "000");
            Date date = new Date(time);
            if (date.getMinutes() > 9) {
                return ((1900 + date.getYear()) + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes());
            } else {
                return ((1900 + date.getYear()) + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":0" + date.getMinutes());
            }
        } catch (Exception e) {
            return "";
        }
    }
}

package com.yuanyu.ceramics.utils;

import java.text.SimpleDateFormat;
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


    public static String CountTime(long timestr) {
        try {
            Date date = new Date(timestr);
            if (date.getMinutes() > 9) {
                return ((1900 + date.getYear()) + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes());
            } else {
                return ((1900 + date.getYear()) + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":0" + date.getMinutes());
            }
        } catch (Exception e) {
            return "";
        }
    }
    //订单剩余时间
    public static String OrderTimeRemain(String createtime,String systemTime) {
        try {
            long endTime=(Long.parseLong(createtime)+(60*30));
            long currentTime=Long.parseLong(systemTime);
            if(currentTime>endTime){
                return "订单已取消";
            }else{
                long minute=(endTime-currentTime)/60;//转化minute
                return minute+"分钟后订单自动取消";
            }
        }catch (Exception e){
            L.e(e.getMessage());
            return "";
        }
    }
    // 剩余时间 传入long型字符串
    public static String TimeRemain(String finish,String system) {
        try {
            long RemainTime=Long.parseLong(finish)-Long.parseLong(system);
            StringBuilder stringBuilder=new StringBuilder("剩余: ");
            long day=RemainTime/(24*60*60);
            if(day>0){
                stringBuilder.append(day).append("天 ");
            }
            long hour=(RemainTime%(24*60*60))/(60*60);
            if(hour>0){
                stringBuilder.append(hour).append("时 ");
            }
            long min=RemainTime/60;
            if(day==0&&hour==0){
                stringBuilder.append(min).append("分钟 ");
            }
            if(RemainTime<=0){
                return "";
            }
            return stringBuilder.toString();
        }catch (Exception e){
            return "";
        }
    }
    public static String getRemainingTime(String order_time,String current_time){
        try{
            long remainingTime = Long.valueOf(order_time)+30*60 - Long.valueOf(current_time);
            remainingTime = remainingTime>0 ? remainingTime:0;
            SimpleDateFormat sdf = new SimpleDateFormat("mm");
            String result = sdf.format(new Date(remainingTime * 1000L));
            return result;
        }catch (Exception e){
            L.e(e.getMessage());
            return "0";
        }

    }
}

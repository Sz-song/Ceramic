package com.yuanyu.ceramics.utils;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Md5Utils {
    private static final String key = "ls&1997.115*@39a";
    //时间戳
    public static String getTimeStamp() {
        DateFormat fmtDate = new java.text.SimpleDateFormat("yyyyMMdd");
        Calendar dateAndTime = Calendar.getInstance(Locale.CHINA);
        String updataDate;
        updataDate = fmtDate.format(dateAndTime.getTime());
        return updataDate;
    }


    //随机字符串
    public static String getRandomString(int length) { //length表示生成字符串的长度
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

    //签名
    public static String getSignature(String timestamp,String randomstr){
        String signature = toMD5("#"+key+"|"+timestamp+"|"+randomstr+"|"+key+"#");
        return signature;
    }




    public static  String toMD5(String plainText) {
        StringBuilder buf = new StringBuilder();
        try {
            //生成实现指定摘要算法的 MessageDigest 对象。
            MessageDigest md = MessageDigest.getInstance("MD5");
            //使用指定的字节数组更新摘要。
            md.update(plainText.getBytes());
            //通过执行诸如填充之类的最终操作完成哈希计算。
            byte b[] = md.digest();
            //生成具体的md5密码到buf数组
            int i;
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            System.out.println("32位: " + buf.toString());// 32位的加密
            System.out.println("16位: " + buf.toString().substring(8, 24));// 16位的加密，其实就是32位加密后的截取
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return buf.toString();
    }
    // 将时间戳转为字符串
    public static String getStrSecond(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 例如：cc_time=1291778220
        try {
            long lcc_time = Long.valueOf(cc_time);
            re_StrTime = sdf.format(new Date(lcc_time * 1000L));
            return re_StrTime;
        }catch (Exception e){
            return "";
        }
    }
    // 将时间戳转为字符串
    public static String getStrMin(String cc_time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }
    // 将字符串转为时间戳(精确到分)
    public static String getTimeMin(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
        }
        return re_time;
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
    //时间转化
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
    /**
     * 使用java正则表达式去掉多余的.与0
     * @param s
     * @return
     */
    public static String subZeroAndDot(String s){
        if(s.indexOf(".") > 0){
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }
    public static String subZeroAndDot(double s){
        String temp = s+"";
        if(temp.indexOf(".") > 0){
            temp = temp.replaceAll("0+?$", "");//去掉多余的0
            temp = temp.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return temp;
    }
}

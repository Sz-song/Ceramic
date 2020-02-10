package com.yuanyu.ceramics.utils;

import java.security.MessageDigest;
import java.text.DateFormat;
import java.util.Calendar;
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
        StringBuffer buf = new StringBuffer("");
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
}

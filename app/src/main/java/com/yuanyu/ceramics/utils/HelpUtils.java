package com.yuanyu.ceramics.utils;

import java.text.DecimalFormat;

public class HelpUtils {
    //阅读量
    public static String getReadNum(String readnum) {
        try {
            int num=Integer.parseInt(readnum);
            if(num<10000){
                return readnum;
            }else if(num<100000000){
                DecimalFormat df=new DecimalFormat("0.0");
                return df.format((float)num/10000)+"w";
            }else {
                DecimalFormat df=new DecimalFormat("0.0");
                return df.format((float)num/100000000)+"亿";
            }
        }catch (Exception e){
            L.e(e.getMessage());
            return "0";
        }

    }
}

package com.yuanyu.ceramics.item;

import java.util.List;

/**
 * Created by Administrator on 2018-07-10.
 */

public class ItemBean {

    private String goodsname;
    private double goodsprice;
    private double oldprice;
    private String studio;
    private List<String> youhui;
    private List<String> goodslist;
    private List<String> introducelist;
    private boolean iscollected;
    private String sowaccountid;
    private int status;
    private String serial_no;//区块链编号
    private String zhonglei;//种类
    private String pise;//皮色
    private String fenlei;//分类
    private String chanzhuang;//产状
    private String ticai;//题材
    private String weight;//重量
    private String artist;
    private String length;
    private String width;
    private String height;

    public String getGoodsname() {
        return goodsname;
    }

    public void setGoodsprice(double goodsprice) {
        this.goodsprice = goodsprice;
    }

    public double getGoodsprice() {
        return goodsprice;
    }

    public double getOldprice() {
        return oldprice;
    }

    public String getStudio() {
        return studio;
    }

    public List<String> getYouhui() {
        return youhui;
    }

    public List<String> getGoodslist() {
        return goodslist;
    }

    public List<String> getIntroducelist() {
        return introducelist;
    }

    public boolean isIscollected() {
        return iscollected;
    }

    public String getSowaccountid() {
        return sowaccountid;
    }

    public int getStatus() {
        return status;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public String getZhonglei() {
        return zhonglei;
    }

    public String getPise() {
        return pise;
    }

    public String getFenlei() {
        return fenlei;
    }

    public String getChanzhuang() {
        return chanzhuang;
    }

    public String getTicai() {
        return ticai;
    }

    public String getWeight() {
        return weight;
    }

    public String getArtist() {
        return artist;
    }

    public String getLength() {
        return length;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }
}

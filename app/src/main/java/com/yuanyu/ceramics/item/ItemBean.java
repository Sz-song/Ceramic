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

    public ItemBean() {
    }

    public ItemBean(String goodsname, double goodsprice, double oldprice, String studio, List<String> youhui, List<String> goodslist, List<String> introducelist, boolean iscollected, String sowaccountid, int status, String serial_no, String zhonglei, String pise, String fenlei, String chanzhuang, String ticai, String weight, String artist, String length, String width, String height) {
        this.goodsname = goodsname;
        this.goodsprice = goodsprice;
        this.oldprice = oldprice;
        this.studio = studio;
        this.youhui = youhui;
        this.goodslist = goodslist;
        this.introducelist = introducelist;
        this.iscollected = iscollected;
        this.sowaccountid = sowaccountid;
        this.status = status;
        this.serial_no = serial_no;
        this.zhonglei = zhonglei;
        this.pise = pise;
        this.fenlei = fenlei;
        this.chanzhuang = chanzhuang;
        this.ticai = ticai;
        this.weight = weight;
        this.artist = artist;
        this.length = length;
        this.width = width;
        this.height = height;
    }

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

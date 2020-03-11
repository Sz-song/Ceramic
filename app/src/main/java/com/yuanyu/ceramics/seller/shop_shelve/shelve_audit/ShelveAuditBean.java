package com.yuanyu.ceramics.seller.shop_shelve.shelve_audit;

import java.io.Serializable;

/**
 * Created by cat on 2018/8/17.
 */

public class ShelveAuditBean implements Serializable {
    private String image;
    private String name;
    private String artist; //匠人
    private String num;// 数量
    private String price;
    private int type;//审核状态(0审核中1失败2成功3上架记录,即所有数据)
    private String commodityid;
    private String failed_massage;

    public String getFailed_massage() {return failed_massage;}
    public String getImage() {
        return image;
    }
    public String getName() {
        return name;
    }
    public String getArtist() {return artist; }
    public String getNum() {
        return num;
    }
    public String getPrice() {
        return price;
    }
    public int getType() {
        return type;
    }
    public String getCommodityid() {
        return commodityid;
    }

    public ShelveAuditBean(String image, String name, String artist, String num, String price, int type, String commodityid, String failed_massage) {
        this.image = image;
        this.name = name;
        this.artist = artist;
        this.num = num;
        this.price = price;
        this.type = type;
        this.commodityid = commodityid;
        this.failed_massage = failed_massage;
    }
}

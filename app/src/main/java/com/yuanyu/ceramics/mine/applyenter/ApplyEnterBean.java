package com.yuanyu.ceramics.mine.applyenter;

import java.util.List;


public class ApplyEnterBean {
    private String name;
    private String shopname;
    private String sale_type;
    private String primary_sale;
    private String tel;
    private String idcard;
    private String location;
    private List<String> image;

    public ApplyEnterBean(String name, String shopname, String sale_type, String primary_sale, String tel, String idcard, String location, List<String> image) {
        this.name = name;
        this.shopname = shopname;
        this.sale_type = sale_type;
        this.primary_sale = primary_sale;
        this.tel = tel;
        this.idcard = idcard;
        this.location = location;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShopname() {
        return shopname;
    }

    public void setShopname(String shopname) {
        this.shopname = shopname;
    }

    public String getSale_type() {
        return sale_type;
    }

    public void setSale_type(String sale_type) {
        this.sale_type = sale_type;
    }

    public String getPrimary_sale() {
        return primary_sale;
    }

    public void setPrimary_sale(String primary_sale) {
        this.primary_sale = primary_sale;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }
}

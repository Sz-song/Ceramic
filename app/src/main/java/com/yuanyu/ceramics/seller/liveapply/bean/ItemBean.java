package com.yuanyu.ceramics.seller.liveapply.bean;

import java.io.Serializable;

public class ItemBean implements Serializable {
    private String id;
    private String image;
    private String item;
    private String price;
    private boolean isChecked = false;

    public ItemBean(String id, String image, String item, String price, boolean isChecked) {
        this.id = id;
        this.image = image;
        this.item = item;
        this.price = price;
        this.isChecked = isChecked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }


    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}

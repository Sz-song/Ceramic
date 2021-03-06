package com.yuanyu.ceramics.fenlei;

public class FenLeiResBean {
    private String id;
    private String name;
    private String location;
    private String price;
    private String image;
    private String shop;
    public FenLeiResBean(String id, String name, String location, String price, String image, String shop) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.price = price;
        this.image = image;
        this.shop = shop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }
}

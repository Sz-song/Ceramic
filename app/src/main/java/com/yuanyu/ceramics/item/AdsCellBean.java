package com.yuanyu.ceramics.item;

public class AdsCellBean {
    private String id;
    private String image;
    private String name;
    private Double price;
    private String location;
    private String shop;
    private String shopid;

    public AdsCellBean(String id, String image, String name, Double price, String location, String shop, String shopid) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.location = location;
        this.shop = shop;
        this.shopid = shopid;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }
}

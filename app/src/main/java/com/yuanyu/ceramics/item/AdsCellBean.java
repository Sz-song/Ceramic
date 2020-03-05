package com.yuanyu.ceramics.item;

public class AdsCellBean {
    private String id;
    private String image;
    private String name;
    private Double price;
    private String location;
    private String shop;
    private String shopid;

    public AdsCellBean(String image, String name, Double price, String location){
        this.image = image;
        this.name = name;
        this.price = price;
        this.location = location;
    }
    public AdsCellBean(String image, String name, String shop, Double price){
        this.image = image;
        this.name = name;
        this.shop = shop;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public String getShopid() {
        return shopid;
    }
}

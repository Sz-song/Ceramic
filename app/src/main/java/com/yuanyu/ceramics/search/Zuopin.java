package com.yuanyu.ceramics.search;

public class Zuopin {
    private String id;
    private String image;
    private String name;
    private String location;
    private float price;
    private String shop;



    public Zuopin(String id, String image, String name, String location, float price, String shop) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.location = location;
        this.price = price;
        this.shop = shop;
    }
    public String getLocation() {
        return location;
    }

    public float getPrice() {
        return price;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPrice(float price) {
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



}

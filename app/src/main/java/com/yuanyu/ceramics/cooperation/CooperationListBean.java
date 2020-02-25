package com.yuanyu.ceramics.cooperation;

public class CooperationListBean {
    public CooperationListBean(String id, String image, String name, String introduce, String url) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.introduce = introduce;
        this.url = url;
    }

    private String id;
    private String image;
    private String name;
    private String introduce;
    private String url;

    public String getId() { return id;}

    public String getImage() {return image; }

    public String getName() { return name; }

    public String getUrl() { return url; }

    public String getIntroduce() {
        return introduce;
    }
}

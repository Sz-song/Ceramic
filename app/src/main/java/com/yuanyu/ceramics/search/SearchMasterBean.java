package com.yuanyu.ceramics.search;

public class SearchMasterBean {
    private String portrait;
    private String name;
    private String title;
    private String fensi;
    private String guanzhu;
    private String dongtai;
    private int isFollowed;
    private String id;

    public SearchMasterBean(String portrait, String name, String title, String fensi, String guanzhu, String dongtai, int isFollowed) {
        this.portrait = portrait;
        this.name = name;
        this.title = title;
        this.fensi = fensi;
        this.guanzhu = guanzhu;
        this.dongtai = dongtai;
        this.isFollowed = isFollowed;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public int getIsFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(int isFollowed) {
        this.isFollowed = isFollowed;
    }

    public String getName() {
        return name;
    }


    public String getFensi() {
        return fensi;
    }

    public String getGuanzhu() {
        return guanzhu;
    }

    public String getDongtai() {
        return dongtai;
    }


    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFensi(String fensi) {
        this.fensi = fensi;
    }

    public void setGuanzhu(String guanzhu) {
        this.guanzhu = guanzhu;
    }

    public void setDongtai(String dongtai) {
        this.dongtai = dongtai;
    }

    public int isFollowed() {
        return isFollowed;
    }

    public void setFollowed(int followed) {
        isFollowed = followed;
    }

}

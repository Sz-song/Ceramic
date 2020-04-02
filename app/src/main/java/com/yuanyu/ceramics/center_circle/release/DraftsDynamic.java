package com.yuanyu.ceramics.center_circle.release;

import com.yuanyu.ceramics.common.DynamicContentBean;
import com.yuanyu.ceramics.common.FriendBean;

import java.util.ArrayList;
import java.util.List;

public class DraftsDynamic {
    private List<DynamicContentBean> listcontent;
    private String inputStr;
    private boolean isopen;
    private ArrayList<String> listimages;
    private List<FriendBean> listfriends;
    private int id;

    public DraftsDynamic(List<DynamicContentBean> dynamicContentList, String inputStr, boolean isopen, ArrayList<String> listimages, List<FriendBean> listfriends, int id) {
        this.listcontent   = dynamicContentList;
        this.inputStr = inputStr;
        this.isopen = isopen;
        this.listimages = listimages;
        this.listfriends = listfriends;
        this.id = id;
    }

    public List<DynamicContentBean> listcontent() {
        return listcontent;
    }

    public void listcontent(List<DynamicContentBean> dynamicContentList) {
        this.listcontent = dynamicContentList;
    }

    public String getInputStr() {
        return inputStr;
    }

    public void setInputStr(String inputStr) {
        this.inputStr = inputStr;
    }

    public boolean isIsopen() {
        return isopen;
    }

    public void setIsopen(boolean isopen) {
        this.isopen = isopen;
    }

    public ArrayList<String> getListimages() {
        return listimages;
    }

    public void setListimages(ArrayList<String> listimages) {
        this.listimages = listimages;
    }

    public List<FriendBean> getListfriends() {
        return listfriends;
    }

    public void setListfriends(List<FriendBean> listfriends) {
        this.listfriends = listfriends;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

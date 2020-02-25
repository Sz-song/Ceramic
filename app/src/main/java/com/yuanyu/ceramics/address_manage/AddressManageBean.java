package com.yuanyu.ceramics.address_manage;

import java.io.Serializable;

public class AddressManageBean implements Serializable {

    private String addressid;
    private String name;
    private String phone;
    private String province;
    private String city;
    private String exparea;
    private String address;
    private int isdefault;

    public AddressManageBean(String name, String phone, String province, String city, String exparea, String address) {
        this.name = name;
        this.phone = phone;
        this.province = province;
        this.city = city;
        this.exparea = exparea;
        this.address = address;
    }

    public void setIsdefault(int isdefault) {
        this.isdefault = isdefault;
    }

    public String getAddressid() {
        return addressid;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getExparea() {
        return exparea;
    }

    public String getAddress() {
        return address;
    }

    public int getIsdefault() {
        return isdefault;
    }
}

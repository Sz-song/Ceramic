package com.yuanyu.ceramics.myinfo;

import java.io.Serializable;

public class MyInfoBean  implements Serializable {
    private String name;
    private String introduce;
    private String gender;
    private String birthday;
    private String email;
    private String location;

    public MyInfoBean(String name, String introduce, String gender, String birthday, String email, String location) {
        this.name = name;
        this.introduce = introduce;
        this.gender = gender;
        this.birthday = birthday;
        this.email = email;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

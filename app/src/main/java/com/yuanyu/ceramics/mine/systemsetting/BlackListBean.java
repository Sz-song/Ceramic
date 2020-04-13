package com.yuanyu.ceramics.mine.systemsetting;

import java.util.Objects;

import androidx.annotation.Nullable;

public class BlackListBean {
    private String portrait;
    private String blackuserid;
    private String name;

    public BlackListBean(String portrait, String blackuserid, String name) {
        this.portrait = portrait;
        this.blackuserid = blackuserid;
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public String getBlackuserid() {
        return blackuserid;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        BlackListBean bean = (BlackListBean) obj;
        return Objects.equals(name, bean.name) && Objects.equals(portrait, bean.portrait)&&Objects.equals(blackuserid, bean.blackuserid);

    }
}

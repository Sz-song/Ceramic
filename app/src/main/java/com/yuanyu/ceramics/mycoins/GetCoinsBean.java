package com.yuanyu.ceramics.mycoins;

public class GetCoinsBean {
    private String shelves_coins_num;//每天上架3件商品获取金豆数量
    private String trading_coins_num;//每天成交获取金豆数量(销售额>500)
    private String share_coins_num;//每天分享店铺获取金豆
    private String invite_coins_num;//邀请新用户获取金豆
    private String enter_coins_num;//商家再次入驻获取金豆
    private String share_num;//分享的次数

    public String getShelves_coins_num() {
        return shelves_coins_num;
    }

    public String getTrading_coins_num() {
        return trading_coins_num;
    }

    public String getShare_coins_num() {
        return share_coins_num;
    }

    public String getInvite_coins_num() {
        return invite_coins_num;
    }

    public String getEnter_coins_num() {
        return enter_coins_num;
    }

    public String getShare_num() {
        return share_num;
    }
}

package com.yuanyu.ceramics.mycoins;

import java.util.List;

public class CoinsBean {
    private String coins;
    private List<ExchangeCoinsBean> exchange;
    private List<ExchangeCoinsBean> GetCoinsBean;

    public String getCoins() {
        return coins;
    }

    public List<ExchangeCoinsBean> getExchange() {
        return exchange;
    }

    public List<ExchangeCoinsBean> getGetCoinsBean() {
        return GetCoinsBean;
    }
}

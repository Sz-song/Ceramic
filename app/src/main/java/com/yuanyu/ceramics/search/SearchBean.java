package com.yuanyu.ceramics.search;

import java.util.List;

/**
 * Created by cat on 2018/7/25.
 */

public class SearchBean {
    private List<Zuopin> AdsCellBean;
    private List<SearchMasterBean> GuanzhuDashiBean;
    private List<Shop> SearchShopBean;

    public List<Shop> getSearchShopBean() {
        return SearchShopBean;
    }
    public List<Zuopin> getAdsCellBean() {
        return AdsCellBean;
    }
    public List<SearchMasterBean> getGuanzhuDashiBean() {
        return GuanzhuDashiBean;
    }
}

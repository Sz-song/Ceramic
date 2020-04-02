package com.yuanyu.ceramics.db;

import org.litepal.crud.LitePalSupport;

public class SearchHistoryBean extends LitePalSupport {
    private String history;
    private String type;//0首页搜索，1 区块链搜索

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

package com.yuanyu.ceramics.center_circle.detail;

/**
 * Created by Administrator on 2018-08-14.
 */

public class ArticleContentBean {
    private int  type;//0 文字  1图片 (0 文字，1 @某人)
    private String content;

    public ArticleContentBean(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

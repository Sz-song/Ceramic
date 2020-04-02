package com.yuanyu.ceramics.center_circle.release;

import com.yuanyu.ceramics.center_circle.detail.ArticleContentBean;

import java.util.List;

public class DraftsArticle {
    private String cover;
    private String title;
    private List<ArticleContentBean> content;
    private String id;

    public DraftsArticle(String cover, String title, List<ArticleContentBean> content, String id) {
        this.cover = cover;
        this.title = title;
        this.content = content;
        this.id = id;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ArticleContentBean> getContent() {
        return content;
    }

    public void setContent(List<ArticleContentBean> content) {
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

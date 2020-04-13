package com.yuanyu.ceramics.seller.evaluationmanage;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018-08-22.

 */

public class EvaluationManageBean implements Serializable {
    private String id;/**评论id**/
    private int userid;/**评论人id**/
    private String image;/**评论者头像**/
    private String name;/**评论者name**/
    private String time;/**评论时间**/
    private String productname;/**购买商品名**/
    private String evaleation;/**评论详情**/
    private String reply_txt; /**商家回复**/
    private String evaleation2;/**追评详情**/
    private String reply_txt2;/**商家回复追评**/
    private List<String> pic_list;/**pinglun图片**/

    public EvaluationManageBean(String id, int userid, String image, String name, String time, String productname, String evaleation, String evaleation2, String reply_txt, String reply_txt2, List<String> pic_list) {
        this.id = id;
        this.userid = userid;
        this.image = image;
        this.name = name;
        this.time = time;
        this.productname = productname;
        this.evaleation = evaleation;
        this.evaleation2 = evaleation2;
        this.reply_txt = reply_txt;
        this.reply_txt2 = reply_txt2;
        this.pic_list = pic_list;
    }

    public String getEvaleation2() {
        return evaleation2;
    }

    public void setEvaleation2(String evaleation2) {
        this.evaleation2 = evaleation2;
    }

    public String getReply_txt2() {
        return reply_txt2;
    }

    public void setReply_txt2(String reply_txt2) {
        this.reply_txt2 = reply_txt2;
    }

    /**评论图片集最多三张（0-3）**/



    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public String getEvaleation() {
        return evaleation;
    }

    public void setEvaleation(String evaleation) {
        this.evaleation = evaleation;
    }

    public List<String> getPic_list() {
        return pic_list;
    }

    public void setPic_list(List<String> pic_list) {
        this.pic_list = pic_list;
    }

    public String getReply_txt() {
        return reply_txt;
    }

    public void setReply_txt(String reply_txt) {
        this.reply_txt = reply_txt;
    }
}

package com.yuanyu.ceramics.seller.shop_shelve;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cat on 2018/8/30.
 */

public class ShelvingDetailBean {

    private String box_no;//盒子编号
    private String location;//位置
    private String title;//标题
    private String description; // 商品属性  详细介绍等
    private List<String> images;   //用于商品首页展示的图片list
    private String video;//视频
    private String video_cover;//视频封面
    private String serial_no; //商品编号
    private String artisan ;    //匠人
    private String price;//价格
    private String amount;//库存
    private String fenlei;//分类
    private String zhonglei;//种类
    private String ticai;//题材
    private String pise;//皮色
    private String chanzhuang;//产状
    private int length;//长
    private int width;//宽
    private int height;//高
    private double weight;//重量
    private int freight;//邮费
    private String shopid;
    private String remark;
    private int storage_status;//库存状态（0 仓库中 1借出 2已出售）
    private int shelves_status;//上架状态（0未上架 1已上架）
    private String id;//商品ID号
    private String dd_id;
    private String time;//入库时间
    private String loantype; // 借出状态  0现场借出 1快递借出
    private String loanid; // 借出Id
    private String dd_price;

    public ShelvingDetailBean() {
        images=new ArrayList<>();
    }

    public void setNoData(){
       setTitle("");
       setDescription("");
       setArtisan("");
       setPrice("");
       setAmount("");
       setDd_price("");
       setFenlei("");
       setZhonglei("");
       setChanzhuang("");
       setVideo_cover("");
       setPise("");
       setTicai("");
       setWeight(0);
       setWidth(0);
       setLength(0);
       setHeight(0);
       setSerial_no("");
       setId("");
       setDd_id("");
       images.clear();
    }

    public String getDd_id() {
        return dd_id;
    }

    public void setDd_id(String dd_id) {
        this.dd_id = dd_id;
    }

    public String getBox_no() {
        return box_no;
    }

    public void setBox_no(String box_no) {
        this.box_no = box_no;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVideo_cover() {
        return video_cover;
    }

    public void setVideo_cover(String video_cover) {
        this.video_cover = video_cover;
    }

    public String getSerial_no() {
        return serial_no;
    }

    public void setSerial_no(String serial_no) {
        this.serial_no = serial_no;
    }

    public String getArtisan() {
        return artisan;
    }

    public void setArtisan(String artisan) {
        this.artisan = artisan;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFenlei() {
        return fenlei;
    }

    public void setFenlei(String fenlei) {
        this.fenlei = fenlei;
    }

    public String getZhonglei() {
        return zhonglei;
    }

    public void setZhonglei(String zhonglei) {
        this.zhonglei = zhonglei;
    }

    public String getTicai() {
        return ticai;
    }

    public void setTicai(String ticai) {
        this.ticai = ticai;
    }

    public String getPise() {
        return pise;
    }

    public void setPise(String pise) {
        this.pise = pise;
    }

    public String getChanzhuang() {
        return chanzhuang;
    }

    public void setChanzhuang(String chanzhuang) {
        this.chanzhuang = chanzhuang;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public int getFreight() {
        return freight;
    }

    public void setFreight(int freight) {
        this.freight = freight;
    }

    public String getShopid() {
        return shopid;
    }

    public void setShopid(String shopid) {
        this.shopid = shopid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getStorage_status() {
        return storage_status;
    }

    public void setStorage_status(int storage_status) {
        this.storage_status = storage_status;
    }

    public int getShelves_status() {
        return shelves_status;
    }

    public void setShelves_status(int shelves_status) {
        this.shelves_status = shelves_status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getLoantype() {
        return loantype;
    }

    public void setLoantype(String loantype) {
        this.loantype = loantype;
    }

    public String getLoanid() {
        return loanid;
    }

    public void setLoanid(String loanid) {
        this.loanid = loanid;
    }

    public String getDd_price() {
        return dd_price;
    }

    public void setDd_price(String dd_price) {
        this.dd_price = dd_price;
    }
}

package com.yuanyu.ceramics.global;


import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.bazaar.MasterWorkBean;
import com.yuanyu.ceramics.bazaar.StoreCenterBean;
import com.yuanyu.ceramics.broadcast.BroadcastBean;
import com.yuanyu.ceramics.cart.GoodsBean;
import com.yuanyu.ceramics.common.DynamicBean;
import com.yuanyu.ceramics.common.FriendBean;
import com.yuanyu.ceramics.common.ResultBean;
import com.yuanyu.ceramics.common.VideoBean;
import com.yuanyu.ceramics.dingzhi.DashiCellBean;
import com.yuanyu.ceramics.dingzhi.DingzhiDetailBean;
import com.yuanyu.ceramics.dingzhi.GenerateOrdersBean;
import com.yuanyu.ceramics.dingzhi.MyDingzhiBean;
import com.yuanyu.ceramics.home.homepage.HomepageBean;
import com.yuanyu.ceramics.item.AdsCellBean;
import com.yuanyu.ceramics.item.ItemDetailBean;
import com.yuanyu.ceramics.login.LoginBean;
import com.yuanyu.ceramics.logistics.LogisticsBean;
import com.yuanyu.ceramics.master.MasterItemBean;
import com.yuanyu.ceramics.meet_master.MeetMasterBean;
import com.yuanyu.ceramics.message.MessageBean;
import com.yuanyu.ceramics.mine.MineBean;
import com.yuanyu.ceramics.mine.systemsetting.BlackListBean;
import com.yuanyu.ceramics.order.MyOrderFragmentBean;
import com.yuanyu.ceramics.order.OrderDetailBean;
import com.yuanyu.ceramics.order.refund.RefundDetailBean;
import com.yuanyu.ceramics.order.refund.RefundListBean;
import com.yuanyu.ceramics.personal_index.PersonalIndexBean;
import com.yuanyu.ceramics.personal_index.fans_focus.FocusAndFansBean;
import com.yuanyu.ceramics.seller.delivery.CourierBean;
import com.yuanyu.ceramics.seller.delivery.DeliveryBean;
import com.yuanyu.ceramics.seller.delivery.WaitDeliveryBean;
import com.yuanyu.ceramics.seller.order.ShopOrderBean;
import com.yuanyu.ceramics.seller.order.detail.ShopOrderDetailBean;
import com.yuanyu.ceramics.seller.shop_shelve.ShelvingDetailBean;
import com.yuanyu.ceramics.seller.shop_shelve.shelve_audit.ShelveAuditBean;
import com.yuanyu.ceramics.shop_index.ShopGoodsBean;
import com.yuanyu.ceramics.shop_index.ShopIndexBean;
import com.yuanyu.ceramics.shop_index.ShopPinglunBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;

public interface HttpService {
    //用户登录
    @POST("app_api/login.php")
    Observable<BaseResponse<LoginBean>> login(@Body RequestBody body);
    //第三方登陆
    @POST("app_api/third_login.php")
    Observable<BaseResponse<LoginBean>> thirdLogin(@Body RequestBody body);
    //首页初始化
    @POST("app_api/ceramics/homepage.php")
    Observable<BaseResponse<HomepageBean>> homepage(@Body RequestBody body);
    //直播列表
    @POST("app_api/ceramics/broadcast_list.php")
    Observable<BaseResponse<List<BroadcastBean>>> broadcastlist(@Body RequestBody body);
    //直播列表
    @POST("app_api/ceramics/message_list.php")
    Observable<BaseResponse<List<MessageBean>>> messagelist(@Body RequestBody body);
    //吾家初始化
    @POST("app_api/ceramics/mine_init.php")
    Observable<BaseResponse<MineBean>> mineinit(@Body RequestBody body);
    //大师列表
    @POST("app_api/ceramics/master_list.php")
    Observable<BaseResponse<List<MasterItemBean>>> master_list(@Body RequestBody body);
    //购物车
    @POST("app_api/ceramics/shoppingcart.php")
    Observable<BaseResponse<List<GoodsBean>>> getGoods(@Body RequestBody body);
    //删除购物车
    @POST("app_api/home_page/cart_delete.php")
    Observable<BaseResponse<String[]>> deleteCart(@Body RequestBody body);
    //微博登陆验证
    @GET("users/show.json")
    Call<ResponseBody> getWeiboData(@QueryMap Map<String,String> params);
    //获取集市大师作品
    @POST("app_api/ceramics/master_works.php")
    Observable<BaseResponse<List<MasterWorkBean>>> getMasterWorkList(@Body RequestBody body);
    //获取集市商铺
    @POST("app_api/ceramics/store_center.php")
    Observable<BaseResponse<List<StoreCenterBean>>> getStoreCenterList(@Body RequestBody body);
    @POST("app_api/grounding/generate_bond_orders.php")
    Observable<BaseResponse<GenerateOrdersBean>> generateUserBondOrder(@Body RequestBody body);
    //店铺定制列表
    @POST("app_api/shangjia/seller_getdingzhi.php")
    Observable<BaseResponse<List<MyDingzhiBean>>> getShopDingzhiList(@Body RequestBody body);
    //定制详情
    @POST("app_api/home_page/dingzhi.php")
    Observable<BaseResponse<DingzhiDetailBean>> dingzhiDetail(@Body RequestBody body);
    //生成定制保证金(尾款)订单
    @POST("app_api/home_page/generate_dingzhi_orders.php")
    Observable<BaseResponse<String>> generateDingzhiBondOrder(@Body RequestBody body);
    //用户保证金支付
    @POST("app_api/home_page/dingzhi_bondpay.php")
    Observable<BaseResponse<Boolean>> dingzhiBondPay(@Body RequestBody body);
    //商家接受定制订单
    @POST("app_api/home_page/confirm_order.php")
    Observable<BaseResponse<Boolean>> confirmDingzhiOrder(@Body  RequestBody body);
    //商家接受定制订单
    @POST("app_api/home_page/cancel_dingzhi_order.php")
    Observable<BaseResponse<Boolean>> cancelDingzhiOrder(@Body  RequestBody body);
    //定制发货
    @POST("app_api/home_page/dingzhi_courier.php")
    Observable<BaseResponse<Boolean>> dingzhiCourier(@Body  RequestBody body);
    //定制确认收货
    @POST("app_api/home_page/dingzhi_receive.php")
    Observable<BaseResponse<Boolean>> dingzhiConfirmReceipt(@Body RequestBody body);
    //发布定制需求
    @POST("app_api/home_page/dingzhi.php")
    Observable<BaseResponse<String[]>> publishDingzhi(@Body RequestBody body);
    //获取我的定制
    @POST("app_api/home_page/dingzhi.php")
    Observable<BaseResponse<List<MyDingzhiBean>>> getMyDingzhi(@Body RequestBody body);
    //获取遇见大师
    @POST("app_api/master/master_studio_list.php")
    Observable<BaseResponse<List<MeetMasterBean>>> getMasterStudio(@Body RequestBody body);
    //获取指定大师
    @POST("app_api/home_page/dingzhi.php")
    Observable<BaseResponse<List<DashiCellBean>>> ChooseDashi(@Body RequestBody body);
    //个人主页初始化
    @POST("app_api/yuba/personal_index.php")
    Observable<BaseResponse<PersonalIndexBean>> PersonalIndex(@Body RequestBody body);
    //关注与取关
    @POST("app_api/yuba/focusandchancel_new.php")
    Observable<BaseResponse<Boolean>>Focus(@Body RequestBody body);
    //添加黑名单
    @POST("app_api/home_page/add_blacklist.php")
    Observable<BaseResponse<Boolean>>addBlacklist(@Body RequestBody body);
    //个人动态文章
    @POST("app_api/yuba/personal_dynamic_article.php")
    Observable<BaseResponse<List<DynamicBean>>> getPersonalDynamicArticle(@Body RequestBody body);
    //点赞
    @POST("app_api/yuba/dianzan.php")
    Observable<BaseResponse<String []>>dianZan(@Body RequestBody body);

    //发布动态
    @POST("app_api/yuba/releasedynamic.php")
    Observable<BaseResponse<Boolean>>ReleaseDynamic(@Body RequestBody body);
    //获取好友列表
    @POST("app_api/grounding/getfriends.php")
    Observable<BaseResponse<List<FriendBean>>>getFriends(@Body RequestBody body);
    //发布文章
    @POST("app_api/yuba/releasearticle.php")
    Observable<BaseResponse<Boolean>>ReleaseArticle(@Body RequestBody body);

    //评论
    @POST("app_api/yuba/releasepinglun.php")
    Observable<BaseResponse<String>>pingLun(@Body RequestBody body);
    //删除动态
    @POST("app_api/yuba/delete_dynamic.php")
    Observable<BaseResponse<String[]>> DeleteDynamic(@Body RequestBody body);
    //删除文章
    @POST("app_api/yuba/delete_article.php")
    Observable<BaseResponse<String[]>> DeleteArticle(@Body RequestBody body);
    //屏蔽
    @POST("app_api/yuba/shield.php")
    Observable<BaseResponse<String[]>> shield(@Body RequestBody body);
    //店铺动态
    @POST("app_api/commodity/shop_dynamic.php")
    Observable<BaseResponse<List<DynamicBean>>> getShopDynamic(@Body RequestBody body);
    //店铺内分类搜索
    @POST("app_api/home_page/shop_fenlei.php")
    Observable<BaseResponse<List<ResultBean>>> getShopFenleiData(@Body RequestBody body);
    //用户访问店铺首页
    @POST("app_api/shangjia/shophead.php")
    Observable<BaseResponse<ShopIndexBean>>getUserShopIndexResult(@Body RequestBody body);
    //收藏商品collectItem
    @POST("app_api/shangjia/add_collection.php")
    Observable<BaseResponse<Boolean>> collectItem(@Body RequestBody body);
    //分享店铺
    @POST("app_api/wujia/share_shop.php")
    Observable<BaseResponse<Boolean>> shareShop(@Body RequestBody body);
    //店铺首页 店铺评论
    @POST("app_api/commodity/shop_pinglun.php")
    Observable<BaseResponse<ShopPinglunBean>> getShopPinglun(@Body RequestBody body);
    //店铺首页 新品和全部1商品
    @POST("app_api/shangjia/shopcommodity.php")
    Observable<BaseResponse<List<ShopGoodsBean>>> getShopCommodity(@Body RequestBody body);
    //广场动态和文章
    @POST("app_api/yuba/square_dynamic_article.php")
    Observable<BaseResponse<List<DynamicBean>>> getSquareDynamicArticle(@Body RequestBody body);
    //订单详情
    @POST("app_api/wujia/ordermoremsg.php")
    Observable<BaseResponse<OrderDetailBean>>getOrderDetailData(@Body RequestBody body);
    //取消订单
    @POST("app_api/home_page/cancelorder.php")
    Observable<BaseResponse<String[]>>cancelOrder(@Body RequestBody body);
    //删除订单
    @POST("app_api/home_page/deleteorder.php")
    Observable<BaseResponse<String[]>>deleteOrder(@Body RequestBody body);
    //确认收货
    @POST("app_api/home_page/receivedelivery.php")
    Observable<BaseResponse<String[]>>confirmReceived(@Body RequestBody body);
//    我的订单
    @POST("app_api/wujia/waitpay.php")
    Observable<BaseResponse<List<MyOrderFragmentBean>>>getOrderList(@Body RequestBody body);
    //提醒发货
    @POST("app_api/wujia/reminddelivery.php")
    Observable<BaseResponse<String[]>>remindDelivery(@Body RequestBody body);
    //上传图片
    @POST("app_api/upload/upload.php")
    @Multipart
    Observable<BaseResponse<List<String>>> uploadImage(@Part("data")RequestBody body, @Part MultipartBody.Part[] part);
    //申请退款
    @POST("app_api/wujia/submitrefund.php")
    Observable<BaseResponse> submitRefund(@Body RequestBody body);
    //吾家退款订单
    @POST("app_api/wujia/refundwujia.php")
    Observable<BaseResponse<List<RefundListBean>>>getWujiaRefundData(@Body RequestBody body);
    //退款详情
    @POST("app_api/wujia/refundwujiamoremsg.php")
    Observable<BaseResponse<RefundDetailBean>>refundDetail(@Body RequestBody body);
    //取消退款/售后申请
    @POST("app_api/wujia/cancel_refund.php")
    Observable<BaseResponse<String[]>> CancelRefund(@Body RequestBody body);
    //退货退款填写退款物流单号
    @POST("app_api/wujia/add_refund_logistics.php")
    Observable<BaseResponse<String[]>> InputLogistics(@Body RequestBody body);
    //关注和粉丝列表
    @POST("app_api/wujia/add_refund_logistics.php")
    Observable<BaseResponse<List<FocusAndFansBean>>> getFocusandFansList(@Body RequestBody body);
    //申请入驻
    @POST("app_api/home_page/applyenter.php")
    Observable<BaseResponse<String[]>> ApplyEnter(@Body RequestBody body);
    //移除黑名单
    @POST("app_api/home_page/remove_blacklist.php")
    Observable<BaseResponse>removeBlacklist(@Body RequestBody body);
    //黑名单初始化
    @POST("app_api/home_page/blacklist.php")
    Observable<BaseResponse<List<BlackListBean>>>getBlacklist(@Body RequestBody body);
    //获取版本信息
    @POST("app_api/home_page/get_version.php")
    Observable<BaseResponse<Integer>> getVersion(@Body RequestBody body);
    //获取验证码
    @POST("back/test/api/app_login/login_module.php")
    Observable<BaseResponse<String[]>> getValidCode(@Body RequestBody body);
    //重置密码
    @POST("app_api/app_login/login_module.php")
    Observable<BaseResponse<String[]>> resetPwd(@Body RequestBody body);
    //用户反馈
    @POST("app_api/home_page/usersopinion.php")
    Observable<BaseResponse<String[]>> usersOpinion(@Body RequestBody body);

    //商品详情页itemDetail
    @POST("app_api/commodity/item_detail.php")
    Observable<BaseResponse<ItemDetailBean>> itemDetail(@Body RequestBody body);
    //添加购物车
    @POST("app_api/home_page/cart_add.php")
    Observable<BaseResponse<String[]>> addCart(@Body RequestBody body);
    //加载更多广告
    @POST("app_api/home_page/loadmoreads.php")
    Observable<BaseResponse<List<AdsCellBean>>> loadMoreAds(@Body RequestBody body);
    //    商家管理获取订单管理getOrdersManage
    @POST("app_api/home_page/loadmoreads.php")
    Observable<BaseResponse<List<ShopOrderBean>>> getOrdersManage(@Body RequestBody body);
    //    shopGetOrderDetail
    @POST("app_api/home_page/loadmoreads.php")
    Observable<BaseResponse<ShopOrderDetailBean>> shopGetOrderDetail(@Body RequestBody body);
//    getLogisticsTracing
    //快递递踪
    @POST("")
    Observable<BaseResponse<LogisticsBean>> getLogisticsTracing(@Body RequestBody body);
//    modityOrderPrice商家修改未支付订单价格
    @POST("")
    Observable<BaseResponse<Boolean>> modityOrderPrice(@Body RequestBody body);
//    修改商家
    @POST("123")
    Observable<BaseResponse<Boolean>> ShopChangeIntroduce(@Body RequestBody body);

    //商品上架接口
    @POST("")
    Observable<BaseResponse<String[]>>Shelve(@Body RequestBody body);
    //上传视频
    @POST("")
    @Multipart
    Observable<BaseResponse<VideoBean>> uploadVideo(@Part("data")RequestBody body,
                                                    @Part MultipartBody.Part[] part);
    //商品上架审核状态接口
    @POST("")
    Observable<BaseResponse<List<ShelveAuditBean>>>getWaitReviewResult(@Body RequestBody body);
    //删除仓库物品
    @POST("")
    Observable<BaseResponse<String[]>>getWareHouseDeleteResult(@Body RequestBody body);
    //我的商品 重新上架
    @POST("")
    Observable<BaseResponse<ShelvingDetailBean>>getReOnSaleData(@Body RequestBody body);
    //我的商品 下架接口
    @POST("")
    Observable<BaseResponse<String[]>>shopGoodsOffShelves(@Body RequestBody body);
    //我的商品接口
    @POST("")
    Observable<BaseResponse<List<ShopGoodsBean>>>getShopGoodsList(@Body RequestBody body);
    //商家获取商品详情
    @POST("")
    Observable<BaseResponse<ShelvingDetailBean>> getShopGoodsDetail(@Body RequestBody body);


    //查看快递类型
    @POST("app_api/shangjia/ExpRecommend.php")
    Observable<BaseResponse<List<CourierBean>>> getCourierData(@Body RequestBody body);
    //获取所有快递
    @POST("app_api/shangjia/expresscompany.php")
    Observable<BaseResponse<List<CourierBean>>> getCourierCompany(@Body RequestBody body);
    //商家发货
    @POST("app_api/shangjia/shopdelivery.php")
    Observable<BaseResponse<String>>Delivery(@Body RequestBody body);
    //商家发货获取收货人信息
    @POST("app_api/shangjia/getdeliverymsg.php")
    Observable<BaseResponse<DeliveryBean>>getDeliveryData(@Body RequestBody body);
    //商家获取待发货数据
    @POST("app_api/shangjia/getnodelivery.php")
    Observable<BaseResponse<List<WaitDeliveryBean>>>getWaitDeliveryData(@Body RequestBody body);
    //获取分类
    @POST("app_api/home_page/getfenlei.php")
    Observable<BaseResponse<List<ResultBean>>>getFenleiResult(@Body RequestBody body);
}

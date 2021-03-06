package com.yuanyu.ceramics.global;


import com.yuanyu.ceramics.address_manage.AddressManageBean;
import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.bazaar.MasterWorkBean;
import com.yuanyu.ceramics.bazaar.StoreCenterBean;
import com.yuanyu.ceramics.broadcast.BroadcastBean;
import com.yuanyu.ceramics.broadcast.pull.LivePullBean;
import com.yuanyu.ceramics.broadcast.push.LivePushBean;
import com.yuanyu.ceramics.cart.GoodsBean;
import com.yuanyu.ceramics.center_circle.release.DraftsArticle;
import com.yuanyu.ceramics.center_circle.release.DraftsBean;
import com.yuanyu.ceramics.center_circle.release.DraftsDynamic;
import com.yuanyu.ceramics.chat.ChatBean;
import com.yuanyu.ceramics.common.DynamicBean;
import com.yuanyu.ceramics.common.FenleiBean;
import com.yuanyu.ceramics.common.FriendBean;
import com.yuanyu.ceramics.common.ResultBean;
import com.yuanyu.ceramics.common.VideoBean;
import com.yuanyu.ceramics.cooperation.CooperationListBean;
import com.yuanyu.ceramics.dingzhi.DashiCellBean;
import com.yuanyu.ceramics.dingzhi.DingzhiDetailBean;
import com.yuanyu.ceramics.dingzhi.GenerateOrdersBean;
import com.yuanyu.ceramics.dingzhi.MyDingzhiBean;
import com.yuanyu.ceramics.fenlei.FenLeiResBean;
import com.yuanyu.ceramics.home.homepage.FaxianBean;
import com.yuanyu.ceramics.item.AdsCellBean;
import com.yuanyu.ceramics.item.ItemDetailBean;
import com.yuanyu.ceramics.login.LoginBean;
import com.yuanyu.ceramics.login.TokenBean;
import com.yuanyu.ceramics.logistics.LogisticsBean;
import com.yuanyu.ceramics.master.MasterItemBean;
import com.yuanyu.ceramics.meet_master.MeetMasterBean;
import com.yuanyu.ceramics.message.MessageBean;
import com.yuanyu.ceramics.mine.MineBean;
import com.yuanyu.ceramics.mine.my_collect.MyCollectBean;
import com.yuanyu.ceramics.mine.systemsetting.BlackListBean;
import com.yuanyu.ceramics.mycoins.ExchangeCoinsBean;
import com.yuanyu.ceramics.mycoins.GetCoinsBean;
import com.yuanyu.ceramics.mycoins.MyCoinsDetailBean;
import com.yuanyu.ceramics.myinfo.MyInfoBean;
import com.yuanyu.ceramics.order.MyOrderFragmentBean;
import com.yuanyu.ceramics.order.OrderDetailBean;
import com.yuanyu.ceramics.order.refund.RefundDetailBean;
import com.yuanyu.ceramics.order.refund.RefundListBean;
import com.yuanyu.ceramics.personal_index.PersonalIndexBean;
import com.yuanyu.ceramics.personal_index.fans_focus.FocusAndFansBean;
import com.yuanyu.ceramics.search.SearchItemBean;
import com.yuanyu.ceramics.search.SearchMasterBean;
import com.yuanyu.ceramics.search.SearchShopBean;
import com.yuanyu.ceramics.seller.delivery.CourierBean;
import com.yuanyu.ceramics.seller.delivery.DeliveryBean;
import com.yuanyu.ceramics.seller.delivery.WaitDeliveryBean;
import com.yuanyu.ceramics.seller.evaluationmanage.EvaluationManageBean;
import com.yuanyu.ceramics.seller.index.SellerIndexBean;
import com.yuanyu.ceramics.seller.liveapply.LiveApplyStatusBean;
import com.yuanyu.ceramics.seller.liveapply.bean.SelectItemBean;
import com.yuanyu.ceramics.seller.order.ShopOrderBean;
import com.yuanyu.ceramics.seller.order.detail.ShopOrderDetailBean;
import com.yuanyu.ceramics.seller.refund.RefundBean;
import com.yuanyu.ceramics.seller.shop_shelve.ShelvingDetailBean;
import com.yuanyu.ceramics.seller.shop_shelve.shelve_audit.ShelveAuditBean;
import com.yuanyu.ceramics.shop_index.ShopGoodsBean;
import com.yuanyu.ceramics.shop_index.ShopIndexBean;
import com.yuanyu.ceramics.shop_index.ShopPinglunBean;
import com.yuanyu.ceramics.wxapi.WXBean;

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
    //注册
    @POST("back/test/api/app_login/login_module.php")
    Observable<BaseResponse<String[]>> register(@Body RequestBody body);
//    //询问昵称是否占用
//    @POST("app_api/wujia/view_username.php")
//    Observable<BaseResponse<Boolean>>viewUsername(@Body RequestBody body);
    //用户登录
    @POST("back/test/api/app_login/login_module.php")
    Observable<BaseResponse<LoginBean>> login(@Body RequestBody body);
    //第三方登陆
    @POST("app_api/third_login.php")
    Observable<BaseResponse<LoginBean>> thirdLogin(@Body RequestBody body);
    //首页初始化
    @POST("back/test/api/homepage/faxian.php")
    Observable<BaseResponse<FaxianBean>> homepage(@Body RequestBody body);
    //直播列表
    @POST("back/test/api/broadcast/broadcast_list.php")
    Observable<BaseResponse<List<BroadcastBean>>> broadcastlist(@Body RequestBody body);
    //直播列表
    @POST("back/test/api/homepage/get_userinfo.php")
    Observable<BaseResponse<List<MessageBean>>> messagelist(@Body RequestBody body);
    //吾家初始化
    @POST("back/test/api/mine/mineindex.php")
    Observable<BaseResponse<MineBean>> mineinit(@Body RequestBody body);
//    商家首页
    @POST("back/test/api/shangjia/sellermyhome.php")
    Observable<BaseResponse<SellerIndexBean>> sellerinit(@Body RequestBody body);
    //首页商品搜索
    @POST("back/test/api/homepage/home_page_search.php")
    Observable<BaseResponse<List<SearchItemBean>>> getSearchItemList(@Body RequestBody body);
    //首页店铺搜索
    @POST("back/test/api/homepage/home_page_search.php")
    Observable<BaseResponse<List<SearchShopBean>>> getSearchShopList(@Body RequestBody body);
    //首页大师搜索
    @POST("back/test/api/homepage/home_page_search.php")
    Observable<BaseResponse<List<SearchMasterBean>>> SearchMasterList(@Body RequestBody body);
    //大师列表
    @POST("app_api/ceramics/master_list.php")
    Observable<BaseResponse<List<MasterItemBean>>> master_list(@Body RequestBody body);
    //购物车
    @POST("back/test/api/shopping/shoppingcart.php")
    Observable<BaseResponse<List<GoodsBean>>> getGoods(@Body RequestBody body);
    //删除购物车
    @POST("back/test/api/shopping/cart_delete.php")
    Observable<BaseResponse<String[]>> deleteCart(@Body RequestBody body);
    //微博登陆验证
    @GET("users/show.json")
    Call<ResponseBody> getWeiboData(@QueryMap Map<String,String> params);
    //获取集市大师作品
    @POST("back/test/api/homepage/master_works.php")
    Observable<BaseResponse<List<MasterWorkBean>>> getMasterWorkList(@Body RequestBody body);
    //获取集市商铺
    @POST("back/test/api/homepage/store_center.php")
    Observable<BaseResponse<List<StoreCenterBean>>> getStoreCenterList(@Body RequestBody body);
    @POST("app_api/grounding/generate_bond_orders.php")
    Observable<BaseResponse<GenerateOrdersBean>> generateUserBondOrder(@Body RequestBody body);
    //店铺定制列表
    @POST("back/test/api/dingzhi/seller_getdingzhi.php")
    Observable<BaseResponse<List<MyDingzhiBean>>> getShopDingzhiList(@Body RequestBody body);
    //定制详情
    @POST("back/test/api/dingzhi/dingzhi_detail.php")
    Observable<BaseResponse<DingzhiDetailBean>> dingzhiDetail(@Body RequestBody body);
    //生成定制保证金(尾款)订单
    @POST("back/test/api/dingzhi/generate_dingzhi_orders.php")
    Observable<BaseResponse<GenerateOrdersBean>> generateDingzhiBondOrder(@Body RequestBody body);
    //用户保证金支付
    @POST("app_api/home_page/dingzhi_bondpay.php")
    Observable<BaseResponse<Boolean>> dingzhiBondPay(@Body RequestBody body);
    //商家接受定制订单
    @POST("back/test/api/dingzhi/confirm_order.php")
    Observable<BaseResponse<Boolean>> confirmDingzhiOrder(@Body  RequestBody body);
    //商家拒绝定制订单
    @POST("back/test/api/dingzhi/cancel_dingzhi_order.php")
    Observable<BaseResponse<Boolean>> cancelDingzhiOrder(@Body  RequestBody body);
    //定制发货
    @POST("back/test/api/dingzhi/dingzhi_courier.php")
    Observable<BaseResponse<Boolean>> dingzhiCourier(@Body  RequestBody body);
    //定制确认收货
    @POST("back/test/api/dingzhi/dingzhi_receive.php")
    Observable<BaseResponse<Boolean>> dingzhiConfirmReceipt(@Body RequestBody body);
    //发布定制需求
    @POST("back/test/api/dingzhi/publishdingzhi.php")
    Observable<BaseResponse<String[]>> publishDingzhi(@Body RequestBody body);
    //获取我的定制
    @POST("back/test/api/dingzhi/getmydingzhi.php")
    Observable<BaseResponse<List<MyDingzhiBean>>> getMyDingzhi(@Body RequestBody body);
    //获取遇见大师
    @POST("back/test/api/master/meet_master.php")
    Observable<BaseResponse<List<MeetMasterBean>>> getMasterStudio(@Body RequestBody body);
    //获取指定大师
    @POST("back/test/api/dingzhi/getmasterorshop.php")
    Observable<BaseResponse<List<DashiCellBean>>> ChooseDashi(@Body RequestBody body);
    //个人主页初始化
    @POST("back/test/api/quan/personal_index.php")
    Observable<BaseResponse<PersonalIndexBean>> PersonalIndex(@Body RequestBody body);
    //关注与取关
    @POST("back/test/api/quan/focusandchancel.php")
    Observable<BaseResponse<Boolean>> Focus(@Body RequestBody body);
    //添加黑名单
    @POST("back/test/api/homepage/add_blacklist.php")
    Observable<BaseResponse<Boolean>> addBlacklist(@Body RequestBody body);
    //个人动态文章
    @POST("back/test/api/quan/personal_dynamic_article.php")
    Observable<BaseResponse<List<DynamicBean>>> getPersonalDynamicArticle(@Body RequestBody body);
    //点赞
    @POST("back/test/api/quan/dianzan.php")
    Observable<BaseResponse<String []>> dianZan(@Body RequestBody body);
    //查看个人资料
    @POST("back/test/api/mine/view_userinfo.php")
    Observable<BaseResponse<MyInfoBean>> viewUserInfo(@Body RequestBody body);
    //询问昵称是否占用
    @POST("back/test/api/mine/view_username.php")
    Observable<BaseResponse<Boolean>> viewUsername(@Body RequestBody body);
    //修改个人资料
    @POST("back/test/api/mine/edit_userinfo.php")
    Observable<BaseResponse<String[]>> editUserinfo(@Body RequestBody body);

    //发布动态
    @POST("back/test/api/quan/releasedynamic.php")
    Observable<BaseResponse<Boolean>> ReleaseDynamic(@Body RequestBody body);
    //    保存动态
    @POST("back/test/api/quan/save_dynamic.php")
    Observable<BaseResponse<Boolean>> SaveDynamic(@Body RequestBody body);
    //    获取草稿箱动态详情
    @POST("back/test/api/quan/get_dynamic_draft.php")
    Observable<BaseResponse<DraftsDynamic>> getDynamic(@Body RequestBody body);
    //获取好友列表
    @POST("back/test/api/quan/getfriends.php")
    Observable<BaseResponse<List<FriendBean>>> getFriends(@Body RequestBody body);
    //发布文章
    @POST("back/test/api/quan/releasearticle.php")
    Observable<BaseResponse<Boolean>> ReleaseArticle(@Body RequestBody body);
    //    获取草稿箱文章详情
    @POST("back/test/api/quan/get_drafts_articledetail.php")
    Observable<BaseResponse<DraftsArticle>> getArticle(@Body RequestBody body);
    //    保存文章
    @POST("back/test/api/quan/drafts_article.php")
    Observable<BaseResponse<Boolean>> SaveArticle(@Body RequestBody body);

//    草稿箱
//    从草稿箱删除动态/文章
    @POST("back/test/api/quan/delete_drafts.php")
    Observable<BaseResponse<String[]>> deleteDraftsDynamic(@Body RequestBody body);

    //评论
    @POST("app_api/yuba/releasepinglun.php")
    Observable<BaseResponse<String>> pingLun(@Body RequestBody body);
    //删除动态
    @POST("back/test/api/quan/delete_dynamic.php")
    Observable<BaseResponse<String[]>> DeleteDynamic(@Body RequestBody body);
    //删除文章
    @POST("back/test/api/quan/delete_article.php")
    Observable<BaseResponse<String[]>> DeleteArticle(@Body RequestBody body);
    //屏蔽
    @POST("back/test/api/quan/shield.php")
    Observable<BaseResponse<String[]>> shield(@Body RequestBody body);
    //店铺动态
    @POST("back/test/api/commodity/shop_dynamic.php")
    Observable<BaseResponse<List<DynamicBean>>> getShopDynamic(@Body RequestBody body);
    //店铺内分类搜索
    @POST("back/test/api/homepage/shop_fenlei.php")
    Observable<BaseResponse<List<ResultBean>>> getShopFenleiData(@Body RequestBody body);
    //用户访问店铺首页
    @POST("back/test/api/homepage/shophead.php")
    Observable<BaseResponse<ShopIndexBean>> getUserShopIndexResult(@Body RequestBody body);
    //收藏商品、店铺collectItem
    @POST("back/test/api/mine/add_collection.php")
    Observable<BaseResponse<Boolean>> collectItem(@Body RequestBody body);
    //分享店铺
    @POST("app_api/wujia/share_shop.php")
    Observable<BaseResponse<Boolean>> shareShop(@Body RequestBody body);
    //店铺首页 店铺评论
    @POST("back/test/api/commodity/shop_pinglun.php")
    Observable<BaseResponse<ShopPinglunBean>> getShopPinglun(@Body RequestBody body);
    //店铺首页 新品和全部1商品
    @POST("back/test/api/homepage/shopxinpin.php")
    Observable<BaseResponse<List<ShopGoodsBean>>> getShopCommodity(@Body RequestBody body);
    //广场动态和文章
    @POST("back/test/api/quan/square_dynamic_article.php")
    Observable<BaseResponse<List<DynamicBean>>> getSquareDynamicArticle(@Body RequestBody body);
    //订单详情
    @POST("back/test/api/order/ordermoremsg.php")
    Observable<BaseResponse<OrderDetailBean>> getOrderDetailData(@Body RequestBody body);
    //取消订单
    @POST("back/test/api/order/cancelorder.php")
    Observable<BaseResponse<String[]>> cancelOrder(@Body RequestBody body);
    //删除订单
    @POST("back/test/api/order/deleteorder.php")
    Observable<BaseResponse<String[]>> deleteOrder(@Body RequestBody body);
    //确认收货
    @POST("back/test/api/order/receivedelivery.php")
    Observable<BaseResponse<String[]>> confirmReceived(@Body RequestBody body);
//    我的订单
    @POST("back/test/api/mine/waitpay.php")
    Observable<BaseResponse<List<MyOrderFragmentBean>>> getOrderList(@Body RequestBody body);
    //提醒发货
    @POST("back/test/api/order/reminddelivery.php")
    Observable<BaseResponse<String[]>> remindDelivery(@Body RequestBody body);
    //上传图片
    @POST("back/test/api/upload/upload.php")
    @Multipart
    Observable<BaseResponse<List<String>>> uploadImage(@Part("data")RequestBody body, @Part MultipartBody.Part[] part);
    //申请退款
    @POST("back/test/api/order/submitrefund.php")
    Observable<BaseResponse> submitRefund(@Body RequestBody body);
    //吾家退款订单
    @POST("back/test/api/order/refundwujia.php")
    Observable<BaseResponse<List<RefundListBean>>> getWujiaRefundData(@Body RequestBody body);
    //用户查看退款详情
    @POST("back/test/api/order/refundwujiamoremsg.php")
    Observable<BaseResponse<RefundDetailBean>> refundDetail(@Body RequestBody body);
    //取消退款/售后申请
    @POST("back/test/api/order/cancel_refund.php")
    Observable<BaseResponse<Boolean>> CancelRefund(@Body RequestBody body);
    //退货退款填写退款物流单号
    @POST("back/test/api/order/add_refund_logistics.php")
    Observable<BaseResponse<String[]>> InputLogistics(@Body RequestBody body);
    //关注和粉丝列表
    @POST("back/test/api/quan/focusandfans.php")
    Observable<BaseResponse<List<FocusAndFansBean>>> getFocusandFansList(@Body RequestBody body);
    //申请入驻
    @POST("back/test/api/mine/applyenter.php")
    Observable<BaseResponse<String[]>> ApplyEnter(@Body RequestBody body);
//    申请大师
    @POST("back/test/api//mine/masterattestation.php")
    Observable<BaseResponse<String[]>> MasterAttestation(@Body RequestBody body);
    //移除黑名单
    @POST("back/test/api/homepage/remove_blacklist.php")
    Observable<BaseResponse<String[]>> removeBlacklist(@Body RequestBody body);
    //黑名单初始化
    @POST("back/test/api/homepage/blacklist.php")
    Observable<BaseResponse<List<BlackListBean>>> getBlacklist(@Body RequestBody body);
    //获取版本信息
    @POST("app_api/home_page/get_version.php")
    Observable<BaseResponse<Integer>> getVersion(@Body RequestBody body);
    //获取验证码
    @POST("back/test/api/app_login/login_module.php")
    Observable<BaseResponse<String[]>> getValidCode(@Body RequestBody body);
    //重置密码
    @POST("back/test/api/app_login/auto_login.php")
    Observable<BaseResponse<String[]>> resetPwd(@Body RequestBody body);
    //用户反馈
    @POST("back/test/api/homepage/usersopinion.php")
    Observable<BaseResponse<String[]>> usersOpinion(@Body RequestBody body);

    //商品详情页itemDetail
    @POST("back/test/api/commodity/item_detail.php")
    Observable<BaseResponse<ItemDetailBean>> itemDetail(@Body RequestBody body);
    //添加购物车
    @POST("back/test/api/shopping/cart_add.php")
    Observable<BaseResponse<String[]>> addCart(@Body RequestBody body);
    //加载更多广告
    @POST("back/test/api/homepage/loadmoreads.php")
    Observable<BaseResponse<List<AdsCellBean>>> loadMoreAds(@Body RequestBody body);
    //    商家管理获取订单管理getOrdersManage
    @POST("back/test/api/shangjia/ordermanage.php")
    Observable<BaseResponse<List<ShopOrderBean>>> getOrdersManage(@Body RequestBody body);

    //商家查看退款详情
    @POST("back/test/api/order/refundmoremsg.php")
    Observable<BaseResponse<com.yuanyu.ceramics.seller.refund.refund_detail.RefundDetailBean>> getRefundDetail(@Body RequestBody body);

    //商家退款审核
    @POST("back/test/api/order/refundreview.php")
    Observable<BaseResponse<Boolean>> RefundReview(@Body RequestBody body);
    //    shopGetOrderDetail
    @POST("back/test/api/order/sellordermoremsg.php")
    Observable<BaseResponse<ShopOrderDetailBean>> shopGetOrderDetail(@Body RequestBody body);
//    getLogisticsTracing
    //快递递踪
    @POST("back/test/api/order/express_tracking.php")
    Observable<BaseResponse<LogisticsBean>> getLogisticsTracing(@Body RequestBody body);
//    modityOrderPrice商家修改未支付订单价格
    @POST("back/test/api/order/change_order_price.php")
    Observable<BaseResponse<Boolean>> modityOrderPrice(@Body RequestBody body);
    //商家退款接口
    @POST("back/test/api/order/refund.php")
    Observable<BaseResponse<List<RefundBean>>> getRefundResult(@Body RequestBody body);

//    修改商家店铺简介
    @POST("back/test/api/shangjia/change_shop_introduce.php")
    Observable<BaseResponse<Boolean>> ShopChangeIntroduce(@Body RequestBody body);

    //商品上架接口
    @POST("back/test/api/shangjia/shangjiaapply.php")
    Observable<BaseResponse<String[]>> Shelve(@Body RequestBody body);
    //上传视频
    @POST("back/test/api/upload/upload_video.php")
    @Multipart
    Observable<BaseResponse<VideoBean>> uploadVideo(@Part("data")RequestBody body,
                                                    @Part MultipartBody.Part[] part);
    //商品上架审核状态接口
    @POST("back/test/api/shangjia/waitreview.php")
    Observable<BaseResponse<List<ShelveAuditBean>>> getWaitReviewResult(@Body RequestBody body);
    //商家删除商品
    @POST("back/test/api/shangjia/delete_commidity.php")
    Observable<BaseResponse<String[]>> DeleteResult(@Body RequestBody body);
    //我的商品 重新申请上架获取商品信息
    @POST("back/test/api/shangjia/reonsale.php")
    Observable<BaseResponse<ShelvingDetailBean>> getReOnSaleData(@Body RequestBody body);
    //我的商品 下架接口
    @POST("back/test/api/shangjia/upsale.php")
    Observable<BaseResponse<String[]>> shopGoodsOffShelves(@Body RequestBody body);
    //我的商品接口
    @POST("back/test/api/shangjia/mycommodity.php")
    Observable<BaseResponse<List<com.yuanyu.ceramics.seller.shop_goods.ShopGoodsBean>>>getShopGoodsList(@Body RequestBody body);
    //商家获取商品详情
    @POST("back/test/api/shangjia/get_shop_goodsdetail.php")
    Observable<BaseResponse<ShelvingDetailBean>> getShopGoodsDetail(@Body RequestBody body);

    //查看快递类型
    @POST("123")
    Observable<BaseResponse<List<CourierBean>>> getCourierData(@Body RequestBody body);
    //获取所有快递
    @POST("back/test/api/order/expresscompany.php")
    Observable<BaseResponse<List<CourierBean>>> getCourierCompany(@Body RequestBody body);
    //获取商家申请直播状态
    @POST("back/test/api/broadcast/liveapplystate.php")
    Observable<BaseResponse<LiveApplyStatusBean>> getLiveApplyState(@Body RequestBody body);
    //商家申请直播
    @POST("back/test/api/broadcast/insert_live_application.php")
    Observable<BaseResponse<String[]>> liveApply(@Body RequestBody body);
    //直播获取商品列表
    @POST("back/test/api/broadcast/selectitem.php")
    Observable<BaseResponse<SelectItemBean>> selectItem(@Body RequestBody body);
    //商家发货
    @POST("back/test/api/order/shopdelivery.php")
    Observable<BaseResponse<String>> Delivery(@Body RequestBody body);
    //商家发货获取收货人信息
    @POST("back/test/api/shangjia/getdeliverymsg.php")
    Observable<BaseResponse<DeliveryBean>> getDeliveryData(@Body RequestBody body);
    //商家获取待发货数据
    @POST("back/test/api/shangjia/getnodelivery.php")
    Observable<BaseResponse<List<WaitDeliveryBean>>> getWaitDeliveryData(@Body RequestBody body);
    //获取分类结果
    @POST("back/test/api/homepage/getfenlei.php")
    Observable<BaseResponse<List<FenLeiResBean>>> getFenleiResult(@Body RequestBody body);
    //添加收货地址
    @POST("back/test/api/address/add_address.php")
    Observable<BaseResponse<String[]>> addAddress(@Body RequestBody body);
    //编辑收货地址
    @POST("back/test/api/address/edit_address.php")
    Observable<BaseResponse<String[]>> editAddress(@Body RequestBody body);
    //收货地址列表初始化
    @POST("back/test/api/address/address.php")
    Observable<BaseResponse<List<AddressManageBean>>> getAddressData(@Body RequestBody body);
    //设置默认地址
    @POST("back/test/api/address/default_address.php")
    Observable<BaseResponse<String[]>> setDefaultAddress(@Body RequestBody body);
    //删除地址
    @POST("back/test/api/address/delete_address.php")
    Observable<BaseResponse<String[]>> deleteAddress(@Body RequestBody body);
    //获取合作机构列表
    @POST("back/test/api/homepage/partners.php")
    Observable<BaseResponse<List<CooperationListBean>>> getCooperationList(@Body RequestBody body);

    //自动登录
    @POST("back/test/api/app_login/auto_login.php")
    Observable<BaseResponse<LoginBean>> autoLogin(@Body RequestBody body);
    //刷新token
    @POST("app_api/app_login/refresh_token.php")
    Observable<BaseResponse<TokenBean>> refreshToken(@Body RequestBody body);
    //获取聊天对象信息

    @POST("back/test/api/homepage/userinfo.php")
    Observable<BaseResponse<ChatBean>> getChaterInfo(@Body RequestBody body);
    //获取我的收藏
    @POST("back/test/api/mine/my_collect.php")
    Observable<BaseResponse<List<MyCollectBean>>> getMyCollect(@Body RequestBody body);
    //修改主页背景和头像
    @POST("back/test/api/mine/change_image.php")
    Observable<BaseResponse> changeImage(@Body RequestBody body);
//    商家更换头像
    @POST("back/test/api/shangjia/replaceportrait.php")
    Observable<BaseResponse<String[]>> getImagePortraitResult(@Body RequestBody body);
    //    获取草稿箱内容
    @POST("back/test/api/quan/get_draftslist.php")
    Observable<BaseResponse<List<DraftsBean>>> getDrafts(@Body RequestBody body);
    //观众直播初始化
    @POST("back/test/api/broadcast/getliveroomdata.php")
    Observable<BaseResponse<LivePullBean>> broadcastInit(@Body RequestBody body);
    //直播订阅
    @POST("app_api/yuba/change_image.php")
    Observable<BaseResponse<List<Boolean>>> subscribeLive(@Body RequestBody body);
    //获取热门搜索
    @POST("back/test/api/homepage/home_hot_search.php")
    Observable<BaseResponse<List<String>>> getSearchHotList(@Body RequestBody body);
    //观众直播初始化
    @POST("back/test/api/broadcast/shop_get_liveroom.php")
    Observable<BaseResponse<LivePushBean>> livePushInit(@Body  RequestBody body);
    //结束直播
    @POST("back/test/api/broadcast/finishlivecast.php")
    Observable<BaseResponse<String[]>> finishLive(@Body  RequestBody body);

    //生成订单
    @POST("back/test/api/homepage/generate_orders.php")
    Observable<BaseResponse<com.yuanyu.ceramics.cart.GenerateOrdersBean>> generateOrders(@Body RequestBody body);
    //获取客服
    @POST("back/test/api/mine/getcustomerservice.php")
    Observable<BaseResponse<String>> getCustomerService(@Body RequestBody body);
    //大额支付获取订单价格
    @POST("back/test/api/order/allprice.php")
    Observable<BaseResponse<String>> getAllprice(@Body RequestBody body);
    //举报
    @POST("back/test/api/homepage/report.php")
    Observable<BaseResponse<String[]>> submitReport(@Body RequestBody body);
    //获取商家评论
    @POST("back/test/commodity/evaluationmanage.php")
    Observable<BaseResponse<List<EvaluationManageBean>>> getEvaluation(@Body RequestBody body);
    //商家回复replyEvaluation
    @POST("123")
    Observable<BaseResponse<String[]>> replyEvaluation(@Body RequestBody body);
    //提交订单评价
    @POST("back/test/api/commodity/evaluateitem.php")
    Observable<BaseResponse<String[]>> submitEvulation(@Body RequestBody body);
    //获取我的金豆
    @POST("123")
    Observable<BaseResponse<String>> getMyCoinsDate(@Body RequestBody body);
    //获取我的金豆任务
    @POST("123")
    Observable<BaseResponse<GetCoinsBean>> getGetCoinsTask(@Body RequestBody body);
    //换金豆兑换比例
    @POST("123")
    Observable<BaseResponse<List<ExchangeCoinsBean>>> getMyCoinsExchange(@Body RequestBody body);
    //兑换金币
    @POST("123")
    Observable<BaseResponse<Boolean>> ExchangeCoins(@Body RequestBody body);
    //金币明细
    @POST("back/test/api/coins/coins_detail.php")
    Observable<BaseResponse<List<MyCoinsDetailBean>>> getMyCoinsDetail(@Body RequestBody body);
}

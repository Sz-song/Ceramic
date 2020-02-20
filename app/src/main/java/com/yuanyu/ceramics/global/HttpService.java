package com.yuanyu.ceramics.global;


import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.bazaar.MasterWorkBean;
import com.yuanyu.ceramics.bazaar.StoreCenterBean;
import com.yuanyu.ceramics.broadcast.BroadcastBean;
import com.yuanyu.ceramics.cart.GoodsBean;
import com.yuanyu.ceramics.home.homepage.HomepageBean;
import com.yuanyu.ceramics.login.LoginBean;
import com.yuanyu.ceramics.master.MasterItemBean;
import com.yuanyu.ceramics.message.MessageBean;
import com.yuanyu.ceramics.mine.MineBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
}

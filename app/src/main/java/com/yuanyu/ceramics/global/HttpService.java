package com.yuanyu.ceramics.global;


import com.yuanyu.ceramics.base.BaseResponse;
import com.yuanyu.ceramics.broadcast.BroadcastBean;
import com.yuanyu.ceramics.cart.GoodsBean;
import com.yuanyu.ceramics.home.homepage.HomepageBean;
import com.yuanyu.ceramics.login.LoginBean;
import com.yuanyu.ceramics.master.MasterItemBean;
import com.yuanyu.ceramics.message.MessageBean;
import com.yuanyu.ceramics.mine.MineBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HttpService {
    //用户登录
    @POST("app_api/login.php")
    Observable<BaseResponse<LoginBean>> login(@Body RequestBody body);
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
    @POST("app_api/home_page/shoppingcart.php")
    Observable<BaseResponse<List<GoodsBean>>> getGoods(@Body RequestBody body);
    //删除购物车
    @POST("app_api/home_page/cart_delete.php")
    Observable<BaseResponse<String[]>> deleteCart(@Body RequestBody body);
}

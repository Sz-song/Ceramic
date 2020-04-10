package com.yuanyu.ceramics.wxapi;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.utils.L;

import org.greenrobot.eventbus.EventBus;

import static com.tencent.mm.opensdk.constants.ConstantsAPI.COMMAND_PAY_BY_WX;


public class WXPayEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_entry);
        api = WXAPIFactory.createWXAPI(this, AppConstant.WECHAT_APP_ID);//这里填入自己的微信APPID
        api.handleIntent(getIntent(), this);
    }

    @Override
    public void onResp(BaseResp baseResp) {
        L.e("coyc", "onPayFinish, errCode = " + baseResp.errCode+"  "+baseResp.getType()+"  "+baseResp.toString());
        if (baseResp.getType() == COMMAND_PAY_BY_WX) {
            int errCord = baseResp.errCode;

            if (errCord == 0) {
                EventBus.getDefault().post(new WechatEvent(baseResp.errCode,baseResp.transaction,baseResp.openId));
                finish();
            } else if (errCord == -1){
                EventBus.getDefault().post(new WechatEvent(baseResp.errCode,baseResp.transaction,baseResp.openId));
                finish();
            } else if (errCord == -2){
                EventBus.getDefault().post(new WechatEvent(baseResp.errCode,baseResp.transaction,baseResp.openId));
                finish();
            }
            //这里接收到了返回的状态码可以进行相应的操作，如果不想在这个页面操作可以把状态码存在本地然后finish掉这个页面，这样就回到了你调起支付的那个页面
            //获取到你刚刚存到本地的状态码进行相应的操作就可以了
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }
}

package com.yuanyu.ceramics.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.yuanyu.ceramics.utils.L;

import static com.yuanyu.ceramics.AppConstant.WECHAT_APP_ID;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, WECHAT_APP_ID, false);
        try {
            api.handleIntent(getIntent(), this);
        }catch (Exception e){
            L.e(e.getMessage());
            e.printStackTrace();
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
        finish();
    }

    @Override
    public void onResp(BaseResp resp) {
        if(resp.getType()==1){//第三方登录
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    L.e("WX","sendauth OK");
                    SendAuth.Resp newResp = (SendAuth.Resp) resp;
                    //获取微信传回的code
                    String code = newResp.code;
                    String state=newResp.state;
                    try {
                        if (Long.parseLong(state.substring(0, 13)) + 5 * 60* 1000 < System.currentTimeMillis()) {
                            Toast.makeText(this, "授权已过期", Toast.LENGTH_SHORT).show();
                        } else {
                            if(state.substring(13).equals("0")){
//                                WeChatLogin(code);
                            }else if(state.substring(13).equals("1")){
//                                WeChatBind(code);
                            }
                        }
                    }catch (Exception e){
                        L.e("exception is:" +e.getMessage());
                    }
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    L.e("WXTest","onResp ERR_USER_CANCEL ");
                    Toast.makeText(WXEntryActivity.this, "授权取消", Toast.LENGTH_SHORT).show();
                    //发送取消
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    L.e("WXTest","onResp ERR_AUTH_DENIED");
                    Toast.makeText(WXEntryActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
                    //发送被拒绝
                    break;
                default:
                    L.e("WXTest","onResp default errCode " + resp.errCode);
                    //发送返回
                    break;
            }
            finish();
        }else if(resp.getType()==2){//微信分享
            L.e("errcode is"+resp.errCode);
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    L.e("分享回调");
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    L.e("WXTest","onResp ERR_USER_CANCEL ");
                    Toast.makeText(WXEntryActivity.this, "分享取消", Toast.LENGTH_SHORT).show();
                    finish();
                    //发送取消
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    L.e("WXTest","onResp ERR_AUTH_DENIED");
                    Toast.makeText(WXEntryActivity.this, "分享失败", Toast.LENGTH_SHORT).show();
                    finish();
                    //发送被拒绝
                    break;
                default:
                    L.e("WXTest","onResp default errCode " + resp.errCode);
                    finish();
                    //发送返回
                    break;
            }
        }else{
            finish();
        }
        finish();
        L.e("结束");
    }
}

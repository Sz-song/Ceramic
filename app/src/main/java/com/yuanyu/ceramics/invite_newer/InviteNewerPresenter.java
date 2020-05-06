package com.yuanyu.ceramics.invite_newer;

import android.graphics.Bitmap;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.Md5Utils;
import com.yuanyu.ceramics.wxapi.Util;

public class InviteNewerPresenter extends BasePresenter<InviteNewerConstract.IInviteNewerView> implements InviteNewerConstract.IInviteNewerPresenter {

    @Override
    public void ShareWechat(Bitmap bmp, String miniProgrampath, String url, String title) {
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = url; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = "gh_d13c9fedfc0a";     // 小程序原始id
        miniProgramObj.path = miniProgrampath;            //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;// 小程序消息title
        msg.description = "";// 小程序消息desc
        msg.thumbData = Util.bmpToByteArray(bmp, 128);// 小程序消息封面图片，小于128k
        bmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = Md5Utils.getRandomString(5) + System.currentTimeMillis();
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
        AppConstant.wx_api.sendReq(req);
    }
}

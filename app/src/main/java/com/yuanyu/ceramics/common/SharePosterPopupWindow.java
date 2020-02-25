package com.yuanyu.ceramics.common;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.sina.weibo.sdk.api.ImageObject;
import com.sina.weibo.sdk.api.TextObject;
import com.sina.weibo.sdk.api.WebpageObject;
import com.sina.weibo.sdk.api.WeiboMultiMessage;
import com.sina.weibo.sdk.share.WbShareHandler;
import com.sina.weibo.sdk.utils.Utility;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMiniProgramObject;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.QRCodeUtil;
import com.yuanyu.ceramics.wxapi.Util;

import java.io.ByteArrayOutputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class SharePosterPopupWindow extends PopupWindow {

    private ImageView cancel;
    private CircleImageView shopPortrait;
    private TextView title;
    private TextView shopName;
    private TextView shareType;
    private ImageView qrCode;
    private ImageView posterImage;
    private LinearLayout sava;
    private LinearLayout wechat;
    private LinearLayout wechatMonment;
    private LinearLayout qq;
    private LinearLayout weibo;
    private LinearLayout poster;
    private Context context;
    private View view;
    private OnSavaImageListener savaImageListener;
    private OnNoDataListener shareShopListener;
    private WebpageObject webpageObject;
    private ImageObject imageObject ;
    private WbShareHandler shareHandler;
    private String url;

    public void setShareShopListener(OnNoDataListener shareShopListener) {
        this.shareShopListener = shareShopListener;
    }

    public void setSavaImageListener(OnSavaImageListener savaImageListener) {
        this.savaImageListener = savaImageListener;
    }

    public SharePosterPopupWindow(Context context, Activity activity, String shop_logo, String shop_name, String share_type, String share_title, String poster_image, String url, String miniProgramPath) {
        super(context);
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_share_poster, null);
        this.setContentView(this.view);
        this.url=url;
        shareHandler = new WbShareHandler(activity);
        shareHandler.registerApp();
        webpageObject = new WebpageObject();
        imageObject = new ImageObject();
        cancel=view.findViewById(R.id.cancel);
        shopPortrait=view.findViewById(R.id.shop_portrait);
        title=view.findViewById(R.id.title);
        shopName=view.findViewById(R.id.shop_name);
        shareType=view.findViewById(R.id.share_type);
        qrCode=view.findViewById(R.id.qr_code);
        posterImage=view.findViewById(R.id.poster_image);
        sava =view.findViewById(R.id.sava);
        wechat=view.findViewById(R.id.wechat);
        wechatMonment=view.findViewById(R.id.wechat_monment);
        qq=view.findViewById(R.id.qq);
        weibo=view.findViewById(R.id.weibo);
        poster=view.findViewById(R.id.poster);
        shopName.setText(shop_name);
        shareType.setText(share_type);
        title.setText(share_title);
        GlideApp.with(context)
                .load(AppConstant.BASE_URL+shop_logo)
                .placeholder(R.drawable.img_default)
                .override(150,150)
                .into(shopPortrait);
        GlideApp.with(context)
                .load(AppConstant.BASE_URL+poster_image)
                .placeholder(R.drawable.img_default)
                .into(posterImage);
        Bitmap bitmap= QRCodeUtil.createQRCodeBitmap(url,600,600);
        GlideApp.with(context)
                .load(bitmap)
                .into(qrCode);
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);//外部点击消失
        ColorDrawable dw = new ColorDrawable(0x004000000);
        this.setBackgroundDrawable(dw);
        this.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 =((Activity) context).getWindow().getAttributes();
            lp1.alpha = 1f; // 0.0-1.0
            ((Activity) context).getWindow().setAttributes(lp1);
        });
        cancel.setOnClickListener(v -> dismiss());
        sava.setOnClickListener(v -> {
            poster.setDrawingCacheEnabled(true);
            poster.buildDrawingCache();
            Bitmap posterImage = Bitmap.createBitmap(poster.getDrawingCache());
            savaImageListener.SavaImage(posterImage,0);
        });
        wechat.setOnClickListener(v -> {
            posterImage.setDrawingCacheEnabled(true);
            posterImage.buildDrawingCache();
            Bitmap posterbitmap = Bitmap.createBitmap(posterImage.getDrawingCache());
            if(share_title.trim().length()>0){
                shareWechat(posterbitmap, miniProgramPath,share_type+"  "+shop_name+"-"+share_title);
            }else{
                shareWechat(posterbitmap, miniProgramPath,share_type+"  "+shop_name);
            }
            if(share_type.equals("店铺分享")&&shareShopListener!=null){
                shareShopListener.setNodata();
            }
        });
        wechatMonment.setOnClickListener(v -> {
            poster.setDrawingCacheEnabled(true);
            poster.buildDrawingCache();
            Bitmap posterImage = Bitmap.createBitmap(poster.getDrawingCache());
            shareWechatMonment(posterImage);
            if(share_type.equals("店铺分享")&&shareShopListener!=null){
                shareShopListener.setNodata();
            }
        });
        qq.setOnClickListener(v -> {
            poster.setDrawingCacheEnabled(true);
            poster.buildDrawingCache();
            Bitmap posterImage = Bitmap.createBitmap(poster.getDrawingCache());
            savaImageListener.SavaImage(posterImage,1);
        });
        weibo.setOnClickListener(v -> {
            poster.setDrawingCacheEnabled(true);
            poster.buildDrawingCache();
            Bitmap posterImage = Bitmap.createBitmap(poster.getDrawingCache());
            ShareSina(share_type+shop_name,share_title,url,posterImage);
            if(share_type.equals("店铺分享")&&shareShopListener!=null){
                shareShopListener.setNodata();
            }
        });
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        super.showAtLocation(parent, gravity, x, y);
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = 0.7f; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
    }
    public interface OnSavaImageListener{
        //0 保存图片 1 分享到QQ
        void SavaImage(Bitmap bitmap ,int type);
    }

    //微信
    private void shareWechat(Bitmap bmp, String miniProgrampath, String title){
        WXMiniProgramObject miniProgramObj = new WXMiniProgramObject();
        miniProgramObj.webpageUrl = url; // 兼容低版本的网页链接
        miniProgramObj.miniprogramType = WXMiniProgramObject.MINIPTOGRAM_TYPE_RELEASE;// 正式版:0，测试版:1，体验版:2
        miniProgramObj.userName = "gh_d13c9fedfc0a";     // 小程序原始id
        miniProgramObj.path = miniProgrampath;            //小程序页面路径；对于小游戏，可以只传入 query 部分，来实现传参效果，如：传入 "?foo=bar"
        WXMediaMessage msg = new WXMediaMessage(miniProgramObj);
        msg.title = title;                    // 小程序消息title
        msg.description = "";        // 小程序消息desc
        msg.thumbData = Util.bmpToByteArray(bmp, 128);// 小程序消息封面图片，小于128k
        bmp.recycle();
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("miniProgram");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneSession;  // 目前只支持会话
        AppConstant.wx_api.sendReq(req);
    }
    private void shareWechatMonment(Bitmap bmp) {
        //初始化 WXImageObject 和 WXMediaMessage 对象
        WXImageObject imgObj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        //设置缩略图
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, 180, 320, true);
        bmp.recycle();
        msg.thumbData = Util.bmpToByteArray(thumbBmp, 128);//最大32kb
        //构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        AppConstant.wx_api.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

    private void ShareSina(String title,String content,String url,Bitmap bmp){
        final WeiboMultiMessage weiboMessage = new WeiboMultiMessage();
        L.e("微博分享");
        new Thread(() -> {
            //创建文本消息对象。
            TextObject textObject = new TextObject();
            textObject.text = content;    //标题
            textObject.title = title;
            textObject.actionUrl = url;
            weiboMessage.textObject = textObject;
            //创建图片消息对象。
            L.e("创建图片消息对象");
            imageObject.setImageObject(compressQuality(bmp,2*1024*1024));  //大图  不能超过2m
            weiboMessage.imageObject = imageObject;
            //创建网页消息对象
            L.e("创建网页消息对象");
            webpageObject.identify = Utility.generateGUID();
            webpageObject.title = title;
            webpageObject.description = content;
            L.e("压缩小图");
            webpageObject.setThumbImage(compressQuality(bmp,1024*30));  //缩略图  不能超过32k
            webpageObject.actionUrl = url;
            webpageObject.defaultText = "默认文案";
            weiboMessage.mediaObject =webpageObject;
            L.e("发送请求");
            shareHandler.shareMessage(weiboMessage,true);
        }).start();
    }

    //bitmap 压缩
    private Bitmap compressQuality(Bitmap bm,long size) {
        L.e("size"+bm.getByteCount());
        if(bm.getByteCount()<size){
            L.e("size"+bm.getByteCount());
            return bm;
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        byte[] bytes = bos.toByteArray();
        if(BitmapFactory.decodeByteArray(bytes, 0, bytes.length).getByteCount()>size){
            Matrix matrix = new Matrix();
            matrix.setScale(0.8f, 0.8f);
            Bitmap bit= Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
            if(bit.getByteCount()>size){
                return compressQuality(bit,size);
            }else{
                L.e("size"+bit.getByteCount());
                return bit;
            }
        }else{
            L.e("size"+BitmapFactory.decodeByteArray(bytes, 0, bytes.length).getByteCount());
            return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
    }
}

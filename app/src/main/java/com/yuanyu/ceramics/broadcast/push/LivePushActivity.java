package com.yuanyu.ceramics.broadcast.push;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.global.GlideApp;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.graphics.BitmapFactory.decodeResource;
import static com.tencent.rtmp.TXLiveConstants.VIDEO_QUALITY_HIGH_DEFINITION;
import static com.tencent.rtmp.TXLiveConstants.VIDEO_QUALITY_SUPER_DEFINITION;
import static com.tencent.rtmp.TXLiveConstants.VIDEO_QUALITY_ULTRA_DEFINITION;

public class LivePushActivity extends BaseActivity<LivePushPresenter> implements LivePushConstract.ILivePushView {
    @BindView(R.id.pusher_view)
    TXCloudVideoView pusherView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.change_camera)
    ImageView changeCamera;
    @BindView(R.id.skin_care)
    ImageView skinCare;
    @BindView(R.id.add_filter)
    ImageView addFilter;
    @BindView(R.id.change_sharpness)
    ImageView changeSharpness;
    @BindView(R.id.shopping)
    ImageView shopping;
    @BindView(R.id.add_auction)
    ImageView addAuction;
    @BindView(R.id.bottom_tool)
    LinearLayout bottomTool;
    @BindView(R.id.pusher_avatar)
    CircleImageView pusherAvatar;
    @BindView(R.id.pusher_shop_name)
    TextView pusherShopName;
    @BindView(R.id.watch)
    TextView watch;
    private TXLivePusher livePusher;
    private LivePopupwindowSkinCare livePopupwindowSkinCare;
    private LivePopupwindowFilter livePopupwindowFilter;
    private LivePopupwindowSharpness livePopupwindowSharpness;
    private LivePopupWindowAddAuction livePopupWindowAddAuction;

    @Override
    protected int getLayout() {
        return R.layout.activity_live_push;
    }

    @Override
    protected LivePushPresenter initPresent() {
        return new LivePushPresenter();
    }

    @Override
    protected void initEvent() {
        String licenceURL = "http://license.vod2.myqcloud.com/license/v1/dbf37d2b67f6f47cb14a97b9f895fd26/TXLiveSDK.licence"; // 获取到的 licence url
        String licenceKey = "8dab526c669d9c8fd4494ba082944269"; // 获取到的 licence key
        TXLiveBase.getInstance().setLicence(this, licenceURL, licenceKey);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_rd);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.RECORD_AUDIO);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            initLivePush();
        }
        livePopupwindowSkinCare = new LivePopupwindowSkinCare(this);
        livePopupwindowSkinCare.setOnPositionClickListener(position -> {
            if (position == 0) {
                GlideApp.with(this)
                        .load(R.drawable.skin_care)
                        .into(skinCare);
            } else {
                GlideApp.with(this)
                        .load(R.drawable.skin_care_focus)
                        .into(skinCare);
            }
            livePusher.setBeautyFilter(1, position, position, position);
        });
        livePopupwindowFilter = new LivePopupwindowFilter(this);
        livePopupwindowFilter.setOnPositionClickListener(this::switchFilter);
        livePopupwindowSharpness = new LivePopupwindowSharpness(this);
        livePopupwindowSharpness.setOnPositionClickListener(this::switchSharpness);
        livePopupWindowAddAuction=new LivePopupWindowAddAuction(this);
    }


    @Override
    public void switchFilter(int position) {
        Bitmap filterBmp;
        GlideApp.with(this)
                .load(R.drawable.add_filter_focus)
                .into(addFilter);
        switch (position) {
            case 1:
                filterBmp = decodeResource(getResources(), R.drawable.filter_biaozhun);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
                break;
            case 2:
                filterBmp = decodeResource(getResources(), R.drawable.filter_yinghong);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
                break;
            case 3:
                filterBmp = decodeResource(getResources(), R.drawable.filter_yunshang);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
                break;
            case 4:
                filterBmp = decodeResource(getResources(), R.drawable.filter_chunzhen);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
                break;
            case 5:
                filterBmp = decodeResource(getResources(), R.drawable.filter_bailan);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
                break;
            case 6:
                filterBmp = decodeResource(getResources(), R.drawable.filter_yuanqi);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
                break;
            case 7:
                filterBmp = decodeResource(getResources(), R.drawable.filter_chaotuo);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
                break;
            case 8:
                filterBmp = decodeResource(getResources(), R.drawable.filter_xiangfen);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
                break;
            case 9:
                filterBmp = decodeResource(getResources(), R.drawable.filter_white);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
                break;
            case 10:
                filterBmp = decodeResource(getResources(), R.drawable.filter_langman);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
                break;
            case 11:
                filterBmp = decodeResource(getResources(), R.drawable.filter_qingxin);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
                break;
            case 12:
                filterBmp = decodeResource(getResources(), R.drawable.filter_weimei);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
                break;
            case 13:
                filterBmp = decodeResource(getResources(), R.drawable.filter_fennen);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
            case 14:
                filterBmp = decodeResource(getResources(), R.drawable.filter_huaijiu);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
            case 15:
                filterBmp = decodeResource(getResources(), R.drawable.filter_landiao);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
                break;
            case 16:
                filterBmp = decodeResource(getResources(), R.drawable.filter_qingliang);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
                break;
            case 17:
                filterBmp = decodeResource(getResources(), R.drawable.filter_rixi);
                livePusher.setFilter(filterBmp);
                livePusher.setSpecialRatio(0.5f);
                break;
            default:
                livePusher.setFilter(null);
                livePusher.setSpecialRatio(0);
                GlideApp.with(this)
                        .load(R.drawable.add_filter)
                        .into(addFilter);
        }
    }

    @Override
    public void switchSharpness(int pos) {
        switch (pos) {
            case 0:
                livePusher.setVideoQuality(VIDEO_QUALITY_HIGH_DEFINITION, false, false);
                GlideApp.with(this)
                        .load(R.drawable.high_sharpness)
                        .into(changeSharpness);
                break;
            case 1:
                livePusher.setVideoQuality(VIDEO_QUALITY_SUPER_DEFINITION, false, false);
                GlideApp.with(this)
                        .load(R.drawable.super_sharpness)
                        .into(changeSharpness);
                break;
            case 2:
                livePusher.setVideoQuality(VIDEO_QUALITY_ULTRA_DEFINITION, false, false);
                GlideApp.with(this)
                        .load(R.drawable.ultra_sharpness)
                        .into(changeSharpness);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0) {
                for (int result : grantResults) {
                    if (result != PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "请同意权限才能使用该功能", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
                initLivePush();
            } else {
                Toast.makeText(this, "请开启摄像头权限", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        livePusher.stopPusher();
        livePusher.stopCameraPreview(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.live_push_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.share:
//                TODO
                break;
            default:
                break;
        }
        return true;

    }

    @Override
    public void initLivePush() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保持屏幕常亮
        TXLivePushConfig livePushConfig = new TXLivePushConfig();
        livePusher = new TXLivePusher(this);
        livePusher.setConfig(livePushConfig);
        livePusher.startCameraPreview(pusherView);
        String rtmpURL = "rtmp://push.jadeall.cn/live/123?txSecret=2442934948e45281b4ef7938a2688a18&txTime=5E8368FF"; //此处填写您的 rtmp 推流地址
        livePusher.startPusher(rtmpURL.trim());
    }

    @OnClick({R.id.change_camera, R.id.skin_care, R.id.add_filter, R.id.change_sharpness, R.id.shopping, R.id.add_auction})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.change_camera:
                livePusher.switchCamera();
                break;
            case R.id.skin_care:
                livePopupwindowSkinCare.showAtLocation(bottomTool, Gravity.BOTTOM, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()));
                break;
            case R.id.add_filter:
                livePopupwindowFilter.showAtLocation(bottomTool, Gravity.BOTTOM, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()));
                break;
            case R.id.change_sharpness:
                livePopupwindowSharpness.showAtLocation(bottomTool, Gravity.BOTTOM, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()));
                break;
            case R.id.shopping:

                break;
            case R.id.add_auction:
                livePopupWindowAddAuction.showAtLocation(bottomTool, Gravity.BOTTOM, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics()));
                break;
        }
    }

}

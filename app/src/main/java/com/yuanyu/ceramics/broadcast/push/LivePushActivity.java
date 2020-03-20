package com.yuanyu.ceramics.broadcast.push;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.rtmp.ITXLivePushListener;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLivePushConfig;
import com.tencent.rtmp.TXLivePusher;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LivePushActivity extends BaseActivity<LivePushPresenter> implements LivePushConstract.ILivePushView {
    @BindView(R.id.pusher_view)
    TXCloudVideoView pusherView;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private TXLivePusher livePusher;
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
        List<String> permissionList=new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.CAMERA);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.RECORD_AUDIO);
        }
        if(!permissionList.isEmpty()){
            String [] permissions =permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(this,permissions,1);
        }else {
            initLivePush();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1){
            if(grantResults.length>0){
                for(int result : grantResults){
                    if(result!=PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "请同意权限才能使用该功能", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                }
                initLivePush();
            }else{
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
        TXLivePushConfig livePushConfig  = new TXLivePushConfig();
        livePusher = new TXLivePusher(this);
        livePusher.setConfig(livePushConfig);
        livePusher.startCameraPreview(pusherView);
        String rtmpURL = "rtmp://push.jadeall.cn/live/12312312543?txSecret=4a680a8a19dd1dc4c32b2b20d9e3872b&txTime=5E74E87F"; //此处填写您的 rtmp 推流地址
        livePusher.startPusher(rtmpURL.trim());
        livePusher.setBeautyFilter(0,9,9,9);
        livePusher.switchCamera();
    }
}

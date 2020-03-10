package com.yuanyu.ceramics.common;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

public class VideoDisplayActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.delete)
    ImageView delete;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.player)
    JzvdStd player;
    private Intent intent;
    private static final int DELETE_VIDEO = 1005;
    private int isdelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_display);
        ButterKnife.bind(this);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_shop);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        title.setText("查看视频");
        intent=getIntent();
        isdelete=intent.getIntExtra("delete",1);
        if(isdelete!=1){
            delete.setVisibility(View.GONE);
        }
        Jzvd.WIFI_TIP_DIALOG_SHOWED=true;//关闭4G流量监控
        String video = intent.getStringExtra("video");
        String cover = intent.getStringExtra("cover");
        if(video.startsWith("video/")){
            player.setUp(AppConstant.BASE_URL+video, "", Jzvd.SCREEN_NORMAL);
        }else {
            player.setUp(video, "", Jzvd.SCREEN_NORMAL);
        }
        L.e("cover is:"+cover);
        if(cover.startsWith("video/")||cover.startsWith("video/")){
            GlideApp.with(this)
                    .load(AppConstant.BASE_URL+cover)
                    .placeholder(R.drawable.img_default)
                    .override(300, 300)
                    .into(player.thumbImageView);
        }else{
            GlideApp.with(this)
                    .load(video)
                    .placeholder(R.drawable.img_default)
                    .override(300, 300)
                    .into(player.thumbImageView);
        }
    }


}

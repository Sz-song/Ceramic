package com.yuanyu.ceramics.mine.applyenter;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.NormalActivity;
import com.yuanyu.ceramics.common.GlideEngine;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UploadEnterImageActivity extends NormalActivity {
    private final int SELECT_IMAGE_POSITIVE= 201;
    private final int SELECT_IMAGE_REVERSE= 301;
    private final int SELECT_IMAGE_SHOP= 401;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.positive_idcard)
    ImageView positiveIdcard;
    @BindView(R.id.reverse_idcard)
    ImageView reverseIdcard;
    @BindView(R.id.linear_idcard)
    LinearLayout linearIdcard;
    @BindView(R.id.shop_card)
    ImageView shopCard;
    @BindView(R.id.linear_shop)
    LinearLayout linearShop;
    @BindView(R.id.summit)
    TextView summit;
    private int type;
    private String image_positive;
    private String image_reverse;
    private String image_shop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_enter_image);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        type=getIntent().getIntExtra("type",-1);
        if(type==0){//上传身份证
            linearIdcard.setVisibility(View.VISIBLE);
            linearShop.setVisibility(View.GONE);
            title.setText("实名认证");
        }else if(type==1){
            linearIdcard.setVisibility(View.GONE);
            linearShop.setVisibility(View.VISIBLE);
            title.setText("营业执照认证");
        }else{
            linearIdcard.setVisibility(View.GONE);
            linearShop.setVisibility(View.GONE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE_POSITIVE && data != null) {
            List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
            image_positive=images.get(0).getPath();
            GlideApp.with(this)
                    .load(images.get(0).getPath())
                    .into(positiveIdcard);
        } else if (requestCode == SELECT_IMAGE_REVERSE && data != null) {
            List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
            image_reverse=images.get(0).getPath();
            GlideApp.with(this)
                    .load(images.get(0).getPath())
                    .into(reverseIdcard);
        } else if(requestCode == SELECT_IMAGE_SHOP && data != null){
            List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
            image_shop=images.get(0).getPath();
            GlideApp.with(this)
                    .load(images.get(0).getPath())
                    .into(shopCard);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @OnClick({R.id.positive_idcard, R.id.reverse_idcard, R.id.shop_card, R.id.summit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.positive_idcard:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .maxSelectNum(1)// 选择图片数量
                        .isCamera(true)// 是否显示拍照按钮
                        .forResult(SELECT_IMAGE_POSITIVE);
                break;
            case R.id.reverse_idcard:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .maxSelectNum(1)// 选择图片数量
                        .isCamera(true)// 是否显示拍照按钮
                        .forResult(SELECT_IMAGE_REVERSE);
                break;
            case R.id.shop_card:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .maxSelectNum(1)// 选择图片数量
                        .isCamera(true)// 是否显示拍照按钮
                        .forResult(SELECT_IMAGE_SHOP);
                break;
            case R.id.summit:
                if(type==0){
                    if(null==image_positive||image_positive.trim().length()==0){
                        Toast.makeText(this, "请上传身份证头像页", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(null==image_reverse||image_reverse.trim().length()==0){
                        Toast.makeText(this, "请上传身份证国徽页", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent=new Intent();
                    intent.putExtra("image_positive",image_positive);
                    intent.putExtra("image_reverse",image_reverse);
                    setResult(RESULT_OK,intent);
                    finish();
                }else if(type==1){
                    if(null==image_shop||image_shop.trim().length()==0){
                        Toast.makeText(this, "请上传营业执照", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Intent intent=new Intent();
                    intent.putExtra("image_shop",image_shop);
                    setResult(RESULT_OK,intent);
                    finish();
                }else{
                    Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

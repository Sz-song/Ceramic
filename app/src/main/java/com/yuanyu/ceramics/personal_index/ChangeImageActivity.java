package com.yuanyu.ceramics.personal_index;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.common.GlideEngine;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import top.zibin.luban.Luban;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class ChangeImageActivity extends BaseActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.select_photo)
    TextView selectPhoto;
    @BindView(R.id.quit)
    TextView quit;
    @BindView(R.id.confirm)
    TextView confirm;
    @BindView(R.id.image)
    SquareImageView image;
    private int imagetype;
    private String url;
    private PersonalIndexModel model;
    List<File> file = new ArrayList<>();
    List<String> listimage = new ArrayList<>();
    @Override
    protected int getLayout() {
        return R.layout.activity_change_image;
    }

    @Override
    protected BasePresenter initPresent() {
        return new BasePresenter() {
        };
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        Intent intent = getIntent();
        imagetype = intent.getIntExtra("imagetype", -1);
        url = intent.getStringExtra("url");
        if (imagetype == 1) {
            selectPhoto.setText("更改背景图");
            GlideApp.with(this).load(BASE_URL +url).placeholder(R.drawable.img_default).into(image);
        } else if (imagetype == 0) {
            selectPhoto.setText("更改头像");
            GlideApp.with(this).load(BASE_URL +url).placeholder(R.drawable.logo_default).into(image);
        }
        confirm.setVisibility(View.GONE);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1);
            actionBar.setDisplayShowTitleEnabled(false);
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @OnClick({R.id.select_photo, R.id.quit, R.id.confirm})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.select_photo:
                if (imagetype == 0) {
                    PictureSelector.create(this)
                            .openGallery(PictureMimeType.ofImage())
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .maxSelectNum(1)
                            .enableCrop(true)
                            .withAspectRatio(1, 1)
                            .rotateEnabled(false)
                            .forResult(1003);
                } else if (imagetype == 1) {
                    PictureSelector.create(this)
                            .openGallery(PictureMimeType.ofImage())
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .maxSelectNum(1)
                            .isCamera(false)
                            .forResult(1004);
                }
                break;
            case R.id.quit:
                finish();
                break;
            case R.id.confirm:
                uploadImage(file);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1003 && data != null) {
            List<LocalMedia> temp = PictureSelector.obtainMultipleResult(data);
            List<String> images = new ArrayList<>();
            for (LocalMedia localMedia : temp) images.add(localMedia.getCutPath());
            compressImage(images);
            Glide.with(ChangeImageActivity.this).load(images.get(0)).into(image);
        }
        else if (requestCode == 1004 && data != null) {
            List<LocalMedia> temp = PictureSelector.obtainMultipleResult(data);
            List<String> images = new ArrayList<>();
            for (LocalMedia localMedia : temp) images.add(localMedia.getPath());
            compressImage(images);
            Glide.with(ChangeImageActivity.this).load(images.get(0)).into(image);
        }
    }

    //压缩图片
    @SuppressLint("CheckResult")
    public void compressImage(final List<String> image) {
        Flowable.just(image).observeOn(Schedulers.io())
                .map(list -> Luban.with(ChangeImageActivity.this).load(list).get())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(files -> {
                    file.clear();
                    file.add(0, files.get(0));
                    confirm.setVisibility(View.VISIBLE);
                });
    }

    //上传图片
    public void uploadImage(List<File> images) {
        model = new PersonalIndexModel();
        model.uploadImage(images).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<List<String>>())
                .subscribe(new BaseObserver<List<String>>() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        L.e("error is " + e.status + e.message);
                    }

                    @Override
                    public void onNext(List<String> list) {
                        listimage.add(0, list.get(0));
                        chanageImage(listimage.get(0));
                    }
                });
    }

    private void chanageImage(final String image) {/** 更改头像**/
        model = new PersonalIndexModel();
        model.changeImage(Sp.getString(ChangeImageActivity.this, "useraccountid"), imagetype, image)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<>())
                .subscribe(new BaseObserver() {
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        Toast.makeText(ChangeImageActivity.this, "修改失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(Object o) {
                        Toast.makeText(ChangeImageActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent();
                        if (imagetype == 1) {
                            intent.putExtra("bg_url", image);
                        } else if (imagetype == 0) {
                            intent.putExtra("portrait_url", image);
                        }
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                });
    }
}

package com.yuanyu.ceramics.center_circle.release;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.center_circle.detail.ArticleContentBean;
import com.yuanyu.ceramics.common.GlideEngine;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;
import com.yuanyu.ceramics.utils.xrichtext.RichTextEditor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReleaseArticleActivity extends BaseActivity<ReleaseArticlePresenter> implements ReleaseArticleConstract.IReleaseArticleView {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.release)
    TextView release;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.article_cover)
    ImageView articleCover;
    @BindView(R.id.article_title)
    EditText articleTitle;
    @BindView(R.id.article_content)
    RichTextEditor articleContent;
    @BindView(R.id.add_image)
    ImageView addImage;
    @BindView(R.id.bottom_tool)
    LinearLayout bottomTool;
    private List<ArticleContentBean> content;
    private String imagecover = null;
    private String cover;
    private LoadingDialog dialog;
    private List<String> image = new ArrayList<>();

    @Override
    protected int getLayout() {
        return R.layout.activity_release_article;
    }

    @Override
    protected ReleaseArticlePresenter initPresent() {
        return new ReleaseArticlePresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        dialog = new LoadingDialog(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        articleContent.setOnTouchListener((arg0, arg1) -> true);
        content = new ArrayList<>();
        articleTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() > 25) {
                    Toast.makeText(ReleaseArticleActivity.this, "字数超过限制", Toast.LENGTH_SHORT).show();
                    articleTitle.setText(editable.toString().substring(0, 25));
                    articleTitle.setSelection(25);
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1003 && data != null) {
            List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
            for(int i=0;i<images.size();i++) {
                articleContent.insertImage(images.get(i).getPath(), 320);
            }
        } else if (requestCode == 1004 && data != null) {
            List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
            imagecover = images.get(0).getPath();
            GlideApp.with(this)
                    .load(images.get(0).getPath())
                    .override(200,200)
                    .into(articleCover);
        }
    }
    //获取富文本内容。文字getText()  图片则获取路径再和封面一起上传
    private void BuildDataList() {
        List<RichTextEditor.EditData> editList = articleContent.buildEditData();
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null) {
                content.add(new ArticleContentBean(0, itemData.inputStr));
            } else if (itemData.imagePath != null) {
                image.add(itemData.imagePath);
                content.add(new ArticleContentBean(1, itemData.imagePath));
            }
        }
        boolean isEmpty = true;
        for (int i = 0; i < content.size(); i++) {
            if (content.get(i).getContent().length() > 0) {
                isEmpty = false;
                dialog.show();
                presenter.compressImages(this,image);
                break;
            }
        }
        if (isEmpty) {
            Toast.makeText(this, "内容不能为空", Toast.LENGTH_SHORT).show();
            image.clear();
            content.clear();
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
    @Override
    public void compressImagesSuccess(List<File> list) {
        presenter.uploadImage(list);
    }

    @Override
    public void compressImagesFail() {
        image.clear();
        content.clear();
        Toast.makeText(this, "上传图片失败", Toast.LENGTH_SHORT).show();
        L.e("上传图片失败");
        dialog.dismiss();
    }

    @Override
    public void uploadImageSuccess(List<String> list) {
        //把图片路径替换成服务器路径
        cover = list.get(0);
        if (list.size() > 1) {
            int temp = 1;
            for (int i = 0; i < content.size(); i++) {
                if (content.get(i).getType() == 1) {
                    content.set(i, new ArticleContentBean(1, list.get(temp)));
                    temp++;
                }
            }
        }
        presenter.releaseArticle(cover,articleTitle.getText().toString(), Sp.getInt(this, AppConstant.USER_ACCOUNT_ID),content);

    }

    @Override
    public void uploadImageFail(ExceptionHandler.ResponeThrowable e) {
        image.clear();
        content.clear();
        Toast.makeText(this, "图片上传失败", Toast.LENGTH_SHORT).show();
        L.e(e.status+" "+e.message);
        dialog.dismiss();
    }

    @Override
    public void releaseArticleSuccess(Boolean b) {
        Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        finish();
    }

    @Override
    public void releaseArticleFail(ExceptionHandler.ResponeThrowable e) {
        image.clear();
        content.clear();
        Toast.makeText(this, "发布失败", Toast.LENGTH_SHORT).show();
        L.e(e.status+" "+e.message);
        dialog.dismiss();
    }

    @OnClick({R.id.release, R.id.article_cover, R.id.add_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.release:
                if (imagecover == null) {
                    Toast.makeText(this, "请添加封面", Toast.LENGTH_SHORT).show();
                    return;
                } else if (articleTitle.getText().toString().trim().equals("")) {
                    Toast.makeText(this, "请添加标题", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    image.add(imagecover);
                    BuildDataList();
                }
                break;
            case R.id.article_cover:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .maxSelectNum(1)// 选择图片数量
                        .isCamera(true)// 是否显示拍照按钮
                        .forResult(1004);
                break;
            case R.id.add_image:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .maxSelectNum(8)// 选择图片数量
                        .isCamera(true)// 是否显示拍照按钮
                        .forResult(1003);
                break;
        }
    }
}

package com.yuanyu.ceramics.center_circle.release;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
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
import com.yuanyu.ceramics.common.DeleteDialog;
import com.yuanyu.ceramics.common.GlideEngine;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;
import com.yuanyu.ceramics.utils.xrichtext.RichTextEditor;
import static com.yuanyu.ceramics.AppConstant.BASE_URL;

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
    @BindView(R.id.back)
    TextView back;
    private List<ArticleContentBean> content;
    private String imagecover = null;
    private String cover;
    private LoadingDialog dialog;
    private List<String> image = new ArrayList<>();
    private boolean canres = false;
    private String articleId = "";
    private boolean savedraft = false;

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
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        Intent getintent=getIntent();
        articleId = getintent.getStringExtra("articleid");
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
                if (imagecover == null || articleTitle.getText().toString().trim().equals("")){
                    canres = false;
                    release.setBackgroundResource(R.drawable.disablebtnbg);
                }else {
                    canres = true;
                    release.setBackgroundResource(R.drawable.ablebtnbg);
                }
            }
        });
        if (articleId != null && articleId.length() > 0){
            getArticleDetail();
        }else {
            content.clear();
        }
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
            if (imagecover==null){}else{
                articleCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            if (imagecover == null || articleTitle.getText().toString().trim().equals("")){
                canres = false;
                release.setBackgroundResource(R.drawable.disablebtnbg);
            }else {
                canres = true;
                release.setBackgroundResource(R.drawable.ablebtnbg);
            }
        }
    }
    //获取富文本内容。文字getText()  图片则获取路径再和封面一起上传
    private void BuildDataList() {
        List<RichTextEditor.EditData> editList = articleContent.buildEditData();
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null) {
                content.add(new ArticleContentBean(0, itemData.inputStr));
            } else if (itemData.imagePath != null) {
                if (itemData.imagePath.indexOf("img/") != -1){
                    String tempstr = itemData.imagePath.substring(itemData.imagePath.indexOf("img/"));
                    image.add(tempstr);
                    content.add(new ArticleContentBean(1, tempstr));
                }else {
                    image.add(itemData.imagePath);
                    content.add(new ArticleContentBean(1, itemData.imagePath));
                }
            }
        }
        List<String> templist = new ArrayList<>();
        for (int i = 0; i < image.size(); i++) {
            if (image.get(i).startsWith("img/")){}
            else {
                templist.add(image.get(i));
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
        if (item.getItemId() == android.R.id.home) {
            finish();
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
        if (list.size() == 1){
            if (imagecover != null && imagecover.length() > 0){
                if (imagecover.startsWith("img/")){
                    cover = imagecover;
                }else {
                    cover = list.get(0);
                }
            }else {
                for (int i = 0; i < content.size(); i++) {
                    if (content.get(i).getType() == 1 && !content.get(i).getContent().startsWith("img/")) {
                        content.set(i, new ArticleContentBean(1, list.get(0)));
                        break;
                    }
                }
            }
        }
        if (list.size() > 1) {
            if (imagecover != null && imagecover.length() > 0){
                if (imagecover.startsWith("img/")){
                    cover = imagecover;
                    int temp = 0;
                    for (int i = 0; i < content.size(); i++) {
                        if (content.get(i).getType() == 1 && !content.get(i).getContent().startsWith("img/")) {
                            content.set(i, new ArticleContentBean(1, list.get(temp)));
                            temp++;
                        }
                    }
                }else {
                    cover = list.get(0);
                    int temp = 1;
                    for (int i = 0; i < content.size(); i++) {
                        if (content.get(i).getType() == 1 && !content.get(i).getContent().startsWith("img/")) {
                            content.set(i, new ArticleContentBean(1, list.get(temp)));
                            temp++;
                        }
                    }
                }
            }else {
                int temp = 0;
                for (int i = 0; i < content.size(); i++) {
                   if (content.get(i).getType() == 1 && !content.get(i).getContent().startsWith("img/")) {
                        content.set(i, new ArticleContentBean(1, list.get(temp)));
                        temp++;
                    }
                }
            }

        }
        String id = "";

        if (articleId != null && articleId.length() > 0){
            id = articleId;
        }else {
            id = "";
        }
        if (savedraft){
            presenter.saveArticle(id,cover,articleTitle.getText().toString(),Sp.getString(this,AppConstant.USER_ACCOUNT_ID),content);
        }else {
            presenter.releaseArticle(id,cover,articleTitle.getText().toString(),Sp.getString(this,AppConstant.USER_ACCOUNT_ID),content);

        }
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
        if (articleId != null && articleId.length() > 0){
            presenter.deleteArticle(Sp.getString(this, AppConstant.USER_ACCOUNT_ID),articleId);
        }else {
            finish();
        }
    }

    @Override
    public void releaseArticleFail(ExceptionHandler.ResponeThrowable e) {
        image.clear();
        content.clear();
        Toast.makeText(this, "发布失败", Toast.LENGTH_SHORT).show();
        L.e(e.status+" "+e.message);
        dialog.dismiss();
    }
    @Override
    public void saveArticleSuccess(Boolean b) {
        content.clear();
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        finish();
    }

    @Override
    public void saveArticleFail(ExceptionHandler.ResponeThrowable e) {
        content.clear();
        Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        L.e(e.status+" "+e.message);
        dialog.dismiss();
    }
    @Override
    public void getArticleSuccess(DraftsArticle beans) {
        L.e("获取成功");
        imagecover = beans.getCover();
        if (imagecover != null && imagecover.length() > 0){
            GlideApp.with(this)
                    .load(BASE_URL+imagecover)
                    .override(200,200)
                    .into(articleCover);
            articleCover.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        articleTitle.setText(beans.getTitle());
        int index=0;
        if(beans.getContent().size()>0){
            articleContent.getAllLayout().removeAllViews();
        }
        for (int i = 0; i < beans.getContent().size(); i++) {
            if (beans.getContent().get(i).getType() == 0){
                articleContent.addEditTextAtIndex(index,beans.getContent().get(i).getContent());
                index++;
            }else {
                if(i==beans.getContent().size()-1){
                    articleContent.insertImage(BASE_URL+beans.getContent().get(i).getContent(),320);
                    index++;
                }else{
                    if(i!=beans.getContent().size()-1&&beans.getContent().get(i+1).getType() == 0){
                        articleContent.addImageViewAtIndex(index,BASE_URL+beans.getContent().get(i).getContent());
                        index++;
                    }else{
                        articleContent.insertImage(BASE_URL+beans.getContent().get(i).getContent(),320);
                        index=index+2;
                    }

                }


            }
        }
    }
    @Override
    public void getArticleFail(ExceptionHandler.ResponeThrowable e) {
        Toast.makeText(this, "获取失败", Toast.LENGTH_SHORT).show();
        L.e(e.status+" "+e.message);
        finish();
    }

    @Override
    public void deleteArticleSuccess(String[] b) {
        L.e("删除成功");
        finish();
    }

    @Override
    public void deleteArticleFail(ExceptionHandler.ResponeThrowable e) {
        L.e("删除失败");
        finish();
    }

    @OnClick({R.id.release, R.id.article_cover, R.id.add_image,R.id.back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.release:
                savedraft = false;
                if (canres){
                    image.clear();
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
                }else {}
                break;
            case R.id.article_cover:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .maxSelectNum(1)// 选择图片数量
                        .isCamera(true)// 是否显示拍照按钮
                        .forResult(1004);
                if (imagecover == null || articleTitle.getText().toString().trim().equals("")){
                    canres = false;
                    release.setBackgroundResource(R.drawable.disablebtnbg);
                }else {
                    canres = true;
                    release.setBackgroundResource(R.drawable.ablebtnbg);
                }
                break;
            case R.id.add_image:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .maxSelectNum(8)// 选择图片数量
                        .isCamera(true)// 是否显示拍照按钮
                        .forResult(1003);
                break;
            case R.id.back:
                ormethos();
                break;
        }
    }
    private void savedrafts(List<String> imglist){
        DeleteDialog dialog2 = new DeleteDialog(ReleaseArticleActivity.this);
        dialog2.setTitle("保留此次编辑？");
        dialog2.setNoOnclickListener(() -> {
            dialog2.dismiss();
            finish();
        });
        dialog2.setYesOnclickListener(() -> {
            String nowid = "";
            if (articleId != null && articleId.length() > 0){
                nowid = articleId;
            }else {
                nowid = "";
            }
            dialog2.dismiss();
            dialog.show();
            List<String> templist = new ArrayList<>();
            for (int i = 0; i < imglist.size(); i++) {
                if (imglist.get(i).startsWith("img/")){}
                else {
                    templist.add(imglist.get(i));
                }
            }
            L.e("iiiii"+imglist.size());
            if (templist.size() > 0){
                presenter.compressImages(this,templist);
            }else {
                presenter.saveArticle(nowid,"",articleTitle.getText().toString(),Sp.getString(this,AppConstant.USER_ACCOUNT_ID),content);
            }

        });
        dialog2.show();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            ormethos();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }
    private void getArticleDetail(){
        presenter.getArticle(articleId);
    }
    private void ormethos(){
        savedraft = true;
        content.clear();
        if(imagecover != null && imagecover.length() > 0){
            image.add(imagecover);
        }
        List<RichTextEditor.EditData> editList = articleContent.buildEditData();
        for (RichTextEditor.EditData itemData : editList) {
            if (itemData.inputStr != null) {
                content.add(new ArticleContentBean(0, itemData.inputStr));
            } else if (itemData.imagePath != null) {
                if (itemData.imagePath.indexOf("img/") != -1){
                    String tempstr = itemData.imagePath.substring(itemData.imagePath.indexOf("img/"));
                    image.add(tempstr);
                    content.add(new ArticleContentBean(1, tempstr));
                }else {
                    image.add(itemData.imagePath);
                    content.add(new ArticleContentBean(1, itemData.imagePath));
                }

            }
        }
        boolean isEmpty = true;
        for (int i = 0; i < content.size(); i++) {
            if (content.get(i).getContent().length() > 0) {
                isEmpty = false;
                break;
            }
        }
        if (imagecover == null && articleTitle.getText().toString().trim().equals("") && isEmpty){
            finish();
        }else {
            savedrafts(image);
        }
    }
}

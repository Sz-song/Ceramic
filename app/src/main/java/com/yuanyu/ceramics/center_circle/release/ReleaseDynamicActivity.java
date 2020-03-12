package com.yuanyu.ceramics.center_circle.release;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sunhapper.spedittool.view.SpEditText;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.CantScrollGirdLayoutManager;
import com.yuanyu.ceramics.common.DynamicContentBean;
import com.yuanyu.ceramics.common.FriendBean;
import com.yuanyu.ceramics.common.GlideEngine;
import com.yuanyu.ceramics.common.ImageDisplayActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.UploadPhotoAdapter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReleaseDynamicActivity extends BaseActivity<ReleaseDynamicPresenter> implements ReleaseDynamicConstract.IReleaseDynamicView {

    private static final int REQUEST_CODE = 0x00000011;
    private static final int DISPLAY_IMAGE = 200;
    private static final int DELETE_IMAGE = 201;
    private static final int imageSize = 9;
    private static final int DYNAMIC_TYPE_CODE = 300;
    private static final int DYNAMIC_REMIND = 400;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.release)
    TextView release;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.edit_yuyouquan)
    SpEditText editYuyouquan;
    @BindView(R.id.text_num)
    TextView textNum;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.location_image)
    ImageView locationImage;
    @BindView(R.id.remind_friends)
    TextView remindFriends;
    @BindView(R.id.remind_relat)
    RelativeLayout remindRelat;
    @BindView(R.id.limit_image)
    ImageView limitImage;
    @BindView(R.id.limit_friends)
    TextView limitFriends;
    @BindView(R.id.select_people)
    TextView selectPeople;
    @BindView(R.id.limit_relat)
    RelativeLayout limitRelat;

    private ArrayList<String> mList;
    private List<FriendBean> list;
    private UploadPhotoAdapter adapter;
    private String addPic = "add_pic" + R.drawable.add_pic;
    private boolean isopen=true;
    private int textnum;
    private List<Integer> start_index=new ArrayList<>();
    private List<Integer> end_index=new ArrayList<>();
    private List<Integer> listfriends =new ArrayList<>();
    private List<String>listimages=new ArrayList<>();
    private List<DynamicContentBean> dynamicContentList =new ArrayList<>();
    private LoadingDialog dialog;

    @Override
    protected int getLayout() {
        return R.layout.activity_release_dynamic;
    }

    @Override
    protected ReleaseDynamicPresenter initPresent() {
        return new ReleaseDynamicPresenter();
    }
    @SuppressLint("RestrictedApi")
    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        title.setText("发布动态");
        dialog=new LoadingDialog(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        selectPeople.setText("所有人可见");
        textNum.setText("0/140");
        recyclerview.setLayoutManager(new CantScrollGirdLayoutManager(this, 3));
        mList = new ArrayList<>();
        list=new ArrayList<>();
        mList.add(addPic);
        adapter = new UploadPhotoAdapter(ReleaseDynamicActivity.this, mList);
        adapter.setCancelListener(position -> {
            mList.remove(position);
            if (!mList.get(mList.size() - 1).contains("add_pic")) mList.add(addPic);
            adapter.notifyDataSetChanged();
        });
        adapter.setOnItemClickListener((position, view) -> {
            if (adapter.getImages().get(position).contains("add_pic")) {
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .maxSelectNum(imageSize - adapter.getItemCount() + 1)// 选择图片数量
                        .isCamera(true)// 是否显示拍照按钮
                        .forResult(REQUEST_CODE);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent(ReleaseDynamicActivity.this, ImageDisplayActivity.class);
                    L.e("image is " + adapter.getImages().get(position));
                    intent.putExtra("image", adapter.getImages().get(position));
                    intent.putExtra("position", position);
                    startActivityForResult(intent, DISPLAY_IMAGE, ActivityOptions.makeSceneTransitionAnimation(ReleaseDynamicActivity.this, view, "displayImage").toBundle());
                } else {
                    Intent intent = new Intent(ReleaseDynamicActivity.this, ImageDisplayActivity.class);
                    L.e("image is " + adapter.getImages().get(position));
                    intent.putExtra("image", adapter.getImages().get(position));
                    intent.putExtra("position", position);
                    startActivityForResult(intent, DISPLAY_IMAGE);
                }
            }
        });
        recyclerview.setAdapter(adapter);
        editYuyouquan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                textnum = editYuyouquan.getText().length();
                textNum.setText(textnum + "/140");
                if (editable.toString().length()>139) {
                    textNum.setTextColor(getResources().getColor(R.color.blackLight));
                    Toast.makeText(ReleaseDynamicActivity.this, "字数不能超过140", Toast.LENGTH_SHORT).show();
                } else {
                    textNum.setTextColor(getResources().getColor(R.color.lightGray));
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
            if ((images.size() + adapter.getItemCount()) == imageSize + 1) {
                mList.remove(adapter.getItemCount() - 1);
                for (int i = images.size() - 1; i >= 0; i--) {
                    mList.add(0, images.get(i).getPath());
                }
                adapter.notifyDataSetChanged();
            } else {
                for (int i = images.size() - 1; i >= 0; i--) {
                    mList.add(0, images.get(i).getPath());
                }
                adapter.notifyDataSetChanged();
            }
        }
        if (requestCode == DISPLAY_IMAGE && resultCode == DELETE_IMAGE) {
            int position = data.getIntExtra("position", 999);
            if (position <= mList.size()) {
                if (mList.get(mList.size() - 1).contains("add_pic")) {
                    mList.remove(position);
                    adapter.notifyDataSetChanged();
                } else {
                    mList.remove(position);
                    mList.add(addPic);
                    adapter.notifyDataSetChanged();
                }

            }
        }
        if (requestCode == DYNAMIC_TYPE_CODE && data != null) {
            if (data.getIntExtra("dynamic_type", -1) == -1) {
                selectPeople.setText("所有人可见");
                isopen=true;
            } else if (data.getIntExtra("dynamic_type", -1) == 0) {
                selectPeople.setText("所有人可见");
                isopen=true;
            } else if (data.getIntExtra("dynamic_type", -1) == 1) {
                selectPeople.setText("仅关注的人可见");
                isopen=false;
            }
        }
        if (requestCode == DYNAMIC_REMIND && data != null) {
            list.clear();
            list.addAll((List<FriendBean>) data.getExtras().getSerializable("friend_data"));
            int wordlen=0;
            for (int i=0;i<list.size();i++){
                wordlen += list.get(i).getName().length();
            }
//            FriendBean friendBean = (FriendBean) data.getSerializableExtra("friend_data");
            if(editYuyouquan.getText().toString().length()+wordlen>139){
                Toast.makeText(this, "字数超过限制", Toast.LENGTH_SHORT).show();
            }else {
                for (int i=0;i<list.size();i++){
                    editYuyouquan.insertSpecialStr("@" + list.get(i).getName() + " ", false, list.get(i).getId(), new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)));
                }
                }
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

    @OnClick({R.id.release, R.id.remind_relat, R.id.limit_relat})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.release:
                if(editYuyouquan.getText().toString().length()>140){
                    Toast.makeText(this, "字数超过140", Toast.LENGTH_SHORT).show();
                }else if (editYuyouquan.getText().toString().trim().length()<1&&mList.size() == 1){
                    Toast.makeText(this, "内容不为空", Toast.LENGTH_SHORT).show();
                }
                else {
                    SpEditText.SpData[] spDatas = editYuyouquan.getSpDatas();
                    start_index.add(0);
                    for (int i = 0; i < spDatas.length; i++) {
                        end_index.add(spDatas[i].getStart());
                        start_index.add(spDatas[i].getEnd());
                    }
                    end_index.add(editYuyouquan.length());
                    String str = editYuyouquan.getText().toString();
                    for (int i = 0; i < spDatas.length * 2 + 1; i++) {
                        if (i % 2 == 0) {
                            dynamicContentList.add(new DynamicContentBean(str.substring(start_index.get(i / 2), end_index.get(i / 2)), 0,"0"));
                        } else {
                            dynamicContentList.add(new DynamicContentBean(str.substring(end_index.get(i / 2), start_index.get(i / 2 + 1)), 1, spDatas[i / 2].getCustomData()+""));
                            listfriends.add((int) spDatas[i / 2].getCustomData());
                        }
                    }
                    if (mList.size() == 1) {
                        dialog.show();
                        presenter.releaseDynamic(Sp.getString(this,AppConstant.USER_ACCOUNT_ID),listimages,listfriends,isopen,dynamicContentList);
                    } else if (mList.get(mList.size()-1).contains("add_pic")) {
                        for(int i=0;i<mList.size()-1;i++){listimages.add(mList.get(i));}
                        dialog.show();
                        presenter.compressImages(this,listimages);
                    } else {
                        listimages.addAll(mList);
                        dialog.show();
                        presenter.compressImages(this,listimages);
                    }
                }
                break;
            case R.id.remind_relat:
                intent = new Intent(ReleaseDynamicActivity.this, SelectFriendActivity.class);
                startActivityForResult(intent, DYNAMIC_REMIND);
                break;
            case R.id.limit_relat:
                intent = new Intent(ReleaseDynamicActivity.this, DynamicTypeActivity.class);
                startActivityForResult(intent, DYNAMIC_TYPE_CODE);
                break;
        }
    }
    @Override
    public void compressImagesSuccess(List<File> list) {
        presenter.uploadImage(list);
    }

    @Override
    public void compressImagesFail() {
        listimages.clear();
        listfriends.clear();
        dynamicContentList.clear();
        Toast.makeText(this, "上传图片失败", Toast.LENGTH_SHORT).show();
        L.e("上传图片失败");
        dialog.dismiss();
    }

    @Override
    public void uploadImageSuccess(List<String> list) {
        presenter.releaseDynamic(Sp.getString(this,AppConstant.USER_ACCOUNT_ID),list,listfriends,isopen,dynamicContentList);
    }

    @Override
    public void uploadImageFail(ExceptionHandler.ResponeThrowable e) {
        listimages.clear();
        listfriends.clear();
        dynamicContentList.clear();
        Toast.makeText(this, "图片上传失败", Toast.LENGTH_SHORT).show();
        L.e("图片上传失败");
        dialog.dismiss();
    }

    @Override
    public void releaseDynamicSuccess(Boolean b) {
        Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        finish();
    }

    @Override
    public void releaseDynamicFail(ExceptionHandler.ResponeThrowable e) {
        listimages.clear();
        listfriends.clear();
        dynamicContentList.clear();
        Toast.makeText(this, "发布失败", Toast.LENGTH_SHORT).show();
        L.e(e.message+"  "+e.status);
        dialog.dismiss();
    }

}

package com.yuanyu.ceramics.center_circle.release;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.sunhapper.spedittool.view.SpEditText;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.CantScrollGirdLayoutManager;
import com.yuanyu.ceramics.common.DeleteDialog;
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
    @BindView(R.id.back)
    TextView back;

    private ArrayList<String> mList;
    private List<FriendBean> listfri;
    private UploadPhotoAdapter adapter;
    private String addPic = "add_pic" + R.drawable.add_pic;
    private boolean isopen = true;
    private int textnum;
    private List<Integer> start_index = new ArrayList<>();
    private List<Integer> end_index = new ArrayList<>();
    private List<Integer> listfriends = new ArrayList<>();
    private List<String> listimages = new ArrayList<>();
    private List<DynamicContentBean> dynamicContentList = new ArrayList<>();
    private LoadingDialog dialog;
    private boolean canres = false;
    private String dynamicId = "";
    private boolean savedrafts = false;
    private String savestr = "";

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
        dialog = new LoadingDialog(this);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        //        如果是从草稿箱来的
        Intent getintent = getIntent();
        dynamicId = getintent.getStringExtra("dynamicid");
        recyclerview.setLayoutManager(new CantScrollGirdLayoutManager(this, 3));
        mList = new ArrayList<>();
        listfri = new ArrayList<>();
        adapter = new UploadPhotoAdapter(ReleaseDynamicActivity.this, mList);
        adapter.setCancelListener(position -> {
            mList.remove(position);
            if (!mList.get(mList.size() - 1).contains("add_pic")) mList.add(addPic);
            adapter.notifyDataSetChanged();
            if ((mList.size() == 1) && editYuyouquan.getText().toString().trim().length() < 1) {
                canres = false;
                release.setBackgroundResource(R.drawable.disablebtnbg);
            } else {
                canres = true;
                release.setBackgroundResource(R.drawable.ablebtnbg);
            }
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
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mList.size() == 1 && editable.toString().length() < 1) {
                    canres = false;

                    release.setBackgroundResource(R.drawable.disablebtnbg);
                } else {
                    canres = true;
                    release.setBackgroundResource(R.drawable.ablebtnbg);
//                    release.setTextAppearance(ReleaseDynamicActivity.this, R.style.ableRea);
                    textnum = editYuyouquan.getText().length();
                    textNum.setText(textnum + "/140");
                    if (editable.toString().length() > 139) {
                        textNum.setTextColor(getResources().getColor(R.color.blackLight));
                        Toast.makeText(ReleaseDynamicActivity.this, "字数不能超过140", Toast.LENGTH_SHORT).show();
                    } else {
                        textNum.setTextColor(getResources().getColor(R.color.lightGray));
                    }
                }

            }
        });
        if (dynamicId != null && dynamicId.length() > 0) {
            getDynamicDetail();
        } else {
            selectPeople.setText("所有人可见");
            textNum.setText("0/140");
            mList.add(addPic);
        }
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
            if ((mList.size() == 1) && editYuyouquan.getText().toString().trim().length() < 1) {
                canres = false;
                release.setBackgroundResource(R.drawable.disablebtnbg);
            } else {
                canres = true;
                release.setBackgroundResource(R.drawable.ablebtnbg);
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
            if ((mList.size() == 1) && editYuyouquan.getText().toString().trim().length() < 1) {
                canres = false;
                release.setBackgroundResource(R.drawable.disablebtnbg);
            } else {
                canres = true;
                release.setBackgroundResource(R.drawable.ablebtnbg);
            }
        }
        if (requestCode == DYNAMIC_TYPE_CODE && data != null) {
            if (data.getIntExtra("dynamic_type", -1) == -1) {
                selectPeople.setText("所有人可见");
                isopen = true;
            } else if (data.getIntExtra("dynamic_type", -1) == 0) {
                selectPeople.setText("所有人可见");
                isopen = true;
            } else if (data.getIntExtra("dynamic_type", -1) == 1) {
                selectPeople.setText("仅关注的人可见");
                isopen = false;
            }
        }
        if (requestCode == DYNAMIC_REMIND && data != null) {
            listfri.clear();
            listfri.addAll((List<FriendBean>) data.getExtras().getSerializable("friend_data"));
            int wordlen = 0;
            for (int i = 0; i < listfri.size(); i++) {
                wordlen += listfri.get(i).getName().length();
            }
//            FriendBean friendBean = (FriendBean) data.getSerializableExtra("friend_data");
            if (editYuyouquan.getText().toString().length() + wordlen > 139) {
                Toast.makeText(this, "字数超过限制", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < listfri.size(); i++) {
                    editYuyouquan.insertSpecialStr("@" + listfri.get(i).getName() + " ", false, listfri.get(i).getId(), new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)));
                }
            }
        }
    }

    @OnClick({R.id.release, R.id.remind_relat, R.id.limit_relat,R.id.back})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.release:
                savedrafts = false;
                if (canres) {
                    dynamicContentList.clear();
                    listfriends.clear();
                    if (editYuyouquan.getText().toString().length() > 140) {
                        Toast.makeText(this, "字数超过140", Toast.LENGTH_SHORT).show();
                    } else if (editYuyouquan.getText().toString().trim().length() < 1 && mList.size() == 1) {
                        Toast.makeText(this, "内容不为空", Toast.LENGTH_SHORT).show();
                    } else {
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
                                dynamicContentList.add(new DynamicContentBean(str.substring(start_index.get(i / 2), end_index.get(i / 2)), 0, "0"));
                            } else {
                                dynamicContentList.add(new DynamicContentBean(str.substring(end_index.get(i / 2), start_index.get(i / 2 + 1)), 1, spDatas[i / 2].getCustomData() + ""));
                                listfriends.add((int) spDatas[i / 2].getCustomData());
                            }
                        }
                        String draftsid = "";
                        if (dynamicId != null && dynamicId.length() > 0) {
                            draftsid = dynamicId;
                        } else {
                            draftsid = "";
                        }
                        if (mList.size() == 1) {
                            dialog.show();
                            presenter.releaseDynamic(draftsid, Sp.getString(this, AppConstant.USER_ACCOUNT_ID), listimages, listfriends, isopen, dynamicContentList);
                        } else if (mList.get(mList.size() - 1).contains("add_pic")) {
                            for (int i = 0; i < mList.size() - 1; i++) {
                                listimages.add(mList.get(i));
                            }
                            dialog.show();
                            presenter.compressImages(this, listimages);
                        } else {
                            listimages.addAll(mList);
                            dialog.show();
                            presenter.compressImages(this, listimages);
                        }
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
            case R.id.back:
                if (canres) {
                    savedrafts();
                } else {
                    finish();
                }
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
        listfri.clear();
        dynamicContentList.clear();
        Toast.makeText(this, "上传图片失败", Toast.LENGTH_SHORT).show();
        L.e("上传图片失败");
        dialog.dismiss();
    }

    @Override
    public void uploadImageSuccess(List<String> list) {
        int j = 0;
        for (int i = 0; i < listimages.size(); i++) {
            if (listimages.get(i).startsWith("img/")){

            }else {
                listimages.set(i,list.get(j));
                j++;
            }
        }

        String draftsid = "";
        if (dynamicId != null && dynamicId.length() > 0) {
            draftsid = dynamicId;
        } else {
            draftsid = "";
        }
        if (savedrafts) {
            presenter.savedraftsDynamic(draftsid,Sp.getString(this, AppConstant.USER_ACCOUNT_ID),dynamicContentList,savestr,listimages,listfri,isopen);
        }
        else{
            presenter.releaseDynamic(draftsid, Sp.getString(this, AppConstant.USER_ACCOUNT_ID), listimages, listfriends, isopen, dynamicContentList);
        }
    }

    @Override
    public void uploadImageFail(ExceptionHandler.ResponeThrowable e) {
        listimages.clear();
        listfriends.clear();
        listfri.clear();
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
        listfri.clear();
        dynamicContentList.clear();
        Toast.makeText(this, "发布失败", Toast.LENGTH_SHORT).show();
        L.e(e.message + "  " + e.status);
        dialog.dismiss();
    }
    @Override
    public void savedraftsDynamicSuccess(Boolean b) {
        Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
        finish();
    }

    @Override
    public void savedraftsDynamicFail(ExceptionHandler.ResponeThrowable e) {
        Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        L.e(e.message + "  " + e.status);
        listimages.clear();
        listfriends.clear();
        listfri.clear();
        dynamicContentList.clear();
        dialog.dismiss();
    }

    @Override
    public void getDynamicSuccess(DraftsDynamic lists) {
        L.e("获取成功");
        if (!lists.getInputStr().equals("")){
            if (lists.listcontent().size() < 1){
                editYuyouquan.setText(lists.getInputStr());
            }
        }else {
            editYuyouquan.setText("");
        }
        if (lists.isIsopen()){
            selectPeople.setText("所有人可见");
        }else {
            selectPeople.setText("仅关注的人可见");
        }
        if (lists.listcontent().size() > 0){
            editYuyouquan.setText("");
            dynamicContentList = lists.listcontent();
            canres = true;
            release.setBackgroundResource(R.drawable.ablebtnbg);
            textnum = editYuyouquan.getText().length();
            textNum.setText(textnum + "/140");
            int wordlen=0;
            for (int i=0;i<lists.listcontent().size();i++){
                wordlen += lists.listcontent().get(i).getContent().length();
            }
            if(editYuyouquan.getText().toString().length()+wordlen>139){
                Toast.makeText(this, "字数超过限制", Toast.LENGTH_SHORT).show();
            }else {
                for (int i = 0; i < lists.listcontent().size(); i++) {
                    if (lists.listcontent().get(i).getFlag() == 0) {
                        editYuyouquan.append(lists.listcontent().get(i).getContent());
                    } else {
                        int tempid = 0;
                        try {
                            tempid = Integer.valueOf(lists.listcontent().get(i).getId()).intValue();
                        }catch (NumberFormatException e){
                            e.printStackTrace();
                        }
                        editYuyouquan.insertSpecialStr("@" + lists.listcontent().get(i).getContent().substring(1) + " ", false,tempid, new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)));
                    }
                }
            }

        }else {
            textNum.setText("0/140");
        }
        if (lists.getListfriends() != null && lists.getListfriends().size() > 0){
            listfri = lists.getListfriends();
        }else {
            listfri.clear();
        }
        if (lists.getListimages().size() > 0 && lists.getListimages().size() < 9){
            for (int i = 0; i < lists.getListimages().size(); i++) {
                mList.add(lists.getListimages().get(i));
            }
            mList.add(addPic);
        }else if(lists.getListimages().size() == 9){
            for (int i = 0; i < lists.getListimages().size(); i++) {
                mList.add(lists.getListimages().get(i));
            }
        } else {
            mList.clear();
            mList.add(addPic);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getDynamicFail(ExceptionHandler.ResponeThrowable e) {
        Toast.makeText(this, "获取失败", Toast.LENGTH_SHORT).show();
        L.e(e.status+" "+e.message);
        finish();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            if (canres){
                savedrafts();
            }else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    private void savedrafts(){
        savedrafts = true;
        DeleteDialog dialog2 = new DeleteDialog(ReleaseDynamicActivity.this);
        dialog2.setTitle("保留此次编辑？");
        dialog2.setNoOnclickListener(() -> {
            finish();
        });
        dialog2.setYesOnclickListener(() -> {
            if (editYuyouquan.getText().toString().length() > 140) {
                Toast.makeText(this, "字数超过140", Toast.LENGTH_SHORT).show();
            } else if (editYuyouquan.getText().toString().trim().length() < 1 && mList.size() == 1) {
                Toast.makeText(this, "内容不为空", Toast.LENGTH_SHORT).show();
            } else {
                dynamicContentList.clear();
                listfriends.clear();
                listfri.clear();
                dialog2.dismiss();
                String nowid = "";
                if (dynamicId != null && dynamicId.length() > 0){
                    nowid = dynamicId;
                }else {
                    nowid = "";
                }
                SpEditText.SpData[] spDatas = editYuyouquan.getSpDatas();
                start_index.add(0);
                for (int i = 0; i < spDatas.length; i++) {
                    end_index.add(spDatas[i].getStart());
                    start_index.add(spDatas[i].getEnd());
                }
                end_index.add(editYuyouquan.length());
                String str = editYuyouquan.getText().toString();
                savestr = str;
                for (int i = 0; i < spDatas.length * 2 + 1; i++) {
                    if (i % 2 == 0) {
                        dynamicContentList.add(new DynamicContentBean(str.substring(start_index.get(i / 2), end_index.get(i / 2)), 0, "0"));
                    } else {
                        dynamicContentList.add(new DynamicContentBean(str.substring(end_index.get(i / 2), start_index.get(i / 2 + 1)), 1, spDatas[i / 2].getCustomData() + ""));
                        listfriends.add((int) spDatas[i / 2].getCustomData());
                    }
                }
                if (mList.size() == 1) {
                    dialog.show();
                    presenter.savedraftsDynamic(nowid,Sp.getString(this, AppConstant.USER_ACCOUNT_ID),dynamicContentList,savestr,listimages,listfri,isopen);
                } else if (mList.get(mList.size() - 1).contains("add_pic")) {
                    for (int i = 0; i < mList.size() - 1; i++) {
                        listimages.add(mList.get(i));
                    }
                    dialog.show();
                    presenter.compressImages(this, listimages);
                } else {
                    listimages.addAll(mList);
                    dialog.show();
                    presenter.compressImages(this, listimages);
                }
            }
        });
        dialog2.show();
    }
    private void getDynamicDetail(){
        presenter.getDynamic(dynamicId);
    }
}

package com.yuanyu.ceramics.seller.liveapply;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.broadcast.pull.LivePullActivity;
import com.yuanyu.ceramics.broadcast.push.LivePushActivity;
import com.yuanyu.ceramics.common.CantScrollGirdLayoutManager;
import com.yuanyu.ceramics.common.CommonDecoration;
import com.yuanyu.ceramics.common.GlideEngine;
import com.yuanyu.ceramics.common.view.CustomDatePicker;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.seller.liveapply.bean.ItemBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Md5Utils;
import com.yuanyu.ceramics.utils.Sp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class LiveApplyActivity extends BaseActivity<LiveApplyPresenter> implements LiveApplyConstract.ILiveApplyView {
    private final int REQUEST_CODE = 201;
    private final int SELECT_IMAGE = 301;
    private final int START_LIVE = 401;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.cover_image)
    RoundedImageView coverImage;
    @BindView(R.id.live_title)
    EditText liveTitle;
    @BindView(R.id.live_time)
    TextView liveTime;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.live_submit)
    TextView liveSubmit;
    @BindView(R.id.root)
    CoordinatorLayout root;

    private LiveApplyAdapter adapter;
    private List<ItemBean> list;
    private CustomDatePicker picker;
    private SimpleDateFormat sdf;
    private String now;
    private String cover = "";
    private String id = "";
    private int status;

    @Override
    protected int getLayout() {
        return R.layout.activity_live_apply;
    }

    @Override
    protected LiveApplyPresenter initPresent() {
        return new LiveApplyPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        list = new ArrayList<>();
        adapter = new LiveApplyAdapter(this, list);
        recyclerview.setAdapter(adapter);
        recyclerview.setLayoutManager(new CantScrollGirdLayoutManager(this, 2));
        recyclerview.addItemDecoration(new CommonDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, this.getResources().getDisplayMetrics())));
        adapter.setOnSelectClickListener(() -> {
            Intent intent = new Intent(this, SelectItemActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", (Serializable) list);
            intent.putExtras(bundle);
            startActivityForResult(intent, REQUEST_CODE);
        });
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        now = sdf.format(new Date());
        picker = new CustomDatePicker(this, time -> { //回调接口，获得选中的时间
            liveTime.setText(time);
        }, "2019-01-01 00:00", "2100-01-01 00:00"); //初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        picker.showSpecificTime(true); //显示时和分
        picker.setIsLoop(true); //允许循环滚动
        presenter.getLiveApplyState(Sp.getString(this, AppConstant.SHOP_ID));
    }

    @Override
    public void uploadImageSuccess(List<String> imglist) {
        String time = Md5Utils.getTimeMin(liveTime.getText().toString());
        presenter.liveApply(id, Sp.getString(this, AppConstant.USER_ACCOUNT_ID), Sp.getString(LiveApplyActivity.this, AppConstant.SHOP_ID), liveTitle.getText().toString(), imglist.get(0), time, list);
    }

    @Override
    public void uploadImageFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message + "  " + e.status);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getLiveApplyStateSuccess(LiveApplyStatusBean bean) {
        list.clear();
        status = bean.getApply_state();
        // 0:审核中;1:审核成功;2:审核失败;3:暂无申请;
        if (bean.getApply_state() == 0) {
            coverImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            cover = bean.getCover_img();
            id = bean.getId();
            GlideApp.with(LiveApplyActivity.this)
                    .load(BASE_URL + bean.getCover_img())
                    .placeholder(R.drawable.add_cover1)
                    .into(coverImage);
            title.setText("审核中");
            liveTitle.setText(bean.getTitle());
            liveTitle.setFocusable(false);
            liveTitle.setFocusableInTouchMode(false);
            liveSubmit.setVisibility(View.GONE);
            if (!bean.getStart_time().equals("")) {
//                liveTime.setText(Md5Utils.getStrMin(bean.getStart_time()));
            } else {
                liveTime.setText(now);
            }

            adapter.setCan_select(false);
            list.addAll(bean.getItem_list());
            adapter.notifyDataSetChanged();
        } else if (bean.getApply_state() == 1) {
            id = bean.getId();
            cover = bean.getCover_img();
            coverImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            GlideApp.with(LiveApplyActivity.this)
                    .load(BASE_URL + bean.getCover_img())
                    .placeholder(R.drawable.add_item)
                    .into(coverImage);
            title.setText("审核成功");
            liveSubmit.setText("进入直播间");
            liveTitle.setText(bean.getTitle());
            liveTitle.setFocusable(false);
            liveTitle.setFocusableInTouchMode(false);
            liveSubmit.setVisibility(View.VISIBLE);
            if (!bean.getStart_time().equals("")) {
//                liveTime.setText(Md5Utils.getStrMin(bean.getStart_time()));
            } else {
                liveTime.setText(now);
            }
            adapter.setCan_select(false);
            list.addAll(bean.getItem_list());
            adapter.notifyDataSetChanged();
        } else if (bean.getApply_state() == 2) {
            cover = bean.getCover_img();
            coverImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            id = bean.getId();
            GlideApp.with(LiveApplyActivity.this)
                    .load(BASE_URL + bean.getCover_img())
                    .placeholder(R.drawable.add_item)
                    .into(coverImage);
            title.setText("审核失败");
            liveSubmit.setText("重新提交");
            liveTitle.setText(bean.getTitle());
            if (!bean.getStart_time().equals("")) {
                liveTime.setText(Md5Utils.getStrMin(bean.getStart_time()));
            } else {
                liveTime.setText(now);
            }
            adapter.setCan_select(true);
            list.addAll(bean.getItem_list());
            adapter.notifyDataSetChanged();
        }
        if (bean.getApply_state() == 3) {
            title.setText("直播申请");
            coverImage.setScaleType(ImageView.ScaleType.FIT_XY);
            GlideApp.with(LiveApplyActivity.this)
                    .load(R.drawable.add_cover1)
                    .placeholder(R.drawable.add_cover1)
                    .into(coverImage);
        }
    }

    @Override
    public void getLiveApplyStateFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
        status =3;
        title.setText("直播申请");
        coverImage.setScaleType(ImageView.ScaleType.FIT_XY);
        GlideApp.with(LiveApplyActivity.this)
                .load(R.drawable.add_cover1)
                .placeholder(R.drawable.add_cover1)
                .into(coverImage);
    }

    @Override
    public void liveApplySuccess() {
        Toast.makeText(LiveApplyActivity.this, "申请成功，请耐心等待审核", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void liveApplyFail(ExceptionHandler.ResponeThrowable e) {
        L.e("error is " + e.message + e.status);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == 202) {
            list.clear();
            list.addAll((List<ItemBean>) data.getExtras().getSerializable("data"));
            adapter.notifyDataSetChanged();
        } else if (requestCode == SELECT_IMAGE && data != null) {
            List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
            String strimage;
            if(images.get(0).getCutPath()!=null&&images.get(0).getCutPath().length()>0){
                strimage=images.get(0).getCutPath();
            }else if(images.get(0).getAndroidQToPath()!=null&&images.get(0).getAndroidQToPath().length()>0){
                strimage=images.get(0).getAndroidQToPath();
            }else{
                strimage=images.get(0).getPath();
            }
            coverImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
            cover=strimage;
            Glide.with(this)
                    .load(strimage)
                    .placeholder(R.drawable.img_default)
                    .into(coverImage);
        } else if(requestCode == START_LIVE){
            finish();
        }
    }


    @OnClick({R.id.cover_image, R.id.live_submit, R.id.live_time})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cover_image:
                if (status == 3 || status == 2) {
                    PictureSelector.create(this)
                            .openGallery(PictureMimeType.ofImage())
                            .loadImageEngine(GlideEngine.createGlideEngine())
                            .maxSelectNum(1)// 选择图片数量
                            .isCamera(true)// 是否显示拍照按钮
                            .forResult(SELECT_IMAGE);
                }
                break;
            case R.id.live_submit:
                if (status == 2 || status == 3) {
                    if (cover.startsWith("img")) {
                        String time = Md5Utils.getTimeMin(liveTime.getText().toString());
                        presenter.liveApply(id, Sp.getString(LiveApplyActivity.this, "useraccountid"), Sp.getString(LiveApplyActivity.this, AppConstant.SHOP_ID), liveTitle.getText().toString(), cover, time, list);
                    } else {
                        if (cover.length() > 1) {
                            List<String> images = new ArrayList<>();
                            images.add(cover);
                            presenter.compressImages(this, images);
                        } else {
                            Toast.makeText(this, "请上传直播封面", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else if(status == 1){
                    Intent intent=new Intent(this, LivePushActivity.class);
                    intent.putExtra("live_id",id);
                    startActivity(intent);
                }
                break;
            case R.id.live_time:
                if (status == 3 || status == 2) {
                    if (liveTime.getText().toString().equals("")) picker.show(now);
                    else picker.show(liveTime.getText().toString());
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

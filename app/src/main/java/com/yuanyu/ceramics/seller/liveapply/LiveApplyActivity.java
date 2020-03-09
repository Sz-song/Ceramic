package com.yuanyu.ceramics.seller.liveapply;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.CantScrollGirdLayoutManager;
import com.yuanyu.ceramics.common.CommonDecoration;
import com.yuanyu.ceramics.common.view.CustomDatePicker;
import com.yuanyu.ceramics.seller.liveapply.bean.ItemBean;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.Sp;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

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
    @BindView(R.id.live_type)
    TextView liveType;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.live_submit)
    Button liveSubmit;
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.root)
    CoordinatorLayout root;
    private LiveApplyAdapter adapter;
    private List<ItemBean> list;
    private CustomDatePicker picker;
    private LiveApplyTypePopupWindow popupWindow;
    private SimpleDateFormat sdf;
    private String now;
    private String cover = "";
    private String id = "";
    private int status;
    private int live_type = 0;

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
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        list = new ArrayList<>();
        popupWindow = new LiveApplyTypePopupWindow(this);
        popupWindow.setPositionClickListener(position -> {
            live_type = position;
            if (position == 2) liveType.setText("美玉直播");
            else if (position == 3) liveType.setText("淘石开料");
            else if (position == 4) liveType.setText("玉说天下");
            popupWindow.dismiss();
        });
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
    public void uploadImageSuccess(List<String> list) {

    }

    @Override
    public void uploadImageFail(ExceptionHandler.ResponeThrowable e) {

    }

    @Override
    public void getLiveApplyStateSuccess(LiveApplyStatusBean bean) {

    }

    @Override
    public void getLiveApplyStateFail(ExceptionHandler.ResponeThrowable e) {

    }

    @Override
    public void liveApplySuccess() {

    }

    @Override
    public void liveApplyFail(ExceptionHandler.ResponeThrowable e) {

    }

    @Override
    public void showToast(String str) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

package com.yuanyu.ceramics.dingzhi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.FenleiTypeBean;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DingzhiPublishDetailActivity extends BaseActivity<DingzhiPublishDetailPresenter> implements DingzhiPublishDetailConstract.IDingzhiPublishDetailView {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.master_name)
    TextView masterName;
    @BindView(R.id.introduce_detail)
    EditText introduceDetail;
    @BindView(R.id.personal_use)
    TextView personalUse;
    @BindView(R.id.friend_use)
    TextView friendUse;
    @BindView(R.id.gift_use)
    TextView giftUse;
    @BindView(R.id.low_price)
    TextView lowPrice;
    @BindView(R.id.mid_price)
    TextView midPrice;
    @BindView(R.id.top_price)
    TextView topPrice;
    @BindView(R.id.fenlei)
    TextView fenlei;
    @BindView(R.id.fenlei_linear)
    LinearLayout fenleiLinear;
    @BindView(R.id.zhonglei)
    TextView zhonglei;
    @BindView(R.id.waiguan)
    TextView waiguan;
    @BindView(R.id.waiguan_linear)
    LinearLayout ticaiLinear;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.divider)
    View divider;
    @BindView(R.id.root)
    CoordinatorLayout root;
    private String master_id= ""; //指定大师id
    private String useage = "自用";
    private int priceType = 0;
    private SelectPopupWindow popup;
    private LoadingDialog dialog;
    private List<FenleiTypeBean> fenleiList, zhongleiList,waiguanlist;
    private InputMethodManager imm;

    @Override
    protected int getLayout() {
        return R.layout.activity_dingzhi_publish_detail;
    }

    @Override
    protected DingzhiPublishDetailPresenter initPresent() {
        return new DingzhiPublishDetailPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        title.setText("立即定制");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        dialog = new LoadingDialog(this);
        fenleiList = new ArrayList<>();
        zhongleiList = new ArrayList<>();
        waiguanlist = new ArrayList<>();
        presenter.initData(fenleiList, zhongleiList,waiguanlist);
        lowPrice.setActivated(true);
        personalUse.setActivated(true);
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(introduceDetail.getWindowToken(), 0);
    }

    public void initFenleiPopup(final List<FenleiTypeBean> mList, final TextView textView) {
        popup = new SelectPopupWindow(this);
        final FenleiTypeAdapter adapter = new FenleiTypeAdapter(this, mList);
        popup.setAdapter(adapter);
        popup.showAtLocation(root, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.7f;
        getWindow().setAttributes(params);
        popup.setOnDismissListener(() -> {
            WindowManager.LayoutParams params1 = getWindow().getAttributes();
            params1.alpha = 1f;
            getWindow().setAttributes(params1);
        });
        popup.reset(view -> {
            for (int i = 0; i < mList.size(); i++) {
                mList.get(i).setChoose(false);
            }
            adapter.notifyDataSetChanged();
        });
        popup.confirm(view -> {
            String temp = "";
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).isChoose()) {
                    temp = temp + mList.get(i).getType() + "、";
                }
            }
            if (temp.equals("")) textView.setText("");
            else textView.setText(temp.substring(0, temp.length() - 1));
            popup.dismiss();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == RESULT_OK) {
            if (Sp.getString(this, AppConstant.USER_ACCOUNT_ID) .equals(data.getStringExtra("id"))) {
                Toast.makeText(this, "请不要选择自己", Toast.LENGTH_SHORT).show();
            } else {
                masterName.setText(data.getStringExtra("name"));
                master_id = data.getStringExtra("id");
            }

        }
    }

    @OnClick({R.id.master_name, R.id.personal_use, R.id.friend_use, R.id.gift_use, R.id.low_price, R.id.mid_price, R.id.top_price, R.id.fenlei_linear, R.id.zhonglei_linear,R.id.submit,R.id.waiguan_linear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.master_name:
                imm.hideSoftInputFromWindow(introduceDetail.getWindowToken(), 0);
                Intent intent = new Intent(DingzhiPublishDetailActivity.this, SelectDashiActivity.class);
                startActivityForResult(intent, 1000);
                break;
            case R.id.personal_use:
                imm.hideSoftInputFromWindow(introduceDetail.getWindowToken(), 0);
                personalUse.setActivated(true);
                friendUse.setActivated(false);
                giftUse.setActivated(false);
                useage = personalUse.getText().toString();
                break;
            case R.id.friend_use:
                imm.hideSoftInputFromWindow(introduceDetail.getWindowToken(), 0);
                personalUse.setActivated(false);
                friendUse.setActivated(true);
                giftUse.setActivated(false);
                useage = friendUse.getText().toString();
                break;
            case R.id.gift_use:
                imm.hideSoftInputFromWindow(introduceDetail.getWindowToken(), 0);
                personalUse.setActivated(false);
                friendUse.setActivated(false);
                giftUse.setActivated(true);
                useage = giftUse.getText().toString();
                break;
            case R.id.low_price:
                imm.hideSoftInputFromWindow(introduceDetail.getWindowToken(), 0);
                lowPrice.setActivated(true);
                midPrice.setActivated(false);
                topPrice.setActivated(false);
                priceType = 0;
                break;
            case R.id.mid_price:
                imm.hideSoftInputFromWindow(introduceDetail.getWindowToken(), 0);
                lowPrice.setActivated(false);
                midPrice.setActivated(true);
                topPrice.setActivated(false);
                priceType = 1;
                break;
            case R.id.top_price:
                imm.hideSoftInputFromWindow(introduceDetail.getWindowToken(), 0);
                lowPrice.setActivated(false);
                midPrice.setActivated(false);
                topPrice.setActivated(true);
                priceType = 2;
                break;
            case R.id.fenlei_linear:
                imm.hideSoftInputFromWindow(introduceDetail.getWindowToken(), 0);
                initFenleiPopup(fenleiList, fenlei);
                break;
            case R.id.waiguan_linear:
                imm.hideSoftInputFromWindow(introduceDetail.getWindowToken(), 0);
                initFenleiPopup(waiguanlist, waiguan);
                break;
            case R.id.zhonglei_linear:
                imm.hideSoftInputFromWindow(introduceDetail.getWindowToken(), 0);
                initFenleiPopup(zhongleiList, zhonglei);
                break;
            case R.id.submit:
                dialog.show();
                presenter.dingzhiPublish(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), master_id , introduceDetail.getText().toString(), useage, priceType, fenlei.getText().toString(), zhonglei.getText().toString(),waiguan.getText().toString());
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

    @Override
    public void showToast(String msg) {
        dialog.dismiss();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void dingzhiPublishSuccess() {
        dialog.dismiss();
        Toast.makeText(this, "发布成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DingzhiPublishDetailActivity.this, MyDingzhiActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void dingzhiPublishFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }
}

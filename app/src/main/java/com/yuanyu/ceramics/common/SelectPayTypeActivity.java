package com.yuanyu.ceramics.common;

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
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BasePresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectPayTypeActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.alipay_img)
    ImageView alipayImg;
    @BindView(R.id.alipay_checkbox)
    SmoothCheckBox alipayCheckbox;
    @BindView(R.id.alipay)
    LinearLayout alipay;
    @BindView(R.id.wechatpay_img)
    ImageView wechatpayImg;
    @BindView(R.id.wechat_checkbox)
    SmoothCheckBox wechatCheckbox;
    @BindView(R.id.wechatpay)
    LinearLayout wechatpay;
    @BindView(R.id.largepay_checkbox)
    SmoothCheckBox largepayCheckbox;
    @BindView(R.id.largepay)
    LinearLayout largepay;
    @BindView(R.id.summit)
    TextView summit;
    @BindView(R.id.divider)
    View divider;
    private int paytype=-1;

    @Override
    protected int getLayout() {
        return R.layout.activity_pay_type;
    }

    @Override
    protected BasePresenter initPresent() {
        return null;
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        if (getIntent().getStringExtra("title") != null) {
            title.setText(getIntent().getStringExtra("title"));
        }else{
            title.setText("选择支付方式");
        }
        if(getIntent().getStringExtra("price") != null){
            price.setText("¥  "+getIntent().getStringExtra("price"));
        }else{
            price.setText("请选择支付方式");
        }
        if(getIntent().getBooleanExtra("is_large",true)){
            largepay.setVisibility(View.VISIBLE);
        }else{
            largepay.setVisibility(View.GONE);
        }
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
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

    @OnClick({R.id.alipay, R.id.wechatpay, R.id.largepay, R.id.alipay_checkbox, R.id.wechat_checkbox, R.id.largepay_checkbox,R.id.summit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alipay:
            case R.id.alipay_checkbox:
                paytype=0;
                alipayCheckbox.setChecked(true);
                wechatCheckbox.setChecked(false);
                largepayCheckbox.setChecked(false);
                break;
            case R.id.wechatpay:
            case R.id.wechat_checkbox:
                paytype=1;
                alipayCheckbox.setChecked(false);
                wechatCheckbox.setChecked(true);
                largepayCheckbox.setChecked(false);
                break;
            case R.id.largepay:
            case R.id.largepay_checkbox:
                paytype=3;
                alipayCheckbox.setChecked(false);
                wechatCheckbox.setChecked(false);
                largepayCheckbox.setChecked(true);
                break;
            case R.id.summit:
                if(paytype>=0){
                    Intent intent = new Intent();
                    intent.putExtra("paytype", paytype);
                    setResult(RESULT_OK, intent);
                    finish();
                }else{
                    Toast.makeText(this, "请选择支付方式", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}

package com.yuanyu.ceramics.large_payment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tencent.imsdk.TIMConversationType;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.chat.ChatActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class LargePaymentActivity extends BaseActivity {


    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.alipay_img)
    ImageView alipayImg;
    @BindView(R.id.alipay)
    LinearLayout alipay;
    @BindView(R.id.bankpay)
    LinearLayout bankpay;
    @BindView(R.id.call_yuanyu)
    TextView callYuanyu;
    @BindView(R.id.other_pay)
    TextView otherPay;
    @BindView(R.id.contact_kefu)
    TextView contactKefu;
    @BindView(R.id.divider)
    View divider;
    private CountDownTimer countDownTimer;
    private String CustomerService;
    private LargePaymentModel model;
    private String price;
    private long remain_time;
    private int type;//0订单支付，1定制支付;
    private List<String> list;
    @Override
    protected int getLayout() {
        return R.layout.activity_large_payment;
    }

    @Override
    protected BasePresenter initPresent() {
        return new BasePresenter() {
        };
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
        list=new ArrayList<>();
        model = new LargePaymentModel();
        if (getIntent().getStringArrayListExtra("list")!= null) {
            list.addAll(getIntent().getStringArrayListExtra("list"));
        } else {
            Toast.makeText(this, "编号错误", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (getIntent().getStringExtra("price") != null) {
            price = getIntent().getStringExtra("price");
        } else {
            price = "";
        }
        if (getIntent().getIntExtra("type", -1) >= 0) {
            type = getIntent().getIntExtra("type", -1);
        } else {
            Toast.makeText(this, "未知支付类型", Toast.LENGTH_SHORT).show();
            finish();
        }
        if(type==0){
            countDownTimer = new CountDownTimer(1790 * 1000, 1000) {
                @Override
                public void onTick(long remain) {
                    time.setText(remain / (1000 * 60) + "分" + remain % (1000 * 60) / 1000 + "秒");
                    remain_time = remain;
                }

                @Override
                public void onFinish() {
                    time.setText("订单取消,联系客服可继续购买");
                }
            };
            countDownTimer.start();
        }else{
            time.setText("请在和大师确认后支付");
        }
        getCustomerService();
    }

    @OnClick({R.id.alipay, R.id.bankpay, R.id.call_yuanyu, R.id.other_pay, R.id.contact_kefu})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.alipay:
                intent = new Intent(this, AlipayAccountActivity.class);
                intent.putExtra("price", price);
                intent.putExtra("remain_time", remain_time);
                intent.putExtra("type", type);
                intent.putStringArrayListExtra("list",(ArrayList<String>) list);
                startActivity(intent);
                break;
            case R.id.bankpay:
                intent = new Intent(this, BankAccountActivity.class);
                intent.putExtra("price", price);
                intent.putExtra("remain_time", remain_time);
                intent.putExtra("type", type);
                intent.putStringArrayListExtra("list",(ArrayList<String>) list);
                startActivity(intent);
                break;
            case R.id.call_yuanyu:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    try {
                        intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:079186728196"));
                        startActivity(intent);
                    } catch (SecurityException e) {
                        L.e(e.getMessage());
                    }
                }
                break;
            case R.id.other_pay:
                finish();
                break;
            case R.id.contact_kefu:
                if (CustomerService != null && CustomerService.length() > 0) {
//                    ChatActivity.navToChat(this, CustomerService, TIMConversationType.C2C);
                } else {
                    Toast.makeText(this, "客服繁忙，请稍后再试", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void getCustomerService() {
        model.getCustomerService(Sp.getString(this, AppConstant.USER_ACCOUNT_ID))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String>())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        CustomerService = s;
                    }

                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        L.e(e.status + "  " + e.message);
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:079186728196"));
                    startActivity(intent);
                } catch (SecurityException e) {
                    L.e(e.getMessage());
                }
            } else {
                Toast.makeText(this, "权限错误", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }


}

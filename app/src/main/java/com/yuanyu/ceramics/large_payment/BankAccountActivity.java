package com.yuanyu.ceramics.large_payment;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BankAccountActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.company_name)
    TextView companyName;
    @BindView(R.id.copy_company)
    TextView copyCompany;
    @BindView(R.id.account_bank)
    TextView accountBank;
    @BindView(R.id.copy_bank)
    TextView copyBank;
    @BindView(R.id.account_name)
    TextView accountName;
    @BindView(R.id.copy_account)
    TextView copyAccount;
    @BindView(R.id.order_num_txt)
    TextView orderNumTxt;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.call_yuanyu)
    TextView callYuanyu;
    @BindView(R.id.save_screenshot)
    TextView saveScreenshot;
    @BindView(R.id.divider)
    View divider;
    private CountDownTimer countDownTimer;
    private String totalprice;
    private long remain_time;
    private int type;
    private List<String> list;
    private LargePaymentModel model;
    private LargeOrderlistAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_account);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        list=new ArrayList<>();
        model=new LargePaymentModel();
        if (getIntent().getStringArrayListExtra("list")!= null) {
            list.addAll(getIntent().getStringArrayListExtra("list"));
        } else {
            Toast.makeText(this, "编号错误", Toast.LENGTH_SHORT).show();
            finish();
        }
        getOrderPrice(list);
        LinearLayoutManager manager=new LinearLayoutManager(this){
            @Override
            public boolean canScrollVertically() { return false;}
            @Override
            public boolean canScrollHorizontally() {return false;}
        };
        recyclerview.setLayoutManager(manager);
        adapter=new LargeOrderlistAdapter(this,list);
        recyclerview.setAdapter(adapter);
        if (getIntent().getStringExtra("price") != null) {
            totalprice = getIntent().getStringExtra("price");
        }
        if (getIntent().getIntExtra("type", -1) >= 0) {
            type = getIntent().getIntExtra("type", -1);
        } else {
            Toast.makeText(this, "未知支付类型", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (getIntent().getLongExtra("remain_time", -1) >= 0) {
            remain_time = getIntent().getLongExtra("remain_time", -1);
        } else {
            remain_time = 1790 * 1000;
        }
        if(type==0){
            orderNumTxt.setText("订单编号");
            getOrderPrice(list);
            countDownTimer = new CountDownTimer(remain_time, 1000) {
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
            orderNumTxt.setText("定制编号");
            time.setText("请在和大师确认后支付");
            price.setText("¥  "+totalprice);
        }
    }
    private void getOrderPrice(List<String> list) {
        model.getAllprice(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String>())
                .subscribe(new BaseObserver<String>() {
                    @Override
                    public void onNext(String s) {
                        price.setText("¥  "+s);
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        price.setText("¥  "+totalprice);
                    }
                });
    }


    @OnClick({R.id.copy_company, R.id.copy_bank, R.id.copy_account, R.id.call_yuanyu, R.id.save_screenshot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.copy_company:
                copy(companyName.getText().toString());
                break;
            case R.id.copy_bank:
                copy(accountBank.getText().toString());
                break;
            case R.id.copy_account:
                copy(accountName.getText().toString());
                break;
            case R.id.call_yuanyu:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                } else {
                    try {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:079186728196"));
                        startActivity(intent);
                    } catch (SecurityException e) {
                        L.e(e.getMessage());
                    }
                }
                break;
            case R.id.save_screenshot:
                if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                } else {
                    saveScreenshot();
                }
                break;
        }
    }

    private void saveScreenshot() {
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        if (bitmap != null) {
            try {// 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + System.currentTimeMillis()+".JPEG";
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os);
                os.flush();
                os.close();
                Uri uri = Uri.fromFile(file);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
                L.e("存储失败"+e.getMessage());
            }
        }
    }

    private void copy(String str) {
        try {
            //获取剪贴板管理器：
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建普通字符型ClipData
            ClipData mClipData = ClipData.newPlainText("Label", str);
            // 将ClipData内容放到系统剪贴板里。
            cm.setPrimaryClip(mClipData);
            Toast.makeText(this, "复制成功", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(this, "复制失败", Toast.LENGTH_SHORT).show();
        }
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
        }else if(requestCode==2){
            if(grantResults.length>0&&grantResults[0]== PackageManager.PERMISSION_GRANTED){
                saveScreenshot();
            }else{
                Toast.makeText(this, "权限被禁止", Toast.LENGTH_SHORT).show();
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

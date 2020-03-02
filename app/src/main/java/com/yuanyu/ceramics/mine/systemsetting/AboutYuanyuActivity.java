package com.yuanyu.ceramics.mine.systemsetting;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.NormalActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class AboutYuanyuActivity extends NormalActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.version)
    TextView version;
    @BindView(R.id.version_text)
    TextView versionText;
    @BindView(R.id.relat_version)
    RelativeLayout relatVersion;
    @BindView(R.id.text_type)
    TextView textType;
    @BindView(R.id.relat_function)
    RelativeLayout relatFunction;
    @BindView(R.id.relat_officeweb)
    RelativeLayout relatOfficeweb;
    @BindView(R.id.relat_ruler)
    RelativeLayout relatRuler;
    private SystemSettingModel model=new SystemSettingModel();
    private String versionname;
    private int versioncode;
    private boolean isUpdate=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_ceramic);
        ButterKnife.bind(this);
        title.setText("关于青花瓷App");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        initView();
    }
    private void initView() {
        getLocalVersion(this);
        version.setText(versionname);
        getNewVersion();
    }
    public  void getLocalVersion(Context ctx) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = ctx.getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(ctx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        if (packageInfo != null) {
            versionname = packageInfo.versionName;
            versioncode=packageInfo.versionCode;
        }

    }

    public void getNewVersion() {
        model.getVersion()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<String>())
                .subscribe(new BaseObserver<Integer>() {
                    @Override
                    public void onNext(Integer s) {
                        if(versioncode==s){
                            isUpdate=false;
                            versionText.setText("已经是最新版本");
                        }else{
                            isUpdate=true;
                            versionText.setText("请更新到最新版本");
                        }
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        L.e(e.status+"  "+e.message);
                    }
                });
    }

    @OnClick({R.id.relat_version, R.id.relat_function, R.id.relat_officeweb, R.id.relat_ruler})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.relat_version:
                if(isUpdate){
                    intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(BASE_URL+"apk/yuanyu.apk");
                    intent.setData(content_url);
                    startActivity(intent);
                }
                break;
            case R.id.relat_function:
//                intent = new Intent(this, NewerGuideActivity.class);
//                intent.putExtra("type",6);
//                startActivity(intent);
                break;
            case R.id.relat_officeweb:
//                intent = new Intent(this, NewerGuideActivity.class);
//                intent.putExtra("type",4);
//                startActivity(intent);
                break;
            case R.id.relat_ruler:
//                intent = new Intent(this, NewerGuideActivity.class);
//                intent.putExtra("type",5);
//                startActivity(intent);
                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}

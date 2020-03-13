package com.yuanyu.ceramics.myinfo;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyInfoActivity extends BaseActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.edit)
    TextView edit;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.introduce)
    TextView introduce;
    @BindView(R.id.gender)
    TextView gender;
    @BindView(R.id.brithday)
    TextView brithday;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    private int userid;
    private MyInfoBean infoBean;

        private MyInfoModel model=new MyInfoModel();
    @Override
    protected int getLayout() {
        return R.layout.activity_my_info;
    }

    @Override
    protected BasePresenter initPresent() {
        return new BasePresenter() {
        };
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        initView();
    }
    private void initView() {
        setSupportActionBar(toolbar);
        title.setText("个人资料");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        Intent intent = getIntent();
        userid = intent.getIntExtra("userid", -1);
        if (userid == -1) {
            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (userid+"" == Sp.getString(this, "useraccountid")) {
            edit.setVisibility(View.VISIBLE);
        }else{
            edit.setVisibility(View.GONE);
        }
        edit.setVisibility(View.VISIBLE);
        initData();
    }
    private void initData() {
        model.viewUserInfo(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<MyInfoBean>())
                .subscribe(new BaseObserver<MyInfoBean>() {
                    @Override
                    public void onNext(MyInfoBean bean) {
                        infoBean=new MyInfoBean(bean.getName(),bean.getIntroduce(),bean.getGender(),bean.getBirthday(),bean.getEmail(),bean.getLocation());
                        resumeView();
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        L.e(e.message+" "+e.status);
                    }
                });
    }
    private void resumeView() {
        name.setText(infoBean.getName());
        introduce.setText(infoBean.getIntroduce());
        gender.setText(infoBean.getGender());
        brithday.setText(infoBean.getBirthday());
        email.setText(infoBean.getEmail());
        location.setText(infoBean.getLocation());
    }
    @OnClick(R.id.edit)
    public void onViewClicked() {
        Intent intent=new Intent(MyInfoActivity.this,EditMyInfoActivity.class);
        intent.putExtra("infoBean",infoBean);
        startActivityForResult(intent,1001);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

}

package com.yuanyu.ceramics.seller.index;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShopChangeIntroduceActivity extends BaseActivity<ShopChangeIntroducePresenter> implements ShopChangeIntroduceConstract.IShopChangeIntroduceView {

    @BindView(R.id.save)
    TextView save;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.introduce)
    EditText introduce;
    private String introduce_txt="";

    @Override
    protected int getLayout() {
        return R.layout.activity_shop_change_introduce;
    }

    @Override
    protected ShopChangeIntroducePresenter initPresent() {
        return new ShopChangeIntroducePresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_shop);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        if(getIntent().getStringExtra("introduce")!=null){
            introduce_txt=getIntent().getStringExtra("introduce");
            introduce.setText(introduce_txt);
        }
        introduce.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals(introduce_txt)||editable.toString().trim().length()==0){
                    save.setTextColor(getResources().getColor(R.color.lightGray));
                }else{
                    save.setTextColor(getResources().getColor(R.color.white));
                }
                if(editable.toString().length()>50){
                    Toast.makeText(ShopChangeIntroduceActivity.this, "字数超过限制", Toast.LENGTH_SHORT).show();
                    introduce.setText(editable.toString().substring(0,50));
                }
            }
        });
    }

    @Override
    public void ShopChangeIntroduceSuccess(Boolean bool) {
        if (bool) {
            Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "修改失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void ShopChangeIntroduceFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message + "  " + e.status);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.save)
    public void onViewClicked() {
        if(introduce.getText().toString().equals(introduce_txt)){
            Toast.makeText(this, "没有修改信息", Toast.LENGTH_SHORT).show();
        }else if(introduce.getText().toString().trim().length()==0){
            Toast.makeText(this, "不能为空", Toast.LENGTH_SHORT).show();
        }else{
            presenter.ShopChangeIntroduce(Sp.getString(this, AppConstant.SHOP_ID),introduce.getText().toString().trim());
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
}

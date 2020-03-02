package com.yuanyu.ceramics.mine.systemsetting;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.NormalActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class OpinionOfYouActivity extends NormalActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.use_problem)
    Button useProblem;
    @BindView(R.id.use_improve)
    Button useImprove;
    @BindView(R.id.problem_txt)
    EditText problemTxt;
    @BindView(R.id.contact_num)
    EditText contactNum;
    @BindView(R.id.submit)
    Button submit;
    private SystemSettingModel model=new SystemSettingModel();
    private int type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opinion_of_you);
        ButterKnife.bind(this);
        title.setText("意见反馈");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        useProblem.setActivated(true);
        type=0;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @OnClick({R.id.use_problem, R.id.use_improve, R.id.submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.use_problem:
                useProblem.setActivated(true);
                useImprove.setActivated(false);
                type=0;
                break;
            case R.id.use_improve:
                useProblem.setActivated(false);
                useImprove.setActivated(true);
                type=1;
                break;
            case R.id.submit:
                if(problemTxt.getText().toString().trim().length()>0){
                    model.usersOpinion(Sp.getString(OpinionOfYouActivity.this, AppConstant.USER_ACCOUNT_ID),type,problemTxt.getText().toString(),contactNum.getText().toString())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                            .subscribe(new BaseObserver<String[]>() {
                                @Override
                                public void onNext(String[] strings) {
                                    Toast.makeText(OpinionOfYouActivity.this, "反馈成功，感谢您的建议", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                @Override
                                public void onError(ExceptionHandler.ResponeThrowable e) {
                                    L.e(e.status+"  "+e.message);
                                    Toast.makeText(OpinionOfYouActivity.this, "系统繁忙，请稍候再试", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                break;
        }
    }
}

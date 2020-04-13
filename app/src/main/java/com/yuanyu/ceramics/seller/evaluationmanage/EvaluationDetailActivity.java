package com.yuanyu.ceramics.seller.evaluationmanage;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.common.ReportActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EvaluationDetailActivity extends BaseActivity {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.reply)
    Button reply;
    @BindView(R.id.reply_txt)
    EditText replyTxt;
    @BindView(R.id.input)
    LinearLayout input;
    @BindView(R.id.root)
    CoordinatorLayout root;
    private EvaluationManageBean bean;
    private EvalutionDetailAdapter adapter;
    private EvaluationManageModel model = new EvaluationManageModel();
    View decorView;
    View contentView;
    private int screenHeight;

    @Override
    protected int getLayout() {
        return R.layout.activity_evaluation_detail;
    }

    @Override
    protected BasePresenter initPresent() {
        return new BasePresenter() {
        };
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        decorView = getWindow().getDecorView();
        screenHeight = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
        contentView = findViewById(Window.ID_ANDROID_CONTENT);
        root.setFocusable(true);
        root.setFocusableInTouchMode(true);
        root.requestFocus();
        input.setVisibility(View.GONE);
        title.setText("评论详情");

        setSupportActionBar(toolbar);
        bean = (EvaluationManageBean) getIntent().getSerializableExtra("EvaluationManageBean");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        HideBottomView(bean);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        adapter = new EvalutionDetailAdapter(this, bean);
        recyclerview.setAdapter(adapter);
    }
    public static void actionStart(Context context, EvaluationManageBean bean) {
        Intent intent = new Intent(context, EvaluationDetailActivity.class);//这里的Target.class是启动目标Activity
        intent.putExtra("EvaluationManageBean", bean);
        context.startActivity(intent);
    }

    private void HideBottomView(EvaluationManageBean bean) {
        int i = 0;
        int j = 0;
        if (bean.getEvaleation().length() > 0) {
            i++;
        }
        if (bean.getEvaleation2().length() > 0) {
            i++;
        }
        if (bean.getReply_txt2().length() > 0) {
            j++;
        }
        if (bean.getReply_txt().length() > 0) {
            j++;
        }
        if (j < i) {
            reply.setVisibility(View.VISIBLE);
        } else {
            reply.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return true;
    }


    @OnClick({R.id.report, R.id.reply})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.report:
                ReportActivity.actionStart(this, bean.getId(), 11);
                break;
            case R.id.reply:
                replyTxt.setFocusable(true);
                replyTxt.setFocusableInTouchMode(true);
                replyTxt.requestFocus();
                input.setVisibility(View.VISIBLE);
                L.e("SOFT_INPUT_STATE_ALWAYS_VISIBLE");
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm!=null){
                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);}
                decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));
                break;
        }
    }

    @OnClick(R.id.commit)
    public void onViewClicked() {
        if(replyTxt.getText().toString().length()>0){
            final int replytype;
            if(bean.getEvaleation2().length()>0){
                replytype=2;
            }else{
                replytype=1;
            }
            model.replyEvaluation(Sp.getString(EvaluationDetailActivity.this, AppConstant.SHOP_ID),bean.getId(),replytype,replyTxt.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                    .subscribe(new BaseObserver<String[]>() {
                        @Override
                        public void onNext(String[] strings) {
                            Toast.makeText(EvaluationDetailActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                            if(replytype==1){
                                bean.setReply_txt(replyTxt.getText().toString());
                            }else{
                                bean.setReply_txt2(replyTxt.getText().toString());
                            }
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            if(imm!=null){
                                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                            }
                            replyTxt.setText("");
                            input.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(ExceptionHandler.ResponeThrowable e) {
                            Toast.makeText(EvaluationDetailActivity.this, "回复失败", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            Toast.makeText(EvaluationDetailActivity.this, "回复不能为空", Toast.LENGTH_SHORT).show();
        }
    }

    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return () -> {
            Rect r = new Rect();
            decorView.getWindowVisibleDisplayFrame(r);
            if (screenHeight < r.bottom) screenHeight = r.bottom;
            int diff = screenHeight - r.bottom;
            if (diff != 0) {
                if (contentView.getPaddingBottom() != diff) {
                    contentView.setPadding(0, 0, 0, diff);
                }
            } else {
                if (contentView.getPaddingBottom() != 0) {
                    contentView.setPadding(0, 0, 0, 0);
                    input.setVisibility(View.GONE);
                    root.setFocusable(true);
                    root.setFocusableInTouchMode(true);
                    root.requestFocus();
                }
            }
        };
    }
}

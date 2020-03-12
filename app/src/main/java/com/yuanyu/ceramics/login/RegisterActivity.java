package com.yuanyu.ceramics.login;

import android.os.CountDownTimer;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.Selection;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterContract.IRegisterView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.code)
    EditText code;
    @BindView(R.id.get_code)
    TextView getCode;
    @BindView(R.id.linear_code)
    LinearLayout linearCode;
    @BindView(R.id.nickname)
    EditText nickname;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.password_again)
    EditText passwordAgain;
    @BindView(R.id.linear_pwd)
    LinearLayout linearPwd;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.agree_rule)
    TextView agreeRule;
    private CountDownTimer timer;

    @Override
    protected int getLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected RegisterPresenter initPresent() {
        return new RegisterPresenter();
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
        title.setText("用户注册");
        SpannableString spansb;
        RegisterClickableSpan clickableSpan=new RegisterClickableSpan(this);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        spansb = new SpannableString("注册即表示阅读并同意服务条款");
        spansb.setSpan(clickableSpan, 10, spansb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spansb.setSpan(colorSpan, 10, spansb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        agreeRule.setMovementMethod(LinkMovementMethod.getInstance());
        agreeRule.setText(spansb);
        //昵称输入限制
        nickname.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                String editables = nickname.getText().toString();
                String strs = stringFilter(editables);
                if (!editables.equals(strs)) {
                    nickname.setText(strs);
                    nickname.setSelection(strs.length());//设置新的光标所在位置
                }
                int mTextMaxlenght = 0;
                Editable editable = nickname.getText();
                String str = editable.toString().trim();
                int selEndIndex = Selection.getSelectionEnd(editable);
                for (int i = 0; i < str.length(); i++) {
                    char charAt = str.charAt(i);
                    if (charAt >= 32 && charAt <= 122) {
                        mTextMaxlenght++;
                    } else {
                        mTextMaxlenght += 2;
                    }
// 当最大字符大于40时，进行字段的截取，并进行提示字段的大小
                    if (mTextMaxlenght > 16) {
                        String newStr = str.substring(0, i);
                        nickname.setText(newStr);
                        editable = nickname.getText();
                        int newLen = editable.length();
                        if (selEndIndex > newLen) {
                            selEndIndex = editable.length();
                        }
                        Selection.setSelection(editable, selEndIndex);
                        Toast.makeText(RegisterActivity.this, "最大长度为16个字符或8个汉字！", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    public String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母、数字和汉字
        String regEx = "[^a-zA-Z0-9\u4E00-\u9FA5]";//正则表达式
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    @Override
    public void registerSuccess() {
        Toast.makeText(this, "注册成功，请登录", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.get_code, R.id.register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.get_code:
                presenter.getValidCode(account.getText().toString());
                timer = new CountDownTimer(60 * 1000 + 1050, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                        getCode.setEnabled(false);
                        getCode.setText(((int) millisUntilFinished / 1000 - 1) + " s");
                    }

                    @Override
                    public void onFinish() {
                        getCode.setEnabled(true);
                        getCode.setText("获取验证码");
                    }
                }.start();
                break;
            case R.id.register:
                presenter.register(account.getText().toString(), code.getText().toString(), nickname.getText().toString(), loginPassword.getText().toString(), passwordAgain.getText().toString());
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
    protected void onDestroy() {
        super.onDestroy();
        if (timer != null) timer.cancel();
    }
}

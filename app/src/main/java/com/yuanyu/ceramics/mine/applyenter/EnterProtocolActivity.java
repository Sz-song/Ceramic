package com.yuanyu.ceramics.mine.applyenter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.NormalActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.mine.dashiattestation.DashiAttestationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;


public class EnterProtocolActivity extends NormalActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.disagree)
    TextView disagree;
    @BindView(R.id.agree)
    TextView agree;
    private LoadingDialog dialog;
    private int type;//0 入驻协议 ，1 大师入驻协议

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_protocol);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        type = getIntent().getIntExtra("type", -1);
        dialog = new LoadingDialog(this);
        webview.getSettings().setJavaScriptEnabled(true);
        if (type == 0) {
            title.setText("入驻协议");
            webview.loadUrl(BASE_URL + "rule_html/merchant_entry_agreement.html");
        } else if (type == 1) {
            title.setText("大师入驻协议");
            webview.loadUrl(BASE_URL + "rule_html/master_entry_agreement.html");
        }
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                dialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.dismiss();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Toast.makeText(EnterProtocolActivity.this, "加载失败，请重试", Toast.LENGTH_SHORT).show();
                agree.setClickable(false);
            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @OnClick({R.id.disagree, R.id.agree})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.disagree:
                finish();
                break;
            case R.id.agree:
                if (type == 0) {
                    Intent intent = new Intent(this, ApplyEnterActivity.class);
                    startActivity(intent);
               }
                else if (type == 1) {
                    Intent intent = new Intent(this, DashiAttestationActivity.class);
                    startActivity(intent);
                }
                finish();
                break;
        }
    }
}

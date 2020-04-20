package com.yuanyu.ceramics.common;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.yuanyu.ceramics.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;
import static com.yuanyu.ceramics.AppConstant.WEBSITE;

public class NewerGuideActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.webview)
    WebView webview;
    private int type;//0拍卖 1 直播
    private LoadingDialog dialog;
    private boolean isAlive=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newer_guide);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        isAlive=true;
        dialog=new LoadingDialog(this);
        Intent intent=getIntent();
        type=intent.getIntExtra("type",-1);
        if(type<0){
            Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
            finish();
        }
        webview.getSettings().setJavaScriptEnabled(true);
        switch (type){
            case 0:
                title.setText("拍卖平台管理规则");
                webview.loadUrl(BASE_URL+"rule_html/auction_user.html");
                break;
            case 1:
                title.setText("直播平台管理规则");
                webview.loadUrl(BASE_URL+"rule_html/boardcast_ruler.html");
                break;
            case 2:
                title.setText("定制说明(买家版)");
                webview.loadUrl(BASE_URL+"rule_html/dingzhi_user_ruler.html");
                break;
            case 3:
                title.setText("定制说明(卖家版)");
                webview.loadUrl(BASE_URL+"rule_html/dingzhi_seller_ruler.html");
                break;
            case 4:
                title.setText("访问源玉官网");
                webview.loadUrl("http://www.jadeall.cn");
                break;
            case 5:
                title.setText("服务条款");
                webview.loadUrl(BASE_URL+"rule_html/app_rules.html");
                break;
            case 6:
                title.setText("功能介绍");
                webview.loadUrl(BASE_URL+"rule_html/function.html");
                break;
            case 7:
                title.setText("活动规则");
                webview.loadUrl(WEBSITE+"rule_html/activity_rule.html");
                break;
            case 8:
                title.setText("商家入驻协议");
                webview.loadUrl(BASE_URL + "rule_html/merchant_entry_agreement.html");
                break;
        }
        webview.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if(isAlive){
                    dialog.show();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if(isAlive){
                    dialog.dismiss();
                }
            }
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(isAlive){
                    dialog.dismiss();
                }
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isAlive=false;
    }
}

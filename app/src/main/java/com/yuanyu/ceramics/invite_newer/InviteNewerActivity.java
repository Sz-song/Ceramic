package com.yuanyu.ceramics.invite_newer;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.NewerGuideActivity;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.QRCodeUtil;
import com.yuanyu.ceramics.utils.Sp;

import java.io.File;
import java.io.FileOutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.yuanyu.ceramics.AppConstant.WEBSITE;


public class InviteNewerActivity extends BaseActivity<InviteNewerPresenter> implements InviteNewerConstract.IInviteNewerView{

    @BindView(R.id.share_wechat)
    View shareWechat;
    @BindView(R.id.share_qrcode)
    View shareQrcode;
    @BindView(R.id.bottom_txt)
    TextView bottomTxt;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.poster)
    LinearLayout poster;
    @BindView(R.id.sava_image)
    TextView savaImage;
    @BindView(R.id.image_linear)
    LinearLayout imageLinear;
    @BindView(R.id.root)
    CoordinatorLayout root;
    @BindView(R.id.qr_code)
    SquareImageView qrCode;


    @Override
    protected int getLayout() {
        return R.layout.activity_invite_newer;
    }

    @Override
    protected InviteNewerPresenter initPresent() {
        return new InviteNewerPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        toolbar.setPopupTheme(R.style.ThemeOverlay_AppCompat_Light);
        String url = WEBSITE + "h5/YuanyuMiniprogram/html/page/action/invite_register/invite_register.html?uid=" + Sp.getString(this, AppConstant.USER_ACCOUNT_ID);
        Bitmap bitmap = QRCodeUtil.createQRCodeBitmap(url, 400, 400);
        GlideApp.with(this)
                .load(bitmap)
                .into(qrCode);
        SpannableString spansb;
        InviteClickableSpan clickableSpan = new InviteClickableSpan(this);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        spansb = new SpannableString("更多详情查看活动规则");
        spansb.setSpan(clickableSpan, 6, spansb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spansb.setSpan(colorSpan, 6, spansb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        bottomTxt.setMovementMethod(LinkMovementMethod.getInstance());
        bottomTxt.setText(spansb);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.invite_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
//            case R.id.activity_rule:
//                Intent intent = new Intent(this, NewerGuideActivity.class);
//                intent.putExtra("type", 7);
//                startActivity(intent);
//                break;
        }
        return true;
    }

    @OnClick({R.id.share_wechat, R.id.share_qrcode,R.id.poster, R.id.sava_image,R.id.image_linear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.share_wechat:
                root.setDrawingCacheEnabled(true);
                root.buildDrawingCache();
                Bitmap posterImage = Bitmap.createBitmap(root.getDrawingCache());
                String miniProgrampath="/pagesA/webpage/webpage?url=https://hcode.jadeall.cn/h5/YuanyuMiniprogram/html/page/action/invite_register/invite_register.html?uid="+Sp.getString(this,AppConstant.USER_ACCOUNT_ID);
                String url = WEBSITE + "h5/YuanyuMiniprogram/html/page/action/invite_register/invite_register.html?uid=" + Sp.getString(this, AppConstant.USER_ACCOUNT_ID);
                String content = "源玉邀请新人得金豆,金豆可以换现金。";
                presenter.ShareWechat(posterImage, miniProgrampath, url, content);
                break;
            case R.id.share_qrcode:
                imageLinear.setVisibility(View.VISIBLE);
                break;
            case R.id.poster:
                L.e("poster");
                break;
            case R.id.sava_image:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                } else {
                    saveScreenshot();
                }
                break;
            case R.id.image_linear:
                imageLinear.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                saveScreenshot();
            } else {
                Toast.makeText(this, "权限被禁止", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void saveScreenshot() {
        imageLinear.setVisibility(View.VISIBLE);
        View dView = getWindow().getDecorView();
        dView.setDrawingCacheEnabled(true);
        dView.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(dView.getDrawingCache());
        if (bitmap != null) {
            try {// 获取内置SD卡路径
                String sdCardPath = Environment.getExternalStorageDirectory().getPath();
                // 图片文件路径
                String filePath = sdCardPath + File.separator + System.currentTimeMillis() + ".JPEG";
                File file = new File(filePath);
                FileOutputStream os = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, os);
                os.flush();
                os.close();
                Uri uri = Uri.fromFile(file);
                sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
                imageLinear.setVisibility(View.GONE);
            } catch (Exception e) {
                imageLinear.setVisibility(View.GONE);
                Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
                L.e("存储失败" + e.getMessage());
            }
        } else {
            imageLinear.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        if(imageLinear.getVisibility()==View.VISIBLE){
            imageLinear.setVisibility(View.GONE);
        }else{
            super.onBackPressed();
        }
    }
}

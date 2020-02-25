package com.yuanyu.ceramics.shop_index;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.Tencent;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.SharePosterPopupWindow;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.personal_index.PersonalIndexPopupWindow;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.ShareUiListener;
import com.yuanyu.ceramics.utils.Sp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;
import static com.yuanyu.ceramics.AppConstant.QQ_APP_ID;

public class ShopIndexActivity extends BaseActivity<ShopIndexPresenter> implements ShopIndexConstract.IShopIndexView {


    @BindView(R.id.background)
    ImageView background;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.shop_name)
    TextView shopName;
    @BindView(R.id.fans_num_txt)
    TextView fansNumTxt;
    @BindView(R.id.fans_num)
    TextView fansNum;
    @BindView(R.id.location)
    TextView location;
    @BindView(R.id.focus)
    TextView focus;
    @BindView(R.id.protrait)
    CircleImageView protrait;
    @BindView(R.id.top_protrait)
    CircleImageView topProtrait;
    @BindView(R.id.top_shop_name)
    TextView topShopName;
    @BindView(R.id.top_relat)
    RelativeLayout topRelat;
    @BindView(R.id.coll)
    CollapsingToolbarLayout coll;
    @BindView(R.id.tablelayout)
    XTabLayout tablelayout;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.filter)
    LinearLayout filter;
    @BindView(R.id.message)
    TextView message;
    @BindView(R.id.shop_index_content)
    CoordinatorLayout shopIndexContent;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.filter_reset)
    TextView filterReset;
    @BindView(R.id.filter_sure)
    TextView filterSure;
    @BindView(R.id.drawerlayout)
    DrawerLayout drawerlayout;
    @BindView(R.id.mid_relat)
    RelativeLayout midRelat;
    @BindView(R.id.master_studio_certification)
    ImageView masterStudioCertification;
    private ShopIndexFilterAdapter adapter;
    private String shopid;
    private String userid;
    private Bitmap bitmap;
    private SharePosterPopupWindow posterPopupWindow;
    private List<String> pathList;

    @Override
    protected int getLayout() {
        return R.layout.activity_shop_index;
    }

    @Override
    protected ShopIndexPresenter initPresent() {
        return new ShopIndexPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_rd);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        pathList=new ArrayList<>();
        Intent intent = getIntent();
        shopid = intent.getStringExtra("shopid");
        ShopIndexFragmentAdapter fragmentAdapter = new ShopIndexFragmentAdapter(getSupportFragmentManager(), shopid);
        viewpager.setAdapter(fragmentAdapter);
        tablelayout.setupWithViewPager(viewpager);
        int position = intent.getIntExtra("position", 0);
        viewpager.setCurrentItem(position);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        adapter = new ShopIndexFilterAdapter(this);
        recyclerview.setAdapter(adapter);
        presenter.getShopIndexData(shopid, Sp.getString(this, "useraccountid"));
//        appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
//            @Override
//            public void onStateChanged(AppBarLayout appBarLayout, State state) {
//                if (state == State.EXPANDED) {
//                    topRelat.setVisibility(View.GONE);
//                    midRelat.setVisibility(View.VISIBLE);
//                } else if (state == State.COLLAPSED) {
//                    topRelat.setVisibility(View.VISIBLE);
//                    midRelat.setVisibility(View.GONE);
//                }
//            }
//        });
    }

    @Override
    public void getShopIndexDataSuccess(ShopIndexBean bean) {
        GlideApp.with(ShopIndexActivity.this)
                .load(BASE_URL + bean.getPortrait())
                .optionalTransform(new BlurTransformation(20))
                .placeholder(R.drawable.img_default)
                .into(background);
        GlideApp.with(ShopIndexActivity.this)
                .load(BASE_URL + bean.getPortrait())
                .placeholder(R.drawable.img_default)
                .into(protrait);
        GlideApp.with(ShopIndexActivity.this)
                .load(BASE_URL + bean.getPortrait())
                .placeholder(R.drawable.img_default)
                .into(topProtrait);
        shopName.setText(bean.getName());
        topShopName.setText(bean.getName());
        location.setText(bean.getLocation());
        fansNum.setText(bean.getFensi_num());
        if (bean.getIscollect() == 0) {
            focus.setText("+ 关注");
        } else if (bean.getIscollect() == 1) {
            focus.setText("已关注");
        }
        if(bean.isIsmaster()){
            masterStudioCertification.setVisibility(View.VISIBLE);
        }else{
            masterStudioCertification.setVisibility(View.GONE);
        }
        userid = bean.getId();
        posterPopupWindow=new SharePosterPopupWindow(this,this,bean.getPortrait(),bean.getName(),"店铺分享","",bean.getPortrait(),BASE_URL + "YuanyuMiniprogram/html/page/shop_homepage/shop_homepage.html?id=" + shopid,"/pagesA/shop_homepage/shop_homepage?id="+ shopid);
        posterPopupWindow.setSavaImageListener((bitmap,type) -> {
            this.bitmap=bitmap;
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, type);
            } else {
                presenter.saveScreenshot(bitmap,type);
            }
        });
        posterPopupWindow.setShareShopListener(() -> presenter.shareShop(Sp.getString(this,AppConstant.SHOP_ID),Sp.getString(this,AppConstant.USER_ACCOUNT_ID)));
    }

    @Override
    public void getShopIndexDataFail(ExceptionHandler.ResponeThrowable e) {
        L.e("error is " + e.status + e.message);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void focusShopSuccess(Boolean b) {
        try {
            if (b) {
                focus.setText("已关注");
                fansNum.setText((Integer.parseInt(fansNum.getText().toString())) + 1 + "");
            } else {
                focus.setText("+ 关注");
                fansNum.setText((Integer.parseInt(fansNum.getText().toString()) - 1) + "");
            }
        } catch (Exception e) {
            L.e(e.getMessage());
        }
    }

    @Override
    public void focusShopFail(ExceptionHandler.ResponeThrowable e) {
        L.e("error is " + e.status + e.message);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveScreenshotSuccess(Uri uri, int type, String path) {
        if(type==0){
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        }else{
            pathList.add(path);
            Bundle params = new Bundle();
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,path);
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "返回源玉");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
//            params.putInt(QQShare.SHARE_TO_QQ_EXT_INT, QQShare. SHARE_TO_QQ_FLAG_QZONE_AUTO_OPEN);//分享到QQ空间
            Tencent mTencent = Tencent.createInstance(QQ_APP_ID, this);//appid 需改
            mTencent.shareToQQ(this, params, new ShareUiListener());
            presenter.shareShop(Sp.getString(this,AppConstant.SHOP_ID),Sp.getString(this,AppConstant.USER_ACCOUNT_ID));
        }
    }

    @Override
    public void saveScreenshotFail(int type) {
        if(type==0){
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void shareShopSuccess() {
        L.e("增加");
    }

    @Override
    public void shareShopFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message+"   "+e.status);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @OnClick({R.id.filter, R.id.message, R.id.focus})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.filter:
                drawerlayout.openDrawer(GravityCompat.END);
                break;
            case R.id.message:
//                if(null!=Sp.getString(this, AppConstant.MOBILE)&&Sp.getString(this,AppConstant.MOBILE).length()>8) {
//                   ChatActivity.navToChat(this, userid, TIMConversationType.C2C);
//                }else{
//                    Intent intent = new Intent(this, BindPhoneActivity.class);
//                    intent.putExtra("type",1);
//                    startActivity(intent);
//                }
                break;
            case R.id.focus:
                if(null!=Sp.getString(this,AppConstant.MOBILE)&&Sp.getString(this,AppConstant.MOBILE).length()>8){
                    presenter.focusShop(2, shopid, Sp.getString(this, "useraccountid"));
                }else{
//                    Intent intent = new Intent(this, BindPhoneActivity.class);
//                    intent.putExtra("type",1);
//                    startActivity(intent);
                }
                break;
        }
    }

    @OnClick({R.id.filter_reset, R.id.filter_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.filter_reset:
                adapter.reset();
                break;
            case R.id.filter_sure:
                adapter.search(shopid);
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.more:
                PersonalIndexPopupWindow popupWindow = new PersonalIndexPopupWindow(this, shopid+"", 10);
                popupWindow.getContentView().measure(0, 0);
                int i = (int) ((popupWindow.getContentView().getMeasuredWidth()) * 0.8);
                popupWindow.showAsDropDown(findViewById(R.id.more), -i, 0);
                popupWindow.HideDelete();
                popupWindow.HideBlacklist();//隐藏拉黑（自己不能拉黑自己）
                popupWindow.shareClickListener(view -> {
                    popupWindow.dismiss();
                    if(posterPopupWindow!=null){
                        posterPopupWindow.showAtLocation(shopIndexContent, Gravity.BOTTOM,0,0);
                    }
                });
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.more_menu_rd, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(int i=0;i<pathList.size();i++){
            try {
                File file = new File(pathList.get(i));
                if(file.exists()) {
                    if (file.delete()) {
                        L.e("删除成功");
                    } else {
                        L.e("删除失败");
                    }
                }
            }catch (Exception e){
                L.e(e.getMessage()+"删除失败");
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0||requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.saveScreenshot(bitmap,requestCode);
            } else {
                Toast.makeText(this, "读写手机存储权限被禁止", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

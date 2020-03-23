package com.yuanyu.ceramics.personal_index;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
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
import androidx.viewpager.widget.ViewPager;

import com.androidkun.xtablayout.XTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.sina.weibo.sdk.WbSdk;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.Tencent;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.SharePosterPopupWindow;
import com.yuanyu.ceramics.common.ViewImageActivity;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.myinfo.MyInfoActivity;
import com.yuanyu.ceramics.personal_index.fans_focus.FocusAndFansActicity;
import com.yuanyu.ceramics.shop_index.ShopIndexActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HelpUtils;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.ShareUiListener;
import com.yuanyu.ceramics.utils.Sp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;
import static com.yuanyu.ceramics.AppConstant.QQ_APP_ID;

public class PersonalIndexActivity extends BaseActivity<PersonalIndexPresenter> implements PersonalIndexConstract.IPersonalIndexView {


    @BindView(R.id.portrait)
    CircleImageView portrait;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.introduce)
    TextView introduce;
    @BindView(R.id.focus_num)
    TextView focusNum;
    @BindView(R.id.view)
    View view;
    @BindView(R.id.fans_num)
    TextView fansNum;
    @BindView(R.id.show_more)
    ImageView showMore;
    @BindView(R.id.ordinary_user)
    RelativeLayout ordinaryUser;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.title_image)
    CircleImageView titleImage;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.title_relat)
    RelativeLayout titleRelat;
    @BindView(R.id.coll)
    CollapsingToolbarLayout coll;
    @BindView(R.id.tabLayout)
    XTabLayout tabLayout;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.focus)
    TextView focus;
    @BindView(R.id.shop)
    TextView shop;
    @BindView(R.id.contact)
    TextView contact;
    @BindView(R.id.bottom_relat)
    LinearLayout bottomRelat;
    @BindView(R.id.root)
    CoordinatorLayout root;
    private PersonalIndexFragmentAdapter adapter;
    private String useraccountid;//查看人 id
    private String userid;//被查看人 id
    private String shopid;
    private String fans_num;
    private int usertype = 1;//判断普通用户还是大师用户 0超级管理员1普通用户 2卖家 3大师，4审核人员
    private String urlimage;
    private LoadingDialog loaddialog;
    private SharePosterPopupWindow posterPopupWindow;
    private List<String> pathList;
    private Bitmap bitmap;

    public static void actionStart(Context context, String userid) {
        Intent intent = new Intent(context, PersonalIndexActivity.class);//这里的.class是启动目标Activity
        intent.putExtra("userid", userid);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_personal_index;
    }

    @Override
    protected PersonalIndexPresenter initPresent() {
        return new PersonalIndexPresenter();
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
        pathList = new ArrayList<>();
        WbSdk.install(this,new AuthInfo(this, AppConstant.APP_KEY, AppConstant.REDIRECT_URL,AppConstant.SCOPE));
        Jzvd.WIFI_TIP_DIALOG_SHOWED = true;//关闭4G流量监控
//        appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
//            @Override
//            public void onStateChanged(AppBarLayout appBarLayout, State state) {
//                if (state == State.EXPANDED) {
//                    if (usertype == 3) {
//                        titleRelat.setVisibility(View.GONE);
//                    }
//                    titleImage.setVisibility(View.GONE);
//                    toolbarTitle.setVisibility(View.VISIBLE);
//                    title.setVisibility(View.GONE);
//                } else if (state == State.COLLAPSED) {
//                    titleRelat.setVisibility(View.VISIBLE);
//                    titleImage.setVisibility(View.VISIBLE);
//                    toolbarTitle.setVisibility(View.GONE);
//                    title.setVisibility(View.VISIBLE);
//                }
//            }
//        });
        useraccountid = Sp.getString(this, "useraccountid");
        Intent intent = getIntent();
        userid = intent.getStringExtra("userid");
        loaddialog = new LoadingDialog(this);
        loaddialog.show();
        presenter.getPersonalIndexData(useraccountid, userid);
        adapter = new PersonalIndexFragmentAdapter(getSupportFragmentManager(), userid);
        viewpager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewpager);
    }

    @Override
    public void getPersonalIndexDataSuccess(PersonalIndexBean bean) {
        urlimage = bean.getPortrait();
        usertype = bean.getType();
        shopid = bean.getShopid();
        fans_num = bean.getFans_num();
        String focus_num = bean.getFocus_num();
        GlideApp.with(PersonalIndexActivity.this)
                .load(BASE_URL + bean.getPortrait())
                .placeholder(R.drawable.logo_default)
                .into(titleImage);
        title.setText(bean.getName());
        toolbarTitle.setText(bean.getName() + "的主页");
        String userName = bean.getName();
        if (bean.getType() != 3) {
            ordinaryUser.setVisibility(View.VISIBLE);
//            masterUser.setVisibility(View.GONE);
            GlideApp.with(PersonalIndexActivity.this)
                    .load(BASE_URL + bean.getPortrait())
                    .placeholder(R.drawable.logo_default)
                    .into(portrait);
            titleImage.setVisibility(View.GONE);
            toolbarTitle.setVisibility(View.VISIBLE);
            title.setVisibility(View.GONE);
            name.setText(bean.getName());
            focusNum.setText("关注  " + HelpUtils.getReadNum(bean.getFocus_num()));
            fansNum.setText("粉丝  " + HelpUtils.getReadNum(bean.getFans_num()));
            if (bean.getContent() != null && bean.getContent().length() > 0) {
                introduce.setText(bean.getContent());
            } else {
                introduce.setText("这家伙很懒，暂无简介");
            }
            if (bean.getIsfollow() == 1) {
                focus.setText("已关注");
            } else {
                focus.setText("+ 关注");
            }
            if (userid.equals(useraccountid)) {//自己进入自己主页
                if (bean.getShopid() != null && bean.getShopid().length() > 0) {
                    bottomRelat.setVisibility(View.VISIBLE);
                    CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomRelat.getLayoutParams();
                    CoordinatorLayout.LayoutParams layoutParams1 = (CoordinatorLayout.LayoutParams) viewpager.getLayoutParams();
                    layoutParams1.setMargins(0, 0, 0, layoutParams.height);
                    viewpager.setLayoutParams(layoutParams1);
                    shop.setVisibility(View.VISIBLE);
                    contact.setVisibility(View.GONE);
                    focus.setVisibility(View.GONE);
                } else {
                    bottomRelat.setVisibility(View.GONE);
                }
            } else if (userid != useraccountid) {
                bottomRelat.setVisibility(View.VISIBLE);
                CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) bottomRelat.getLayoutParams();
                CoordinatorLayout.LayoutParams layoutParams1 = (CoordinatorLayout.LayoutParams) viewpager.getLayoutParams();
                layoutParams1.setMargins(0, 0, 0, layoutParams.height);
                viewpager.setLayoutParams(layoutParams1);
                if (bean.getShopid() != null && bean.getShopid().length() > 0) {
                    shop.setVisibility(View.VISIBLE);
                } else {
                    shop.setVisibility(View.GONE);
                }
            }
        } else {
            ordinaryUser.setVisibility(View.GONE);
//            masterUser.setVisibility(View.VISIBLE);
            bottomRelat.setVisibility(View.GONE);
            titleRelat.setVisibility(View.GONE);
//            GlideApp.with(PersonalIndexActivity.this)
//                    .load(BASE_URL + bean.getPortrait())
//                    .placeholder(R.drawable.logo_default)
//                    .into(masterPortrait);
//            if (bean.getVideo().length() > 3) {
//                videoMaster.setVisibility(View.VISIBLE);
//                bgMaster.setVisibility(View.GONE);
//                videoMaster.setUp(BASE_URL + bean.getVideo(), "", Jzvd.SCREEN_NORMAL);
//                GlideApp.with(this)
//                        .load(BASE_URL + bean.getCover())
//                        .placeholder(R.drawable.logo_default)
//                        .override(300, 300)
//                        .into(videoMaster.thumbImageView);
//            } else {
//                videoMaster.setVisibility(View.GONE);
//                bgMaster.setVisibility(View.VISIBLE);
//                GlideApp.with(this)
//                        .load(BASE_URL + urlimage)
//                        .placeholder(R.drawable.logo_default)
//                        .override(300, 300)
//                        .into(bgMaster);
//            }
//            masterName.setText(bean.getName());
//            masterFocusNum.setText(HelpUtils.getReadNum(bean.getFocus_num()));
//            masterFansNum.setText(HelpUtils.getReadNum(bean.getFans_num()));
//            masterDynamicNum.setText(HelpUtils.getReadNum(bean.getDynamic_num()));
//            if (bean.getContent() != null && bean.getContent().length() > 0) {
//                SpannableString spansb;
//                MasterIntroduceClickableSpan clickableSpan = new MasterIntroduceClickableSpan(this, userid);
//                ForegroundColorSpan colorSpan = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
//                if (bean.getContent().length() < 50) {
//                    spansb = new SpannableString(bean.getContent() + "...查看更多");
//                    spansb.setSpan(clickableSpan, bean.getContent().length(), spansb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                    spansb.setSpan(colorSpan, bean.getContent().length(), spansb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                } else {
//                    spansb = new SpannableString(bean.getContent().substring(0, 50) + "...查看更多");
//                    spansb.setSpan(clickableSpan, 50, spansb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                    spansb.setSpan(colorSpan, 50, spansb.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                }
//                masterIntroduce.setMovementMethod(LinkMovementMethod.getInstance());
//                masterIntroduce.setText(spansb);
//            } else {
//                masterIntroduce.setText("这家伙很懒，暂无简介");
//            }
//            if (bean.getIsfollow() == 1) {
//                focusMaster.setText("已关注");
//            } else {
//                focusMaster.setText("+ 关注");
//            }
//            if (userid.equals(useraccountid)) {//自己进入自己主页
//                focusMaster.setVisibility(View.GONE);
//                contactMaster.setVisibility(View.GONE);
//            } else {
//                focusMaster.setVisibility(View.VISIBLE);
//                contactMaster.setVisibility(View.VISIBLE);
//            }
//            if (bean.getShopid() != null && bean.getShopid().length() > 0) {
//                enterMasterShop.setVisibility(View.VISIBLE);
//            } else {
//                enterMasterShop.setVisibility(View.GONE);
//            }
        }
//        posterPopupWindow = new SharePosterPopupWindow(this, this, bean.getPortrait(), bean.getName(), "源玉主页", bean.getContent(), bean.getPortrait(), BASE_URL + "YuanyuMiniprogram/html/page/masterpage/masterpage.html?id=" + userid, "/pagesA/masterpage/masterpage?id=" + userid);
//        posterPopupWindow.setSavaImageListener((bitmap, type) -> {
//            this.bitmap = bitmap;
//            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, type);
//            } else {
//                presenter.saveScreenshot(bitmap, type);
//            }
//        });

        loaddialog.dismiss();
    }

    @Override
    public void getPersonalIndexDataFail(ExceptionHandler.ResponeThrowable e) {
        L.e("response" + e.message + e.status);
        loaddialog.dismiss();
        Toast.makeText(PersonalIndexActivity.this, "获取数据失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void focusSuccess(Boolean b) {
        try {
            if (b) {
                Toast.makeText(PersonalIndexActivity.this, "关注成功", Toast.LENGTH_SHORT).show();
                focus.setText("已关注");
//                focusMaster.setText("已关注");
                fans_num = (Integer.parseInt(fans_num) + 1) + "";
            } else {
                Toast.makeText(PersonalIndexActivity.this, "取关成功", Toast.LENGTH_SHORT).show();
                fans_num = (Integer.parseInt(fans_num) - 1) + "";
                focus.setText("+ 关注");
//                focusMaster.setText("+ 关注");
            }
            fansNum.setText("粉丝  " + HelpUtils.getReadNum(fans_num));
//            masterFansNum.setText(HelpUtils.getReadNum(fans_num));
        } catch (Exception e) {
            L.e(e.getMessage());
        }
    }

    @Override
    public void focusFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.status + " " + e.message);
        Toast.makeText(PersonalIndexActivity.this, e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addBlacklistSuccess(Boolean b) {
        if (b) {
            Toast.makeText(PersonalIndexActivity.this, "成功拉黑", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(PersonalIndexActivity.this, "该用户已被拉黑", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addBlacklistFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.status + " " + e.message);
        Toast.makeText(PersonalIndexActivity.this, e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void saveScreenshotSuccess(Uri uri, int type, String path) {
        if (type == 0) {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        } else {
            pathList.add(path);
            Bundle params = new Bundle();
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL, path);
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "返回源玉");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
            Tencent mTencent = Tencent.createInstance(QQ_APP_ID, this);
            mTencent.shareToQQ(this, params, new ShareUiListener());
        }
    }

    @Override
    public void saveScreenshotFail(int type) {
        if (type == 0) {
            Toast.makeText(this, "保存失败", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "分享失败", Toast.LENGTH_SHORT).show();
        }
    }

//    @OnClick({R.id.focus_num, R.id.fans_num, R.id.portrait, R.id.show_more, R.id.focus, R.id.contact, R.id.shop, R.id.master_fans, R.id.master_focus, R.id.focus_master, R.id.contact_master, R.id.enter_master_shop})
@OnClick({R.id.show_more,R.id.focus_num,R.id.portrait})
public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.focus_num:
            case R.id.master_focus:
                intent = new Intent(PersonalIndexActivity.this, FocusAndFansActicity.class);
                intent.putExtra("userid", userid);
                intent.putExtra("position", 1);
                startActivity(intent);
                break;
//            case R.id.fans_num:
//            case R.id.master_fans:
////                intent = new Intent(PersonalIndexActivity.this, FocusAndFansActicity.class);
////                intent.putExtra("userid", userid);
////                intent.putExtra("position", 2);
////                startActivity(intent);
//                break;
            case R.id.portrait://更改头像
                if (userid.equals(useraccountid)) {
                    intent = new Intent(PersonalIndexActivity.this, ChangeImageActivity.class);
                    intent.putExtra("imagetype", 0);
                    intent.putExtra("url", urlimage);
                    startActivityForResult(intent, 1002);
                } else {
                    ArrayList imagelist = new ArrayList();
                    imagelist.add(urlimage);
                    ViewImageActivity.actionStart(PersonalIndexActivity.this, 0, imagelist);
                }
                break;
            case R.id.show_more://编辑资料
                intent = new Intent(PersonalIndexActivity.this, MyInfoActivity.class);
                L.e("-----------"+userid);
                intent.putExtra("userid", Integer.parseInt(userid));
                startActivityForResult(intent, 1003);
                break;
//            case R.id.focus://关注此人
//            case R.id.focus_master:
//                if (null != Sp.getString(this, AppConstant.MOBILE) && Sp.getString(this, AppConstant.MOBILE).length() > 8) {
//                    presenter.focus(useraccountid, userid);
//                } else {
//                    Toast.makeText(this, "请先绑定手机号", Toast.LENGTH_SHORT).show();
////                    intent = new Intent(this, BindPhoneActivity.class);
////                    intent.putExtra("type",1);
////                    startActivity(intent);
//                }
//                break;
//            case R.id.contact: //私信此人
//            case R.id.contact_master:
////                if(null!=Sp.getString(this,AppConstant.MOBILE)&&Sp.getString(this,AppConstant.MOBILE).length()>8) {
////                    ChatActivity.navToChat(this, userid + "", TIMConversationType.C2C);
////                }else{
////                    intent = new Intent(this, BindPhoneActivity.class);
////                    intent.putExtra("type",1);
////                    startActivity(intent);
////                }
//                break;
//            case R.id.shop://进入店铺
//                if (null != shopid && shopid.length() > 0) {
//                    intent = new Intent(PersonalIndexActivity.this, ShopIndexActivity.class);
//                    intent.putExtra("shopid", shopid + "");
//                    startActivity(intent);
//                } else {
//                    Toast.makeText(this, "该用户的店铺尚未开张", Toast.LENGTH_SHORT).show();
//                }
//                break;
//            case R.id.enter_master_shop:
//                intent = new Intent(PersonalIndexActivity.this, ShopIndexActivity.class);
//                intent.putExtra("shopid", shopid + "");
//                startActivity(intent);
//                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.more:
                final PersonalIndexPopupWindow popupWindow = new PersonalIndexPopupWindow(this, userid + "", 5);
                popupWindow.getContentView().measure(0, 0);
                final int i = (int) ((popupWindow.getContentView().getMeasuredWidth()) * 0.8);
                L.e("width is " + i);
                popupWindow.showAsDropDown(findViewById(R.id.more), -i, 0);
                popupWindow.HideDelete();
                if (userid.equals(useraccountid)) {  //自己进入自己主页
                    popupWindow.HideBlacklist();//隐藏拉黑（自己不能拉黑自己）
                }
                popupWindow.setBlacklist(view -> {
                    presenter.addBlacklist(useraccountid, userid);
                    popupWindow.dismiss();
                });
                popupWindow.shareClickListener(view -> {
                    popupWindow.dismiss();
                    if (posterPopupWindow != null) {
                        posterPopupWindow.showAtLocation(root, Gravity.BOTTOM, 0, 0);
                    }
                });
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1002) {//更改头像
                urlimage = data.getStringExtra("portrait_url");
                GlideApp.with(PersonalIndexActivity.this)
                        .load(BASE_URL + urlimage)
                        .placeholder(R.drawable.logo_default)
                        .into(titleImage);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for (int i = 0; i < pathList.size(); i++) {
            try {
                File file = new File(pathList.get(i));
                if (file.exists()) {
                    if (file.delete()) {
                        L.e("删除成功");
                    } else {
                        L.e("删除失败");
                    }
                }
            } catch (Exception e) {
                L.e(e.getMessage() + "删除失败");
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 0 || requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                presenter.saveScreenshot(bitmap, requestCode);
            } else {
                Toast.makeText(this, "读写手机存储权限被禁止,请在设置中同意权限", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

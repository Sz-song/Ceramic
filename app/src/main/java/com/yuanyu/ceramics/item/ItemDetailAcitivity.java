package com.yuanyu.ceramics.item;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.connect.share.QQShare;
import com.tencent.tauth.Tencent;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.cart.GoodsBean;
import com.yuanyu.ceramics.common.AppBarStateChangeListener;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.SharePosterPopupWindow;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.personal_index.PersonalIndexPopupWindow;
import com.yuanyu.ceramics.shop_index.ShopIndexActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.Jzvd;

public class ItemDetailAcitivity extends BaseActivity<ItemDetailPresenter> implements ItemDetailConstract.IItemDetailView {

    @BindView(R.id.viewpager)
    ViewPager viewpager;
    @BindView(R.id.img_position)
    TextView imgPosition;
    @BindView(R.id.img_num)
    TextView imgNum;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.top_image)
    RoundedImageView topImage;
    @BindView(R.id.top_price)
    TextView topPrice;
    @BindView(R.id.relativebtn)
    RelativeLayout relativebtn;
    @BindView(R.id.coll)
    CollapsingToolbarLayout coll;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.fabtn)
    FloatingActionButton fabtn;
    @BindView(R.id.shoucang_img)
    ImageView shoucangImg;
    @BindView(R.id.shoucang)
    LinearLayout shoucang;
    @BindView(R.id.sixin)
    LinearLayout sixin;
    @BindView(R.id.dianpu)
    LinearLayout dianpu;
    @BindView(R.id.add_cart)
    TextView addCart;
    @BindView(R.id.buy_fast)
    TextView buyFast;
    @BindView(R.id.root)
    CoordinatorLayout root;

    private ItemDetailAdapter itemDetailAdapter;
    private GridLayoutManager gridLayoutManager;
    private List<AdsCellBean> adsCellBeanList = new ArrayList<>();
    private LoadingDialog loadingDialog;
    private String itemid;
    private String price_qiugou = "";
    private String qiugou_id;
    private int page = 0;
    private boolean canload = true;
    private int type;
    private int status = 0;//货物状态
    private ItemDetailBean bean;
    private List<String> pathList;
    private SharePosterPopupWindow posterPopupWindow;
    private Bitmap bitmap;

    public static void actionStart(Context context, String id) {
        Intent intent = new Intent(context, ItemDetailAcitivity.class);//这里的Target.class是启动目标Activity
        intent.putExtra("id", id);
        intent.putExtra("type", 3);
        context.startActivity(intent);
    }

    public static void actionStart(Context context, String id, String price_qiugou, String qiugou_id) {
        Intent intent = new Intent(context, ItemDetailAcitivity.class);//这里的Target.class是启动目标Activity
        intent.putExtra("id", id);
        intent.putExtra("price_qiugou", price_qiugou);
        intent.putExtra("qiugou_id", qiugou_id);
        intent.putExtra("type", 4);
        L.e(qiugou_id);
        context.startActivity(intent);
    }
    @Override
    protected int getLayout() {
        return R.layout.activity_item_detail;
    }

    @Override
    protected ItemDetailPresenter initPresent() {
        return new ItemDetailPresenter();
    }
    @SuppressLint("RestrictedApi")
    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        loadingDialog = new LoadingDialog(this);
        loadingDialog.show();
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_rd);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        pathList=new ArrayList<>();
        bean = new ItemDetailBean();
        Intent intent = getIntent();
        itemid = intent.getStringExtra("id");
        type = intent.getIntExtra("type", 3);
        if (null != intent.getStringExtra("price_qiugou") && type == 4) {
            price_qiugou = intent.getStringExtra("price_qiugou");
            qiugou_id = intent.getStringExtra("qiugou_id");
        }
        relativebtn.setVisibility(View.GONE);
        fabtn.setVisibility(View.GONE);
        presenter.getItemDetail(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), itemid);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.more:
                PersonalIndexPopupWindow popupWindow = new PersonalIndexPopupWindow(this, itemid, 7);
                popupWindow.getContentView().measure(0, 0);
                int i = (int) ((popupWindow.getContentView().getMeasuredWidth()) * 0.8);
                popupWindow.showAsDropDown(findViewById(R.id.more), -i, 0);
                popupWindow.HideDelete();
                popupWindow.HideBlacklist();
                popupWindow.shareClickListener(view -> {
                    popupWindow.dismiss();
                    if(posterPopupWindow!=null){
                        posterPopupWindow.showAtLocation(root, Gravity.BOTTOM,0,0);
                    }
                });
                break;
            default:
                break;
        }
        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.auction_detail_menu, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }

    @Override
    public void onBackPressed() {
        if (Jzvd.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    public void getItemDetailSuccess(ItemDetailBean itemDetailBean) {
        bean.setbean(itemDetailBean);
        //给广告部分添加边距
        if(bean.getItembean().getIntroducelist() != null){
            recyclerview.addItemDecoration(new CheckOrderDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8, ItemDetailAcitivity.this.getResources().getDisplayMetrics()), bean.getItembean().getIntroducelist().size() + 2));
        }else {
            recyclerview.addItemDecoration(new CheckOrderDecoration((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 8, ItemDetailAcitivity.this.getResources().getDisplayMetrics()), 2));
        }
        canload = false;
        presenter.loadMoreAds(page);
        try {
            if (price_qiugou.trim().length() > 0 && type == 4 && Float.parseFloat(price_qiugou) < bean.getItembean().getGoodsprice()) {
                bean.getItembean().setGoodsprice(Float.parseFloat(price_qiugou));
                addCart.setVisibility(View.GONE);
            }
            if (bean.getItembean().isIscollected()) {
                shoucangImg.setBackgroundResource(R.drawable.collectedblue);
            }
            if (Integer.parseInt(bean.getStore_num()) < 1) {
                addCart.setVisibility(View.GONE);
                buyFast.setText("已售罄");
                buyFast.setTextColor(this.getResources().getColor(R.color.white));
                buyFast.setBackgroundResource(R.color.background_gray);
                status = 1;
            }
        } catch (Exception e) {
            L.e(e.getMessage());
        }
        if (bean.getItembean().getStatus() != 1) {
            addCart.setVisibility(View.GONE);
            buyFast.setText("已下架");
            buyFast.setTextColor(this.getResources().getColor(R.color.white));
            buyFast.setBackgroundResource(R.color.background_gray);
            status = 2;
        }
        gridLayoutManager = new GridLayoutManager(this, 2);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position <= bean.getItembean().getIntroducelist().size() + 1) {
                    return 2;
                } else {
                    return 1;
                }
            }
        });
        topPrice.setText("¥" + bean.getItembean().getGoodsprice());
//        GlideApp.with(this)
//                .load(AppConstant.BASE_URL + bean.getItembean().getGoodslist().get(0))
//                .override(100, 100)
//                .placeholder(R.drawable.img_default)
//                .into(topImage);
        recyclerview.setLayoutManager(gridLayoutManager);
        itemDetailAdapter = new ItemDetailAdapter(this, bean, adsCellBeanList);
        recyclerview.setAdapter(itemDetailAdapter);
        itemDetailAdapter.setOnShareClickListener(() -> {
            if(posterPopupWindow!=null){
                posterPopupWindow.showAtLocation(root,Gravity.BOTTOM,0,0);
            }
        });
        itemDetailAdapter.setOnFocusMasterListener(() -> {
            canload = false;
            presenter.focus(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), bean.getMaster_id());
        });
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
                int lastPosition = -1;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    if (layoutManager instanceof GridLayoutManager) {
                        lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof LinearLayoutManager) {
                        lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                        int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                        ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                    }
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1 && canload) {
                        canload = false;
                        presenter.loadMoreAds(page);
                    }
                }
            }
        });
        ViewPagerAdapter adapter = new ViewPagerAdapter(this, bean.getItembean().getGoodslist(), bean.getVideo(), bean.getCover());
        viewpager.setAdapter(adapter);
        imgPosition.setText("1");
        if (bean.getVideo().trim().length() > 0) {
            imgNum.setText("/" + (bean.getItembean().getGoodslist().size() + 1));
        } else {
            imgNum.setText("/" + bean.getItembean().getGoodslist().size());
        }
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                int i = position + 1;
                imgPosition.setText("" + i);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
        appbar.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.EXPANDED) {
                    relativebtn.setVisibility(View.GONE);
                    fabtn.setVisibility(View.GONE);//展开状态
                } else if (state == State.COLLAPSED) {
                    relativebtn.setVisibility(View.VISIBLE);
                    GlideApp.with(ItemDetailAcitivity.this)
                            .load(R.drawable.back_top)
                            .override(fabtn.getWidth(), fabtn.getHeight())
                            .into(fabtn);
                    fabtn.setVisibility(View.VISIBLE);
                } else {
                    relativebtn.setVisibility(View.GONE);
                    fabtn.setVisibility(View.GONE); //中间状态
                }
            }
        });
        posterPopupWindow=new SharePosterPopupWindow(this,this,itemDetailBean.getStorebean().getStudioheadimg(),itemDetailBean.getStorebean().getStorename(),"商品分享",itemDetailBean.getItembean().getGoodsname(),bean.getItembean().getGoodslist().get(0),"YuanyuMiniprogram/html/page/commodityDetail/commodityDetail.html?id=" + itemid,"/pagesA/commodity_detail/commodity_detail?id="+ itemid);
        posterPopupWindow.setSavaImageListener((bitmap,type) -> {
            this.bitmap=bitmap;
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, type);
            } else {
                presenter.saveScreenshot(bitmap,type);
            }
        });
        loadingDialog.dismiss();
    }

    @Override
    public void getItemDetailFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.status + "  " + e.message);
        Toast.makeText(ItemDetailAcitivity.this, e.message, Toast.LENGTH_SHORT).show();
        loadingDialog.dismiss();
    }

    @Override
    public void collectItemSuccess(Boolean b) {
        if (b) {
            Toast.makeText(this, "收藏成功", Toast.LENGTH_SHORT).show();
            shoucangImg.setBackgroundResource(R.drawable.collectedblue);
        } else {
            Toast.makeText(this, "取消收藏成功", Toast.LENGTH_SHORT).show();
            shoucangImg.setBackgroundResource(R.drawable.shoucang);
        }
    }

    @Override
    public void collectItemFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.status + e.message);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addCartSuccess() {
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void addCartFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.status + e.message);
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loadMoreAdsSuccess(List<AdsCellBean> adsCellBeanList) {
        canload = true;
        adsCellBeanList.addAll(adsCellBeanList);
        page++;
        itemDetailAdapter.notifyItemRangeInserted(bean.getItembean().getIntroducelist().size() + adsCellBeanList.size() + 2 - adsCellBeanList.size(), adsCellBeanList.size());
    }

    @Override
    public void loadMoreAdsFail(ExceptionHandler.ResponeThrowable e) {
        canload = true;
        L.e(e.status + e.message);
    }

    @Override
    public void focusSuccess(Boolean b) {
        bean.setMaster_focus(b);
        if (b) {
            Toast.makeText(this, "关注成功", Toast.LENGTH_SHORT).show();
        }
        itemDetailAdapter.notifyItemChanged(0);
        canload = true;
    }

    @Override
    public void focusFail(ExceptionHandler.ResponeThrowable e) {
        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show();
        L.e(e.status + e.message);
        canload = true;
    }

    @Override
    public void saveScreenshotSuccess(Uri uri, int type, String filePath) {
        if(type==0){
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show();
        }else{
            pathList.add(filePath);
            Bundle params = new Bundle();
            params.putString(QQShare.SHARE_TO_QQ_IMAGE_LOCAL_URL,filePath);
            params.putString(QQShare.SHARE_TO_QQ_APP_NAME, "返回源玉");
            params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_IMAGE);
//            Tencent mTencent = Tencent.createInstance(QQ_APP_ID, this);
//            mTencent.shareToQQ(this, params, new ShareUiListener());
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
    @OnClick({R.id.fabtn, R.id.shoucang, R.id.sixin, R.id.dianpu, R.id.add_cart, R.id.buy_fast})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.fabtn:
                if (recyclerview.getScrollState() != 0) {
                    recyclerview.stopScroll();
                }
                ((GridLayoutManager) recyclerview.getLayoutManager()).scrollToPositionWithOffset(0, 0);
                appbar.setExpanded(true);
                break;
            case R.id.shoucang:
                if(null!=Sp.getString(this,AppConstant.MOBILE)&&Sp.getString(this,AppConstant.MOBILE).length()>8) {
                    presenter.collectItem(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), itemid, bean.getStorebean().getShop_id());
                }else{
//                    intent = new Intent(this, BindPhoneActivity.class);
//                    intent.putExtra("type",1);
//                    startActivity(intent);
                }
                break;
            case R.id.sixin:
//                if(null!=Sp.getString(this,AppConstant.MOBILE)&&Sp.getString(this,AppConstant.MOBILE).length()>8) {
//                    ChatActivity.navToChat(this, bean.getItembean().getSowaccountid(), TIMConversationType.C2C);
//                }else{
//                    intent = new Intent(this, BindPhoneActivity.class);
//                    intent.putExtra("type",1);
//                    startActivity(intent);
//                }
                break;
            case R.id.dianpu:
                intent = new Intent(this, ShopIndexActivity.class);
                intent.putExtra("shopid", bean.getStorebean().getShop_id() + "");
                startActivity(intent);
                break;
            case R.id.add_cart:
                if(null!=Sp.getString(this,AppConstant.MOBILE)&&Sp.getString(this,AppConstant.MOBILE).length()>8) {
                    if (Sp.getString(this, AppConstant.SHOP_ID).trim().equals(bean.getStorebean().getShop_id() + "")) {
                        Toast.makeText(this, "此商品为您店里的商品", Toast.LENGTH_SHORT).show();
                    } else {
                        presenter.addCart(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), itemid);
                    }
                }else{
//                    intent = new Intent(this, BindPhoneActivity.class);
//                    intent.putExtra("type",1);
//                    startActivity(intent);
                }
                break;
            case R.id.buy_fast:
                if(null!=Sp.getString(this,AppConstant.MOBILE)&&Sp.getString(this,AppConstant.MOBILE).length()>8) {

                    if (Sp.getString(this, AppConstant.SHOP_ID).trim().equals(bean.getStorebean().getShop_id() + "")) {
                        Toast.makeText(this, "此商品为您店里的商品", Toast.LENGTH_SHORT).show();
                    } else {
                        if (status == 0) {
                            List<GoodsBean> payList = new ArrayList<>();
                            GoodsBean goodsBean = new GoodsBean(itemid, bean.getStorebean().getStudioheadimg(), bean.getStorebean().getStorename(), bean.getStorebean().getShop_id(),
                                    bean.getItembean().getGoodslist().get(0), bean.getItembean().getGoodsname(), "", bean.getItembean().getGoodsprice(), true, true, true);
                            payList.add(goodsBean);
//                            intent = new Intent(this, CheckOrderActivity.class);
//                            intent.putExtra("totalPrice", bean.getItembean().getGoodsprice());
//                            intent.putExtra("listSize", payList.size());
//                            intent.putExtra("type", type);
//                            intent.putExtra("tag", 0);
//                            intent.putExtra("qiugou_id", qiugou_id);
//                            for (int i = 0; i < payList.size(); i++) {
//                                intent.putExtra("payList" + i, payList.get(i));
//                            }
//                            finish();
//                            startActivityForResult(intent, 3);
                        } else if (status == 1) {
                            Toast.makeText(this, "该商品已售罄", Toast.LENGTH_SHORT).show();
                        } else if (status == 2) {
                            Toast.makeText(this, "该商品已下架", Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }else{
//                    intent = new Intent(this, BindPhoneActivity.class);
//                    intent.putExtra("type",1);
//                    startActivity(intent);
                }
        }
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

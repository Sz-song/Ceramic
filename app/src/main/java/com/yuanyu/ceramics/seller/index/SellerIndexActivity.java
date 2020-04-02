package com.yuanyu.ceramics.seller.index;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.address_manage.AddressManageActivity;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.common.GlideEngine;
import com.yuanyu.ceramics.dingzhi.MyDingzhiActivity;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.mine.applyenter.EnterProtocolActivity;
import com.yuanyu.ceramics.mine.systemsetting.SystemSettingActivity;
import com.yuanyu.ceramics.order.MyOrderActivity;
import com.yuanyu.ceramics.order.refund.RefundListActivity;
import com.yuanyu.ceramics.seller.liveapply.LiveApplyActivity;
import com.yuanyu.ceramics.seller.order.ShopOrderActivity;
import com.yuanyu.ceramics.seller.shop_goods.ShopGoodsActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;
import static com.yuanyu.ceramics.AppConstant.DAIFAHUO;
import static com.yuanyu.ceramics.AppConstant.DAIFUKUAN;
import static com.yuanyu.ceramics.AppConstant.DAIPINGJIA;
import static com.yuanyu.ceramics.AppConstant.DAISHOUHUO;
import static com.yuanyu.ceramics.MyApplication.getContext;

public class SellerIndexActivity extends BaseActivity<SellerIndexPresenter> implements SellerIndexConstract.IMineView {

    private static final int CHANGE_PORTRAIT = 1000;
    @BindView(R.id.image_head)
    ImageView imageHead;
    @BindView(R.id.system_setting)
    ImageView systemSetting;
    @BindView(R.id.protrait)
    CircleImageView protrait;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.introduce)
    TextView introduce;
    @BindView(R.id.mine_relat)
    RelativeLayout mineRelat;
    @BindView(R.id.all_order)
    TextView allOrder;
    @BindView(R.id.nopay)
    LinearLayout nopay;
    @BindView(R.id.nofahuo)
    LinearLayout nofahuo;
    @BindView(R.id.yifahuo)
    LinearLayout yifahuo;
    @BindView(R.id.yishouhuo)
    LinearLayout yishouhuo;
    @BindView(R.id.refund)
    LinearLayout refund;
    @BindView(R.id.commodity)
    LinearLayout commodity;
    @BindView(R.id.my_dingzhi)
    LinearLayout myDingzhi;
    @BindView(R.id.liveapply)
    LinearLayout liveapply;
    @BindView(R.id.message)
    LinearLayout message;
    @BindView(R.id.contactkf)
    LinearLayout contactkf;
    @BindView(R.id.changeuser)
    LinearLayout changeuser;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    @Override
    protected int getLayout() {
        return R.layout.seller_index_activity;
    }

    @Override
    protected SellerIndexPresenter initPresent() {
        return new SellerIndexPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        swipe.setRefreshing(false);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> presenter.initData(Sp.getString(getContext(), AppConstant.SHOP_ID)));
        presenter.initData(Sp.getString(getContext(), AppConstant.SHOP_ID));
    }


    @Override
    public void initDataSuccess(SellerIndexBean bean) {
        swipe.setRefreshing(false);
        GlideApp.with(this)
                .load(BASE_URL + bean.getPortrait()).placeholder(R.drawable.logo_default)
                .override(100, 100)
                .into(protrait);
        name.setText(bean.getName());
        if(bean.getIntroduce()!=null&&bean.getIntroduce().length()>0){
            introduce.setText(bean.getIntroduce());
        }else {
            introduce.setText("暂无简介");
        }

    }

    @Override
    public void initDataFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.status + e.message);
        swipe.setRefreshing(false);
    }

    @OnClick({R.id.protrait,R.id.introduce, R.id.mine_relat, R.id.all_order, R.id.nopay, R.id.nofahuo, R.id.yifahuo, R.id.yishouhuo, R.id.refund, R.id.commodity, R.id.my_dingzhi, R.id.liveapply, R.id.message, R.id.contactkf, R.id.changeuser})
    public void onViewClicked(View view) {
        Intent intent;
        ReplacePortraitPopupWindow portraitPopupWindow;
        switch (view.getId()) {
            case R.id.protrait:
            case R.id.mine_relat:
                portraitPopupWindow = new ReplacePortraitPopupWindow(getContext());
                portraitPopupWindow.showAtLocation(swipe, Gravity.BOTTOM, 0, 0);
                portraitPopupWindow.setPortraitClickListener(v -> {
//                    PictureSelector.create(getActivity()).openGallery(PictureMimeType.ofImage())
//                            .loadImageEngine(GlideEngine.createGlideEngine())
//                              .maxSelectNum(1)
//                            .forResult(CHANGE_PORTRAIT);
//                    portraitPopupWindow.dismiss();
                });
                portraitPopupWindow.setIntroduceClickListener(v -> {
                    Intent intent1=new Intent(getContext(),ShopChangeIntroduceActivity.class);
                    intent1.putExtra("introduce",introduce.getText().toString());
                    getContext().startActivity(intent1);
                    portraitPopupWindow.dismiss();
                    portraitPopupWindow.dismiss();
                });
                break;
            case R.id.introduce:
                portraitPopupWindow = new ReplacePortraitPopupWindow(getContext());
                portraitPopupWindow.showAtLocation(swipe, Gravity.BOTTOM, 0, 0);
                portraitPopupWindow.setPortraitClickListener(v -> {

                });
                portraitPopupWindow.setIntroduceClickListener(v -> {
                    Intent intent1=new Intent(getContext(),ShopChangeIntroduceActivity.class);
                    intent1.putExtra("introduce",introduce.getText().toString());
                    getContext().startActivity(intent1);
                    portraitPopupWindow.dismiss();
                });
                break;
            case R.id.all_order:
                intent = new Intent(this, ShopOrderActivity.class);
                intent.putExtra("position", 0);
                startActivity(intent);
                break;
            case R.id.nopay:
                intent = new Intent(this, ShopOrderActivity.class);
                intent.putExtra("position", 1);
                startActivity(intent);
                break;
            case R.id.nofahuo:
                intent = new Intent(this, ShopOrderActivity.class);
                intent.putExtra("position", 2);
                startActivity(intent);
                break;
            case R.id.yifahuo:
                intent = new Intent(this, ShopOrderActivity.class);
                intent.putExtra("position", 3);
                startActivity(intent);
                break;
            case R.id.yishouhuo:
                intent = new Intent(this, ShopOrderActivity.class);
                intent.putExtra("position", 4);
                startActivity(intent);
                break;
            case R.id.refund:
                intent = new Intent(this, RefundListActivity.class);
                startActivity(intent);
                break;
            case R.id.commodity:
                intent = new Intent(this, ShopGoodsActivity.class);
                startActivity(intent);
                break;
            case R.id.my_dingzhi:
                intent = new Intent(this, MyDingzhiActivity.class);
                startActivity(intent);
                break;
            case R.id.liveapply:
                intent = new Intent(this, LiveApplyActivity.class);
                startActivity(intent);
                break;
            case R.id.message:
                break;
            case R.id.contactkf:

                break;
            case R.id.changeuser:
                intent = new Intent(this, SystemSettingActivity.class);
                startActivity(intent);
                break;
        }
    }


}

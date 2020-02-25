package com.yuanyu.ceramics.mine;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.address_manage.AddressManageActivity;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.bazaar.BazaarActivity;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import jp.wasabeef.glide.transformations.BlurTransformation;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class MineFragment extends BaseFragment<MinePresenter> implements MineConstract.IMineView {
    @BindView(R.id.background)
    ImageView background;
    @BindView(R.id.cart)
    ImageView cart;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.introduce)
    TextView introduce;
    @BindView(R.id.fans_num)
    TextView fansNum;
    @BindView(R.id.fans)
    LinearLayout fans;
    @BindView(R.id.focus_num)
    TextView focusNum;
    @BindView(R.id.focus)
    LinearLayout focus;
    @BindView(R.id.dynamic_num)
    TextView dynamicNum;
    @BindView(R.id.dynamic)
    LinearLayout dynamic;
    @BindView(R.id.protrait)
    CircleImageView protrait;
    @BindView(R.id.all_order)
    TextView allOrder;
    @BindView(R.id.daifukuan_image)
    ImageView daifukuanImage;
    @BindView(R.id.daifukuan)
    RelativeLayout daifukuan;
    @BindView(R.id.daifahuo_image)
    ImageView daifahuoImage;
    @BindView(R.id.daifahuo)
    RelativeLayout daifahuo;
    @BindView(R.id.daishouhuo_image)
    ImageView daishouhuoImage;
    @BindView(R.id.daishouhuo)
    RelativeLayout daishouhuo;
    @BindView(R.id.daipingjia_image)
    ImageView daipingjiaImage;
    @BindView(R.id.daipingjia)
    RelativeLayout daipingjia;
    @BindView(R.id.refund_image)
    ImageView refundImage;
    @BindView(R.id.refund)
    RelativeLayout refund;
    @BindView(R.id.address)
    LinearLayout address;
    @BindView(R.id.my_coupon)
    LinearLayout myCoupon;
    @BindView(R.id.mycollect)
    LinearLayout mycollect;
    @BindView(R.id.dashi)
    TextView dashi;
    @BindView(R.id.dashiattesta)
    LinearLayout dashiattesta;
    @BindView(R.id.apply_enter)
    TextView applyEnter;
    @BindView(R.id.applyenter)
    LinearLayout applyenter;
    @BindView(R.id.system_setting)
    LinearLayout systemSetting;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    protected MinePresenter initPresent() {
        return new MinePresenter();
    }

    @Override
    protected void initEvent(View view) {
        swipe.setRefreshing(false);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> presenter.initData(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID)));
    }

    @Override
    protected void initData() {
        presenter.initData(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID));
    }

    @Override
    public void initDataSuccess(MineBean bean) {
        swipe.setRefreshing(false);
        GlideApp.with(getContext())
                .load(BASE_URL + bean.getPortrait()).placeholder(R.drawable.img_default)
                .override(100, 100)
                .into(protrait);
        GlideApp.with(getContext())
                .load(BASE_URL + bean.getPortrait())
                .optionalTransform(new BlurTransformation(20))
                .placeholder(R.drawable.img_default)
                .into(background);
        name.setText(bean.getName());
        fansNum.setText(bean.getFans_num());
        focusNum.setText(bean.getFocus_num());
        dynamicNum.setText(bean.getDongtai_num());
        introduce.setText(bean.getIntroduce());
    }

    @Override
    public void initDataFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.status + e.message);
        swipe.setRefreshing(false);
    }
    @OnClick(R.id.address)
    public  void  onViewClicked(View view){
        Intent intent;
        switch (view.getId()){
            case R.id.address:
                intent = new Intent(getContext(), AddressManageActivity.class);
                startActivity(intent);
                break;
        }
    }
}

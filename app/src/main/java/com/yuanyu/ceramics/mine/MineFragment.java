package com.yuanyu.ceramics.mine;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.address_manage.AddressManageActivity;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.dingzhi.MyDingzhiActivity;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.invite_newer.InviteNewerActivity;
import com.yuanyu.ceramics.mine.applyenter.EnterProtocolActivity;
import com.yuanyu.ceramics.mine.my_collect.MyCollectActivity;
import com.yuanyu.ceramics.mine.systemsetting.SystemSettingActivity;
import com.yuanyu.ceramics.mycoins.MyCoinsActivity;
import com.yuanyu.ceramics.order.MyOrderActivity;
import com.yuanyu.ceramics.order.refund.RefundListActivity;
import com.yuanyu.ceramics.personal_index.PersonalIndexActivity;
import com.yuanyu.ceramics.personal_index.fans_focus.FocusAndFansActicity;
import com.yuanyu.ceramics.seller.index.SellerIndexActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;
import static com.yuanyu.ceramics.AppConstant.DAIFAHUO;
import static com.yuanyu.ceramics.AppConstant.DAIFUKUAN;
import static com.yuanyu.ceramics.AppConstant.DAIPINGJIA;
import static com.yuanyu.ceramics.AppConstant.DAISHOUHUO;

public class MineFragment extends BaseFragment<MinePresenter> implements MineConstract.IMineView {


    @BindView(R.id.title)
    TextView title;
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
    @BindView(R.id.my_dingzhi)
    LinearLayout myDingzhi;
    @BindView(R.id.my_collect)
    LinearLayout myCollect;
    @BindView(R.id.applyenter)
    LinearLayout applyenter;
    @BindView(R.id.dashiattesta)
    LinearLayout dashiattesta;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.daifukuan_count)
    TextView daifukuanCount;
    @BindView(R.id.daifahuo_count)
    TextView daifahuoCount;
    @BindView(R.id.daishouhuo_count)
    TextView daishouhuoCount;
    @BindView(R.id.daipingjia_count)
    TextView daipingjiaCount;
    @BindView(R.id.refund_count)
    TextView refundCount;
    @BindView(R.id.sellstatus)
    TextView sellstatus;
    @BindView(R.id.dashi)
    TextView dashi;
    @BindView(R.id.coins)
    LinearLayout coins;
    @BindView(R.id.invite)
    LinearLayout invite;

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
        if (Sp.getString(getContext(), AppConstant.SHOP_ID, "").length() > 0) {
            sellstatus.setText("进入店铺");
        } else {
            sellstatus.setText("商家入驻");
        }
    }

    @Override
    protected void initData() {
        presenter.initData(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID));
    }

    @Override
    public void initDataSuccess(MineBean bean) {
        swipe.setRefreshing(false);
        GlideApp.with(getContext())
                .load(BASE_URL + bean.getPortrait()).placeholder(R.drawable.logo_default)
                .override(100, 100)
                .into(protrait);
        name.setText(bean.getName());
        fansNum.setText(bean.getFans_num());
        focusNum.setText(bean.getFocus_num());
        dynamicNum.setText(bean.getDongtai_num());
        introduce.setText(bean.getIntroduce());
        presenter.setCount(daifukuanCount, bean.getDaifukuan());
        presenter.setCount(daifahuoCount, bean.getDaifahuo());
        presenter.setCount(daishouhuoCount, bean.getDaishouhuo());
        presenter.setCount(daipingjiaCount, bean.getDaipingjia());
        presenter.setCount(refundCount, bean.getTuikuan());
        if (bean.getMerchant_status() == 0) {
            sellstatus.setText("商家入驻");
        } else if (bean.getMerchant_status() == 1) {
            Sp.putString(getContext(), AppConstant.SHOP_ID, bean.getShop_id());
            sellstatus.setText("进入店铺");
        } else if (bean.getMerchant_status() == 2) {
            sellstatus.setText("商家入驻(审核中)");
            applyenter.setClickable(false);
        }
        if (bean.getDashi_status() == 2) {
            dashi.setText("大师认证（审核中）");
            dashiattesta.setClickable(false);
        } else if (bean.getDashi_status() == 1) {
            dashi.setText("大师主页");
            dashiattesta.setClickable(true);
            dashiattesta.setOnClickListener(view -> PersonalIndexActivity.actionStart(getContext(), Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID)));
        }
    }

    @Override
    public void initDataFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.status + e.message);
        swipe.setRefreshing(false);
    }

    @OnClick({R.id.fans, R.id.focus, R.id.dynamic, R.id.protrait, R.id.mine_relat, R.id.all_order, R.id.daifukuan, R.id.daifahuo, R.id.daishouhuo, R.id.daipingjia, R.id.refund, R.id.address, R.id.my_dingzhi, R.id.my_collect, R.id.dashiattesta, R.id.applyenter, R.id.system_setting,R.id.coins,R.id.invite})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.fans:
                intent = new Intent(getContext(), FocusAndFansActicity.class);
                intent.putExtra("userid", Sp.getString(getContext(), "useraccountid"));
                intent.putExtra("position", 2);
                startActivity(intent);
                break;
            case R.id.focus:
                intent = new Intent(getContext(), FocusAndFansActicity.class);
                intent.putExtra("userid", Sp.getString(getContext(), "useraccountid"));
                intent.putExtra("position", 1);
                startActivity(intent);
                break;
            case R.id.dynamic:
            case R.id.protrait:
            case R.id.mine_relat:
                PersonalIndexActivity.actionStart(getContext(), Sp.getString(getContext(), "useraccountid"));
                break;
            case R.id.all_order:
                intent = new Intent(getContext(), MyOrderActivity.class);
                intent.putExtra("status", 0);
                startActivity(intent);
                break;
            case R.id.daifukuan:
                intent = new Intent(getContext(), MyOrderActivity.class);
                intent.putExtra("status", DAIFUKUAN);
                startActivity(intent);
                break;
            case R.id.daifahuo:
                intent = new Intent(getContext(), MyOrderActivity.class);
                intent.putExtra("status", DAIFAHUO);
                startActivity(intent);
                break;
            case R.id.daishouhuo:
                intent = new Intent(getContext(), MyOrderActivity.class);
                intent.putExtra("status", DAISHOUHUO);
                startActivity(intent);
                break;
            case R.id.daipingjia:
                intent = new Intent(getContext(), MyOrderActivity.class);
                intent.putExtra("status", DAIPINGJIA);
                startActivity(intent);
                break;
            case R.id.refund:
                intent = new Intent(getContext(), RefundListActivity.class);
                startActivity(intent);
                break;
            case R.id.address:
                intent = new Intent(getContext(), AddressManageActivity.class);
                startActivity(intent);
                break;
            case R.id.my_dingzhi:
                intent = new Intent(getContext(), MyDingzhiActivity.class);
                startActivity(intent);
                break;
            case R.id.my_collect:
                intent = new Intent(getContext(), MyCollectActivity.class);
                startActivity(intent);
                break;
            case R.id.dashiattesta:
                intent = new Intent(this.getContext(), EnterProtocolActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.applyenter:
                if (Sp.getString(getContext(), AppConstant.SHOP_ID).equals("")) {
                    intent = new Intent(this.getContext(), EnterProtocolActivity.class);
                    intent.putExtra("type", 0);
                    startActivity(intent);
                } else {
                    intent = new Intent(getActivity(), SellerIndexActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.system_setting:
                intent = new Intent(getContext(), SystemSettingActivity.class);
                startActivity(intent);
                break;
            case R.id.coins:
                intent = new Intent(getContext(), MyCoinsActivity.class);
                startActivity(intent);
                break;
            case R.id.invite:
                intent = new Intent(getContext(), InviteNewerActivity.class);
                startActivity(intent);
                break;
        }
    }
}

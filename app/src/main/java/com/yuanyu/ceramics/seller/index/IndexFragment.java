package com.yuanyu.ceramics.seller.index;

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
import com.yuanyu.ceramics.mine.applyenter.EnterProtocolActivity;
import com.yuanyu.ceramics.mine.systemsetting.SystemSettingActivity;
import com.yuanyu.ceramics.order.MyOrderActivity;
import com.yuanyu.ceramics.order.refund.RefundListActivity;
import com.yuanyu.ceramics.personal_index.PersonalIndexActivity;
import com.yuanyu.ceramics.personal_index.fans_focus.FocusAndFansActicity;
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

public class IndexFragment extends BaseFragment<IndexPresenter> implements IndexConstract.IMineView {


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
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_index, container, false);
    }

    @Override
    protected IndexPresenter initPresent() {
        return new IndexPresenter();
    }

    @Override
    protected void initEvent(View view) {
        swipe.setRefreshing(false);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> presenter.initData(Sp.getString(getContext(), AppConstant.SHOP_ID)));
    }

    @Override
    protected void initData() {
        presenter.initData(Sp.getString(getContext(), AppConstant.SHOP_ID));
    }

    @Override
    public void initDataSuccess(IndexBean bean) {
        swipe.setRefreshing(false);
        GlideApp.with(getContext())
                .load(BASE_URL + bean.getPortrait()).placeholder(R.drawable.img_default)
                .override(100, 100)
                .into(protrait);
        name.setText(bean.getName());
        introduce.setText(bean.getIntroduce());
    }

    @Override
    public void initDataFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.status + e.message);
        swipe.setRefreshing(false);
    }

    @OnClick({R.id.protrait, R.id.mine_relat, R.id.all_order, R.id.nopay, R.id.nofahuo, R.id.yifahuo, R.id.yishouhuo, R.id.refund, R.id.commodity, R.id.my_dingzhi, R.id.liveapply, R.id.message, R.id.contactkf, R.id.changeuser})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.protrait:
            case R.id.mine_relat:
//                PersonalIndexActivity.actionStart(getContext(), Sp.getString(getContext(), "useraccountid"));
                break;
            case R.id.all_order:
                intent = new Intent(getContext(), MyOrderActivity.class);
                intent.putExtra("status", 0);
                startActivity(intent);
                break;
            case R.id.nopay:
                intent = new Intent(getContext(), MyOrderActivity.class);
                intent.putExtra("status", DAIFUKUAN);
                startActivity(intent);
                break;
            case R.id.nofahuo:
                intent = new Intent(getContext(), MyOrderActivity.class);
                intent.putExtra("status", DAIFAHUO);
                startActivity(intent);
                break;
            case R.id.yifahuo:
                intent = new Intent(getContext(), MyOrderActivity.class);
                intent.putExtra("status", DAISHOUHUO);
                startActivity(intent);
                break;
            case R.id.yishouhuo:
                intent = new Intent(getContext(), MyOrderActivity.class);
                intent.putExtra("status", DAIPINGJIA);
                startActivity(intent);
                break;
            case R.id.refund:
                intent = new Intent(getContext(), RefundListActivity.class);
                startActivity(intent);
                break;
            case R.id.commodity:
                intent = new Intent(getContext(), AddressManageActivity.class);
                startActivity(intent);
                break;
            case R.id.my_dingzhi:
                intent = new Intent(getContext(), MyDingzhiActivity.class);
                startActivity(intent);
                break;
            case R.id.liveapply:
                break;
            case R.id.message:
                break;
            case R.id.contactkf:
                if (Sp.getString(getContext(), AppConstant.SHOP_ID).equals("")) {
                    intent = new Intent(this.getContext(), EnterProtocolActivity.class);
                    intent.putExtra("type", 0);
                    startActivity(intent);
                }
                break;
            case R.id.changeuser:
                intent = new Intent(getContext(), SystemSettingActivity.class);
                startActivity(intent);
                break;
        }
    }
}

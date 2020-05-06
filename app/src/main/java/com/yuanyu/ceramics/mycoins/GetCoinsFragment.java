package com.yuanyu.ceramics.mycoins;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.bazaar.BazaarActivity;
import com.yuanyu.ceramics.invite_newer.InviteNewerActivity;
import com.yuanyu.ceramics.seller.order.ShopOrderActivity;
import com.yuanyu.ceramics.seller.shop_shelve.ShelveActivity;
import com.yuanyu.ceramics.shop_index.ShopIndexActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import butterknife.BindView;
import butterknife.OnClick;

public class GetCoinsFragment extends BaseFragment<GetCoinsPresenter> implements GetCoinsConstract.IGetCoinsView {
    @BindView(R.id.shelves_name)
    TextView shelvesName;
    @BindView(R.id.shelves_coins_num)
    TextView shelvesCoinsNum;
    @BindView(R.id.shelves_get_coins)
    TextView shelvesGetCoins;
    @BindView(R.id.shelves)
    LinearLayout shelves;
    @BindView(R.id.trading_name)
    TextView tradingName;
    @BindView(R.id.trading_coins_num)
    TextView tradingCoinsNum;
    @BindView(R.id.trading_get_coins)
    TextView tradingGetCoins;
    @BindView(R.id.trading)
    LinearLayout trading;
    @BindView(R.id.share_name)
    TextView shareName;
    @BindView(R.id.share_coins_num)
    TextView shareCoinsNum;
    @BindView(R.id.share_get_coins)
    TextView shareGetCoins;
    @BindView(R.id.share)
    LinearLayout share;
    @BindView(R.id.invite_name)
    TextView inviteName;
    @BindView(R.id.invite_coins_num)
    TextView inviteCoinsNum;
    @BindView(R.id.invite_get_coins)
    TextView inviteGetCoins;
    @BindView(R.id.invite)
    LinearLayout invite;
    @BindView(R.id.enter_name)
    TextView enterName;
    @BindView(R.id.enter_coins_num)
    TextView enterCoinsNum;
    @BindView(R.id.enter_get_coins)
    TextView enterGetCoins;
    @BindView(R.id.enter)
    LinearLayout enter;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_getconins, container, false);
    }

    @Override
    protected GetCoinsPresenter initPresent() {
        return new GetCoinsPresenter();
    }

    @Override
    protected void initEvent(View view) {
        if (Sp.getString(getContext(), AppConstant.SHOP_ID).length() > 0) {
            shelves.setVisibility(View.VISIBLE);
        } else {
            shelves.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initData() {
        presenter.getMyCoinsTask(Sp.getString(getContext(),AppConstant.USER_ACCOUNT_ID),Sp.getString(getContext(),AppConstant.SHOP_ID));
    }

    @Override
    public void getMyCoinsTaskSuccess(GetCoinsBean bean) {
        shelvesCoinsNum.setText("奖励"+bean.getShelves_coins_num()+"颗金豆");
        tradingCoinsNum.setText("奖励"+bean.getTrading_coins_num()+"颗金豆");
        shareCoinsNum.setText("奖励"+bean.getShare_coins_num()+"颗金豆");
        inviteCoinsNum.setText("奖励"+bean.getInvite_coins_num()+"颗金豆");
        enterCoinsNum.setText("奖励"+bean.getEnter_coins_num()+"颗金豆");
        shareName.setText("成功分享店铺一次("+bean.getShare_num()+"/3)");
    }

    @Override
    public void getMyCoinsTaskFail(ExceptionHandler.ResponeThrowable e) {
        L.e(e.message+"  "+e.status);
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
    }
    @OnClick({R.id.shelves_get_coins, R.id.trading_get_coins, R.id.share_get_coins, R.id.invite_get_coins, R.id.enter_get_coins})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.shelves_get_coins:
                if(Sp.getString(getContext(),AppConstant.SHOP_ID)!=null&&Sp.getString(getContext(),AppConstant.SHOP_ID).length()>0){
                    intent = new Intent(getContext(), ShelveActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.trading_get_coins:
                if(Sp.getString(getContext(),AppConstant.SHOP_ID)!=null&&Sp.getString(getContext(),AppConstant.SHOP_ID).length()>0){
                    intent=new Intent(getContext(), ShopOrderActivity.class);
                    startActivity(intent);
                }else{
                    intent=new Intent(getContext(), BazaarActivity.class);
                    intent.putExtra("position",1);
                    startActivity(intent);
                }
                break;
            case R.id.share_get_coins:
                if(Sp.getString(getContext(),AppConstant.SHOP_ID)!=null&&Sp.getString(getContext(),AppConstant.SHOP_ID).length()>0){
                    intent=new Intent(getContext(), ShopIndexActivity.class);
                    intent.putExtra("shopid",Sp.getString(getContext(),AppConstant.SHOP_ID));
                    startActivity(intent);
                }else{
                    intent=new Intent(getContext(),BazaarActivity.class);
                    intent.putExtra("position",0);
                    startActivity(intent);
                }
                break;
            case R.id.invite_get_coins:
            case R.id.enter_get_coins:
                intent=new Intent(getContext(), InviteNewerActivity.class);
                startActivity(intent);
                break;
        }
    }
    public void reflash(){
        presenter.getMyCoinsTask(Sp.getString(getContext(),AppConstant.USER_ACCOUNT_ID),Sp.getString(getContext(),AppConstant.SHOP_ID));
    }
}

package com.yuanyu.ceramics.cart;

import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.common.LoadingDialog;
import com.yuanyu.ceramics.common.view.SmoothCheckBox;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CartFragment extends BaseFragment<CartPresenter> implements CartConstract.ICartView {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.select_all)
    SmoothCheckBox selectAll;
    @BindView(R.id.check)
    TextView check;
    @BindView(R.id.price)
    TextView price;
    private CartAdapter adapter;
    private List<CartBean> list ;
    private List<GoodsBean> payList;
    private LoadingDialog dialog;
    private double totalPrice;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.cart_fragment, container, false);
    }

    @Override
    protected CartPresenter initPresent() {
        return new CartPresenter();
    }

    @Override
    protected void initEvent(View view) {
        list = new ArrayList<>();
        payList = new ArrayList<>();
        dialog = new LoadingDialog(getActivity());
        dialog.show();
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> {
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.getGoodsdata(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID));
        });
        selectAll.setChecked(true);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(getContext(), list);
        adapter.setOnDeleteListener(str -> {
            dialog.show();
            presenter.deleteCartItem(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID),str);
        });
        adapter.setOnReflashListener(() -> reflesh());
        presenter.getGoodsdata(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID));
        recyclerview.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        presenter.getGoodsdata(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID));
    }

    @Override
    public void getGoodsdataSuccess(List<GoodsBean> goodsBeans) {
        swipe.setRefreshing(false);
        presenter.initList(list,goodsBeans);
        adapter.notifyDataSetChanged();
        dialog.dismiss();
    }

    @Override
    public void getGoodsdataFail(ExceptionHandler.ResponeThrowable e) {
        swipe.setRefreshing(false);
        L.e(e.message + "  " + e.status);
        Toast.makeText(getContext(), "加载失败", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    @Override
    public void deleteCartItemSuccess(String id) {
        dialog.dismiss();
        for(int i=list.size()-1;i>=0;i--){
            for(int j=list.get(i).getList().size()-1;j>=0;j--){
                if(list.get(i).getList().get(j).getId().equals(id)){
                    list.get(i).getList().remove(j);
                }
            }
            if(list.get(i).getList().size()==0){
                list.remove(i);
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void deleteCartItemFail(ExceptionHandler.ResponeThrowable e) {
        dialog.dismiss();
        L.e(e.message + "  " + e.status);
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
    }

    private void reflesh() {
        int selectCount = 0;//选中数
        int sumCount=0;//商品数
        totalPrice = 0;//选中价格
        for (int i = 0; i < list.size(); i++){
            for(int j=0;j<list.get(i).getList().size();j++){
                sumCount++;
                if(list.get(i).getList().get(j).isSelect()){
                    selectCount++;
                    totalPrice = totalPrice +list.get(i).getList().get(j).getPrice();
                }
            }
        }
        title.setText("购物车("+sumCount+")");
        check.setText("确认(" + selectCount + ")");
        SpannableString spannableString = new SpannableString("合计:" + "¥" + String.format("%.2f", totalPrice));
        ForegroundColorSpan span = new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary));
        spannableString.setSpan(span, 3, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        price.setText(spannableString);
    }
}

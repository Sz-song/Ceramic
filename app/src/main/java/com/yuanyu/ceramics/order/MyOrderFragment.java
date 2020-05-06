package com.yuanyu.ceramics.order;

import android.app.Dialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.common.CommonDialog;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;


public class MyOrderFragment extends BaseFragment<MyOrderFragmentPresenter> implements MyOrderFragmentConstract.IOrderFragmentView {
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.nodata_img)
    ImageView nodataImg;
    @BindView(R.id.nodata)
    TextView nodata;
    @BindView(R.id.frame)
    FrameLayout frame;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private int status;
    private int page;
    private MyOrderAdapter adapter;
    private List<MyOrderFragmentBean> list;
    private boolean isRemind=true;



    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.common_fragment, container, false);
    }

    @Override
    protected MyOrderFragmentPresenter initPresent() {
        return new MyOrderFragmentPresenter();
    }

    @Override
    protected void initEvent(View view) {
        if (getArguments() != null) {
            status = getArguments().getInt("type");
        }
        page=0;
        list = new ArrayList<>();
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> {
            page=0;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.getOrderList(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID),page,status);
        });
        GlideApp.with(this).load(R.drawable.nodata_img).into(nodataImg);
        nodata.setText("暂无数据");
        adapter = new MyOrderAdapter(getContext(), list, false);
        adapter.setCancelListener(position -> {
            CommonDialog dialog = new CommonDialog(getContext(), R.style.commonDialog);
            dialog.setContent("是否确定取消订单？");
            dialog.setLeftBtnText("确定取消");
            dialog.setRightBtnText("我再想想");
            dialog.show();
            dialog.setListener(new CommonDialog.DialogClickListener() {
                @Override
                public void onLeftBtnClick(Dialog dialog) {
                    dialog.dismiss();
                    presenter.cancelOrder(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID),list.get(position).getOrdernum(),position);
                }

                @Override
                public void onRightBtnClick(Dialog dialog) {
                    dialog.dismiss();
                }
            });
        });

        adapter.setDeleteListener(position -> {
            CommonDialog dialog = new CommonDialog(getContext(), R.style.commonDialog);
            dialog.setContent("是否确定删除订单？");
            dialog.setLeftBtnText("确定删除");
            dialog.setRightBtnText("我再想想");
            dialog.show();
            dialog.setListener(new CommonDialog.DialogClickListener() {
                @Override
                public void onLeftBtnClick(Dialog dialog) {
                    dialog.dismiss();
                    presenter.deleteOrder(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID),list.get(position).getOrdernum(),position);
                }

                @Override
                public void onRightBtnClick(Dialog dialog) {
                    dialog.dismiss();
                }
            });
        });
        adapter.setConfirmListener(position -> {
            CommonDialog dialog = new CommonDialog(getContext(), R.style.commonDialog);
            dialog.setContent("是否确认收货？");
            dialog.setLeftBtnText("确认");
            dialog.setRightBtnText("取消");
            dialog.show();
            dialog.setListener(new CommonDialog.DialogClickListener() {
                @Override
                public void onLeftBtnClick(Dialog dialog) {
                    dialog.dismiss();
                    presenter.confirmReceived(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID),list.get(position).getOrdernum(),position);
                }

                @Override
                public void onRightBtnClick(Dialog dialog) {
                    dialog.dismiss();
                }
            });
        });
        adapter.setFahuoListener(position -> {
            if (isRemind) {
                presenter.remindDelivery(Sp.getString(getContext(),AppConstant.USER_ACCOUNT_ID),list.get(position).getOrdernum(),position);
            } else {
                Toast.makeText(getContext(), "已提醒发货", Toast.LENGTH_SHORT).show();
            }
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(adapter);
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastPosition;
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                    lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                    if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1&&lastPosition>9) {
                        presenter.getOrderList(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID),page,status);
                    }
                }
            }
        });
    }

    @Override
    protected void initData() {
        presenter.getOrderList(Sp.getString(getContext(), AppConstant.USER_ACCOUNT_ID),page,status);
    }

    @Override
    public void getOrderListSuccess(List<MyOrderFragmentBean> listbean) {
        list.addAll(listbean);
        adapter.notifyItemRangeInserted(list.size() - listbean.size(), listbean.size());
        swipe.setRefreshing(false);
        page++;
        if (list.size() == 0) {
            nodataImg.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        } else {
            nodataImg.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void getOrderListFail(ExceptionHandler.ResponeThrowable e) {
        swipe.setRefreshing(false);
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
        if (list.size() == 0) {
            nodataImg.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        } else {
            nodataImg.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void cancelOrderSuccess(int position) {
        Toast.makeText(getContext(), "订单已取消", Toast.LENGTH_SHORT).show();
        list.remove(position);
        adapter.notifyDataSetChanged();
        if (list.size() == 0) {
            nodataImg.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        } else {
            nodataImg.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void cancelOrderFail(ExceptionHandler.ResponeThrowable e) {
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
        if (list.size() == 0) {
            nodataImg.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        } else {
            nodataImg.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void deleteOrderSuccess(int position) {
        Toast.makeText(getContext(), "订单已删除", Toast.LENGTH_SHORT).show();
        list.remove(position);
        adapter.notifyDataSetChanged();
        if (list.size() == 0) {
            nodataImg.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        } else {
            nodataImg.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void deleteOrderFail(ExceptionHandler.ResponeThrowable e) {
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
        if (list.size() == 0) {
            nodataImg.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        } else {
            nodataImg.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void remindDeliverySuccess(int position) {
        Toast.makeText(getContext(), "已提醒发货", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void remindDeliveryFail(ExceptionHandler.ResponeThrowable e) {
        Toast.makeText(getContext(), "提醒失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void confirmReceivedSuccess(int position) {
        Toast.makeText(getContext(), "收货成功", Toast.LENGTH_SHORT).show();
        list.remove(position);
        adapter.notifyDataSetChanged();
        if (list.size() == 0) {
            nodataImg.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        } else {
            nodataImg.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }

    @Override
    public void confirmReceivedFail(ExceptionHandler.ResponeThrowable e) {
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
        if (list.size() == 0) {
            nodataImg.setVisibility(View.VISIBLE);
            nodata.setVisibility(View.VISIBLE);
        } else {
            nodataImg.setVisibility(View.GONE);
            nodata.setVisibility(View.GONE);
        }
    }
}

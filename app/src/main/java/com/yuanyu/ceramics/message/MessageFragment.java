package com.yuanyu.ceramics.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.L;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageFragment extends BaseFragment<MessagePresenter> implements MessageConstract.IMessageView {
    @BindView(R.id.contact)
    LinearLayout contact;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.pinglun_img)
    CircleImageView pinglunImg;
    @BindView(R.id.pinglun)
    LinearLayout pinglun;
    @BindView(R.id.zan_img)
    CircleImageView zanImg;
    @BindView(R.id.zan)
    LinearLayout zan;
    @BindView(R.id.kefu_img)
    CircleImageView kefuImg;
    @BindView(R.id.kefu)
    LinearLayout kefu;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    private List<MessageBean> list;
    private MessageAdapter adapter;
    private boolean canreflash;

    @Override
    protected View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    protected MessagePresenter initPresent() {
        return new MessagePresenter();
    }

    @Override
    protected void initEvent(View view) {
        list = new ArrayList<>();
        canreflash=true;
        adapter = new MessageAdapter(getContext(), list);
        recyclerview.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerview.setAdapter(adapter);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(() -> {
            if(canreflash){
                canreflash=false;
                list.clear();
                adapter.notifyDataSetChanged();
                presenter.initData();
            }
        });
    }

    @Override
    protected void initData() {
        canreflash=false;
        presenter.initData();
    }

    @Override
    public void initDataSuccess(List<MessageBean> beans) {
        list.addAll(beans);
        adapter.notifyDataSetChanged();
        swipe.setRefreshing(false);
        canreflash=true;
    }

    @Override
    public void initDataFail(ExceptionHandler.ResponeThrowable e) {
        Toast.makeText(getContext(), e.message, Toast.LENGTH_SHORT).show();
        L.e(e.status + "  " + e.message);
        swipe.setRefreshing(false);
        canreflash=true;
    }

    @Override
    public void receiveMessageSuccess() {
        if(canreflash) {
            canreflash = false;
            list.clear();
            adapter.notifyDataSetChanged();
            presenter.initData();
        }
    }

}

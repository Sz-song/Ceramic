package com.yuanyu.ceramics.seller.evaluationmanage;


import android.content.Context;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseFragment;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.BasePresenter;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018-08-22.
 */

public class EvaluationManageFragment extends BaseFragment {
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
    @BindView(R.id.reply_txt)
    EditText replyTxt;
    @BindView(R.id.commit)
    TextView commit;
    @BindView(R.id.input)
    LinearLayout input;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.root)
    RelativeLayout root;
    private String shopid;
    private int type;
    private int screenHeight;

    /**
     * 0 好评 1中评 2差评
     **/
    private int page = 0;
    private int page_size = 10;
    private int position;
    private List<EvaluationManageBean> list;
    private EvaluationManageAdapter adapter;
    private EvaluationManageModel model = new EvaluationManageModel();
    View decorView;
    View contentView;

    @Override
    public void initData() {
    }

    @Override
    public View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.evalution_fragment, container, false);
        return view;
    }

    @Override
    protected BasePresenter initPresent() {
        return new BasePresenter() {
        };
    }

    @Override
    public void initEvent(View view) {
        decorView = getActivity().getWindow().getDecorView();
        screenHeight = decorView.getContext().getResources().getDisplayMetrics().heightPixels;
        contentView = getActivity().findViewById(Window.ID_ANDROID_CONTENT);
        root.setFocusable(true);
        root.setFocusableInTouchMode(true);
        root.requestFocus();
        shopid = Sp.getString(getContext(), AppConstant.SHOP_ID);
        type = getArguments().getInt("type");
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recyclerview.setLayoutManager(manager);
        adapter = new EvaluationManageAdapter(getActivity(), list);
        recyclerview.setAdapter(adapter);
        input.setVisibility(View.GONE);
        adapter.setReplyOnLintener(new EvaluationManageAdapter.ReplyOnLintener() {
            /**回复按钮点击事件**/
            @Override
            public void reply(int p) {
                replyTxt.setFocusable(true);
                replyTxt.setFocusableInTouchMode(true);
                replyTxt.requestFocus();
                position=p;
                input.setVisibility(View.VISIBLE);
                L.e("SOFT_INPUT_STATE_ALWAYS_VISIBLE");
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if(imm!=null){
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);}
                decorView.getViewTreeObserver().addOnGlobalLayoutListener(getGlobalLayoutListener(decorView, contentView));
            }
        });
        swipe.setRefreshing(true);
        swipe.setColorSchemeResources(R.color.colorPrimary);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                list.clear();
                adapter.notifyDataSetChanged();
                page=0;
                initlist();
            }
        });
        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
                int lastPosition = -1;
                //当前状态为停止滑动状态SCROLL_STATE_IDLE时
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
                    if (lastPosition >= recyclerView.getLayoutManager().getItemCount() - 1) {
                        initlist();
                    }
                }
            }
        });
        initlist();
    }
    private void initlist() {
        model.getEvaluation(shopid, page, page_size, type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<>())
                .subscribe(new BaseObserver<List<EvaluationManageBean>>() {
                    @Override
                    public void onNext(List<EvaluationManageBean> beanlist) {
                        for (int i = 0; i < beanlist.size(); i++) {
                            list.add(beanlist.get(i));
                        }
                        page++;
                        adapter.notifyItemRangeInserted(list.size() - beanlist.size(), beanlist.size());
                        swipe.setRefreshing(false);
                    }

                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        L.e(e.status + " " + e.message);
                        swipe.setRefreshing(false);
                    }
                });
    }

    @OnClick(R.id.commit)
    public void onViewClicked() {
        if(replyTxt.getText().toString().length()>0){
            final int replytype;
            if(list.get(position).getEvaleation2().length()>0){
                replytype=2;
            }else{
                replytype=1;
            }
            model.replyEvaluation(shopid,list.get(position).getId(),replytype,replyTxt.getText().toString())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(new HttpServiceInstance.ErrorTransformer<String[]>())
                    .subscribe(new BaseObserver<String[]>() {
                        @Override
                        public void onNext(String[] strings) {
                            Toast.makeText(getContext(), "评论成功", Toast.LENGTH_SHORT).show();
                            if(replytype==1){
                                list.get(position).setReply_txt(replyTxt.getText().toString());
                            }else{
                                list.get(position).setReply_txt2(replyTxt.getText().toString());
                            }
                            adapter.notifyItemChanged(position);
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            if(imm!=null){
                                imm.hideSoftInputFromWindow(getActivity().getWindow().getDecorView().getWindowToken(), 0);
                            }
                            replyTxt.setText("");
                            input.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(ExceptionHandler.ResponeThrowable e) {
                            Toast.makeText(getContext(), "回复失败", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else {
            Toast.makeText(getContext(), "回复不能为空", Toast.LENGTH_SHORT).show();
        }
    }
    private ViewTreeObserver.OnGlobalLayoutListener getGlobalLayoutListener(final View decorView, final View contentView) {
        return () -> {
            Rect r = new Rect();
            decorView.getWindowVisibleDisplayFrame(r);
            if (screenHeight < r.bottom) screenHeight = r.bottom;
            int diff = screenHeight - r.bottom;
            if (diff != 0) {
                if (contentView.getPaddingBottom() != diff) {
                    contentView.setPadding(0, 0, 0, diff);
                }
            } else {
                if (contentView.getPaddingBottom() != 0) {
                    contentView.setPadding(0, 0, 0, 0);
                    input.setVisibility(View.GONE);
                    root.setFocusable(true);
                    root.setFocusableInTouchMode(true);
                    root.requestFocus();
                }
            }
        };
    }
}

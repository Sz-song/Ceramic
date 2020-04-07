package com.yuanyu.ceramics.mine.systemsetting;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tencent.imsdk.TIMFriendshipManager;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.friendship.TIMFriend;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseObserver;
import com.yuanyu.ceramics.base.NormalActivity;
import com.yuanyu.ceramics.utils.ExceptionHandler;
import com.yuanyu.ceramics.utils.HttpServiceInstance;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;
import com.yuanyu.ceramics.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BlackListActivity extends NormalActivity {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    List<BlackListBean> list=new ArrayList<>();
    private BlackListAdapter adapter;
    private SystemSettingModel model=new SystemSettingModel();
    private int page=0;
    private int page_size=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_backlist);
        ButterKnife.bind(this);
        title.setText("黑名单管理");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerview.setLayoutManager(manager);
        adapter=new BlackListAdapter(list,this);
        recyclerview.setAdapter(adapter);
        initlist();
    }

    private void initlist() {
        TIMFriendshipManager.getInstance().getBlackList(new TIMValueCallBack<List<TIMFriend>>() {
            @Override
            public void onError(int i, String s) {
                ToastUtils.showToast(BlackListActivity.this,"Error code = " + i + ", desc = " + s);
            }

            @Override
            public void onSuccess(List<TIMFriend> timFriends) {
                if (timFriends != null && timFriends.size() > 0) {
                    for (TIMFriend friend : timFriends) {
                        L.e(friend.getIdentifier()+friend.toString());
                    }
                }
            }
        });
        String useraccountid= Sp.getString(this,"useraccountid");
        model.getBlacklist(useraccountid,page,page_size)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(new HttpServiceInstance.ErrorTransformer<>())
                .subscribe(new BaseObserver<List<BlackListBean>>() {
                    @Override
                    public void onNext(List<BlackListBean> dynamicList) {
                        for(int i=0;i<dynamicList.size();i++){
                            list.add(dynamicList.get(i));
                        }
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void onError(ExceptionHandler.ResponeThrowable e) {
                        L.e("response"+e.message+e.status);
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }
}

package com.yuanyu.ceramics.broadcast.pull;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMElem;
import com.tencent.imsdk.TIMElemType;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMUserProfile;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.ext.group.TIMGroupMemberResult;
import com.tencent.rtmp.ITXLivePlayListener;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class LivePullActivity extends BaseActivity<LivePullPresenter> implements LivePullConstract.ILivePullView {

    @BindView(R.id.player_view)
    TXCloudVideoView playerView;
    @BindView(R.id.pusher_avatar)
    CircleImageView pusherAvatar;
    @BindView(R.id.pusher_shop_name)
    TextView pusherShopName;
    @BindView(R.id.watch)
    TextView watch;
    @BindView(R.id.focus)
    Button focus;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.caht_recyclerview)
    RecyclerView cahtRecyclerview;
    @BindView(R.id.chat_input)
    EditText chatInput;
    @BindView(R.id.send)
    TextView send;
    @BindView(R.id.shopping)
    ImageView shopping;
    private TXLivePlayer livePlayer;
    private LiveChatAdapter adapter;
    private List<LiveChatBean> list;
    private TIMConversation conversation;
    private String groupId="@TGS#a5GDQRJGE";

    @Override
    protected int getLayout() {
        return R.layout.activity_live_pull;
    }

    @Override
    protected LivePullPresenter initPresent() {
        return new LivePullPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back_rd);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保持屏幕常亮
        conversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, groupId);
        livePlayer = new TXLivePlayer(this);
        list = new ArrayList<>();
        list.add(new LiveChatBean("0", "系统通知", "倡导文明直播，诚信交易，将会对内容进行24小时的在线巡查。任何传播违法、违规、低俗、暴力等不良信息的行为将会导致账号封停。"));
        adapter = new LiveChatAdapter(this, list);
        cahtRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        cahtRecyclerview.setAdapter(adapter);
    }
    @Override
    public void initDataSuccess(LivePullBean bean) {
        groupId=bean.getGroupid();
        pusherShopName.setText(bean.getShop_name());
        GlideApp.with(this)
                .load(AppConstant.BASE_URL+bean.getShop_portrait())
                .placeholder(R.drawable.logo_default)
                .override(50,50)
                .into(pusherAvatar);
        presenter.IMLogin(Sp.getString(this, AppConstant.USER_ACCOUNT_ID),Sp.getString(this, AppConstant.USERSIG),bean.getGroupid());
    }

    @Override
    public void initLivePull() {
        livePlayer.setPlayerView(playerView);
        livePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        livePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        TXLivePlayConfig livePlayConfig = new TXLivePlayConfig();
        //自动模式
        livePlayConfig.setAutoAdjustCacheTime(true);
        livePlayConfig.setMinAutoAdjustCacheTime(1);
        livePlayConfig.setMaxAutoAdjustCacheTime(5);
        livePlayer.setConfig(livePlayConfig);
        String flvUrl = "http://play.jadeall.cn/live/123.flv";
        livePlayer.startPlay(flvUrl, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐 FLV
    }
    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void receiveMessageSuccess(LiveChatBean chatBean) {
        list.add(chatBean);
        adapter.notifyItemRangeInserted(list.size() - 1, 1);
        ((LinearLayoutManager) cahtRecyclerview.getLayoutManager()).scrollToPositionWithOffset(list.size() - 1, 0);
    }

    @Override
    public void sentMassageSuccess(String msg) {
        LiveChatBean entity = new LiveChatBean(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), Sp.getString(this, AppConstant.USERNAME), msg);
        list.add(entity);
        adapter.notifyItemRangeInserted(list.size() - 1, 1);
        ((LinearLayoutManager) cahtRecyclerview.getLayoutManager()).scrollToPositionWithOffset(list.size() - 1, 0);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(livePlayer!=null){livePlayer.stopPlay(true); }// true 代表清除最后一帧画面
        playerView.onDestroy();
        presenter.quitChatGroup(groupId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.auction_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.more:
                break;
            default:
                break;
        }
        return true;

    }

    @OnClick({R.id.focus,R.id.send, R.id.shopping})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.focus:
                break;
            case R.id.send:
                if(chatInput.getText().toString().length()>0){
                    presenter.sentMassage(chatInput.getText().toString(), conversation);
                    chatInput.setText("");
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null){
                        imm.hideSoftInputFromWindow(chatInput.getWindowToken(), 0);
                    }
                }else{
                    Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.shopping:
                break;
        }
    }
}

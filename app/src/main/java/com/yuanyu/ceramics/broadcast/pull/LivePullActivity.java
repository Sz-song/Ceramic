package com.yuanyu.ceramics.broadcast.pull;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
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
    private TIMGroupManager groupManager;

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
        groupManager = TIMGroupManager.getInstance();
        List<String> user = new ArrayList<>();
        user.add(Sp.getString(this, AppConstant.USER_ACCOUNT_ID));
        groupManager.applyJoinGroup("@TGS#a5GDQRJGE", "", new TIMCallBack() {
                    @Override
                    public void onError(int i, String s) {
                        L.e(s);
                        Toast.makeText(LivePullActivity.this, "加入直播聊天室失败", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess() {
                        Toast.makeText(LivePullActivity.this, "加入直播聊天室成功", Toast.LENGTH_SHORT).show();
                    }
                });
        list = new ArrayList<>();
        list.add(new LiveChatBean("0", "系统通知", "倡导文明直播，诚信交易，将会对内容进行24小时的在线巡查。任何传播违法、违规、低俗、暴力等不良信息的行为将会导致账号封停。"));
        adapter = new LiveChatAdapter(this, list);
        cahtRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        cahtRecyclerview.setAdapter(adapter);
        presenter.initLivePull();
        initLivePull();

    }

    @Override
    public void initLivePull() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保持屏幕常亮
        conversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, "@TGS#a5GDQRJGE");
        livePlayer = new TXLivePlayer(this);
        livePlayer.setPlayListener(new ITXLivePlayListener() {
            @Override
            public void onPlayEvent(int i, Bundle bundle) {

            }

            @Override
            public void onNetStatus(Bundle bundle) {

            }
        });
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
    public void receiveMessageSuccess(List<TIMMessage> TIMMessagelist) {
        L.e(TIMMessagelist.size() + "");
        for (int i = 0; i < TIMMessagelist.size(); i++) {
            if (TIMMessagelist.get(i).getConversation().getPeer().equals("@TGS#a5GDQRJGE")) {
                TIMMessage msg = TIMMessagelist.get(i);
                msg.getSenderProfile(new TIMValueCallBack<TIMUserProfile>() {
                    @Override
                    public void onError(int i, String s) {
                        L.e("资料获取失败");
                    }

                    @Override
                    public void onSuccess(TIMUserProfile timUserProfile) {
                        for (int j = 0; j < msg.getElementCount(); ++j) {
                            TIMElem elem = msg.getElement(j);
                            //获取当前元素的类型
                            TIMElemType elemType = elem.getType();
                            if (elemType == TIMElemType.Text) {
                                TIMTextElem textElem = (TIMTextElem) elem;
                                LiveChatBean chatBean = new LiveChatBean(timUserProfile.getIdentifier(), timUserProfile.getNickName(), textElem.getText());
                                list.add(chatBean);
                                adapter.notifyItemRangeInserted(list.size() - 1, 1);
                                ((LinearLayoutManager) cahtRecyclerview.getLayoutManager()).scrollToPositionWithOffset(list.size() - 1, 0);
                            } else if (elemType == TIMElemType.Image) {
                                //处理图片消息
                            }
                        }
                    }
                });

            }
        }
    }

    @Override
    public void sentMassageSuccess(String msg) {
        LiveChatBean entity = new LiveChatBean(Sp.getString(this, AppConstant.USER_ACCOUNT_ID),
                Sp.getString(this, AppConstant.USERNAME), msg);
        list.add(entity);
        adapter.notifyItemRangeInserted(list.size() - 1, 1);
        ((LinearLayoutManager) cahtRecyclerview.getLayoutManager()).scrollToPositionWithOffset(list.size() - 1, 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        livePlayer.stopPlay(true); // true 代表清除最后一帧画面
        playerView.onDestroy();
        groupManager.quitGroup("@TGS#a5GDQRJGE", new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                L.e(s);
                Toast.makeText(LivePullActivity.this, "退出直播聊天室失败", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onSuccess() {
                Toast.makeText(LivePullActivity.this, "退出直播", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.live_push_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.share:
                presenter.sentMassage("大家好", conversation);
//                TODO
                break;
            default:
                break;
        }
        return true;

    }

    @OnClick({R.id.focus, R.id.chat_input, R.id.send, R.id.shopping})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.focus:
                break;
            case R.id.chat_input:
                break;
            case R.id.send:
                break;
            case R.id.shopping:
                break;
        }
    }
}

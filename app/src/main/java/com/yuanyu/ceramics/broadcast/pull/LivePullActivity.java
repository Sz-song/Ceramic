package com.yuanyu.ceramics.broadcast.pull;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.broadcast.LiveCustomMessage;
import com.yuanyu.ceramics.broadcast.push.LiveItemBean;
import com.yuanyu.ceramics.broadcast.push.LivePopupwindowItem;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.item.ItemDetailAcitivity;
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
    @BindView(R.id.enter_message)
    TextView enterMessage;
    @BindView(R.id.item_image)
    RoundedImageView itemImage;
    @BindView(R.id.item_name)
    TextView itemName;
    @BindView(R.id.item_price)
    TextView itemPrice;
    @BindView(R.id.buy_fast)
    TextView buyFast;
    @BindView(R.id.item)
    RelativeLayout item;
    private TXLivePlayer livePlayer;
    private LiveChatAdapter adapter;
    private List<LiveChatBean> list;
    private TIMConversation conversation;
    private String groupId;
    private String id;
    private String playUrl;
    private int audienceNum = 0;
    private LivePopupwindowItem livePopupwindowItem;
    private List<LiveItemBean> itemList;

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
        id = getIntent().getStringExtra("live_id");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//保持屏幕常亮
        livePlayer = new TXLivePlayer(this);
        list = new ArrayList<>();
        list.add(new LiveChatBean("0", "系统通知", "倡导文明直播，诚信交易，将会对内容进行24小时的在线巡查。任何传播违法、违规、低俗、暴力等不良信息的行为将会导致账号封停。", 0));
        adapter = new LiveChatAdapter(this, list);
        cahtRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        cahtRecyclerview.setAdapter(adapter);
        presenter.initData(id);
    }

    @Override
    public void initDataSuccess(LivePullBean bean) {
        groupId = bean.getGroupid();
        playUrl = bean.getPlayurl();
        pusherShopName.setText(bean.getShop_name());
        GlideApp.with(this)
                .load(AppConstant.BASE_URL + bean.getShop_portrait())
                .placeholder(R.drawable.logo_default)
                .override(50, 50)
                .into(pusherAvatar);
        itemList = new ArrayList<>();
        itemList.addAll(bean.getList());
        if(itemList!=null&&itemList.size()>0){
            presenter.getItemPosition(groupId);
        }else{
            item.setVisibility(View.GONE);
        }
        livePopupwindowItem = new LivePopupwindowItem(this, 0, itemList, bean.getShop_id(), bean.getShop_name(), bean.getShop_portrait());
        presenter.IMLogin(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), Sp.getString(this, AppConstant.USERSIG), Sp.getString(this, AppConstant.USERNAME), bean.getGroupid());
    }

    @Override
    public void initLivePull() {
        L.e("init_push");
        conversation = TIMManager.getInstance().getConversation(TIMConversationType.Group, groupId);
        livePlayer.setPlayerView(playerView);
        livePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
        livePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
        TXLivePlayConfig livePlayConfig = new TXLivePlayConfig();
        //自动模式
        livePlayConfig.setMinAutoAdjustCacheTime(1);
        livePlayConfig.setMaxAutoAdjustCacheTime(5);
        livePlayConfig.setEnableMessage(true);
        livePlayer.setConfig(livePlayConfig);
        if (playUrl != null && playUrl.length() > 0) {
            livePlayer.startPlay(playUrl, TXLivePlayer.PLAY_TYPE_LIVE_FLV); //推荐 FLV
        } else {
            Toast.makeText(this, "播放失败", Toast.LENGTH_SHORT).show();
        }
        LiveCustomMessage enterMsg = new LiveCustomMessage(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), Sp.getString(this, AppConstant.USERNAME) + "进入直播间", 1);
        Gson gson = new Gson();
        String msgJson = gson.toJson(enterMsg);
        presenter.sentMassage(msgJson, conversation, 1);
        presenter.getNumAudience(groupId);
    }

    @Override
    public void getNumAudienceSuccess(int num) {
        audienceNum = num;
        watch.setText(audienceNum + "人");
    }

    @Override
    public void changeItem(String item_id) {
        for(int i=0;i<itemList.size();i++){
            if(itemList.get(i).getId().equals(item_id)){
                item.setVisibility(View.VISIBLE);
                GlideApp.with(this)
                        .load(AppConstant.BASE_URL+itemList.get(i).getImage())
                        .override(100,100)
                        .placeholder(R.drawable.img_default)
                        .into(itemImage);
                itemName.setText(itemList.get(i).getName());
                itemPrice.setText("¥"+itemList.get(i).getPrice());
                item.setOnClickListener(view -> ItemDetailAcitivity.actionStart(this, item_id));
                return;
            }
        }
        item.setVisibility(View.VISIBLE);
        GlideApp.with(this)
                .load(AppConstant.BASE_URL+itemList.get(0).getImage())
                .override(100,100)
                .placeholder(R.drawable.img_default)
                .into(itemImage);
        itemName.setText(itemList.get(0).getName());
        itemPrice.setText("¥"+itemList.get(0).getPrice());
        buyFast.setOnClickListener(view -> ItemDetailAcitivity.actionStart(this, itemList.get(0).getId()));
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void receiveMessageSuccess(LiveChatBean chatBean) {
        if (chatBean.getType() == 0 || chatBean.getType() == 1) {
            list.add(chatBean);
            adapter.notifyItemRangeInserted(list.size() - 1, 1);
            ((LinearLayoutManager) cahtRecyclerview.getLayoutManager()).scrollToPositionWithOffset(list.size() - 1, 0);
        }

        if (chatBean.getType() == 1) {
            audienceNum++;
            watch.setText(audienceNum + "人");
        } else if (chatBean.getType() == 2) {
            audienceNum--;
            if (audienceNum > 0) {
                watch.setText(audienceNum + "人");
            } else {
                presenter.getNumAudience(groupId);
            }
        }else if(chatBean.getType()==3){//更换展示商品
            changeItem(chatBean.getMessage());
        }
    }

    @Override
    public void sentMassageSuccess(String msg, int type) {
        if (type == 0||type == 1) {//聊天
            LiveChatBean entity = new LiveChatBean(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), Sp.getString(this, AppConstant.USERNAME), msg, type);
            list.add(entity);
            adapter.notifyItemRangeInserted(list.size() - 1, 1);
            ((LinearLayoutManager) cahtRecyclerview.getLayoutManager()).scrollToPositionWithOffset(list.size() - 1, 0);
        }
    }


    @OnClick({R.id.focus, R.id.send, R.id.shopping})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.focus:
                break;
            case R.id.send:
                if (chatInput.getText().toString().length() > 0) {
                    if (conversation != null) {
                        presenter.sentMassage(chatInput.getText().toString(), conversation, 0);
                        chatInput.setText("");
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(chatInput.getWindowToken(), 0);
                        }
                    } else {
                        Toast.makeText(this, "发送失败", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "输入不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.shopping:
                if (livePopupwindowItem != null) {
                    livePopupwindowItem.showAtLocation(playerView, Gravity.BOTTOM, 0, 0);
                }
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (livePlayer != null) {
            livePlayer.stopPlay(true);
        }// true 代表清除最后一帧画面
        playerView.onDestroy();
        LiveCustomMessage enterMsg = new LiveCustomMessage(Sp.getString(this, AppConstant.USER_ACCOUNT_ID), Sp.getString(this, AppConstant.USERNAME) + "进入直播间", 2);
        Gson gson = new Gson();
        String msgJson = gson.toJson(enterMsg);
        presenter.sentMassage(msgJson, conversation, 2);
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
}

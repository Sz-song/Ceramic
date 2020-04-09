package com.yuanyu.ceramics.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
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
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMTextElem;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.utils.L;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChatActivity extends BaseActivity<ChatPresenter> implements ChatConstract.IChatView {

    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.voice_input_switch)
    ImageView voiceInputSwitch;
    @BindView(R.id.chat_message_input)
    EditText chatMessageInput;
    @BindView(R.id.chat_voice_input)
    Button chatVoiceInput;
    @BindView(R.id.face_btn)
    ImageView faceBtn;
    @BindView(R.id.more_btn)
    ImageView moreBtn;
    @BindView(R.id.send_btn)
    Button sendBtn;
    private String userid;
    private String username;
    private String protrait;
    private long timediff;
    private ChatAdapter adapter;
    private List<ChatEntity> list;
    private TIMConversation conversation;

    public static void navToChat(Context context, String identify) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra("userid", identify);
        context.startActivity(intent);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_chat;
    }

    @Override
    protected ChatPresenter initPresent() {
        return new ChatPresenter();
    }

    @Override
    protected void initEvent() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.back1_gray);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        list = new ArrayList<>();
        userid = getIntent().getStringExtra("userid");
        conversation = TIMManager.getInstance().getConversation(TIMConversationType.C2C, userid);
        presenter.ConversationInit(conversation,Sp.getString(this,AppConstant.USER_ACCOUNT_ID));
        presenter.getChaterInfo(userid);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(this, list);
        recyclerview.setAdapter(adapter);
        chatMessageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    sendBtn.setVisibility(View.VISIBLE);
                    moreBtn.setVisibility(View.GONE);
                } else {
                    sendBtn.setVisibility(View.GONE);
                    moreBtn.setVisibility(View.VISIBLE);
                }
            }
        });
        if(conversation!=null){
            conversation.setReadMessage(conversation.getLastMsg(), new TIMCallBack() {
                @Override
                public void onError(int i, String s) {L.e(i+s+"已读");}

                @Override
                public void onSuccess() {L.e("已读");}
            });
        }
    }

    @OnClick({R.id.voice_input_switch, R.id.face_btn, R.id.more_btn, R.id.send_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.voice_input_switch:
                break;
            case R.id.face_btn:
                break;
            case R.id.more_btn:
                break;
            case R.id.send_btn:
                if (conversation != null) {
                    presenter.sentMassage(chatMessageInput.getText().toString(), conversation);
                    chatMessageInput.setText("");
                }
                break;
        }
    }

    @Override
    public void getChaterInfoSuccess(ChatBean bean) {
        title.setText(bean.getNickname());
        username = bean.getNickname();
        protrait = bean.getProtrait();
        timediff = bean.getNowtime() * 1000 - (new Date().getTime());
    }

    @Override
    public void sentMassageSuccess(String msg) {
        ChatEntity entity = new ChatEntity(Sp.getString(this, AppConstant.USER_ACCOUNT_ID),
                Sp.getString(this, AppConstant.USERNAME), msg,
                Sp.getString(this, AppConstant.PROTRAIT),
                new Date().getTime(), 0,true);
        list.add(entity);
        adapter.notifyItemRangeInserted(list.size() - 1, 1);
        ((LinearLayoutManager) recyclerview.getLayoutManager()).scrollToPositionWithOffset(list.size() - 1, 0);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void receiveMessageSuccess(String msg,boolean issender) {//接受消息
        ChatEntity entity;
        if(issender){
            entity = new ChatEntity(Sp.getString(this,AppConstant.USER_ACCOUNT_ID), Sp.getString(this,AppConstant.USERNAME), msg, Sp.getString(this,AppConstant.PROTRAIT), new Date().getTime(), 0,issender);
        }else{
            entity = new ChatEntity(userid, username, msg, protrait, new Date().getTime(), 0,issender);
        }
        list.add(entity);
        adapter.notifyItemRangeInserted(list.size() - 1, 1);
        ((LinearLayoutManager) recyclerview.getLayoutManager()).scrollToPositionWithOffset(list.size() - 1, 0);
        conversation.setReadMessage(conversation.getLastMsg(), new TIMCallBack() {
            @Override
            public void onError(int i, String s) {}

            @Override
            public void onSuccess() {}
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

package com.yuanyu.ceramics.chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.base.BaseActivity;
import com.yuanyu.ceramics.utils.Sp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @BindView(R.id.swipe)
    SwipeRefreshLayout swipe;
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
        list=new ArrayList<>();
        userid = getIntent().getStringExtra("userid");
        conversation= TIMManager.getInstance().getConversation(TIMConversationType.C2C, userid);
        recyclerview.setLayoutManager(new LinearLayoutManager(this));
        adapter=new ChatAdapter(this,list);
        recyclerview.setAdapter(adapter);
        chatMessageInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    sendBtn.setVisibility(View.VISIBLE);
                    moreBtn.setVisibility(View.GONE);
                }else{
                    sendBtn.setVisibility(View.GONE);
                    moreBtn.setVisibility(View.VISIBLE);
                }
            }
        });
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
                if(conversation!=null){
                    presenter.sentMassage(chatMessageInput.getText().toString(),conversation);
                }
                break;
        }
    }

    @Override
    public void sentMassageSuccess(String msg) {
        ChatEntity entity=new ChatEntity(Sp.getString(this, AppConstant.USER_ACCOUNT_ID),
                Sp.getString(this, AppConstant.USER_ACCOUNT_ID),msg,
                Sp.getString(this, AppConstant.USER_ACCOUNT_ID),
                new Date().getTime(),0);
        list.add(entity);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}

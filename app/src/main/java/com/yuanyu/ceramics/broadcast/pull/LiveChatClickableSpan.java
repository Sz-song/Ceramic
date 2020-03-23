package com.yuanyu.ceramics.broadcast.pull;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.yuanyu.ceramics.personal_index.PersonalIndexActivity;

import androidx.annotation.NonNull;

public class LiveChatClickableSpan extends ClickableSpan {
    private Context context;
    private String id;
    public LiveChatClickableSpan(Context context,String id) {
        this.context = context;
        this.id=id;
    }
    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }
    @Override
    public void onClick(@NonNull View view) {
        if(id.length()>2){
            PersonalIndexActivity.actionStart(context,id );
        }
    }
}

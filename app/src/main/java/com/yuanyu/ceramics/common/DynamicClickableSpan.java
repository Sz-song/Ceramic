package com.yuanyu.ceramics.common;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

import com.yuanyu.ceramics.personal_index.PersonalIndexActivity;

public class DynamicClickableSpan extends ClickableSpan {
    private Context context;
    private String id;
    public DynamicClickableSpan(Context context,String id) {
        this.context = context;
        this.id=id;
    }
    @Override
    public void updateDrawState(TextPaint ds) {
        ds.setUnderlineText(false);
    }
    @Override
    public void onClick(@NonNull View view) {
        PersonalIndexActivity.actionStart(context,id );
    }
}


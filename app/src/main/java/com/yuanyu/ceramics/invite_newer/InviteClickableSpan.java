package com.yuanyu.ceramics.invite_newer;

import android.content.Context;
import android.content.Intent;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;

import com.yuanyu.ceramics.common.NewerGuideActivity;

public class InviteClickableSpan extends ClickableSpan {
    private Context context;

    public InviteClickableSpan(Context context) {this.context = context; }

    @Override
    public void onClick(@NonNull View widget) {
        Intent intent=new Intent(context, NewerGuideActivity.class);
        intent.putExtra("type",7);
        context.startActivity(intent);
    }
}
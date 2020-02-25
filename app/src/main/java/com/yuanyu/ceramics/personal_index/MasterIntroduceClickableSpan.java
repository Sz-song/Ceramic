package com.yuanyu.ceramics.personal_index;

import android.content.Context;
import android.content.Intent;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import androidx.annotation.NonNull;


public class MasterIntroduceClickableSpan extends ClickableSpan {
    private Context context;
    private String userid;

    MasterIntroduceClickableSpan(Context context, String userid) {
        this.context = context;
        this.userid = userid;
    }

    @Override
    public void updateDrawState(@NonNull TextPaint ds) {
        ds.setUnderlineText(false);
    }
    @Override
    public void onClick(@NonNull View view) {
//        Intent intent=new Intent(context,MasterIntroduceActivity.class);
//        intent.putExtra("userid",userid);
//        context.startActivity(intent);
    }
}

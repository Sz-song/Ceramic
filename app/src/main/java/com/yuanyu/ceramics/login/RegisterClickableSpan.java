package com.yuanyu.ceramics.login;

import android.content.Context;

import androidx.annotation.NonNull;
import android.text.style.ClickableSpan;
import android.view.View;



public class RegisterClickableSpan extends ClickableSpan {
    private Context context;

    public RegisterClickableSpan(Context context) {this.context = context; }

    @Override
    public void onClick(@NonNull View widget) {
//        Intent intent=new Intent(context,NewerGuideActivity.class);
//        intent.putExtra("type",5);
//        context.startActivity(intent);
    }
}

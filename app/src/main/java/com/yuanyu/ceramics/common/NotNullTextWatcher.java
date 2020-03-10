package com.yuanyu.ceramics.common;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

public class NotNullTextWatcher implements TextWatcher {
    private View view;

    public NotNullTextWatcher(View view) {
        this.view = view;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if(editable.toString().length()>0){
            view.setVisibility(View.GONE);
        }else {
            view.setVisibility(View.VISIBLE);
        }
    }
}

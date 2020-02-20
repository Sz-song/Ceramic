package com.yuanyu.ceramics.common;
import android.content.Context;
import androidx.recyclerview.widget.GridLayoutManager;

public class CantScrollGirdLayoutManager extends GridLayoutManager {
    public CantScrollGirdLayoutManager(Context context, int spanCount) {
        super(context, spanCount);
    }
    @Override
    public boolean canScrollVertically() { return false; }
    @Override
    public boolean canScrollHorizontally() { return false;}
}


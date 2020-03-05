package com.yuanyu.ceramics.item;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class CheckOrderDecoration extends RecyclerView.ItemDecoration {
    private int space;
    private int startPosition;
    public CheckOrderDecoration(int space, int startPosition) {
        this.space = space;
        this.startPosition=startPosition;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);
        //在广告部分才生效
        if(pos>=startPosition){
            outRect.top = space/2;
            outRect.bottom=space/2;
            if ((pos-startPosition)%2==1) {
                outRect.left = space/2;
                outRect.right = space;
            } else {
                outRect.left = space;
                outRect.right = space/2;
            }
        }
    }
}

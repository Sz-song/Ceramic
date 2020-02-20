package com.yuanyu.ceramics.common;
import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class CommonDecoration extends RecyclerView.ItemDecoration  {
    private int space;
    public CommonDecoration(int space) {
        this.space = space;
    }
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int pos = parent.getChildAdapterPosition(view);
        outRect.top = space/2;
        outRect.bottom=space/2;
        if (pos%2==1) {
            outRect.left = space/2;
            outRect.right = space;
        } else {
            outRect.left = space;
            outRect.right = space/2;
        }

    }
}

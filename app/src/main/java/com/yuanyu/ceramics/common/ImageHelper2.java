package com.yuanyu.ceramics.common;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class ImageHelper2 extends ItemTouchHelper.Callback {
    private int dragFlags;
    private int swipeFlags;
    private RecyclerView.Adapter adapter;
    private List<PhotoVideoBean> images;//图片经过压缩处理

    public ImageHelper2(RecyclerView.Adapter adapter, List<PhotoVideoBean> images) {
        this.adapter = adapter;
        this.images = images;
    }

    /**
     * 设置item是否处理拖拽事件和滑动事件，以及拖拽和滑动操作的方向
     *
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //判断 recyclerView的布局管理器数据
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {//设置能拖拽的方向
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            swipeFlags = 0;//0则不响应事件
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    /**
     * 当用户从item原来的位置拖动可以拖动的item到新位置的过程中调用
     *
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return
     */
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();//得到item原来的position
        int toPosition = target.getAdapterPosition();//得到目标position
        if (toPosition == 0) return true;
        if (images.get(images.size()-1).getType() == 0){
            if (toPosition == images.size() - 1 || images.size() - 1 == fromPosition) {
                return true;
            }
        }
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(images, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(images, i, i - 1);
            }
        }
        adapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }
    /**
     * 设置是否支持长按拖拽
     *
     * @return
     */
    @Override
    public boolean isLongPressDragEnabled() {
        return false;
    }

    /**
     * @param viewHolder
     * @param direction
     */
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
    /**
     * 当用户与item的交互结束并且item也完成了动画时调用
     *
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        adapter.notifyDataSetChanged();
    }





    /**
     * 当长按选中item的时候（拖拽开始的时候）调用
     *
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 设置手指离开后ViewHolder的动画时间，在用户手指离开后调用
     *
     * @param recyclerView
     * @param animationType
     * @param animateDx
     * @param animateDy
     * @return
     */
    @Override
    public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        //手指放开
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }


}

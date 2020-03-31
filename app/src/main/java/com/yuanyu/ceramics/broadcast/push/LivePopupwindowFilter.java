package com.yuanyu.ceramics.broadcast.push;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;
import android.widget.Toast;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;
import com.yuanyu.ceramics.master.FlowLayoutManager;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

class LivePopupwindowFilter extends PopupWindow {
    private Context context;
    private View view;
    private RecyclerView recyclerview;
    private OnPositionClickListener onPositionClickListener;
    private List<String> list;
    private LiveFilterAdapter adapter;

    void setOnPositionClickListener(OnPositionClickListener onPositionClickListener) {
        this.onPositionClickListener = onPositionClickListener;
    }

    LivePopupwindowFilter(Context context) {
        super(context);
        this.context = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_live_filter, null);
        this.recyclerview=view.findViewById(R.id.recyclerview);
        list=new ArrayList<>();
        adapter=new LiveFilterAdapter(context,list,0);
        initList();
        this.setOutsideTouchable(true);//外部点击消失
        this.setContentView(this.view);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x004000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.dialogAnimStyle);
        recyclerview.setLayoutManager(new FlowLayoutManager(context,true));
        recyclerview.setAdapter(adapter);
        adapter.setClickListener(position -> onPositionClickListener.callback(position));
    }

    private void initList() {
        list.add("无\u3000");
        list.add("标准");
        list.add("樱红");
        list.add("云裳");
        list.add("纯真");
        list.add("白兰");
        list.add("元气");
        list.add("超脱");
        list.add("香氛");
        list.add("美白");
        list.add("浪漫");
        list.add("清新");
        list.add("唯美");
        list.add("粉嫩");
        list.add("怀旧");
        list.add("蓝调");
        list.add("清凉");
        list.add("日系");
    }
}

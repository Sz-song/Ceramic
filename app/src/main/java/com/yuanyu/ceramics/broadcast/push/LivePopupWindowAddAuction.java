package com.yuanyu.ceramics.broadcast.push;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.SquareImageView;
import com.yuanyu.ceramics.global.GlideApp;

public class LivePopupWindowAddAuction extends PopupWindow implements View.OnClickListener {
    private View view;
    private Context mContext;
    private EditText nameEt,priceEt,bidEt;
    private SquareImageView coverImg;
    private ImageView refresh;
    private TextView cancel,submit;
    private AddAuctionCallback callback;
    private TextView time1,time3,time5,postpone0,postpone15,postpone30;
    private int time = -1;
    private int postponeTime = -1;
    public LivePopupWindowAddAuction(Context context) {
        super(context);
        mContext = context;
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_add_auction, null);
        nameEt = view.findViewById(R.id.name_et);
        priceEt = view.findViewById(R.id.price_et);
        bidEt = view.findViewById(R.id.bid_et);
        coverImg = view.findViewById(R.id.add_image);
        refresh = view.findViewById(R.id.refresh);
        refresh.setOnClickListener(view -> callback.refresh());
        time1 = view.findViewById(R.id.time_1);
        time3 = view.findViewById(R.id.time_3);
        time5 = view.findViewById(R.id.time_5);
        postpone0 = view.findViewById(R.id.postpone_0);
        postpone15 = view.findViewById(R.id.postpone_15);
        postpone30 = view.findViewById(R.id.postpone_30);
        time1.setOnClickListener(this);
        time3.setOnClickListener(this);
        time5.setOnClickListener(this);
        postpone0.setOnClickListener(this);
        postpone15.setOnClickListener(this);
        postpone30.setOnClickListener(this);

        cancel=view.findViewById(R.id.cancel);
        submit=view.findViewById(R.id.submit);
        cancel.setActivated(false);
        submit.setEnabled(true);
        submit.setActivated(true);
        this.setOutsideTouchable(true);//外部点击消失
        this.setContentView(this.view);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x004000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.dialogAnimStyle);

        cancel.setOnClickListener(view -> dismiss());
        submit.setOnClickListener(view -> {
            if (nameEt.getText().toString().trim().equals("")) {
                Toast.makeText(mContext, "名称不能为空", Toast.LENGTH_SHORT).show();
                return;
            } else if (priceEt.getText().toString().trim().equals("")) {
                Toast.makeText(mContext, "起拍价不能为空", Toast.LENGTH_SHORT).show();
                return;
            } else if (bidEt.getText().toString().trim().equals("")){
                Toast.makeText(mContext, "加价幅度不能为空", Toast.LENGTH_SHORT).show();
                return;
            } else if (Integer.parseInt(bidEt.getText().toString()) <= 0){
                Toast.makeText(mContext, "加价幅度需大于0", Toast.LENGTH_SHORT).show();
                return;
            }else if (time < 0){
                Toast.makeText(mContext, "请选择拍卖时长", Toast.LENGTH_SHORT).show();
                return;
            }else if (postponeTime < 0){
                Toast.makeText(mContext, "请选择延时结拍时长", Toast.LENGTH_SHORT).show();
                return;
            }
            submit.setEnabled(false);
            callback.submit(nameEt.getText().toString(),priceEt.getText().toString(),bidEt.getText().toString(),time,postponeTime);
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.time_1:
                time1.setTextColor(mContext.getResources().getColor(R.color.gold));
                time3.setTextColor(mContext.getResources().getColor(R.color.gray));
                time5.setTextColor(mContext.getResources().getColor(R.color.gray));
                time = 1;
                break;
            case R.id.time_3:
                time1.setTextColor(mContext.getResources().getColor(R.color.gray));
                time3.setTextColor(mContext.getResources().getColor(R.color.gold));
                time5.setTextColor(mContext.getResources().getColor(R.color.gray));
                time = 3;
                break;
            case R.id.time_5:
                time1.setTextColor(mContext.getResources().getColor(R.color.gray));
                time3.setTextColor(mContext.getResources().getColor(R.color.gray));
                time5.setTextColor(mContext.getResources().getColor(R.color.gold));
                time = 5;
                break;
            case R.id.postpone_0:
                postpone0.setTextColor(mContext.getResources().getColor(R.color.gold));
                postpone15.setTextColor(mContext.getResources().getColor(R.color.gray));
                postpone30.setTextColor(mContext.getResources().getColor(R.color.gray));
                postponeTime = 0;
                break;
            case R.id.postpone_15:
                postpone0.setTextColor(mContext.getResources().getColor(R.color.gray));
                postpone15.setTextColor(mContext.getResources().getColor(R.color.gold));
                postpone30.setTextColor(mContext.getResources().getColor(R.color.gray));
                postponeTime = 15;
                break;
            case R.id.postpone_30:
                postpone0.setTextColor(mContext.getResources().getColor(R.color.gray));
                postpone15.setTextColor(mContext.getResources().getColor(R.color.gray));
                postpone30.setTextColor(mContext.getResources().getColor(R.color.gold));
                postponeTime = 30;
                break;
        }

    }

    public interface  AddAuctionCallback{
        void refresh();
        void submit(String name, String price, String bid, int time, int postponeTime);
    }
    public void setImage(Bitmap bitmap){
        GlideApp.with(mContext).load(bitmap).override(coverImg.getWidth(), coverImg.getHeight()).into(coverImg);
    }

    public void setCallback(AddAuctionCallback callback) {
        this.callback = callback;
    }
}

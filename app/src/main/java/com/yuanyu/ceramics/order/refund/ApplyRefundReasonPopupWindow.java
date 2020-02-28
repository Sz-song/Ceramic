package com.yuanyu.ceramics.order.refund;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.OnPositionClickListener;
import com.yuanyu.ceramics.common.view.SmoothCheckBox;

public class ApplyRefundReasonPopupWindow extends PopupWindow {
    private TextView noReasonTxt;
    private TextView whyCantChoose;
    private SmoothCheckBox noReasonCheck;
    private LinearLayout noReason;
    private SmoothCheckBox poorQualityCheck;
    private LinearLayout poorQuality;
    private SmoothCheckBox falseDeliveryCheck;
    private LinearLayout falseDelivery;
    private SmoothCheckBox describeDiscrepancyCheck;
    private LinearLayout describeDiscrepancy;

    private TextView cancel;
    private OnPositionClickListener onReasonCallback;

    public void setOnStringCallback(OnPositionClickListener onReasonCallback) {
        this.onReasonCallback = onReasonCallback;
    }

    public ApplyRefundReasonPopupWindow(Context context, boolean ischoose, int position) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.popup_refund_reason, null);
        this.setContentView(view);
        noReasonTxt=view.findViewById(R.id.no_reason_txt);
        whyCantChoose=view.findViewById(R.id.why_cant_choose);
        noReasonCheck=view.findViewById(R.id.no_reason_check);
        noReason=view.findViewById(R.id.no_reason);
        poorQualityCheck=view.findViewById(R.id.poor_quality_check);
        poorQuality=view.findViewById(R.id.poor_quality);
        falseDeliveryCheck=view.findViewById(R.id.false_delivery_check);
        falseDelivery=view.findViewById(R.id.false_delivery);
        describeDiscrepancyCheck=view.findViewById(R.id.describe_discrepancy_check);
        describeDiscrepancy=view.findViewById(R.id.describe_discrepancy);
        cancel=view.findViewById(R.id.cancel);
        this.setOutsideTouchable(true);//外部点击消失
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x004000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.dialogAnimStyle);
        WindowManager.LayoutParams lp = ((Activity)context).getWindow().getAttributes();
        lp.alpha = 0.7f; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
        this.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 =((Activity) context).getWindow().getAttributes();
            lp1.alpha = 1f; // 0.0-1.0
            ((Activity) context).getWindow().setAttributes(lp1);
        });
        if(ischoose){
            noReasonTxt.setTextColor(context.getResources().getColor(R.color.blackLight));
            whyCantChoose.setText("");
        }else{
            noReasonTxt.setTextColor(context.getResources().getColor(R.color.lightGray));
            whyCantChoose.setText("时间已过");
        }
        if(position==0){
            noReasonCheck.setChecked(true);
        }else if(position==1){
            poorQualityCheck.setChecked(true);
        }else if(position==2){
            falseDeliveryCheck.setChecked(true);
        }else if(position==3){
            describeDiscrepancyCheck.setChecked(true);
        }
        noReason.setOnClickListener(v -> {
            if(ischoose){
                setCheckBox();
                noReasonCheck.setChecked(true);
                onReasonCallback.callback(0);
            }else{
                Toast.makeText(context, "时间已过", Toast.LENGTH_SHORT).show();
            }
        });
        noReasonCheck.setOnClickListener(v -> {
            if(ischoose){
                setCheckBox();
                noReasonCheck.setChecked(true);
                onReasonCallback.callback(0);
            }else{
                Toast.makeText(context, "时间已过", Toast.LENGTH_SHORT).show();
            }
        });
        poorQuality.setOnClickListener(v -> {
            setCheckBox();
            poorQualityCheck.setChecked(true);
            onReasonCallback.callback(1);
        });
        poorQualityCheck.setOnClickListener(v -> {
            setCheckBox();
            poorQualityCheck.setChecked(true);
            onReasonCallback.callback(1);
        });
        falseDelivery.setOnClickListener(v -> {
            setCheckBox();
            falseDeliveryCheck.setChecked(true);
            onReasonCallback.callback(2);
        });
        falseDeliveryCheck.setOnClickListener(v -> {
            setCheckBox();
            falseDeliveryCheck.setChecked(true);
            onReasonCallback.callback(2);
        });
        describeDiscrepancy.setOnClickListener(v -> {
            setCheckBox();
            describeDiscrepancyCheck.setChecked(true);
            onReasonCallback.callback(3);
        });
        describeDiscrepancyCheck.setOnClickListener(v -> {
            setCheckBox();
            describeDiscrepancyCheck.setChecked(true);
            onReasonCallback.callback(3);
        });
        cancel.setOnClickListener(v ->dismiss());
    }

    private void setCheckBox() {
        noReasonCheck.setChecked(false);
        poorQualityCheck.setChecked(false);
        falseDeliveryCheck.setChecked(false);
        describeDiscrepancyCheck.setChecked(false);
    }
}

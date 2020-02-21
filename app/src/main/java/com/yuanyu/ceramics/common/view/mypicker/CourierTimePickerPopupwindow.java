package com.yuanyu.ceramics.common.view.mypicker;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.utils.TimeUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CourierTimePickerPopupwindow extends PopupWindow {

    private Context context;
    private View view;
    private TextView today,tomorrow,dayAfterTomorrow;
    private RecyclerView recyclerveiw;
    private String start_now;
    private List<CourierTimeBean> list;
    private int day;
    private CourierTimePickerClickListener clickListener;

    public void setClickListener(CourierTimePickerClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public CourierTimePickerPopupwindow(@NonNull Context context, String start_now) {
        super(context);
        this.context = context;
        this.start_now=start_now;
        this.view = LayoutInflater.from(context).inflate(R.layout.popup_courier_time_picker, null);
        today=view.findViewById(R.id.today);
        tomorrow=view.findViewById(R.id.tomorrow);
        dayAfterTomorrow=view.findViewById(R.id.day_after_tomorrow);
        recyclerveiw=view.findViewById(R.id.recyclerview);
        list=new ArrayList<>();
        initView();
        this.setOutsideTouchable(true);//外部点击消失
        this.setContentView(this.view);
        this.setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0x004000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.dialogAnimStyle);
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.7f; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
        this.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = ((Activity) context).getWindow().getAttributes();
            lp1.alpha = 1f; // 0.0-1.0
            ((Activity) context).getWindow().setAttributes(lp1);
        });
    }
    private void initView() {
        LinearLayoutManager manager=new LinearLayoutManager(context);
        recyclerveiw.setLayoutManager(manager);
        CourierTimePickerAdapter adapter=new CourierTimePickerAdapter(context,list);
        recyclerveiw.setAdapter(adapter);
        long todaytime = Long.parseLong(start_now + "000");
        long tomorrowtime = Long.parseLong(start_now + "000")+(24*3600*1000);
        long dayAfterTomorrowtime = Long.parseLong(start_now + "000")+(48*3600*1000);
        Date tadaydate = new Date(todaytime);
        Date tomorrowdate = new Date(tomorrowtime);
        Date dayAfterTomorrowdate = new Date(dayAfterTomorrowtime);
        String todaystr=(1900 + tadaydate.getYear()) + "-" + (tadaydate.getMonth() + 1) + "-" + tadaydate.getDate() + " ";
        String tomorrow_str=(1900 + tomorrowdate.getYear()) + "-" + (tomorrowdate.getMonth() + 1) + "-" + tomorrowdate.getDate() + " ";
        String dayAfterTomorrow_str=(1900 + dayAfterTomorrowdate.getYear()) + "-" + (dayAfterTomorrowdate.getMonth() + 1) + "-" + dayAfterTomorrowdate.getDate() + " ";
        int now=tadaydate.getHours();
        if(now>=0&&now<9){
            day=0;
            today.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            tomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            dayAfterTomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            list.add(new CourierTimeBean("09:00-11:00",false));
            list.add(new CourierTimeBean("11:00-13:00",false));
            list.add(new CourierTimeBean("13:00-15:00",false));
            list.add(new CourierTimeBean("15:00-17:00",false));
            list.add(new CourierTimeBean("17:00-19:00",false));
            adapter.notifyDataSetChanged();
        }else if(now>=9&&now<11){
            day=0;
            today.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            tomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            dayAfterTomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            list.add(new CourierTimeBean("2小时上门",false));
            list.add(new CourierTimeBean("09:00-11:00",true));
            list.add(new CourierTimeBean("11:00-13:00",false));
            list.add(new CourierTimeBean("13:00-15:00",false));
            list.add(new CourierTimeBean("15:00-17:00",false));
            list.add(new CourierTimeBean("17:00-19:00",false));
            adapter.notifyDataSetChanged();
        }else if(now>=11&&now<13){
            day=0;
            today.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            tomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            dayAfterTomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            list.add(new CourierTimeBean("2小时上门",false));
            list.add(new CourierTimeBean("09:00-11:00",true));
            list.add(new CourierTimeBean("11:00-13:00",true));
            list.add(new CourierTimeBean("13:00-15:00",false));
            list.add(new CourierTimeBean("15:00-17:00",false));
            list.add(new CourierTimeBean("17:00-19:00",false));
            adapter.notifyDataSetChanged();
        }else if(now>=13&&now<15){
            day=0;
            today.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            tomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            dayAfterTomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            list.add(new CourierTimeBean("2小时上门",false));
            list.add(new CourierTimeBean("09:00-11:00",true));
            list.add(new CourierTimeBean("11:00-13:00",true));
            list.add(new CourierTimeBean("13:00-15:00",true));
            list.add(new CourierTimeBean("15:00-17:00",false));
            list.add(new CourierTimeBean("17:00-19:00",false));
            adapter.notifyDataSetChanged();
        }else if(now>=15&&now<17){
            day=0;
            today.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            tomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            dayAfterTomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            list.add(new CourierTimeBean("2小时上门",false));
            list.add(new CourierTimeBean("09:00-11:00",true));
            list.add(new CourierTimeBean("11:00-13:00",true));
            list.add(new CourierTimeBean("13:00-15:00",true));
            list.add(new CourierTimeBean("15:00-17:00",true));
            list.add(new CourierTimeBean("17:00-19:00",false));
            adapter.notifyDataSetChanged();
        }else if(now>=17&&now<19){
            day=0;
            today.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            tomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            dayAfterTomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            list.add(new CourierTimeBean("2小时上门",false));
            list.add(new CourierTimeBean("09:00-11:00",true));
            list.add(new CourierTimeBean("11:00-13:00",true));
            list.add(new CourierTimeBean("13:00-15:00",true));
            list.add(new CourierTimeBean("15:00-17:00",true));
            list.add(new CourierTimeBean("17:00-19:00",true));
            adapter.notifyDataSetChanged();
        }else if(now>=19){
            day=1;
            today.setTextColor(context.getResources().getColor(R.color.blackLight));
            tomorrow.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            dayAfterTomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            list.add(new CourierTimeBean("09:00-11:00",false));
            list.add(new CourierTimeBean("11:00-13:00",false));
            list.add(new CourierTimeBean("13:00-15:00",false));
            list.add(new CourierTimeBean("15:00-17:00",false));
            list.add(new CourierTimeBean("17:00-19:00",false));
            adapter.notifyDataSetChanged();
        }
        today.setOnClickListener(view -> {
            list.clear();
            adapter.notifyDataSetChanged();
            day=0;
            today.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            tomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            dayAfterTomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            if(now>=0&&now<9){
                list.add(new CourierTimeBean("09:00-11:00",false));
                list.add(new CourierTimeBean("11:00-13:00",false));
                list.add(new CourierTimeBean("13:00-15:00",false));
                list.add(new CourierTimeBean("15:00-17:00",false));
                list.add(new CourierTimeBean("17:00-19:00",false));
                adapter.notifyDataSetChanged();
            }else if(now>=9&&now<11){
                list.add(new CourierTimeBean("2小时上门",false));
                list.add(new CourierTimeBean("09:00-11:00",true));
                list.add(new CourierTimeBean("11:00-13:00",false));
                list.add(new CourierTimeBean("13:00-15:00",false));
                list.add(new CourierTimeBean("15:00-17:00",false));
                list.add(new CourierTimeBean("17:00-19:00",false));
                adapter.notifyDataSetChanged();
            }else if(now>=11&&now<13){
                list.add(new CourierTimeBean("2小时上门",false));
                list.add(new CourierTimeBean("09:00-11:00",true));
                list.add(new CourierTimeBean("11:00-13:00",true));
                list.add(new CourierTimeBean("13:00-15:00",false));
                list.add(new CourierTimeBean("15:00-17:00",false));
                list.add(new CourierTimeBean("17:00-19:00",false));
                adapter.notifyDataSetChanged();
            }else if(now>=13&&now<15){
                list.add(new CourierTimeBean("2小时上门",false));
                list.add(new CourierTimeBean("09:00-11:00",true));
                list.add(new CourierTimeBean("11:00-13:00",true));
                list.add(new CourierTimeBean("13:00-15:00",true));
                list.add(new CourierTimeBean("15:00-17:00",false));
                list.add(new CourierTimeBean("17:00-19:00",false));
                adapter.notifyDataSetChanged();
            }else if(now>=15&&now<17){
                list.add(new CourierTimeBean("2小时上门",false));
                list.add(new CourierTimeBean("09:00-11:00",true));
                list.add(new CourierTimeBean("11:00-13:00",true));
                list.add(new CourierTimeBean("13:00-15:00",true));
                list.add(new CourierTimeBean("15:00-17:00",true));
                list.add(new CourierTimeBean("17:00-19:00",false));
                adapter.notifyDataSetChanged();
            }else if(now>=17&&now<19){
                list.add(new CourierTimeBean("2小时上门",false));
                list.add(new CourierTimeBean("09:00-11:00",true));
                list.add(new CourierTimeBean("11:00-13:00",true));
                list.add(new CourierTimeBean("13:00-15:00",true));
                list.add(new CourierTimeBean("15:00-17:00",true));
                list.add(new CourierTimeBean("17:00-19:00",true));
                adapter.notifyDataSetChanged();
            }else if(now>=19){
                list.add(new CourierTimeBean("09:00-11:00",true));
                list.add(new CourierTimeBean("11:00-13:00",true));
                list.add(new CourierTimeBean("13:00-15:00",true));
                list.add(new CourierTimeBean("15:00-17:00",true));
                list.add(new CourierTimeBean("17:00-19:00",true));
                adapter.notifyDataSetChanged();
            }
        });
        tomorrow.setOnClickListener(view -> {
            list.clear();
            adapter.notifyDataSetChanged();
            day=1;
            today.setTextColor(context.getResources().getColor(R.color.blackLight));
            tomorrow.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            dayAfterTomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            list.add(new CourierTimeBean("09:00-11:00",false));
            list.add(new CourierTimeBean("11:00-13:00",false));
            list.add(new CourierTimeBean("13:00-15:00",false));
            list.add(new CourierTimeBean("15:00-17:00",false));
            list.add(new CourierTimeBean("17:00-19:00",false));
            adapter.notifyDataSetChanged();
        });
        dayAfterTomorrow.setOnClickListener(view -> {
            list.clear();
            adapter.notifyDataSetChanged();
            day=2;
            today.setTextColor(context.getResources().getColor(R.color.blackLight));
            tomorrow.setTextColor(context.getResources().getColor(R.color.blackLight));
            dayAfterTomorrow.setTextColor(context.getResources().getColor(R.color.colorPrimary));
            list.add(new CourierTimeBean("09:00-11:00",false));
            list.add(new CourierTimeBean("11:00-13:00",false));
            list.add(new CourierTimeBean("13:00-15:00",false));
            list.add(new CourierTimeBean("15:00-17:00",false));
            list.add(new CourierTimeBean("17:00-19:00",false));
            adapter.notifyDataSetChanged();
        });
        adapter.setOnPositionClickListener(position -> {
            if(list.get(position).getTime().equals("09:00-11:00")){
                if(day==0){
                    clickListener.timepickerClick(todaystr+"09:00:00",todaystr+"11:00:00","9点-11点");//yyyy-MM-dd HH:mm:ss
                }else if(day==1){
                    clickListener.timepickerClick(tomorrow_str+"09:00:00",tomorrow_str+"11:00:00","明天9点-11点");
                }else if(day==2){
                    clickListener.timepickerClick(dayAfterTomorrow_str+"09:00:00",dayAfterTomorrow_str+"11:00:00","后天9点-11点");
                }
            }else if(list.get(position).getTime().equals("11:00-13:00")){
                if(day==0){
                    clickListener.timepickerClick(todaystr+"11:00:00",todaystr+"13:00:00","11点-13点");
                }else if(day==1){
                    clickListener.timepickerClick(tomorrow_str+"11:00:00",tomorrow_str+"13:00:00","明天11点-13点");
                }else if(day==2){
                    clickListener.timepickerClick(dayAfterTomorrow_str+"11:00:00",dayAfterTomorrow_str+"13:00:00","后天11点-13点");
                }
            }else if(list.get(position).getTime().equals("13:00-15:00")){
                if(day==0){
                    clickListener.timepickerClick(todaystr+"13:00:00",todaystr+"15:00:00","13点-15点");
                }else if(day==1){
                    clickListener.timepickerClick(tomorrow_str+"13:00:00",tomorrow_str+"15:00:00","明天13点-15点");
                }else if(day==2){
                    clickListener.timepickerClick(dayAfterTomorrow_str+"13:00:00",dayAfterTomorrow_str+"15:00:00","后天13点-15点");
                }
            }else if(list.get(position).getTime().equals("15:00-17:00")){
                if(day==0){
                    clickListener.timepickerClick(todaystr+"15:00:00",todaystr+"17:00:00","15点-17点");
                }else if(day==1){
                    clickListener.timepickerClick(tomorrow_str+"15:00:00",tomorrow_str+"17:00:00","明天15点-17点");
                }else if(day==2){
                    clickListener.timepickerClick(dayAfterTomorrow_str+"15:00:00",dayAfterTomorrow_str+"17:00:00","后天15点-17点");
                }
            }else if(list.get(position).getTime().equals("17:00-19:00")){
                if(day==0){
                    clickListener.timepickerClick(todaystr+"17:00:00",todaystr+"19:00:00","17点-19点");
                }else if(day==1){
                    clickListener.timepickerClick(tomorrow_str+"17:00:00",tomorrow_str+"19:00:00","明天17点-19点");
                }else if(day==2){
                    clickListener.timepickerClick(dayAfterTomorrow_str+"17:00:00",dayAfterTomorrow_str+"19:00:00","后天17点-19点");
                }
            }else if(list.get(position).getTime().equals("2小时上门")){
                clickListener.timepickerClick(TimeUtils.CountTime(start_now)+":00",TimeUtils.CountTime((Long.parseLong(start_now)+7200)+"")+":00","2小时上门");
            }
            dismiss();
        });
    }

}

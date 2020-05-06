package com.yuanyu.ceramics.seller.evaluationmanage;

import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

/**
 * Created by Administrator on 2018-08-23.
 */

public class EvalutionDetailAdapter extends RecyclerView.Adapter {
    private Context context;
    private EvaluationManageBean bean;

    public EvalutionDetailAdapter(Context context, EvaluationManageBean bean) {
        this.context = context;
        this.bean = bean;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else if (position > 0 && position < bean.getPic_list().size() + 1) {
            return 2;
        } else if (position > bean.getPic_list().size()) {
            return 3;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == 1) {
            view = LayoutInflater.from(context).inflate(R.layout.cell_evalutiondetail0, parent, false);
            return new ViewHolder0(view);
        } else if (viewType == 2) {
            view = LayoutInflater.from(context).inflate(R.layout.cell_evalutiondetail1, parent, false);
            return new ViewHolder1(view);
        } else if (viewType == 3) {
            view = LayoutInflater.from(context).inflate(R.layout.cell_evalutiondetail2, parent, false);
            return new ViewHolder2(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder0){
            GlideApp.with(context)
                    .load(BASE_URL+bean.getImage())
                    .placeholder(R.drawable.img_default)
                    .into(((ViewHolder0) holder).image);
            ((ViewHolder0) holder).name.setText(bean.getName());
            ((ViewHolder0) holder).time.setText(CountTime(bean.getTime()));
            ((ViewHolder0) holder).productName.setText(bean.getProductname());
            SpannableString spannableString = new SpannableString("买家的评论："+bean.getEvaleation());
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.blackLight));
            spannableString.setSpan(colorSpan, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            ((ViewHolder0) holder).evaleation.setText(spannableString);
        } else if(holder instanceof ViewHolder1){
            GlideApp.with(context)
                    .load(BASE_URL+bean.getPic_list().get(position-1))
                    .placeholder(R.drawable.img_default)
                    .into(((ViewHolder1) holder).pic);
        } else if(holder instanceof ViewHolder2){
            if(bean.getReply_txt().length()>0&&bean.getReply_txt()!=null){
                SpannableString spannableString0 = new SpannableString("掌柜的回复："+bean.getReply_txt());
                ForegroundColorSpan colorSpan0 = new ForegroundColorSpan(Color.parseColor("#d0a739"));
                spannableString0.setSpan(colorSpan0, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ((ViewHolder2) holder).reply.setText(spannableString0);
            }
            if(bean.getReply_txt2().length()>0&&bean.getReply_txt2()!=null){
                SpannableString spannableString1= new SpannableString("掌柜的回复："+bean.getReply_txt2());
                ForegroundColorSpan colorSpan1 = new ForegroundColorSpan(Color.parseColor("#d0a739"));
                spannableString1.setSpan(colorSpan1, 0, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ((ViewHolder2) holder).reply2.setText(spannableString1);
            }
            if(bean.getEvaleation2().length()>0&&bean.getEvaleation2()!=null){
                SpannableString spannableString2= new SpannableString("买家追评："+bean.getEvaleation2());
                ForegroundColorSpan colorSpan2 = new ForegroundColorSpan(Color.parseColor("#ff0000"));
                spannableString2.setSpan(colorSpan2, 0, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                ((ViewHolder2) holder).evaleation2.setText(spannableString2);
            }
        }

    }
    private String CountTime(String timestr) {
        try {
            long time = Long.parseLong(timestr + "000");
            Date date = new Date(time);
            if (date.getMinutes() > 9) {
                return ((1900 + date.getYear()) + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":" + date.getMinutes());
            } else {
                return ((1900 + date.getYear()) + "-" + (date.getMonth() + 1) + "-" + date.getDate() + " " + date.getHours() + ":0" + date.getMinutes());
            }
        } catch (Exception e) {
            return e.getMessage();
        }
    }
    @Override
    public int getItemCount() {
        return bean.getPic_list().size() + 2;
    }

    static class ViewHolder0 extends RecyclerView.ViewHolder {
        @BindView(R.id.image)
        CircleImageView image;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.product_name)
        TextView productName;
        @BindView(R.id.evaleation)
        TextView evaleation;
        @BindView(R.id.item)
        LinearLayout item;

        ViewHolder0(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.pic)
        ImageView pic;

        ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.reply)
        TextView reply;
        @BindView(R.id.evaleation2)
        TextView evaleation2;
        @BindView(R.id.reply2)
        TextView reply2;

        ViewHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

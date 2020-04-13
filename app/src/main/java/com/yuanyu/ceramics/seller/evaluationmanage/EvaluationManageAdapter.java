package com.yuanyu.ceramics.seller.evaluationmanage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.ReportActivity;
import com.yuanyu.ceramics.common.SquareImageViewAdapter;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.personal_index.PersonalIndexActivity;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

/**
 * Created by Administrator on 2018-08-22.
 */

public class EvaluationManageAdapter extends RecyclerView.Adapter<EvaluationManageAdapter.ViewHolder> {
    private Context context;
    private List<EvaluationManageBean> list;
    private ReplyOnLintener replyOnLintener;

    public EvaluationManageAdapter(Context context, List<EvaluationManageBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.cell_ecaluationmanage, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        int i = 0;
        int j = 0;
        GlideApp.with(context)
                .load(BASE_URL+list.get(position).getImage())
                .placeholder(R.drawable.img_default)
                .into(holder.image);
        holder.name.setText(list.get(position).getName());
        holder.time.setText(CountTime(list.get(position).getTime()));
        holder.productName.setText(list.get(position).getProductname());
        holder.evaleation.setText("顾客评价：" + list.get(position).getEvaleation());
        holder.replyTxt.setVisibility(View.GONE);
        if (list.get(position).getEvaleation().length() > 0) {
            i++;
        }
        if (list.get(position).getEvaleation2().length() > 0) {
            i++;
        }
        if (list.get(position).getReply_txt2().length() > 0) {
            j++;
        }
        if (list.get(position).getReply_txt().length() > 0) {
            holder.replyTxt.setVisibility(View.VISIBLE);
            holder.replyTxt.setText("掌柜的回复：" + list.get(position).getReply_txt());
            j++;
        }
        if (j < i) {
            holder.reply.setVisibility(View.VISIBLE);
        } else {
            holder.reply.setVisibility(View.GONE);
        }
        GridLayoutManager manager = new GridLayoutManager(context, 3);
        holder.imgRecycle.setLayoutManager(manager);
        SquareImageViewAdapter adapter = new SquareImageViewAdapter(context, list.get(position).getPic_list());
        holder.imgRecycle.setAdapter(adapter);
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EvaluationDetailActivity.actionStart(context, list.get(position));
            }
        });
        holder.imgRecycle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EvaluationDetailActivity.actionStart(context, list.get(position));
            }
        });
        holder.replyTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EvaluationDetailActivity.actionStart(context, list.get(position));
            }
        });
        holder.evaleation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EvaluationDetailActivity.actionStart(context, list.get(position));
            }
        });
        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PersonalIndexActivity.actionStart(context, list.get(position).getUserid()+"");
            }
        });
        holder.report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportActivity.actionStart(context, list.get(position).getId(), 11);
            }
        });
        holder.reply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replyOnLintener.reply(position);
            }
        });
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
        return list.size();
    }
    public interface ReplyOnLintener {
        void reply(int position);
    }
    public void setReplyOnLintener(ReplyOnLintener replyOnLintener) {
        this.replyOnLintener = replyOnLintener;
    }
    static class ViewHolder extends RecyclerView.ViewHolder {
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
        @BindView(R.id.img_recycle)
        RecyclerView imgRecycle;
        @BindView(R.id.reply_txt)
        TextView replyTxt;
        @BindView(R.id.report)
        Button report;
        @BindView(R.id.reply)
        Button reply;
        @BindView(R.id.item)
        LinearLayout item;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

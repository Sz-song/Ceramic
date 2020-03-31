package com.yuanyu.ceramics.common;

import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.personal_index.PersonalIndexActivity;
import com.yuanyu.ceramics.utils.HelpUtils;
import com.yuanyu.ceramics.utils.Sp;
import com.yuanyu.ceramics.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

public class YubaHotRecyclerViewAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<DynamicBean> list;
    private OnPositionClickListener onFocusListenner;
    private OnPositionClickListener onDianzanListenner;
//    private OnPositionClickListener onPinglunListenner;
    private OnPositionClickListener onMoreListenner;
    public YubaHotRecyclerViewAdapter(Context context, List<DynamicBean> list) {
        this.context = context;
        this.list = list;
    }


    public void setOnFocusListenner(OnPositionClickListener onFocusListenner) {
        this.onFocusListenner = onFocusListenner;
    }

    public void setOnDianzanListenner(OnPositionClickListener onDianzanListenner) {
        this.onDianzanListenner = onDianzanListenner;
    }

//    public void setOnPinglunListenner(OnPositionClickListener onPinglunListenner) {
//        this.onPinglunListenner = onPinglunListenner;
//    }

    public void setOnMoreListenner(OnPositionClickListener onMoreListenner) {
        this.onMoreListenner = onMoreListenner;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType() == 1) {
            return 0;
        } else if (list.get(position).getType() == 0) {
            if (list.get(position).getPicture_list().size() == 0) {
                return 1;
            } else if (list.get(position).getPicture_list().size() == 1) {
                return 2;
            } else if (list.get(position).getPicture_list().size() > 1) {
                return 3;
            }
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new ViewHolder0(LayoutInflater.from(context).inflate(R.layout.cell_article, parent, false));
        } else if (viewType == 1) {
            return new ViewHolder1(LayoutInflater.from(context).inflate(R.layout.cell_dynamic0, parent, false));
        } else if (viewType == 2) {
            return new ViewHolder2(LayoutInflater.from(context).inflate(R.layout.cell_dynamic1, parent, false));
        } else if (viewType == 3) {
            return new ViewHolder3(LayoutInflater.from(context).inflate(R.layout.cell_dynamic2, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder0) {
            holder.itemView.setOnClickListener(view -> {
//                Intent intent=new Intent(context,ArticleDetailActivity.class);
//                intent.putExtra("article_id",list.get(position).getId());
//                context.startActivity(intent);
            });
            GlideApp.with(context)
                    .load(BASE_URL + list.get(position).getPortrait())
                    .placeholder(R.drawable.logo_default)
                    .override(100, 100)
                    .into(((ViewHolder0) holder).portrait);
            GlideApp.with(context)
                    .load(BASE_URL + list.get(position).getImagearticle())
                    .placeholder(R.drawable.img_default)
                    .override(400, 400)
                    .into(((ViewHolder0) holder).itemImage);
            ((ViewHolder0) holder).name.setText(list.get(position).getName());
            ((ViewHolder0) holder).time.setText(TimeUtils.CountTime(list.get(position).getTime()));
            ((ViewHolder0) holder).title.setText(list.get(position).getTitle());
            ((ViewHolder0) holder).dianzanNum.setText(list.get(position).getDianzan_num());
            ((ViewHolder0) holder).pinglunNum.setText(list.get(position).getPinglun_num());
            ((ViewHolder0) holder).readNum.setText(HelpUtils.getReadNum(list.get(position).getRead_num())+"  阅读");
            ((ViewHolder0) holder).titleTXT.setText("发布了文章《"+list.get(position).getTitle()+"》");
            if (list.get(position).isIsdianzan()) {
                GlideApp.with(context).load(R.drawable.dianzan_focus).placeholder(R.drawable.img_default).into(((ViewHolder0) holder).dianzanimg);
            } else {
                GlideApp.with(context).load(R.drawable.dianzan).placeholder(R.drawable.img_default).into(((ViewHolder0) holder).dianzanimg);
            }
            if(list.get(position).isIsmaster()){
                ((ViewHolder0) holder).masterCertification.setVisibility(View.VISIBLE);
            }else{
                ((ViewHolder0) holder).masterCertification.setVisibility(View.GONE);
            }
            if (!list.get(position).isIsfocus()) {
                if(list.get(position).getUseraccountid().equals(Sp.getString(context,AppConstant.USER_ACCOUNT_ID))){
                    ((ViewHolder0) holder).isfocus.setVisibility(View.GONE);
                }else {
                    ((ViewHolder0) holder).isfocus.setVisibility(View.VISIBLE);
                }
            } else {
                ((ViewHolder0) holder).isfocus.setVisibility(View.GONE);
            }

            ((ViewHolder0) holder).portrait.setOnClickListener(view -> PersonalIndexActivity.actionStart(context, list.get(position).getUseraccountid()));
//            ((ViewHolder0) holder).pinglun.setOnClickListener(view -> onPinglunListenner.callback(position));
            ((ViewHolder0) holder).dianzan.setOnClickListener(view -> {
                if(null!=Sp.getString(context, AppConstant.MOBILE)&& Sp.getString(context,AppConstant.MOBILE).length()>8) {
                    onDianzanListenner.callback(position);
                }else{
//                    Intent intent = new Intent(context, BindPhoneActivity.class);
//                    intent.putExtra("type",1);
//                    context.startActivity(intent);
                }
            });
            ((ViewHolder0) holder).more.setOnClickListener(view -> onMoreListenner.callback(position));
            ((ViewHolder0) holder).isfocus.setOnClickListener(view -> {
                    if (null != Sp.getString(context, AppConstant.MOBILE) && Sp.getString(context, AppConstant.MOBILE).length() > 8) {
                        onFocusListenner.callback(position);
                    } else {
//                        Intent intent = new Intent(context, BindPhoneActivity.class);
//                        intent.putExtra("type", 1);
//                        context.startActivity(intent);
                    }
                }
            );
        } else if (holder instanceof ViewHolder1) {
            holder.itemView.setOnClickListener(view -> {
//                Intent intent=new Intent(context,DynamicDetailActivity.class);
//                intent.putExtra("dynamic_id",list.get(position).getId());
//                context.startActivity(intent);
            });
            GlideApp.with(context)
                    .load(BASE_URL + list.get(position).getPortrait())
                    .override(100, 100)
                    .placeholder(R.drawable.logo_default)
                    .into(((ViewHolder1) holder).portrait);
            ((ViewHolder1) holder).name.setText(list.get(position).getName());
            ((ViewHolder1) holder).time.setText(TimeUtils.CountTime(list.get(position).getTime()));
            ((ViewHolder1) holder).pinglunNum.setText(list.get(position).getPinglun_num());
            ((ViewHolder1) holder).dianzanNum.setText(list.get(position).getDianzan_num());
            ((ViewHolder1) holder).readNum.setText(HelpUtils.getReadNum(list.get(position).getRead_num())+"  阅读");
            ((ViewHolder1) holder).introduceTxt.setMovementMethod(LinkMovementMethod.getInstance());
            ((ViewHolder1) holder).introduceTxt.setText(parseTxt(list.get(position).getContent()));
            if (list.get(position).isIsdianzan()) {
                GlideApp.with(context).load(R.drawable.dianzan_focus).placeholder(R.drawable.img_default).into(((ViewHolder1) holder).dianzanimg);
            } else {
                GlideApp.with(context).load(R.drawable.dianzan).placeholder(R.drawable.img_default).into(((ViewHolder1) holder).dianzanimg);
            }
            if(list.get(position).isIsmaster()){
                ((ViewHolder1) holder).masterCertification.setVisibility(View.VISIBLE);
            }else{
                ((ViewHolder1) holder).masterCertification.setVisibility(View.GONE);
            }
            ((ViewHolder1) holder).portrait.setOnClickListener(view -> PersonalIndexActivity.actionStart(context, list.get(position).getUseraccountid()));
//            ((ViewHolder1) holder).pinglun.setOnClickListener(view -> onPinglunListenner.callback(position));
            ((ViewHolder1) holder).dianzan.setOnClickListener(view -> onDianzanListenner.callback(position));
            ((ViewHolder1) holder).more.setOnClickListener(view -> onMoreListenner.callback(position));
            ((ViewHolder1) holder).isfocus.setOnClickListener(view -> onFocusListenner.callback(position));
            ((ViewHolder1) holder).introduceTxt.setOnClickListener(view -> {
//                Intent intent=new Intent(context,DynamicDetailActivity.class);
//                intent.putExtra("dynamic_id",list.get(position).getId());
//                context.startActivity(intent);
            });
            if (!list.get(position).isIsfocus()) {
                if(list.get(position).getUseraccountid().equals(Sp.getString(context,AppConstant.USER_ACCOUNT_ID))){
                    ((ViewHolder1) holder).isfocus.setVisibility(View.GONE);
                }else {
                    ((ViewHolder1) holder).isfocus.setVisibility(View.VISIBLE);
                }
            } else {
                ((ViewHolder1) holder).isfocus.setVisibility(View.GONE);
            }
        } else if (holder instanceof ViewHolder2) {
            holder.itemView.setOnClickListener(view -> {
//                Intent intent=new Intent(context,DynamicDetailActivity.class);
//                intent.putExtra("dynamic_id",list.get(position).getId());
//                context.startActivity(intent);
            });
            GlideApp.with(context)
                    .load(BASE_URL + list.get(position).getPortrait())
                    .override(100, 100)
                    .placeholder(R.drawable.logo_default)
                    .into(((ViewHolder2) holder).portrait);
            GlideApp.with(context)
                    .load(BASE_URL + list.get(position).getPicture_list().get(0))
                    .override(400, 600)
                    .placeholder(R.drawable.img_default)
                    .into(((ViewHolder2) holder).images);
            ((ViewHolder2) holder).name.setText(list.get(position).getName());
            ((ViewHolder2) holder).time.setText(TimeUtils.CountTime(list.get(position).getTime()));
            ((ViewHolder2) holder).pinglunNum.setText(list.get(position).getPinglun_num());
            ((ViewHolder2) holder).dianzanNum.setText(list.get(position).getDianzan_num());
            ((ViewHolder2) holder).readNum.setText(HelpUtils.getReadNum(list.get(position).getRead_num())+"  阅读");
            ((ViewHolder2) holder).introduceTxt.setMovementMethod(LinkMovementMethod.getInstance());
            ((ViewHolder2) holder).introduceTxt.setText(parseTxt(list.get(position).getContent()));
            if (!list.get(position).isIsfocus()) {
                if(list.get(position).getUseraccountid().equals(Sp.getString(context,AppConstant.USER_ACCOUNT_ID))){
                    ((ViewHolder2) holder).isfocus.setVisibility(View.GONE);
                }else {
                    ((ViewHolder2) holder).isfocus.setVisibility(View.VISIBLE);
                }
            } else {
                ((ViewHolder2) holder).isfocus.setVisibility(View.GONE);
            }
            if (list.get(position).isIsdianzan()) {
                GlideApp.with(context).load(R.drawable.dianzan_focus).placeholder(R.drawable.img_default).into(((ViewHolder2) holder).dianzanimg);
            } else {
                GlideApp.with(context).load(R.drawable.dianzan).placeholder(R.drawable.img_default).into(((ViewHolder2) holder).dianzanimg);
            }
            if(list.get(position).isIsmaster()){
                ((ViewHolder2) holder).masterCertification.setVisibility(View.VISIBLE);
            }else{
                ((ViewHolder2) holder).masterCertification.setVisibility(View.GONE);
            }
            ((ViewHolder2) holder).portrait.setOnClickListener(view -> PersonalIndexActivity.actionStart(context, list.get(position).getUseraccountid()));
//            ((ViewHolder2) holder).images.setOnClickListener(view -> ViewImageActivity.actionStart(context,position, (ArrayList) list.get(position).getPicture_list()));
//            ((ViewHolder2) holder).pinglun.setOnClickListener(view -> onPinglunListenner.callback(position));
            ((ViewHolder2) holder).dianzan.setOnClickListener(view -> onDianzanListenner.callback(position));
            ((ViewHolder2) holder).more.setOnClickListener(view -> onMoreListenner.callback(position));
            ((ViewHolder2) holder).isfocus.setOnClickListener(view -> onFocusListenner.callback(position));
            ((ViewHolder2) holder).introduceTxt.setOnClickListener(view -> {
//                Intent intent=new Intent(context,DynamicDetailActivity.class);
//                intent.putExtra("dynamic_id",list.get(position).getId());
//                context.startActivity(intent);
            });
        } else if (holder instanceof ViewHolder3) {
            holder.itemView.setOnClickListener(view -> {
//                Intent intent=new Intent(context,DynamicDetailActivity.class);
//                intent.putExtra("dynamic_id",list.get(position).getId());
//                context.startActivity(intent);
            });
            GlideApp.with(context)
                    .load(BASE_URL + list.get(position).getPortrait())
                    .override(100, 100)
                    .placeholder(R.drawable.logo_default)
                    .into(((ViewHolder3) holder).portrait);
            ((ViewHolder3) holder).name.setText(list.get(position).getName());
            ((ViewHolder3) holder).time.setText(TimeUtils.CountTime(list.get(position).getTime()));
            ((ViewHolder3) holder).pinglunNum.setText(list.get(position).getPinglun_num());
            ((ViewHolder3) holder).dianzanNum.setText(list.get(position).getDianzan_num());
            ((ViewHolder3) holder).readNum.setText(HelpUtils.getReadNum(list.get(position).getRead_num())+"  阅读");
            ((ViewHolder3) holder).introduceTxt.setMovementMethod(LinkMovementMethod.getInstance());
            ((ViewHolder3) holder).introduceTxt.setText(parseTxt(list.get(position).getContent()));
            CantScrollGirdLayoutManager manager=new CantScrollGirdLayoutManager(context,3);
            ((ViewHolder3) holder).recyclerview.setLayoutManager(manager);
            SquareImageViewAdapter adapter=new SquareImageViewAdapter(context,list.get(position).getPicture_list());
            ((ViewHolder3) holder).recyclerview.setAdapter(adapter);
            if (!list.get(position).isIsfocus()) {
                if(list.get(position).getUseraccountid().equals(Sp.getString(context,AppConstant.USER_ACCOUNT_ID))){
                    ((ViewHolder3) holder).isfocus.setVisibility(View.GONE);
                }else {((ViewHolder3) holder).isfocus.setVisibility(View.VISIBLE);}
            } else {
                ((ViewHolder3) holder).isfocus.setVisibility(View.GONE);
            }
            if (list.get(position).isIsdianzan()) {
                GlideApp.with(context).load(R.drawable.dianzan_focus).into(((ViewHolder3) holder).dianzanimg);
            } else {
                GlideApp.with(context).load(R.drawable.dianzan).into(((ViewHolder3) holder).dianzanimg);
            }
            if(list.get(position).isIsmaster()){
                ((ViewHolder3) holder).masterCertification.setVisibility(View.VISIBLE);
            }else{
                ((ViewHolder3) holder).masterCertification.setVisibility(View.GONE);
            }
            ((ViewHolder3) holder).portrait.setOnClickListener(view -> PersonalIndexActivity.actionStart(context, list.get(position).getUseraccountid()));
//            ((ViewHolder3) holder).pinglun.setOnClickListener(view -> onPinglunListenner.callback(position));
            ((ViewHolder3) holder).dianzan.setOnClickListener(view -> onDianzanListenner.callback(position));
            ((ViewHolder3) holder).more.setOnClickListener(view -> onMoreListenner.callback(position));
            ((ViewHolder3) holder).isfocus.setOnClickListener(view -> onFocusListenner.callback(position));
            ((ViewHolder3) holder).introduceTxt.setOnClickListener(view -> {
//                Intent intent=new Intent(context,DynamicDetailActivity.class);
//                intent.putExtra("dynamic_id",list.get(position).getId());
//                context.startActivity(intent);
            });
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder0 extends RecyclerView.ViewHolder {
        @BindView(R.id.portrait)
        CircleImageView portrait;
        @BindView(R.id.master_certification)
        ImageView masterCertification;
        @BindView(R.id.isfocus)
        TextView isfocus;
        @BindView(R.id.more)
        ImageView more;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.item_image)
        RoundedImageView itemImage;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.title_txt)
        TextView titleTXT;
        @BindView(R.id.relat)
        RelativeLayout relat;
        @BindView(R.id.read_num)
        TextView readNum;
        @BindView(R.id.view2)
        View view2;
        @BindView(R.id.pinglun_num)
        TextView pinglunNum;
        @BindView(R.id.pinglun)
        RelativeLayout pinglun;
        @BindView(R.id.dianzanimg)
        ImageView dianzanimg;
        @BindView(R.id.view3)
        View view3;
        @BindView(R.id.dianzan_num)
        TextView dianzanNum;
        @BindView(R.id.dianzan)
        RelativeLayout dianzan;
        @BindView(R.id.linear)
        LinearLayout linear;

        ViewHolder0(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder1 extends RecyclerView.ViewHolder {
        @BindView(R.id.portrait)
        CircleImageView portrait;
        @BindView(R.id.master_certification)
        ImageView masterCertification;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.isfocus)
        TextView isfocus;
        @BindView(R.id.more)
        ImageView more;
        @BindView(R.id.introduce_txt)
        TextView introduceTxt;
        @BindView(R.id.read_num)
        TextView readNum;
        @BindView(R.id.pinglun_num)
        TextView pinglunNum;
        @BindView(R.id.pinglun)
        RelativeLayout pinglun;
        @BindView(R.id.dianzanimg)
        ImageView dianzanimg;
        @BindView(R.id.dianzan_num)
        TextView dianzanNum;
        @BindView(R.id.dianzan)
        RelativeLayout dianzan;

        ViewHolder1(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder2 extends RecyclerView.ViewHolder {
        @BindView(R.id.portrait)
        CircleImageView portrait;
        @BindView(R.id.master_certification)
        ImageView masterCertification;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.isfocus)
        TextView isfocus;
        @BindView(R.id.more)
        ImageView more;
        @BindView(R.id.introduce_txt)
        TextView introduceTxt;
        @BindView(R.id.images)
        ImageView images;
        @BindView(R.id.read_num)
        TextView readNum;
        @BindView(R.id.pinglun_num)
        TextView pinglunNum;
        @BindView(R.id.pinglun)
        RelativeLayout pinglun;
        @BindView(R.id.dianzanimg)
        ImageView dianzanimg;
        @BindView(R.id.dianzan_num)
        TextView dianzanNum;
        @BindView(R.id.dianzan)
        RelativeLayout dianzan;

        ViewHolder2(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class ViewHolder3 extends RecyclerView.ViewHolder {
        @BindView(R.id.portrait)
        CircleImageView portrait;
        @BindView(R.id.master_certification)
        ImageView masterCertification;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.time)
        TextView time;
        @BindView(R.id.isfocus)
        TextView isfocus;
        @BindView(R.id.more)
        ImageView more;
        @BindView(R.id.introduce_txt)
        TextView introduceTxt;
        @BindView(R.id.recyclerview)
        RecyclerView recyclerview;
        @BindView(R.id.read_num)
        TextView readNum;
        @BindView(R.id.pinglun_num)
        TextView pinglunNum;
        @BindView(R.id.pinglun)
        RelativeLayout pinglun;
        @BindView(R.id.dianzanimg)
        ImageView dianzanimg;
        @BindView(R.id.dianzan_num)
        TextView dianzanNum;
        @BindView(R.id.dianzan)
        RelativeLayout dianzan;

        ViewHolder3(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    private SpannableString parseTxt(List<DynamicContentBean> contentBeanList) {
        try {
                StringBuilder stringBuffer = new StringBuilder();
                List<Integer> liststart = new ArrayList<>();
                List<Integer> listend = new ArrayList<>();
                List<String> listid = new ArrayList<>();
                for (int i = 0; i < contentBeanList.size(); i++) {
                    if (contentBeanList.get(i).getFlag() == 1) {
                        liststart.add(stringBuffer.toString().length());
                        stringBuffer.append(contentBeanList.get(i).getContent());
                        try {
                            listid.add(contentBeanList.get(i).getId());
                        }catch (Exception e){
                            listid.add("0");
                        }
                        listend.add(stringBuffer.toString().length());
                    } else if (contentBeanList.get(i).getFlag() == 0) {
                        stringBuffer.append(contentBeanList.get(i).getContent());
                    }
                }
                List<ForegroundColorSpan> listcolor = new ArrayList<>();
                List<DynamicClickableSpan> listclick = new ArrayList<>();
                for (int i = 0; i < liststart.size(); i++) {
                    ForegroundColorSpan colorSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.colorPrimary));
                    DynamicClickableSpan clickableSpan = new DynamicClickableSpan(context, listid.get(i));
                    listcolor.add(colorSpan);
                    listclick.add(clickableSpan);
                }
                SpannableString spannabletxt = new SpannableString(stringBuffer.toString());
                for (int i = 0; i < liststart.size(); i++) {
                    spannabletxt.setSpan(listcolor.get(i), liststart.get(i), listend.get(i), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    spannabletxt.setSpan(listclick.get(i), liststart.get(i), listend.get(i), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                }
                return spannabletxt;
        }catch (Exception e){
            return new SpannableString("");
        }
    }
}

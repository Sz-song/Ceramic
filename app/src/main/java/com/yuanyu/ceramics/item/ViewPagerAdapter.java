package com.yuanyu.ceramics.item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.yuanyu.ceramics.AppConstant;
import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.common.ViewImageActivity;
import com.yuanyu.ceramics.global.GlideApp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;

import static com.yuanyu.ceramics.AppConstant.BASE_URL;

/**
 * Created by Administrator on 2018-07-06.
 */

public class ViewPagerAdapter extends PagerAdapter {
    private List<String> data;
    private Context mContext;
    private String video;
    private String cover;

    public ViewPagerAdapter(Context context, List<String> data, String video, String cover) {
        this.mContext = context;
        this.data = data;
        this.video = video;
        this.cover = cover;
    }


    @Override
    public int getCount() {
        if (video.trim().length() > 0) {
           return data.size()+1;
        }else{
            return data.size();
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        if (video.trim().length() > 0) {
            if (position == 0) {
                Jzvd.WIFI_TIP_DIALOG_SHOWED=true;//关闭4G流量监控
                View view = LayoutInflater.from(mContext).inflate(R.layout.videoview, container, false);
                ViewHolder1 holder = new ViewHolder1(view);
                view.setTag(holder);
                holder.video.setUp(BASE_URL+video, "", Jzvd.SCREEN_NORMAL);
                GlideApp.with(mContext)
                        .load(BASE_URL+cover)
                        .placeholder(R.drawable.img_default)
                        .override(300, 300)
                        .into(holder.video.thumbImageView);
                container.addView(view);
                return view;
            } else {
                View view = LayoutInflater.from(mContext).inflate(R.layout.imageview, container, false);
                ViewHolder holder = new ViewHolder(view);
                view.setTag(holder);
                loadImage(holder.imageView, data.get(position - 1));
                container.addView(view);
                holder.imageView.setOnClickListener(view1 -> ViewImageActivity.actionStart(mContext, position - 1, (ArrayList) data));
                return view;
            }
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.imageview, container, false);
            ViewHolder holder = new ViewHolder(view);
            view.setTag(holder);
            loadImage(holder.imageView, data.get(position));
            container.addView(view);
            holder.imageView.setOnClickListener(view1 -> ViewImageActivity.actionStart(mContext, position, (ArrayList) data));
            return view;
        }
    }

    public void loadImage(ImageView view, String url) {
        GlideApp.with(mContext)
                .load(BASE_URL + url)
                .placeholder(R.drawable.img_default)
                .override(400, 400)
                .into(view);
    }

    static class ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            imageView = view.findViewById(R.id.image);
        }
    }


    static class ViewHolder1 {
        @BindView(R.id.video)
        JzvdStd video;

        ViewHolder1(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

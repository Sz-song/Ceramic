package com.yuanyu.ceramics.release_popwindow;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.yuanyu.ceramics.R;
import com.yuanyu.ceramics.global.GlideApp;
import com.yuanyu.ceramics.utils.anime.KickBackAnimator;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ReleasePopWindow extends PopupWindow implements View.OnClickListener {
    private View rootView;
    private RelativeLayout contentView;
    private Activity context;
    private ImageView imageView;

    public ReleasePopWindow(Activity context){
        this.context = context;
        rootView = LayoutInflater.from(context).inflate(R.layout.popup_release, null);
        setContentView(rootView);
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        contentView = rootView.findViewById(R.id.root);
        LinearLayout close = rootView.findViewById(R.id.ll_close);
        imageView= rootView.findViewById(R.id.background);
        GlideApp.with(context)
                .load(R.drawable.yuba_release_bg)
                .into(imageView);
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = 0.5f; // 0.0-1.0
        context.getWindow().setAttributes(lp);
        close.setOnClickListener(this);
        showAnimation(contentView);
        setOutsideTouchable(true);
        setFocusable(true);
    }
    //进入动画效果
    private void showAnimation(ViewGroup layout) {
        //遍历根试图下的一级子试图
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
            //忽略关闭组件
            if (child.getId() == R.id.ll_close) {
                ValueAnimator anim = ObjectAnimator.ofFloat(child,"rotation",-45,0);
                anim.setDuration(400);
                anim.start();
                continue;
            }
            //设置所有一级子试图的点击事件
            child.setOnClickListener(this);
            child.setVisibility(View.INVISIBLE);
            //延迟显示每个子试图(主要动画就体现在这里)
            Observable.timer(i * 50, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) {
                            child.setVisibility(View.VISIBLE);
                            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                            fadeAnim.setDuration(400);
                            KickBackAnimator kickAnimator = new KickBackAnimator();
                            kickAnimator.setDuration(300);
                            fadeAnim.setEvaluator(kickAnimator);
                            fadeAnim.start();
                        }
                    });
        }

    }
    //关闭动画效果
    private void closeAnimation(ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
            if (child.getId() == R.id.ll_close) {
                ValueAnimator anim = ObjectAnimator.ofFloat(child,"rotation",0,-45);
                anim.setDuration(400);
                anim.start();
                continue;
            }
            Observable.timer((layout.getChildCount() - i - 1) * 30, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) {
                            child.setVisibility(View.VISIBLE);
//                            if (child.getId() == R.id.drafts) {
//                                child.setVisibility(View.GONE);
//                            }
                            ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 0, 600);
                            fadeAnim.setDuration(400);
                            KickBackAnimator kickAnimator = new KickBackAnimator();
                            kickAnimator.setDuration(300);
                            fadeAnim.setEvaluator(kickAnimator);
                            fadeAnim.start();
                            fadeAnim.addListener(new Animator.AnimatorListener() {

                                @Override
                                public void onAnimationStart(Animator animation) {
                                }

                                @Override
                                public void onAnimationRepeat(Animator animation) {
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    child.setVisibility(View.INVISIBLE);
                                }

                                @Override
                                public void onAnimationCancel(Animator animation) {
                                }
                            });
                        }
                    });


            if (child.getId() == R.id.wenzhang) {
                Observable.timer((layout.getChildCount() - i) * 30 + 80, TimeUnit.MILLISECONDS)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) {
                                dismiss();
                            }
                        });
            }
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.wenzhang:
//                intent = new Intent(context, ReleaseArticleActivity.class);
//                intent.putExtra("type",0);
//                context.startActivity(intent);
                break;
            case R.id.dongtai:
//                intent = new Intent(context, ReleaseDynamicActivity.class);
//                context.startActivity(intent);
                break;
//            case R.id.drafts:
//                //TODO
//                break;
            case R.id.ll_close:
                if (isShowing()) {
                    closeAnimation(contentView);
                }
                break;
            default:
                break;
        }
    }
    @Override
    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        super.setOnDismissListener(onDismissListener);
    }
}

package com.yuanyu.ceramics.common;

/**
 * Created by Administrator on 2018-07-13.
 */
import android.content.Context;

public class CompatUtils {
    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}

package com.vinpin.commonutils;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;

/**
 * 资源工具类
 *
 * @author zwp
 *         create at 2017/8/15 10:00
 */
public final class ResourcesUtils {

    private ResourcesUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 获取资源字符串
     */
    public static String getString(int id) {
        return Utils.getApp().getResources().getString(id);
    }

    /**
     * 获取资源字符串数组
     */
    public static String[] getStringArray(int id) {
        return Utils.getApp().getResources().getStringArray(id);
    }

    /**
     * 获取颜色值
     */
    public static int getColor(int id) {
        return ContextCompat.getColor(Utils.getApp(), id);
    }

    /**
     * 获取drawable
     */
    public static Drawable getDrawable(int id) {
        return ContextCompat.getDrawable(Utils.getApp(), id);
    }
}

package com.vinpin.commonutils;

import android.app.Application;
import androidx.annotation.NonNull;

/**
 * Utils初始化相关
 *
 * @author zwp
 *         create at 2017/8/15 10:00
 */
public class Utils {

    private static Application sApplication;

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 初始化工具类
     *
     * @param app 应用
     */
    public static void init(@NonNull final Application app) {
        Utils.sApplication = app;
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext
     */
    public static Application getApp() {
        if (sApplication != null) {
            return sApplication;
        } else {
            throw new NullPointerException("u should init first");
        }
    }
}

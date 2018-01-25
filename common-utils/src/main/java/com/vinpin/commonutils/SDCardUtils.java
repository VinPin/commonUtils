package com.vinpin.commonutils;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * 手机SD卡管理工具
 *
 * @author zwp
 *         create at 2017/8/15 10:00
 */
public class SDCardUtils {

    private SDCardUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡路径
     *
     * @return SD卡路径 一般是/storage/emulated/0
     */
    public static String getSDCardPath() {
        if (!isSDCardEnable()) {
            return null;
        }
        return Environment.getExternalStorageDirectory().getPath();
    }

    /**
     * 获取SD卡剩余空间
     *
     * @return SD卡剩余空间
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static String getFreeSpace() {
        if (!isSDCardEnable()) {
            return null;
        }
        StatFs stat = new StatFs(getSDCardPath());
        long blockSize, availableBlocks;
        availableBlocks = stat.getAvailableBlocksLong();
        blockSize = stat.getBlockSizeLong();
        return FileUtils.byte2FitMemorySize(availableBlocks * blockSize);
    }

    /**
     * 获取app缓存存储根路径
     *
     * @return 目录path路径
     */
    public static String getCachePath() {
        String cachePath;
        if (isSDCardEnable()) {
            // 获取到 SDCard/Android/data/你的应用的包名/files
            cachePath = Utils.getApp().getExternalFilesDir(null) + File.separator + "cache";
        } else {
            // 获取/data/data/<application package>/files
            cachePath = Utils.getApp().getFilesDir() + File.separator + "cache";
        }
        FileUtils.createOrExistsDir(cachePath);
        return cachePath;
    }
}

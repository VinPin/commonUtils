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
 * create at 2017/8/15 10:00
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
    public static long getFreeSpace() {
        if (!isSDCardEnable()) {
            return 0;
        }
        StatFs statfs = new StatFs(getSDCardPath());
        long availableBlocksLong;
        long blockSizeLong;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            availableBlocksLong = statfs.getAvailableBlocksLong();
            blockSizeLong = statfs.getBlockSizeLong();
        } else {
            availableBlocksLong = statfs.getAvailableBlocks();
            blockSizeLong = statfs.getBlockSize();
        }
        return availableBlocksLong * blockSizeLong;
    }

    /**
     * 获取app缓存存储根路径
     *
     * @return 目录path路径
     */
    public static String getRootCachePath() {
        String cachePath;
        if (isSDCardEnable()) {
            // 获取到 SDCard/Android/data/你的应用的包名/files
            cachePath = Utils.getApp().getExternalFilesDir(null) + "";
        } else {
            // 获取/data/data/<application package>/files
            cachePath = Utils.getApp().getFilesDir() + "";
        }
        FileUtils.createOrExistsDir(cachePath);
        return cachePath;
    }

    /**
     * 获取app缓存存储根路径下cache文件夹下
     *
     * @return 目录path路径
     */
    public static String getCachePath() {
        String cachePath = getRootCachePath() + File.separator + "cache";
        FileUtils.createOrExistsDir(cachePath);
        return cachePath;
    }
}

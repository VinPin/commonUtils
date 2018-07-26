package com.vinpin.commonutils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.io.File;
import java.io.IOException;

/**
 * 文件相关工具类
 *
 * @author zwp
 * create at 2017/8/15 10:00
 */
public final class FileUtils {

    private FileUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 根据文件路径获取文件
     *
     * @param filePath 文件路径
     * @return 文件
     */
    public static File getFileByPath(final String filePath) {
        return TextUtils.isEmpty(filePath) ? null : new File(filePath);
    }

    /**
     * 根据文件获取文件路径
     *
     * @param file 文件
     * @return 文件路径
     */
    public static String getPathByFile(final File file) {
        return file != null ? file.getPath() : "";
    }

    /**
     * 判断文件是否存在
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在<br>{@code false}: 不存在
     */
    public static boolean isFileExists(final String filePath) {
        File file = getFileByPath(filePath);
        return file != null && file.exists();
    }

    /**
     * 重命名文件
     *
     * @param filePath 文件路径
     * @param newName  新名称
     * @return {@code true}: 重命名成功<br>{@code false}: 重命名失败
     */
    public static boolean rename(final String filePath, final String newName) {
        File file = getFileByPath(filePath);
        // 文件为空返回false
        if (file == null) {
            return false;
        }
        // 文件不存在返回false
        if (!file.exists()) {
            return false;
        }
        // 新的文件名为空返回false
        if (TextUtils.isEmpty(newName)) {
            return false;
        }
        // 如果文件名没有改变返回true
        if (newName.equals(file.getName())) {
            return true;
        }
        File newFile = new File(file.getParent() + File.separator + newName);
        // 如果重命名的文件已存在返回false
        return !newFile.exists() && file.renameTo(newFile);
    }

    /**
     * 判断是否是目录
     *
     * @param dirPath 目录路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isDir(final String dirPath) {
        File file = getFileByPath(dirPath);
        return isFileExists(dirPath) && (file != null && file.isDirectory());
    }

    /**
     * 判断是否是文件
     *
     * @param filePath 文件路径
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isFile(final String filePath) {
        File file = getFileByPath(filePath);
        return isFileExists(filePath) && (file != null && file.isFile());
    }

    /**
     * 判断目录是否存在，不存在则判断是否创建成功
     *
     * @param dirPath 目录路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsDir(final String dirPath) {
        File file = getFileByPath(dirPath);
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return file != null && (file.exists() ? file.isDirectory() : file.mkdirs());
    }

    /**
     * 判断文件是否存在，不存在则判断是否创建成功
     *
     * @param filePath 文件路径
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createOrExistsFile(final String filePath) {
        File file = getFileByPath(filePath);
        if (file == null) {
            return false;
        }
        // 如果存在，是文件则返回true，是目录则返回false
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(getPathByFile(file.getParentFile()))) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断文件是否存在，存在则在创建之前删除
     *
     * @param filePath 文件路径
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public static boolean createFileByDeleteOldFile(final String filePath) {
        File file = getFileByPath(filePath);
        if (file == null) {
            return false;
        }
        // 文件存在并且删除失败返回false
        if (file.exists() && !file.delete()) {
            return false;
        }
        // 创建目录失败返回false
        if (!createOrExistsDir(getPathByFile(file.getParentFile()))) {
            return false;
        }
        try {
            return file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除目录
     *
     * @param dirPath 目录路径
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteDir(final String dirPath) {
        File dir = getFileByPath(dirPath);
        if (dir == null) {
            return false;
        }
        // 目录不存在返回true
        if (!dir.exists()) {
            return true;
        }
        // 不是目录返回false
        if (!dir.isDirectory()) {
            return false;
        }
        // 现在文件存在且是文件夹
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!file.delete()) {
                        return false;
                    }
                } else if (file.isDirectory()) {
                    if (!deleteDir(getPathByFile(file))) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    /**
     * 删除文件
     *
     * @param srcFilePath 文件路径
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteFile(final String srcFilePath) {
        File file = getFileByPath(srcFilePath);
        return file != null && (!file.exists() || file.isFile() && file.delete());
    }

    /**
     * 字节数转合适内存大小
     * <p>保留2位小数</p>
     *
     * @param byteNum 字节数
     * @return 合适内存大小
     */
    @SuppressLint("DefaultLocale")
    public static String byte2FitMemorySize(final long byteNum) {
        return byte2FitMemorySize(byteNum, 2);
    }

    /**
     * 字节数转合适内存大小
     *
     * @param byteNum  字节数
     * @param decimals 保留小数点位数，至少大于0
     * @return 合适内存大小
     */
    @SuppressLint("DefaultLocale")
    public static String byte2FitMemorySize(final long byteNum, int decimals) {
        if (decimals < 1) {
            return "shouldn't be less than one!";
        }
        String format = "%." + decimals + "f";
        if (byteNum < 0) {
            return "shouldn't be less than zero!";
        } else if (byteNum < 1024) {
            return String.format(format + "B", (double) byteNum);
        } else if (byteNum < 1048576) {
            return String.format(format + "KB", (double) byteNum / 1024);
        } else if (byteNum < 1073741824) {
            return String.format(format + "MB", (double) byteNum / 1048576);
        } else {
            return String.format(format + "GB", (double) byteNum / 1073741824);
        }
    }
}

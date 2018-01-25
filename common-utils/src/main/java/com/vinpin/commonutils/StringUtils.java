package com.vinpin.commonutils;

import android.support.annotation.Nullable;

/**
 * 字符串相关工具类
 *
 * @author zwp
 *         create at 2017/8/15 10:00
 */
public final class StringUtils {

    private StringUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断字符串是否为null或长度为0
     *
     * @param s 待校验字符串
     * @return {@code true}: 空<br> {@code false}: 不为空
     */
    public static boolean isEmpty(final CharSequence s) {
        return s == null || s.length() == 0;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isTrimEmpty(final String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 判断字符串是否为null或全为空白字符
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空白字符<br> {@code false}: 不为null且不全空白字符
     */
    public static boolean isSpace(final String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * null转为长度为0字符串转为null的字符串
     *
     * @param str 待转字符串
     * @return str为null转为长度为0字符串转为null的字符串，否则不改变
     */
    public static String nullIfEmpty(@Nullable String str) {
        return isEmpty(str) ? null : str;
    }

    /**
     * null转为长度为0的字符串
     *
     * @param str 待转字符串
     * @return str为null转为长度为0字符串，否则不改变
     */
    public static String emptyIfNull(final String str) {
        return str == null ? "" : str;
    }

    /**
     * 返回字符串长度
     *
     * @param s 字符串
     * @return null返回0，其他返回自身长度
     */
    public static int length(final CharSequence s) {
        return s == null ? 0 : s.length();
    }

    /**
     * 按字节数截取某个长度的字符串
     *
     * @param s          字符串
     * @param subSLength 字节数
     * @return 截取后的字符串
     */
    public static String subStr(String s, int subSLength) {
        if (s == null) {
            return "";
        }
        // 截取字节数
        int tempSubLength = subSLength;
        // 截取的子串
        String subStr = s.substring(0, s.length() < subSLength ? s.length() : subSLength);
        // 截取子串的字节长度
        int subStrByetsL = subStr.getBytes().length;
        // 说明截取的字符串中包含有汉字
        while (subStrByetsL > tempSubLength) {
            int subSLengthTemp = --subSLength;
            subStr = s.substring(0, subSLengthTemp > s.length() ? s.length() : subSLengthTemp);
            subStrByetsL = subStr.getBytes().length;
        }
        return subStr;
    }

    /**
     * 获取字符串的字节数
     *
     * @param s 字符串
     * @return null返回-1，其他返回字符串的字节数
     */
    public static int getStringBytes(String s) {
        if (s == null) {
            return -1;
        }
        return s.getBytes().length;
    }
}
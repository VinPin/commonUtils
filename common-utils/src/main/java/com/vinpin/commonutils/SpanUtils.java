package com.vinpin.commonutils;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.UnderlineSpan;

/**
 * SpannableString相关工具类
 *
 * @author zwp
 *         create at 2017/8/15 10:00
 */
public class SpanUtils {

    private static final int DEFAULT_COLOR = 0x12000000;
    /**
     * 被图片替换的占位符
     */
    private static final String SPACE_TEXT = "-";

    private SpannableStringBuilder mBuilder;
    private CharSequence mText;
    private int flag;
    @ColorInt
    private int foregroundColor;
    @ColorInt
    private int backgroundColor;
    private float proportion;
    private float xProportion;
    private boolean isStrikethrough;
    private boolean isUnderline;
    private ImageSpan imageSpan;

    public SpanUtils() {
        mBuilder = new SpannableStringBuilder();
        mText = "";
        setDefault();
    }

    public SpanUtils(@NonNull CharSequence text) {
        mBuilder = new SpannableStringBuilder();
        mText = text;
        setDefault();
    }

    private void setDefault() {
        flag = Spanned.SPAN_EXCLUSIVE_EXCLUSIVE;
        foregroundColor = DEFAULT_COLOR;
        backgroundColor = DEFAULT_COLOR;
        proportion = -1;
        xProportion = -1;
        isStrikethrough = false;
        isUnderline = false;
        imageSpan = null;
    }

    /**
     * 追加样式字符串
     *
     * @param text 样式字符串文本
     */
    public SpanUtils append(@NonNull CharSequence text) {
        setSpan();
        mText = text;
        return this;
    }

    /**
     * 用ImageSpan替换文本
     *
     * @param imageSpan 图片
     */
    public SpanUtils append(@NonNull ImageSpan imageSpan) {
        append(SPACE_TEXT);
        this.imageSpan = imageSpan;
        return this;
    }

    /**
     * 设置标识
     *
     * @param flag <ul>
     *             <li>{@link Spanned#SPAN_INCLUSIVE_EXCLUSIVE}</li>
     *             <li>{@link Spanned#SPAN_INCLUSIVE_INCLUSIVE}</li>
     *             <li>{@link Spanned#SPAN_EXCLUSIVE_EXCLUSIVE}</li>
     *             <li>{@link Spanned#SPAN_EXCLUSIVE_INCLUSIVE}</li>
     *             </ul>
     */
    public SpanUtils setFlag(int flag) {
        this.flag = flag;
        return this;
    }

    /**
     * 设置前景色
     *
     * @param color 前景色
     */
    public SpanUtils setForegroundColor(@ColorInt int color) {
        this.foregroundColor = color;
        return this;
    }

    /**
     * 设置背景色
     *
     * @param color 背景色
     */
    public SpanUtils setBackgroundColor(@ColorInt int color) {
        this.backgroundColor = color;
        return this;
    }

    /**
     * 设置字体比例
     *
     * @param proportion 比例
     */
    public SpanUtils setProportion(float proportion) {
        this.proportion = proportion;
        return this;
    }

    /**
     * 设置字体横向比例
     *
     * @param proportion 比例
     */
    public SpanUtils setXProportion(float proportion) {
        this.xProportion = proportion;
        return this;
    }

    /**
     * 设置删除线
     */
    public SpanUtils setStrikethrough() {
        this.isStrikethrough = true;
        return this;
    }

    /**
     * 设置下划线
     */
    public SpanUtils setUnderline() {
        this.isUnderline = true;
        return this;
    }

    /**
     * 创建样式字符串
     *
     * @return 样式字符串
     */
    public SpannableStringBuilder create() {
        setSpan();
        return mBuilder;
    }

    /**
     * 设置样式
     */
    private void setSpan() {
        int start = mBuilder.length();
        mBuilder.append(this.mText);
        int end = mBuilder.length();
        if (foregroundColor != DEFAULT_COLOR) {
            // 设置前景色
            mBuilder.setSpan(new ForegroundColorSpan(foregroundColor), start, end, flag);
            foregroundColor = DEFAULT_COLOR;
        }
        if (backgroundColor != DEFAULT_COLOR) {
            // 设置背景色
            mBuilder.setSpan(new BackgroundColorSpan(backgroundColor), start, end, flag);
            backgroundColor = DEFAULT_COLOR;
        }
        if (proportion != -1) {
            // 设置字体比例
            mBuilder.setSpan(new RelativeSizeSpan(proportion), start, end, flag);
            proportion = -1;
        }
        if (xProportion != -1) {
            // 设置字体横向比例
            mBuilder.setSpan(new ScaleXSpan(xProportion), start, end, flag);
            xProportion = -1;
        }
        if (isStrikethrough) {
            // 设置删除线
            mBuilder.setSpan(new StrikethroughSpan(), start, end, flag);
            isStrikethrough = false;
        }
        if (isUnderline) {
            // 设置下划线
            mBuilder.setSpan(new UnderlineSpan(), start, end, flag);
            isUnderline = false;
        }
        if (imageSpan != null) {
            // 图片
            mBuilder.setSpan(imageSpan, start, end, flag);
            imageSpan = null;
        }
    }
}

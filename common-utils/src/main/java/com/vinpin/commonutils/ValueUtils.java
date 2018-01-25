package com.vinpin.commonutils;

import android.support.annotation.NonNull;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * 数值工具类
 *
 * @author zwp
 *         create at 2017/2/22 10:30
 */
public final class ValueUtils {

    /**
     * int  表示的范围为-2^31到2^31-1 -2147483648到2147483647   10位数
     * long  表示的范围为-2^64到2^64-1 -9223372036854775807到9223372036854775808  19位
     * float  表示的范围为-2^128到2^128-1 1.4E-45到3.4028235E38  38位
     * double  表示的范围为-2^1024到2^1024-1 4.9E-324到1.7976931348623157E308  308位
     */

    private ValueUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 长数值处理
     * <p>
     * <li>数值位数	        调整显示    是否保留小数点</li>
     * <li>＞0、＜1	        0.XX	    保留小数点后两位</li>
     * <li>＜10000	        整数	    不保留</li>
     * <li>                 不是整数	保留小数点后两位</li>
     * <li>五位数-八位数	1万-9999万	保留小数点后两位</li>
     * <li>九位数及以上位数	1亿-xxx亿	保留小数点后两位</li>
     */
    public static String formetLongValue(long value) {
        int million = 10000; // 万
        int billion = million * 10000; // 亿
        if (value >= billion) {
            if (value % billion == 0) {
                return String.format("%s亿", (float) value / billion);
            } else {
                return String.format(Locale.getDefault(), "%.2f亿", (float) value / billion);
            }
        } else if (value >= million) {
            if (value % million == 0) {
                return String.format("%s万", (float) value / million);
            } else {
                return String.format(Locale.getDefault(), "%.2f万", (float) value / million);
            }
        } else {
            return String.valueOf(value);
        }
    }

    public static String formetLongValue(double value) {
        long million = 10000; // 万
        long billion = million * 10000; // 亿
        if (value >= billion) {
            if (value % billion == 0) {
                return String.format("%s亿", value / billion);
            } else {
                return String.format(Locale.getDefault(), "%.2f亿", value / billion);
            }
        } else if (value >= million) {
            if (value % million == 0) {
                return String.format("%s万", value / million);
            } else {
                return String.format(Locale.getDefault(), "%.2f万", value / million);
            }
        } else {
            return String.valueOf(value);
        }
    }

    public static String formetLongValue(@NonNull String value) {
        try {
            if (RegexUtils.isNumeric(value.trim())) {
                if (value.trim().length() >= 10) {// 超出int的最大值了
                    return formetLongValue(Long.parseLong(value.trim()));
                } else {
                    return formetLongValue(Integer.parseInt(value.trim()));
                }
            } else {
                return value;
            }
        } catch (Exception e) {
            return value;
        }
    }

    /**
     * 图表坐标轴长数值处理
     * <p>
     * 数值位数         单位    是否保留小数点
     * ＞0、＜1000	    无	    不保留
     * 四位数-六位数	k	    保留小数点后两位
     * 七位数-九位数	m	    保留小数点后两位
     * 十位数及以上位数	b	    保留小数点后两位
     */
    public static String formetAxisValue(long value) {
        int k = 1000;
        int m = 1000 * k;
        int b = 1000 * m;
        if (value >= b) {
            if (value % b == 0) {
                return String.format("%sb", (float) value / b);
            } else {
                return String.format(Locale.getDefault(), "%.2fb", (float) value / b);
            }
        } else if (value >= m) {
            if (value % m == 0) {
                return String.format("%sm", value / m);
            } else {
                return String.format(Locale.getDefault(), "%.2fm", (float) value / m);
            }
        } else if (value >= k) {
            if (value % k == 0) {
                return String.format("%sk", value / k);
            } else {
                return String.format(Locale.getDefault(), "%.2fk", (float) value / k);
            }
        } else {
            return String.valueOf(value);
        }
    }

    public static String formetAxisValue(double value) {
        int k = 1000;
        int m = 1000 * k;
        int b = 1000 * m;
        if (value >= b) {
            if (value % b == 0) {
                return String.format("%sb", value / b);
            } else {
                return String.format(Locale.getDefault(), "%.2fb", value / b);
            }
        } else if (value >= m) {
            if (value % m == 0) {
                return String.format("%sm", value / m);
            } else {
                return String.format(Locale.getDefault(), "%.2fm", value / m);
            }
        } else if (value >= k) {
            if (value % k == 0) {
                return String.format("%sk", value / k);
            } else {
                return String.format(Locale.getDefault(), "%.2fk", value / k);
            }
        } else {
            return String.valueOf(value);
        }
    }

    /**
     * 当浮点型数据位数超过10位之后，数据变成科学计数法显示。用此方法可以使其正常显示。
     */
    public static String formatFloatNumber(double value) {
        if (value != 0.00) {
            if (value - Math.round(value) <= 0.000000001) {// 整数
                return new DecimalFormat("########").format(value);
            } else {// 非整数
                return new DecimalFormat("########.00").format(value);
            }
        } else {
            return "0.00";
        }
    }

    /**
     * 格式化百分比
     *
     * @param percent 百分比
     */
    public static String formatPercent(float percent) {
        return String.format(Locale.getDefault(), "%.2f", percent * 100) + "%";
    }
}

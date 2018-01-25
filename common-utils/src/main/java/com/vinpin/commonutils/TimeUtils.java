package com.vinpin.commonutils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 时间相关工具类
 *
 * @author zwp
 *         create at 2017/8/15 10:00
 */
public class TimeUtils {

    /* <pre>
     *                                             HH:mm    15:44
     *                                            h:mm a    3:44 下午
     *                                           HH:mm z    15:44 CST
     *                                           HH:mm Z    15:44 +0800
     *                                        HH:mm zzzz    15:44 中国标准时间
     *                                          HH:mm:ss    15:44:40
     *                                        yyyy-MM-dd    2016-08-12
     *                                  yyyy-MM-dd HH:mm    2016-08-12 15:44
     *                               yyyy-MM-dd HH:mm:ss    2016-08-12 15:44:40
     *                          yyyy-MM-dd HH:mm:ss zzzz    2016-08-12 15:44:40 中国标准时间
     *                     EEEE yyyy-MM-dd HH:mm:ss zzzz    星期五 2016-08-12 15:44:40 中国标准时间
     *                          yyyy-MM-dd HH:mm:ss.SSSZ    2016-08-12 15:44:40.461+0800
     *                        yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     *                      yyyy.MM.dd G 'at' HH:mm:ss z    2016.08.12 公元 at 15:44:40 CST
     *                                            K:mm a    3:44 下午
     *                                  EEE, MMM d, ''yy    星期五, 八月 12, '16
     *                             hh 'o''clock' a, zzzz    03 o'clock 下午, 中国标准时间
     *                      yyyyy.MMMMM.dd GGG hh:mm aaa    02016.八月.12 公元 03:44 下午
     *                        EEE, d MMM yyyy HH:mm:ss Z    星期五, 12 八月 2016 15:44:40 +0800
     *                                     yyMMddHHmmssZ    160812154440+0800
     *                        yyyy-MM-dd'T'HH:mm:ss.SSSZ    2016-08-12T15:44:40.461+0800
     * EEEE 'DATE('yyyy-MM-dd')' 'TIME('HH:mm:ss')' zzzz    星期五 DATE(2016-08-12) TIME(15:44:40) 中国标准时间
     * </pre>
     */

    /**
     * 日期转换字符串
     *
     * @param date       Date类型的时间
     * @param formatType 格式为yyyy-MM-dd HH:mm:ss  //yyyy年MM月dd日 HH时mm分ss秒
     */
    public static String dateToString(Date date, String formatType) {
        return new SimpleDateFormat(formatType, Locale.getDefault()).format(date);
    }

    /**
     * 字符串转换日期
     * <p>
     * strTime的时间格式必须要与formatType的时间格式相同
     *
     * @param stringTime 要转换的string类型的时间
     * @param formatType 格式为yyyy-MM-dd HH:mm:ss  //yyyy年MM月dd日 HH时mm分ss秒
     */
    public static Date stringToDate(String stringTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType, Locale.getDefault());
        Date date = null;
        try {
            date = formatter.parse(stringTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 字符串转换long类型
     * <p>
     * strTime的时间格式必须要与formatType的时间格式相同
     *
     * @param stringTime 要转换的string类型的时间
     * @param formatType 格式为yyyy-MM-dd HH:mm:ss  //yyyy年MM月dd日 HH时mm分ss秒
     */
    public static long stringToLong(String stringTime, String formatType) {
        Date date = stringToDate(stringTime, formatType);
        return date == null ? 0 : date.getTime();
    }

    /**
     * 将日期格式化成 yyyy-MM-dd HH:mm
     */
    public static String getStandardTime(Date date) {
        return dateToString(date, "yyyy-MM-dd HH:mm");
    }
}
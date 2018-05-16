package com.hunter.helloandroid.module.update.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2017
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2017/3/16 18:04
 * <p>
 * 描    述：时间工具类
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class DateUtil {

    /**
     * 获取今天的日期
     *
     * @return
     */
    public static String getToday() {
        return getToday("yyyy-MM-dd_HH:mm:ss");
    }

    /**
     * 获取日期
     *
     * @return
     */
    public static String getToday(String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(new Date());
    }

    /**
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String s) {
        return dateToStamp("yyyy-MM-dd HH:mm:ss", s);
    }

    /**
     * 将时间转换为时间戳
     */
    public static long dateToStamp(String pattern, String s) {
        long res = -1;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date date = simpleDateFormat.parse(s);
            res = date.getTime();
        } catch (Exception e) {
        }
        return res;
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(long lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 将时间戳转换为时间
     */
    public static String stampToDate(String pattern, long lt) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 把日期时间字符串从一种显示规则转到另一种显示规则
     *
     * @param pattern1 pattern1->pattern2
     * @param pattern2 pattern1->pattern2
     * @param str      日期时间字符串：2017-04-06 14:32:54
     * @return 返回新的日期时间字符串
     */
    public static String dateToDate(String pattern1, String pattern2, String str) {
        long stamp = DateUtil.dateToStamp(pattern1, str);
        return DateUtil.stampToDate(pattern2, stamp);
    }

    /**
     * 过去七天
     */
    public static String getPast7Day() {
        return getPast7Day("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 过去七天
     */
    public static String getPast7Day(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.DATE, -7);
        return format.format(c.getTime());
    }

    /**
     * 过去一月
     */
    public static String getPastMonth() {
        return getPastMonth("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 过去一月
     */
    public static String getPastMonth(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.MONTH, -1);
        Date m = c.getTime();
        return format.format(m);
    }

    /**
     * 过去三个月
     */
    public static String getPast3Month() {
        return getPast3Month("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 过去三个月
     */
    public static String getPast3Month(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.MONTH, -3);
        Date m3 = c.getTime();
        return format.format(m3);
    }

    /**
     * 过去一年
     */
    public static String getPastYear() {
        return getPastYear("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 过去一年
     */
    public static String getPastYear(String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        Calendar c = Calendar.getInstance();

        c.setTime(new Date());
        c.add(Calendar.YEAR, -1);
        Date y = c.getTime();
        return format.format(y);
    }

    /**
     * 把日期转为字符串
     */
    public static String converDateToString(Date date) {
        return converDateToString("yyyy-MM-dd", date);
    }

    /**
     * 把日期转为字符串
     */
    public static String converDateToString(String pattern, Date date) {
        @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat(pattern);
        return df.format(date);
    }

    /**
     * 把字符串转为日期
     */
    public static Date converStringToDate(String strDate) {
        return converStringToDate("yyyy-MM-dd", strDate);
    }

    /**
     * 把字符串转为日期
     */
    public static Date converStringToDate(String pattern, String strDate) {
        Date parse = null;
        if (strDate != null && strDate.length() > 0) {
            DateFormat df = new SimpleDateFormat(pattern);
            try {
                parse = df.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return parse;
    }

    /**
     * 获取时间间隔
     *
     * @param inputTime 传入的时间格式必须类似于“yyyy-MM-dd HH:mm:ss”这样的格式
     **/
    public static String getInterval(String inputTime) {
        String result = inputTime;

        if (inputTime.length() != 19) {
            return result;
        }

        try {
            SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            ParsePosition pos = new ParsePosition(0);
            Date d1 = sd.parse(inputTime, pos);

            // 用现在距离1970年的时间间隔new
            // Date().getTime()减去以前的时间距离1970年的时间间隔d1.getTime()得出的就是以前的时间与现在时间的时间间隔

            long time = new Date().getTime() - d1.getTime();// 得出的时间间隔是毫秒
            if (time / 1000 <= 0) {

                // 如果时间间隔小于等于0秒则显示“刚刚”time/10得出的时间间隔的单位是秒
                result = "刚刚";
            } else if (time / 1000 < 60) {

                // 如果时间间隔小于60秒则显示多少秒前
                int se = (int) ((time % 60000) / 1000);
                result = se + "秒前";
            } else if (time / 60000 < 60) {

                // 如果时间间隔小于60分钟则显示多少分钟前
                int m = (int) ((time % 3600000) / 60000);// 得出的时间间隔的单位是分钟
                result = m + "分钟前";
            } else if (time / 3600000 < 24) {

                // 如果时间间隔小于24小时则显示多少小时前
                int h = (int) (time / 3600000);// 得出的时间间隔的单位是小时
                result = h + "小时前";
            } else if (time / 86400000 < 2) {

                // 如果时间间隔小于2天则显示昨天
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                result = sdf.format(d1.getTime());
                result = "昨天" + result;
            } else if (time / 86400000 < 3) {

                // 如果时间间隔小于3天则显示前天
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                result = sdf.format(d1.getTime());
                result = "前天" + result;
            } else if (time / 86400000 < 30) {

                // 如果时间间隔小于30天则显示多少天前
                SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日 HH:mm");
                result = sdf.format(d1.getTime());
            } else if (time / 2592000000L < 12) {

                // 如果时间间隔小于12个月则显示多少月前
                SimpleDateFormat sdf = new SimpleDateFormat("MM月dd日");
                result = sdf.format(d1.getTime());
            } else {

                // 大于1年，显示年月日时间
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                result = sdf.format(d1.getTime());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }


    /**
     * 解析时间戳
     *
     * @param timestampStr 字符串中包含时间戳 "/Date(1496372743000)/"
     */
    public static long parseTimestamp(String timestampStr) {

        long timestamp = -1;
        String number = getStringNumber(timestampStr);

        try {
            if (!TextUtils.isEmpty(number)) {
                timestamp = Long.parseLong(number);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * 从一段字符串中取出数字
     */
    private static String getStringNumber(String str) {
        StringBuilder sb = new StringBuilder();
        if (!TextUtils.isEmpty(str)) {
            int length = str.length();
            for (int i = 0; i < length; i++) {
                String charAt = String.valueOf(str.charAt(i));
                if (isNumber(charAt)) {
                    sb.append(charAt);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 判断是否是数字
     */
    private static boolean isNumber(String charAt) {
        Pattern compile = Pattern.compile("[0-9]");
        Matcher matcher = compile.matcher(charAt);
        return matcher.matches();
    }

}

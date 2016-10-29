package com.example.snoy.myapplication.lib.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类
 * formatDataTime 格式化日期时间
 * formatDate 格式化日期
 * formatTime 格式化时间
 * formatDateCustom 自定义格式的格式化日期时间
 * string2Date 将时间字符串转换成Date
 * getDate 获取系统日期
 * getTime 获取系统时间
 * getDateTime 获取系统日期时间
 * subtractDate 计算两个时间差
 * getDateAfter 得到几天后的时间
 * getWeekOfMonth 获取当前时间为本月的第几周
 * getDayOfWeek 获取当前时间为本周的第几天
 */
public final class DateUtils {

    private static final SimpleDateFormat DATE_FORMAT_DATETIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT_DATE = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATE_FORMAT_TIME = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat DATE_FORMAT_MONTH = new SimpleDateFormat("MM");
    private static final SimpleDateFormat DATE_FORMAT_YEAR = new SimpleDateFormat("yyyy");


    private DateUtils() {
    }

    /**
     * formatDataTime 格式化日期时间
     */
    public static String formatDataTime(long date) {
        return DATE_FORMAT_DATETIME.format(new Date(date));
    }

    /**
     * formatDate 格式化日期
     */
    public static String formatDate(long date) {
        return DATE_FORMAT_DATE.format(new Date(date));
    }

    /**
     * formatDataTime 格式化当前月
     */
    public static String formatDataMonth(long date) {
        return DATE_FORMAT_MONTH.format(new Date(date));
    }
    /**
     * formatDataTime 格式化当前年
     */
    public static String formatDataYear(long date) {
        return DATE_FORMAT_YEAR.format(new Date(date));
    }

    /**
     * formatTime 格式化时间
     */
    public static String formatTime(long date) {
        return DATE_FORMAT_TIME.format(new Date(date));
    }

    /**
     * formatDateCustom 自定义格式的格式化日期时间
     */
    public static String formatDateCustom(String beginDate, String format) {
        return new SimpleDateFormat(format).format(new Date(Long.parseLong(beginDate)));
    }


    public static String formatDateCustom(Date beginDate, String format) {
        return new SimpleDateFormat(format).format(beginDate);
    }

    /**
     * stringToDate 将时间字符串转换成Date
     */
    public static Date stringToDate(String s, String style) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
        simpleDateFormat.applyPattern(style);
        Date date = null;
        if (s == null || s.length() < 6) {
            return null;
        }
        try {
            date = simpleDateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * getTime 获取系统时间
     */
    public static String getTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(System.currentTimeMillis());
        return cal.get(Calendar.HOUR_OF_DAY) + ":" + cal.get(Calendar.MINUTE) + ":" + cal.get(Calendar.SECOND);
    }

    /**
     * getDate 获取系统日期
     */
    public static String getDate() {
        return new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
    }

    /**
     * getDateTime 获取系统日期时间
     */
    public static String getDateTime() {
        return DATE_FORMAT_DATETIME.format(System.currentTimeMillis());
    }

    /**
     * getDateTime 获取系统日期时间
     */
    public static String getDateTime(String format) {
        return new SimpleDateFormat(format).format(System.currentTimeMillis());
    }

    /**
     * subtractDate 计算两个时间差
     */
    public static long subtractDate(Date dateStart, Date dateEnd) {
        return dateEnd.getTime() - dateStart.getTime();
    }

    /**
     * getDateAfter 得到几天后的时间
     */
    public static Date getDateAfter(Date d, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(d);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    /**
     * getWeekOfMonth 获取当前时间为本月的第几周
     */
    public static int getWeekOfMonth() {
        Calendar calendar = Calendar.getInstance();
        int week = calendar.get(Calendar.WEEK_OF_MONTH);
        return week - 1;
    }

    /**
     * getDayOfWeek 获取当前时间为本周的第几天
     */
    public static int getDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if (day == 1) {
            day = 7;
        } else {
            day = day - 1;
        }
        return day;
    }
}

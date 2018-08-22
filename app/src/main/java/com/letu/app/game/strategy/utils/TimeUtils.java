package com.letu.app.game.strategy.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ${user} on 2018/7/16
 */
public class TimeUtils {

    private static final String DEFAULT_FORMAT="yyyy-MM-dd HH:mm";
    private static final String FORMAT_YYYY_MM="yyyy-MM";
    private static final String FORMAT_YYYY_MM_DD="yyyy-MM-dd";
    private static final String FORMAT_MM_DD="MM-dd";
    private static final String FORMAT_YYYY_MM_DD_HH_MM="yyyy-MM-dd HH:mm";
    private static final String FORMAT_YYYY_MM_DD_HH_MM_SS="yyyy-MM-dd HH:mm:ss";

    private final static long MS_MINUTE = 60 * 1000;// 1分钟
    private final static long MS_HOUR = 60 * MS_MINUTE;// 1小时
    private final static long MS_DAY = 24 * MS_HOUR;// 1天
    private final static long MS_MONTH = 31 * MS_DAY;// 月
    private final static long MS_YEAR = 12 * MS_MONTH;// 年

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param seconds   精确到秒的字符串
     * @param format
     * @return
     */
    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || seconds.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = DEFAULT_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds + "000")));
    }

    public static String timeStamp2Date(String seconds) {

        return timeStamp2Date(seconds,null);
    }

    public static String time2Date(String time) {


        return time2Date(time,null);
    }

    /**
     *
     * @param time 精确到毫秒
     * @param format
     * @return
     */
    public static String time2Date(String time,String format) {

        if (time == null || time.isEmpty() || time.equals("null")) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = DEFAULT_FORMAT;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(time)));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date   字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 取得当前时间戳（精确到秒）
     *
     * @return
     */
    public static String timeStamp() {
        long time = System.currentTimeMillis();
        String t = String.valueOf(time / 1000);
        return t;
    }


    /**
     * 返回文字描述的日期
     *
     * @param date
     * @return
     */
    public static String Date2TextTime(Date date) {
        if (date == null) {
            return null;
        }
        long diff = new Date().getTime() - date.getTime();
        long r = 0;
        if (diff > MS_YEAR) {
            r = (diff / MS_YEAR);
            return r + "年前";
        }
        if (diff > MS_MONTH) {
            r = (diff / MS_MONTH);
            return r + "个月前";
        }
        if (diff > MS_DAY) {
            r = (diff / MS_DAY);
            return r + "天前";
        }
        if (diff > MS_HOUR) {
            r = (diff / MS_HOUR);
            return r + "个小时前";
        }
        if (diff > MS_MINUTE) {
            r = (diff / MS_MINUTE);
            return r + "分钟前";
        }
        return "刚刚";
    }

    public static String time2TextTime(long time){
        return Date2TextTime(new Date(time));
    }
}

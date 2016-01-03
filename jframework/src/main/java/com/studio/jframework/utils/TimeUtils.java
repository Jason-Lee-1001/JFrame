package com.studio.jframework.utils;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jason
 */
public class TimeUtils {

    public static SimpleDateFormat mSimplePattern = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    public static SimpleDateFormat mShortPattern = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    public static String parseTimeMillis(long time, SimpleDateFormat sdf) {
        return sdf.format(new Date(time));
    }

    public static long toTimeMillis(String time, SimpleDateFormat sdf) {
        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String getSinceTime(long currentTime, String dateStr) throws ParseException {
        if (TextUtils.isEmpty(dateStr)) {
            return "";
        }
        Date date = mSimplePattern.parse(dateStr);
        long since = currentTime - date.getTime();
        if (since < 60 * 1000) {
            return "刚刚";
        } else if (60 * 1000 <= since && since < 60 * 60 * 1000) {
            return since / (60 * 1000) + "分钟前";
        } else if (60 * 60 * 1000 <= since && since < 24 * 60 * 60 * 1000) {
            return since / (60 * 60 * 1000l) + "小时前";
        } else if (24 * 60 * 60 * 1000 <= since && since < 30 * 24 * 60 * 60 * 1000l) {
            return since / (24 * 60 * 60 * 1000l) + "天前";
        } else if (30 * 24 * 60 * 60 * 1000l <= since && since < 12 * 30 * 24 * 60 * 60 * 1000l) {
            return (since / (30 * 24 * 60 * 60 * 1000l)) + "月前";
        }
        return "";
    }

    public static String getSinceTime(long currentTime, long dateTime) throws ParseException {
        long since = currentTime - dateTime;
        if (since < 60 * 1000) {
            return "刚刚";
        } else if (60 * 1000 <= since && since < 60 * 60 * 1000) {
            return since / (60 * 1000) + "分钟前";
        } else if (60 * 60 * 1000 <= since && since < 24 * 60 * 60 * 1000) {
            return since / (60 * 60 * 1000l) + "小时前";
        } else if (24 * 60 * 60 * 1000 <= since && since < 30 * 24 * 60 * 60 * 1000l) {
            return since / (24 * 60 * 60 * 1000l) + "天前";
        } else if (30 * 24 * 60 * 60 * 1000l <= since && since < 12 * 30 * 24 * 60 * 60 * 1000l) {
            return (since / (30 * 24 * 60 * 60 * 1000l)) + "月前";
        }
        return "";
    }

    public static String getShortDate(long timeMills) {
        return mShortPattern.format(new Date(timeMills));
    }

    public static String getYear(long timeMills) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy", Locale.getDefault());
        return sdf.format(new Date(timeMills));
    }

    public static String getMonth(long timeMills) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM", Locale.getDefault());
        return sdf.format(new Date(timeMills));
    }

    public static String getDay(long timeMills) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd", Locale.getDefault());
        return sdf.format(new Date(timeMills));
    }

    public static boolean isWorkingDay(Date date) {
        int dayOfWeek = getDayOfWeek(date);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            return false;
        } else {
            return true;
        }
    }

    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getDayOfYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_YEAR);
    }

    public static Date addDay(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, days);
        return calendar.getTime();
    }

    public static Date addMonth(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, months);
        return calendar.getTime();
    }

    public static Date addYear(Date date, int years) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, years);
        return calendar.getTime();
    }
}

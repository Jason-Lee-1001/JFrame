package com.studio.jframework.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jason
 */
public class TimeUtils {

    public static String parseTimeMillis(long time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        return sdf.format(new Date(time));
    }

    public static long toTimeMillis(String time, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern, Locale.getDefault());
        try {
            return sdf.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static String createAt(long time) {
        long now = System.currentTimeMillis();
        if (now - time > 45) {

        }
        return null;

    }

    /*  now - 1443169814000 < 60000 - > just now
        now - 1443169814000 => 60000*n - > n mins
        now - 1443169814000 => 60*60000*n = 3600000n - > n hrs
        now - 1443169814000 => 24*60*60000*n = 86400000n - > n days
        now - 1443169814000 => 30*24*60*60000*n = 2592000000n - > n months
        now - 1443169814000 => 365*24*60*60000*n = 31536000000 - > n years
     */

}

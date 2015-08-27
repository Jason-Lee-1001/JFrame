package com.studio.jframework.utils;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Build.VERSION;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PhoneUtils {
    private static PhoneUtils instance;
    private Activity act;
    private TelephonyManager tm;

    private PhoneUtils(Activity paramActivity) {
        this.tm = ((TelephonyManager) paramActivity.getSystemService(Context.TELEPHONY_SERVICE));
        this.act = paramActivity;
    }

    public static PhoneUtils getInstance(Activity paramActivity) {
        if (instance == null) {
            instance = new PhoneUtils(paramActivity);
        }
        return instance;
    }

    public static String getBrand() {
        return Build.BRAND;
    }

    public static String getModel() {
        return Build.MODEL;
    }

    public static String getVersion() {
        return VERSION.RELEASE;
    }

    public String[] getCpuInfo() {
        String[] arrayOfString1 = {"", ""};
        try {
            BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/cpuinfo"), 8192);
            String[] arrayOfString2 = localBufferedReader.readLine().split("\\s+");
            for (int i = 2; i < arrayOfString2.length; i++)
                arrayOfString1[0] = (arrayOfString1[0] + arrayOfString2[i] + " ");
            String[] arrayOfString3 = localBufferedReader.readLine().split("\\s+");
            arrayOfString1[1] = (arrayOfString1[1] + arrayOfString3[2]);
            localBufferedReader.close();
            return arrayOfString1;
        } catch (IOException localIOException) {
        }
        return arrayOfString1;
    }

    public String getDeviceCode() {
        String str1 = getPackageName();
        String str2 = getIMEI();
        String str3 = Settings.Secure.getString(this.act.getContentResolver(), "android_id");
        return MD5Utils.get32bitsMD5(str1 + str2 + str3, MD5Utils.ENCRYPTION_A);
    }

    public String getIMEI() {
        if (this.tm == null)
            return null;
        return this.tm.getDeviceId();
    }

    public String getIMSI() {
        if (this.tm == null)
            return null;
        return this.tm.getSubscriberId();
    }

    public String getMacAddress() {
        return ((WifiManager) this.act.getSystemService(Context.WIFI_SERVICE)).getConnectionInfo().getMacAddress();
    }

    public String getMetaData(String paramString) {
        try {
            String str = this.act.getPackageManager().getApplicationInfo(getPackageName(), 128).metaData.getString(paramString);
            return str;
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return "";
    }

    public int getNetWorkType() {
        if (this.tm == null)
            return 0;
        return this.tm.getNetworkType();
    }

    public String getPackageName() {
        return this.act.getPackageName();
    }

    public String getPhoneNumber() {
        if (this.tm == null)
            return null;
        return this.tm.getLine1Number();
    }

    public String getTotalMemory() {
        long l = 0L;
        try {
            BufferedReader localBufferedReader = new BufferedReader(new FileReader("/proc/meminfo"), 8192);
            l = 1024 * Integer.valueOf(localBufferedReader.readLine().split("\\s+")[1]).intValue();
            localBufferedReader.close();
            return Formatter.formatFileSize(this.act, l);
        } catch (IOException localIOException) {
            return Formatter.formatFileSize(this.act, l);
        }
    }

    public boolean isAirModeOpen() {
        return Settings.System.getInt(this.act.getContentResolver(), "airplane_mode_on", 0) == 1;
    }
}
package com.studio.jframework.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;

/**
 * Created by Jason
 *
 * need to be tested
 *
 * NetworkUtils is used to operate network relative stuff
 * Such as GPS, Wifi, Mobile network, Bluetooth
 */
public class NetwrokUtils {

    /**
     * Check if the network is available
     *
     * @param context Context
     * @return True if the network is available, false otherwise
     */
    public static boolean isNetworkAvailable(Context context) {
        try {
            return isWifiNetworkEnable(context) || isMobileNetworkEnable(context);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Check if the mobile network is available
     *
     * @param context Context
     * @return True if network connectivity exists or is in the process of being established, false otherwise
     * @throws Exception
     */
    public static boolean isMobileNetworkEnable(Context context) throws Exception {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isMobileDataEnable;
        isMobileDataEnable = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        return isMobileDataEnable;
    }

    /**
     * Check if the wifi network is available
     *
     * @param context Context
     * @return True if network connectivity exists or is in the process of being established, false otherwise
     * @throws Exception
     */
    public static boolean isWifiNetworkEnable(Context context) throws Exception {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isWifiDataEnable;
        isWifiDataEnable = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        return isWifiDataEnable;
    }

    /**
     * Check if the GPS is enable
     *
     * @param context Context
     * @return True if the GPS is enable, false otherwise
     */
    public static boolean isGpsEnable(Context context) {
        LocationManager locationManager = ((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }
}

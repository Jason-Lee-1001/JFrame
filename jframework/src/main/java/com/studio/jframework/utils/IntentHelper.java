package com.studio.jframework.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * Created by Jason
 *
 * need to be tested
 *
 * IntentHelper is designed for opening native application.
 * Such as call phone, open gallery, open settings .....
 */
public class IntentHelper {

    /**
     * Request code for sdk version lower than 19
     */
    public static final int PICK_PHOTO = 0;

    /**
     * Request code for sdk version higher than 19(including 19)
     */
    public static final int PICK_PHOTO_KITKAT = 1;

    /**
     * Call phone<p/>
     * Specify call phone permission<p/>
     *
     * @param context  Context
     * @param phoneNum The number you wanna dial
     */
    public static void callPhoneNum(Context context, String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));
        context.startActivity(intent);
    }

    /**
     * Open web browser with specify url<p/>
     *
     * @param context Context
     * @param url     The url you wanna open
     */
    public static void openWebPage(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        context.startActivity(intent);
    }

    /**
     * Pick photo from gallery<p/>
     * The content uri will be saved in the intent in onActivityResult
     *
     * @param context Context
     */
    public static void pickPhotoFromGallery(Context context) {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                ((Activity) context).startActivityForResult(intent, PICK_PHOTO_KITKAT);
            } else {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
                intent.setType("image/*");
                ((Activity) context).startActivityForResult(intent, PICK_PHOTO);
            }
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Go to the gps setting page
     *
     * @param context Context
     */
    public static void openGPSSetting(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException ex) {
            intent.setAction(Settings.ACTION_SETTINGS);
            try {
                context.startActivity(intent);
            }catch (ActivityNotFoundException e){
                e.printStackTrace();
            }
        }
    }

}

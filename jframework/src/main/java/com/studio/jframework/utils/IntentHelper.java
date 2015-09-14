package com.studio.jframework.utils;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.text.TextUtils;

import java.io.File;

/**
 * need to be tested
 * <p/>
 * <p>IntentHelper is designed for opening native application.
 * Such as call phone, open gallery, open settings .....
 *
 * @author Jason
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
    @SuppressWarnings("ResourceType")
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
     * Launch camera and take a picture
     *
     * @param activity Activity
     * @param dir      The dir to store the picture
     * @param filename The file name of the picture
     * @param code     The code used in startActivityForResult
     * @return True if the camera start successfully, false otherwise
     */
    public static boolean takePhoto(Activity activity, String dir, String filename, int code) {
        String filePath = dir + filename;

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File cameraDir = new File(dir);
        LogUtils.d("filePath", filePath);
        LogUtils.d("cameraDir exist", cameraDir.exists() + "");
        if (!cameraDir.exists()) {
            return false;
        }

        File file = new File(filePath);
        Uri outputFileUri = Uri.fromFile(file);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
        try {
            activity.startActivityForResult(intent, code);
        } catch (ActivityNotFoundException e) {
            return false;
        }
        return true;
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
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Open the email client and send email
     *
     * @param context Context
     * @param email   The email address
     * @param subject The email receiver
     * @param content The email content
     */
    public static void sendEmail(Context context, String email, String subject, String content) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("plain/text");
            String address[] = new String[]{email};
            if (!TextUtils.isEmpty(subject)) {
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
            }
            if (!TextUtils.isEmpty(content)) {
                intent.putExtra(Intent.EXTRA_TEXT, content);
            }
            intent.putExtra(Intent.EXTRA_EMAIL, address);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Show the action share dialog
     *
     * @param context Context
     * @param title   The title that show in share action chooser
     * @param content The content you wanna share
     */
    public static void showTextActionShare(Context context, String title, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/*").putExtra(Intent.EXTRA_TEXT, content);
        Intent choose = Intent.createChooser(intent, title);
        context.startActivity(choose);
    }

    /**
     * Install an app by apk file
     *
     * @param context Context
     * @param apkFile The apk file
     */
    public static void installApp(Context context, File apkFile){
        Intent install = new Intent(Intent.ACTION_VIEW);
        install.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(install);
    }

}

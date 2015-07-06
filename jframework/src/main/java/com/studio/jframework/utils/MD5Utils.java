package com.studio.jframework.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * A tool to encrypt a String with MD5, provide two ways
 */
public class MD5Utils {

    /**
     * Method 1
     */
    public static final int ENCRYPTION_A = 1;
    /**
     * Method 2
     */
    public static final int ENCRYPTION_B = 2;

    /**
     * MD5 Encrypt
     *
     * @param string The string to be encrypted
     * @return The encrypted string
     */
    public static String get32bitsMD5(String string, int method) {
        switch (method) {
            case ENCRYPTION_A: {
                MessageDigest messageDigest = null;
                try {
                    messageDigest = MessageDigest.getInstance("MD5");
                    messageDigest.reset();
                    messageDigest.update(string.getBytes("UTF-8"));
                } catch (NoSuchAlgorithmException e) {
                    System.exit(-1);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                byte[] byteArray = messageDigest.digest();
                StringBuilder md5StrBuff = new StringBuilder();
                for (byte aByteArray : byteArray) {
                    if (Integer.toHexString(0xFF & aByteArray).length() == 1) {
                        md5StrBuff.append("0").append(Integer.toHexString(0xFF & aByteArray));
                    } else {
                        md5StrBuff.append(Integer.toHexString(0xFF & aByteArray));
                    }
                }
                return md5StrBuff.toString();
            }
            case ENCRYPTION_B: {
                char hexDigits[] = {
                        '0', '1', '2', '3',
                        '4', '5', '6', '7',
                        '8', '9', 'a', 'b',
                        'c', 'd', 'e', 'f'};
                try {
                    byte[] strTemp = string.getBytes();
                    MessageDigest mdTemp = MessageDigest.getInstance("MD5");
                    mdTemp.update(strTemp);
                    byte[] md = mdTemp.digest();
                    int j = md.length;
                    char str[] = new char[j * 2];
                    int k = 0;
                    for (int i = 0; i < j; i++) {
                        byte b = md[i];
                        str[k++] = hexDigits[b >> 4 & 0xf];
                        str[k++] = hexDigits[b & 0xf];
                    }
                    return new String(str);
                } catch (Exception e) {
                    return null;
                }
            }
            default:
                return null;
        }
    }
}

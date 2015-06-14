package com.studio.jframework.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    /**
     * Method 1
     */
    public static final int ENCRYPT_METHOD_1 = 1;
    /**
     * Method 2
     */
    public static final int ENCRYPT_METHOD_2 = 2;

    /**
     * MD5 Encrypt
     *
     * @param string The string to be encrypted
     * @return The encrypted string
     */
    public static String get32bitsMD5(String string, int method) {
        switch (method) {
            case ENCRYPT_METHOD_1: {
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
            case ENCRYPT_METHOD_2: {
                char hexDigits[] = {
                        '0', '1', '2', '3',
                        '4', '5', '6', '7',
                        '8', '9', 'a', 'b',
                        'c', 'd', 'e', 'f'};
                try {
                    byte[] strTemp = string.getBytes();
                    // 使用MD5创建MessageDigest对象
                    MessageDigest mdTemp = MessageDigest.getInstance("MD5");
                    mdTemp.update(strTemp);
                    byte[] md = mdTemp.digest();
                    int j = md.length;
                    char str[] = new char[j * 2];
                    int k = 0;
                    for (int i = 0; i < j; i++) {
                        byte b = md[i];
                        // System.out.println((int)b);
                        // 将没个数(int)b进行双字节加密
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

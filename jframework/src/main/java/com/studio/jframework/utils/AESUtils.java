package com.studio.jframework.utils;

import android.annotation.SuppressLint;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Jason<p/>
 * <p/>
 * A class that is used to encrypt and decrypt
 */
public class AESUtils {

    private Cipher mCiper;

    private final static String HEX = "0123456789ABCDEF";

    @SuppressLint("GetInstance")
    public AESUtils() {
        try {
            mCiper = Cipher.getInstance("AES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            e.printStackTrace();
        }
    }

    /**
     * A method to generate a key
     *
     * @param seed The original seed for generating the key
     * @return The generated key
     */
    public Key generateKey(byte[] seed) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
            sr.setSeed(seed);
            keyGenerator.init(128,sr);
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] keyBytes = secretKey.getEncoded();
            return new SecretKeySpec(keyBytes, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] encryptToBytes(Key key, String source) {
        try {
            mCiper.init(Cipher.ENCRYPT_MODE, key);
            return mCiper.doFinal(source.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public byte[] decryptToBytes(Key key, byte[] encrypted) {
        try {
            mCiper.init(Cipher.DECRYPT_MODE, key);
            return mCiper.doFinal(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toHex(String txt) {
        return toHex(txt.getBytes());
    }

    public String fromHex(String hex) {
        return new String(toByte(hex));
    }

    public byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }

    public String toHex(byte[] buf) {
        if (buf == null)
            return "";
        StringBuffer result = new StringBuffer(2 * buf.length);
        for (byte aBuf : buf) {
            appendHex(result, aBuf);
        }
        return result.toString();
    }

    private void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

}

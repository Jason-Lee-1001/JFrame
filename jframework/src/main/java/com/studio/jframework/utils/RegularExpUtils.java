package com.studio.jframework.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Provide the most common regular expression methods
 *
 * @author Jason
 */
public class RegularExpUtils {

    /**
     * Judge if the string is email address
     *
     * @param mail The email address string
     * @return True if the string is email address, false otherwise
     */
    public static boolean isMail(String mail) {
        boolean flag;
        try {
            String check = "^[\\w-]+(\\.[\\w-]+)*@[\\w-]+(\\.[\\w-]+)+$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(mail);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * Judge if the string is mobile number
     *
     * @param mobile The mobile number string
     * @return True if the string is mobile number, false otherwise
     */
    public static boolean isMobile(String mobile) {
        return mobile.matches("^[1][3,5,8,4,7]+\\d{9}");
    }

    /**
     * Judge if the string combine with numbers
     *
     * @param paramString The string to be judge
     * @return True if the string is combined with number, false otherwise
     */
    public static boolean isNumeric(String paramString) {
        if (paramString == null) {
            return false;
        }
        int i = paramString.length();
        for (int j = 0; ; j++) {
            if (j >= i)
                break;
            if (!Character.isDigit(paramString.charAt(j)))
                break;
        }
        return true;
    }

}

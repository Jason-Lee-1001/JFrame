package com.studio.jframework.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * @author Jason
 */
public class JsonUtils {

    /**
     * Parse json string to an object
     * @param t An object
     * @param string Json string to be parsed
     * @param <T> Type of the class
     * @return The desired object that parsed from the string
     * @throws JsonSyntaxException
     */
    public static <T> T parseToObject(Class<T> t, String string) throws JsonSyntaxException{
        Gson gson = new Gson();
        return gson.fromJson(string, t);
    }

}

package com.studio.jframework.utils;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Arrays;
import java.util.List;

/**
 * @author Jason
 */
public class JsonUtils {

    /*You may declare a Model like this
    public class Person{
        public static final String CREATE_AT = "create_at";

        public int id;
        @SerializedName(CREATE_AT)
        public String createAt;
    }
    */

    /**
     * Parse json string to an object
     *
     * @param t      The class you want to cast
     * @param string Json string to be parsed
     * @param <T>    Type of the class
     * @return The desired object that parsed from the string
     * @throws JsonSyntaxException
     */
    public static <T> T parseToObject(Class<T> t, String string) throws JsonSyntaxException {
        return new Gson().fromJson(string, t);
    }

    /**
     * Parse json array to a List
     *
     * @param t      The list
     * @param string Json string to be parsed
     * @param <T>    Type of the class
     * @return The desired list that parsed from the string
     * @throws JsonSyntaxException
     */
    public static <T> List<T> parseToList(Class<T[]> t, String string) throws JsonSyntaxException {
        return Arrays.asList(new Gson().fromJson(string, t));
    }

}

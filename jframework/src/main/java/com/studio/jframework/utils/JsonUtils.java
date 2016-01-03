package com.studio.jframework.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jason
 */
public class JsonUtils {

    /*需要自定义字段名但跟服务器返回的字段不相同时，可以用@SerializedName注解
    public class Person{
        public static final String CREATE_AT = "create_at";

        public int id;
        @SerializedName(CREATE_AT)
        public String createAt;
    }
    */

    /**
     * 将Json字符串转为Bean类
     *
     * @param clazz  类型，如City.class
     * @param string 需要被转化的字符串
     * @param <T>    类型
     * @return T指定的类型
     * @throws JsonSyntaxException
     */
    public static <T> T parseToObject(Class<T> clazz, String string) throws JsonSyntaxException {
        return new Gson().fromJson(string, clazz);
    }

    /**
     * 将JsonElement转为Bean类
     *
     * @param clazz   类型，如City.class
     * @param element 需要被转化的JsonElement
     * @param <T>     类型
     * @return T指定的类型
     * @throws JsonSyntaxException
     */
    public static <T> T parseToObject(Class<T> clazz, JsonElement element) throws JsonSyntaxException {
        return new Gson().fromJson(element, clazz);
    }

    /**
     * 将Json字符串转为List
     *
     * @param clazz  类型，如City.class
     * @param string 需要被转化的Json字符串
     * @param <T>    类型
     * @return T指定的类型构成的list
     * @throws JsonSyntaxException
     */
    public static <T> List<T> parseToList(Class<T> clazz, String string) throws JsonSyntaxException {
        return parseToList(clazz, new JsonParser().parse(string));
    }

    /**
     * 将JsonElement转为List
     *
     * @param clazz   类型，如City.class
     * @param element 需要被转化的JsonElement
     * @param <T>     类型
     * @return T指定的类型
     * @throws JsonSyntaxException
     */
    public static <T> List parseToList(Class<T> clazz, JsonElement element) throws JsonSyntaxException {
        JsonArray array = element.getAsJsonArray();
        List<T> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            list.add(parseToObject(clazz, array.get(i)));
        }
        return list;
    }
}

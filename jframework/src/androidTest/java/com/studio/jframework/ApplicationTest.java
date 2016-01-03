package com.studio.jframework;

import android.app.Application;
import android.test.ApplicationTestCase;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.studio.jframework.utils.JsonUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    public void testGsonNum1() {
        String str = "[{'id': '1','code': 'bj','name': '北京','map': '39.90403, 116.40752599999996'}, {'id': '2','code': 'sz','name': '深圳','map': '22.543099, 114.05786799999998'}, {'id': '9','code': 'sh','name': '上海','map': '31.230393,121.473704'}, {'id': '10','code': 'gz','name': '广州','map': '23.129163,113.26443500000005'}]";
        String singleStr = "{'id': '1','code': 'bj','name': '北京','map': '39.90403, 116.40752599999996'}";

        JsonParser parser = new JsonParser();
//        JsonObject object = parser.parse(singleStr).getAsJsonObject();
//        City single = JsonUtils.parseToObject(City.class, object);
//        System.out.println("---->"+single.toString());

        JsonArray array = parser.parse(str).getAsJsonArray();
        List<City> citylist = JsonUtils.parseToList(City.class, array);
        for (City city :
                citylist) {
            System.out.println(city.toString());
        }

    }

    public static <T> void parse(String str, T t) {
        Gson gson = new Gson();
        List<T> rs;
        Type type = new TypeToken<ArrayList<T>>() {
        }.getType();
        rs = gson.fromJson(str, type);
        for (T o : rs) {
            System.out.println(o.toString());
        }
    }
}
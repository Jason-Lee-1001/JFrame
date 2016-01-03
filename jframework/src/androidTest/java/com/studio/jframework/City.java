package com.studio.jframework;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jason
 */
public class City {
    /*'id': '1','code': 'bj','name': '北京','map': '39.90403, 116.40752599999996'*/

    @SerializedName("id")
    private String cityId;
    @SerializedName("code")
    private String cityCode;
    @SerializedName("name")
    private String cityName;
    @SerializedName("map")
    private String cityLocation;

    @Override
    public String toString() {
        return "City{" +
                "cityId='" + cityId + '\'' +
                ", cityCode='" + cityCode + '\'' +
                ", cityName='" + cityName + '\'' +
                ", cityLocation='" + cityLocation + '\'' +
                '}';
    }
}

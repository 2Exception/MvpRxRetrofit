package com.example.andy.mvprxretrofit.model.weather;

import com.google.gson.annotations.SerializedName;

/**
 * <pre>
 *
 *
 *          天气回应体
 *
 *      <pre/>
 *
 * 文件名： MvpRxRetrofit
 * Created by WestDeco on 2017/3/31.
 * 签名： 用风雅的态度看世界，用痞子的风格过日子
 * E-mail：  717616019@qq.com
 * GitHub：  https://github.com/KellenHu
 * CSDN：    http://my.csdn.net/westdeco
 */

public class ShowWeatherBody {

    @SerializedName("f1")
    public ShowApiWeatherNormalInner Weather;//后一天的天气预报
    @SerializedName("now")
    public ShowApiWeatherNowInner now;//现在的天气预报

    public class ShowApiWeatherNormalInner{
        public String day;//日期
        public String air_press;//气压
        public String sun_begin_end;//白天持续时间
        public String weekday;//星期几
        public String ziwaixian;//紫外线
        //白天
        public String day_air_temperature;//气温
        public String day_weather;//天气“晴雨”
        public String day_weather_code;//天气代码
        public String day_weather_pic;//天气图片
        public String day_wind_direction;//风向
        public String day_wind_power;//风力
        //晚上
        public String night_air_temperature;
        public String night_weather;
        public String night_weather_code;
        public String night_weather_pic;
        public String night_wind_direction;
        public String night_wind_power;
    }

    public class ShowApiWeatherNowInner {
        public String aqi;//污染指数
        public String sd;//湿度
        public String temperature;//气温
        public String temperature_time;//气温时间
        public String weather;//天气“晴雨”
        public String weather_code;//天气代码
        public String weather_pic;//天气图片
        public String wind_direction;//风向
        public String wind_power;//风力
    }
}

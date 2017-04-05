package com.example.andy.mvprxretrofit.server;

import com.example.andy.mvprxretrofit.common.BizInterface;
import com.example.andy.mvprxretrofit.model.entity.ShowApiResponse;
import com.example.andy.mvprxretrofit.model.picture.PictureBody;
import com.example.andy.mvprxretrofit.model.weather.ShowWeatherBody;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * <pre>
 *
 *
 *      百度网络请求API（基于Retrofit）
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

public interface BaiduAPI {

    /**
     * 天气预报响应
     * @param Cache-Control 原本需要传递这个参数，但是在RetrofitService中设置了拦截器，就不需要传递这个参数了
     * @param area 地区名称，比如北京
     * @param needMoreDay 是否需要返回7天数据中的后4天。1为返回，0为不返回。
     * @param needIndex 是否需要返回指数数据，比如穿衣指数、紫外线指数等。1为返回，0为不返回。
     * @param needAlarm 是否需要天气预警。1为需要，0为不需要。
     * @param need3HourForcast 是否需要当天每3小时1次的天气预报列表。1为需要，0为不需要。
     * @return
     */
    @GET(BizInterface.WEATHER_URL)
    @Headers("apikey:" + BizInterface._API_KEY)
    Observable<ShowApiResponse<ShowWeatherBody>> getWeather(@Query("area") String area,
                                                            @Query("needMoreDay") String needMoreDay,
                                                            @Query("needIndex") String needIndex,
                                                            @Query("needAlarm") String needAlarm,
                                                            @Query("need3HourForcast") String need3HourForcast);

    /**
     * 美图大全响应
     * @param type "id": 4001, //此id很重要，在【图片查询】接口里将使用此id进行分类查询
    "name": "清纯"
     * @param page 页数
     * @return
     */
    @GET(BizInterface.PICTURES_URL)
    @Headers("apikey: " + BizInterface._API_KEY)
    Observable<ShowApiResponse<PictureBody>> getPictures(
                                                         @Query("type") String type,
                                                         @Query("page") String page);

}

package com.example.andy.mvprxretrofit.server;

import com.example.andy.mvprxretrofit.common.BizInterface;
import com.example.andy.mvprxretrofit.model.entity.ShowApiResponse;
import com.example.andy.mvprxretrofit.model.news.NewsBody;
import com.example.andy.mvprxretrofit.model.news.ShowApiNews;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;

/**
 * <pre>
 *
 *
 *      易源的API，
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

public interface ShowAPI {

    /**
     * 新闻列表
     * @param cacheControl 缓存控制
     * @param appId 易源appid
     * @param key 易源密钥
     * @param page 页数
     * @param channelId 频道id
     * @param channelName 名称
     * @return
     */
    @GET(BizInterface.NEWS_URL)
    @Headers("apikey: " + BizInterface._API_KEY)
    Call<ShowApiResponse<ShowApiNews>> getNewsList(
                                                   @Query("showapi_appid") String appId,
                                                   @Query("showapi_sign") String key,
                                                   @Query("page") String page,
                                                   @Query("channelId") String channelId,//新闻频道id，必须精确匹配
                                                   @Query("channelName") String channelName);//新闻频道名称，可模糊匹配


    /**
     * 上传不固定图片数量
     * 上传文件示例接口
     * @param description
     * @param fileMap
     * @return
     */
    @Multipart
    @POST("upload")
    Observable<String> upload(@Part("description") String description,
                              @PartMap Map<String,RequestBody> fileMap);


}

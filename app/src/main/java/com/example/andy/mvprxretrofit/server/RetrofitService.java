package com.example.andy.mvprxretrofit.server;

import android.support.annotation.NonNull;

import com.example.andy.mvprxretrofit.MyApplication;
import com.example.andy.mvprxretrofit.common.BizInterface;
import com.example.andy.mvprxretrofit.utils.NetUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.internal.http.OkHeaders;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 *
 * <pre>
 *
 *     网络请求引引擎类，包含网络拦截器，包含网络缓存策略
 *     <pre/>
 *
 *
 * 文件名： MvpRxRetrofit
 * Created by WestDeco on 2017/3/31.
 * 签名： 用风雅的态度看世界，用痞子的风格过日子
 * E-mail：  717616019@qq.com
 * GitHub：  https://github.com/KellenHu
 * CSDN：    http://my.csdn.net/westdeco
 */

public class RetrofitService {

    //设置 数据的缓存时间，有效期为两天
    protected static final long CACHE_STALE_SEC= 60 * 60 * 24 * 2;
    //查询缓存的Cache-Control 配置，为 only-if-cache 时，只查询缓存而不会请求服务器， max-stale可以配合设置缓存失效时间
    protected static final String CACHE_CONTROL_CACHE = "only-if-cache, max-stale=" + CACHE_STALE_SEC;
    //查询缓存的Cache-Control配置，为 Cache-Control设置为max-age=0时则不会使用缓存，而是请求服务器
    protected static final String CACHE_CONTROL_NETWORK = "max-age=0";

    /**
     * 单例模式的 OKhttpClient,以及Retrifit引擎类
     */
    private static OkHttpClient mOkHttpClient ;
    private static RetrofitService instance = null;

    //私有构造函数，使用newInstance方式访问对象(单利RetrofitService)
    private RetrofitService(){}
    //单利 引擎
    public static RetrofitService getInstance(){
        if (instance == null){
            synchronized (RetrofitService.class){
                if (instance == null){
                    instance = new RetrofitService();
                }
            }
        }
        return instance;
    }


    /**
     * 百度API,也是单列模式
     */
    private volatile static BaiduAPI baiduAPI = null;
    public static BaiduAPI createBaiduAPI (){
        if (baiduAPI == null){
            synchronized (RetrofitService.class){
                if (baiduAPI == null){
                    initOkHttpClient();
                    baiduAPI = new Retrofit.Builder()
                            .client(mOkHttpClient)
                            .baseUrl(BizInterface.BAIDU_API)
                            .addConverterFactory(ScalarsConverterFactory.create())////增加返回值为String的支持
                            .addConverterFactory(GsonConverterFactory.create())//增加返回值为Gson实体类的支持
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//增加对Rx操作的支持
                            .build().create(BaiduAPI.class);
                }
            }
        }
        return baiduAPI;
    }

    /**
     * 易源api
     */
    private volatile static ShowAPI showAPI = null;
    public static ShowAPI createShowAPI(){
        if (showAPI == null){
            synchronized (RetrofitService.class){
                if (showAPI == null){
                    initOkHttpClient();
                    showAPI = new Retrofit.Builder()
                            .client(mOkHttpClient)
                            .baseUrl(BizInterface.SHOW_API)
                            .addConverterFactory(GsonConverterFactory.create())//增加Gson实体类的支持
                            .addConverterFactory(ScalarsConverterFactory.create())////增加返回值为String的支持
//                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build().create(ShowAPI.class);
                }
            }
        }
        return showAPI;
    }


    /**
     * 初始化构造 okhttp客户端
     */
    private static void initOkHttpClient() {
        if (mOkHttpClient == null){
            //因为涉及到百度和易缘的两个API，BaseUrl的不同，所以这里的Retrofit不为静态（在create***Api中动态创建）,但是okHttpClieat的配置是一样的，送一可以用静态创建一次
            File cacheFile = new File(MyApplication.getContext().getCacheDir(),"HttpCache");//指定缓存路径
            Cache cache = new Cache(cacheFile,1024*1024*100);//指定缓存最大大小为100Mb
            //云端相应头拦截器，用来动态配置缓存策略
            Interceptor reWriteCacheControlInterceptor = new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    //如果请求的时候没网
                    if (!NetUtil.isConnected(MyApplication.getContext())){
                        request = request.newBuilder()
                                .cacheControl(CacheControl.FORCE_CACHE)
                                .build();//强制读取缓存
                    }
                    //响应的时候
                    Response originalResponse = chain.proceed(request);
                    if (NetUtil.isConnected(MyApplication.getContext())){
                        //有网的时候读取接口上的@Headers里的配置，你可以在这里统一配置
                        String cacheControl = request.cacheControl().toString();
                        return originalResponse.newBuilder()
                                .header("Cache-Control",cacheControl)
                                .removeHeader("Pragma").build();
                    }else {
                        return originalResponse.newBuilder().header("Cache-Control",
                                "public, only-if-cached," + CACHE_STALE_SEC)
                                .removeHeader("Pragma").build();
                    }
                }
            };

            /**
             * okhttp 2.x
             */
//            mOkHttpClient = new OkHttpClient();
//            mOkHttpClient.setCache(cache);
//            mOkHttpClient.networkInterceptors().add(rewriteCacheControlInterceptor);
//            mOkHttpClient.interceptors().add(rewriteCacheControlInterceptor);
//            mOkHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
            /**
             * okhttp 3.x
             */
            mOkHttpClient = new OkHttpClient.Builder()
                    .cache(cache)
                    .addNetworkInterceptor(reWriteCacheControlInterceptor)
                    .addInterceptor(reWriteCacheControlInterceptor)
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .build();
        }
    }


    /**
     * 根据网络状况过去缓存策略
     * @return
     */
    @NonNull
    public static String getCacheControl(){
        return NetUtil.isConnected(MyApplication.getContext()) ? CACHE_CONTROL_NETWORK : CACHE_CONTROL_CACHE;
    }
}

package com.example.andy.mvprxretrofit.server;

import com.orhanobut.logger.Logger;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 文件名： MvpRxRetrofit
 * Created by WestDeco on 2017/3/31.
 * 签名： 用风雅的态度看世界，用痞子的风格过日子
 * E-mail：  717616019@qq.com
 * GitHub：  https://github.com/KellenHu
 * CSDN：    http://my.csdn.net/westdeco
 */
@Deprecated
public class LoggingIntercept implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //获取精度更高的 纳微秒
        long t1 = System.nanoTime();
        //打印请求信息
        Logger.d(String.format("发送网络请求 request %s  on  %s%n%s",request.url(),chain.connection(),request.headers()));
        //打印返回response信息
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();
        Logger.i(String.format("接收网络请求 response  for %s  in  %.1fms%n%s",response.request().url(),(t2-t1)/1e6d,response.headers()));
        return null;
    }
}

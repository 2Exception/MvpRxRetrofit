package com.example.andy.mvprxretrofit.model.news;

import android.accounts.NetworkErrorException;

import com.example.andy.mvprxretrofit.common.BizInterface;
import com.example.andy.mvprxretrofit.model.INetLoadModel;
import com.example.andy.mvprxretrofit.model.OnNetResponseListener;
import com.example.andy.mvprxretrofit.model.entity.ShowApiResponse;
import com.example.andy.mvprxretrofit.server.RetrofitService;
import com.orhanobut.logger.Logger;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * <pre>
 *
 *      新闻的请求结果
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

public class NewsModelImpl implements INetLoadModel<List<NewsBody>>{

    public static final String CHANNEL_ID = "5572a109b3cdc86cf39001db";//频道id 来自api指定
    public static final String CHANNEL_NAME = "国内最新";//频道名称 来自api指定


    @Override
    public void onNetLoad(final OnNetResponseListener<List<NewsBody>> onNetResponseListener, String... params) {
        //注意，此处采用Retrofit的官方响应方式，天气预报采用RxJava，学习一下两种用法
        Call<ShowApiResponse<ShowApiNews>> call = RetrofitService.getInstance()
                .createShowAPI().getNewsList(BizInterface.SHOW_API_APPID
                        ,BizInterface.SHOW_API_KEY,params[0],params[1],params[2]);


        call.enqueue(new Callback<ShowApiResponse<ShowApiNews>>() {
            @Override
            public void onResponse(Call<ShowApiResponse<ShowApiNews>> call, Response<ShowApiResponse<ShowApiNews>> response) {
                if (response.body() != null){
                    Logger.d(response.message() + response.code() + response.body().showapi_res_code
                            + response.body().showapi_res_error);
                    //回掉请求结果
                    onNetResponseListener.onSuccess(response.body().showapi_res_body.pagebean.contentlist);
                }else {
                    onNetResponseListener.onFailure(new Exception("网络请求失败 Code:" + response.code() + "\nMessage:" + response.message()));
                }
            }

            @Override
            public void onFailure(Call<ShowApiResponse<ShowApiNews>> call, Throwable t) {
                onNetResponseListener.onFailure(t);
            }
        });
    }
}

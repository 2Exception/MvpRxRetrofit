package com.example.andy.mvprxretrofit.model.weather;

import com.example.andy.mvprxretrofit.model.IFileUploadModel;
import com.example.andy.mvprxretrofit.model.INetLoadModel;
import com.example.andy.mvprxretrofit.model.OnNetResponseListener;
import com.example.andy.mvprxretrofit.model.entity.ShowApiResponse;
import com.example.andy.mvprxretrofit.server.RetrofitService;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;

/**
 * <pre>
 *
 *      请求天气网络实现类，模拟操作了一波文件上传
 *
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

public class WeatherNetModelImpl  implements INetLoadModel<ShowWeatherBody>,IFileUploadModel<String>{

    @Override
    public void onNetLoad(final OnNetResponseListener onNetResponseListener, String... params) {

        Observable<ShowApiResponse<ShowWeatherBody>> observable = RetrofitService.createBaiduAPI()
                .getWeather(params[0],params[1],params[2],params[3],params[4]);

        observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>(){
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        onNetResponseListener.onStart();
                    }
                })
                .subscribe(new Observer<ShowApiResponse<ShowWeatherBody>>() {
                    @Override
                    public void onSubscribe(Disposable d){
                    }
                    @Override
                    public void onNext(ShowApiResponse<ShowWeatherBody> value){
                        //这是判断是否是业务层面的服务器请求异常，此处是根据返回的值是否为空来判断的，根据自己和服务器的约定自行判断业务异常
                        if (value.showapi_res_body.now != null){
                                onNetResponseListener.onSuccess(value.showapi_res_body);
                            }else {
                                onNetResponseListener.onFailure(new Exception(value.showapi_res_code));
                            }
                    }

                    @Override
                    public void onError(Throwable e){
                        //此处属于服务器请求异常！
                        onNetResponseListener.onFailure(e);
                        onNetResponseListener.onFinish();
                    }

                    @Override
                    public void onComplete() {
                            //仅成功后会调用,失败则不会调用
                        onNetResponseListener.onFinish();
                    }
                });
    }

    /**
     * 实现上传文件接口
     * @param onNetResponseListener 网络的回掉
     * @param fileMap 文件的map
     */
    @Override
    public void onFileUpload(final OnNetResponseListener<String> onNetResponseListener, String description, Map<String, RequestBody> fileMap) {
        Observable<String> observable = RetrofitService.createShowAPI().upload(description,fileMap);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        onNetResponseListener.onStart();
                    }
                })
                .subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                onNetResponseListener.onSuccess(value);
            }

            @Override
            public void onError(Throwable e) {
                onNetResponseListener.onFailure(e);
                onNetResponseListener.onFinish();
            }

            @Override
            public void onComplete() {
                onNetResponseListener.onFinish();
            }
        });


    }
}

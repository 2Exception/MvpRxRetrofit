package com.example.andy.mvprxretrofit.view.homepage;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.example.andy.mvprxretrofit.R;
import com.example.andy.mvprxretrofit.delegate.HomeFragmentDelegate;
import com.example.andy.mvprxretrofit.model.OnNetResponseListener;
import com.example.andy.mvprxretrofit.model.weather.ShowWeatherBody;
import com.example.andy.mvprxretrofit.model.weather.WeatherNetModelImpl;
import com.example.andy.mvprxretrofit.mvp_base_frame.presenter.FragmentPresenter;
import com.example.andy.mvprxretrofit.server.RetrofitService;
import com.orhanobut.logger.Logger;

/**
 * <pre>
 *
 *
 *
 *
 *      <pre/>
 *
 * 文件名： MvpRxRetrofit
 * Created by WestDeco on 2017/4/1.
 * 签名： 用风雅的态度看世界，用痞子的风格过日子
 * E-mail：  717616019@qq.com
 * GitHub：  https://github.com/KellenHu
 * CSDN：    http://my.csdn.net/westdeco
 */

public class HomeFragment extends FragmentPresenter<HomeFragmentDelegate> implements View.OnClickListener{

    public static final String NEED_MORE_DAY = "1";
    public static final String NEED_INDEX = "1";
    public static final String NEED_ALARM = "1";
    public static final String NEED_3_HOUR_FORCAST = "1";
    /**
     *  WeatherModel
     */
    WeatherNetModelImpl weatherNetModel;

    @Override
    protected Class<HomeFragmentDelegate> getDelegateClass() {
        return HomeFragmentDelegate.class;
    }
    /**
     * create方式
     * @return
     */
    public static HomeFragment newInstance(){
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        weatherNetModel = new WeatherNetModelImpl();
    }


    @Override
    protected void bindEventListener(){
        super.bindEventListener();
        viewDelegate.setOnClickListener(this,R.id.bt_weather);
    }

    /**
     * 请求天气
     */
    private void netWeather(){
        if (TextUtils.isEmpty(viewDelegate.getInputLocation())){
            viewDelegate.showSnackbar("输入不能为空");
            return;
        }
        weatherNetModel.onNetLoad(new OnNetResponseListener<ShowWeatherBody>() {
            @Override
            public void onStart(){
                viewDelegate.showLoading();
            }
            @Override
            public void onFinish(){
                viewDelegate.showContent();
            }
            @Override
            public void onSuccess(ShowWeatherBody data){
                viewDelegate.closeSoftInput();
                viewDelegate.showNowWeatherDialog(data);
            }
            @Override
            public void onFailure(Throwable t){
                viewDelegate.showSnackbar("请求错误");
                Logger.e("Net Failure!");
            }
        },viewDelegate.getInputLocation(),NEED_MORE_DAY,NEED_INDEX,NEED_ALARM,NEED_3_HOUR_FORCAST);
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.bt_weather:
                netWeather();//请求天气接口
                break;
        }
    }
}
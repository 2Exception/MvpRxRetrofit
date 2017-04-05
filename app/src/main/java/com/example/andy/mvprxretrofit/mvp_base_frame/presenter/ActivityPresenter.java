package com.example.andy.mvprxretrofit.mvp_base_frame.presenter;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.andy.mvprxretrofit.base.MySwipeBackActivity;
import com.example.andy.mvprxretrofit.mvp_base_frame.view.AppDelegate;
import com.example.andy.mvprxretrofit.mvp_base_frame.view.IDelegate;

/**
 * 文件名： MvpRxRetrofit
 * Created by WestDeco on 2017/3/29.
 * 签名： 用风雅的态度看世界，用痞子的风格过日子
 * E-mail：  717616019@qq.com
 * GitHub：  https://github.com/KellenHu
 * CSDN：    http://my.csdn.net/westdeco
 */

public abstract class ActivityPresenter <T extends IDelegate> extends MySwipeBackActivity{

    protected T viewDelegate;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public ActivityPresenter(){
        try{
            viewDelegate = getDelegateClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("create IDelegete error");
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDelegate.create(getLayoutInflater(),null,savedInstanceState);
        //为Activity设置从代理那里取到的rootView
        setContentView(viewDelegate.getRootView());
        //为activity设置从代理那里取到的Toolbar
        initToolbar();
        //设置控件
        viewDelegate.initWidget();
        //初始化View
        initView();
        //初始化数据
        initData(savedInstanceState);
        //绑定事件监听
        bindEventListener();
    }

    protected void bindEventListener(){
    };

    protected abstract void initData(@Nullable Bundle savedInstanceState);

    protected abstract void initView();

    /**
     *    //在当前状态防备代理类被销毁
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        try{
            if (viewDelegate == null)
                viewDelegate = getDelegateClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException("create IDelegate error");
        }
    }

    /**
     * 创建Toolbar的菜单栏
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (viewDelegate.getOptionsMenuId() != 0){
            getMenuInflater().inflate(viewDelegate.getOptionsMenuId(),menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewDelegate = null;
    }

    /**
     * 展示SnackBar   此功能暂时不提供，由开发人员自己定制，或者抽离到Util类
     */
    public void showSnakeBar(String msg){
//        Snackbar.make()
    }

    /**
     * 设置Toolbar
     */
    private void initToolbar() {
        Toolbar toolbar = viewDelegate.getToolBar();
        if (toolbar != null )
            setSupportActionBar(toolbar);
    }

    /**
     * 取得实际视图代理类
     * @return
     */
    protected abstract Class<T> getDelegateClass();
}

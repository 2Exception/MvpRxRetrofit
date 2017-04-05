package com.example.andy.mvprxretrofit.mvp_base_frame.view;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 文件名： MvpRxRetrofit
 * Created by WestDeco on 2017/3/29.
 * 签名： 用风雅的态度看世界，用痞子的风格过日子
 * E-mail：  717616019@qq.com
 * GitHub：  https://github.com/KellenHu
 * CSDN：    http://my.csdn.net/westdeco
 *
 *
 *
 * 描述：  视图层的代理协议
 */

public interface IDelegate {
    void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    int getOptionsMenuId();

    Toolbar getToolBar();

    View getRootView();

    void initWidget();
}

package com.example.andy.mvprxretrofit.view;

import android.content.Context;
import android.view.View;

/**
 * <Pre>
 *     加载视图接口
 * </Pre>
 *
 * Created by WestDeco on 2017/3/29.
 * 签名： 用风雅的态度看世界，用痞子的风格过日子
 * E-mail：  717616019@qq.com
 * GitHub：  https://github.com/KellenHu
 * CSDN：    http://my.csdn.net/westdeco
 */
public interface LoadingView {
    void showLoading();
    void showContent();
    void showError(int messageId, View.OnClickListener listener);
    Context getContext();
}

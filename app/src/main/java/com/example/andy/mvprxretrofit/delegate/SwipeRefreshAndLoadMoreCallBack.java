package com.example.andy.mvprxretrofit.delegate;

/**
 * <Pre>
 * 下拉刷新与加载更多接口，用于presenter与view解耦
 * </Pre>
 *
 * Created by WestDeco on 2017/4/5.
 * 签名： 用风雅的态度看世界，用痞子的风格过日子
 * E-mail：  717616019@qq.com
 * GitHub：  https://github.com/KellenHu
 * CSDN：    http://my.csdn.net/westdeco
 */
public interface SwipeRefreshAndLoadMoreCallBack {
    /**
     * 下拉刷新
     */
    void refresh();

    /**
     * 加载更多
     */
    void loadMore();
}

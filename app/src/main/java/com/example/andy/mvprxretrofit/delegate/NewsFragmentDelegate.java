package com.example.andy.mvprxretrofit.delegate;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.andy.mvprxretrofit.R;
import com.example.andy.mvprxretrofit.adapter.NewsListAdapter;
import com.example.andy.mvprxretrofit.mvp_base_frame.view.AppDelegate;
import com.example.andy.mvprxretrofit.view.LoadingView;

/**
 * <pre>
 *
 *
 *
 *
 *      <pre/>
 *
 * 文件名： MvpRxRetrofit
 * Created by WestDeco on 2017/4/5.
 * 签名： 用风雅的态度看世界，用痞子的风格过日子
 * E-mail：  717616019@qq.com
 * GitHub：  https://github.com/KellenHu
 * CSDN：    http://my.csdn.net/westdeco
 */

public class NewsFragmentDelegate extends BaseRecyclerViewDelegate{

    //RecyclerView的视图管理Manager
    private LinearLayoutManager mLayoutManager;

    @Override
    void initRecyclerView(){
        mLayoutManager = new LinearLayoutManager(this.getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerview.setLayoutManager(mLayoutManager);
    }

    /**
     * 为RecyclerViews设置Adapter
     */
    public void setListAdapter(RecyclerView.Adapter adapter){
        recyclerview.setAdapter(adapter);
    }


    @NonNull
    @Override
    boolean setFloatingActionMenuVisible() {
        return false;
    }

    @Override
    public void registerSwipeRefreshCallBack(final SwipeRefreshAndLoadMoreCallBack callBack) {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                callBack.refresh();
            }
        });
    }

    @Override
    public void registerLoadMoreCallBack(final SwipeRefreshAndLoadMoreCallBack callBack, final RecyclerView.Adapter adapter) {
        //由于override过来的Afapter是基类的Adapter所以强转一下
        final NewsListAdapter newsListAdapter = (NewsListAdapter)adapter;

        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {

            private int lastVisibleItem;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == newsListAdapter.getItemCount()
                        && newsListAdapter.isShowFooter()) {
                    //加载更多
                    callBack.loadMore();
                }
            }
        });

    }


    @Override
    public Context getContext() {
        return getActivity();
    }
}

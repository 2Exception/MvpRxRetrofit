package com.example.andy.mvprxretrofit.delegate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.andy.mvprxretrofit.R;
import com.example.andy.mvprxretrofit.adapter.NewsListAdapter;
import com.example.andy.mvprxretrofit.common.Constants;
import com.example.andy.mvprxretrofit.mvp_base_frame.view.AppDelegate;
import com.example.andy.mvprxretrofit.view.LoadingView;
import com.example.andy.mvprxretrofit.widget.ProgressLayout;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;

/**
 * <pre>
 *      通用的带上拉刷新，下拉加载的RecyclerView的列表结构代理
 *      <pre/>
 *
 * 文件名： MvpRxRetrofit
 * Created by WestDeco on 2017/4/5.
 * 签名： 用风雅的态度看世界，用痞子的风格过日子
 * E-mail：  717616019@qq.com
 * GitHub：  https://github.com/KellenHu
 * CSDN：    http://my.csdn.net/westdeco
 */

public abstract class BaseRecyclerViewDelegate extends AppDelegate implements LoadingView {


    @Bind(R.id.recyclerview)
    RecyclerView recyclerview;
    @Bind(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @Bind(R.id.progress_layout)
    ProgressLayout progressLayout;
    @Bind(R.id.floating_action_button1)
    FloatingActionButton floatingActionButton1;
    @Bind(R.id.floating_action_button2)
    FloatingActionButton floatingActionButton2;
    @Bind(R.id.floating_action_button3)
    FloatingActionButton floatingActionButton3;
    @Bind(R.id.floating_action_button4)
    FloatingActionButton floatingActionButton4;
    @Bind(R.id.floating_action_menu)
    FloatingActionMenu floatingActionMenu;


    protected List<FloatingActionButton> mFloatingActionButtons;//悬浮菜单选项数组


    @Override
    public int getRootLayoutId(){
        return R.layout.layout_base_recyclerview;
    }

    /**
     * 初始化RecyclerView，设置LayoutManager，Decoration之类的
     */
    abstract void initRecyclerView();

    /**
     * 设置悬浮菜单是否显示，必须重些
     */
    @NonNull
    abstract boolean setFloatingActionMenuVisible();

    @Override
    public void initWidget() {
        initSwipeRefreshLayout();
        initRecyclerView();
        initFloatingActionMenu();
    }

    /**
     * 初始化悬浮菜单
     */
    private void initFloatingActionMenu() {
        floatingActionMenu.setVisibility(setFloatingActionMenuVisible() ? View.VISIBLE : View.GONE);
        floatingActionMenu.setClosedOnTouchOutside(true);
        mFloatingActionButtons = new ArrayList<>();
        mFloatingActionButtons.add(floatingActionButton1);
        mFloatingActionButtons.add(floatingActionButton2);
        mFloatingActionButtons.add(floatingActionButton3);
        mFloatingActionButtons.add(floatingActionButton4);
    }


    /**
     * 初始化下拉刷新控件
     */
    public void initSwipeRefreshLayout(){
        swipeRefreshLayout.setColorSchemeColors(Constants.colors);
    }


    /**
     * 下拉刷新的注册响应回掉方法，必须重写
     * @param callBack
     */
    abstract public void registerSwipeRefreshCallBack(final SwipeRefreshAndLoadMoreCallBack callBack);

    /**
     * 加载更多的注册响应回调，必须重写
     */
    abstract public void registerLoadMoreCallBack(final SwipeRefreshAndLoadMoreCallBack callBack,final RecyclerView.Adapter adapter);

    @Override
    public void showLoading() {
        if (!progressLayout.isLoading())
            progressLayout.showLoading();
    }

    @Override
    public void showContent() {
        //将当前SwiprefreshLayout设置为false（刷新完成），否则根据recyclerview的下拉到底自动加载判断就不允许加载数据
        swipeRefreshLayout.setRefreshing(false);
        if (!progressLayout.isContent())
            progressLayout.showContent();
    }

    @Override
    public void showError(int messageId, View.OnClickListener listener) {
        //将当前SwiprefreshLayout设置为false（刷新完成），否则根据recyclerview的下拉到底自动加载判断就不允许加载数据
        swipeRefreshLayout.setRefreshing(false);
        if (!progressLayout.isError())
            progressLayout.showError(messageId,listener);
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

}

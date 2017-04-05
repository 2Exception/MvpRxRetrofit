package com.example.andy.mvprxretrofit.view.newspage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.andy.mvprxretrofit.R;
import com.example.andy.mvprxretrofit.adapter.NewsListAdapter;
import com.example.andy.mvprxretrofit.delegate.NewsFragmentDelegate;
import com.example.andy.mvprxretrofit.delegate.SwipeRefreshAndLoadMoreCallBack;
import com.example.andy.mvprxretrofit.model.OnNetResponseListener;
import com.example.andy.mvprxretrofit.model.news.NewsBody;
import com.example.andy.mvprxretrofit.model.news.NewsModelImpl;
import com.example.andy.mvprxretrofit.mvp_base_frame.presenter.FragmentPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *
 *      新闻列表Fragment
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

public class NewsFragment extends FragmentPresenter<NewsFragmentDelegate> implements SwipeRefreshAndLoadMoreCallBack{


    //NewsModel
    private NewsModelImpl newsModel;

    //分页
    private int mPageNum = 1;
    //适配器
    private NewsListAdapter mAdapter;
    //新闻数据列表
    private List<NewsBody> mNews = new ArrayList<>();


    public static NewsFragment newInstance() {
        Bundle args = new Bundle();
        NewsFragment fragment = new NewsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initData() {
        newsModel = new NewsModelImpl();
        mAdapter = new NewsListAdapter(getActivity(),mNews);
        viewDelegate.setListAdapter(mAdapter);//设置adapter
        viewDelegate.registerLoadMoreCallBack(this,mAdapter);//注册加载更多
        viewDelegate.registerSwipeRefreshCallBack(this);//注册下拉刷新

        mAdapter.setOnItemClickListener(new NewsListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                NewsBody item = mNews.get(position);
                Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
                if((item.imageurls != null && item.imageurls.size() > 0)) {
                    intent.putExtra(NewsDetailActivity.ARG_NEWS_PIC, item.imageurls.get(0).url);
                }
                intent.putExtra(NewsDetailActivity.ARG_NEWS_URL, item.link);
                intent.putExtra(NewsDetailActivity.ARG_NEWS_TITLE, item.title);
                startActivity(intent);
            }
        });

        //第一次加载一次数据
        netNews(true);
    }


    /**
     * 网络请求，新闻列表
     * @param isRefresh 是否是刷新
     */
    private void netNews(final boolean isRefresh){
        if(isRefresh){
            mPageNum = 1;
        }else {
            mPageNum++;
        }

        newsModel.onNetLoad(new OnNetResponseListener<List<NewsBody>>() {
            @Override
            public void onStart() {
                //该页面不需要正在加载的全屏progress,列表形式页面只需要下拉刷新效果和加载更多效果
//                viewDelegate.showLoading();
            }

            @Override
            public void onFinish() {
//                viewDelegate.showContent();//由于用的是retrofit原始请求方式，所以没有onFinish回调方法，将onFinish的操作放在O你Success中一起完成
            }

            @Override
            public void onSuccess(List<NewsBody> data) {
                viewDelegate.showContent();
                if(isRefresh) {
                    if(!mNews.isEmpty()){
                        mNews.clear();
                    }
                }
                mNews.addAll(data);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                viewDelegate.showError(R.string.loading, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        netNews(true);
                    }
                });
            }
        },String.valueOf(mPageNum), NewsModelImpl.CHANNEL_ID, NewsModelImpl.CHANNEL_NAME);


    }

    @Override
    protected Class<NewsFragmentDelegate> getDelegateClass() {
        return NewsFragmentDelegate.class;
    }

    @Override
    public void refresh() {
        netNews(true);
    }

    @Override
    public void loadMore() {
        netNews(false);
    }
}

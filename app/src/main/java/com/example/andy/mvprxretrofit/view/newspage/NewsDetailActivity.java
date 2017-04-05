package com.example.andy.mvprxretrofit.view.newspage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.andy.mvprxretrofit.delegate.NewsDetailDelegate;
import com.example.andy.mvprxretrofit.delegate.NewsFragmentDelegate;
import com.example.andy.mvprxretrofit.mvp_base_frame.presenter.ActivityPresenter;
import com.example.andy.mvprxretrofit.utils.ToolsUtil;

import me.imid.swipebacklayout.lib.SwipeBackLayout;

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

public class NewsDetailActivity extends ActivityPresenter<NewsDetailDelegate>{

    /**
     * 需要点击列表传递过来的新闻详情链接
     */
    public static final String ARG_NEWS_URL = "arg_news_url";
    /**
     * 需要传递过来的新闻图片
     */
    public static final String ARG_NEWS_PIC = "arg_news_pic";
    /**
     * 需要传递过来的新闻标题
     */
    public static final String ARG_NEWS_TITLE = "arg_news_title";
    private String mUrl = "";
    private String mPic = "";
    private String mTitle = "";

    private SwipeBackLayout mSwipeBackLayout;

    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        if(getIntent().getExtras() != null){
            mUrl = getIntent().getStringExtra(ARG_NEWS_URL);
            mPic = getIntent().getStringExtra(ARG_NEWS_PIC);
            mTitle = getIntent().getStringExtra(ARG_NEWS_TITLE);
        }else {
            finish();
            viewDelegate.showSnackbar("参数有误");
        }
    }

    protected void initView() {
        setSupportActionBar(viewDelegate.getToolBar());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewDelegate.getToolBar().setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mSwipeBackLayout = getSwipeBackLayout();
        mSwipeBackLayout.setEdgeSize(ToolsUtil.getWidthInPx(this));
        mSwipeBackLayout.setEdgeTrackingEnabled(SwipeBackLayout.EDGE_LEFT);
        viewDelegate.setCollapsingToolbarLayoutTitle(mTitle);
        viewDelegate.setImageWithURL(mPic);
        viewDelegate.loadNewsDetail(mUrl);
    }

    @Override
    protected Class<NewsDetailDelegate> getDelegateClass() {
        return NewsDetailDelegate.class;
    }
}

package com.example.andy.mvprxretrofit.delegate;

import android.graphics.Bitmap;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.andy.mvprxretrofit.R;
import com.example.andy.mvprxretrofit.mvp_base_frame.view.AppDelegate;
import com.example.andy.mvprxretrofit.utils.GlideUtil;
import com.rey.material.widget.ProgressView;

import butterknife.Bind;

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

public class NewsDetailDelegate extends AppDelegate {



    @Bind(R.id.iv_detail)
    ImageView ivDetail;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @Bind(R.id.progress)
    ProgressView progress;
    @Bind(R.id.webview)
    WebView webview;

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_news_detail;
    }

    @Override
    public void initWidget() {
        initWebView();
    }

    @Override
    public Toolbar getToolBar() {
        return toolbar;
    }

    /**
     * 初始化webview
     */
    private void initWebView() {
        WebSettings ws = webview.getSettings();
        ws.setJavaScriptEnabled(true);
        ws.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);  //设置 缓存模式(true);
        ws.setAppCacheEnabled(true);
        ws.setSupportZoom(false);
        ws.setUseWideViewPort(true);// 可任意比例缩放
        ws.setJavaScriptCanOpenWindowsAutomatically(true);//js支持
        ws.setDomStorageEnabled(true);

        webview.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progress.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progress.setVisibility(View.GONE);
            }

        });
    }

    public void setCollapsingToolbarLayoutTitle(String title){
        collapsingToolbar.setTitle(title);
    }

    public void setImageWithURL(String url){
        GlideUtil.loadImage(getActivity(), url,ivDetail);
    }

    public void loadNewsDetail(String url){
        webview.loadUrl(url);
    }

}

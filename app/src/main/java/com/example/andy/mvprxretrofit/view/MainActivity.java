package com.example.andy.mvprxretrofit.view;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;

import com.example.andy.mvprxretrofit.delegate.MainActivityDelegate;
import com.example.andy.mvprxretrofit.mvp_base_frame.presenter.ActivityPresenter;

public class MainActivity extends ActivityPresenter<MainActivityDelegate> implements View.OnClickListener{
    private static final String TAG = MainActivity.class.getSimpleName();



    @Override
    protected void initData(@Nullable Bundle savedInstanceState) {
        viewDelegate.setmFragmentManager(getSupportFragmentManager());
        viewDelegate.setFragmentProgress(savedInstanceState);
    }

    @Override
    protected void initView() {

    }


    @Override
    protected Class<MainActivityDelegate> getDelegateClass() {
        return MainActivityDelegate.class;
    }

    @Override
    public void onClick(View v){}

    @Override
    protected void bindEventListener() {
        super.bindEventListener();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        viewDelegate.onSaveInstanceState(outState);
    }
}

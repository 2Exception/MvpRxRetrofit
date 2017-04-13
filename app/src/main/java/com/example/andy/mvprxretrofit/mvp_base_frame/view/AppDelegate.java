package com.example.andy.mvprxretrofit.mvp_base_frame.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.andy.mvprxretrofit.utils.ToastUtils;

import butterknife.ButterKnife;

/**
 * 文件名： MvpRxRetrofit
 * Created by WestDeco on 2017/3/29.
 * 签名： 用风雅的态度看世界，用痞子的风格过日子
 * E-mail：  717616019@qq.com
 * GitHub：  https://github.com/KellenHu
 * CSDN：    http://my.csdn.net/westdeco
 *
 *
 * 视图层的基类
 */

public  abstract class AppDelegate implements IDelegate{

    protected final SparseArray<View> mViews = new SparseArray<>();

    protected View rootView;

    public abstract int getRootLayoutId();

    @Override
    public void create(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int rootLayoutId = getRootLayoutId();
        rootView = inflater.inflate(rootLayoutId,container,false);
        ButterKnife.bind(this,rootView);
    }

    @Override
    public int getOptionsMenuId() {
        return 0;
    }

    @Override
    public Toolbar getToolBar() {
        return null;
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }


    public <T extends View> T bindView(int id) {
        T view = (T) mViews.get(id);
        if (view == null) {
            view = (T) rootView.findViewById(id);
            mViews.put(id, view);
        }
        return view;
    }

    public <T extends View> T get(int id) {
        return (T) bindView(id);
    }

    /**
     * 如果 继承了代理类的View（Activity，Fragment）实现了OnClickListener，则必须在该view的OnBindViewevent
     * 调用该方法
     * @param listener
     * @param ids
     */
    public void setOnClickListener(View.OnClickListener listener, int... ids) {
        if (ids == null) {
            return;
        }
        for (int id : ids) {
            get(id).setOnClickListener(listener);
        }
    }

    public void showToast(String msg) {
        ToastUtils.showShort(msg);
//        Toast.makeText(rootView.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
    public void showSnackbar(String msg) {
        Snackbar.make(rootView, msg, Snackbar.LENGTH_LONG)
                .show();
    }

    public <T extends Activity> T getActivity() {
        return (T) rootView.getContext();
    }

}

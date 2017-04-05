package com.example.andy.mvprxretrofit.delegate;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.andy.mvprxretrofit.R;
import com.example.andy.mvprxretrofit.common.Constants;
import com.example.andy.mvprxretrofit.mvp_base_frame.view.AppDelegate;
import com.example.andy.mvprxretrofit.view.homepage.HomeFragment;
import com.example.andy.mvprxretrofit.view.newspage.NewsFragment;

import java.util.ArrayList;
import java.util.Arrays;

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
 * Created by WestDeco on 2017/4/1.
 * 签名： 用风雅的态度看世界，用痞子的风格过日子
 * E-mail：  717616019@qq.com
 * GitHub：  https://github.com/KellenHu
 * CSDN：    http://my.csdn.net/westdeco
 */

public class MainActivityDelegate extends AppDelegate {


    @Bind(R.id.fragment_container)
    FrameLayout fragmentContainer;
    @Bind(R.id.foot_bar_home)
    RadioButton footBarHome;
    @Bind(R.id.foot_bar_im)
    RadioButton footBarIm;
    @Bind(R.id.foot_bar_interest)
    RadioButton footBarInterest;
    @Bind(R.id.main_footbar_user)
    RadioButton mainFootbarUser;
    @Bind(R.id.group)
    RadioGroup group;
    @Bind(R.id.tvShopCartNum)
    TextView tvShopCartNum;
    @Bind(R.id.textUnreadLabel)
    TextView textUnreadLabel;
    @Bind(R.id.layoutFooter)
    RelativeLayout layoutFooter;


    /**
     * fragment切换相关
     */
    private FragmentManager mFragmentManager;
    private ArrayList<String> fragmentTags;

    private static final String CURR_INDEX = "currIndex";
    private static int currIndex = 0;//当前窗口

    @Override
    public int getRootLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initWidget() {
    }

    public void setmFragmentManager(FragmentManager fragmentManager){
        mFragmentManager = fragmentManager;
    }

    public void setFragmentProgress(@NonNull Bundle savedInstanceState){
        fragmentTags = new ArrayList<>(Arrays.asList("HomeFragment", "NewsFragment", "ShopCartFragment", "PersonalFragment"));
        //当前的页面
        currIndex = 0;
        if(savedInstanceState != null) {
            currIndex = savedInstanceState.getInt(CURR_INDEX);
            hideSavedFragment();
        }


        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home: currIndex = 0;
                        showFragment();
                        break;
                    case R.id.foot_bar_im: currIndex = 1;
                        showFragment();
                        break;
                    case R.id.foot_bar_interest: currIndex = 2;
                        showFragment();
                        break;
                    case R.id.main_footbar_user: currIndex = 3;
                        showFragment();
                        if (!Constants.LOGIN){
//                            UIHelper.showLogin(MainActivity.this,111);//跳转登录按钮
                        }
                        break;
                    default: break;
                }
            }
        });
        showFragment();
    }


    private void showFragment(){
        if (currIndex == 1) {
//            UIHelper.showLogin(MainActivity.this);
        }
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if(fragment == null){
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < fragmentTags.size(); i++) {
            Fragment f = mFragmentManager.findFragmentByTag(fragmentTags.get(i));
            if(f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()){
            fragmentTransaction.show(fragment);
        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        mFragmentManager.executePendingTransactions();
    }

    /**
     * 根据currentIndex初始化Fragment
     */
    private Fragment instantFragment(int currIndex){
        switch (currIndex){
            case 0: return HomeFragment.newInstance();
            case 1: return NewsFragment.newInstance();
//            case 2: return ShopCartFragment.newInstance();
//            case 3: return PersonalFragment.newInstance();
            default: return null;
        }
    }


    /**
     * 隐藏所有的Fragment
     */
    private void hideSavedFragment() {
        Fragment fragment = mFragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if(fragment != null){
            mFragmentManager.beginTransaction().hide(fragment).commit();
        }
    }
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(CURR_INDEX, currIndex);
    }
}

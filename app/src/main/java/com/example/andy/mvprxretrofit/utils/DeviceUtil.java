package com.example.andy.mvprxretrofit.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 文件名： MvpRxRetrofit
 * Created by WestDeco on 2017/3/29.
 * 签名： 用风雅的态度看世界，用痞子的风格过日子
 * E-mail：  717616019@qq.com
 * GitHub：  https://github.com/KellenHu
 * CSDN：    http://my.csdn.net/westdeco
 */
public class DeviceUtil {
    /**
     * 获取当前应用的版本号
     */
    public static String getVersionName(Context context){
        PackageManager packageManager = context.getPackageManager();
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "0.0.0";
        }
        String versionName = packageInfo.versionName;
        return versionName;
    }
}

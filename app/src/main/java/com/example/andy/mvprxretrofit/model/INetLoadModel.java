package com.example.andy.mvprxretrofit.model;

/**
 * <pre>
 *
 *      数据请求model基类,参数类型为String
 *
 *
 *      <pre/>
 *
 * 文件名： MvpRxRetrofit
 * Created by WestDeco on 2017/3/31.
 * 签名： 用风雅的态度看世界，用痞子的风格过日子
 * E-mail：  717616019@qq.com
 * GitHub：  https://github.com/KellenHu
 * CSDN：    http://my.csdn.net/westdeco
 */

public interface INetLoadModel<T> {
    /**
     *  网络请求接口
     * @param onNetResponseListener 网络响应
     * @param params 不定参数，数目不定
     */
    //TODO 写完Listner回调之后，一定不要忘了，参数在Listener后面，别忘了加参数
    void onNetLoad(OnNetResponseListener<T> onNetResponseListener,String... params);
}

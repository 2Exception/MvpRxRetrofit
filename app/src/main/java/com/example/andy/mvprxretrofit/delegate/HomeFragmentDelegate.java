package com.example.andy.mvprxretrofit.delegate;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.andy.mvprxretrofit.R;
import com.example.andy.mvprxretrofit.model.weather.ShowWeatherBody;
import com.example.andy.mvprxretrofit.mvp_base_frame.view.AppDelegate;
import com.example.andy.mvprxretrofit.utils.GlideUtil;
import com.example.andy.mvprxretrofit.view.LoadingView;
import com.example.andy.mvprxretrofit.widget.ProgressLayout;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.ViewHolder;

import butterknife.Bind;

/**
 * <pre>
 *
 *          首页Fragment的代理，实现了lodingView接口
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

public class HomeFragmentDelegate extends AppDelegate implements LoadingView{


    private ImageView iv_weather;
    private TextView tv_weather, tv_aqi, tv_sd, tv_wind_direction, tv_wind_power, tv_temperature_time,
            tv_temperature;

    private LinearLayout ll_dialog_holder;


    @Bind(R.id.et_location)
    EditText etLocation;
    @Bind(R.id.bt_weather)
    Button btWeather;
    @Bind(R.id.progress_layout)
    ProgressLayout progressLayout;

    @Override
    public int getRootLayoutId() {
        return R.layout.fragment_home;
    }

    /**
     * 获取输入的地名
     * @return 地名
     */
    public String getInputLocation(){
        return etLocation.getText().toString().trim();
    }

    /**
     * 显示当前天气弹窗
     */
    public void showNowWeatherDialog(ShowWeatherBody weather) {
        Holder holder = null;
        if (ll_dialog_holder == null){
            ll_dialog_holder = (LinearLayout) getActivity().getLayoutInflater().inflate(R.layout.dialog_weather, null);
            holder = new ViewHolder(ll_dialog_holder);
            findHolderChildView(holder);
            ll_dialog_holder.setTag(holder);
        }else {
            holder = (Holder) ll_dialog_holder.getTag();
        }
        GlideUtil.loadImage(getActivity(), weather.now.weather_pic, iv_weather);
        tv_weather.setText(weather.now.weather);
        tv_temperature.setText(weather.now.temperature + "℃");
        tv_temperature_time.setText(weather.now.temperature_time);
        tv_aqi.setText(String.format(getActivity().getResources().getString(R.string.weather_dialog_aqi),
                weather.now.aqi));
        tv_sd.setText(String.format(getActivity().getResources().getString(R.string.weather_dialog_sd),
                weather.now.sd));
        tv_wind_direction.setText(String.format(getActivity().getResources().getString(R.string.weather_dialog_wind_direction),
                weather.now.wind_direction));
        tv_wind_power.setText(String.format(getActivity().getResources().getString(R.string.weather_dialog_wind_power),
                weather.now.wind_power));
        showOnlyContentDialog(holder, Gravity.BOTTOM, false);
    }

    /**
     * 仅显示内容的dialog
     *
     * @param holder
     * @param gravity         显示位置（居中，底部，顶部）
     * @param expanded        是否支持展开（有列表时适用）
     */
    private void showOnlyContentDialog(Holder holder, int gravity,
                                       boolean expanded) {
        final DialogPlus dialog = DialogPlus.newDialog(getActivity())
                .setContentHolder(holder)
                .setGravity(gravity)
                .setExpanded(expanded)
                .setCancelable(true)
                .create();
        dialog.show();
    }

    /**
     * 关闭软键盘
     */
    public void closeSoftInput(){
        InputMethodManager inputMethodManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(etLocation.getWindowToken(), 0);
    }


    /**
     * 查找弹窗的holder的子控件
     *
     * @param holder
     */
    private void findHolderChildView(Holder holder) {
        iv_weather = (ImageView) holder.getInflatedView().findViewById(R.id.iv_weather);
        tv_weather = (TextView) holder.getInflatedView().findViewById(R.id.tv_weather);
        tv_temperature = (TextView) holder.getInflatedView().findViewById(R.id.tv_temperature);
        tv_temperature_time = (TextView) holder.getInflatedView().findViewById(R.id.tv_temperature_time);
        tv_aqi = (TextView) holder.getInflatedView().findViewById(R.id.tv_aqi);
        tv_sd = (TextView) holder.getInflatedView().findViewById(R.id.tv_sd);
        tv_wind_direction = (TextView) holder.getInflatedView().findViewById(R.id.tv_wind_direction);
        tv_wind_power = (TextView) holder.getInflatedView().findViewById(R.id.tv_wind_power);
    }

    @Override
    public void showLoading() {
        progressLayout.showLoading();
    }

    @Override
    public void showContent() {
        if (!progressLayout.isContent())
            progressLayout.showContent();
    }

    @Override
    public void showError(int messageId, View.OnClickListener listener) {
        progressLayout.showError(messageId,listener);
    }

    @Override
    public Context getContext(){
        return getActivity();
    }

    @Override
    public void initWidget() {

    }
}

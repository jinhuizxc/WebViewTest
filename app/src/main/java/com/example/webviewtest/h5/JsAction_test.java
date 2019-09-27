package com.example.webviewtest.h5;

import android.app.Activity;
import android.webkit.JavascriptInterface;

import com.blankj.utilcode.util.ToastUtils;
import com.example.webviewtest.h5.H5TestActivity;

public class JsAction_test {

    private Activity context;


    public JsAction_test(Activity context) {
        this.context = context;
    }

    /**
     * 获取地图位置
     *
     * [INFO:CONSOLE(52)] "Uncaught ReferenceError: getlocation is not defined",
     *
     * @return
     */
    @JavascriptInterface
    public String getLocation(String param) {
        ToastUtils.showShort(param.toString());
        final String[] locationInfo = new String[1];
        H5TestActivity activity = (H5TestActivity) context;
        // 将获取的位置信息回调回来;
//        activity.getLocation();
//        activity.setLocationInfoListener(new TBSActivity.LocationInfoListener() {
//            @Override
//            public void onLocationInfo(LocationBean locationBean) {
//                locationInfo[0] = GsonUtils.toJson(locationBean);
//                Logger.d("获取地图位置 ->locationInfo = " + locationInfo[0]);
//            }
//        });
        return locationInfo[0];
    }

    //在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
    @JavascriptInterface
    public void showInfoFromJs(String name) {
        ToastUtils.showShort(name);
    }




}

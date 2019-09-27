package com.example.webviewtest.test;

import android.app.Activity;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

class JsObject{

    private Activity activity;

    public JsObject(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void funAndroid() {
        Toast.makeText(activity, "调用android本地方法funAndroid!", Toast.LENGTH_LONG).show();
    }

}

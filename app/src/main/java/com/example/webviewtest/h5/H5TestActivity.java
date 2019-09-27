package com.example.webviewtest.h5;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.example.webviewtest.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * android 调用js
 * js 调用android
 * 有参无参方法
 * <p>
 * window.action.getlocation();
 * <p>
 * android执行js回调函数
 * https://blog.csdn.net/u598975767/article/details/86542900
 */
public class H5TestActivity extends AppCompatActivity  {


    @BindView(R.id.input_et)
    EditText inputEt;
    @BindView(R.id.webView)
    WebView tbsWebView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5_test);
        ButterKnife.bind(this);

        setTitle("h5测试");
        setWebView();
    }



    /* private void initWebView() {
        tbsWebView.loadUrl("file:///android_asset/test_h5.html");
        WebSettings mWebSettings = tbsWebView.getSettings();
        //启用JavaScript。
        mWebSettings.setJavaScriptEnabled(true);
        mWebSettings.setUseWideViewPort(true);
        mWebSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }*/

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView() {

        WebSettings webSettings = tbsWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // 载入 js
        tbsWebView.loadData("", "text/html", null);
        tbsWebView.loadUrl("file:///android_asset/test_h5.html");

        //添加客户端支持
        tbsWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // 当打开新链接时，使用当前的 WebView，不会使用系统其他浏览器
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                // 在这里执行你想调用的js函数
                // android 调用js代码
                // mWebView.loadUrl("javascript:funFromjs()");
                // js调用android代码,设置本地调用对象及其接口
//                tbsWebView.addJavascriptInterface(new JsAction_test(H5TestActivity.this), "action");
//                loadJs(url);

                // 在这里执行你想调用的js函数
                //设置本地调用对象及其接口
                tbsWebView.addJavascriptInterface(new JsAction_test(H5TestActivity.this), "action");
            }

        });

        tbsWebView.setWebChromeClient(new WebChromeClient() {

        });

        // 建立与后台的js调用
        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
        tbsWebView.addJavascriptInterface(new JsAction_test(this), "action");//AndroidtoJS类对象映射到js的test对象

        //点击调用js中方法
        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                tbsWebView.loadUrl("javascript:funFromjs()");
                Toast.makeText(H5TestActivity.this, "调用javascript:funFromjs()", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * 4.4以上可用 evaluateJavascript 效率高
     */
    private void loadJs(String jsString) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            tbsWebView.evaluateJavascript(jsString, null);
        } else {
            tbsWebView.loadUrl(jsString);
        }
    }


    //在java中调用js代码
    public void sendInfoToJs(View view) {
        String msg = inputEt.getText().toString();
        //调用js中的函数：showInfoFromJava(msg)
        tbsWebView.loadUrl("javascript:showInfoFromJava('" + msg + "')");
    }


}

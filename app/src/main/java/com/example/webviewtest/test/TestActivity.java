package com.example.webviewtest.test;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.webviewtest.R;

/**
 * Android JS桥交互（"Uncaught ReferenceError: xxx is not defined or xxx has no method"）
 * https://blog.csdn.net/u012301841/article/details/49907779
 */
public class TestActivity extends AppCompatActivity {

    TextView mTextview;
    Button mBtn1;
    WebView mWebView;
    Context mContext;

    @SuppressLint("SetJavaScriptEnabled")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        // 初始化
        initViews();
        // 设置编码
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        // 支持js
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        //设置背景颜色 透明
        mWebView.setBackgroundColor(Color.argb(0, 0, 0, 0));
        mWebView.setWebViewClient(new WebViewClientDemo());
        // 载入 js
        mWebView.loadData("", "text/html", null);
        mWebView.loadUrl("file:///android_asset/test.html");
        
        //点击调用js中方法
        mBtn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mWebView.loadUrl("javascript:funFromjs()");
                Toast.makeText(mContext, "调用javascript:funFromjs()", Toast.LENGTH_LONG).show();
            }
        });

    }
    
    class WebViewClientDemo extends WebViewClient {
    	
		@Override
    	public boolean shouldOverrideUrlLoading(WebView view, String url) {
    		 System.out.println("url:" + url);
    		 // 当打开新链接时，使用当前的 WebView，不会使用系统其他浏览器  
    		 view.loadUrl(url);
    		 return true;
    	}
    	
    	@Override
    	public void onPageFinished(WebView view, String url) {
    		super.onPageFinished(view, url);
    		// 在这里执行你想调用的js函数  
    		//设置本地调用对象及其接口
            mWebView.addJavascriptInterface(new JsObject(TestActivity.this), "android");
    	}
    	
    }
    

    
    public void initViews() {
        mBtn1 = (Button) findViewById(R.id.btn_1);
        mTextview = (TextView) findViewById(R.id.tv_view);
        mWebView = (WebView) findViewById(R.id.wv_view);

        mContext = getApplicationContext();
    }
    

}

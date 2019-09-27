package com.example.webviewtest.jsbridge;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;

import com.example.webviewtest.R;
import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;

/**
 * Java 和 js互通框架
 * https://github.com/Tamicer/JsBridge-Android
 */
public class JsBridgeActivity extends Activity implements OnClickListener {

	private final String TAG = "MainActivity";

	BridgeWebView webView;

	Button button;

	int RESULT_CODE = 0;

	ValueCallback<Uri> mUploadMessage;

	ValueCallback<Uri[]> mUploadMessageArray;

    static class Location {
        String address;
    }

    static class User {
        String name;
        Location location;
        String testStr;
    }

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_js_bridge);

        webView = (BridgeWebView) findViewById(R.id.webView);

		button = (Button) findViewById(R.id.button);

		button.setOnClickListener(this);

		webView.setDefaultHandler(new DefaultHandler());

		webView.setWebChromeClient(new WebChromeClient() {

			@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType, String capture) {
				this.openFileChooser(uploadMsg);
			}

			@SuppressWarnings("unused")
			public void openFileChooser(ValueCallback<Uri> uploadMsg, String AcceptType) {
				this.openFileChooser(uploadMsg);
			}

			public void openFileChooser(ValueCallback<Uri> uploadMsg) {
				mUploadMessage = uploadMsg;
				pickFile();
			}

			@Override
			public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
				mUploadMessageArray = filePathCallback;
				pickFile();
				return true;
			}
		});

		//加载本地网页
		webView.loadUrl("file:///android_asset/js_bridge.html");

		//必须和js同名函数，注册具体执行函数，类似java实现类。
		webView.registerHandler("submitFromWeb", new BridgeHandler() {

			@Override
			public void handler(String data, CallBackFunction function) {
				Log.i(TAG, "handler = submitFromWeb, data from web = " + data);
                function.onCallBack("submitFromWeb exe, response data 中文 from Java");
			}

		});

        User user = new User();
        Location location = new Location();
        location.address = "SDU";
        user.location = location;
        user.name = "大头鬼";

        webView.callHandler("functionInJs", new Gson().toJson(user), new CallBackFunction() {
            @Override
            public void onCallBack(String data) {

            }
        });

        webView.send("hello");

	}

	public void pickFile() {
		Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
		chooserIntent.setType("image/*");
		startActivityForResult(chooserIntent, RESULT_CODE);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if (requestCode == RESULT_CODE) {
			if (null == mUploadMessage && null == mUploadMessageArray){
				return;
			}
			if(null!= mUploadMessage && null == mUploadMessageArray){
				Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
				mUploadMessage.onReceiveValue(result);
				mUploadMessage = null;
			}

			if(null == mUploadMessage && null != mUploadMessageArray){
				Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
				mUploadMessageArray.onReceiveValue(new Uri[]{result});
				mUploadMessageArray = null;
			}

		}
	}

	@Override
	public void onClick(View v) {
		if (button.equals(v)) {
            webView.callHandler("functionInJs", "data from Java", new CallBackFunction() {

				@Override
				public void onCallBack(String data) {
					// TODO Auto-generated method stub
					Log.i(TAG, "reponse data from js " + data);
				}

			});
		}

	}

}

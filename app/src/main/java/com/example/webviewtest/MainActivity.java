package com.example.webviewtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.webviewtest.h5.H5TestActivity;
import com.example.webviewtest.jsbridge.JsBridgeActivity;
import com.example.webviewtest.test.TestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.btn)
    Button btn;
    @BindView(R.id.btn_test)
    Button btnTest;
    @BindView(R.id.btn_js_bridge)
    Button btnJsBridge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn, R.id.btn_test, R.id.btn_js_bridge})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn:
                ActivityUtils.startActivity(H5TestActivity.class);
                break;
            case R.id.btn_test:
                ActivityUtils.startActivity(TestActivity.class);
                break;
            case R.id.btn_js_bridge:
                ActivityUtils.startActivity(JsBridgeActivity.class);
                break;
        }
    }
}

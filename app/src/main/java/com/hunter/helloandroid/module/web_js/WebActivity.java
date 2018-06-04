package com.hunter.helloandroid.module.web_js;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hunter.helloandroid.R;
import com.hunter.helloandroid.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * ================================================================
 * <p>
 * 版    权： 上海田韵物联网科技有限公司(c)2018
 * <p>
 * 作    者： 黄自航
 * <p>
 * 版    本： V1.0
 * <p>
 * 创建日期： 2018/6/4 15:50
 * <p>
 * 描    述：
 * <p>
 * <p>
 * 修订历史：
 * <p>
 * ================================================================
 */
public class WebActivity extends AppCompatActivity {

    @BindView(R.id.wv_map)
    WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_js);

        ButterKnife.bind(this);

        loadWebView();

    }

    /**
     * 加载WebView
     */
    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void loadWebView() {

//        showLoadingDialog();

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.addJavascriptInterface(new WepAppInterface(this), "Android");

        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.loadUrl("file:///android_asset/web/index.html");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                mWebView.setVisibility(View.VISIBLE);
//                dismissLoadingDialog();
            }

        });
    }


    private class WepAppInterface {
        private Context mContext;

        public WepAppInterface(Context context) {
            this.mContext = context;
        }

        @JavascriptInterface
        public String onClickCity(String city) {

            ToastUtil.showPrompt("点击了:" + city);

            return "onPageLoad";
        }

    }


}

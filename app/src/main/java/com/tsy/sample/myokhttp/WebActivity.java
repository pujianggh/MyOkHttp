package com.tsy.sample.myokhttp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 通用web加载页面
 * @author pujiang
 * @date 2017-8-29 17:34
 * @mail 515210530@qq.com
 * @Description:
 */
public class WebActivity extends Activity {
    private static final String APP_CACAHE_DIRNAME = "/fnd_app/webcache";
    private Context mContext;// 上下文
    private WebView mWebvContent;
    private String mUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_web);
        mUrl = getIntent().getStringExtra("url");
        initView();
        initWebView();
    }

    /**
     * 组件初始化
     */
    private void initView() {
        mWebvContent = (WebView) findViewById(R.id.webv_content);
    }

    protected void initWebView() {
        WebSettings webSettings = mWebvContent.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        // 设置JS可用
        webSettings.setAllowFileAccess(true);
        // 自适应屏幕
        // webSettings.setUseWideViewPort(true);
        // webSettings.setLoadWithOverviewMode(true);
        // webSettings.setSupportZoom(true);
        // webSettings.setBuiltInZoomControls(true);
        // JAVAScript脚本
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setSaveFormData(true);
        //webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // 设置 缓存模式
        // 开启 DOM storage API 功能
        //webSettings.setDomStorageEnabled(true);
        // 开启 database storage API 功能
        //webSettings.setDatabaseEnabled(true);
        //String cacheDirPath = mContext.getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        // 设置数据库缓存路径
        //webSettings.setDatabasePath(cacheDirPath);
        // 设置 Application Caches 缓存目录
        //webSettings.setAppCachePath(cacheDirPath);
        // 开启 Application Caches 功能
        webSettings.setAppCacheEnabled(true);

        mWebvContent.requestFocus();
        //mWebvContent.addJavascriptInterface(new , "android");
        webSettings.setDefaultTextEncodingName("UTF-8");
        mWebvContent.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d("pj","onPageStarted---->");
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d("pj","onPageFinished---->");
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //view.loadUrl(url);//当打开新的连接时,使用当前的webview,不使用系统其他浏览器
                return true;
            }
        });
        mWebvContent.loadUrl(mUrl);
        mWebvContent.setLayerType(View.LAYER_TYPE_SOFTWARE,null);
        mWebvContent.setBackgroundColor(0);
    }
}

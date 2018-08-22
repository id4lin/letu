package com.letu.app.game.strategy.ui.me.widget;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.letu.app.baselib.base.BaseActivity;
import com.letu.app.game.strategy.R;
import com.letu.app.game.strategy.constant.Constant;
import com.letu.app.game.strategy.dagger.DaggerAppBaseComponent;
import com.letu.app.game.strategy.ui.me.contract.WebViewContract;
import com.letu.app.game.strategy.ui.me.presenter.WebViewPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebViewActivity extends BaseActivity<WebViewPresenter> implements WebViewContract.View {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.me_webView)
    WebView meWebView;
    private String mTitle;
    private String mFileName;

    @Override
    protected void daggerInit() {
        DaggerAppBaseComponent.builder()
                .appComponent(getAppComponent())
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mPresenter.attachView(this);

        Intent intent = getIntent();
        mTitle = intent.getStringExtra(Constant.KEY_INTENT_ME_WEBVIEW_TITLE);
        mFileName = intent.getStringExtra(Constant.KEY_INTENT_ME_WEBVIEW_FILE_NAME);

        toolbar.setTitle(mTitle);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        initWebView();

    }

    void initWebView(){
        //加载assets目录下的html
        //加上下面这段代码可以使网页中的链接不以浏览器的方式打开
        meWebView.setWebViewClient(new WebViewClient());
        meWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//滚动条风格，为0指滚动条不占用空间，直接覆盖在网页上
        //得到webview设置
        WebSettings webSettings = meWebView.getSettings();
        //允许使用javascript
        webSettings.setJavaScriptEnabled(true);
        //设置字符编码
        webSettings.setDefaultTextEncodingName("UTF-8");
        //支持缩放
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setMinimumLogicalFontSize(18);
        //将WebAppInterface与javascript绑定
        //webview.addJavascriptInterface(new PaymentJavaScriptInterface(),"Android");
        //android assets目录下html文件路径url为 file:///android_asset/profile.html
        String url = "file:///android_asset/" + mFileName;
        meWebView.loadUrl(url);
    }
}

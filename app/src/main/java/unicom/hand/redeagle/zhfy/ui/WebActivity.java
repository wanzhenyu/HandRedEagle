package unicom.hand.redeagle.zhfy.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import unicom.hand.redeagle.R;

import static unicom.hand.redeagle.R.id.webView;


public class WebActivity extends Activity {
    private WebView mWebView;
    private WebSettings webSettings;
    private String errorHtml = "";
    private ProgressDialog progressDialog;
    private TextView common_title_middle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        errorHtml = "<html><body><h1>网络连接异常，请打开网络!</h1></body></html>";
        initData();
    }

    @SuppressLint("NewApi")
    private void initData() {
        // TODO Auto-generated method stub
        mWebView = (WebView) findViewById(webView);
        common_title_middle = (TextView) findViewById(R.id.common_title_middle);
        webSettings = mWebView.getSettings();
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setBuiltInZoomControls(true);
        webSettings.setSupportZoom(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        if (getIntent().getStringExtra("url") != null) {
            mWebView.loadUrl(getIntent().getStringExtra("url"));
            common_title_middle.setText(getIntent().getStringExtra("title"));
        }
        mWebView.setWebViewClient(new HelloWebViewClient());
        findViewById(R.id.common_title_left).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        finish();
                    }
                });
    }

    //Web视图
    private class HelloWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

    }


//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
//            mWebView.goBack(); // goBack()表示返回WebView的上一页面
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//
//    }
    @Override
    protected void onResume() {
        super.onResume();
        try {
            if (mWebView != null) {
                mWebView.onResume();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            if (mWebView != null) {
                mWebView.onPause();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


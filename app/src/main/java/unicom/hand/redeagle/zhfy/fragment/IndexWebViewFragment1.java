package unicom.hand.redeagle.zhfy.fragment;


import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.yealink.utils.NetworkUtils;

import unicom.hand.redeagle.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IndexWebViewFragment1 extends LazyBaseFragment {

    private ProgressBar pb;
    private WebView web;
    public IndexWebViewFragment1() {
        // Required empty public constructor
    }




    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_index_web_view_fragment1;
    }

    @Override
    protected void initView(View v) {
        web = (WebView) v.findViewById(R.id.web);
        WebSettings webSettings = web.getSettings();

//如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        String loadurl = "http://www.pdsdj.gov.cn/djwsp_show.aspx?id=314";
        Log.e("aaa",loadurl);

//设置自适应屏幕，两者合用
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小

//缩放操作
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件

//其他细节操作
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE); //关闭webview中缓存
        webSettings.setAllowFileAccess(true); //设置可以访问文件
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true); //支持通过JS打开新窗口
        webSettings.setLoadsImagesAutomatically(true); //支持自动加载图片
        webSettings.setDefaultTextEncodingName("utf-8");//设置编码格式
        pb = (ProgressBar) v.findViewById(R.id.pb);
        web.setWebViewClient(new WebViewClient(){




            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pb.setVisibility(View.GONE);
            }


            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
//                super.onReceivedSslError(view, handler, error);
                handler.proceed();
            }
        });

        web.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                pb.setProgress(newProgress);


            }
        });
        web.loadUrl(loadurl);
    }
//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
//        if(keyCode==KeyEvent.KEYCODE_BACK&&web.canGoBack()){
//            web.goBack();
//            return true;
//        }
//
//        return super.onKeyDown(keyCode, event);
//    }




    @Override
    public void setTitleRightClick() {

    }

}

package com.dbsh.skup.client;

import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//웹뷰 클라이언트
public class MyWebClient extends WebViewClient {

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        handler.proceed(); // SSL 인증서 무시
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        //return super.shouldOverrideUrlLoading(view, request);
        view.loadUrl(request.getUrl().toString());
        return true; //응용프로그램이 직접 url 처리
    }
}

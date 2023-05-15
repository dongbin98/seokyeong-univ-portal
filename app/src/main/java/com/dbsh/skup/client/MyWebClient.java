package com.dbsh.skup.client;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

//웹뷰 클라이언트
public class MyWebClient extends WebViewClient {

    @SuppressLint("WebViewClientOnReceivedSslError")
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        builder.setMessage("이 사이트의 보안 인증서는 신뢰하는 보안 인증서가 아닙니다. 계속하시겠습니까?");
        builder.setPositiveButton("계속하기", (dialog, which) -> handler.proceed());
        builder.setNegativeButton("취소", ((dialog, which) -> handler.cancel()));
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        //return super.shouldOverrideUrlLoading(view, request);
        view.loadUrl(request.getUrl().toString());
        return true; //응용프로그램이 직접 url 처리
    }
}

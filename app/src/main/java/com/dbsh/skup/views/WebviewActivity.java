package com.dbsh.skup.views;


import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.dbsh.skup.databinding.WebviewFormBinding;
import com.dbsh.skup.viewmodels.WebViewViewModel;

public class WebviewActivity extends AppCompatActivity {

	private WebviewFormBinding binding;
	private WebViewViewModel viewModel;

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    /* DataBinding */
	    /*binding = DataBindingUtil.setContentView(this, R.layout.webview_form);
	    binding.setLifecycleOwner(this);
	    viewModel = new WebViewViewModel();
	    binding.setViewModel(viewModel);
	    binding.executePendingBindings();	// 바인딩 강제 즉시실행

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");

        webView = binding.webview;
        webView.setWebViewClient(new MyWebClient());          // 새 창 띄우기 않기
        webView.setWebChromeClient(new WebChromeClient());

        webView.getSettings().setLoadWithOverviewMode(true);    // WebView 화면크기에 맞추도록 설정 - setUseWideViewPort 와 같이 써야함
        webView.getSettings().setUseWideViewPort(true);         // wide viewport 설정 - setLoadWithOverviewMode 와 같이 써야함

        webView.getSettings().setSupportZoom(true);             // 줌 설정 여부
        webView.getSettings().setBuiltInZoomControls(false);    // 줌 확대/축소 버튼 여부

        webView.getSettings().setJavaScriptEnabled(true);       // 자바스크립트 사용여부
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true); // javascript가 window.open()을 사용할 수 있도록 설정
        webView.getSettings().setSupportMultipleWindows(true);  // 멀티 윈도우 사용 여부

        webView.getSettings().setDomStorageEnabled(true);       // 로컬 스토리지 사용여부
        webView.loadUrl(url);*/
    }
}

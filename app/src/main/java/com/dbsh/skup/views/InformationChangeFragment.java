package com.dbsh.skup.views;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.dbsh.skup.R;
import com.dbsh.skup.model.UserData;
import com.dbsh.skup.databinding.InformationChangeFormBinding;
import com.dbsh.skup.dto.ResponseInformationMap;
import com.dbsh.skup.viewmodels.InformationChangeViewModel;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

public class InformationChangeFragment extends Fragment implements OnBackPressedListener {

    private InformationChangeFormBinding binding;
    private InformationChangeViewModel viewModel;

    // this Fragment
    private Fragment InformationChangeFragment;

    // parent Fragment
    private HomeRightContainer homeRightContainer;

    String id, token;
	String address, zipcode, gunmulNo, ip;  // 도로명주소, 우편번호, 건물번호, 아이피
	Handler handler;
    UserData userData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* DataBinding */
        binding = DataBindingUtil.inflate(inflater, R.layout.information_change_form, container, false);
        viewModel = new InformationChangeViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();    // 바인딩 강제 즉시실행

        userData = ((UserData) getActivity().getApplication());
		token = userData.getToken();
        id = userData.getId();
		ip = getLocalIpAddress();
		handler = new Handler();
		System.out.println(ip);

        InformationChangeFragment = this;
        homeRightContainer = ((HomeRightContainer) this.getParentFragment());

        Toolbar mToolbar = binding.informationChangeToolbar;

        ((HomeActivity) getActivity()).setSupportActionBar(mToolbar);
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("");

		// 개인정보 가져오기
	    viewModel.getInformation(token, id);

		binding.informationChangeAddress.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				/*initandLoadWebView();
				binding.informationChangeWebview.setVisibility(View.VISIBLE);*/
			}
		});

		// 개인정보 수정 버튼
        binding.informationChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.informationChangeButton.setClickable(false);
				// 개인정보 수정요청
				viewModel.changeInformation(token, id,
						binding.informationChangeHomeNumber.getText().toString(),
						binding.informationChangePhoneNumber.getText().toString(),
						binding.informationChangeGuardianPhoneNumber.getText().toString(),
						address,
						binding.informationChangeDetailAddress.getText().toString(),
						zipcode,
						gunmulNo,
						binding.informationChangeEmail.getText().toString(),
						ip
						);
				System.out.println(zipcode);
				System.out.println(gunmulNo);
				System.out.println(address);
            }
        });

		// 영어이름 수정 버튼
		binding.informationChangeEnglishNameButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				binding.informationChangeEnglishNameButton.setClickable(false);
				// 영어이름 수정요청
				viewModel.changeEnglishName(token, id, ip,
						binding.informationChangeEnglishName.getText().toString());
			}
		});

		binding.informationChangeHomeNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		binding.informationChangePhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
		binding.informationChangeGuardianPhoneNumber.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

		viewModel.responseInformationMapLiveData.observe(getViewLifecycleOwner(), new Observer<ResponseInformationMap>() {
			@SuppressLint("SetTextI18n")
			@Override
			public void onChanged(ResponseInformationMap responseInformationMap) {
				if(responseInformationMap != null) {
					if(responseInformationMap.getNewZipCode() == null) {
						binding.informationChangeAddress.setText("");
						binding.informationChangeDetailAddress.setText("");
					} else {
						zipcode = responseInformationMap.getNewZipCode();
						address = responseInformationMap.getJuminDoroAddr1();
						gunmulNo = responseInformationMap.getGunmulNo();
						binding.informationChangeAddress.setText("(" + responseInformationMap.getNewZipCode() + ") " + responseInformationMap.getJuminDoroAddr1());
						binding.informationChangeDetailAddress.setText(responseInformationMap.getJuminDoroAddr2());
					}
					binding.informationChangeHomeNumber.setText(responseInformationMap.getTelNo1() + "-" + responseInformationMap.getTelNo2() + "-" + responseInformationMap.getTelNo3());
					binding.informationChangePhoneNumber.setText(responseInformationMap.getHpNo1() + "-" + responseInformationMap.getHpNo2() + "-" + responseInformationMap.getHpNo3());
					binding.informationChangeGuardianPhoneNumber.setText(responseInformationMap.getGurd1HpNo1() + "-" + responseInformationMap.getGurd1HpNo2() + "-" + responseInformationMap.getGurd1HpNo3());
					binding.informationChangeEmail.setText(responseInformationMap.getEmail());
					binding.informationChangeEnglishName.setText(responseInformationMap.getEngNm());
				}
			}
		});

		viewModel.changeInformationSuccess.observe(getViewLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(String s) {
				if(s.equals("S")) {
					Toast.makeText(getContext(), "저장되었습니다", Toast.LENGTH_SHORT).show();
				} else if(s.equals("F")) {
					Toast.makeText(getContext(), "저장에 실패했습니다", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getContext(), "네트워크 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
				}
				binding.informationChangeButton.setClickable(true);
			}
		});

		viewModel.changeEnglishNameSuccess.observe(getViewLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(String s) {
				if(s.equals("S")) {
					Toast.makeText(getContext(), "저장되었습니다", Toast.LENGTH_SHORT).show();
				} else if(s.equals("F")) {
					Toast.makeText(getContext(), "저장에 실패했습니다", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getContext(), "네트워크 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
				}
				binding.informationChangeEnglishNameButton.setClickable(true);
			}
		});

        return binding.getRoot();
    }

    @Override
    public void onBackPressed() {
		if(binding.informationChangeWebview.getVisibility() == View.VISIBLE) {
			binding.informationChangeWebview.setVisibility(View.INVISIBLE);
			initWebView();
		} else {
			homeRightContainer.getChildFragmentManager().beginTransaction().remove(this).commit();
			homeRightContainer.getChildFragmentManager().popBackStackImmediate();
			homeRightContainer.popFragment();
		}
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((HomeActivity)context).setOnBackPressedListener(this);
    }

	public String getLocalIpAddress() {
		try {
			for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
				NetworkInterface intf = en.nextElement();
				for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
					InetAddress inetAddress = enumIpAddr.nextElement();
					if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
						return inetAddress.getHostAddress();
					}
				}
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		return null;
	}

	WebViewClient client = new WebViewClient() {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
			return false;
		}

		@SuppressLint("WebViewClientOnReceivedSslError")
		@Override
		public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
			handler.proceed();
		}
	};

	WebChromeClient chromeClient = new WebChromeClient() {
		@SuppressLint("SetJavaScriptEnabled")
		@Override
		public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
			WebView webView = new WebView(getContext());
			webView.getSettings().setJavaScriptEnabled(true);

			Dialog dialog = new Dialog(getContext());
			dialog.setContentView(webView);
			WindowManager.LayoutParams layoutParams = dialog.getWindow().getAttributes();
			layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
			layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
			dialog.getWindow().setAttributes(layoutParams);
			dialog.show();

			webView.setWebChromeClient(new WebChromeClient() {
				@Override
				public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
					super.onJsAlert(view, url, message, result);
					return true;
				}

				@Override
				public void onCloseWindow(WebView window) {
					dialog.dismiss();
				}
			});
			initWebView();
			((WebView.WebViewTransport) resultMsg.obj).setWebView(webView);
			resultMsg.sendToTarget();

			return true;
		}
	};

	@SuppressLint("SetJavaScriptEnabled")
	public void initandLoadWebView() {
		binding.informationChangeWebview.getSettings().setJavaScriptEnabled(true);
		binding.informationChangeWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		binding.informationChangeWebview.getSettings().setSupportMultipleWindows(true);
		binding.informationChangeWebview.getSettings().setUseWideViewPort(true);
		binding.informationChangeWebview.getSettings().setLoadWithOverviewMode(true);

		binding.informationChangeWebview.addJavascriptInterface(new AndroidBridge(), "skup");
		binding.informationChangeWebview.setWebViewClient(client);
		binding.informationChangeWebview.setWebChromeClient(chromeClient);
//		binding.informationChangeWebview.loadUrl("http://10.0.2.2:8080/kakao");
		binding.informationChangeWebview.loadUrl("http://skuniv-cgvrlab.kro.kr/test/kakao/api/address");
	}

	@SuppressLint("SetJavaScriptEnabled")
	public void initWebView() {
		binding.informationChangeWebview.getSettings().setJavaScriptEnabled(true);
		binding.informationChangeWebview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		binding.informationChangeWebview.getSettings().setSupportMultipleWindows(true);
		binding.informationChangeWebview.getSettings().setUseWideViewPort(true);
		binding.informationChangeWebview.getSettings().setLoadWithOverviewMode(true);

		binding.informationChangeWebview.addJavascriptInterface(new AndroidBridge(), "skup");
		binding.informationChangeWebview.setWebViewClient(client);
		binding.informationChangeWebview.setWebChromeClient(chromeClient);
//		binding.informationChangeWebview.loadUrl("http://10.0.2.2:8080/kakao");
		binding.informationChangeWebview.loadUrl("http://skuniv-cgvrlab.kro.kr/test/kakao/api/address");
	}

	private class AndroidBridge {
		@JavascriptInterface
		public void setAddress(final String arg1, final String arg2, final String arg3) {
			handler.post(new Runnable() {
				@Override
				public void run() {
					zipcode = arg1;
					address = arg2;
					gunmulNo = arg3;
					binding.informationChangeAddress.setText(String.format("(%s) %s", zipcode, address));
					binding.informationChangeWebview.setVisibility(View.INVISIBLE);
					initWebView();
				}
			});
		}
	}
}

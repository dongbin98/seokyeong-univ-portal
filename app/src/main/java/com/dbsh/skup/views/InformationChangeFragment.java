package com.dbsh.skup.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.dbsh.skup.R;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.InformationChangeFormBinding;
import com.dbsh.skup.model.ResponseInformationMap;
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

        InformationChangeFragment = this;
        homeRightContainer = ((HomeRightContainer) this.getParentFragment());

        Toolbar mToolbar = binding.informationChangeToolbar;

        ((HomeActivity) getActivity()).setSupportActionBar(mToolbar);
        ((HomeActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((HomeActivity) getActivity()).getSupportActionBar().setTitle("");

		// 개인정보 가져오기
	    viewModel.getInformation(token, id);

        binding.informationChangeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.informationChangeButton.setClickable(false);
				// 영어이름 수정요청
	            viewModel.changeEnglishName(token, id, ip,
			            binding.informationChangeEnglishName.getText().toString());

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
            }
        });

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

		viewModel.changeEnglishNameSuccess.observe(getViewLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(String s) {
				if(s.equals("S")) {
					Toast.makeText(getContext(), "저장되었습니다", Toast.LENGTH_SHORT).show();
					onBackPressed();
				} else if(s.equals("F")) {
					Toast.makeText(getContext(), "저장에 실패했습니다", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getContext(), "네트워크 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
				}
			}
		});

		viewModel.changeEnglishNameSuccess.observe(getViewLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(String s) {
				if(s.equals("S")) {
					Toast.makeText(getContext(), "저장되었습니다", Toast.LENGTH_SHORT).show();
					onBackPressed();
				} else if(s.equals("F")) {
					Toast.makeText(getContext(), "저장에 실패했습니다", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getContext(), "네트워크 연결을 확인해주세요", Toast.LENGTH_SHORT).show();
				}
			}
		});

        return binding.getRoot();
    }

    @Override
    public void onBackPressed() {
        homeRightContainer.getChildFragmentManager().beginTransaction().remove(this).commit();
        homeRightContainer.getChildFragmentManager().popBackStackImmediate();
        homeRightContainer.popFragment();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        ((HomeActivity)context).setOnBackPressedListner(this);
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
}

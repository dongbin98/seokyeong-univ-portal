package com.dbsh.skup.views;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class InformationChangeFragment extends Fragment implements OnBackPressedListener {

    private InformationChangeFormBinding binding;
    private InformationChangeViewModel viewModel;

    // this Fragment
    private Fragment InformationChangeFragment;

    // parent Fragment
    private HomeRightContainer homeRightContainer;

    String id, token;
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
				// 수정 요청
            }
        });

		viewModel.responseInformationMapLiveData.observe(getViewLifecycleOwner(), new Observer<ResponseInformationMap>() {
			@SuppressLint("SetTextI18n")
			@Override
			public void onChanged(ResponseInformationMap responseInformationMap) {
				if(responseInformationMap != null) {
					binding.informationChangeAddress.setText("(" + responseInformationMap.getNewZipCode() + ") " + responseInformationMap.getAddr1());
					binding.informationChangeDetailAddress.setText(responseInformationMap.getAddr2());
					binding.informationChangeHomeNumber.setText(responseInformationMap.getTelNo1() + "-" + responseInformationMap.getTelNo2() + "-" + responseInformationMap.getTelNo3());
					binding.informationChangePhoneNumber.setText(responseInformationMap.getHpNo1() + "-" + responseInformationMap.getHpNo2() + "-" + responseInformationMap.getHpNo3());
					binding.informationChangeGuardianPhoneNumber.setText(responseInformationMap.getGurd1HpNo1() + "-" + responseInformationMap.getGurd1HpNo2() + "-" + responseInformationMap.getGurd1HpNo3());
					binding.informationChangeEmail.setText(responseInformationMap.getEmail());
					binding.informationChangeEnglishName.setText(responseInformationMap.getEngNm());
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
}

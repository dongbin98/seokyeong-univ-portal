package com.dbsh.skup.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.HomeRightContainerBinding;
import com.dbsh.skup.viewmodels.HomeRightContainerViewModel;

public class HomeRightContainer extends Fragment implements OnBackPressedListener{

    private HomeRightContainerBinding binding;
    private HomeRightContainerViewModel viewModel;

	// this Fragment
	private Fragment HomeRightFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* Data Binding */
        binding = DataBindingUtil.inflate(inflater, R.layout.home_right_container, container, false);
        viewModel = new HomeRightContainerViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

	    HomeRightFragment = new HomeRightFragment();
		getChildFragmentManager().beginTransaction().add(binding.homeRightContainer.getId(), HomeRightFragment).commit();

        return binding.getRoot();
    }

	@Override
	public void onBackPressed() {
		//
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		((HomeActivity)context).setOnBackPressedListner(this);
	}
}
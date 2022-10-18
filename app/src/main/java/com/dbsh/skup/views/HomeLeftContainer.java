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
import com.dbsh.skup.databinding.HomeLeftContainerBinding;
import com.dbsh.skup.viewmodels.HomeLeftContainerViewModel;

public class HomeLeftContainer extends Fragment implements OnBackPressedListener{

    private HomeLeftContainerBinding binding;
    private HomeLeftContainerViewModel viewModel;

	// this Fragment
	private Fragment HomeLeftFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* Data Binding */
        binding = DataBindingUtil.inflate(inflater, R.layout.home_left_container, container, false);
        viewModel = new HomeLeftContainerViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

	    HomeLeftFragment = new HomeLeftFragment();
		getChildFragmentManager().beginTransaction().add(binding.homeLeftContainer.getId(), HomeLeftFragment).commit();

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
package com.dbsh.skup.views;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.HomeLeftContainerBinding;
import com.dbsh.skup.viewmodels.HomeLeftContainerViewModel;

import java.util.List;

public class HomeLeftContainer extends Fragment implements OnBackPressedListener{

    private HomeLeftContainerBinding binding;
    private HomeLeftContainerViewModel viewModel;
	private long time = 0;

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

	public void replaceFragment(Fragment afterFragment, Bundle bundle) {
		if (bundle != null) {
			afterFragment.setArguments(bundle);
		}
		if (!afterFragment.isAdded()) {
			getChildFragmentManager().beginTransaction().add(binding.homeLeftContainer.getId(), afterFragment).commit();
		} else {
			getChildFragmentManager().beginTransaction().replace(binding.homeLeftContainer.getId(), afterFragment).commit();
		}
		getChildFragmentManager().beginTransaction().show(afterFragment).addToBackStack(null).commit();
	}

	@Override
	public void onBackPressed() {
		List<Fragment> childFragments = getChildFragmentManager().getFragments();
		for(int i = childFragments.size()-1; i >= 0; i--) {
			if(childFragments.get(i) instanceof OnBackPressedListener) {
				((OnBackPressedListener) childFragments.get(i)).onBackPressed();
				return;
			}
		}
		if (System.currentTimeMillis() - time >= 2000) {
			time = System.currentTimeMillis();
			Toast.makeText(getContext(), "한번 더 누르면 로그인창으로 이동합니다.", Toast.LENGTH_SHORT).show();
		} else if (System.currentTimeMillis() - time < 2000) {
			((HomeActivity)getActivity()).finish();
		}
	}

	@Override
	public void onAttach(@NonNull Context context) {
		super.onAttach(context);
		((HomeActivity)context).setOnBackPressedListner(this);
	}
}
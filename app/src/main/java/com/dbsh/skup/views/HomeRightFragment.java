package com.dbsh.skup.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.HomeRightFormBinding;
import com.dbsh.skup.viewmodels.HomeRightViewModel;

public class HomeRightFragment extends Fragment {

	HomeRightFormBinding binding;
	HomeRightViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		/* Data Binding */
		binding = DataBindingUtil.inflate(inflater, R.layout.home_right_form, container, false);
		viewModel = new HomeRightViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();

        return binding.getRoot();
    }
}
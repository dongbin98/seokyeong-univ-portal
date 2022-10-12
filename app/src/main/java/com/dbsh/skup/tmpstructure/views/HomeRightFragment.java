package com.dbsh.skup.tmpstructure.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.HomeTmpRightFormBinding;
import com.dbsh.skup.tmpstructure.viewmodels.HomeRightViewModel;

public class HomeRightFragment extends Fragment {

	HomeTmpRightFormBinding binding;
	HomeRightViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		/* Data Binding */
		binding = DataBindingUtil.inflate(inflater, R.layout.home_tmp_right_form, container, false);
		viewModel = new HomeRightViewModel();
		binding.setViewModel(viewModel);
		binding.executePendingBindings();

        return binding.getRoot();
    }
}
package com.dbsh.skup.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dbsh.skup.R;
import com.dbsh.skup.model.UserData;
import com.dbsh.skup.databinding.HomeCenterInformationFormBinding;
import com.dbsh.skup.viewmodels.HomeCenterInformationViewModel;

public class HomeCenterInformationFragment extends Fragment {

    private HomeCenterInformationFormBinding binding;
    private HomeCenterInformationViewModel viewModel;

    UserData userData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /* Data Binding */
        binding = DataBindingUtil.inflate(inflater, R.layout.home_center_information_form, container, false);
        binding.setLifecycleOwner(getViewLifecycleOwner());
        viewModel = new ViewModelProvider(getActivity()).get(HomeCenterInformationViewModel.class);
        userData = ((UserData) getActivity().getApplication());
        binding.setUserData(userData);

        return binding.getRoot();
    }
}

package com.dbsh.skup.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.GradeAllFormBinding;
import com.dbsh.skup.viewmodels.GradeAllViewModel;

public class GradeAllFragment extends Fragment {
    GradeAllFormBinding binding;
    GradeAllViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.grade_all_form, container, false);
        viewModel = new GradeAllViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        return binding.getRoot();
    }
}

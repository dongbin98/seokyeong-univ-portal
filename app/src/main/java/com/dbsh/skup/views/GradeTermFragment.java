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
import com.dbsh.skup.databinding.GradeTermFormBinding;
import com.dbsh.skup.viewmodels.GradeTermViewModel;

public class GradeTermFragment extends Fragment {
    GradeTermFormBinding binding;
    GradeTermViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.grade_term_form, container, false);
        viewModel = new GradeTermViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        return binding.getRoot();
    }
}

package com.dbsh.skup.tmpstructure.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.HomeTmpCenterInformationFormBinding;
import com.dbsh.skup.tmpstructure.data.UserData;
import com.dbsh.skup.tmpstructure.viewmodels.HomeCenterInformationViewModel;

public class HomeCenterInformationFragment extends Fragment {

    private HomeTmpCenterInformationFormBinding binding;
    private HomeCenterInformationViewModel viewModel;

    UserData userData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /* Data Binding */
        binding = DataBindingUtil.inflate(inflater, R.layout.home_tmp_center_information_form, container, false);
        viewModel = new HomeCenterInformationViewModel();
        binding.setViewModel(viewModel);
        binding.executePendingBindings();

        userData = ((UserData) getActivity().getApplication());

        String col = userData.getColName();
        String ma = userData.getDeptName();
        String studentId = userData.getId() + " " + userData.getKorName();
        String email = userData.getEmailAddress();
        String mentor = userData.getTutorName() + " 멘토";
        String haknyun = userData.getSchYR() + "학년";

        System.out.println("college = " + col);

        binding.card1College.setText(col);
        binding.card1Major.setText(ma);
        binding.card1StuInfo.setText(studentId);
        binding.card1MailAddr.setText(email);
        binding.card1MentorName.setText(mentor);
        binding.card1HaknyunText.setText(haknyun);

        return binding.getRoot();
    }
}

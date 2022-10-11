package com.dbsh.skup.tmpstructure.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.user.User;

public class HomeCenterInformationFragment extends Fragment {

    User userData;

    TextView college;
    TextView major;
    TextView stu_info;
    TextView mail_addr;
    TextView mentor_name;
    TextView haknyun_text;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home_center_information_form, container, false);

        college = rootView.findViewById(R.id.card1_college);
        major = rootView.findViewById(R.id.card1_major);
        stu_info = rootView.findViewById(R.id.card1_stu_info);
        mail_addr = rootView.findViewById(R.id.card1_mail_addr);
        mentor_name = rootView.findViewById(R.id.card1_mentor_name);
        haknyun_text = rootView.findViewById(R.id.card1_haknyun_text);

        userData = ((User) getActivity().getApplication());

        String col = userData.getColName();
        String ma = userData.getDeptName();
        String studentId = userData.getId() + " " + userData.getKorName();
        String email = userData.getEmailAddress();
        String mentor = userData.getTutorName() + " 멘토";
        String haknyun = userData.getSchYR() + "학년";

        college.setText(col);
        major.setText(ma);
        stu_info.setText(studentId);
        mail_addr.setText(email);
        mentor_name.setText(mentor);
        haknyun_text.setText(haknyun);

        return rootView;
    }
}

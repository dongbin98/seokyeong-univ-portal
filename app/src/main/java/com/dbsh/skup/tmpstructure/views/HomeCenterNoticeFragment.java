package com.dbsh.skup.tmpstructure.views;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.webview.WebviewActivity;

public class HomeCenterNoticeFragment extends Fragment {
    TextView home_center_notice_type;
    TextView home_center_notice_department;
    TextView home_center_notice_date;
    TextView home_center_notice_title;

    public static HomeCenterNoticeFragment newInstance(int number, String title, String type, String date, String department, String url) {
        HomeCenterNoticeFragment fragment = new HomeCenterNoticeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("number", number);
        bundle.putString("title", title);
        bundle.putString("type", type);
        bundle.putString("date", date);
        bundle.putString("department", department);
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null) {
            int num = getArguments().getInt("number");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home_center_notice_form, container, false);
        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("url", getArguments().getString("url"));
                startActivity(intent);
            }
        });
        home_center_notice_type = rootView.findViewById(R.id.home_center_notice_type);
        home_center_notice_department = rootView.findViewById(R.id.home_center_notice_department);
        home_center_notice_date = rootView.findViewById(R.id.home_center_notice_date);
        home_center_notice_title =  rootView.findViewById(R.id.home_center_notice_title);

        if(getArguments() != null) {
            home_center_notice_type.setText(getArguments().getString("type"));
            home_center_notice_department.setText(getArguments().getString("department"));
            home_center_notice_date.setText("ㆍ" + getArguments().getString("date"));
            home_center_notice_title.setText(getArguments().getString("title"));
        }
        return rootView;
    }
}

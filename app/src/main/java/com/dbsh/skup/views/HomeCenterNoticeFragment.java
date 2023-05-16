package com.dbsh.skup.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.HomeCenterNoticeFormBinding;
import com.dbsh.skup.viewmodels.HomeCenterNoticeViewModel;

public class HomeCenterNoticeFragment extends Fragment {
	private HomeCenterNoticeFormBinding binding;
	private HomeCenterNoticeViewModel viewModel;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    /* DataBinding */
	    binding = DataBindingUtil.inflate(inflater, R.layout.home_center_notice_form, container, false);
	    viewModel = new HomeCenterNoticeViewModel();
	    binding.setViewModel(viewModel);
	    binding.executePendingBindings();    // 바인딩 강제 즉시실행
	    binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // WebView
                /*Intent intent = new Intent(getActivity(), WebviewActivity.class);
                intent.putExtra("url", getArguments().getString("url"));*/

                // WebBrowser
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(getArguments().getString("url")));
                startActivity(intent);
            }
        });

        if(getArguments() != null) {
	        binding.homeCenterNoticeType.setText(getArguments().getString("type"));
	        binding.homeCenterNoticeDepartment.setText(getArguments().getString("department"));
	        binding.homeCenterNoticeDate.setText("ㆍ" + getArguments().getString("date"));
            binding.homeCenterNoticeTitle.setText(getArguments().getString("title"));
        }
        return binding.getRoot();
    }
}

package com.dbsh.skup.views;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.HomeRightFormBinding;
import com.dbsh.skup.service.NoticeNotificationService;
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

		binding.getStationBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(binding.getStationBtn.getText().equals("공지사항 알림켜기")) {
					/* Test Notification */
					Intent intent = new Intent(getActivity(), NoticeNotificationService.class);
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
						getActivity().startForegroundService(intent);
					} else {
						getActivity().startService(intent);
					}
					binding.getStationBtn.setText("공지사항 알림끄기");
				}
			}
		});

        return binding.getRoot();
    }
}
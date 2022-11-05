package com.dbsh.skup.views;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.io.IOException;

public class HomeRightFragment extends Fragment {

	HomeRightFormBinding binding;
	HomeRightViewModel viewModel;
	Boolean notificationCheck;
	Boolean loginCheck;

	// for Json File
	final String fileName = "station.json";

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		/* Data Binding */
		binding = DataBindingUtil.inflate(inflater, R.layout.home_right_form, container, false);
		viewModel = new HomeRightViewModel(getActivity().getApplication());
		binding.setViewModel(viewModel);
		binding.executePendingBindings();

		// Notification Check
		SharedPreferences notification = getActivity().getSharedPreferences("notice", Activity.MODE_PRIVATE);
		SharedPreferences.Editor notificationEdit = notification.edit();
		notificationCheck = notification.getBoolean("checked", false);
		binding.mypageNotificationSwitch.setChecked(notificationCheck);

		if(binding.mypageNotificationSwitch.isChecked() && !isServiceRunningCheck()) {
			Intent intent = new Intent(getActivity(), NoticeNotificationService.class);
			notificationEdit.putBoolean("checked", true);
			notificationEdit.apply();
			intent.setAction("start");
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				getActivity().startForegroundService(intent);
			} else {
				getActivity().startService(intent);
			}
		}

		binding.mypageNotificationSwitch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), NoticeNotificationService.class);
				if(binding.mypageNotificationSwitch.isChecked()) {
					notificationEdit.putBoolean("checked", true);
					notificationEdit.apply();
					intent.setAction("start");
					if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
						getActivity().startForegroundService(intent);
					} else {
						getActivity().startService(intent);
					}
				} else {
					notificationEdit.putBoolean("checked", false);
					notificationEdit.apply();
					if(isServiceRunningCheck()) {
						intent.setAction("stop");
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
							getActivity().startForegroundService(intent);
						} else {
							getActivity().startService(intent);
						}
					}
				}
			}
		});

		// Login Auto Check
	    SharedPreferences auto = getActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
		SharedPreferences.Editor autoLoginEdit = auto.edit();
		loginCheck = auto.getBoolean("checked", false);
		binding.mypageAutologinSwitch.setChecked(loginCheck);

		binding.mypageAutologinSwitch.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(binding.mypageAutologinSwitch.isChecked()) {
					autoLoginEdit.putBoolean("checked", true);
					autoLoginEdit.apply();
				} else {
					autoLoginEdit.putBoolean("checked", false);
					autoLoginEdit.apply();
				}
			}
		});
		// Bus Route Update
		binding.mypageView4.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				binding.mypageView4.setClickable(false);
				try {
					viewModel.getFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		// Change Password

		// Logout
		binding.mypageView6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getActivity().finish();
			}
		});

        return binding.getRoot();
    }

	public boolean isServiceRunningCheck() {
		ActivityManager manager = (ActivityManager) getActivity().getSystemService(Activity.ACTIVITY_SERVICE);
		for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
			if ("com.dbsh.skup.service.NoticeNotificationService".equals(service.service.getClassName())) {
				return true;
			}
		}
		return false;
	}
}
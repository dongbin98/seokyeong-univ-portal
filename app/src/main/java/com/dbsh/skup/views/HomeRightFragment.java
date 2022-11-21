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
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.dbsh.skup.R;
import com.dbsh.skup.data.UserData;
import com.dbsh.skup.databinding.HomeRightFormBinding;
import com.dbsh.skup.model.ResponseStationItem;
import com.dbsh.skup.service.NoticeNotificationService;
import com.dbsh.skup.viewmodels.HomeRightViewModel;

import java.io.IOException;
import java.util.List;

public class HomeRightFragment extends Fragment {

	HomeRightFormBinding binding;
	HomeRightViewModel viewModel;
	Boolean notificationCheck;
	Boolean loginCheck;

	UserData userData;

	// this Fragment
	private Fragment HomeRightFragment;

	// parent Fragment
	private HomeRightContainer homeRightContainer;

	// for Json File
	final String file1164 = "1164.json";
	final String file2115 = "2115.json";
	int routeUpdate;
	int passwordModifyDay;

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		/* Data Binding */
		binding = DataBindingUtil.inflate(inflater, R.layout.home_right_form, container, false);
		viewModel = new HomeRightViewModel(getActivity().getApplication());
		binding.setViewModel(viewModel);
		binding.executePendingBindings();

		userData = ((UserData) getActivity().getApplication());
		passwordModifyDay = Integer.parseInt(userData.getLastPasswordModify());
		binding.mypageId.setText(userData.getId());
		binding.mypageName.setText(userData.getKorName());
		if(passwordModifyDay <= 30) {
			binding.mypagePasswordDay.setBackgroundResource(R.drawable.frame_red_line);
			binding.mypagePasswordDay.setTextColor(getContext().getColor(R.color.mainRed));
		} else if(passwordModifyDay <= 60) {
			binding.mypagePasswordDay.setBackgroundResource(R.drawable.frame_yellow_line);
			binding.mypagePasswordDay.setTextColor(getContext().getColor(R.color.mainYellow));
		} else {
			binding.mypagePasswordDay.setBackgroundResource(R.drawable.frame_blue_line);
			binding.mypagePasswordDay.setTextColor(getContext().getColor(R.color.mainBlue));
		}
		binding.mypagePasswordDay.setText(String.format("D - %s", userData.getLastPasswordModify()));

		HomeRightFragment = this;
		homeRightContainer = ((HomeRightContainer) this.getParentFragment());

		// 자식 프래그먼트에서 부모 프래그먼트 확인을 위함
		Bundle bundle = new Bundle();
		bundle.putString("type", "right");

		// Information Change
		binding.mypageSetting.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				homeRightContainer.pushFragment(HomeRightFragment, new InformationChangeFragment(), null);
			}
		});

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
				routeUpdate = 0;
				viewModel.getStaton1164();
				viewModel.getStaton2115();
			}
		});
		// Change Password
		binding.mypageView5.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				homeRightContainer.pushFragment(HomeRightFragment, new PasswordAuthFragment(), bundle);
			}
		});

		// Logout
		binding.mypageView6.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getActivity().finish();
			}
		});

		viewModel.station1164.observe(getViewLifecycleOwner(), new Observer<List<ResponseStationItem>>() {
			@Override
			public void onChanged(List<ResponseStationItem> responseStationItems) {
				if(responseStationItems == null) {
					Toast.makeText(getContext(), "1164번 노선정보를 불러오지 못했습니다 다시 갱신해주세요", Toast.LENGTH_SHORT).show();
				} else {
					try {
						viewModel.write1164File(viewModel.makeJson(responseStationItems, "1164"));
					} catch (IOException e) {
						Toast.makeText(getContext(), "1164번 노선정보를 불러오지 못했습니다 다시 갱신해주세요", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		viewModel.station2115.observe(getViewLifecycleOwner(), new Observer<List<ResponseStationItem>>() {
			@Override
			public void onChanged(List<ResponseStationItem> responseStationItems) {
				if(responseStationItems == null) {
					Toast.makeText(getContext(), "2115번 노선정보를 불러오지 못했습니다 다시 갱신해주세요", Toast.LENGTH_SHORT).show();
				} else {
					try {
						viewModel.write2115File(viewModel.makeJson(responseStationItems, "2115"));
					} catch (IOException e) {
						Toast.makeText(getContext(), "2115번 노선정보를 불러오지 못했습니다 다시 갱신해주세요", Toast.LENGTH_SHORT).show();
					}
				}
			}
		});

		viewModel.file1164State.observe(getViewLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(String s) {
				routeUpdate++;
				if(s.equals("F")) {
					Toast.makeText(getContext(), "1164번 노선정보를 불러오지 못했습니다 다시 갱신해주세요", Toast.LENGTH_SHORT).show();
				}
				if(routeUpdate == 2) {
					Toast.makeText(getContext(), "노선정보 업데이트 완료", Toast.LENGTH_SHORT).show();
					binding.mypageView4.setClickable(true);
				}
			}
		});

		viewModel.file2115State.observe(getViewLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(String s) {
				routeUpdate++;
				if(s.equals("F")) {
					Toast.makeText(getContext(), "2115번 노선정보를 불러오지 못했습니다 다시 갱신해주세요", Toast.LENGTH_SHORT).show();
				}
				if(routeUpdate == 2) {
					Toast.makeText(getContext(), "노선정보 업데이트 완료", Toast.LENGTH_SHORT).show();
					binding.mypageView4.setClickable(true);
				}
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
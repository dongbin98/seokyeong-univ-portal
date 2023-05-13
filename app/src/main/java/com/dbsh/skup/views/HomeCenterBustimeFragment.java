package com.dbsh.skup.views;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.HomeCenterBustimeFormBinding;
import com.dbsh.skup.model.BusData;
import com.dbsh.skup.viewmodels.HomeCenterBustimeViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeCenterBustimeFragment extends Fragment {

	private HomeCenterBustimeFormBinding binding;
	private HomeCenterBustimeViewModel viewModel;
	private FusedLocationProviderClient fusedLocationProviderClient;

	// for Json File
	final String file1164 = "1164.json";
	final String file2115 = "2115.json";

	// refresh
	Date date;
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	double myGpsX, myGpsY;

	private ActivityResultLauncher<String[]> locationPermissionRequest;
	final String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		/* Data Binding */
		binding = DataBindingUtil.inflate(inflater, R.layout.home_center_bustime_form, container, false);
		binding.setLifecycleOwner(getViewLifecycleOwner());
		viewModel = new ViewModelProvider(getActivity()).get(HomeCenterBustimeViewModel.class);
		viewModel.setContext(getContext());

		BusData busData = new BusData();
		binding.setBusData(busData);

		busData.setBusType("서비스 준비중");

		/* Bus Time 보류
		// 정류장 정보 가져오기
		File f1164 = new File(getActivity().getFilesDir(), file1164);
		File f2115 = new File(getActivity().getFilesDir(), file2115);

		// GPS + Network 위치 통합
		fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

		LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 60000)
				.setIntervalMillis(60000)
				.setMinUpdateIntervalMillis(50000)
				.build();

		// 최인접 버스정류장 실시간 도착시간 구하기 -> 너무 많은 API 사용으로 중단
		LocationCallback locationCallback = new LocationCallback() {
			@Override
			public void onLocationResult(@NonNull LocationResult locationResult) {
				super.onLocationResult(locationResult);

				for (Location loc : locationResult.getLocations()) {
					myGpsX = loc.getLongitude();
					myGpsY = loc.getLatitude();
				}
				if (f1164.exists() && f2115.exists()) {
					Log.d("tag", "received " + locationResult.getLocations().size() + " locations");
					for (Location loc : locationResult.getLocations()) {
						myGpsX = loc.getLongitude();
						myGpsY = loc.getLatitude();
						// 최인접 버스정류장 도착시간 구하기 (비동기식)
						viewModel.updateBusArrive(myGpsX, myGpsY);
						date = new Date(System.currentTimeMillis());
						simpleDateFormat.format(date);
						busData.setUpdateDate("갱신 : " + simpleDateFormat.format(date));
						binding.setBusData(busData);
					}
				} else if (f1164.exists() && !f2115.exists()) {
					Log.d("tag", "received " + locationResult.getLocations().size() + " locations");
					for (Location loc : locationResult.getLocations()) {
						myGpsX = loc.getLongitude();
						myGpsY = loc.getLatitude();
						// 최인접 버스정류장 도착시간 구하기 (비동기식)
						viewModel.updateBusArrive(myGpsX, myGpsY);
						date = new Date(System.currentTimeMillis());
						simpleDateFormat.format(date);
						busData.setUpdateDate("갱신 : " + simpleDateFormat.format(date));
						binding.setBusData(busData);
					}
					Toast.makeText(getActivity(), "2115노선 정류장 정보가 유효하지 않습니다", Toast.LENGTH_SHORT).show();
				} else if (!f1164.exists() && f2115.exists()) {
					Log.d("tag", "received " + locationResult.getLocations().size() + " locations");
					for (Location loc : locationResult.getLocations()) {
						myGpsX = loc.getLongitude();
						myGpsY = loc.getLatitude();
						// 최인접 버스정류장 도착시간 구하기 (비동기식)
						viewModel.updateBusArrive(myGpsX, myGpsY);
						date = new Date(System.currentTimeMillis());
						simpleDateFormat.format(date);
						busData.setUpdateDate("갱신 : " + simpleDateFormat.format(date));
						binding.setBusData(busData);
					}
					Toast.makeText(getActivity(), "1164노선 정류장 정보가 유효하지 않습니다", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), "1164, 2115노선 정류장 정보가 유효하지 않습니다", Toast.LENGTH_SHORT).show();
				}
			}

			@Override
			public void onLocationAvailability(@NonNull LocationAvailability locationAvailability) {
				super.onLocationAvailability(locationAvailability);
			}
		};

		*//* registerForActivityResult를 통한 Permission Authorize *//*
		locationPermissionRequest = registerForActivityResult(
				new ActivityResultContracts.RequestMultiplePermissions(),
				result -> {
					Boolean fineLocationGranted = result.get(Manifest.permission.ACCESS_FINE_LOCATION);
					Boolean coarseLocationGranted = result.get(Manifest.permission.ACCESS_COARSE_LOCATION);

					if (Boolean.FALSE.equals(fineLocationGranted) && Boolean.FALSE.equals(coarseLocationGranted)) {
						Toast.makeText(getActivity(), "위치 권한을 거부하였습니다. 추후 앱 설정을 통해 위치 권한을 허용할 수 있습니다.", Toast.LENGTH_SHORT).show();
					} else {
						fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
					}
				}
		);

		if(checkLocationPermission()) {
			showPermissionDialog();
			fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
		}


		viewModel.busType.observe(getViewLifecycleOwner(), s -> {
			busData.setBusType(s);
			binding.setBusData(busData);
		});
		viewModel.location1164.observe(getViewLifecycleOwner(), s -> {
			busData.setLocation1164(s);
			binding.setBusData(busData);
		});
		viewModel.arriveFirst1164.observe(getViewLifecycleOwner(), s -> {
			busData.setArriveFirst1164(s);
			binding.setBusData(busData);
		});
		viewModel.arriveSecond1164.observe(getViewLifecycleOwner(), s -> {
			busData.setArriveSecond1164(s);
			binding.setBusData(busData);
		});
		viewModel.location2115.observe(getViewLifecycleOwner(), s -> {
			busData.setLocation2115(s);
			binding.setBusData(busData);
		});
		viewModel.arriveFirst2115.observe(getViewLifecycleOwner(), s -> {
			busData.setArriveFirst2115(s);
			binding.setBusData(busData);
		});
		viewModel.arriveSecond2115.observe(getViewLifecycleOwner(), s -> {
			busData.setArriveSecond2115(s);
			binding.setBusData(busData);
		});

		binding.card3LocationRefresh.setOnClickListener(view -> {
			if(checkLocationPermission()) {
				showPermissionDialog();
				if (f1164.exists() && f2115.exists()) {
					// 클릭 시 북악관에서 내 위치까지의 거리 다시구하기
					// 최인접 버스정류장 도착시간 구하기 (비동기식)
					viewModel.updateBusArrive(myGpsX, myGpsY);
					date = new Date(System.currentTimeMillis());
					simpleDateFormat.format(date);
					busData.setUpdateDate("갱신 : " + simpleDateFormat.format(date));
					binding.setBusData(busData);
					Toast.makeText(getActivity(), "갱신되었습니다", Toast.LENGTH_SHORT).show();
				} else if (f1164.exists() && !f2115.exists()) {
					viewModel.updateBusArrive(myGpsX, myGpsY);
					date = new Date(System.currentTimeMillis());
					simpleDateFormat.format(date);
					busData.setUpdateDate("갱신 : " + simpleDateFormat.format(date));
					binding.setBusData(busData);
					Toast.makeText(getActivity(), "2115노선 정류장 정보가 유효하지 않습니다", Toast.LENGTH_SHORT).show();
				} else if (!f1164.exists() && f2115.exists()) {
					viewModel.updateBusArrive(myGpsX, myGpsY);
					date = new Date(System.currentTimeMillis());
					simpleDateFormat.format(date);
					busData.setUpdateDate("갱신 : " + simpleDateFormat.format(date));
					binding.setBusData(busData);
					Toast.makeText(getActivity(), "1164노선 정류장 정보가 유효하지 않습니다", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(getActivity(), "1164, 2115노선 정류장 정보가 유효하지 않습니다", Toast.LENGTH_SHORT).show();
				}
			}
		});*/
		return binding.getRoot();
	}

	private boolean checkLocationPermission() {
		boolean coarseLocationGranted = ContextCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_COARSE_LOCATION
		) == PackageManager.PERMISSION_GRANTED;

		boolean fineLocationGranted = ContextCompat.checkSelfPermission(
				requireContext(),
				Manifest.permission.ACCESS_FINE_LOCATION
		) == PackageManager.PERMISSION_GRANTED;

		if (!coarseLocationGranted && !fineLocationGranted) {
			requestPermission();
		} else {
			return true;
		}
		return false;
	}

	private void requestPermission() {
		ActivityCompat.requestPermissions(requireActivity(), permissions, 1);
		showPermissionDialog();
	}

	private void requestBackgroundPermission() {
		ActivityCompat.requestPermissions(
				requireActivity(),
				new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION},
				2
		);
	}

	private void showPermissionDialog() {
		if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_BACKGROUND_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			AlertDialog.Builder builder = new AlertDialog.Builder(requireContext())
					.setTitle("skup 위치 액세스 권한 안내")
					.setMessage("백그라운드 위치 권한을 위해 항상 허용으로 설정해주세요.")
					.setCancelable(false)
					.setNegativeButton("거부", null)
					.setPositiveButton("동의", (dialog1, which) -> requestBackgroundPermission());
			builder.create().show();
		}
	}
}

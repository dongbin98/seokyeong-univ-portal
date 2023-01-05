package com.dbsh.skup.views;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.HomeCenterBustimeFormBinding;
import com.dbsh.skup.model.BusData;
import com.dbsh.skup.viewmodels.HomeCenterBustimeViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeCenterBustimeFragment extends Fragment {

	private HomeCenterBustimeFormBinding binding;
	private HomeCenterBustimeViewModel viewModel;

	// for Json File
	final String file1164 = "1164.json";
	final String file2115 = "2115.json";

	// refresh
	Date date;
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

	// for GPS
	private FusedLocationProviderClient fusedLocationProviderClient;                // GPS + Network 통합
	private LocationRequest locationRequest;
	private LocationCallback locationCallback;

	double myGpsX, myGpsY;

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

		viewModel.busType.observe(getViewLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(String s) {
				busData.setBusType(s);
				binding.setBusData(busData);
			}
		});
		viewModel.location1164.observe(getViewLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(String s) {
				busData.setLocation1164(s);
				binding.setBusData(busData);
			}
		});
		viewModel.arriveFirst1164.observe(getViewLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(String s) {
				busData.setArriveFirst1164(s);
				binding.setBusData(busData);
			}
		});
		viewModel.arriveSecond1164.observe(getViewLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(String s) {
				busData.setArriveSecond1164(s);
				binding.setBusData(busData);
			}
		});
		viewModel.location2115.observe(getViewLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(String s) {
				busData.setLocation2115(s);
				binding.setBusData(busData);
			}
		});
		viewModel.arriveFirst2115.observe(getViewLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(String s) {
				busData.setArriveFirst2115(s);
				binding.setBusData(busData);
			}
		});
		viewModel.arriveSecond2115.observe(getViewLifecycleOwner(), new Observer<String>() {
			@Override
			public void onChanged(String s) {
				busData.setArriveSecond2115(s);
				binding.setBusData(busData);
			}
		});

		// 정류장 정보 가져오기
		File f1164 = new File(getActivity().getFilesDir(), file1164);
		File f2115 = new File(getActivity().getFilesDir(), file2115);

		fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

//		if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
//				&& ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//			ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
//			ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
//		}

		locationRequest = locationRequest.create();
		locationRequest.setInterval(60000);
		locationRequest.setFastestInterval(50000);

		locationCallback = new LocationCallback() {
			@Override
			public void onLocationResult(@NonNull LocationResult locationResult) {
				super.onLocationResult(locationResult);
				if (locationResult == null) {
					Log.d("tag", "locationResult null");
					return;
				}
				if(f1164.exists() && f2115.exists()) {
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
				} else if(f1164.exists() && !f2115.exists()) {
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
				} else if(!f1164.exists() && f2115.exists()) {
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

		fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

		binding.card3LocationRefresh.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if(f1164.exists() && f2115.exists()) {
					// 클릭 시 북악관에서 내 위치까지의 거리 다시구하기
					// 최인접 버스정류장 도착시간 구하기 (비동기식)
					viewModel.updateBusArrive(myGpsX, myGpsY);
					date = new Date(System.currentTimeMillis());
					simpleDateFormat.format(date);
					busData.setUpdateDate("갱신 : " + simpleDateFormat.format(date));
					binding.setBusData(busData);
					Toast.makeText(getActivity(), "갱신되었습니다", Toast.LENGTH_SHORT).show();
				} else if(f1164.exists() && !f2115.exists()) {
					viewModel.updateBusArrive(myGpsX, myGpsY);
					date = new Date(System.currentTimeMillis());
					simpleDateFormat.format(date);
					busData.setUpdateDate("갱신 : " + simpleDateFormat.format(date));
					binding.setBusData(busData);
					Toast.makeText(getActivity(), "2115노선 정류장 정보가 유효하지 않습니다", Toast.LENGTH_SHORT).show();
				} else if(!f1164.exists() && f2115.exists()) {
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
		});
		return binding.getRoot();
	}
}

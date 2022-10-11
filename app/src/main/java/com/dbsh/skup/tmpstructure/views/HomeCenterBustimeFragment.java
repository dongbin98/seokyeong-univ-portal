package com.dbsh.skup.tmpstructure.views;

import android.Manifest;
import android.content.pm.PackageManager;
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
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.databinding.HomeTmpCenterBustimeFormBinding;
import com.dbsh.skup.tmpstructure.viewmodels.HomeCenterBustimeViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class HomeCenterBustimeFragment extends Fragment {

	private HomeTmpCenterBustimeFormBinding binding;
	private HomeCenterBustimeViewModel viewModel;

	// for Json File
	final String fileName = "station.json";

	// refresh
	Date date;
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
		binding = DataBindingUtil.inflate(inflater, R.layout.home_tmp_center_bustime_form, container, false);
		viewModel = new HomeCenterBustimeViewModel(getContext());
		binding.setViewModel(viewModel);
		binding.executePendingBindings();

		viewModel.arrive1164 = new ArrayList<>();
		viewModel.arrive2115 = new ArrayList<>();

		// 정류장 정보 가져오기
		File file = new File(getActivity().getFilesDir(), fileName);
		if (!file.exists()) {
			System.out.println("파일이 존재하지 않음");
			viewModel.getStation();
		}

		fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

		if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
				&& ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
			ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 2);
		}

		locationRequest = locationRequest.create();
		locationRequest.setInterval(60000);
		locationRequest.setFastestInterval(30000);

		locationCallback = new LocationCallback() {
			@Override
			public void onLocationResult(@NonNull LocationResult locationResult) {
				super.onLocationResult(locationResult);
				if (locationResult == null) {
					Log.d("tag", "locationResult null");
					return;
				}
				if(file.exists()) {
					Log.d("tag", "received " + locationResult.getLocations().size() + " locations");
					for (Location loc : locationResult.getLocations()) {
						myGpsX = loc.getLongitude();
						myGpsY = loc.getLatitude();
						viewModel.arrive1164.clear();
						viewModel.arrive2115.clear();
						viewModel.updateBusArrive(myGpsX, myGpsY);
						date = new Date(System.currentTimeMillis());
						simpleDateFormat.format(date);
						binding.card3LocationRefreshTime.setText("갱신 : " + simpleDateFormat.format(date));
					}
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
				if(file.exists()) {
					// 클릭 시 북악관에서 내 위치까지의 거리 다시구하기
					viewModel.arrive1164.clear();
					viewModel.arrive2115.clear();
					viewModel.updateBusArrive(myGpsX, myGpsY);
					date = new Date(System.currentTimeMillis());
					simpleDateFormat.format(date);
					binding.card3LocationRefreshTime.setText("갱신 : " + simpleDateFormat.format(date));
					Toast.makeText(getActivity(), "버스시간 갱신", Toast.LENGTH_SHORT).show();
				}
			}
		});

		return binding.getRoot();
	}
}

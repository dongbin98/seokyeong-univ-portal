package com.dbsh.skup.home;

import static java.lang.Thread.sleep;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.bus.BusArriveTask;
import com.dbsh.skup.bus.Station;
import com.dbsh.skup.bus.StationTask;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HomeCenterBustimeFragment extends Fragment {

	// Data.go.kr
	String stationUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute";
	String arriveUrl = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRoute";   // ?serviceKey=~~?stId=106000319&busRouteId=100100598&ord=1
	final String serviceKey = "6QzxyxwgIYyrLx303Ylnud8BZMmZ4Caw%2Ble0fsW8oc9lsv0KHGZWV5jhZ%2BLgzGbESVdaYzjRmnnwdzWychAaeA%3D%3D";

	// for Json File
	int COUNT = 0;
	final String fileName = "station.json";

	// for GPS
	FusedLocationProviderClient fusedLocationProviderClient;            // GPS + Network 통합

	double myPosX = 0, myPosY = 0, distance = 5000.0;
	double skuPosY = 37.61601970f, skuPosX = 127.01185885f;
	final double limitedDistance = 0.005f;

	final String direction1164Sku = "서경대";               // 1164 서경대 방향
	final String direction1164Garage = "길음전철역";         // 1164 길음역 방향
	final String direction2115Sku = "서경대입구";         // 2115 서경대 방향
	final String direction2115Garage = "중랑공영차고지";   // 2115 차고지 방향

	String shortestStationName, shortestStationId, shortestStationSeq;
	ArrayList<String> arrive1164, arrive2115;

	TextView locationText, cardText1;
	TextView arrive1164Text1, arrive1164Text2;
	TextView arrive2115Text1, arrive2115Text2;

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home_center_bustime_form, container, false);

		arrive1164 = new ArrayList<>();
		arrive2115 = new ArrayList<>();

		// 위치 기반 거리재기
		locationText = (TextView) rootView.findViewById(R.id.card3_location_text);
		cardText1 = (TextView) rootView.findViewById(R.id.card3_text1);
		arrive1164Text1 = (TextView) rootView.findViewById(R.id.card3_1164_arrive1);
		arrive1164Text2 = (TextView) rootView.findViewById(R.id.card3_1164_arrive2);
		arrive2115Text1 = (TextView) rootView.findViewById(R.id.card3_2115_arrive1);
		arrive2115Text2 = (TextView) rootView.findViewById(R.id.card3_2115_arrive2);

		// 정류장 정보 가져오기
		File file = new File(getActivity().getFilesDir(), fileName);
		if(!file.exists()) {
			getStation();
		}

		fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

		cardText1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// 클릭 시 북악관에서 내 위치까지의 거리 다시구하기
				arrive1164.clear();
				arrive2115.clear();
				double myDistance = updateMyDistance();
				JSONObject result = readFile();
				updateBusArrive(result, myDistance);
				Toast.makeText(getActivity(), "수동갱신", Toast.LENGTH_SHORT).show();
			}
		});

		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					while (true) {
						sleep(60000);
						// 1분마다 갱신
						Handler handler = new Handler(Looper.getMainLooper());
						handler.postDelayed(new Runnable() {
							@Override
							public void run() {
								arrive1164.clear();
								arrive2115.clear();
								double myDistance = updateMyDistance();
								JSONObject result = readFile();
								updateBusArrive(result, myDistance);
							}
						}, 0);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		// 북악관에서 내 위치까지의 거리구하기
		double myDistance = updateMyDistance();
		JSONObject result = readFile();
		updateBusArrive(result, myDistance);

        return rootView;
    }

	public void getStation() {
		//serviceKey = URLEncoder.encode(serviceKey);

		ArrayList<Station> stations = new ArrayList<>();

		String search1164 = stationUrl+"?serviceKey="+serviceKey+"&busRouteId=100100171";
		String search2115 = stationUrl+"?serviceKey="+serviceKey+"&busRouteId=100100598";

		StationTask station1164Task = new StationTask();
		StationTask station2115Task = new StationTask();
		try {
			stations = station1164Task.execute(search1164).get();
			stations.addAll(station2115Task.execute(search2115).get());
			JSONObject json = makeJson(stations);
			writeFile(json);
			Toast.makeText(getContext(), "파일 저장완료", Toast.LENGTH_SHORT).show();
		} catch (ExecutionException | InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

	public JSONObject makeJson(ArrayList<Station> stations) {
		JSONObject json = new JSONObject();
		JSONArray result = new JSONArray();
		try {
			for(Station station : stations) {
				COUNT++;
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("routeId", station.getRouteId());
				jsonObject.put("stationId", station.getStationId());
				jsonObject.put("stationName", station.getStationName());
				jsonObject.put("sequence", station.getSeq());
				jsonObject.put("posX", station.getPosX());
				jsonObject.put("posY", station.getPosY());
				jsonObject.put("direction", station.getDirection());
				result.put(jsonObject);
			}
			json.put("COUNT", COUNT);
			json.put("LIST", result);
			return json;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void writeFile(JSONObject json) throws IOException {
		File file = new File(getContext().getFilesDir(), fileName);
		if(!file.exists()) {
			file.createNewFile();
		}
		else {
			file.delete();
			file.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(file,true));
		bw.write(json.toString());
		bw.newLine();
		bw.close();
	}

	public JSONObject readFile() {
		File file = new File(getContext().getFilesDir(), fileName);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			StringBuilder sb = new StringBuilder();
			String str;
			while ((str = reader.readLine()) != null) {
				sb.append(str);
			}
			reader.close();
			JSONObject stationJson = new JSONObject(sb.toString());
			return stationJson;
		} catch (IOException | JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<String> getArrive(String stationId, String routeId, String seq) {
		BusArriveTask busArriveTask = new BusArriveTask();

		String searchUrl = arriveUrl+"?serviceKey=" + serviceKey + "&stId=" + stationId + "&busRouteId=" + routeId + "&ord=" + seq;
		ArrayList<String> result = null;
		try {
			result = busArriveTask.execute(searchUrl).get();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return result;
	}

	public double updateMyDistance() {
		if ( Build.VERSION.SDK_INT >= 23 &&
				ContextCompat.checkSelfPermission( getActivity().getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED ) {
			ActivityCompat.requestPermissions( getActivity(), new String[] {  android.Manifest.permission.ACCESS_FINE_LOCATION  },
					0 );
		}
		else {
			fusedLocationProviderClient.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
				@Override
				public void onSuccess(Location location) {
					myPosX = location.getLongitude();
					myPosY = location.getLatitude();
				}
			});
		}
		double myDistance = Math.sqrt(Math.pow(skuPosX - myPosX, 2) + Math.pow(skuPosY - myPosY, 2));   // 북악관에서 내위치까지의 거리
		System.out.println("현재 내 위치는 = " + myPosX +", " + myPosY);
		System.out.println("거리는 = " + myDistance);
		return myDistance;
	}

	public void updateBusArrive(JSONObject result, Double myDistance) {
		try {
			// 1164
			distance = 5000;
			JSONArray jsonArray = result.getJSONArray("LIST");
			int cnt = Integer.parseInt(result.get("COUNT").toString());
			for(int i = 0; i < cnt; i++) {
				// 내 거리가 한계치보다 멀면 학교로 가는 버스 도착시간 알려줌
				if (myDistance > limitedDistance && jsonArray.getJSONObject(i).get("routeId").equals("100100171") &&
						jsonArray.getJSONObject(i).get("direction").equals(direction1164Sku)) {
					double x = Double.parseDouble(jsonArray.getJSONObject(i).get("posX").toString());
					double y = Double.parseDouble(jsonArray.getJSONObject(i).get("posY").toString());
					double d = Math.sqrt(Math.pow(x - myPosX, 2) + Math.pow(y - myPosY, 2));
					if (distance > d) {
						distance = d;
						shortestStationName = jsonArray.getJSONObject(i).get("stationName").toString();
						shortestStationId = jsonArray.getJSONObject(i).get("stationId").toString();
						shortestStationSeq = jsonArray.getJSONObject(i).get("sequence").toString();
					}
					cardText1.setText("등교 버스시간");
				}
				// 내 거리가 한계치보다 가까우면 집으로 가는 버스 도착시간 알려줌
				else if (myDistance <= limitedDistance && jsonArray.getJSONObject(i).get("routeId").equals("100100171") &&
						jsonArray.getJSONObject(i).get("direction").equals(direction1164Garage)) {
					double x = Double.parseDouble(jsonArray.getJSONObject(i).get("posX").toString());
					double y = Double.parseDouble(jsonArray.getJSONObject(i).get("posY").toString());
					double d = Math.sqrt(Math.pow(x - myPosX, 2) + Math.pow(y - myPosY, 2));
					System.out.printf("%d번째 조회 : %s정거장(%f, %f)에서 내 거리는 %f입니다.\n", i, jsonArray.getJSONObject(i).get("stationName").toString(), x, y, d);
					if (distance > d) {
						distance = d;
						System.out.println("이 정류장이 우선 채택됨");
						shortestStationName = jsonArray.getJSONObject(i).get("stationName").toString();
						shortestStationId = jsonArray.getJSONObject(i).get("stationId").toString();
						shortestStationSeq = jsonArray.getJSONObject(i).get("sequence").toString();
					}
					cardText1.setText("하교 버스시간");
				}
			}
			arrive1164 = getArrive(shortestStationId, "100100171", shortestStationSeq);

			// 2115
			distance = 5000;
			String stationName = "";
			for(int i = 0; i < cnt; i++) {
				if(myDistance > limitedDistance && jsonArray.getJSONObject(i).get("routeId").equals("100100598") &&
						jsonArray.getJSONObject(i).get("direction").equals(direction2115Sku)) {
					double x = Double.parseDouble(jsonArray.getJSONObject(i).get("posX").toString());
					double y = Double.parseDouble(jsonArray.getJSONObject(i).get("posY").toString());
					double d = Math.sqrt(Math.pow(x - myPosX, 2) + Math.pow(y - myPosY, 2));
					if(distance > d) {
						distance = d;
						stationName = jsonArray.getJSONObject(i).get("stationName").toString();
						shortestStationId = jsonArray.getJSONObject(i).get("stationId").toString();
						shortestStationSeq = jsonArray.getJSONObject(i).get("sequence").toString();
					}
				}
				else if(myDistance <= limitedDistance && jsonArray.getJSONObject(i).get("routeId").equals("100100598") &&
						jsonArray.getJSONObject(i).get("direction").equals(direction2115Garage)) {
					double x = Double.parseDouble(jsonArray.getJSONObject(i).get("posX").toString());
					double y = Double.parseDouble(jsonArray.getJSONObject(i).get("posY").toString());
					double d = Math.sqrt(Math.pow(x - myPosX, 2) + Math.pow(y - myPosY, 2));
					if(distance > d) {
						distance = d;
						stationName = jsonArray.getJSONObject(i).get("stationName").toString();
						shortestStationId = jsonArray.getJSONObject(i).get("stationId").toString();
						shortestStationSeq = jsonArray.getJSONObject(i).get("sequence").toString();
					}
				}
			}
			locationText.setText(shortestStationName.concat(", " + stationName));
			arrive2115 = getArrive(shortestStationId, "100100598", shortestStationSeq);

			arrive1164Text1.setText(arrive1164.get(0));
			arrive1164Text2.setText(arrive1164.get(1));
			arrive2115Text1.setText(arrive2115.get(0));
			arrive2115Text2.setText(arrive2115.get(1));

		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}

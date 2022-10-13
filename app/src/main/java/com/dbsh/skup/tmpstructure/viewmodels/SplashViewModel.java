package com.dbsh.skup.tmpstructure.viewmodels;

import android.content.Context;

import com.dbsh.skup.tmpstructure.Service.BusService;
import com.dbsh.skup.tmpstructure.api.BusApi;
import com.dbsh.skup.tmpstructure.model.ResponseStation;
import com.dbsh.skup.tmpstructure.model.ResponseStationItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import needle.Needle;

public class SplashViewModel {

	// Data.go.kr
	final String serviceKey = "6QzxyxwgIYyrLx303Ylnud8BZMmZ4Caw%2Ble0fsW8oc9lsv0KHGZWV5jhZ%2BLgzGbESVdaYzjRmnnwdzWychAaeA%3D%3D";

	// Context
	private Context context;

	// for Json File
	int COUNT = 0;
	final String fileName = "station.json";

	// Retrofit Client
	public BusApi busApi;

	public SplashViewModel(Context context) {
		this.context = context;
	}


	public void getFile() throws IOException {
		Needle.onBackgroundThread().serially().execute(new Runnable() {
			@Override
			public void run() {
				try {
					ArrayList<ResponseStationItem> stations = getStation("100100171");  // 1164
					stations.addAll(getStation("100100598"));  // 2115
					writeFile(makeJson(stations));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private ArrayList<ResponseStationItem> getStation(String busRouteId) throws IOException {
		BusService retrofitBusClient = BusService.getInstance();
		ArrayList<ResponseStationItem> stations = new ArrayList<>();

		if(retrofitBusClient != null) {

			Map<String, String> query = new HashMap<>();
			query.put("serviceKey", serviceKey);
			query.put("busRouteId", busRouteId);

			busApi = BusService.getBusApi();

			// 파일생성전에 데이터가 다 만들어져야 하기때문에 동기식으로 call
			ResponseStation station = busApi.getStationData(query).execute().body();
			if(station.getHeader().getHeaderCd().equals("0"))
				stations = (ArrayList<ResponseStationItem>) station.getBody().getItems();
			else
				stations = null;
		}
		return stations;
	}

	private JSONObject makeJson(ArrayList<ResponseStationItem> stations) {
		JSONObject json = new JSONObject();
		JSONArray result = new JSONArray();
		try {
			for (ResponseStationItem station : stations) {
				COUNT++;
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("routeId", station.getRouteId());
				jsonObject.put("stationId", station.getStationId());
				jsonObject.put("stationName", station.getStationName());
				jsonObject.put("sequence", station.getSeq());
				jsonObject.put("posX", station.getGpsX());
				jsonObject.put("posY", station.getGpsY());
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

	private void writeFile(JSONObject json) throws IOException {
		File file = new File(context.getFilesDir(), fileName);
		if (!file.exists()) {
			file.createNewFile();
		} else {
			file.delete();
			file.createNewFile();
		}
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		bw.write(json.toString());
		bw.newLine();
		bw.close();
	}
}

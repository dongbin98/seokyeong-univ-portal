package com.dbsh.skup.viewmodels;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dbsh.skup.api.BusApi;
import com.dbsh.skup.dto.ResponseStation;
import com.dbsh.skup.dto.ResponseStationItem;
import com.dbsh.skup.client.BusClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashViewModel extends ViewModel {

	// Data.go.kr
	final String serviceKey = "6QzxyxwgIYyrLx303Ylnud8BZMmZ4Caw%2Ble0fsW8oc9lsv0KHGZWV5jhZ%2BLgzGbESVdaYzjRmnnwdzWychAaeA%3D%3D";

	// Context
	private Context context;

	// for Json File
	int COUNT1 = 0;
	int COUNT2 = 0;
	final String file1164 = "1164.json";
	final String file2115 = "2115.json";

	// LiveData
	public MutableLiveData<List<ResponseStationItem>> station1164 = new MutableLiveData<>();
	public MutableLiveData<List<ResponseStationItem>> station2115 = new MutableLiveData<>();

	public MutableLiveData<String> file1164State = new MutableLiveData<>();
	public MutableLiveData<String> file2115State = new MutableLiveData<>();

	// Retrofit Client
	public BusApi busApi;

	public SplashViewModel(Application application) {
		this.context = application.getApplicationContext();
	}

	public void getStaton1164() {
		BusClient retrofitBusClient = BusClient.getInstance();
		Map<String, String> query = new HashMap<>();
		query.put("serviceKey", serviceKey);
		query.put("busRouteId", "100100171");
		busApi = BusClient.getBusApi();
		busApi.getStationData(query).enqueue(new Callback<ResponseStation>() {
			@Override
			public void onResponse(Call<ResponseStation> call, Response<ResponseStation> response) {
				if(response.isSuccessful()) {
					if(response.body().getHeader().getHeaderCd().equals("0")) {
						station1164.setValue(response.body().getBody().getItems());
					} else {
						station1164.setValue(null);
					}
				} else {
					station1164.setValue(null);
				}
			}

			@Override
			public void onFailure(Call<ResponseStation> call, Throwable t) {
				station1164.setValue(null);
			}
		});
	}

	public void getStaton2115() {
		BusClient retrofitBusClient = BusClient.getInstance();
		Map<String, String> query = new HashMap<>();
		query.put("serviceKey", serviceKey);
		query.put("busRouteId", "100100598");
		busApi = BusClient.getBusApi();
		busApi.getStationData(query).enqueue(new Callback<ResponseStation>() {
			@Override
			public void onResponse(Call<ResponseStation> call, Response<ResponseStation> response) {
				if(response.isSuccessful()) {
					if(response.body().getHeader().getHeaderCd().equals("0")) {
						station2115.setValue(response.body().getBody().getItems());
					} else {
						station2115.setValue(null);
					}
				} else {
					station2115.setValue(null);
				}
			}

			@Override
			public void onFailure(Call<ResponseStation> call, Throwable t) {
				station2115.setValue(null);
			}
		});
	}

	public JSONObject makeJson(List<ResponseStationItem> stations, String tag) {
		JSONObject json = new JSONObject();
		JSONArray result = new JSONArray();
		COUNT1 = 0;
		COUNT2 = 0;
		try {
			for (ResponseStationItem station : stations) {
				if(tag.equals("1164"))
					COUNT1++;
				else
					COUNT2++;
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
			if(tag.equals("1164"))
				json.put("COUNT", COUNT1);
			else
				json.put("COUNT", COUNT2);
			json.put("LIST", result);
			return json;
		} catch (JSONException e) {
			if(tag.equals("1164"))
				file1164State.setValue("F");
			else if(tag.equals("2115"))
				file2115State.setValue("F");
			return null;
		}
	}

	public void write1164File(JSONObject json) throws IOException {
		File file = new File(context.getFilesDir(), file1164);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		bw.write(json.toString());
		bw.newLine();
		bw.close();
		file1164State.setValue("S");
	}

	public void write2115File(JSONObject json) throws IOException {
		File file = new File(context.getFilesDir(), file2115);
		BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
		bw.write(json.toString());
		bw.newLine();
		bw.close();
		file2115State.setValue("S");
	}
}

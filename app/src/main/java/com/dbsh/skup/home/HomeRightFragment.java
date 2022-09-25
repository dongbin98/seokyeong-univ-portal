package com.dbsh.skup.home;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.bus.Station;
import com.dbsh.skup.bus.StationTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HomeRightFragment extends Fragment {

	String stationUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute";
	final String serviceKey = "6QzxyxwgIYyrLx303Ylnud8BZMmZ4Caw%2Ble0fsW8oc9lsv0KHGZWV5jhZ%2BLgzGbESVdaYzjRmnnwdzWychAaeA%3D%3D";
	final String fileName = "station.json";
	int COUNT = 0;
	ArrayList<Station> stations;

	Button getStationBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
	    ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home_right_form, container, false);
	    stations = new ArrayList<>();

		getStationBtn = (Button) rootView.findViewById(R.id.getStationBtn);

		getStationBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				getStation();
			}
		});
        // Inflate the layout for this fragment
        return rootView;
    }

	public void getStation() {
		//serviceKey = URLEncoder.encode(serviceKey);

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
}
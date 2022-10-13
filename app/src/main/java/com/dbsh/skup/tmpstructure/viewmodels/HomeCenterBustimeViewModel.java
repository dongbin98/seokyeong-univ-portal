package com.dbsh.skup.tmpstructure.viewmodels;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dbsh.skup.tmpstructure.Service.BusService;
import com.dbsh.skup.tmpstructure.api.BusApi;
import com.dbsh.skup.tmpstructure.model.ResponseArrive;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeCenterBustimeViewModel {

    // for TextView Change
    public MutableLiveData<String> location1164 = new MutableLiveData<>();
    public MutableLiveData<String> arriveFirst1164 = new MutableLiveData<>();
    public MutableLiveData<String> arriveSecond1164 = new MutableLiveData<>();

    public MutableLiveData<String> location2115 = new MutableLiveData<>();
    public MutableLiveData<String> arriveFirst2115 = new MutableLiveData<>();
    public MutableLiveData<String> arriveSecond2115 = new MutableLiveData<>();


    // Data.go.kr
    final String serviceKey = "6QzxyxwgIYyrLx303Ylnud8BZMmZ4Caw%2Ble0fsW8oc9lsv0KHGZWV5jhZ%2BLgzGbESVdaYzjRmnnwdzWychAaeA%3D%3D";

    // Context
    private Context context;

    // Gps
    double distance = 5000.0;
    double skuPosY = 37.61601970f, skuPosX = 127.01185885f;
    final double limitedDistance = 0.005f;

    final String direction1164Sku = "서경대";            // 1164 서경대 방향
    final String direction1164Garage = "길음전철역";      // 1164 길음역 방향
    final String direction2115Sku = "서경대입구";         // 2115 서경대 방향
    final String direction2115Garage = "중랑공영차고지";   // 2115 차고지 방향

    public String shortestStationId, shortestStationSeq;

    // Retrofit Client
    public BusApi busApi;

    // for Json File
    int COUNT = 0;
    final String fileName = "station.json";

    public HomeCenterBustimeViewModel(Context context) {
        this.context = context;
        location1164.setValue("");
        location2115.setValue("");
        arriveFirst1164.setValue("");
        arriveFirst2115.setValue("");
        arriveSecond1164.setValue("");
        arriveSecond2115.setValue("");
    }

    public void getArrive(String stationId, String routeId, String seq) {
        BusService retrofitBusClient = BusService.getInstance();
        if(retrofitBusClient != null) {

            Map<String, String> arrive = new HashMap<>();
            arrive.put("serviceKey", serviceKey);
            arrive.put("stId", stationId);
            arrive.put("busRouteId", routeId);
            arrive.put("ord", seq);

            busApi = BusService.getBusApi();
            busApi.getArriveData(arrive).enqueue(new Callback<ResponseArrive>() {
                @Override
                public void onResponse(Call<ResponseArrive> call, Response<ResponseArrive> response) {
					if (response.isSuccessful()) {
						ResponseArrive responseArrive = response.body();
						System.out.println(responseArrive);
						if (responseArrive.getHeader().getHeaderCd().equals("0")) {
							System.out.println(responseArrive);
							System.out.println(responseArrive.getHeader());
							System.out.println(responseArrive.getBody());
							System.out.println(responseArrive.getBody().getItem());

							if (routeId.equals("100100171")) {  // 1164 정보
								arriveFirst1164.setValue(responseArrive.getBody().getItem().getArrMsg1());
								arriveSecond1164.setValue(responseArrive.getBody().getItem().getArrMsg2());
							} else {    // 2115 정보
								arriveFirst2115.setValue(responseArrive.getBody().getItem().getArrMsg1());
								arriveSecond2115.setValue(responseArrive.getBody().getItem().getArrMsg2());
							}
						}
					}
                }

                @Override
                public void onFailure(Call<ResponseArrive> call, Throwable t) {
                    // 통신 불가능
                    Log.d("Failure", t.getLocalizedMessage());
                }
            });
        }
	}

    public void updateBusArrive(double myGpsX, double myGpsY) {
        // 내 거리 구하기
        double myDistance = Math.sqrt(Math.pow(skuPosX - myGpsX, 2) + Math.pow(skuPosY - myGpsY, 2));   // 북악관에서 내위치까지의 거리
        System.out.println("현재 내 위치는 = " + myGpsX + ", " + myGpsY);
        System.out.println("거리는 = " + myDistance);

        // 정류장 정보 파일에서 읽어오기
        File file = new File(context.getFilesDir(), fileName);
        if(!file.exists())
            return;
        JSONObject result = new JSONObject();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuilder sb = new StringBuilder();
            String str;
            while ((str = reader.readLine()) != null) {
                sb.append(str);
            }
            reader.close();
            JSONObject stationJson = new JSONObject(sb.toString());
            result = stationJson;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        // 최인접 정류장 버스 도착시간 체크
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
					double d = Math.sqrt(Math.pow(x - myGpsX, 2) + Math.pow(y - myGpsY, 2));
					if (distance > d) {
						distance = d;
                        location1164.setValue(jsonArray.getJSONObject(i).get("stationName").toString());
						shortestStationId = jsonArray.getJSONObject(i).get("stationId").toString();
						shortestStationSeq = jsonArray.getJSONObject(i).get("sequence").toString();
					}
				}
				// 내 거리가 한계치보다 가까우면 집으로 가는 버스 도착시간 알려줌
				else if (myDistance <= limitedDistance && jsonArray.getJSONObject(i).get("routeId").equals("100100171") &&
						jsonArray.getJSONObject(i).get("direction").equals(direction1164Garage)) {
					double x = Double.parseDouble(jsonArray.getJSONObject(i).get("posX").toString());
					double y = Double.parseDouble(jsonArray.getJSONObject(i).get("posY").toString());
					double d = Math.sqrt(Math.pow(x - myGpsX, 2) + Math.pow(y - myGpsY, 2));
					//System.out.printf("%d번째 조회 : %s정거장(%f, %f)에서 내 거리는 %f입니다.\n", i, jsonArray.getJSONObject(i).get("stationName").toString(), x, y, d);
					if (distance > d) {
						distance = d;
                        location1164.setValue(jsonArray.getJSONObject(i).get("stationName").toString());
						shortestStationId = jsonArray.getJSONObject(i).get("stationId").toString();
						shortestStationSeq = jsonArray.getJSONObject(i).get("sequence").toString();
					}
				}
			}
			getArrive(shortestStationId, "100100171", shortestStationSeq);

			// 2115
			distance = 5000;
			for(int i = 0; i < cnt; i++) {
				if(myDistance > limitedDistance && jsonArray.getJSONObject(i).get("routeId").equals("100100598") &&
						jsonArray.getJSONObject(i).get("direction").equals(direction2115Sku)) {
					double x = Double.parseDouble(jsonArray.getJSONObject(i).get("posX").toString());
					double y = Double.parseDouble(jsonArray.getJSONObject(i).get("posY").toString());
					double d = Math.sqrt(Math.pow(x - myGpsX, 2) + Math.pow(y - myGpsY, 2));
					if(distance > d) {
						distance = d;
                        location2115.setValue(jsonArray.getJSONObject(i).get("stationName").toString());
						shortestStationId = jsonArray.getJSONObject(i).get("stationId").toString();
						shortestStationSeq = jsonArray.getJSONObject(i).get("sequence").toString();
					}
				}
				else if(myDistance <= limitedDistance && jsonArray.getJSONObject(i).get("routeId").equals("100100598") &&
						jsonArray.getJSONObject(i).get("direction").equals(direction2115Garage)) {
					double x = Double.parseDouble(jsonArray.getJSONObject(i).get("posX").toString());
					double y = Double.parseDouble(jsonArray.getJSONObject(i).get("posY").toString());
					double d = Math.sqrt(Math.pow(x - myGpsX, 2) + Math.pow(y - myGpsY, 2));
					if(distance > d) {
						distance = d;
                        location2115.setValue(jsonArray.getJSONObject(i).get("stationName").toString());
						shortestStationId = jsonArray.getJSONObject(i).get("stationId").toString();
						shortestStationSeq = jsonArray.getJSONObject(i).get("sequence").toString();
					}
				}
			}
			getArrive(shortestStationId, "100100598", shortestStationSeq);

		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
}

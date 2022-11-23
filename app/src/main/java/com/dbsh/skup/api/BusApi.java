package com.dbsh.skup.api;

import com.dbsh.skup.dto.ResponseArrive;
import com.dbsh.skup.dto.ResponseStation;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface BusApi {

	/* Station */
	@GET("busRouteInfo/getStaionByRoute")
	Call<ResponseStation> getStationData(@QueryMap(encoded = true) Map<String, String> query);

	/* Arrive */
	@GET("arrive/getArrInfoByRoute")
	Call<ResponseArrive> getArriveData(@QueryMap(encoded = true) Map<String, String> query);
}

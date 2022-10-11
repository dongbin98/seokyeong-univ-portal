package com.dbsh.skup.tmpstructure.api;

import com.dbsh.skup.tmpstructure.model.ResponseArrive;
import com.dbsh.skup.tmpstructure.model.ResponseStation;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface RetrofitBusAPI {

	/* Station */
	@GET("busRouteInfo/getStaionByRoute")
	Call<ResponseStation> getStationData(@QueryMap Map<String, String> query);

	/* Arrive */
	@GET("arrive/getArrInfoByRoute")
	Call<ResponseArrive> getArriveData(@QueryMap Map<String, String> query);
}

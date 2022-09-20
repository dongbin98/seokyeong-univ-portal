package com.dbsh.skup.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dbsh.skup.R;
import com.dbsh.skup.bus.Station;
import com.dbsh.skup.bus.Station1164PositionTask;
import com.dbsh.skup.bus.Station2115PositionTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class HomeCenterBustimeFragment extends Fragment {

    ArrayList<Station> bus1164;
    ArrayList<Station> bus2115;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.home_center_bustime_form, container, false);
        bus1164 = new ArrayList<>();
        bus2115 = new ArrayList<>();

        getBus();

        return rootView;
    }

    public void getBus() {
        String stationUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getStaionByRoute";
        String timeUrl = "http://ws.bus.go.kr/api/rest/arrive/getArrInfoByRoute";   // ?serviceKey=~~?stId=106000319&busRouteId=100100598&ord=1
        String serviceKey = "6QzxyxwgIYyrLx303Ylnud8BZMmZ4Caw%2Ble0fsW8oc9lsv0KHGZWV5jhZ%2BLgzGbESVdaYzjRmnnwdzWychAaeA%3D%3D";
        //serviceKey = URLEncoder.encode(serviceKey);

        String search1164 = stationUrl+"?serviceKey="+serviceKey+"&busRouteId=100100171";
        String search2115 = stationUrl+"?serviceKey="+serviceKey+"&busRouteId=100100598";

        Station1164PositionTask station1164Position = new Station1164PositionTask();
        Station2115PositionTask station2115Position = new Station2115PositionTask();
        try {
            bus1164 = station1164Position.execute(search1164).get();
            bus2115 = station2115Position.execute(search2115).get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(bus1164.size());
        System.out.println(bus2115.size());
    }
}

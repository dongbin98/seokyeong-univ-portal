package com.dbsh.skup.model;

import com.tickaroo.tikxml.annotation.PropertyElement;
import com.tickaroo.tikxml.annotation.Xml;

@Xml(name = "itemList")
public class ResponseStationItem {
    @PropertyElement(name = "busRouteId")
    String routeId;         // 노선 아이디

    @PropertyElement(name = "station")
    String stationId;       // 정류장 아이디

    @PropertyElement(name = "stationNm")
    String stationName;     // 정류장 이름

    @PropertyElement(name = "direction")
    String direction;       // 방향

    @PropertyElement(name = "seq")
    String seq;             // 정류장 순번

    @PropertyElement(name = "gpsX")
    double gpsX;            // 정류장 좌표X

    @PropertyElement(name = "gpsY")
    double gpsY;            // 정류장 좌표Y

    public String getRouteId() {
        return routeId;
    }

    public String getStationId() {
        return stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public String getDirection() {
        return direction;
    }

    public String getSeq() {
        return seq;
    }

    public double getGpsX() {
        return gpsX;
    }

    public double getGpsY() {
        return gpsY;
    }
}

package com.dbsh.skup.bus;

public class Station {

    private String routeId;         // 노선 아이디
    private String stationId;       // 정류장 아이디
    private String stationName;     // 정류장 이름
    private String direction;       // 방향
    private String seq;             // 정류장 순번
    private double posX;            // 정류장 좌표X
    private double posY;            // 정류장 좌표Y

    public Station(String routeId, String stationId, String stationName, String seq, String direction, double posX, double posY) {
        this.routeId = routeId;
        this.stationId = stationId;
        this.stationName = stationName;
        this.seq = seq;
        this.direction = direction;
        this.posX = posX;
        this.posY = posY;
    }

    public Station() {
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public String getStationId() {
        return stationId;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public double getPosX() {
        return posX;
    }

    public void setPosX(double posX) {
        this.posX = posX;
    }

    public double getPosY() {
        return posY;
    }

    public void setPosY(double posY) {
        this.posY = posY;
    }
}

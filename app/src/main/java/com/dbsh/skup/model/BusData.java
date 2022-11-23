package com.dbsh.skup.model;

public class BusData {
    private String busType;
    private String updateDate;
    private String location1164;
    private String arriveFirst1164;
    private String arriveSecond1164;
    private String location2115;
    private String arriveFirst2115;
    private String arriveSecond2115;

    public BusData() {
        this.busType = "실시간 버스";
        this.updateDate = "위치갱신필요";
        this.location1164 = "";
        this.arriveFirst1164 = "";
        this.arriveSecond1164 = "";
        this.location2115 = "";
        this.arriveFirst2115 = "";
        this.arriveSecond2115 = "";
    }

    public BusData(String busType, String updateDate, String location1164, String arriveFirst1164, String arriveSecond1164, String location2115, String arriveFirst2115, String arriveSecond2115) {
        this.busType = busType;
        this.updateDate = updateDate;
        this.location1164 = location1164;
        this.arriveFirst1164 = arriveFirst1164;
        this.arriveSecond1164 = arriveSecond1164;
        this.location2115 = location2115;
        this.arriveFirst2115 = arriveFirst2115;
        this.arriveSecond2115 = arriveSecond2115;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public String getLocation1164() {
        return location1164;
    }

    public void setLocation1164(String location1164) {
        this.location1164 = location1164;
    }

    public String getArriveFirst1164() {
        return arriveFirst1164;
    }

    public void setArriveFirst1164(String arriveFirst1164) {
        this.arriveFirst1164 = arriveFirst1164;
    }

    public String getArriveSecond1164() {
        return arriveSecond1164;
    }

    public void setArriveSecond1164(String arriveSecond1164) {
        this.arriveSecond1164 = arriveSecond1164;
    }

    public String getLocation2115() {
        return location2115;
    }

    public void setLocation2115(String location2115) {
        this.location2115 = location2115;
    }

    public String getArriveFirst2115() {
        return arriveFirst2115;
    }

    public void setArriveFirst2115(String arriveFirst2115) {
        this.arriveFirst2115 = arriveFirst2115;
    }

    public String getArriveSecond2115() {
        return arriveSecond2115;
    }

    public void setArriveSecond2115(String arriveSecond2115) {
        this.arriveSecond2115 = arriveSecond2115;
    }
}

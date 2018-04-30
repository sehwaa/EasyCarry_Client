package com.example.sehwa.easycarry_client.MainScreen.Data;

/**
 * Created by SEHWA on 2017-10-20.
 */

public class HistoryData {
    private String station_start;
    private String station_dest;
    private String requestedTime;
    private int numOfLockers;
    private int totalPrice;
    private String lockerNum;
    private String lockerPW;
    private String lockerSize;
    private Boolean isCompleted;

    public HistoryData(String station_start, String station_dest, String requestedTime, int numOfLockers, int totalPrice, String lockerNum, String lockerPW, String lockerSize, Boolean isCompleted){
        this.station_start = station_start;
        this.station_dest = station_dest;
        this.requestedTime = requestedTime;
        this.numOfLockers = numOfLockers;
        this.totalPrice = totalPrice;
        this.lockerNum = lockerNum;
        this.lockerPW = lockerPW;
        this.lockerSize = lockerSize;
        this.isCompleted  = isCompleted;

    }


    public String getStation_start(){
        return station_start;
    }

    public String getStation_dest(){
        return station_dest;
    }

    public String getRequestedTime(){
        return requestedTime;
    }

    public int getNumOfLockers(){
        return numOfLockers;
    }

    public int getTotalPrice(){
        return totalPrice;
    }

    public String getLockerNum(){
        return lockerNum;
    }

    public String getLockerPW(){
        return lockerPW;
    }

    public String getLockerSize(){
        return lockerSize;
    }

    public Boolean getIsCompleted(){
        return isCompleted;
    }
}

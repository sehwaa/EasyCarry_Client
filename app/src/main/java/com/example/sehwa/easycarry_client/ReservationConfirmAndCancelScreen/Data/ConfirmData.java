package com.example.sehwa.easycarry_client.ReservationConfirmAndCancelScreen.Data;

/**
 * Created by SEHWA on 2017-10-20.
 */

public class ConfirmData {

    private String station_start;
    private String station_dest;
    private String requestedTime;
    private int numOfLockers;
    private int totalPrice;
    private String lockerNum;
    private String lockerPW;
    private String lockerSize;
    private String status;
    private String locker_id;

    public ConfirmData(String station_start, String station_dest, String requestedTime, int numOfLockers, int totalPrice, String lockerNum, String lockerPW, String lockerSize, String status, String locker_id){
        this.station_start = station_start;
        this.station_dest = station_dest;
        this.requestedTime = requestedTime;
        this.numOfLockers = numOfLockers;
        this.totalPrice = totalPrice;
        this.lockerNum = lockerNum;
        this.lockerPW = lockerPW;
        this.lockerSize = lockerSize;
        this.status = status;
        this.locker_id = locker_id;
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

    public String getStatus(){
        return status;
    }

    public String getLocker_id(){
        return locker_id;
    }
}

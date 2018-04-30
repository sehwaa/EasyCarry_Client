package model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by SEHWA on 2017-10-22.
 */

public class LockerInfo {
    @SerializedName("lockerNum")
    private String lockerNum;
    @SerializedName("lockerPW")
    private String lockerPW;
    @SerializedName("lockerSize")
    private String lockerSize;

    public LockerInfo(String lockerNum, String lockerPW, String lockerSize){
        this.lockerNum = lockerNum;
        this.lockerPW = lockerPW;
        this.lockerSize = lockerSize;
    }

    public String getLockerNum() {
        return lockerNum;
    }

    public String getLockerPW() {
        return lockerPW;
    }

    public String getLockerSize() {
        return lockerSize;
    }
}

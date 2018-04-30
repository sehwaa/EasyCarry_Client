package model.Request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEHWA on 2017-09-23.
 */

public class Lockers {
    @SerializedName("lockers")
    @Expose
    List<LockerInfo> lockers = new ArrayList<LockerInfo>();

    public Lockers(List<LockerInfo> lockers){
        this.lockers = lockers;
    }

}

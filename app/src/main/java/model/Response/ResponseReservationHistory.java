package model.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEHWA on 2017-09-23.
 */

public class ResponseReservationHistory {

    private String _id;
    private String station_start;
    private String station_dest;
    private String requestedTime;
    private int totalPrice;
    private int numOfLockers;
    private Boolean isCompleted;
    private String created_at;
    private String precaution;

    @SerializedName("lockers")
    private List<Lockers> lockers = new ArrayList<Lockers>();


    public String get_id(){
        return _id;
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

    public int getTotalPrice(){
        return totalPrice;
    }

    public int getNumOfLockers(){
        return numOfLockers;
    }

    public boolean getisCompleted(){
        return isCompleted;
    }

    public String getCreated_at(){
        return created_at;
    }

    public String getPrecaution(){
        return precaution;
    }


    public class Lockers{
        protected String _id;
        protected String lockerNum;
        protected String lockerSize;
        protected String lockerPW;
        protected String status;

        public String get_id(){
            return _id;
        }

        public String getLockerNum(){
            return lockerNum;
        }

        public String getLockerSize(){
            return lockerSize;
        }

        public String getLockerPW(){
            return lockerPW;
        }

        public String getStatus(){
            return status;
        }
    }


}

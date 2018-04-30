package com.example.sehwa.easycarry_client.MainScreen.Item;

/**
 * Created by gig46 on 2017-08-22.
 */

public class ListViewItem {

    private String dateStr ;
    private String startstationStr ;
    private String arrivalstationStr;
    private String moneyStr;


    public void setDate(String date) {
        dateStr = date ;
    }
    public void setStartstation(String startstation) {
        startstationStr = startstation ;
    }

    public void setArrivalstation (String arrivalstation) {
        arrivalstationStr = arrivalstation ;
    }

    public void setMoney (String money) {
        moneyStr = money ;
    }


    public String getDate() {
        return this.dateStr ;
    }
    public String getStartstation() {
        return this.startstationStr ;
    }
    public String getArrivalstation() {
        return this.arrivalstationStr ;
    }
    public String getMoney() {
        return this.moneyStr ;
    }




}

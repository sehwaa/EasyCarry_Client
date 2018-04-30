package com.example.sehwa.easycarry_client.MainScreen.Data;

/**
 * Created by SEHWA on 2017-10-21.
 */

public class ChatData {
    public String name;
    public String message;
    public String time;
    public String firebaseKey; // Firebase Realtime Database 에 등록된 Key 값
    public String userEmail; // 사용자 이메일주소
//    private int profileimg;

    public ChatData(){

    }

    public ChatData(String name, String message){
        this.name = name;
        this.message = message;
    }

    public String getname(){
        return name;
    }

    public void setname(String name){
        this.name = name;
    }

    public String getMessage(){
        return message;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    //    public int getProfileimg(){
//        return profileimg;
//    }
//
//    public void setProfileimg(int profileimg){
//        this.profileimg = profileimg;
//    }
}

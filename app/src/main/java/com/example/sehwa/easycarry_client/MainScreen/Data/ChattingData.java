package com.example.sehwa.easycarry_client.MainScreen.Data;

/**
 * Created by SEHWA on 2017-10-22.
 */

public class ChattingData {

    private String name;
    private String chatmessage;

    public ChattingData(String name, String chatmessage) {
        this.name = name;
        this.chatmessage = chatmessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChatmessage() {
        return chatmessage;
    }

    public void setChatmessage(String chatmessage) {
        this.chatmessage = chatmessage;
    }
}

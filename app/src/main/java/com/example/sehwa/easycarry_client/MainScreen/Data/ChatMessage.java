package com.example.sehwa.easycarry_client.MainScreen.Data;

public class ChatMessage {
    public boolean left;
    public String message;
    public String time;
    public String firebaseKey;
    public String name;

    public ChatMessage(boolean left, String message, String time, String firebaseKey, String name) {
        super();
        this.left = left;
        this.message = message;
        this.time = time;
        this.firebaseKey = firebaseKey;
        this.name = name;
    }
}
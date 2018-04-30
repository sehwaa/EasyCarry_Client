package com.example.sehwa.easycarry_client.FirstScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sehwa.easycarry_client.MainScreen.NaviMainActivity;
import com.example.sehwa.easycarry_client.R;
import com.example.sehwa.easycarry_client.SignUpScreen.SignUpActivity;

import connect.ApplicationController;
import model.Request.RequestSignIn;
import model.Response.ResponseSignIn;
import networkservice.NetworkServiceInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable(){
            @Override
            public void run() {
                try{
                    Thread.sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);

                finish();
            }
        }).start();
    }
}

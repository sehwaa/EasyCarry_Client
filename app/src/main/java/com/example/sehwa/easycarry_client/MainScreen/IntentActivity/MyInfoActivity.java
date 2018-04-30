package com.example.sehwa.easycarry_client.MainScreen.IntentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sehwa.easycarry_client.InformationModifyScreen.InformationModifyActivity;
import com.example.sehwa.easycarry_client.MainScreen.NaviMainActivity;
import com.example.sehwa.easycarry_client.R;

import networkservice.NetworkServiceInterface;

/**
 * Created by SEHWA on 2017-10-20.
 */

public class MyInfoActivity extends AppCompatActivity {

    public TextView nameTxt;
    private TextView emailTxt;
    public TextView phoneNoTxt;
    private Button changeBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_info_fragment);

        init();
        setTextView();
        click();

    }

    private void init(){
        nameTxt = (TextView) findViewById(R.id.nameTxt);
        emailTxt = (TextView) findViewById(R.id.mail2);
        phoneNoTxt = (TextView) findViewById(R.id.phone2);
        changeBtn = (Button) findViewById(R.id.changeBtn);
    }

    private void setTextView(){
        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);

        Intent intent = getIntent();

        if(intent.getStringExtra("username") == null && intent.getStringExtra("phoneNo") == null){
            nameTxt.setText(preferences.getString("username",""));
            emailTxt.setText(preferences.getString("email", ""));
            phoneNoTxt.setText(preferences.getString("phoneNo", ""));
        }else{
            nameTxt.setText(intent.getStringExtra("username"));
            phoneNoTxt.setText(intent.getStringExtra("phoneNo"));
            emailTxt.setText(preferences.getString("email", ""));
        }
    }

    private void click(){
        changeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyInfoActivity.this, InformationModifyActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(MyInfoActivity.this, NaviMainActivity.class);
        startActivity(intent);
        finish();
    }
}

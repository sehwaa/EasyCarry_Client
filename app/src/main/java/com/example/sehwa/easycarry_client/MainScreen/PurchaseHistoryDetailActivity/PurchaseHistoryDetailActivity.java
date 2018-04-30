package com.example.sehwa.easycarry_client.MainScreen.PurchaseHistoryDetailActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sehwa.easycarry_client.R;

/**
 * Created by SEHWA on 2017-10-20.
 */

public class PurchaseHistoryDetailActivity extends AppCompatActivity {

    private String station_start;
    private String station_dest;
    private String requestedTime;
    private int numOfLockers;
    private int totalPrice;
    private String lockerNum;
    private String lockerPW;
    private String lockerSize;
    private String status;
    private Boolean isCompleted;

    private TextView station_start_txt;
    private TextView station_dest_txt;
    private TextView username_txt;
    private TextView userPhoneNum_txt;
    private TextView requestedTime_txt;
    private TextView numOfLockers_txt;
    private TextView lockerSize_txt;
    private TextView lockerNum_txt;
    private TextView lockerPW_txt;
    private TextView totalPrice_txt;
    private TextView isCompleted_txt;
    private Button okBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchasehistory_detail);

        getIntentResult();
        init();
        setTextView();
        click();
    }

    private void getIntentResult(){
        Intent intent2 = getIntent();
        station_start = intent2.getStringExtra("station_start");
        station_dest = intent2.getStringExtra("station_dest");
        requestedTime = intent2.getStringExtra("requestedTime");
        numOfLockers = intent2.getIntExtra("numOfLockers", 1);
        totalPrice = intent2.getIntExtra("totalPrice", 0);
        lockerNum = intent2.getStringExtra("lockerNum");
        lockerPW = intent2.getStringExtra("lockerPW");
        lockerSize = intent2.getStringExtra("lockerSize");
        status = intent2.getStringExtra("status");
        isCompleted = intent2.getBooleanExtra("isCompleted", false);
        setResult(RESULT_OK, intent2);
    }

    private void init(){
        station_start_txt = (TextView) findViewById(R.id.station_start);
        station_dest_txt = (TextView) findViewById(R.id.station_dest);
        username_txt = (TextView)findViewById(R.id.reservationnameTxt);
        userPhoneNum_txt = (TextView)findViewById(R.id.reservationphoneTxt);
        requestedTime_txt = (TextView)findViewById(R.id.requestedTimeTxt);
        numOfLockers_txt = (TextView)findViewById(R.id.numOfLockersTxt);
        lockerSize_txt = (TextView)findViewById(R.id.lockersizeTxt);
        lockerNum_txt = (TextView)findViewById(R.id.lockernumTxt);
        lockerPW_txt = (TextView)findViewById(R.id.lockerpwTxt);
        totalPrice_txt = (TextView)findViewById(R.id.paymentTxt);
        isCompleted_txt = (TextView)findViewById(R.id.isCompletedTxt);
        okBtn = (Button)findViewById(R.id.okBtn);
    }

    private void setTextView(){
        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String username = preferences.getString("username", "");
        String userPhoneNum = preferences.getString("phoneNo", "");
        username_txt.setText(username);
        userPhoneNum_txt.setText(userPhoneNum);
        station_start_txt.setText(station_start);
        station_dest_txt.setText(station_dest);
        requestedTime_txt.setText(requestedTime);
        String numOfLockerstemp = String.valueOf(numOfLockers);
        numOfLockers_txt.setText(numOfLockerstemp);
        lockerSize_txt.setText(lockerSize);
        lockerNum_txt.setText(lockerNum);
        lockerPW_txt.setText(lockerPW);
        String totalPricetemp = String.valueOf(totalPrice);
        totalPrice_txt.setText(totalPricetemp);
        if(isCompleted == true){
            isCompleted_txt.setText("정상 처리 완료");
        }else{
            isCompleted_txt.setText("비정상 처리");
        }
    }

    private void click(){
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}

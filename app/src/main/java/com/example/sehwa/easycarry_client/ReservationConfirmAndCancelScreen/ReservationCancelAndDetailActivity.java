package com.example.sehwa.easycarry_client.ReservationConfirmAndCancelScreen;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sehwa.easycarry_client.FirstScreen.MainActivity;
import com.example.sehwa.easycarry_client.MainScreen.NaviMainActivity;
import com.example.sehwa.easycarry_client.R;

import connect.ApplicationController;
import networkservice.NetworkServiceInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SEHWA on 2017-09-21.
 */

public class ReservationCancelAndDetailActivity extends AppCompatActivity {

    private String station_start;
    private String station_dest;
    private String requestedTime;
    private int numOfLockers;
    private String lockerSize;
    private String lockerNum;
    private String lockerPW;
    private int totalPrice;
    private String status;
    private String order_id;

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
    private TextView status_txt;

    private Button okBtn;
    private Button cancelBtn;

    private NetworkServiceInterface networkServiceInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservationcancelcheck_detail);

        getIntentResult();
        init();
        setTextView();
        click();

    }

    private void getIntentResult(){
        Intent intent = getIntent();
        station_start = intent.getStringExtra("station_start");
        station_dest = intent.getStringExtra("station_dest");
        requestedTime = intent.getStringExtra("requestedTime");
        numOfLockers = intent.getIntExtra("numOfLockers", 1);
        totalPrice = intent.getIntExtra("totalPrice", 0);
        lockerNum = intent.getStringExtra("lockerNum");
        lockerPW = intent.getStringExtra("lockerPW");
        lockerSize = intent.getStringExtra("lockerSize");
        status = intent.getStringExtra("status");
        order_id = intent.getStringExtra("order_id");
        System.out.println(order_id);
        setResult(RESULT_OK, intent);
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
        status_txt = (TextView)findViewById(R.id.statusTxt);

        okBtn = (Button)findViewById(R.id.reservationcheckBtn);
        cancelBtn = (Button)findViewById(R.id.reservationcancelBtn);

        networkServiceInterface = ApplicationController.getInstance().getNetworkServiceInterface();
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
        status_txt.setText(status);
    }

    private void click(){
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ReservationCancelAndDetailActivity.this);
                builder.setTitle("예약 취소")
                        .setMessage("예약을 취소 하시겠습니까?")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                cancelnetwork();
                            }
                        });

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void cancelnetwork(){
        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String cookie = preferences.getString("Cookie", "");

        Call<ResponseBody> cancelCall = networkServiceInterface.cancelCall(cookie, order_id);
        cancelCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println(response.code());
                System.out.println(response.message());
                if(response.code() == 200){
                    Toast.makeText(getApplicationContext(), "예약 취소가 정상적으로 처리되었습니다!", Toast.LENGTH_SHORT).show();
                    onBackPressed();
                }else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), "로그인 해주세요!", Toast.LENGTH_SHORT).show();
                }else if(response.code() == 404){
                    Toast.makeText(getApplicationContext(), "이미 취소된 항목입니다!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "네트워크 오류!", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(ReservationCancelAndDetailActivity.this, ConfirmActivity.class);
        startActivity(intent);
        finish();
    }
}

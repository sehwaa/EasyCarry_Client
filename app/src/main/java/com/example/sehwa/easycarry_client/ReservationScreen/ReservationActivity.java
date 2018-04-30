package com.example.sehwa.easycarry_client.ReservationScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sehwa.easycarry_client.R;

import org.json.JSONArray;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import connect.ApplicationController;
import model.Request.LockerInfo;
import model.Request.Lockers;
import model.Response.ResponseReservation;
import networkservice.NetworkServiceInterface;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SEHWA on 2017-09-21.
 */

public class ReservationActivity extends AppCompatActivity {

    private TextView startstationTxt;
    private TextView endstationTxt;
    private TextView reservationnameTxt;
    private TextView reservationphoneTxt;
    private Spinner hourSpinner;
    private Spinner minuteSpinner;
    private RadioButton lockerCount1;
    private RadioButton lockerCount2;
    private RadioButton lockerCount3;
    private TextView paymentTxt;
    private Button reservationBtn;
    private TextView startlineTxt;
    private TextView endlineTxt;
    private EditText precautionEt;

    ArrayAdapter<String> hourspinnerAdapter = null;
    ArrayAdapter<String> minutespinnerAdapter = null;

    private Spinner lineSpinner;
    private Spinner stationSpinner;

    ArrayAdapter<String> linespinnerAdapter = null;

    ArrayAdapter<String> stationspinnerAdapter_1 = null;
    ArrayAdapter<String> stationspinnerAdapter_2 = null;
    ArrayAdapter<String> stationspinnerAdapter_3 = null;
    ArrayAdapter<String> stationspinnerAdapter_4 = null;
    ArrayAdapter<String> stationspinnerAdapter_5 = null;
    ArrayAdapter<String> stationspinnerAdapter_6 = null;
    ArrayAdapter<String> stationspinnerAdapter_7 = null;
    ArrayAdapter<String> stationspinnerAdapter_8 = null;
    ArrayAdapter<String> stationspinnerAdapter_9 = null;

    private Button selectstartstationBtn;
    private Button selectdestinationBtn;

    private String hour;
    private String minute;

    private String startstation;
    private String destination;

    private String email;
    private String username;
    private String phoneNo;
    private int totalprice;

    private int numOfLockers;
    private long pressedTime;

    private String lockernum[];
    private String lockerpwd[];
    private String lockersize[];

    private String precaution;
    private Uri lockerPhotos[];
    private String requestedTime;
    private String cookie;

    NetworkServiceInterface networkServiceInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        init();
        click();

    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String success = preferences.getString("Success", "");
        if(success.equals("ok")){
            email = preferences.getString("email", "");
            username = preferences.getString("username", "");
            phoneNo = preferences.getString("phoneNo", "");
            reservationnameTxt.setText(username);
            reservationphoneTxt.setText(phoneNo);
            cookie = preferences.getString("Cookie", "");
        }
    }

    private void init(){

        startstationTxt = (TextView) findViewById(R.id.startstationTxt);
        endstationTxt = (TextView) findViewById(R.id.endstationTxt);
        reservationnameTxt = (TextView)findViewById(R.id.reservationnameTxt);
        reservationphoneTxt = (TextView)findViewById(R.id.reservationphoneTxt);
        hourSpinner = (Spinner)findViewById(R.id.hourSpinner);
        minuteSpinner = (Spinner) findViewById(R.id.minuteSpinner);
        lockerCount1 = (RadioButton)findViewById(R.id.lockerCount1);
        lockerCount2 = (RadioButton)findViewById(R.id.lockerCount2);
        lockerCount3 = (RadioButton)findViewById(R.id.lockerCount3);
        paymentTxt = (TextView)findViewById(R.id.paymentTxt);
        reservationBtn = (Button)findViewById(R.id.reservationBtn);
        startlineTxt = (TextView)findViewById(R.id.startlineTxt);
        endlineTxt = (TextView)findViewById(R.id.endlineTxt);
        precautionEt = (EditText)findViewById(R.id.precautionEt);

        hourspinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item,(String[])getResources().getStringArray(R.array.hour));
        minutespinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(R.array.minute));

        linespinnerAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(R.array.line));
        stationspinnerAdapter_1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(R.array.dark_blue_line));
        stationspinnerAdapter_2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(R.array.green_line));
        stationspinnerAdapter_3 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(R.array.orange_line));
        stationspinnerAdapter_4 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(R.array.blue_line));
        stationspinnerAdapter_5 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(R.array.purple_line));
        stationspinnerAdapter_6 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(R.array.dark_orange_line));
        stationspinnerAdapter_7 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(R.array.dark_green_line));
        stationspinnerAdapter_8 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(R.array.pink_line));
        stationspinnerAdapter_9 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, (String[])getResources().getStringArray(R.array.gold_line));

        hourSpinner.setAdapter(hourspinnerAdapter);
        minuteSpinner.setAdapter(minutespinnerAdapter);

        username = reservationnameTxt.getText().toString();
        phoneNo = reservationphoneTxt.getText().toString();
        if(precautionEt.getText().toString().equals("")){
            precaution = "";
        }else{
            precaution = precautionEt.getText().toString();
        }
        totalprice = Integer.parseInt(paymentTxt.getText().toString());

        lockernum = new String[3];
        lockerpwd = new String[3];
        lockersize = new String[3];

        lockerPhotos = new Uri[3];
        networkServiceInterface = ApplicationController.getInstance().getNetworkServiceInterface();
    }

    private void click(){

        hourSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hour = hourSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        minuteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                minute = minuteSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        startstationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = getLayoutInflater();

                final View dialogview = layoutInflater.inflate(R.layout.select_station_dialog, null);

                lineSpinner = (Spinner) dialogview.findViewById(R.id.selectlineSpinner);
                lineSpinner.setAdapter(linespinnerAdapter);

                stationSpinner = (Spinner)dialogview.findViewById(R.id.selectstationSpinner);

                selectstartstationBtn = (Button) dialogview.findViewById(R.id.selectstartstationBtn);

                lineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case 0:
                                stationSpinner.setAdapter(stationspinnerAdapter_1);
                                break;
                            case 1:
                                stationSpinner.setAdapter(stationspinnerAdapter_2);
                                break;
                            case 2:
                                stationSpinner.setAdapter(stationspinnerAdapter_3);
                                break;
                            case 3:
                                stationSpinner.setAdapter(stationspinnerAdapter_4);
                                break;
                            case 4:
                                stationSpinner.setAdapter(stationspinnerAdapter_5);
                                break;
                            case 5:
                                stationSpinner.setAdapter(stationspinnerAdapter_6);
                                break;
                            case 6:
                                stationSpinner.setAdapter(stationspinnerAdapter_7);
                                break;
                            case 7:
                                stationSpinner.setAdapter(stationspinnerAdapter_8);
                                break;
                            case 8:
                                stationSpinner.setAdapter(stationspinnerAdapter_9);
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
                builder.setView(dialogview);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                selectstartstationBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startstationTxt.setText(stationSpinner.getSelectedItem().toString());
                        startlineTxt.setText(lineSpinner.getSelectedItem().toString());

                        startstation = startstationTxt.getText().toString();
                        Toast.makeText(getApplicationContext(), startstation, Toast.LENGTH_SHORT).show();
                        alertDialog.dismiss();
                    }
                });
            }
        });

        endstationTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater layoutInflater = getLayoutInflater();

                final View dialogview = layoutInflater.inflate(R.layout.select_destination_dialog, null);

                lineSpinner = (Spinner) dialogview.findViewById(R.id.selectlineSpinner);
                lineSpinner.setAdapter(linespinnerAdapter);

                stationSpinner = (Spinner)dialogview.findViewById(R.id.selectstationSpinner);

                selectdestinationBtn = (Button) dialogview.findViewById(R.id.selectstartstationBtn);

                lineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case 0:
                                stationSpinner.setAdapter(stationspinnerAdapter_1);
                                break;
                            case 1:
                                stationSpinner.setAdapter(stationspinnerAdapter_2);
                                break;
                            case 2:
                                stationSpinner.setAdapter(stationspinnerAdapter_3);
                                break;
                            case 3:
                                stationSpinner.setAdapter(stationspinnerAdapter_4);
                                break;
                            case 4:
                                stationSpinner.setAdapter(stationspinnerAdapter_5);
                                break;
                            case 5:
                                stationSpinner.setAdapter(stationspinnerAdapter_6);
                                break;
                            case 6:
                                stationSpinner.setAdapter(stationspinnerAdapter_7);
                                break;
                            case 7:
                                stationSpinner.setAdapter(stationspinnerAdapter_8);
                                break;
                            case 8:
                                stationSpinner.setAdapter(stationspinnerAdapter_9);
                                break;
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(ReservationActivity.this);
                builder.setView(dialogview);

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();

                selectdestinationBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        endstationTxt.setText(stationSpinner.getSelectedItem().toString());
                        endlineTxt.setText(lineSpinner.getSelectedItem().toString());
                        destination = endstationTxt.getText().toString();
                        alertDialog.dismiss();
                    }
                });
            }
        });

        lockerCount1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numOfLockers = 1;
                Intent intent = new Intent(ReservationActivity.this, EnterOneLockerInformationActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        lockerCount2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numOfLockers = 2;
                Intent intent = new Intent(ReservationActivity.this, EnterTwoLockerInformationActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        lockerCount3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numOfLockers = 3;
                Intent intent = new Intent(ReservationActivity.this, EnterThreeLockerInformationActivity.class);
                startActivityForResult(intent, 1);
            }
        });


        reservationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(numOfLockers == 1){

                    long now = System.currentTimeMillis();

                    Date date = new Date(now);
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                    String getTime = sdf.format(date);
                    //System.out.println(hour.length());
                    String t = "T";
                    String temp = ":";
                    String Z = ":00Z";

                    requestedTime = getTime + t + hour + temp + minute + Z;

                    File file = new File(lockerPhotos[0].getPath());
                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("lockerPhotos", file.getName(), requestFile);

                    LockerInfo lockerInfo = new LockerInfo(lockernum[0], lockerpwd[0], lockersize[0]);
                    List<LockerInfo> lockerInfoList = new ArrayList<LockerInfo>();
                    lockerInfoList.add(0, lockerInfo);
                    Lockers lockers = new Lockers(lockerInfoList);

                    if(startstationTxt.getText().toString().equals(endstationTxt.getText().toString())){
                        Toast.makeText(getApplicationContext(), "출발역 / 도착역을 확인해주세요", Toast.LENGTH_SHORT).show();
                    }else{
                        Call<ResponseReservation> sendReservation = networkServiceInterface.getReservationId(cookie,startstation, destination, requestedTime, totalprice, numOfLockers, lockers, email,username, precaution, body);
                        sendReservation.enqueue(new Callback<ResponseReservation>() {
                            @Override
                            public void onResponse(Call<ResponseReservation> call, Response<ResponseReservation> response) {
                                System.out.println(response.code());
                                System.out.println(response.message());
                                if(response.code() == 200){
                                    Toast.makeText(getApplicationContext(), "예약이 정상적으로 처리 되었습니다!", Toast.LENGTH_SHORT).show();
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), "빠짐없이 입력해주세요!", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseReservation> call, Throwable t) {

                            }
                        });

//                    }
//                } else if(numOfLockers == 2){
//                    File file = new File(lockerPhotos[0].getPath());
//                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
//                    File file1 = new File(lockerPhotos[1].getPath());
//                    RequestBody requestFile1 = RequestBody.create(MediaType.parse("multipart/form-data"), file1);
//                    MultipartBody.Part body =
//                            MultipartBody.Part.createFormData("lockerPhotos", file.getName(), requestFile);
//                    MultipartBody.Part body1 =
//                            MultipartBody.Part.createFormData("lockerPhotos", file1.getName(), requestFile1);
//
//                    List<LockerInfo> lockerInfoList = new ArrayList<LockerInfo>();
//
//                    for(int i = 0 ; i < 2; i++){
//                        LockerInfo lockerInfo = new LockerInfo(lockernum[i], lockerpwd[i], lockersize[i]);
//                        lockerInfoList.add(i, lockerInfo);
//                    }
//
//                    Lockers lockers = new Lockers(lockerInfoList);
//
//                    if(startstationTxt.getText().toString().equals(endstationTxt.getText().toString())){
//                        Toast.makeText(getApplicationContext(), "출발역 / 도착역을 확인해주세요", Toast.LENGTH_SHORT).show();
//                    }else{
//                        Call<ResponseReservation> sendReservation = networkServiceInterface.getReservationId(cookie,startstation,destination,requestedTime,totalprice,numOfLockers, lockers, email, username, precaution , body);
//                        sendReservation.enqueue(new Callback<ResponseReservation>() {
//                            @Override
//                            public void onResponse(Call<ResponseReservation> call, Response<ResponseReservation> response) {
//                                System.out.println(response.code());
//                                System.out.println(response.message());
//                                if(response.code() == 200){
//                                    Toast.makeText(getApplicationContext(), "예약이 정상적으로 처리 되었습니다!", Toast.LENGTH_SHORT).show();
//                                    finish();
//                                }else{
//                                    Toast.makeText(getApplicationContext(), "빠짐없이 입력해주세요!", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                            @Override
//                            public void onFailure(Call<ResponseReservation> call, Throwable t) {
//
//                            }
//                        });

                    }
                }
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK) // 액티비티가 정상적으로 종료되었을 경우
        {
            if(requestCode==1) // InformationInput에서 호출한 경우에만 처리합니다.
            {               // 받아온 이름과 전화번호를 InformationInput 액티비티에 표시합니다.

                if(numOfLockers == 1){
                    lockernum[0] = data.getStringExtra("lockerNum");
                    lockersize[0] = data.getStringExtra("lockerSize");
                    lockerpwd[0] = data.getStringExtra("lockerPW");
                    lockerPhotos[0] = data.getParcelableExtra("lockerPhoto");
                    if(data.getStringExtra("lockerSize").equals("S")){
                        paymentTxt.setText("8000");
                    }else if(data.getStringExtra("lockerSize").equals("M")){
                        paymentTxt.setText("12000");
                    }else{
                        paymentTxt.setText("16000");
                    }
                }else if(numOfLockers == 2){
                    lockernum[0] = data.getStringExtra("lockerNum0");
                    lockersize[0] = data.getStringExtra("lockerSize0");
                    lockerpwd[0] = data.getStringExtra("lockerPw0");
                    lockerPhotos[0] = data.getParcelableExtra("lockerPhoto0");
                    lockernum[1] = data.getStringExtra("lockerNum1");
                    lockersize[1] = data.getStringExtra("lockerSize1");
                    lockerpwd[1] = data.getStringExtra("lockerPw1");
                    lockerPhotos[1] = data.getParcelableExtra("lockerPhoto1");
                }else if(numOfLockers == 3){
                    lockernum[0] = data.getStringExtra("lockerNum0");
                    lockersize[0] = data.getStringExtra("lockerSize0");
                    lockerpwd[0] = data.getStringExtra("lockerPw0");
                    lockerPhotos[0] = data.getParcelableExtra("lockerPhoto0");
                    lockernum[1] = data.getStringExtra("lockerNum1");
                    lockersize[1] = data.getStringExtra("lockerSize1");
                    lockerpwd[1] = data.getStringExtra("lockerPw1");
                    lockerPhotos[1] = data.getParcelableExtra("lockerPhoto1");
                    lockernum[2] = data.getStringExtra("lockerNum2");
                    lockersize[2] = data.getStringExtra("lockerSize2");
                    lockerpwd[2] = data.getStringExtra("lockerPw2");
                    lockerPhotos[2] = data.getParcelableExtra("lockerPhoto2");
                }

            }
        }
    }
}

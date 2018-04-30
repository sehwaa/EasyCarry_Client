package com.example.sehwa.easycarry_client.ReservationConfirmAndCancelScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sehwa.easycarry_client.MainScreen.NaviMainActivity;
import com.example.sehwa.easycarry_client.R;
import com.example.sehwa.easycarry_client.ReservationConfirmAndCancelScreen.Adapter.IconTextListAdapter;
import com.example.sehwa.easycarry_client.ReservationConfirmAndCancelScreen.Data.ConfirmData;
import com.example.sehwa.easycarry_client.ReservationConfirmAndCancelScreen.Item.IconTextItem;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import connect.ApplicationController;
import model.Response.ResponseReservationCheck;
import networkservice.NetworkServiceInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SEHWA on 2017-09-21.
 */

public class ConfirmActivity extends AppCompatActivity {

    //리스트뷰
    private ListView listView1;

    //리스트 어댑터
    public IconTextListAdapter adapter;

    NetworkServiceInterface networkServiceInterface;

    private ArrayList<ConfirmData> confirmDataArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservationcancelcheck);

        init();
        click();
        reservation_check_network();

    }

    private void init(){
        listView1 = (ListView) findViewById(R.id.listView1);
        adapter = new IconTextListAdapter(this);

        networkServiceInterface = ApplicationController.getInstance().getNetworkServiceInterface();

        confirmDataArrayList = new ArrayList<ConfirmData>();
    }

    private void click(){

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendConfirmData(position);
            }

        });
    }

    private void reservation_check_network(){
        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String cookie = preferences.getString("Cookie", "");

        Call<List<ResponseReservationCheck>> getResponseReservationCheck = networkServiceInterface.reservationCheckCall(cookie);
        getResponseReservationCheck.enqueue(new Callback<List<ResponseReservationCheck>>() {
            @Override
            public void onResponse(Call<List<ResponseReservationCheck>> call, Response<List<ResponseReservationCheck>> response) {
                System.out.println(response.code());
                System.out.println(response.message());

                if(response.code() == 200){

                    Gson gson = new Gson();
                    String response_result = gson.toJson(response.body());


                    try {
                        JSONArray jsonArray = new JSONArray(response_result);
                        int count = 0;
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject obj = jsonArray.getJSONObject(i);
                            JSONArray ja = new JSONObject(obj.toString()).getJSONArray("lockers");

                            System.out.println(ja);

                            if(ja.length() == 1){
                                JSONObject jaobj = ja.getJSONObject(0);
                                //응답받는 변수
                                String requestedTime;
                                String parserequestedTime;
                                String station_start;
                                String station_dest;
                                int totalPrice;
                                int numOfLockers;
                                String lockerNum;
                                String lockerSize;
                                String lockerPW;
                                String order_id;
                                Boolean isCompleted;
                                String created_at;
                                String locker_id;
                                String status;

                                order_id = obj.getString("_id");
                                station_start = obj.getString("station_start");
                                station_dest = obj.getString("station_dest");
                                requestedTime = obj.getString("requestedTime");

                                String temp = requestedTime.replace("T", " ");
                                parserequestedTime = temp.replace("Z", "");

                                totalPrice = obj.getInt("totalPrice");
                                numOfLockers = obj.getInt("numOfLockers");
                                created_at = obj.getString("created_at");

                                String createdTemp = created_at.replace("T", " ");
                                String parsecreated_at = createdTemp.replace("Z", "");

                                locker_id = jaobj.getString("_id");
                                lockerNum = jaobj.getString("lockerNum");
                                lockerPW = jaobj.getString("lockerPW");
                                lockerSize = jaobj.getString("lockerSize");
                                status = jaobj.getString("status");

                                adapter.addItem(new IconTextItem(parsecreated_at, station_start, station_dest, totalPrice+"원"));
                                listView1.setAdapter(adapter);

                                ConfirmData confirmData = new ConfirmData(station_start, station_dest, parserequestedTime, numOfLockers,totalPrice, lockerNum, lockerPW, lockerSize, status, order_id);
                                confirmDataArrayList.add(count, confirmData);
                                count++;

                            }else{
                                continue;
                            }

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(getApplicationContext(), "알 수 없는 오류!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ResponseReservationCheck>> call, Throwable t) {
                System.out.println("응답 실패");
                Toast.makeText(getApplicationContext(), "네트워크 오류!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void sendConfirmData(int position){

        String requestedTime = confirmDataArrayList.get(position).getRequestedTime();
        String station_start = confirmDataArrayList.get(position).getStation_start();
        String station_dest = confirmDataArrayList.get(position).getStation_dest();
        int totalPrice = confirmDataArrayList.get(position).getTotalPrice();
        int numOfLockers = confirmDataArrayList.get(position).getNumOfLockers();
        String lockerNum = confirmDataArrayList.get(position).getLockerNum();
        String lockerSize = confirmDataArrayList.get(position).getLockerSize();
        String lockerPW = confirmDataArrayList.get(position).getLockerPW();
        String order_id = confirmDataArrayList.get(position).getLocker_id();
        String status = confirmDataArrayList.get(position).getStatus();

        Intent intent = new Intent(ConfirmActivity.this, ReservationCancelAndDetailActivity.class);
        intent.putExtra("station_start", station_start);
        intent.putExtra("station_dest", station_dest);
        intent.putExtra("requestedTime", requestedTime);
        intent.putExtra("numOfLockers", numOfLockers);
        intent.putExtra("totalPrice", totalPrice);
        intent.putExtra("lockerNum", lockerNum);
        intent.putExtra("lockerPW", lockerPW);
        intent.putExtra("lockerSize", lockerSize);
        intent.putExtra("status", status);
        intent.putExtra("order_id", order_id);

        startActivityForResult(intent, 1);
        finish();
    }
}

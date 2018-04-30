package com.example.sehwa.easycarry_client.MainScreen.IntentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sehwa.easycarry_client.MainScreen.Adapter.ListViewAdapter;
import com.example.sehwa.easycarry_client.MainScreen.Data.HistoryData;
import com.example.sehwa.easycarry_client.MainScreen.NaviMainActivity;
import com.example.sehwa.easycarry_client.MainScreen.PurchaseHistoryDetailActivity.PurchaseHistoryDetailActivity;
import com.example.sehwa.easycarry_client.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import connect.ApplicationController;
import model.Response.ResponseReservationHistory;
import networkservice.NetworkServiceInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SEHWA on 2017-10-20.
 */

public class PurchaseHistoryActivity extends AppCompatActivity{

    ListViewAdapter adapter ;

    ListView listView2;

    private NetworkServiceInterface networkServiceInterface;

    private ArrayList<HistoryData> historyDataArrayList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.purchase_history_fragment);

        init();
        click();
        historynetwork();

    }

    private void init(){
        networkServiceInterface = ApplicationController.getInstance().getNetworkServiceInterface();
        adapter = new ListViewAdapter(this) ;
        listView2 = (ListView)findViewById(R.id.listView2);
        historyDataArrayList = new ArrayList<HistoryData>();
    }

    private void click(){
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sendHistoryData(position);
            }
        });

    }

    private void historynetwork(){
        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String cookie = preferences.getString("Cookie", "");

        Call<List<ResponseReservationHistory>> historyCall = networkServiceInterface.reservationHistoryCall(cookie);
        historyCall.enqueue(new Callback<List<ResponseReservationHistory>>() {
            @Override
            public void onResponse(Call<List<ResponseReservationHistory>> call, Response<List<ResponseReservationHistory>> response) {
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

                            if(ja.length() == 1){
                                JSONObject jaobj = ja.getJSONObject(0);

                                String requestedTime;
                                String station_start;
                                String station_dest;
                                int totalPrice;
                                int numOfLockers;
                                String lockerNum;
                                String lockerSize;
                                String lockerPW;
                                String parserequestedTime;
                                Boolean isCompleted;
                                String created_at;


                                station_start = obj.getString("station_start");
                                station_dest = obj.getString("station_dest");
                                requestedTime = obj.getString("requestedTime");

                                String temp = requestedTime.replace("T", " ");
                                parserequestedTime = temp.replace("Z", "");

                                totalPrice = obj.getInt("totalPrice");
                                numOfLockers = obj.getInt("numOfLockers");
                                isCompleted = obj.getBoolean("isCompleted");
                                created_at = obj.getString("created_at");

                                String createdTemp = created_at.replace("T", " ");
                                String parsecreated_at = createdTemp.replace("Z", "");

                                lockerNum = jaobj.getString("lockerNum");
                                lockerPW = jaobj.getString("lockerPW");
                                lockerSize = jaobj.getString("lockerSize");

                                adapter.addItem(parsecreated_at, station_start, station_dest, String.valueOf(totalPrice));
                                listView2.setAdapter(adapter);

                                HistoryData historyData = new HistoryData(station_start, station_dest, parserequestedTime,numOfLockers,totalPrice,lockerNum,lockerPW,lockerSize,isCompleted);
                                historyDataArrayList.add(count, historyData);
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
            public void onFailure(Call<List<ResponseReservationHistory>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "네트워크 오류!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendHistoryData(int position){

        String station_start = historyDataArrayList.get(position).getStation_start();
        String station_dest = historyDataArrayList.get(position).getStation_dest();
        String requestedTime = historyDataArrayList.get(position).getRequestedTime();
        int numOfLockers = historyDataArrayList.get(position).getNumOfLockers();
        int totalPrice = historyDataArrayList.get(position).getTotalPrice();
        String lockerNum = historyDataArrayList.get(position).getLockerNum();
        String lockerPW = historyDataArrayList.get(position).getLockerPW();
        String lockerSize = historyDataArrayList.get(position).getLockerSize();
        Boolean isCompleted = historyDataArrayList.get(position).getIsCompleted();

        Intent intent2 = new Intent(PurchaseHistoryActivity.this, PurchaseHistoryDetailActivity.class);
        intent2.putExtra("station_start", station_start);
        intent2.putExtra("station_dest", station_dest);
        intent2.putExtra("requestedTime", requestedTime);
        intent2.putExtra("numOfLockers", numOfLockers);
        intent2.putExtra("totalPrice", totalPrice);
        intent2.putExtra("lockerNum", lockerNum);
        intent2.putExtra("lockerPW", lockerPW);
        intent2.putExtra("lockerSize", lockerSize);
        intent2.putExtra("isCompleted", isCompleted);

        startActivityForResult(intent2, 1);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(PurchaseHistoryActivity.this, NaviMainActivity.class);
        startActivity(intent);
        finish();
    }
}

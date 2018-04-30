package com.example.sehwa.easycarry_client.MainScreen.MainFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sehwa.easycarry_client.MainScreen.Adapter.ListViewAdapter;
import com.example.sehwa.easycarry_client.MainScreen.IntentActivity.MyInfoActivity;
import com.example.sehwa.easycarry_client.MainScreen.IntentActivity.PurchaseHistoryActivity;
import com.example.sehwa.easycarry_client.MainScreen.PurchaseHistoryDetailActivity.PurchaseHistoryDetailActivity;
import com.example.sehwa.easycarry_client.R;
import com.example.sehwa.easycarry_client.ReservationConfirmAndCancelScreen.ReservationCancelAndDetailActivity;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import connect.ApplicationController;
import model.Response.ResponseReservationHistory;
import networkservice.NetworkServiceInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SEHWA on 2017-09-21.
 */

public class PurchaseHistoryFragment extends Fragment {
    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getActivity(), PurchaseHistoryActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    //
//    ListViewAdapter adapter ;
//
//    ListView listView2;
//
//    private String requestedTime;
//    private String station_start;
//    private String station_dest;
//    private int totalPrice;
//    private int numOfLockers;
//    private String lockerNum;
//    private String lockerSize;
//    private String lockerPW;
//    private String precaution;
//    private String parserequestedTime;
//    private Boolean isCompleted;
//    private String created_at;
//    private String status;
//
//    private NetworkServiceInterface networkServiceInterface;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.purchase_history_fragment, container, false);
//
//        networkServiceInterface = ApplicationController.getInstance().getNetworkServiceInterface();
//
//        adapter = new ListViewAdapter(this) ;
//        listView2 = (ListView)view.findViewById(R.id.listView2);
//
//        historynetwork();
//
//        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent intent2 = new Intent(getActivity(), PurchaseHistoryDetailActivity.class);
//                intent2.putExtra("station_start", station_start);
//                intent2.putExtra("station_dest", station_dest);
//                intent2.putExtra("requestedTime", parserequestedTime);
//                intent2.putExtra("numOfLockers", numOfLockers);
//                intent2.putExtra("totalPrice", totalPrice);
//                intent2.putExtra("lockerNum", lockerNum);
//                intent2.putExtra("lockerPW", lockerPW);
//                intent2.putExtra("lockerSize", lockerSize);
//                intent2.putExtra("isCompleted", isCompleted);
//
//                startActivityForResult(intent2, 1);
//            }
//        });
//
//        return view;
//    }
//
//    private void historynetwork(){
//        SharedPreferences preferences = getActivity().getSharedPreferences("pref", MODE_PRIVATE);
//        String cookie = preferences.getString("Cookie", "");
//
//        Call<List<ResponseReservationHistory>> historyCall = networkServiceInterface.reservationHistoryCall(cookie);
//        historyCall.enqueue(new Callback<List<ResponseReservationHistory>>() {
//            @Override
//            public void onResponse(Call<List<ResponseReservationHistory>> call, Response<List<ResponseReservationHistory>> response) {
//                System.out.println(response.code());
//                System.out.println(response.message());
//
//                if(response.code() == 200){
//
//                    Gson gson = new Gson();
//                    String response_result = gson.toJson(response.body());
//
//
//                    try {
//                        JSONArray jsonArray = new JSONArray(response_result);
//                        for (int i = 0; i < jsonArray.length(); i++) {
//                            JSONObject obj = jsonArray.getJSONObject(i);
//                            JSONArray ja = new JSONObject(obj.toString()).getJSONArray("lockers");
//
//                            if(ja.length() == 1){
//                                JSONObject jaobj = ja.getJSONObject(0);
//
//                                station_start = obj.getString("station_start");
//                                station_dest = obj.getString("station_dest");
//                                requestedTime = obj.getString("requestedTime");
//
//                                String temp = requestedTime.replace("T", " ");
//                                parserequestedTime = temp.replace("Z", "");
//
//                                totalPrice = obj.getInt("totalPrice");
//                                numOfLockers = obj.getInt("numOfLockers");
//                                isCompleted = obj.getBoolean("isCompleted");
//                                created_at = obj.getString("created_at");
//
//                                String createdTemp = created_at.replace("T", " ");
//                                String parsecreated_at = createdTemp.replace("Z", "");
//
//                                precaution = obj.getString("precaution");
//
//                                lockerNum = jaobj.getString("lockerNum");
//                                lockerPW = jaobj.getString("lockerPW");
//                                lockerSize = jaobj.getString("lockerSize");
//                                status = jaobj.getString("status");
//
//                                adapter.addItem(parsecreated_at, station_start, station_dest, String.valueOf(totalPrice));
//                                listView2.setAdapter(adapter);
//
//                            }else{
//                                continue;
//                            }
//
//                        }
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                }else{
//                    Toast.makeText(getContext(), "알 수 없는 오류!", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<ResponseReservationHistory>> call, Throwable t) {
//                Toast.makeText(getContext(), "네트워크 오류!", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}

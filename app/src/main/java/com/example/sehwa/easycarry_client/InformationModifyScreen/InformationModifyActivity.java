package com.example.sehwa.easycarry_client.InformationModifyScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sehwa.easycarry_client.FirstScreen.LoginActivity;
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
 * Created by SEHWA on 2017-10-20.
 */

public class InformationModifyActivity extends AppCompatActivity {

    private EditText nameModifyEt;
    private EditText phoneNoModifyEt;
    private Button okBtn;

    private String username;
    private String phoneNo;

    private NetworkServiceInterface networkServiceInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info_modify);

        init();
        click();

    }

    private void init(){
        nameModifyEt = (EditText) findViewById(R.id.namemodify);
        phoneNoModifyEt = (EditText) findViewById(R.id.phonemodify);
        okBtn = (Button) findViewById(R.id.okBtn);
        networkServiceInterface = ApplicationController.getInstance().getNetworkServiceInterface();
    }

    private Boolean check(){
        if(nameModifyEt.getText().toString() == null || phoneNoModifyEt.getText().toString() == null){
            return false;
        }else{
            username = nameModifyEt.getText().toString();
            phoneNo = phoneNoModifyEt.getText().toString();
            return true;
        }
    }

    private void click(){
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean checknull = check();
                if(checknull == true){
                    modifynetwork();
                }else{
                    Toast.makeText(getApplicationContext(), "빠짐없이 입력해주세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void modifynetwork(){
        final SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String cookie = preferences.getString("Cookie", "");

        Call<ResponseBody> modifyCall = networkServiceInterface.modifyCall(cookie, username, phoneNo);
        modifyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200){
                    Toast.makeText(getApplicationContext(), "회원정보가 정상적으로 수정되었습니다", Toast.LENGTH_SHORT).show();
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("username");
                    editor.putString("username", username);
                    editor.remove("phoneNo");
                    editor.putString("phoneNo", phoneNo);
                    editor.commit();

                    Intent intent = new Intent(InformationModifyActivity.this, NaviMainActivity.class);
                    intent.putExtra("username", username);
                    intent.putExtra("phoneNo", phoneNo);
                    startActivity(intent);
                    finish();

                }else if(response.code() == 400){
                    Toast.makeText(getApplicationContext(), "빠짐없이 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), "로그인 후 시도하세요", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(InformationModifyActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "네트워크 오류!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

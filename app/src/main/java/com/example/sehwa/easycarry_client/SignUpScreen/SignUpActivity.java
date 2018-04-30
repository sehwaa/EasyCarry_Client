package com.example.sehwa.easycarry_client.SignUpScreen;

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
import model.Request.RequestSignIn;
import model.Request.RequestSignUp;
import model.Response.ResponseSignIn;
import networkservice.NetworkServiceInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SEHWA on 2017-10-05.
 */

public class SignUpActivity extends AppCompatActivity {

    private EditText firstnameEt;
    private EditText lastnameEt;
    private EditText emailEt;
    private EditText phonenumEt;
    private EditText pwdEt;
    private EditText copwdEt;

    private Button signupBtn;

    private NetworkServiceInterface networkServiceInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        init();
        click();

    }

    //초기화
    private void init(){
        firstnameEt = (EditText) findViewById(R.id.firstnameEt);
        lastnameEt = (EditText) findViewById(R.id.lastnameEt);
        emailEt = (EditText) findViewById(R.id.emailEt);
        phonenumEt = (EditText) findViewById(R.id.phonenumEt);
        pwdEt = (EditText) findViewById(R.id.pwdEt);
        copwdEt = (EditText) findViewById(R.id.copwdEt);
        signupBtn = (Button) findViewById(R.id.signupBtn);

        //네트워크 초기화
        networkServiceInterface = ApplicationController.getInstance().getNetworkServiceInterface();
    }

    //EditText String으로 바꿔주는 함수
    private String changeString(EditText editText){
        String parseString = editText.getText().toString();
        return parseString;
    }

    //클릭 이벤트
    private void click(){
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(emailEt == null || phonenumEt == null || pwdEt == null || firstnameEt == null || lastnameEt == null){
                    Toast.makeText(getApplicationContext(), "모두 입력해주세요", Toast.LENGTH_SHORT).show();
                }else{
                    String email = changeString(emailEt);
                    String phoneNo = changeString(phonenumEt);
                    String password = changeString(pwdEt);
                    String username = changeString(firstnameEt) + changeString(lastnameEt);
                    signupnetwork(email, username, phoneNo, password);
                }
            }
        });
    }

    //요청을 보내고 응답 받는 함수(회원가입)
    private void signupnetwork(String email, String username, String phoneNo, String password){

        RequestSignUp requestSignUp = new RequestSignUp(email, username, phoneNo, password);
        Call<ResponseBody> getrequestSignUp = networkServiceInterface.signupCall(requestSignUp);
        getrequestSignUp.enqueue(new Callback<ResponseBody>() {

            //응답 성공
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                System.out.println(response.code());
                System.out.println(response.message());

                if(response.code() == 201){
                    Toast.makeText(getApplicationContext(), "회원가입 성공!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    finish();
                }else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), "모두 입력해주세요", Toast.LENGTH_SHORT).show();
                }else if(response.code() == 409){
                    Toast.makeText(getApplicationContext(), "이미 있는 이메일입니다.", Toast.LENGTH_SHORT).show();
                }
            }

            //응답 실패
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

//        RequestSignIn requestSignIn = new RequestSignIn(email, password);
//        Call<ResponseSignIn> getResponseSignIn = networkServiceInterface.signInCall(requestSignIn);
//        getResponseSignIn.enqueue(new Callback<ResponseSignIn>() {
//
//            //응답 성공
//            @Override
//            public void onResponse(Call<ResponseSignIn> call, Response<ResponseSignIn> response) {
//                System.out.println(response.code());
//                System.out.println(response.message());
//
//                //이메일 가져오기
//                String email = response.body().getEmail();
//                //이름 가져오기
//                String username = response.body().getUsername();
//                //전화번호 가져오기
//                String phoneNo = response.body().getPhoneNo();
//
//                System.out.println(email);
//                System.out.println(username);
//                System.out.println(phoneNo);
//
//                //쿠키 값 저장
//                String cookie = response.headers().get("Set-Cookie");
//
//                SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.putString("email", email);
//                editor.putString("username", username);
//                editor.putString("phoneNo", phoneNo);
//                editor.putString("Cookie", cookie);
//                editor.putString("Success", "ok");
//                editor.commit();
//
//                Toast.makeText(getApplicationContext(), "로그인 되었습니다!", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(MainActivity.this, NaviMainActivity.class);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onFailure(Call<ResponseSignIn> call, Throwable t) {
//                System.out.println("네트워크 오류");
//
//                Toast.makeText(getApplicationContext(), "네트워크 오류", Toast.LENGTH_SHORT).show();
//            }
//        });
    }
}

package com.example.sehwa.easycarry_client.FirstScreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by SEHWA on 2017-10-25.
 */

public class LoginActivity extends AppCompatActivity {

    private Button signinBtn; //로그인 버튼

    private EditText emailEt; //이메일 입력
    private EditText pwdEt; //패스워드 입력

    private TextView signupTxt; //회원가입 창으로 이동

    private NetworkServiceInterface networkServiceInterface;

    private long pressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //초기화
        init();
        click();
    }

    //초기화 함수
    private void init(){

        //리소스 초기화
        emailEt = (EditText)findViewById(R.id.emailEt);
        pwdEt = (EditText) findViewById(R.id.pwdEt);
        signinBtn = (Button) findViewById(R.id.signinBtn);
        signupTxt = (TextView) findViewById(R.id.signupTxt);

        //네트워크 초기화
        networkServiceInterface = ApplicationController.getInstance().getNetworkServiceInterface();
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        String success = preferences.getString("Success", "");
        if(success.equals("ok")){
            String email = preferences.getString("email", "");
            String password = preferences.getString("password", "");

            //signinnetwork(email, password);
            Intent intent = new Intent(LoginActivity.this, NaviMainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    //EditText String으로 바꿔주는 함수
    private String changeString(EditText editText){
        String parseString = editText.getText().toString();
        return parseString;
    }

    public void click(){

        //로그인 버튼 클릭
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = changeString(emailEt);
                String password = changeString(pwdEt);

                signinnetwork(email, password);
            }
        });

        signupTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    //요청을 보내고 응답 받는 함수(로그인)
    private void signinnetwork(String email, final String password){
        RequestSignIn requestSignIn = new RequestSignIn(email, password);
        Call<ResponseSignIn> getResponseSignIn = networkServiceInterface.signInCall(requestSignIn);
        getResponseSignIn.enqueue(new Callback<ResponseSignIn>() {

            //응답 성공
            @Override
            public void onResponse(Call<ResponseSignIn> call, Response<ResponseSignIn> response) {
                System.out.println(response.code());
                System.out.println(response.message());

                if(response.code() == 200){
                    //이메일 가져오기
                    String email = response.body().getEmail();
                    //이름 가져오기
                    String username = response.body().getUsername();
                    //전화번호 가져오기
                    String phoneNo = response.body().getPhoneNo();

                    System.out.println(email);
                    System.out.println(username);
                    System.out.println(phoneNo);

                    //쿠키 값 저장
                    String cookie = response.headers().get("Set-Cookie");

                    SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("email", email);
                    editor.putString("username", username);
                    editor.putString("phoneNo", phoneNo);
                    editor.putString("password", password);
                    editor.putString("Cookie", cookie);
                    editor.putString("Success", "ok");
                    editor.commit();

                    Toast.makeText(getApplicationContext(), "로그인 되었습니다!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, NaviMainActivity.class);
                    startActivity(intent);
                    finish();
                }else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), "올바르지 않은 정보입니다", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "알 수 없는 오류!", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onFailure(Call<ResponseSignIn> call, Throwable t) {
                System.out.println("네트워크 오류");

                Toast.makeText(getApplicationContext(), "네트워크 오류", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //뒤로가기
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (pressedTime == 0) {
            Toast.makeText(LoginActivity.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        } else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if (seconds > 2000) {
                Toast.makeText(LoginActivity.this, " 한 번 더 누르면 종료됩니다.", Toast.LENGTH_LONG).show();
                pressedTime = 0;
            } else {
                super.onBackPressed();
                finish(); // app 종료 시키기
            }
        }
    }
}

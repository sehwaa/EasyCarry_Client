package com.example.sehwa.easycarry_client.MainScreen;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sehwa.easycarry_client.FirstScreen.LoginActivity;
import com.example.sehwa.easycarry_client.FirstScreen.MainActivity;
import com.example.sehwa.easycarry_client.MainScreen.MainFragment.ChatFragment;
import com.example.sehwa.easycarry_client.MainScreen.MainFragment.MainMenuFragment;
import com.example.sehwa.easycarry_client.MainScreen.MainFragment.MyInfoFragment;
import com.example.sehwa.easycarry_client.MainScreen.MainFragment.PurchaseHistoryFragment;
import com.example.sehwa.easycarry_client.MainScreen.MainFragment.SettingFragment;
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

public class NaviMainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public TextView nameTxt;
    public TextView emailTxt;

    private long pressedTime;

    private NetworkServiceInterface networkServiceInterface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("       ");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        displaySelectedScreen(R.id.nav_main);

        View nav_header_view = navigationView.getHeaderView(0);

        nameTxt = (TextView)nav_header_view.findViewById(R.id.nameTxt);
        emailTxt = (TextView)nav_header_view.findViewById(R.id.emailTxt);

        SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
        nameTxt.setText(pref.getString("username",""));
        emailTxt.setText(pref.getString("email",""));

        networkServiceInterface = ApplicationController.getInstance().getNetworkServiceInterface();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
        }

        if ( pressedTime == 0 ) {
            Toast.makeText(NaviMainActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if ( seconds > 2000 ) {
                Toast.makeText(NaviMainActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
                pressedTime = 0 ;
            }
            else {
                super.onBackPressed();
                SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
                String cookie = preferences.getString("Cookie", "");
                //logoutnetwork(cookie);
                finish(); // app 종료 시키기
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        displaySelectedScreen(item.getItemId());
        return true;
    }

    private void displaySelectedScreen(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_main:
                fragment = new MainMenuFragment();
                break;
            case R.id.nav_info:
                fragment = new MyInfoFragment();
                break;
            case R.id.nav_purchase_history:
                fragment = new PurchaseHistoryFragment();
                break;
            case R.id.nav_setting:
                fragment = new SettingFragment();
                break;
            case R.id.nav_logout:
                AlertDialog.Builder builder = new AlertDialog.Builder(NaviMainActivity.this);
                builder.setTitle("로그아웃")
                        .setMessage("로그아웃 하시겠습니까?")
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                SharedPreferences pref = getSharedPreferences("pref", Activity.MODE_PRIVATE);
                                String cookie = pref.getString("Cookie", "");
                                //logoutnetwork(cookie);
                                SharedPreferences.Editor editor = pref.edit(); // Editor를 불러옵니다.
                                editor.clear();
                                editor.commit();
                                Toast.makeText(getApplicationContext(), "로그아웃 되었습니다", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(NaviMainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });

                final AlertDialog alertDialog = builder.create();
                alertDialog.show();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void logoutnetwork(String cookie){
        Call<ResponseBody> logout = networkServiceInterface.signoutCall(cookie);
        logout.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 204){
                }else if(response.code() == 401){
                    Toast.makeText(getApplicationContext(), "로그인이 되지 않은 상태입니다!" , Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "네트워크 오류!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

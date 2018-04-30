package com.example.sehwa.easycarry_client.MainScreen.MainFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.sehwa.easycarry_client.InformationModifyScreen.InformationModifyActivity;
import com.example.sehwa.easycarry_client.MainScreen.IntentActivity.MyInfoActivity;
import com.example.sehwa.easycarry_client.R;

import java.util.List;

import model.Response.ResponseReservationCheck;
import networkservice.NetworkServiceInterface;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by SEHWA on 2017-09-21.
 */

public class MyInfoFragment extends Fragment {

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getActivity(), MyInfoActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

}

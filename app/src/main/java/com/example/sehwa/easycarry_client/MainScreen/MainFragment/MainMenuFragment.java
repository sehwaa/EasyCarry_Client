package com.example.sehwa.easycarry_client.MainScreen.MainFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.sehwa.easycarry_client.MainScreen.IntentActivity.ChatActivity;
import com.example.sehwa.easycarry_client.MainScreen.IntentActivity.ChatListActivity;
import com.example.sehwa.easycarry_client.R;
import com.example.sehwa.easycarry_client.ReservationConfirmAndCancelScreen.ConfirmActivity;
import com.example.sehwa.easycarry_client.ReservationScreen.ReservationActivity;

/**
 * Created by SEHWA on 2017-09-21.
 */

public class MainMenuFragment extends Fragment{

    private LinearLayout reservationLayout;
    private LinearLayout confirmreservationLayout;
    private LinearLayout chatLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.main_fragment, container, false);

        reservationLayout = (LinearLayout)view.findViewById(R.id.reservationLayout);
        confirmreservationLayout = (LinearLayout)view.findViewById(R.id.confirmreservation);
        chatLayout = (LinearLayout)view.findViewById(R.id.chat);

        reservationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReservationActivity.class);
                startActivity(intent);
            }
        });

        confirmreservationLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ConfirmActivity.class);
                startActivity(intent);
            }
        });

//        chatLayout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ChatListActivity.class);
//                startActivity(intent);
//            }
//        });

        return view;
    }
}

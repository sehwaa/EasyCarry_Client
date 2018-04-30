package com.example.sehwa.easycarry_client.MainScreen.MainFragment;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.sehwa.easycarry_client.MainScreen.IntentActivity.ChatActivity;

/**
 * Created by SEHWA on 2017-09-21.
 */

public class ChatFragment extends Fragment {
    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getActivity(), ChatActivity.class);
        startActivity(intent);
        getActivity().finish();
    }
}

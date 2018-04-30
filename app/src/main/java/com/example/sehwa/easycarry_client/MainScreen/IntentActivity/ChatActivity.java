package com.example.sehwa.easycarry_client.MainScreen.IntentActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sehwa.easycarry_client.MainScreen.Adapter.ChatMessageAdapter;
import com.example.sehwa.easycarry_client.MainScreen.Adapter.ChattingAdapter;
import com.example.sehwa.easycarry_client.MainScreen.Data.ChatData;
import com.example.sehwa.easycarry_client.MainScreen.Data.ChatMessage;
import com.example.sehwa.easycarry_client.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by SEHWA on 2017-10-20.
 */

public class ChatActivity extends AppCompatActivity {
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    private final SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat("a h:mm", Locale.getDefault());
    private static final String TAG = "ChatActivity";
    private ChatMessageAdapter chatArrayAdapter;
    private ListView listView;
    private EditText enterchatText;
    private Button sendBtn;
    private Button exitBtn;
    private String name;

    private boolean side = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        setContentView(R.layout.activity_chat);

        init();
        click();

        initFirebaseDatabase();

        chatArrayAdapter = new ChatMessageAdapter(getApplicationContext(), R.layout.activity_chat_singlemessage);
        listView.setAdapter(chatArrayAdapter);

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }

    private void init(){
        sendBtn = (Button) findViewById(R.id.sendBtn);
        enterchatText = (EditText) findViewById(R.id.chatText);
        exitBtn = (Button)findViewById(R.id.exitBtn);
        listView = (ListView) findViewById(R.id.listView1);
        SharedPreferences preferences = getSharedPreferences("pref", MODE_PRIVATE);
        name = preferences.getString("username", "");
    }

    private void click(){
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatData chatData = new ChatData(name, enterchatText.getText().toString());
                chatData.time = mSimpleDateFormat.format(System.currentTimeMillis());

                mDatabaseReference.push().setValue(chatData);

                enterchatText.setText("");
            }
        });
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "될까?", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initFirebaseDatabase() {
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference("message");
        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChatData chatData = dataSnapshot.getValue(ChatData.class);
                chatData.firebaseKey = dataSnapshot.getKey();
                chatArrayAdapter.add(new ChatMessage(side, chatData.message, chatData.time, chatData.firebaseKey, chatData.name));
                chatArrayAdapter.setName(chatData.name);
                listView.smoothScrollToPosition(chatArrayAdapter.getCount());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String firebaseKey = dataSnapshot.getKey();
                int count = chatArrayAdapter.getCount();
                for (int i = 0; i < count; i++) {
                    if (chatArrayAdapter.getItem(i).firebaseKey.equals(firebaseKey)) {
                        chatArrayAdapter.remove(chatArrayAdapter.getItem(i));
                        break;
                    }
                }
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }

}

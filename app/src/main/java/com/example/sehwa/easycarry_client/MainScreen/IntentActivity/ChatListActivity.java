package com.example.sehwa.easycarry_client.MainScreen.IntentActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sehwa.easycarry_client.R;

import java.util.ArrayList;


public class ChatListActivity extends AppCompatActivity{
    private ListView lv;
    private MyAdapter myAdapter;
    private ArrayList<listItem> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chatting_list);

        AdapterView.OnItemClickListener chatListClick = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChatListActivity.this,ChatActivity.class);
                startActivity(intent);
            }
        };

        //listItem 클래스 : ListView의 아이템에 본인이 넣고자 하는 데이터들의 묶음
        list = new ArrayList<listItem>(); //ArrayList로 생성
        lv = (ListView) findViewById(R.id.lv); //listview이 아이디 값을 받아온다
        myAdapter = new MyAdapter(list); //Adapter 생성
        lv.setAdapter(myAdapter); // Adapter에 붙인다.

        //아이템 추가
        list.add(new listItem(R.drawable.nuri,"정용화", "뭐해?"));
        list.add(new listItem(R.drawable.nuri,"이브와가끝나길바래요", "우리 대상이래,,, 여행가자!"));
        list.add(new listItem(R.drawable.nuri, "망톡방", "끌끌"));

        //리스트뷰에 리스너 붙이기
        lv.setOnItemClickListener(chatListClick);
    }

    //ListView의 아이템에 들어가는 커스텀된 데이터들의 묶음
    public class listItem{
        private int profile;
        private String name; // String 카카오톡 대화 목록의 이름
        private String chat; // String 마지막 대화
        //매개변수가 있는 생성자로 바다와 값을 전달한다.
        public listItem(int profile, String name, String chat){
            this.profile = profile;
            this.name = name;
            this.chat = chat;
        }
    }

    //Adapter
    public class MyAdapter extends BaseAdapter{
        private ArrayList<listItem> list;

        public MyAdapter(ArrayList<listItem> list){
            //Adapter 생성시 list값을 받는다.
            this.list=list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public  listItem getItem(int position){
            //현재 position에 따른 list의 값을 반환 시켜준다.
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View converView, ViewGroup parent)
        {
            final int pos=position;

            View v = converView;

            if(v == null) {
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.list_item,parent,false);

                ImageView profile = (ImageView)v.findViewById(R.id.profile_image);
                TextView name = (TextView)v.findViewById(R.id.name);
                TextView chat = (TextView)v.findViewById(R.id.chat);

                profile.setImageResource(getItem(pos).profile);
                name.setText(getItem(pos).name);
                chat.setText(getItem(pos).chat);
            }
            return v;
        }
    }
}

package com.example.sehwa.easycarry_client.MainScreen.Adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sehwa.easycarry_client.MainScreen.Data.ChatData;
import com.example.sehwa.easycarry_client.MainScreen.IntentActivity.ChatActivity;
import com.example.sehwa.easycarry_client.MainScreen.IntentActivity.PurchaseHistoryActivity;
import com.example.sehwa.easycarry_client.MainScreen.Item.ListViewItem;
import com.example.sehwa.easycarry_client.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SEHWA on 2017-10-21.
 */

public class ChattingAdapter extends BaseAdapter {

    private ArrayList<ChatData> listViewItemList = new ArrayList<ChatData>() ;

    public ChattingAdapter(ChatActivity chatActivity) {

    }

    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.chatadapter, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득

        ImageView imageView = (ImageView) convertView.findViewById(R.id.profile_image);
        TextView carrymanName = (TextView) convertView.findViewById(R.id.carrymanName);
        TextView message = (TextView) convertView.findViewById(R.id.chatText);

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        ChatData chatData = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영

        carrymanName.setText(chatData.getname());
        message.setText(chatData.getMessage());

        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String carrymanName, String message) {
        ChatData chatData = new ChatData();

        chatData.setname(carrymanName);
        chatData.setMessage(message);

        listViewItemList.add(chatData);
    }


}

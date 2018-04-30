package com.example.sehwa.easycarry_client.MainScreen.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sehwa.easycarry_client.MainScreen.Data.ChatMessage;
import com.example.sehwa.easycarry_client.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.sehwa.easycarry_client.R.id.txt_time;

public class ChatMessageAdapter extends ArrayAdapter {
    private TextView chatText;
    private TextView time;
    private List chatMessageList = new ArrayList();
    private LinearLayout singleMessageContainer;
    private ChatMessage object;
    private String name_ck;

    // @Override
    public void add(ChatMessage object) {
        this.object = object;
        chatMessageList.add(object);
        super.add(object);
    }

    public ChatMessageAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public void setName(String name) {
        name_ck = name;
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    public ChatMessage getItem(int index) {
        return (ChatMessage) this.chatMessageList.get(index);
    }

    @Override
    public int getItemViewType(int position) {
        String name = getItem(position).name;
//        if(!name.isEmpty() && name_ck.equals(name)) return 0;
//        else return 1;
        return 1;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_chat_singlemessage, parent, false);
        }
        singleMessageContainer = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
        ChatMessage chatMessageObj = getItem(position);
        chatMessageObj.left = getItemViewType(position) == 1 ? true : false;

        if(chatMessageObj.left) {
            LinearLayout ml = (LinearLayout) row.findViewById(R.id.singleMessageContainer);
            TextView t = (TextView) row.findViewById(txt_time);
            ml.removeView(t);

            TextView text = new TextView(row.getContext());

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);;
            lp.gravity = Gravity.BOTTOM;
            text.setLayoutParams(lp);
            text.setTextColor(Color.DKGRAY);
            text.setTextSize(12);
            text.setId(txt_time);

            ml.addView(text);
        }

        chatText = (TextView) row.findViewById(R.id.singleMessage);
        time = (TextView) row.findViewById(txt_time);

        time.setText(chatMessageObj.time);
        chatText.setText(chatMessageObj.message);
        chatText.setBackgroundResource(chatMessageObj.left ? R.drawable.bubble_a : R.drawable.bubble_b);
        singleMessageContainer.setGravity(chatMessageObj.left ? Gravity.LEFT : Gravity.RIGHT);

        return row;
    }

    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

}
package com.example.sehwa.easycarry_client.ReservationConfirmAndCancelScreen.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.sehwa.easycarry_client.R;
import com.example.sehwa.easycarry_client.ReservationConfirmAndCancelScreen.Item.IconTextItem;

/**
 * Created by SEHWA on 2017-09-21.
 */

public class IconTextView extends LinearLayout {

    /**
     * TextView 01
     */
    private TextView mText01;

    /**
     * TextView 02
     */
    private TextView mText02;

    /**
     * TextView 03
     */
    private TextView mText03;

    private TextView mText04;

    public IconTextView(Context context, IconTextItem aItem) {
        super(context);

        // Layout Inflation
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.reservationcancelcheck_item, this, true);


        // Set Text 01
        mText01 = (TextView) findViewById(R.id.dataItem01);
        mText01.setText(aItem.getData(0));

        // Set Text 02
        mText02 = (TextView) findViewById(R.id.dataItem02);
        mText02.setText(aItem.getData(1));

        // Set Text 03
        mText03 = (TextView) findViewById(R.id.dataItem03);
        mText03.setText(aItem.getData(2));


        mText04 = (TextView) findViewById(R.id.dataItem04);
        mText04.setText(aItem.getData(3));

    }

    /**
     * set Text
     *
     * @param index
     * @param data
     */
    public void setText(int index, String data) {
        if (index == 0) {
            mText01.setText(data);
        } else if (index == 1) {
            mText02.setText(data);
        } else if (index == 2) {
            mText03.setText(data);
        } else if (index == 3) {
            mText04.setText(data);
        } else {
            throw new IllegalArgumentException();
        }
    }
}

package com.n00blife.parkalot;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookedSlotActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_slot);
        RecyclerView recyclerView = findViewById(R.id.booked_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String qr = preferences.getString("QRCODE", null);
        List<String> qrArray = Arrays.asList(qr.split("~"));
        List<BookedSlot> bookedSlotList = new ArrayList<>();
        Log.d("BookedSlot", qrArray.toString());
        for(String bookedSlotString : qrArray) {
            if(!bookedSlotString.equals(qrArray.get(0))) {
                String[] bookedSlotArray = bookedSlotString.split("--");
                Log.d("BS", "" + bookedSlotArray.length);
                            String[] amountArray = bookedSlotArray[0].split(":");
                            String[] hasPaidArray = bookedSlotArray[1].split(":");
                            bookedSlotList.add(new BookedSlot(amountArray[1], hasPaidArray[1].equals("true")));
            }
        }
        for(int i = 0; i < bookedSlotList.size(); i++) {
            Log.d("BSL", "AMOUNT : " + bookedSlotList.get(i).getAmount());
            Log.d("BSL", "PAID : " + bookedSlotList.get(i).isHasPaid());
        }
        Log.d("BookedSlotList", bookedSlotList.toString());
        if(qr != null)
            Log.d("BookedSlot", qr);
        BookedSlotAdapter bookedSlotAdapter = new BookedSlotAdapter(this, bookedSlotList);
        recyclerView.setAdapter(bookedSlotAdapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }
}

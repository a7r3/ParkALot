package com.n00blife.parkalot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;

public class ParkingActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(ParkingActivity.this, TimeSlotActivity.class);
        intent.putExtra(SLOT_EXTRA, v.getTag().toString());
        startActivity(intent);
    }

    public static final String SLOT_EXTRA = "chosen_slot";
    private final String SLOT_1 = "1";
    private final String SLOT_2 = "2";
    private final String SLOT_3 = "3";
    private final String SLOT_4 = "4";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_park);
        View slotOne = findViewById(R.id.slot_one);
        slotOne.setTag(SLOT_1);
        slotOne.setOnClickListener(this);
        View slotTwo = findViewById(R.id.slot_two);
        slotTwo.setTag(SLOT_2);
        slotTwo.setOnClickListener(this);
        View slotThree = findViewById(R.id.slot_three);
        slotThree.setTag(SLOT_3);
        slotThree.setOnClickListener(this);
        View slotFour = findViewById(R.id.slot_four);
        slotFour.setTag(SLOT_4);
        slotFour.setOnClickListener(this);
    }
}

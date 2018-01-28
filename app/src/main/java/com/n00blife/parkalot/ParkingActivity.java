package com.n00blife.parkalot;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

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

        final View menuView = findViewById(R.id.menu);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(preferences.getString("AMOUNT", null) == null)
            preferences.edit().putString("AMOUNT", "500").apply();

        menuView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(ParkingActivity.this, menuView);
                popupMenu.inflate(R.menu.menu);
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.menu_booked_slots:
                                Intent intent = new Intent(ParkingActivity.this, BookedSlotActivity.class);
                                startActivity(intent);
                                return true;
                            case R.id.menu_log_out:
                                Toast.makeText(ParkingActivity.this, "Work in Progress", Toast.LENGTH_SHORT).show();
                                return true;
                                default:
                                    return false;
                        }
                    }
                });
            }
        });
    }
}

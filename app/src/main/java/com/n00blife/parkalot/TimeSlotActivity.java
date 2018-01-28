package com.n00blife.parkalot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class TimeSlotActivity extends AppCompatActivity {

    public static String slotName;
    private List<TimeSlot> timeSlots;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeslot);

        slotName = getIntent().getStringExtra(ParkingActivity.SLOT_EXTRA);

        RecyclerView slotRecycler = findViewById(R.id.slot_recycler);
        slotRecycler.setLayoutManager(new LinearLayoutManager(this));
        slotRecycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("Slots");
        timeSlots = new ArrayList<>();
        final TimeSlotAdapter adapter = new TimeSlotAdapter(this, timeSlots);
        slotRecycler.setAdapter(adapter);

        for(int i = 9; i < 21; i++) {
            timeSlots.add(new TimeSlot(i + "-" + (i+1), false));
        }

        for(int i = 0; i < timeSlots.size(); i++) {
            final int j = i;
            reference.child(slotName).child(timeSlots.get(i).getTimeSlotName()).child("booked").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue() != null) {
                        timeSlots.get(j).setSlotBooked((boolean) dataSnapshot.getValue(false));
                        adapter.notifyItemChanged(j);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        Button proceedButton = findViewById(R.id.proceed_button);
        proceedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                for(TimeSlot timeSlot : timeSlots) {
                    if(timeSlot.isSelected()) {
                        stringBuilder.append(timeSlot.getTimeSlotName()).append(":");
                    }
                }
                if(stringBuilder.toString().isEmpty()) {
                    Toast.makeText(TimeSlotActivity.this, "Select a time slot to continue", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    Log.d("SLOTS", stringBuilder.toString());
                    Intent intent = new Intent(TimeSlotActivity.this, PaymentActivity.class);
                    intent.putExtra("SLOTS", stringBuilder.toString());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

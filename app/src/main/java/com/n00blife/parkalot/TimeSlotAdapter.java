package com.n00blife.parkalot;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.List;


public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.ViewHolder> {

    private Context context;
    private List<TimeSlot> timeSlot;

    public TimeSlotAdapter(Context context, List<TimeSlot> timeSlot) {
        super();
        this.context = context;
        this.timeSlot = timeSlot;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.node_timeslot, parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final TimeSlot currentTimeSlot = timeSlot.get(position);
        holder.slotName.setText(currentTimeSlot.getTimeSlotName());

        Log.d("ADapter", TimeSlotActivity.slotName);
        MainActivity.getSlotsDatabase()
                .child(TimeSlotActivity.slotName)
                .child(currentTimeSlot.getTimeSlotName())
                .child("booked")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() != null) {
                            Log.d("ADapter", "TRUE ? " + (boolean) dataSnapshot.getValue());
                            if((boolean) dataSnapshot.getValue()) {
                                holder.itemView.setEnabled(false);
                                holder.itemView.setClickable(false);
                                holder.slotCheckBox.setEnabled(false);
                                holder.slotCheckBox.setClickable(false);
                            } else {
                                holder.itemView.setEnabled(true);
                                holder.itemView.setClickable(true);
                                holder.slotCheckBox.setEnabled(true);
                                holder.slotCheckBox.setClickable(true);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

        holder.slotCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                currentTimeSlot.setSlotBooked(isChecked);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.slotCheckBox.toggle();
            }
        });
    }

    @Override
    public int getItemCount() {
        return timeSlot.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView slotName;
        private ImageView slotStatus;
        private CheckBox slotCheckBox;

        public ViewHolder(View itemView) {
            super(itemView);
            slotName = itemView.findViewById(R.id.slot_name);
            slotStatus = itemView.findViewById(R.id.slot_status);
            slotCheckBox = itemView.findViewById(R.id.slot_checkbox);
        }
    }
}

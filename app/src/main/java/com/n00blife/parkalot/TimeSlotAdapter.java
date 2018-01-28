package com.n00blife.parkalot;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

import java.util.Calendar;
import java.util.List;


public class TimeSlotAdapter extends RecyclerView.Adapter<TimeSlotAdapter.ViewHolder> {

    private Context context;
    private List<TimeSlot> timeSlot;
    private Drawable bg;

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
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        String[] time = currentTimeSlot.getTimeSlotName().split("-");
        int specifiedTime = Integer.parseInt(time[0]);
        Log.d("ADapter", "HOUR : " + hour + " SPEC : " + specifiedTime);
        final boolean isAhead = hour > specifiedTime;
        Log.d("ADapter", TimeSlotActivity.slotName);
        MainActivity.getSlotsDatabase()
                .child(TimeSlotActivity.slotName)
                .child(currentTimeSlot.getTimeSlotName())
                .child("booked")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() != null) {
                            holder.retrievalStatus.setVisibility(View.GONE);
                            if(isAhead) {
                                disableNode(holder);
                            } else {
                                if ((boolean) dataSnapshot.getValue())
                                    enableNode(holder);
                                else {
                                    disableNode(holder);
                                }
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
                currentTimeSlot.setSelected(isChecked);
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

    public void enableNode(ViewHolder holder) {
        Log.d("KEK", "Enabling Node");
        holder.itemView.setEnabled(false);
        holder.itemView.setClickable(false);
        if(bg == null)
            bg = holder.itemView.getBackground();
        holder.itemView.setBackgroundColor(Color.parseColor("#d3d3d3"));
        holder.slotCheckBox.setEnabled(false);
        holder.slotCheckBox.setClickable(false);
    }

    public void disableNode(ViewHolder holder) {
        Log.d("KEK", "Disabling node");
        holder.itemView.setEnabled(true);
        holder.itemView.setClickable(true);
        holder.itemView.setBackground(bg);
        holder.slotCheckBox.setEnabled(true);
        holder.slotCheckBox.setClickable(true);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView slotName;
        private CheckBox slotCheckBox;
        private TextView retrievalStatus;

        public ViewHolder(View itemView) {
            super(itemView);
            slotName = itemView.findViewById(R.id.slot_name);
            slotCheckBox = itemView.findViewById(R.id.slot_checkbox);
            retrievalStatus = itemView.findViewById(R.id.retrieve_status);
        }
    }
}

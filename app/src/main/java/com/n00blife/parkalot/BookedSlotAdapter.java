package com.n00blife.parkalot;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.glxn.qrgen.android.QRCode;

import java.util.List;

public class BookedSlotAdapter extends RecyclerView.Adapter<BookedSlotAdapter.ViewHolder> {

    private List<BookedSlot> bookedSlotList;
    private Activity activity;

    public BookedSlotAdapter(Activity activity, List<BookedSlot> bookedSlotList) {
        super();
        this.activity = activity;
        this.bookedSlotList = bookedSlotList;
    }

    @Override
    public BookedSlotAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(activity).inflate(R.layout.booked_node, parent, false));
    }

    @Override
    public void onBindViewHolder(BookedSlotAdapter.ViewHolder holder, final int position) {
        final String str = "Amount : " + bookedSlotList.get(position).getAmount() + "\nPaid : " + (bookedSlotList.get(position).isHasPaid() ? "YES" : "NO");
        holder.qrDetails.setText(str);
        holder.qrCode.setImageBitmap(QRCode.from(str).withSize(300, 300).bitmap());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, BookedSlotViewerActivity.class);
                intent.putExtra("QRR", bookedSlotList.get(position).getAmount() + "," + bookedSlotList.get(position).isHasPaid());
                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookedSlotList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView qrCode;
        private TextView qrDetails;

        public ViewHolder(View itemView) {
            super(itemView);
            qrCode = itemView.findViewById(R.id.booked_qr_view);
            qrDetails = itemView.findViewById(R.id.booked_slot_view);
        }
    }
}

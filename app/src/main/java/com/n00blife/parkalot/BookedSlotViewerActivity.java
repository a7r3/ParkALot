package com.n00blife.parkalot;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import net.glxn.qrgen.android.QRCode;

/**
 * Created by arvind on 1/28/18.
 */

public class BookedSlotViewerActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booked_slot_viewer);
        String qrData = getIntent().getStringExtra("QRR");
        ImageView imageView = findViewById(R.id.qr_image);
        imageView.setImageBitmap(QRCode.from(qrData).withSize(500, 500).bitmap());
    }
}

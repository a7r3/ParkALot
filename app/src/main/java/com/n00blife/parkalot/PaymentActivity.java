package com.n00blife.parkalot;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PaymentActivity extends AppCompatActivity {

    private TextView payAmount, walletAmount, email, slots;
    int amountPerSlot = 100;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        final String[] selectedSlots = getIntent().getStringExtra("SLOTS").split(":");
        Log.d("PaymentActivity", "" + selectedSlots.length);
        Toolbar toolbar = findViewById(R.id.toolbarSec);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Confirm Payment");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        payAmount = findViewById(R.id.summary_pay_amount);
        walletAmount = findViewById(R.id.summary_wallet_amount);
        email = findViewById(R.id.summary_email);
        StringBuilder slotList = new StringBuilder();
        for(int i = 0; i < selectedSlots.length; i++ ) {
            slotList.append(selectedSlots[i]);
            if(i != selectedSlots.length - 1)
                slotList.append(", ");
        }
        final int totalAmount = selectedSlots.length * amountPerSlot;
        payAmount.setText("" + totalAmount);
        slots = findViewById(R.id.summary_slots);
        slots.setText(slotList.toString());
        email.setText(user.getEmail());
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(PaymentActivity.this);
        final String walletAMOUNT = preferences.getString("AMOUNT", "500");
        if(totalAmount >= Integer.parseInt(walletAMOUNT))
            walletAmount.setText("Rs. " + walletAMOUNT + " (via cash)");
        else
            walletAmount.setText("Rs. " + walletAMOUNT);

        Button payButton = findViewById(R.id.pay);
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(String selectedSlot : selectedSlots) {
                    MainActivity.getSlotsDatabase()
                            .child(TimeSlotActivity.slotName)
                            .child(selectedSlot)
                            .child("booked")
                            .setValue(true);
                    MainActivity.getSlotsDatabase()
                            .child(TimeSlotActivity.slotName)
                            .child(selectedSlot)
                            .child("uid")
                            .setValue(user.getUid());
                    if(Integer.parseInt(walletAMOUNT) > totalAmount) {
                        preferences.edit()
                                .putString("QRCODE", preferences.getString("QRCODE", "") + "~" + "AMOUNT:" + totalAmount + "--PAID:true")
                        .apply();
                        preferences.edit()
                                .putString("AMOUNT", String.valueOf(Integer.parseInt(walletAMOUNT) - totalAmount))
                        .apply();
                        Toast.makeText(PaymentActivity.this, "Paid from Wallet", Toast.LENGTH_LONG).show();
                        Log.d("Paymentactivity", preferences.getString("QRCODE", "null"));
                    } else {
                        preferences.edit()
                                .putString("QRCODE", preferences.getString("QRCODE", "") + "~" + "AMOUNT:" + totalAmount + "--PAID:false")
                        .apply();
                        Toast.makeText(PaymentActivity.this, "Pay with Cash please", Toast.LENGTH_LONG).show();
                    }
                    ImageView imageView = findViewById(R.id.success_payment_image);
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}

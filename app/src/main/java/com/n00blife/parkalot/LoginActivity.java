package com.n00blife.parkalot;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private final int CALLBACK_CODE = 2342;

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        AuthUI.IdpConfig config = new AuthUI.IdpConfig.GoogleBuilder().build();

        database = FirebaseDatabase.getInstance();

        reference = database.getReference().child("Users");

        startActivityForResult(AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(Arrays.asList(config))
                .setLogo(R.mipmap.ic_launcher_round)
        .build(), CALLBACK_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CALLBACK_CODE) {
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if(resultCode == RESULT_OK) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                Log.d(TAG, "UID : " + user.getUid());
                DatabaseReference userReference = reference.child(user.getUid());
                userReference.child("email").setValue(user.getEmail());
                userReference.child("name").setValue(user.getDisplayName());
                userReference.child("profile_pic").setValue(user.getPhotoUrl().toString());
                View view = findViewById(R.id.success_image);
                view.animate().alpha(1).setDuration(500).setInterpolator(new AccelerateInterpolator()).withEndAction(new Runnable() {
                    @Override
                    public void run() {
                        Log.d(TAG, "Run faster");
                        Intent parkIntent = new Intent(LoginActivity.this, ParkingActivity.class);
                        parkIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(parkIntent);
                        finish();
                    }
                });
            } else {
                Log.d(TAG, "Response Code : " + response.getErrorCode());
                View view = findViewById(R.id.failed_image);
                view.animate().alpha(1).withEndAction(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        }
    }
}

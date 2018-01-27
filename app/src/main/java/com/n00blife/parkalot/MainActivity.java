package com.n00blife.parkalot;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

// Implement ZXingScannerView.ResultHandler for handling QR Scanning results

//    @Override
//    public void handleResult(Result result) {
//        Toast.makeText(this, result.getText(), Toast.LENGTH_LONG).show();
//    }

//    private ZXingScannerView zXingScannerView;
//
//    private GoogleSignInClient client;
//
//    private ImageView imageView;
//    private EditText editText;

    public static DatabaseReference getSlotsDatabase() {
        return FirebaseDatabase.getInstance().getReference("Slots");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        zXingScannerView = findViewById(R.id.scanner_view);
//
//        List<BarcodeFormat> formatList = new ArrayList<>();
//
//        formatList.add(BarcodeFormat.CODE_128);
//        formatList.add(BarcodeFormat.QR_CODE);
//
//        zXingScannerView.setFormats(formatList);
//        zXingScannerView.setFlash(false);
//        zXingScannerView.startCamera();

//        FirebaseOptions options = new FirebaseOptions.Builder()
//                .setApiKey("AIzaSyDpzZwPzDKlWWVq4PVs74cbq2Q0hMhMpwQ")
//                .setApplicationId("1:792262658888:android:485a2d984916adb1")
//                .setDatabaseUrl("https://spiral-6f434.firebaseio.com/")
//                .build();
//
//        FirebaseApp.initializeApp(this, options);

        FirebaseApp.initializeApp(this);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        Intent intent;

        if (user == null)
            intent = new Intent(this, LoginActivity.class);
        else
            intent = new Intent(this, ParkingActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

//        imageView = findViewById(R.id.image);
//
//        editText = findViewById(R.id.text);
//
//        Button button  = findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(editText.getText() != null)
//                    imageView.setImageBitmap(QRCode.from(editText.getText().toString()).withSize(500, 500).bitmap());
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        zXingScannerView.setResultHandler(this);
//        zXingScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
//        zXingScannerView.stopCamera();
    }
}

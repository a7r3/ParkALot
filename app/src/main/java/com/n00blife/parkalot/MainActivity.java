package com.n00blife.parkalot;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;

import net.glxn.qrgen.android.QRCode;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    @Override
    public void handleResult(Result result) {
        Toast.makeText(this, result.getText(), Toast.LENGTH_LONG).show();
    }

    private ZXingScannerView zXingScannerView;

    private GoogleSignInClient client;

    private ImageView imageView;
    private EditText editText;

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

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct == null) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(this, ParkingActivity.class);
        }

        imageView = findViewById(R.id.image);

        editText = findViewById(R.id.text);

        Button button  = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText() != null)
                    imageView.setImageBitmap(QRCode.from(editText.getText().toString()).withSize(500, 500).bitmap());
            }
        });
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

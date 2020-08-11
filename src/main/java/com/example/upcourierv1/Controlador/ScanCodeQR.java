package com.example.upcourierv1.Controlador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.upcourierv1.R;
import com.google.zxing.Result;



public class ScanCodeQR extends AppCompatActivity {
    public static final int MY_PERMISSION_CAMERA = 98 ;
    private CodeScanner mCodeScanner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_code_q_r);
        permissionCamera();
        scanner();


    }
    private void scanner(){
        CodeScannerView scannerView = findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                ScanCodeQR.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ScanCodeQR.this, result.getText(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ScanCodeQR.this, ResultScanActivity.class);
                        //intent.putExtra("Confirmacion", result.getText());
                        startActivity(intent);
                    }
                });
            }
        });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCodeScanner.startPreview();
            }
        });
    }
    private boolean permissionCamera(){
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.CAMERA
        }, MY_PERMISSION_CAMERA );
        return true;
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSION_CAMERA:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        scanner();
                    }
                } else {
                    Toast.makeText(this, "without permission", Toast.LENGTH_SHORT).show();
                }
        }
    }*/



    @Override
    protected void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();

    }
}
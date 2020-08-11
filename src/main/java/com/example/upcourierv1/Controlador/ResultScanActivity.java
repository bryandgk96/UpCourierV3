package com.example.upcourierv1.Controlador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.upcourierv1.R;

public class ResultScanActivity extends AppCompatActivity {
    public static TextView result;
    String resp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_scan);
        Bundle b = getIntent().getExtras();
        if(b!=null)
            resp = b.getString("Confirmacion");
        //result = (TextView) findViewById(R.id.resultScan);

        //result.setText(resp+ " Elija metodo de pago");


    }
}
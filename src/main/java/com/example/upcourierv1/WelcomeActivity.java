package com.example.upcourierv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    Button btnDriver, btnCustomer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        btnDriver = (Button) findViewById(R.id.btnWelcome_Driver);
        btnCustomer = (Button) findViewById(R.id.btnWelcome_Customer);

        btnDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent driver = new Intent(WelcomeActivity.this, CreateMapV1.class);
                startActivity(driver);
                finish();
                return;
            }
        });

        btnCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent customer = new Intent(WelcomeActivity.this, CostumerLoginRegisterActivity.class);
                startActivity(customer);
                finish();
                return;
            }
        });

    }

}

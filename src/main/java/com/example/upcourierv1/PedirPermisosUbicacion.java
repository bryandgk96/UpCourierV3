package com.example.upcourierv1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;

public class PedirPermisosUbicacion extends AppCompatActivity {
    public static final int MY_PERMISSION_REQUEST_LOCATION = 99;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedir_permisos_ubicacion);

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    runTimePermision();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    Intent welcomeIntent = new Intent(PedirPermisosUbicacion.this, CreateMapV1.class);
                    startActivity(welcomeIntent);
                }
            }
        };
        thread.start();
    }
    private boolean runTimePermision() {
        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        }, MY_PERMISSION_REQUEST_LOCATION );

        return true;
    }
}

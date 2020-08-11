package com.example.upcourierv1.Controlador;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.upcourierv1.DBHelpers.DBUsuario;
import com.example.upcourierv1.Listeners.DectectMotionEventListener;
import com.example.upcourierv1.Listeners.LocationBroadCastReceiver;
import com.example.upcourierv1.Listeners.OrientationSensorEventListener;
import com.example.upcourierv1.Modelos.LocationUpdates;
import com.example.upcourierv1.Modelos.Usuario;
import com.example.upcourierv1.R;
import com.example.upcourierv1.WelcomeActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


public class DriverMapActivity extends AppCompatActivity implements OnMapReadyCallback {


    public static final int MY_PERMISSION_REQUEST_LOCATION = 99;
    public static final int DEFAULT_ZOOM = 15;
    static DriverMapActivity instance;
    private LocationBroadCastReceiver locationBroadCastReceiver;
    LocationUpdates locationUpdates;
    private GoogleMap mGoogleMap;
    SupportMapFragment supportMapFragment;
    private Button btnCam, btnSalir;
    private SensorManager sensorManager;
    private TextView textOrientation;
    private double orientation = 0;


    private int idUser;
    private DBUsuario dbUsuario;
    private Usuario user;



    public static DriverMapActivity getInstance(){return instance;}
    public static DriverMapActivity getInstance2(){return instance;}
    public static DriverMapActivity getInstance3(){return instance;}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_map_v1);
        //usuario ON

        Bundle b = getIntent().getExtras();
        if( b != null)
            idUser = b.getInt("idUser");


        dbUsuario = new DBUsuario(this);
        user = dbUsuario.getUsuarioById(idUser);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        btnCam = (Button) findViewById(R.id.btn_Cam);
        btnSalir = (Button) findViewById(R.id.btn_leave);
        textOrientation = (TextView) findViewById(R.id.textOrientation);
        locationBroadCastReceiver = new LocationBroadCastReceiver();
        locationUpdates = new LocationUpdates();
        instance = this;

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);

        if(runTimePermision());
        else {
            supportMapFragment.getMapAsync(this);

        }
        orientation();
        movement();
        btnCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverMapActivity.this, ScanCodeQR.class);
                startActivity(intent);
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DriverMapActivity.this, WelcomeActivity.class);
                startActivity(i);
                finish();
                return;
            }
        });

    }

    private boolean runTimePermision() {

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
        }, MY_PERMISSION_REQUEST_LOCATION );

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case MY_PERMISSION_REQUEST_LOCATION:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                        initGPS();
                        supportMapFragment.getMapAsync(this);
                    }
                }else{
                    Toast.makeText(instance, "without permission", Toast.LENGTH_SHORT).show();
                }

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("Map_debug", "onMapReady: EL MAPA SE ESTÁ MOSTRANDO");
        Toast.makeText(this, "onMapReady: EL MAPA SE ESTÁ MOSTRANDO", Toast.LENGTH_SHORT).show();

        mGoogleMap = googleMap;
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            return;
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
        mGoogleMap.getUiSettings().setMapToolbarEnabled(true);

        mGoogleMap.setMyLocationEnabled(true);

    }




    private void initGPS() {
        Intent i = new Intent(this, LocationBroadCastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                0,
                i,
                PendingIntent.FLAG_UPDATE_CURRENT);
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0 , 0, pendingIntent);
    }

    public void getLocation(double lat,double lon){

        LatLng latLng = new LatLng(lat,lon);
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.animateCamera(CameraUpdateFactory.zoomTo(19.0f));





    }

    public void orientation(){
        OrientationSensorEventListener orientationSensorEventListener = new OrientationSensorEventListener();

        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        sensorManager.registerListener(orientationSensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);

        Sensor sensor1 = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(orientationSensorEventListener, sensor1, SensorManager.SENSOR_DELAY_NORMAL);



    }
    private void movement(){
        DectectMotionEventListener dectectMotionEventListener = new DectectMotionEventListener();

        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        sensorManager.registerListener(dectectMotionEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
    public void isFaceup(int faceUp){
        /*if(faceUp == 1)
            textOrientation.setText("Faceup");
        else if(faceUp == 2)
            textOrientation.setText("FaceDown");
        else
            textOrientation.setText("Is standing");*/
        orientation = faceUp;

    }
    public void calAceleration(float y, float z){
        double value;
        if(orientation == 1 || orientation == 2 ) {
            value =y*-1;
            textOrientation.setText(value+"");
        }else {
            value =z*-1;
            textOrientation.setText(value+"");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(locationBroadCastReceiver!=null){
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(LocationManager.KEY_LOCATION_CHANGED);
            registerReceiver(locationBroadCastReceiver,intentFilter);
        }else{
            Toast.makeText(instance, "locationBroadcastReceiver NULL", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(locationBroadCastReceiver);

    }

    @Override
    protected void onStop() {
        super.onStop();

    }
}

package com.example.upcourierv1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateMapV1 extends AppCompatActivity implements OnMapReadyCallback {


    public static final int MY_PERMISSION_REQUEST_LOCATION = 99;
    public static final int DEFAULT_ZOOM = 15;
    static CreateMapV1 instance;
    private LocationBroadCastReceiver locationBroadCastReceiver;
    LocationUpdates locationUpdates;
    private GoogleMap mGoogleMap;
    SupportMapFragment supportMapFragment;

    public static CreateMapV1 getInstance(){return instance;}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_map_v1);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);



        locationBroadCastReceiver = new LocationBroadCastReceiver();
        locationUpdates = new LocationUpdates();
        instance = this;

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);

        if(runTimePermision());
        else {
            supportMapFragment.getMapAsync(this);
        }





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


}

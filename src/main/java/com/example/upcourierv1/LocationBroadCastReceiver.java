package com.example.upcourierv1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;


public class LocationBroadCastReceiver  extends BroadcastReceiver {


    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent != null){
            //final String action = intent.getAction();
            if(intent.hasExtra(LocationManager.KEY_LOCATION_CHANGED)){
                String locationKey = LocationManager.KEY_LOCATION_CHANGED;
                Location location = (Location) intent.getExtras().get(locationKey);

                double latitude = location.getLatitude();
                double longitud = location.getLongitude();
                Log.d("Broadcast", "valores"+latitude+" "+longitud);

                try{
                    CreateMapV1.getInstance().getLocation(latitude,longitud);
                }catch (Exception e){
                    Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                }

            }
        }
    }
}

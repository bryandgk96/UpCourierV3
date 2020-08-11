package com.example.upcourierv1.Listeners;

import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.util.Log;

import com.example.upcourierv1.Controlador.DriverMapActivity;

public class DectectMotionEventListener implements SensorEventListener {
    private static final String TAG = "DectectMotionEventListener";
    private float[] gravity;
    private static final float ALPHA = 0.8f;
    private float x, y, z;

    @SuppressLint("LongLogTag")
    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] values = event.values.clone();
        gravity = new float[3];
        values = highPass(values[0], values[1], values[2]);
        x = values[0];
        y = values [1];
        z = values [2];
        Log.d("Aceleration X: ", String.valueOf(x) );
        Log.d("Aceleration Y: ", String.valueOf(y) );
        Log.d("Aceleration Z: ", String.valueOf(z) );
        try{
            DriverMapActivity.getInstance3().calAceleration(y,z);
        }catch (Exception e){
            Log.d(TAG, "ERROR");
        }
    }
    private float[] highPass(float x, float y, float z) {
        float[] filteredValues = new float[3];

        gravity[0] = ALPHA * gravity[0] + (1 - ALPHA) * x;
        gravity[1] = ALPHA * gravity[1] + (1 - ALPHA) * y;
        gravity[2] = ALPHA * gravity[2] + (1 - ALPHA) * z;

        filteredValues[0] = x - gravity[0];
        filteredValues[1] = y - gravity[1];
        filteredValues[2] = z - gravity[2];
        return filteredValues;
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

package com.example.upcourierv1.Listeners;


import android.annotation.SuppressLint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import android.util.Log;

import com.example.upcourierv1.Controlador.DriverMapActivity;


public class OrientationSensorEventListener implements SensorEventListener {
    private static final String TAG = "OrientationSensorEventListener";
    private boolean isFaceUp;
    private float [] accelerationValues;
    private float [] magneticValues;

    @Override
    public void onSensorChanged(SensorEvent event) {
        float rotationMatrix[];
        switch (event.sensor.getType()){
            case Sensor.TYPE_ACCELEROMETER: // aceletrometro
                accelerationValues = event.values.clone(); // valores
                rotationMatrix = generateRotationMatrix();
                if(rotationMatrix != null){
                    determineOrientation(rotationMatrix);
                }
                break;
            case Sensor.TYPE_MAGNETIC_FIELD: // magnometro
                magneticValues = event.values.clone(); // valores
                rotationMatrix = generateRotationMatrix();
                if(rotationMatrix != null){
                    determineOrientation(rotationMatrix);
                }
                break;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @SuppressLint("LongLogTag")
    private float[] generateRotationMatrix(){
        float[] rotationMatrix = null;
        if(accelerationValues != null && magneticValues != null){
            rotationMatrix = new float[16];
            boolean rotationMatrixGenerated;
            rotationMatrixGenerated = SensorManager.getRotationMatrix(
                    rotationMatrix,
                    null,
                    accelerationValues,
                    magneticValues);
            if(!rotationMatrixGenerated){
                //Log.w(TAG, "Fallo en generar la matriz de rotacion");
                rotationMatrix = null;
            }
        }
        return rotationMatrix;
    }

    @SuppressLint("LongLogTag")
    private void determineOrientation(float[] rotationMatrix) { // detemrina la orientacioón
        float [] orientationValues = new float[3];
        SensorManager.getOrientation(rotationMatrix, orientationValues);

        double azimuth = Math.toDegrees(orientationValues[0]);
        double pitch = Math.toDegrees(orientationValues[1]);
        double roll = Math.toDegrees(orientationValues[2]);


        //Log.d(TAG, "X: "+String.valueOf(azimuth)+ " Y: "+  String.valueOf(pitch)+ " Z: "+String.valueOf(roll));
        if(pitch<= 10){
            if(Math.abs(roll)>= 170){
                onFaceDown();
            }else if(Math.abs(roll)<=10){
                onFaceUp();
            }
            else{

                try{
                    DriverMapActivity.getInstance2().isFaceup(3);
                }catch (Exception e){
                    //Log.d("Salidas", "ERROR");
                }

            }
        }
    }
    @SuppressLint("LongLogTag")
    public void onFaceDown() {
        if(isFaceUp){
            Log.d(TAG, "Está en superficie plana Boca Abajo");

            isFaceUp = false;
            try{
                DriverMapActivity.getInstance2().isFaceup(2);
            }catch (Exception e){
                Log.d(TAG, "ERROR");
            }
        }
    }
    @SuppressLint("LongLogTag")
    public void onFaceUp() {
        if(!isFaceUp){
            Log.d(TAG, "Está en superficie plana Boca Arriba");
            isFaceUp = true;

            try{
                DriverMapActivity.getInstance2().isFaceup(1);
            }catch (Exception e){
                Log.d(TAG, "ERROR");
            }
        }
    }
}

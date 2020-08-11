package com.example.upcourierv1.Modelos;

public class LocationUpdates {
    private double latitude;
    private double longitude;

    public LocationUpdates (){}

    public void setLatitude(double _latitude){
        latitude = _latitude;
    }
    public double getLatitude(){
        return latitude;
    }

    public void setLongitude(double _longitude){
        longitude = _longitude;
    }
    public double getLongitude(){
        return longitude;
    }

}

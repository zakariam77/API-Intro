package com.zproject.POJO.Serialization;

public class Location {
    private double lat;
    private double lng;

    //default constructor needed for deserialisation
    Location(){

    }
    Location(double lat, double lng){
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }



}

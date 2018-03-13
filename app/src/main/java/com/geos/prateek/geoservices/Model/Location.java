package com.geos.prateek.geoservices.Model;

/**
 * Created by Subham on 05-03-2018.
 */

public class Location {
    private String name;
    private String distance;
    private String address;

    public String getName() {
        return name;
    }

    public String getDistance() {
        return distance;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

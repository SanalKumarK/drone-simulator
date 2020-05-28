package org.derivco.entity;

public class TubeStation {
    public final String station;
    public final double latitude;
    public final double longitude;

    public TubeStation(String station, double latitude, double longitude) {
        this.station = station;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
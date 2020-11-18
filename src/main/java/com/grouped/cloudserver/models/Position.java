package com.grouped.cloudserver.models;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(as=Position.class)
public class Position {

    private double lat;
    private double lon;

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Position))
            return false;
        Position other = (Position)o;
        return this.lat == other.lat && this.lon == other.lon;
    }
}

package com.example.sa005gu.swaggersdksamples;

/**
 * Created by sa005gu on 10/5/2017.
 */

public class location{
    Double latitude;
    Double longitude;

    public location(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


}

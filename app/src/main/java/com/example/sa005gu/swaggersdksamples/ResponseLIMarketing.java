package com.example.sa005gu.swaggersdksamples;

/**
 * Created by sa005gu on 10/5/2017.
 */

public class ResponseLIMarketing {

    private String deviceId;
    private Double latitude;
    private Double longitude;

    public ResponseLIMarketing(String deviceId, Double latitude, Double longitude) {
        this.deviceId = deviceId;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}

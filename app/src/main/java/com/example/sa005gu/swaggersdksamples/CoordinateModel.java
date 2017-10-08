package com.example.sa005gu.swaggersdksamples;

import java.util.List;

/**
 * Created by sa005gu on 10/5/2017.
 */

public class CoordinateModel {

    List<location> locations;
    String transactionId;

    public CoordinateModel(List<location> locations, String transactionId) {
        this.locations = locations;
        this.transactionId = transactionId;
    }

    public List<location> getLocations() {
        return locations;
    }

    public void setLocations(List<location> locations) {
        this.locations = locations;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }


}



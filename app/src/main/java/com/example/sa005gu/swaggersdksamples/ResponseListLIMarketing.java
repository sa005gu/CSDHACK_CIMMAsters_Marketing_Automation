package com.example.sa005gu.swaggersdksamples;

import java.util.List;

/**
 * Created by sa005gu on 10/5/2017.
 */

public class ResponseListLIMarketing {
    List<ResponseLIMarketing> deviceLocations;

    public ResponseListLIMarketing(List<ResponseLIMarketing> deviceLocations, String transactionId) {
        this.deviceLocations = deviceLocations;
        this.transactionId = transactionId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    String transactionId;

    public ResponseListLIMarketing(List<ResponseLIMarketing> responselist) {
        this.deviceLocations = responselist;
    }

    public List<ResponseLIMarketing> getResponselist() {
        return deviceLocations;
    }

    public void setResponselist(List<ResponseLIMarketing> responselist) {
        this.deviceLocations = responselist;
    }
}

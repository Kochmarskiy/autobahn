package com.company.autobahn.server.model;

import java.util.Date;

/**
 * Created by Кочмарский on 01.10.2016.
 */
public class CheckpointRecord {
    private int driverId;
    private int checkpointId;
    private Date timestamp;

    public CheckpointRecord(int driverId, int checkpointId, Date timestamp) {
        this.driverId = driverId;
        this.checkpointId = checkpointId;
        this.timestamp = timestamp;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public int getCheckpointId() {
        return checkpointId;
    }

    public void setCheckpointId(int checkpointId) {
        this.checkpointId = checkpointId;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "CheckpointRecord{" +
                "driverId=" + driverId +
                ", checkpointId=" + checkpointId +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}

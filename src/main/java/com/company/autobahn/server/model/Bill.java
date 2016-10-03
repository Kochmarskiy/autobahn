package com.company.autobahn.server.model;

import java.util.Date;

public class Bill {
    private int cost;
    private Date startDate;
    private Date finalDate;
    private int firstChackpointId;
    private int lastCheckpointId;

    public Bill(int cost, Date startDate, Date finalDate, int firstChackpointId, int lastCheckpointId) {
        this.cost = cost;
        this.startDate = startDate;
        this.finalDate = finalDate;
        this.firstChackpointId = firstChackpointId;
        this.lastCheckpointId = lastCheckpointId;
    }


    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getFirstChackpointId() {
        return firstChackpointId;
    }

    public void setFirstChackpointId(int firstChackpointId) {
        this.firstChackpointId = firstChackpointId;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public int getLastCheckpointId() {
        return lastCheckpointId;
    }

    public void setLastCheckpointId(int lastCheckpointId) {
        this.lastCheckpointId = lastCheckpointId;
    }
}

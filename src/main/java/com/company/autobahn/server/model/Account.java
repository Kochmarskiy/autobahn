package com.company.autobahn.server.model;

public class Account {
    private int driverId;
    private String email;

    public Account(int driverId, String email) {
        this.driverId = driverId;
        this.email = email;
    }

    public int getDriverId() {
        return driverId;
    }

    public void setDriverId(int driverId) {
        this.driverId = driverId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Account{" +
                "driverId=" + driverId +
                ", email='" + email + '\'' +
                '}';
    }
}

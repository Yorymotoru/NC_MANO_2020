package com.example.demo.domain;

public class Building {
    private String address;
    private int numberOfFloors;
    private boolean residential;

    public Building(String address, int numberOfFloors, Boolean residential) {
        this.address = address;
        this.numberOfFloors = numberOfFloors;
        this.residential = residential;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(int numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public Boolean getResidential() {
        return residential;
    }

    public void setResidential(Boolean residential) {
        this.residential = residential;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

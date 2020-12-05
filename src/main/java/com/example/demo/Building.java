package com.example.demo;

public class Building {
    private int numberOfFloors;
    private boolean residential;
    private String address;

    Building(int numberOfFloors, Boolean residential, String address) {
        this.numberOfFloors = numberOfFloors;
        this.residential = residential;
        this.address = address;
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

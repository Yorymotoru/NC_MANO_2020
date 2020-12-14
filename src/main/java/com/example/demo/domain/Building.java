package com.example.demo.domain;

import com.sun.istack.NotNull;

public class Building {

    @NotNull
    private Integer id;

    @NotNull
    private String address;

    @NotNegative
    private Integer numberOfFloors;

    private Boolean residential;

    public Building(Integer id, String address, Integer numberOfFloors, Boolean residential) {
        this.id = id;
        this.address = address;
        this.numberOfFloors = numberOfFloors;
        this.residential = residential;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getNumberOfFloors() {
        return numberOfFloors;
    }

    public void setNumberOfFloors(Integer numberOfFloors) {
        this.numberOfFloors = numberOfFloors;
    }

    public Boolean getResidential() {
        return residential;
    }

    public void setResidential(Boolean residential) {
        this.residential = residential;
    }

    @Override
    public String toString() {
        return "Building {" +
                "id = " + id + ", " +
                "address = '" + address + "', " +
                "numberOfFloors = " + numberOfFloors + ", " +
                "residential = " + residential + "}";
    }
}
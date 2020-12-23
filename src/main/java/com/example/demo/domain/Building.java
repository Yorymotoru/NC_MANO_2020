package com.example.demo.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Building {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String address;

    private Integer numberOfFloors;

    private Boolean residential;

    public Building(Integer id, String address, Integer numberOfFloors, Boolean residential) {
        this.id = id;
        this.address = address;
        this.numberOfFloors = numberOfFloors;
        this.residential = residential;
    }

    public Building() {

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
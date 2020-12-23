package com.example.demo.backend.domain;

import javax.persistence.*;

@Entity
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "BUILDING_ID")
    private Building building;

    private String firstName;

    private String lastName;

    public Tenant(Integer id, Building building, String firstName, String lastName) {
        this.id = id;
        this.building = building;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Tenant() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building building) {
        this.building = building;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "Tenant {" +
                "id = " + id + ", " +
                "building_id = '" + building.getId() + "', " +
                "firstname = " + firstName + ", " +
                "lastname = " + lastName + "}";
    }

}

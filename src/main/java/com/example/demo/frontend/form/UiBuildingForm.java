package com.example.demo.frontend.form;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UiBuildingForm {

    private String building_id;

    @NotBlank(message = "Address can't be empty!")
    private String address;

    @NotBlank(message = "Number of floors can't be empty!")
    private Integer numOfFloors;

}

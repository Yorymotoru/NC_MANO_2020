package com.example.demo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;

@SpringBootApplication
@RestController
public class DemoApplication {
    ArrayList<Building> buildings = new ArrayList<>();
    private static Logger log = LoggerFactory.getLogger(DemoApplication.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/get")
    public ResponseEntity getBuilding(@RequestParam(value = "address", defaultValue = "unknown") String address) {
        Building out = searchBuilding(address);
        if (out != null) {
            return new ResponseEntity<>(out, HttpStatus.OK);
        } else {
            return new ResponseEntity<Building>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Building> addBuilding(@RequestBody Building building) throws Exception {
        try {
            jdbcTemplate.update("INSERT INTO building(address, number_of_floors, residential) VALUES (?, ?, ?)", building.getAddress(),
                    building.getNumberOfFloors(),
                    building.getResidential());
        } catch (Exception JdbcSQLIntegrityConstraintViolationException) {
            throw new Exception("it already exist");
        }
        return new ResponseEntity<>(building, HttpStatus.OK);
    }

    @DeleteMapping("/{address}")
    public ResponseEntity deleteBuilding(@PathVariable String address) {
        Building foundBuilding = searchBuilding(address);
        if (foundBuilding != null) {
            buildings.remove(foundBuilding);
            return new ResponseEntity<Building>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<Building>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{address}")
    public ResponseEntity<Building> putBuilding(@PathVariable String address, @RequestBody Building building) {
        Building foundBuilding = searchBuilding(address);
        if (foundBuilding != null) {
            foundBuilding.setNumberOfFloors(building.getNumberOfFloors());
            foundBuilding.setResidential(building.getResidential());
            return new ResponseEntity<>(foundBuilding, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{address}")
    public ResponseEntity<Building> patchBuilding(@PathVariable String address, @RequestBody Building building) {
        Building foundBuilding = searchBuilding(address);
        if (foundBuilding != null) {
            if (building.getAddress() != null) {
                foundBuilding.setAddress(building.getAddress());
            }
            if (building.getNumberOfFloors() != 0) {
                foundBuilding.setNumberOfFloors(building.getNumberOfFloors());
            }
            if (building.getResidential() != null) {
                foundBuilding.setResidential(building.getResidential());
            }
            return new ResponseEntity<>(foundBuilding, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    public Building searchBuilding(String address) {
        Building out = null;
        for (Building building : buildings) {
            if (building.getAddress().equals(address)) {
                out = building;
            }
        }
        return out;
    }

    public Building searchBuilding(Building newBuilding) {
        Building out = null;
        for (Building building : buildings) {
            if (building.getAddress().equals(newBuilding.getAddress())) {
                out = building;
            }
        }
        return out;
    }
}
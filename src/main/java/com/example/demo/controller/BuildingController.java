package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.domain.Building;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping
public class BuildingController {
    final private static Logger log = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    //private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private Building mapRowToBuilding(ResultSet rs, int rowNum) throws SQLException {
        return new Building(
                rs.getString("address"),
                rs.getInt("number_of_floors"),
                rs.getBoolean("residential")
        );
    }

    public List<Building> getAll() {
        return jdbcTemplate.query(
                "SELECT * FROM building",
                (rs, rowNum) -> new Building(
                        rs.getString("address"),
                        rs.getInt("number_of_floors"),
                        rs.getBoolean("residential")));
    }

    @GetMapping("/get")
    public ResponseEntity getBuilding(@RequestParam(value = "address", defaultValue = "unknown") String address) {
        List<Building> buildingList = getAll();
        Building out = searchBuilding(address, buildingList);
        if (out == null) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<Building>(out, HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Building> addBuilding(@RequestBody Building building) throws Exception {
        try {
            jdbcTemplate.update("INSERT INTO building(address, number_of_floors, residential) VALUES (?, ?, ?)",
                    building.getAddress(),
                    building.getNumberOfFloors(),
                    building.getResidential());
        } catch (Exception JdbcSQLIntegrityConstraintViolationException) {
            throw new Exception("it already exist");
        }
        return new ResponseEntity<>(building, HttpStatus.OK);
    }

//    @DeleteMapping("/{address}")
//    public ResponseEntity deleteBuilding(@PathVariable String address) {
//        Building foundBuilding = searchBuilding(address);
//        if (foundBuilding != null) {
//            buildings.remove(foundBuilding);
//            return new ResponseEntity<Building>(HttpStatus.ACCEPTED);
//        }
//        return new ResponseEntity<Building>(HttpStatus.NOT_FOUND);
//    }

//    @PutMapping("/{address}")
//    public ResponseEntity<Building> putBuilding(@PathVariable String address, @RequestBody Building building) {
//        Building foundBuilding = searchBuilding(address);
//        if (foundBuilding != null) {
//            foundBuilding.setNumberOfFloors(building.getNumberOfFloors());
//            foundBuilding.setResidential(building.getResidential());
//            return new ResponseEntity<>(foundBuilding, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }

//    @PatchMapping("/{address}")
//    public ResponseEntity<Building> patchBuilding(@PathVariable String address, @RequestBody Building building) {
//        Building foundBuilding = searchBuilding(address);
//        if (foundBuilding != null) {
//            if (building.getAddress() != null) {
//                foundBuilding.setAddress(building.getAddress());
//            }
//            if (building.getNumberOfFloors() != 0) {
//                foundBuilding.setNumberOfFloors(building.getNumberOfFloors());
//            }
//            if (building.getResidential() != null) {
//                foundBuilding.setResidential(building.getResidential());
//            }
//            return new ResponseEntity<>(foundBuilding, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }


    public Building searchBuilding(String address, List<Building> buildings) {
        Building out = null;
        for (Building building : buildings) {
            if (building.getAddress().equals(address)) {
                out = building;
            }
        }
        return out;
    }

    public Building searchBuilding(Building newBuilding, List<Building> buildings) {
        Building out = null;
        for (Building building : buildings) {
            if (building.getAddress().equals(newBuilding.getAddress())) {
                out = building;
            }
        }
        return out;
    }
}
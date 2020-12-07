package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.domain.Building;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class BuildingController {
    final private static Logger log = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;
    //private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

//    private Building mapRowToBuilding(ResultSet rs, int rowNum) throws SQLException {
//        return new Building(
//                rs.getString("address"),
//                rs.getInt("number_of_floors"),
//                rs.getBoolean("residential")
//        );
//    }

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
            return new ResponseEntity<>(out, HttpStatus.OK);
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

    @DeleteMapping("/{address}")
    public ResponseEntity deleteBuilding(@PathVariable String address) {
        jdbcTemplate.update("DELETE FROM building WHERE address = ?", address);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{address}")
    public ResponseEntity putBuilding(@PathVariable String address, @RequestBody Building building) {
        try {
            jdbcTemplate.update("UPDATE building " +
                            "SET address = ?, number_of_floors = ?, residential = ?" +
                            "WHERE address = ?",
                    building.getAddress(),
                    building.getNumberOfFloors(),
                    building.getResidential(),
                    address);
        } catch (Exception JdbcSQLIntegrityConstraintViolationException) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(building, HttpStatus.OK);
    }

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

//    public Building searchBuilding(Building newBuilding, List<Building> buildings) {
//        Building out = null;
//        for (Building building : buildings) {
//            if (building.getAddress().equals(newBuilding.getAddress())) {
//                out = building;
//            }
//        }
//        return out;
//    }
}
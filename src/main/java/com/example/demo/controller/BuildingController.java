package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.domain.Building;
import com.example.demo.service.BuildingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class BuildingController {
    final private static Logger log = LoggerFactory.getLogger(DemoApplication.class);

    @Autowired
    BuildingService buildingService;

    @GetMapping("/get")
    public ResponseEntity getBuilding(@RequestParam(value = "address", defaultValue = "unknown") String address) {
        Building out = buildingService.searchBuilding(address);
        log.info("GET request for address: " + address);
        if (out == null) {
            log.info("Building with address '" + address + "' not found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            log.info("Founded: " + out);
            return new ResponseEntity<>(out, HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Building> addBuilding(@RequestBody Building building) throws Exception {
        log.info("POST request for " + building);
        try {
            buildingService.insertBuilding(building);
        } catch (Exception JdbcSQLIntegrityConstraintViolationException) {
            log.info("Building is already exist");
            throw new Exception("it already exist");
        }
        log.info("Building posted");
        return new ResponseEntity<>(building, HttpStatus.OK);
    }

    @DeleteMapping("/{address}")
    public ResponseEntity deleteBuilding(@PathVariable String address) {
        log.info("DELETE request for address: " + address);
        buildingService.delBuilding(address);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{address}")
    public ResponseEntity putBuilding(@PathVariable String address, @RequestBody Building building) {
        log.info("PUT request for address: " + address + " with " + building);
        Building foundBuilding = buildingService.searchBuilding(address);
        if (foundBuilding != null) {
            buildingService.delBuilding(address);
            building.setAddress(address);
            buildingService.insertBuilding(building);
            log.info("Building has put");
            return new ResponseEntity<>(building, HttpStatus.OK);
        }
        else {
            log.info("Building not found");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{address}")
    public ResponseEntity<Building> patchBuilding(@PathVariable String address, @RequestBody Building building) {
        log.info("PATCH request for address: " + address + " with " + building);
        Building foundBuilding = buildingService.searchBuilding(address);
        boolean flagNewAddressNotExist = true;
        if (!building.getAddress().equals(address) && buildingService.searchBuilding(building.getAddress()) != null) {
            flagNewAddressNotExist = false;
        }
        if (foundBuilding != null && flagNewAddressNotExist) {
            if (building.getAddress() != null) {
                foundBuilding.setAddress(building.getAddress());
            }
            if (building.getNumberOfFloors() != 0) {
                foundBuilding.setNumberOfFloors(building.getNumberOfFloors());
            }
            if (building.getResidential() != null) {
                foundBuilding.setResidential(building.getResidential());
            }
            buildingService.delBuilding(address);
            buildingService.insertBuilding(foundBuilding);
            log.info("Building has patch: " + foundBuilding);
            return new ResponseEntity<>(foundBuilding, HttpStatus.OK);
        } else {
            log.info("Building not found or new address already exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
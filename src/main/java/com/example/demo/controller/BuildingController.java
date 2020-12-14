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
    public ResponseEntity getBuilding(@RequestParam(value = "id", defaultValue = "0") int id) {
        Building out = buildingService.search(id);
        log.info("GET request for id: " + id);
        if (out == null) {
            log.info("Building with id '" + id + "' not found");
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
            buildingService.insert(building);
        } catch (Exception JdbcSQLIntegrityConstraintViolationException) {
            log.info("Building is already exist");
            throw new Exception("it already exist");
        }
        log.info("Building posted");
        return new ResponseEntity<>(building, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBuilding(@PathVariable int id) {
        log.info("DELETE request for id: " + id);
        buildingService.del(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity putBuilding(@PathVariable int id, @RequestBody Building building) {
        log.info("PUT request for id: " + id + " with " + building);
        if (buildingService.put(id, building)) {
            log.info("Building has put");
            return new ResponseEntity<>(building, HttpStatus.OK);
        }
        else {
            log.info("Building not found or new address is occupied");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Building> patchBuilding(@PathVariable int id, @RequestBody Building building) {
        log.info("PATCH request for id: " + id + " with " + building);
        Building newBuilding = buildingService.patch(id, building);
        if (newBuilding != null) {
            log.info("Building has patch: " + newBuilding);
            return new ResponseEntity<>(newBuilding, HttpStatus.OK);
        } else {
            log.info("Building not found or new address already exist");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
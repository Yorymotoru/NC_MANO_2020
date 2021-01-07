package com.example.demo.frontend.controller;

import com.example.demo.frontend.domain.UiBuilding;
import com.example.demo.frontend.service.UiBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ui")
public class UiController {

    private final UiBuildingService uiBuildingService;

    @Autowired
    UiController(UiBuildingService uiBuildingService) {
        this.uiBuildingService = uiBuildingService;
    }

    @GetMapping("/building/all")
    public ResponseEntity<List<UiBuilding>> getAllBuildings() {
        List<UiBuilding> uiBuildings = uiBuildingService.getAll();
        return new ResponseEntity<>(uiBuildings, HttpStatus.OK);
    }

    @GetMapping("/building/get")
    public ResponseEntity<UiBuilding> getBuilding(@RequestParam(value = "id", defaultValue = "0") int id) {
        UiBuilding out = uiBuildingService.getOne(id);
        if (out == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            return new ResponseEntity<>(out, HttpStatus.OK);
        }
    }

    @DeleteMapping("/building")
    public ResponseEntity deleteBuilding(@PathVariable int id) {
        uiBuildingService.del(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

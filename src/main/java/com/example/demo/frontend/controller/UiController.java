package com.example.demo.frontend.controller;

import com.example.demo.frontend.domain.UiBuilding;
import com.example.demo.frontend.service.UiBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<UiBuilding>> getAll() {
        List<UiBuilding> uiBuildings = uiBuildingService.getAll();
        return new ResponseEntity<>(uiBuildings, HttpStatus.OK);
    }
}

package com.example.demo.frontend.controller;

import com.example.demo.frontend.service.UiBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {

    private final UiBuildingService buildingService;

    @Autowired
    WebController(UiBuildingService buildingService) {
        this.buildingService = buildingService;
    }

    @Value("${welcome.message}")
    String message;

    @Value("${error.message}")
    String errorMessage;

//    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
//    public String index(Model model) {
//
//        model.addAttribute("message", message);
//        return "index";
//    }

    @RequestMapping(value = { "/", "/index" }, method = RequestMethod.GET)
    public String buildingList(Model model) {
        model.addAttribute("buildings", buildingService.getAll());
        return "index";
    }

    @RequestMapping(value = {"/removeBuilding/{building_id}"}, method = RequestMethod.GET)
    public String removeBuilding(@PathVariable(value = "building_id") Integer id, Model model) {
        buildingService.del(id);
        model.addAttribute("buildings", buildingService.getAll());
        return "redirect:/index";
    }



}

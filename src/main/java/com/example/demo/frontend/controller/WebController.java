package com.example.demo.frontend.controller;

import com.example.demo.backend.domain.Building;
import com.example.demo.frontend.domain.UiBuilding;
import com.example.demo.frontend.form.UiBuildingForm;
import com.example.demo.frontend.service.UiBuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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

    @RequestMapping(value = "/removeBuilding/{building_id}", method = RequestMethod.GET)
    public String removeBuilding(@PathVariable(value = "building_id") Integer id, Model model) {
        buildingService.del(id);
        model.addAttribute("buildings", buildingService.getAll());
        return "redirect:/index";
    }

    @RequestMapping(value = "/addBuilding", method = RequestMethod.GET)
    public String formBuilding(Model model) {
        UiBuildingForm uiBuildingForm = new UiBuildingForm();
        model.addAttribute("uiBuildingForm", uiBuildingForm);
        return "form";
    }

    @RequestMapping(value = "/addBuilding", method = RequestMethod.POST)
    public String addBuilding(Model model,
                              @ModelAttribute("uiBuildingForm") UiBuildingForm uiBuildingForm) {
        String address = uiBuildingForm.getAddress();
        Integer numOfFloors = uiBuildingForm.getNumOfFloors();

        if (address != null && address.length() > 0 &&
            numOfFloors != null && numOfFloors > 0)
        {
            UiBuilding uiBuilding = new UiBuilding();
            uiBuilding.setAddress(address);
            uiBuilding.setNumberOfFloors(numOfFloors);

            buildingService.save(uiBuilding);
        }
        return "redirect:/index";
    }

}

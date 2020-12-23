package com.example.demo.frontend.builder;

import com.example.demo.backend.domain.Building;
import com.example.demo.frontend.domain.UiBuilding;
import org.springframework.stereotype.Service;

@Service
public class BuildingBuilder implements Builder<Building, UiBuilding> {

    @Override
    public Building encode(UiBuilding uiBuilding) {
        Building b = new Building();
        b.setAddress(uiBuilding.getAddress());
        b.setNumberOfFloors(uiBuilding.getNumberOfFloors());
        return b;
    }

    @Override
    public UiBuilding decode(Building building) {
        UiBuilding ub = new UiBuilding();
        ub.setAddress(building.getAddress());
        ub.setNumberOfFloors(building.getNumberOfFloors());
        return ub;
    }
}

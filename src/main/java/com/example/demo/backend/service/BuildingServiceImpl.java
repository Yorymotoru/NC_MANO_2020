package com.example.demo.backend.service;

import com.example.demo.backend.domain.Building;
import com.example.demo.backend.repository.BuildingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class BuildingServiceImpl implements BuildingService {

    private final BuildingRepository buildingRepository;

    @Autowired
    BuildingServiceImpl(BuildingRepository buildingRepository) {
        this.buildingRepository = buildingRepository;
    }

    @Override
    public List<Building> getAll() {
        return buildingRepository.findAll();
    }

    @Override
    public Building search(int id) {
        return buildingRepository.findBuildingById(id);
    }

    @Override
    public void insert(@RequestBody Building building) {
        buildingRepository.save(building);
    }

    @Override
    public int del(int id) {
        Building currentBuilding = buildingRepository.findBuildingById(id);
        if (currentBuilding != null) {
            buildingRepository.deleteById(id);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean put(int id, Building building) {
        if (building.getId() == id || search(building.getId()) == null) {
            building.setId(id);
            if (buildingRepository.findBuildingById(id) == null) {
                return false;
            } else {
                buildingRepository.save(building);
                return true;
            }
        }
        return false;
    }

    @Override
    public Building patch(int id, Building building) {
        Building foundBuilding = search(id);
        boolean flagNewIdNotExist = true;
        if (building.getId() != null && !(building.getId() == id) && search(building.getId()) != null) {
            flagNewIdNotExist = false;
        }
        if (foundBuilding != null && flagNewIdNotExist) {
            if (building.getId() != null) {
                foundBuilding.setId(building.getId());
            }
            if (building.getAddress() != null) {
                foundBuilding.setAddress(building.getAddress());
            }
            if (building.getNumberOfFloors() != null) {
                foundBuilding.setNumberOfFloors(building.getNumberOfFloors());
            }
            if (building.getResidential() != null) {
                foundBuilding.setResidential(building.getResidential());
            }
            put(id, foundBuilding);
            return foundBuilding;
        } else {
            return null;
        }
    }
}

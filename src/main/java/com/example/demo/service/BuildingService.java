package com.example.demo.service;

import com.example.demo.domain.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Service
public class BuildingService {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    BuildingService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertBuilding(@RequestBody Building building) {
        jdbcTemplate.update("INSERT INTO building(address, number_of_floors, residential) VALUES (?, ?, ?)",
                building.getAddress(),
                building.getNumberOfFloors(),
                building.getResidential());
    }

    public void delBuilding(String address) {
        jdbcTemplate.update("DELETE FROM building WHERE address = ?", address);
    }

    public List<Building> getAll() {
        return jdbcTemplate.query(
                "SELECT * FROM building",
                (rs, rowNum) -> new Building(
                        rs.getString("address"),
                        rs.getInt("number_of_floors"),
                        rs.getBoolean("residential")));
    }

    public Building searchBuilding(String address) {
        List<Building> buildings = getAll();
        Building out = null;
        for (Building building : buildings) {
            if (building.getAddress().equals(address)) {
                out = building;
            }
        }
        return out;
    }
}

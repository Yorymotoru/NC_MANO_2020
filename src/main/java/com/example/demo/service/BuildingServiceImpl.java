package com.example.demo.service;

import com.example.demo.domain.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BuildingServiceImpl implements BuildingService{
    final private JdbcTemplate jdbcTemplate;
    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    BuildingServiceImpl(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private Building mapRowToBuilding(ResultSet rs, int rowNum) throws SQLException {
        return new Building(
                rs.getInt("id"),
                rs.getString("address"),
                rs.getInt("number_of_floors"),
                rs.getBoolean("residential")
        );
    }

    private Map<String, Object> mapBuildingToParams(Building building) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", building.getId());
        params.put("address", building.getAddress());
        params.put("number_of_floors", building.getNumberOfFloors());
        params.put("residential", building.getResidential());
        return params;
    }

    @Override
    public List<Building> getAll() {
        return namedParameterJdbcTemplate.query("SELECT * FROM building", this::mapRowToBuilding);
    }

    @Override
    public Building search(int id) {
        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM building WHERE id = (:id)",
                Collections.singletonMap("id", id), this::mapRowToBuilding);
    }

    @Override
    public void insert(@RequestBody Building building) {
        jdbcTemplate.update("INSERT INTO building(id, address, number_of_floors, residential) VALUES (?, ?, ?, ?)",
                building.getId(),
                building.getAddress(),
                building.getNumberOfFloors(),
                building.getResidential());
    }

    @Override
    public int del(int id) {
        return namedParameterJdbcTemplate.update("DELETE FROM building WHERE id = (:id)",
                Collections.singletonMap("id", id));
    }

    @Override
    public boolean put(int id, Building building) {
        if (building.getId() == id || search(building.getId()) == null) {
            if (del(id) == 0) {
                return false;
            } else {
                insert(building);
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

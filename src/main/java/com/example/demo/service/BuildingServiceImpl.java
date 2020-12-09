package com.example.demo.service;

import com.example.demo.domain.Building;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
                rs.getString("address"),
                rs.getInt("number_of_floors"),
                rs.getBoolean("residential")
        );
    }

    private Map<String, Object> mapBuildingToParams(Building building) {
        Map<String, Object> params = new HashMap<>();
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
    public Building search(String address) {
        return namedParameterJdbcTemplate.queryForObject("SELECT * FROM building WHERE address = (:address)",
                Collections.singletonMap("address", address), this::mapRowToBuilding);
    }

    @Override
    public void insert(@RequestBody Building building) {
        jdbcTemplate.update("INSERT INTO building(address, number_of_floors, residential) VALUES (?, ?, ?)",
                building.getAddress(),
                building.getNumberOfFloors(),
                building.getResidential());
    }

    @Override
    public int del(String address) {
        return namedParameterJdbcTemplate.update("DELETE FROM building WHERE address = (:address)",
                Collections.singletonMap("address", address));
    }

    @Override
    public boolean put(String address, Building building) {
        if (building.getAddress().equals(address) || search(building.getAddress()) == null) {
            if (del(address) == 0) {
                return false;
            } else {
                insert(building);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean patch(String address, Building building) {
        Building founded = search(address);
        if (founded == null) {
            return false;
        }

        // Формирование sql запроса
        StringBuilder sqlBuilder = new StringBuilder();
        boolean first = true;
        boolean changed = false;

        Map<String, Object> buildingInfo = mapBuildingToParams(building);
        buildingInfo.values().removeIf(Objects::isNull);
        for (Map.Entry<String, Object> entry : buildingInfo.entrySet()) {
            try {
                Field f = founded.getClass().getDeclaredField(entry.getKey());
                f.setAccessible(true);
                if (!f.get(founded).equals(entry.getValue())) {
                    if (!first) {
                        sqlBuilder.append(", ");
                    }
                    first = false;
                    changed = true;
                    sqlBuilder.append(entry.getKey())
                            .append(" = :")
                            .append(entry.getKey());
                }
            } catch (IllegalAccessException | NoSuchFieldException e) {
//                System.err.println(e.getMessage());
            }
        }

        if (changed) {
            buildingInfo.put("address", address);
            String sql = "UPDATE building SET " + sqlBuilder.toString() + " WHERE address = :address";
            namedParameterJdbcTemplate.update(sql, buildingInfo);
            return true;
        }
        return false;
    }
}

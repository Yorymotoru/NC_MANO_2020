package com.example.demo.frontend.service;

import com.example.demo.backend.domain.Building;
import com.example.demo.frontend.builder.BuildingBuilder;
import com.example.demo.frontend.domain.UiBuilding;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
//@ConfigurationProperties(prefix = "backend")
public class UiBuildingService {

    private final RestTemplate restTemplate;
    private final BuildingBuilder buildingBuilder;

    @Getter
    @Setter
    private String server = "http://localhost";

    @Getter
    @Setter
    private String port = "8080";

    @Autowired
    public UiBuildingService(BuildingBuilder buildingBuilder) {
        this.buildingBuilder = buildingBuilder;
        this.restTemplate = new RestTemplate();
    }

    public List<UiBuilding> getAll() {
        Building[] buildings = restTemplate.getForEntity(server + ":" + port + "/building/all", Building[].class)
                .getBody();
        List<UiBuilding> uiBuildings = new ArrayList<>();

        if (buildings != null) {
            for (Building b : buildings) {
                uiBuildings.add(buildingBuilder.decode(b));
            }
        }

        uiBuildings.sort(Comparator.comparing(UiBuilding::getId));

        return uiBuildings;
    }

    public UiBuilding getOne(Integer id) {
        Building building;
        try {
            building = restTemplate.getForEntity(server + ":" + port + "/building/get?id=" + id, Building.class)
                    .getBody();
        } catch (HttpClientErrorException e) {
            building = null;
        }
        return building != null ? buildingBuilder.decode(building) : null;
    }

    public void del(Integer id) {
        restTemplate.delete(server + ":" + port + "/building/" + id, Building.class);
    }

    public void save(UiBuilding uiBuilding) {
        Building building = buildingBuilder.encode(uiBuilding);
        restTemplate.postForEntity(server + ":" + port + "/building/add", building, Building.class);
    }

}

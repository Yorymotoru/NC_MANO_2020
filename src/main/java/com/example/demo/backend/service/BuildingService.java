package com.example.demo.backend.service;

import com.example.demo.backend.domain.Building;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BuildingService {

    public List<Building> getAll();

    public Building getOne(int id);

    public void insert(@RequestBody Building building);

    public int del(int id);

    public boolean put(int id, Building building);

    public Building patch(int id, Building building);

}

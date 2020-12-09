package com.example.demo.service;

import com.example.demo.domain.Building;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BuildingService {
    public List<Building> getAll();

    public Building search(String address);

    public void insert(@RequestBody Building building);

    public int del(String address);

    public boolean put(String address, Building building);

    public boolean patch(String address, Building building);
}

package com.example.demo.repository;

import com.example.demo.domain.Building;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BuildingRepository extends CrudRepository<Building, Integer> {

    List<Building> findAll();

    Building findBuildingById(Integer id);

    Building save(Building building);

    void deleteById(Integer id);

}

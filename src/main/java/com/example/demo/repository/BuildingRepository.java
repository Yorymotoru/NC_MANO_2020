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

//    Building saveBuildingById(Building building, Integer id);

    /*
    @Modifying
    @Query(value = "UPDATE building " +
            "SET address = :address, number_of_floors = :number_of_floors, residential = :residential " +
            "WHERE id = :id",
            nativeQuery = true)
    void updateBuildingById(@Param("id") Integer id,
                            @Param("address") String address,
                            @Param("number_of_floors") Integer numberOfFloors,
                            @Param("residential") Boolean residential);

    @Modifying
    @Query(value = "INSERT INTO building (id, address)  VALUES (?1, ?2)",
            nativeQuery = true)
    int updateAddressById(Integer id, String address);
     */

}

package com.example.demo.backend.repository;

import com.example.demo.backend.domain.Tenant;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TenantRepository extends CrudRepository<Tenant, Integer> {

    List<Tenant> findAll();

    Tenant findTenantById(Integer Id);

}

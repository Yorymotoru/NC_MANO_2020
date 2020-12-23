package com.example.demo.service;

import com.example.demo.domain.Building;
import com.example.demo.domain.Tenant;
import com.example.demo.repository.BuildingRepository;
import com.example.demo.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class TenantServiceImpl implements TenantService {

    final private TenantRepository tenantRepository;
    final private BuildingRepository buildingRepository;

    @Autowired
    public TenantServiceImpl(TenantRepository tenantRepository, BuildingRepository buildingRepository) {
        this.tenantRepository = tenantRepository;
        this.buildingRepository = buildingRepository;
    }

    @Override
    public List<Tenant> getAll() {
        return tenantRepository.findAll();
    }

    @Override
    public Tenant search(int id) {
        return tenantRepository.findTenantById(id);
    }

    @Override
    public void insert(Tenant tenant) {
        tenant.setBuilding (
                buildingRepository.findBuildingById (
                        tenant.getBuilding().getId()
                )
        );
        tenantRepository.save(tenant);
    }

    @Override
    public int del(int id) {
        Tenant currentBuilding = search(id);
        if (currentBuilding != null) {
            tenantRepository.deleteById(id);
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public Tenant put(int id, Tenant tenant) {
        if (tenant.getId() == id || search(tenant.getId()) == null) {
            tenant.setId(id);
            if (search(id) == null) {
                return null;
            } else {
                tenantRepository.save(tenant);
                return tenant;
            }
        }
        return null;
    }

    @Override
    public Tenant patch(int id, Tenant tenant) {
        Tenant foundTenant = search(id);
        boolean flagNewIdNotExist = true;
        if (tenant.getId() != null && !(tenant.getId() == id) && search(tenant.getId()) != null) {
            flagNewIdNotExist = false;
        }
        if (foundTenant != null && flagNewIdNotExist) {
            if (tenant.getId() != null) {
                foundTenant.setId(tenant.getId());
            }
            if (tenant.getBuilding() != null) {
                foundTenant.setBuilding(tenant.getBuilding());
            }
            if (tenant.getFirstName() != null) {
                foundTenant.setFirstName(tenant.getFirstName());
            }
            if (tenant.getLastName() != null) {
                foundTenant.setLastName(tenant.getLastName());
            }
            foundTenant = put(id, foundTenant);
            return foundTenant;
        } else {
            return null;
        }
    }

}

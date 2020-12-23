package com.example.demo.backend.service;

import com.example.demo.backend.domain.Tenant;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface TenantService {

    public List<Tenant> getAll();

    public Tenant search(int id);

    public void insert(@RequestBody Tenant tenant);

    public int del(int id);

    public Tenant put(int id, Tenant tenant);

    public Tenant patch(int id, Tenant tenant);

}

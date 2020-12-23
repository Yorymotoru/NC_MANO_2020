package com.example.demo.backend.controller;

import com.example.demo.DemoApplication;
import com.example.demo.backend.domain.Tenant;
import com.example.demo.backend.service.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tenant")
public class TenantController {
    final private static Logger log = LoggerFactory.getLogger(DemoApplication.class);
    final private TenantService tenantService;

    @Autowired
    TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    @GetMapping("/get")
    public ResponseEntity getTenant(@RequestParam(value = "id", defaultValue = "0") int id) {
        Tenant out = tenantService.search(id);
        log.info("GET request for tenant id: " + id);
        if (out == null) {
            log.info("Tenant with id '" + id + "' not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("Founded: " + out);
            return new ResponseEntity<>(out, HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Tenant> addTenant(@RequestBody @Valid Tenant tenant, BindingResult bindingResult) throws Exception {
        log.info("POST request for " + tenant);
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(tenant, HttpStatus.FORBIDDEN);
        }
        if (tenant.getId() == null || tenantService.search(tenant.getId()) == null) {
            tenantService.insert(tenant);
        } else {
            log.info("Tenant is already exist");
            return new ResponseEntity<>(tenant, HttpStatus.CONFLICT);
        }
        log.info("Tenant posted");
        return new ResponseEntity<>(tenant, HttpStatus.OK);
    }
}

package com.example.company.controller;

import com.example.company.entity.Employee;
import com.example.company.service.CompanyService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class CompanyController {

    private final CompanyService service;
    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping
    public List<Employee> getAllProducts() {
        return this.service.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        return service.getEmployeeById(id);
    }
}

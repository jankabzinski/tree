package com.example.company.service;

import com.example.company.entity.Employee;
import com.example.company.repo.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {


    private CompanyRepository repository;

    @Autowired
    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }
    public List<Employee> getAllEmployees() {

        return repository.findAll();
    }
    public Employee getEmployeeById(long id){
        return repository.findById(id).get();
    }
}

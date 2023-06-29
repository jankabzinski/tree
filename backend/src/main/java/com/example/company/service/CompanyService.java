package com.example.company.service;

import com.example.company.entity.Employee;
import com.example.company.repo.CompanyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyService {
    private final CompanyRepository repository;

    @Autowired
    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    public List<Employee> getAllEmployees(Sort.Direction order, String sortBy) {
        return repository.findAll(Sort.by(order, sortBy));
    }

    public Optional<Employee> getEmployeeById(long id) {
        return repository.findById(id);
    }

    public Employee addNewEmployee(Employee newEmployee) {
        return repository.save(newEmployee);
    }

    public Employee replaceEmployeeById(Employee newEmployee, Long id) {
        return repository.findById(id).map(employee -> {
            employee.setJob(newEmployee.getJob());
            employee.setName(newEmployee.getName());
            return repository.save(employee);
        }).orElseGet(() -> repository.save(newEmployee));
    }

    public void deleteEmployeeById(Long id) {
        repository.deleteById(id);
    }
}

package com.example.company.controller;

import com.example.company.entity.Employee;
import com.example.company.service.CompanyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort.Direction;

import java.util.List;
import java.util.Objects;

import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public class CompanyController {

    private final CompanyService service;
    private final RequestCounter requestCounter;

    public CompanyController(CompanyService service, RequestCounter requestCounter) {
        this.service = service;
        this.requestCounter = requestCounter;
    }

    @GetMapping
    public ResponseEntity<Object> getAllEmployees(@RequestParam(defaultValue = "asc") String order, @RequestParam(defaultValue = "id") String sortBy) {
        try {
            requestCounter.incrementCount();
            Direction sortOrder = order.equals("desc") ? Direction.DESC : Direction.ASC;
            List<Employee> employees = this.service.getAllEmployees(sortOrder, sortBy);

            if (employees.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(employees, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmployee(@PathVariable Long id) {
        requestCounter.incrementCount();
        var result = service.getEmployeeById(id);
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @PostMapping()
    public ResponseEntity<Object> addNewEmployee(@RequestBody Employee newEmployee) {
        requestCounter.incrementCount();
        if (service.getEmployeeById(newEmployee.getId()).isPresent()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.addNewEmployee(newEmployee), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        requestCounter.incrementCount();
        if (!Objects.equals(newEmployee.getId(), id) ||
                service.getEmployeeById(newEmployee.getId()).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(service.replaceEmployeeById(newEmployee, id), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmployeeById(@PathVariable Long id) {
        requestCounter.incrementCount();
        if (service.getEmployeeById(id).isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        service.deleteEmployeeById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

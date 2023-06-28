package com.example.company.controller;

import com.example.company.entity.Employee;
import com.example.company.service.CompanyService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<Employee> getAllEmployees() {
        requestCounter.incrementCount();
        System.out.println(requestCounter.getCount());
        return this.service.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployee(@PathVariable Long id) {
        requestCounter.incrementCount();
        return service.getEmployeeById(id);
    }

    @PostMapping()
    public Employee addNewEmployee(@RequestBody Employee newEmployee) {
        requestCounter.incrementCount();
        return service.addNewEmployee(newEmployee);
    }

    @PutMapping("/{id}")
    Employee replaceEmployee(@RequestBody Employee newEmployee, @PathVariable Long id) {
        requestCounter.incrementCount();
        return service.replaceEmployeeById(newEmployee, id);
    }

    @DeleteMapping("/{id}")
    void deleteEmployeeById(@RequestBody Long id){
        requestCounter.incrementCount();
        service.deleteEmployeeById(id);
    }
}

package com.example.company.entity;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

    @Id
    private Long id;

    private String name;

    private String job;

    /*@Column(name = "manager_id")
    private Long managerId;

    @Column(name = "hire_date", nullable = false)
    private String hireDate;

    @Column(name = "base_salary",nullable = false)
    private Double baseSalary;

    @Column(name = "bonus_salary")
    private Double bonusSalary;

    @Column(name = "department_id", nullable = false)
    private Long departmentId;
    */

}



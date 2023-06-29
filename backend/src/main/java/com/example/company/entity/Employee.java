package com.example.company.entity;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="employee")
public class Employee {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "job",nullable = false)
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



import { Component, OnInit } from '@angular/core';
import { Employee } from '../models/employee.model';
import { EmployeeService } from '../services/employee.service';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {
  employees: Employee[] = [];
  cols: any[] = [];

  constructor(private EmployeeService: EmployeeService) {}

  ngOnInit(): void {
    this.cols = [
      {field:"id", header: "Employee ID"},
      {field:"name", header: "Name"},
      {field:"job", header: "Job"},
    ]
    this.getEmployees();     
  }

  private getEmployees(): void {
    this.EmployeeService.getEmployees().subscribe(
      (json: any) => {
        this.employees = json;
      }
    );
  }

}

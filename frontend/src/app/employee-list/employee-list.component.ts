import { Component, OnInit, ViewChild } from '@angular/core';
import { Table } from 'primeng/table';
import { Employee } from '../models/employee.model';
import { EmployeeService } from '../services/employee.service';

@Component({
  selector: 'app-employee-list',
  templateUrl: './employee-list.component.html',
  styleUrls: ['./employee-list.component.css']
})
export class EmployeeListComponent implements OnInit {
  employees: Employee[] = [];
  cols = [
    { field: "id", header: "Employee ID" },
    { field: "name", header: "Name" },
    { field: "job", header: "Job" }]
  @ViewChild('dataTable', { static: true }) dataTable!: Table;

  constructor(private EmployeeService: EmployeeService) { }

  ngOnInit(): void {
    this.getEmployees();
    this.dataTable.reset();
    this.dataTable.totalRecords = this.employees.length;
    this.dataTable.first = 0;
    this.dataTable.rows = 10;
    //this.dataTable.first=0;
  }

  private getEmployees(): void {
    this.EmployeeService.getEmployees().subscribe(
      (json: any) => {
        this.employees = json;
      }
    );
  }
  deleteEmployee(employee: Employee): void {
    this.EmployeeService.deleteEmployee(employee).subscribe(
      () => this.getEmployees()
    )
  }
}

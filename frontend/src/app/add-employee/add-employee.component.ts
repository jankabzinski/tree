import { Component } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { EmployeeService } from '../services/employee.service';

@Component({
  selector: 'app-add-employee',
  templateUrl: './add-employee.component.html',
  styleUrls: ['./add-employee.component.css']
})
export class AddEmployeeComponent {
  public form: FormGroup;

  constructor(
    public formBuilder: FormBuilder,
    private employeeService: EmployeeService
  ) {
    this.form = this.formBuilder.group({
      id: [''],
      name: [''],
      job: ['']
    });
  }

  submitForm(event: any) {
    event.preventDefault();




    this.employeeService.addEmployee({"id":this.form.value.id,"name":this.form.value.name, "job":this.form.value.job}).subscribe({
      complete: () => {
        this.form.reset();
      }
    });
  }
}

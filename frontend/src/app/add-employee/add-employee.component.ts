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

  constructor(public formBuilder: FormBuilder, private EmployeeService: EmployeeService
  ) {
    this.form = this.formBuilder.group({
      id: '',
      name: '',
      job: ''
    });
  }
  submitForm(event: any) {
    console.log(this.form)
    var formData = new FormData();
    formData.append('id', this.form.get('id')!.value)
    formData.append('name', this.form.get('name')!.value)
    formData.append('job', this.form.get('job')!.value)
    this.EmployeeService.addEmployee(formData).subscribe(
      {
        complete: () => {
          this.form.reset({
            id: '',
            name: '',
            job: ''
          });
        }
      }
    )
  }
}

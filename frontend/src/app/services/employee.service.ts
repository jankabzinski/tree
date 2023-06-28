import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Injectable({
    providedIn: 'root',
})

export class EmployeeService {
    readonly url: string = 'localhost:8080';
    constructor(private http: HttpClient) { }
    getEmployees() {
        return this.http.get<any>(
            this.url + '/employees'
        );
    }
}
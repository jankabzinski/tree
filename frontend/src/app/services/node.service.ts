import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";
import { Node } from "../models/node.model";
import { Observable } from "rxjs/internal/Observable";

@Injectable({
    providedIn: 'root',
})

export class NodeService {
    readonly url: string = 'http://localhost:8080/';

    constructor(private http: HttpClient) { }

    getNode(): Observable<Node[]> {
        return this.http.get<Node[]>(
            `${this.url}nodes`
        );
    }

    addNode(json: any) {
        return this.http.post(
            `${this.url}nodes`, json
        );
    }

    public deleteNode(json: any) {
        console.log(json)
        return this.http.delete(
            `${this.url}nodes/` + json.id, json.id

        )
    }
}

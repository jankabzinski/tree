import {Injectable} from "@angular/core";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Node} from "../models/node.model";
import {Observable} from "rxjs/internal/Observable";

@Injectable({
  providedIn: 'root',
})

export class NodeService {
  readonly url: string = 'http://localhost:8080/';

  constructor(private http: HttpClient) {
  }

  getNodes() {
    return this.http.get<Node[]>(
      `${this.url}nodes`
    );
  }

  addNode(json: any) {
    console.log(json)
    return this.http.post(
      `${this.url}nodes`, {"parent_id":json}
    );
  }

  public deleteNode(json: any) {
    const params = json.parent_id !== null ? json.parent_id : null;
    return this.http.delete(
      `${this.url}nodes/` + json.id + '?parentId='+params
    )
  }

  public updateNode(node:Node){
      return this.http.put(`${this.url}nodes/`+node.id, {
        "id":node.id,
        "parent_id" : node.parent_id,
        "value":node.value
      })
  }


}

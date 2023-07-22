import {Component, OnInit, OnDestroy} from '@angular/core';
import {NodeService} from "../services/node.service";
import {Node} from "../models/node.model";

@Component({
  selector: 'app-tree',
  templateUrl: './tree.component.html',
  styleUrls: ['./tree.component.css']
})
export class TreeComponent implements OnInit {
  nodes: Node[] = [];
  editingNodeId: number | null = null;
  editingNodeValue: number = -1;

  constructor(private nodeService: NodeService) {
  }

  ngOnInit() {
    this.getNodes();
  }

  public addNode(parent_id: any) {

    this.nodeService.addNode(parent_id).subscribe(() => {
      this.getNodes()
    });
  }

  public getNodes() {
    this.nodeService.getNodes().subscribe((data) => {
      this.nodes = this.buildTree(data);
    });
  }

  public deleteNode(node: Node) {
    this.nodeService.deleteNode(node).subscribe(() => {
      this.getNodes();
    });
  }

  buildTree(data: any[]): Node[] {
    const nodeMap = new Map<number, Node>();

    // Tworzymy węzły z podanych danych i dodajemy pole "expanded" z wartością true
    for (const item of data) {
      const {id, parent_id, ...rest} = item;
      const node: Node = {id, parent_id, expanded: true, ...rest};
      node.children = [];
      nodeMap.set(id, node);
    }

    const rootNodes: Node[] = [];
    for (const item of data) {
      const {id, parent_id} = item;
      const node = nodeMap.get(id);

      if (parent_id === null) {
        rootNodes.push(node!);
      } else {
        const parent = nodeMap.get(parent_id);
        if (parent) {
          parent.children!.push(node!);
        }
      }
    }

    return rootNodes;
  }

  editNode(node: Node) {
    this.editingNodeId = node.id;
    this.editingNodeValue = node.value;
  }

  saveNode(node: Node) {
    node.value = this.editingNodeValue;
    this.editingNodeId = null;
    this.nodeService.updateNode(node).subscribe(() => {
      this.getNodes()
    });

  }

  cancelChanges() {
    this.editingNodeId=null;
  }
}

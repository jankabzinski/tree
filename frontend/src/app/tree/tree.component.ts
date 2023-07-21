import {Component, OnInit, OnDestroy} from '@angular/core';
import * as echarts from 'echarts';
import {NodeService} from "../services/node.service";
import {Link} from "../models/link.model";
import {Node} from "../models/node.model";
import {TreeNode} from 'primeng/api';

@Component({
  selector: 'app-tree',
  templateUrl: './tree.component.html',
  styleUrls: ['./tree.component.css']
})
export class TreeComponent implements OnInit {
  nodes: Node[] = [];


  constructor(private nodeService: NodeService) {
  }

  ngOnInit() {
    this.getNodes();
  }

  public getNodes() {
    this.nodeService.getNodes().subscribe((data) => {
      this.nodes =  this.buildTree(data);
    });
  }

  buildTree(data: any[]): Node[] {
    const nodeMap = new Map<number, Node>();

    // Tworzymy węzły z podanych danych i dodajemy pole "expanded" z wartością true
    for (const item of data) {
      const { id, parent_id, ...rest } = item;
      const node: Node = { id, parent_id, expanded: true, ...rest };
      node.children = [];
      nodeMap.set(id, node);
    }

    const rootNodes: Node[] = [];

    // Tworzymy drzewo, łącząc rodziców z dziećmi
    for (const item of data) {
      const { id, parent_id } = item;
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
}

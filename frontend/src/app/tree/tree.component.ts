import {Component, OnInit, OnDestroy} from '@angular/core';
import * as echarts from 'echarts';
import {NodeService} from "../services/node.service";
import {Link} from "../models/link.model";
import {Node} from "../models/node.model";

@Component({
  selector: 'app-tree',
  templateUrl: './tree.component.html',
  styleUrls: ['./tree.component.css']
})
export class TreeComponent implements OnInit, OnDestroy {
  private chart: any;
  private nodes: Node[] = [];
  private links: Link[] = [];

  constructor(private nodeService: NodeService) {
  }

  ngOnInit(): void {
    this.initChart();

    this.nodeService.getNodes().subscribe((json: any) => {
      // @ts-ignore
      this.nodes = json.map((node) => ({
        ...node,
        parent_id: node.parent_id !== null ? node.parent_id.toString() : node.parent_id,
        id: node.id.toString(),
      }));
      for (const node of this.nodes) {
        if (node.parent_id !== null) {
          const link: Link = {
            source: node.parent_id,
            target: node.id,
          };
          this.links.push(link);
        }
      }
      this.renderGraph();
    });
  }

  ngOnDestroy(): void {
    if (this.chart) {
      this.chart.dispose();
    }
  }

  initChart(): void {
    const chartElement = document.getElementById('chart');
    this.chart = echarts.init(chartElement);
  }

  renderGraph(): void {
    const treeData = [
      {
        name: 'Wierzchołek 1',
        value: 100,
        children: [
          {
            name: 'Podwierzchołek 1.1',
            value: 50,
            children: [
              {
                name: 'Podwierzchołek 1.1.1',
                value: 30,
              },
              {
                name: 'Podwierzchołek 1.1.2',
                value: 20,
              },
            ],
          },
          {
            name: 'Podwierzchołek 1.2',
            value: 70,
          },
        ],
      },
      {
        name: 'Wierzchołek 2',
        value: 200,
        children: [
          {
            name: 'Podwierzchołek 2.1',
            value: 80,
          },
          {
            name: 'Podwierzchołek 2.2',
            value: 120,
          },
        ],
      },
    ];


    const option = {
      series: [
        {
          expandAndCollapse: false,
          type: 'tree',
          data: treeData,
          top: '10%', // Ustaw odpowiednią pozycję wizualizacji na osi Y
          left: '10%', // Ustaw odpowiednią pozycję wizualizacji na osi X
          bottom: '10%', // Ustaw odpowiednią pozycję wizualizacji na osi Y
          right: '10%', // Ustaw odpowiednią pozycję wizualizacji na osi X
          symbol: 'circle', // Ustaw typ symbolu dla wierzchołków (np. 'circle')
          symbolSize: 20, // Ustaw wielkość symbolu dla wierzchołków
          label: {
            show: true, // Pokaż etykiety wierzchołków
            position: 'top', // Ustaw pozycję etykiety (np. 'top', 'inside')
          },
          leaves: {
            label: {
              position: 'bottom', // Ustaw pozycję etykiety dla liści (np. 'bottom', 'inside')
            },
          },
          emphasis: {
            focus: 'descendant', // Podświetlaj potomków w przypadku najechania na wierzchołek
          },
        }]
    };

    this.chart.setOption(option, true);
  }
}

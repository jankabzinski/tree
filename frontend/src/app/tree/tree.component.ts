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
    })
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


    const option = {
      title: {
        text: 'Przykładowy graf ECharts - drzewo'
      },
      animationDurationUpdate: 0,
      animationEasingUpdate: 'quinticInOut',
      series: [{
        type: 'graph',
        layout: 'force', // Układ siłowy
        roam: true,
        symbol: 'circle',
        symbolSize: 60,

        label: {
          show: true,
          position: 'inside',
          formatter: (params: any) => {
            if(params.data.leaf === true) {
              return `Value: ${params.data.value}\n\nSum: ${params.data.sum}`;
            }
            else{
              return `Value: ${params.data.value}`;
            }
          }
        },
        edgeSymbol: ['circle', 'arrow'],
        edgeSymbolSize: [4, 10],
        data: this.nodes,
        links: this.links,
        lineStyle: {
          width: 5
        },
        emphasis: {
          focus: 'adjacency',
          lineStyle: {
            width: 10
          }
        },
        force: {
          repulsion: 200, // Dostosowanie wartości repulsion (zmniejszenie odpychania)
          gravity: 0.02,   // Zwiększenie wartości gravity (większe przyciąganie do centrum)
          edgeLength: [70, 100], // Dostosowanie długości krawędzi (zmniejszenie odległości między wierzchołkami)
          orient: 'TB' // Orientacja drzewa - Top to Bottom (góra na dole)
        },
        itemStyle: {

          color: (params:any)=>{
            if(params.data.parent_id === null)
              return 'red';
          else{
            return 'turquoise';
            }}
        }
      }]
    };

    this.chart.setOption(option, true);
  }
}

import { Component, OnInit, OnDestroy } from '@angular/core';
import * as echarts from 'echarts';

@Component({
  selector: 'app-tree',
  templateUrl: './tree.component.html',
  styleUrls: ['./tree.component.css']
})
export class TreeComponent implements OnInit, OnDestroy {
  private chart: any;

  constructor() { }

  ngOnInit(): void {
    this.initChart();
    this.renderGraph();
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
    const data = {
      nodes: [
        { name: 'Node 1', value: 'First Node' },
        { name: 'Node 2', value: 'Second Node' },
        { name: 'Node 3', value: 'Third Node' },
        { name: 'Node 4', value: 'Fourth Node' },
        { name: 'Node 5', value: 'Fourth Node' },
        { name: 'Node 6', value: 'Fourth Node' },

        { name: 'Node 7', value: 'Fourth Node' },
        { name: 'Node 8', value: 'Fourth Node' },
        { name: 'Node 9', value: 'Fourth Node' },
        { name: 'Node 10', value: 'Fourth Node' },

      ],
      links: [
        { source: 0, target: 1 },
        { source: 0, target: 2 },
        { source: 1, target: 3 },
        { source: 3, target: 4 },
        { source: 2, target: 5 },
        { source: 0, target: 6 },
        { source: 0, target: 9 },

        { source: 3, target: 8},

        { source: 4, target: 7 },


      ]
    };

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
          formatter: '{b}\n\n{c}'
        },
        edgeSymbol: ['circle', 'arrow'],
        edgeSymbolSize: [4, 10],
        data: data.nodes,
        links: data.links,
        lineStyle:{
          width:5
        },
        emphasis: {
          focus: 'adjacency',
          lineStyle: {
            width: 10
          }
        },
        force: {
          repulsion: 100, // Dostosowanie wartości repulsion (zmniejszenie odpychania)
          gravity: 0.02,   // Zwiększenie wartości gravity (większe przyciąganie do centrum)
          edgeLength: [70, 100], // Dostosowanie długości krawędzi (zmniejszenie odległości między wierzchołkami)
          orient: 'LR' // Orientacja drzewa - Top to Bottom (góra na dole)
        }
      }]
    };

    this.chart.setOption(option, true);
  }
}

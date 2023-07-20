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
      ],
      links: [
        { source: 'Node 1', target: 'Node 2' },
        { source: 'Node 1', target: 'Node 3' },
        { source: 'Node 2', target: 'Node 3' },
        { source: 'Node 2', target: 'Node 4' },
        { source: 'Node 3', target: 'Node 4' },
      ]
    };

    const option = {
      title: {
        text: 'Przykładowy graf ECharts - drzewo'
      },
      tooltip: {},
      animationDurationUpdate: 1500,
      animationEasingUpdate: 'quinticInOut',
      series: [{
        type: 'graph',
        layout: 'force',
        roam: true,
        symbol: 'circle', // Kształt wierzchołka jako koło
        symbolSize: 80, // Rozmiar wierzchołka
        label: {
          show: true,
          position: 'inside', // Wyśrodkowane etykiety wewnątrz wierzchołków
          formatter: '{b}\n\n{c}' // {b} to nazwa wierzchołka, {c} to wartość (dodatkowy napis)
        },
        edgeSymbol: ['circle', 'arrow'],
        edgeSymbolSize: [4, 10],
        data: data.nodes,
        links: data.links,
        emphasis: {
          focus: 'adjacency',
          lineStyle: {
            width: 10
          }
        },
        force: {
          repulsion: 300,
          edgeLength: [100,400]
        }
      }]
    };

    this.chart.setOption(option, true);
  }

}

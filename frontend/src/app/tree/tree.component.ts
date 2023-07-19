// tree.component.ts
import { Component } from '@angular/core';

@Component({
  selector: 'app-tree',
  templateUrl: './tree.component.html',
})
export class TreeComponent {
  nodes = [
    { id: '1', label: 'Node 1' },
    { id: '2', label: 'Node 2' },
    { id: '3', label: 'Node 3' },
    // Dodaj pozostałe węzły...
  ];

  links = [
    { source: '1', target: '2' },
    { source: '2', target: '3' },
    // Dodaj pozostałe krawędzie...
  ];
}

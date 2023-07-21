export interface Node {
  id: number;
  parent_id: number | null;
  value: number;
  sum: number;
  label: string;
  expanded: boolean;
  children?: Node[];
}

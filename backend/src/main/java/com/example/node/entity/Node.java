package com.example.node.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Node {

    public Node(Long parent_id, int value, int sum) {
        this.parent_id = parent_id;
        this.value = value;
        this.sum = sum;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parent_id;

    private int value;

    private int sum;

}



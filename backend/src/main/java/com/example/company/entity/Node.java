package com.example.company.entity;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Node {

    @Id
    private Long id;

    private int value;

    private Long parent_id;


}



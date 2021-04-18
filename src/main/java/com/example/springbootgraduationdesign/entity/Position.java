package com.example.springbootgraduationdesign.entity;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int po_id;

    @NotNull
    @Column(length = 20)
    private String po_name;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "po_i_id")
    private Industry po_industry;
}

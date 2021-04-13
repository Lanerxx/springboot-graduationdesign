package com.example.springbootgraduationdesign.entity;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Profession {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pr_id;

    @NotNull
    @Column(length = 20)
    private String pr_m_class;

    @NotNull
    @Column(length = 20)
    private String pr_s_class;
}

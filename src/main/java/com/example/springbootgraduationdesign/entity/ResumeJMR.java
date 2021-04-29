package com.example.springbootgraduationdesign.entity;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class ResumeJMR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jmr_id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "jmr_b_id")
    private ResumeJMRBase jmr_base;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "jmr_r_id")
    private Resume jmr_resume;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "jmr_j_id")
    private Job jmr_job;

    @NotNull
    private double jmr_v_match;

    @NotNull
    private EnumWarehouse.SUCCESS_DEGREE jmr_v_success;

    @NotNull
    private double jmr_v_average;

    @NotNull
    private int jmr_v_popularity;

}

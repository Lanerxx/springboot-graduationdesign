package com.example.springbootgraduationdesign.entity;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
public class StudentJMR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jmr_id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "jmr_b_id")
    private StudentJMRBase jmr_base;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "jmr_s_id")
    private Student jmr_student;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "jmr_r_id")
    private Resume jmr_resume;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "jmr_j_id")
    private Job jmr_job;

    @NotNull
    private float jmr_v_match;

    @NotNull
    private float jmr_v_success;

    @NotNull
    private float jmr_v_average;

    @NotNull
    private float jmr_v_polularity;

}

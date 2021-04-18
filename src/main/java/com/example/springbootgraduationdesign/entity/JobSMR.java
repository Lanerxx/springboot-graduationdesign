package com.example.springbootgraduationdesign.entity;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class JobSMR {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int smr_id;

    @NotNull
    @OneToOne
    @JoinColumn(name = "smr_b_id")
    private JobSMRBase smr_base;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "smr_j_id")
    private Job smr_job;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "smr_r_id")
    private Resume smr_resume;

    @NotNull
    private float smr_v_match;

    @NotNull
    private float smr_v_success;

    @NotNull
    private float smr_v_average;

    @NotNull
    private float smr_v_popularity;
}

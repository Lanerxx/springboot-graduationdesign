package com.example.springbootgraduationdesign.entity;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@Entity
public class JobSMRBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int smr_b_id;

    @NotNull
    private int smr_b_ranking;

    @NotNull
    private int smr_b_gpa;

    @NotNull
    private int smr_b_r_count;

    @NotNull
    private int smr_b_p_count;

    @NotNull
    private int smr_b_s_count;

    @NotNull
    private int smr_b_c_count;

    @NotNull
    private int smr_b_h_count;

    @NotNull
    private int smr_b_postion;

    @NotNull
    private int smr_b_location;

    @NotNull
    private int smr_b_insurance;

    @NotNull
    private int smr_b_check_up;

    @NotNull
    private int smr_b_a_bonus;

    @NotNull
    private int smr_b_p_leave;

    @NotNull
    private int smr_b_o_allowance;

    @NotNull
    private int smr_b_stock;

    @NotNull
    private int smr_b_t_subside;

    @NotNull
    private int smr_b_n_subside;

    @NotNull
    private int smr_b_b_trip;

    @NotNull
    private int smr_b_s_range;

    @NotNull
    private int smr_b_career;

    @NotNull
    private int smr_b_experience;
}

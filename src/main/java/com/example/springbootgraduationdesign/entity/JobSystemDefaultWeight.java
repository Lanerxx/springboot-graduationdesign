package com.example.springbootgraduationdesign.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@Entity
public class JobSystemDefaultWeight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jsdw_id;

    @NotNull
    private double jsdw_ranking;

    @NotNull
    private double jsdw_r_count;

    @NotNull
    private double jsdw_p_count;

    @NotNull
    private double jsdw_s_count;

    @NotNull
    private double jsdw_c_count;

    @NotNull
    private double jsdw_h_count;

    @NotNull
    private double jsdw_position;

    @NotNull
    private double jsdw_location;

    @NotNull
    private double jsdw_insurance;

    @NotNull
    private double jsdw_check_up;

    @NotNull
    private double jsdw_a_bonus;

    @NotNull
    private double jsdw_p_leave;

    @NotNull
    private double jsdw_o_allowance;

    @NotNull
    private double jsdw_b_stock;

    @NotNull
    private double jsdw_t_subside;

    @NotNull
    private double jsdw_h_subside;

    @NotNull
    private double jsdw_b_trip;

    @NotNull
    private double jsdw_c_level;

    @NotNull
    private double jsdw_e_history;

    @NotNull
    private double jsdw_e_language;

    @NotNull
    private double jsdw_s_range;

}

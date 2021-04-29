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
public class ResumeSystemDefaultWeight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rsdw_id;

    @NotNull
    private double rsdw_c_scale;

    @NotNull
    private double rsdw_c_f_stage;

    @NotNull
    private double rsdw_c_level;

    @NotNull
    private double rsdw_c_e_history;

    @NotNull
    private double rsdw_e_language;

    @NotNull
    private double rsdw_j_count;

    @NotNull
    private double rsdw_position;

    @NotNull
    private double rsdw_location;

    @NotNull
    private double rsdw_insurance;

    @NotNull
    private double rsdw_check_up;

    @NotNull
    private double rsdw_a_bonus;

    @NotNull
    private double rsdw_p_leave;

    @NotNull
    private double rsdw_o_allowance;

    @NotNull
    private double rsdw_stock;

    @NotNull
    private double rsdw_t_subside;

    @NotNull
    private double rsdw_h_subside;

    @NotNull
    private double rsdw_b_trip;

    @NotNull
    private double rsdw_s_range;
}

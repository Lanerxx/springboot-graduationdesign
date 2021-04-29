package com.example.springbootgraduationdesign.entity;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
public class ResumeJMRBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jmr_b_id;

    @NotNull
    private int jmr_b_c_scale;

    @NotNull
    private int jmr_b_c_f_stage;

    @NotNull
    private int jmr_b_c_level;

    @NotNull
    private int jmr_e_history;

    @NotNull
    private int jmr_b_e_language;

    @NotNull
    private int jmr_b_j_count;

    @NotNull
    private int jmr_b_position;

    @NotNull
    private int jmr_b_location;

    @NotNull
    private int jmr_b_insurance;

    @NotNull
    private int jmr_b_check_up;

    @NotNull
    private int jmr_b_a_bonus;

    @NotNull
    private int jmr_b_p_leave;

    @NotNull
    private int jmr_b_o_allowance;

    @NotNull
    private int jmr_b_stock;

    @NotNull
    private int jmr_b_t_subside;

    @NotNull
    private int jmr_b_h_subside;

    @NotNull
    private int jmr_b_b_trip;

    @NotNull
    private int jmr_b_s_range;
}

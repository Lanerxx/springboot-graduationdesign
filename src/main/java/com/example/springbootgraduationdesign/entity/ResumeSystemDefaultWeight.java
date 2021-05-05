package com.example.springbootgraduationdesign.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class ResumeSystemDefaultWeight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rsdw_id;
    @NotNull
    private double rsdw_ranking;

    @NotNull
    private double rsdw_r_count;

    @NotNull
    private double rsdw_p_count;

    @NotNull
    private double rsdw_s_count;

    @NotNull
    private double rsdw_c_count;

    @NotNull
    private double rsdw_h_count;

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
    private double rsdw_b_stock;

    @NotNull
    private double rsdw_t_subside;

    @NotNull
    private double rsdw_h_subside;

    @NotNull
    private double rsdw_b_trip;

    @NotNull
    private double rsdw_c_level;

    @NotNull
    private double rsdw_e_history;

    @NotNull
    private double rsdw_e_language;

    @NotNull
    private double rsdw_s_range;

    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;
    @Column(columnDefinition = "timestamp default current_timestamp " +
            "on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;

}

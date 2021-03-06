package com.example.springbootgraduationdesign.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
    private int smr_b_position;

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
    private int smr_b_h_subside;

    @NotNull
    private int smr_b_b_trip;

    @NotNull
    private int smr_b_c_level;

    @NotNull
    private int smr_b_e_history;

    @NotNull
    private int smr_b_e_language;

    @NotNull
    private int smr_b_s_range;

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

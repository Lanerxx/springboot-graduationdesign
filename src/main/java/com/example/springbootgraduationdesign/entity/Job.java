package com.example.springbootgraduationdesign.entity;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Job {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int j_id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "j_c_id")
    private Company j_company;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "j_po_id")
    private Position j_position;


    @NotNull
    @Column(length = 80)
    private String j_location;

    @NotNull
    @Column(length = 200)
    private String j_p_require;

    @NotNull
    @Column(length = 50)
    private String j_remark;

    @NotNull
    private boolean posted;

    @NotNull
    private EnumWarehouse.GENDER j_gender;

    @NotNull
    private EnumWarehouse.E_HISTORY j_e_history;

    @NotNull
    private EnumWarehouse.C_LEVEL j_c_level;

    @NotNull
    private EnumWarehouse.E_LANGUAGE j_e_language;

    @NotNull
    private EnumWarehouse.F_LANGUAGE j_f_language;

    @NotNull
    private EnumWarehouse.S_RANGE j_s_range;

    //学生是否要有实习/工作经验（YES or NO）
    @NotNull
    private EnumWarehouse.IF_IS_OR_NOT j_if_career;

    //学生是否要有项目经验（YES or NO）
    @NotNull
    private EnumWarehouse.IF_IS_OR_NOT j_if_project_experience;

    //学生是否要为应届生 （YES or NO）
    @NotNull
    private EnumWarehouse.IF_IS_OR_NOT j_if_fresh;

    @NotNull
    private EnumWarehouse.IF_IS_OR_NOT j_insurance;

    @NotNull
    private EnumWarehouse.IF_IS_OR_NOT j_check_up;

    @NotNull
    private EnumWarehouse.IF_IS_OR_NOT j_a_bonus;

    @NotNull
    private EnumWarehouse.IF_IS_OR_NOT j_p_leave;

    @NotNull
    private EnumWarehouse.IF_IS_OR_NOT j_o_allowance;

    @NotNull
    private EnumWarehouse.IF_IS_OR_NOT j_stock;

    @NotNull
    private EnumWarehouse.IF_IS_OR_NOT j_t_subside;

    @NotNull
    private EnumWarehouse.IF_IS_OR_NOT j_h_subside;

    @NotNull
    private EnumWarehouse.IF_IS_NEED_OR_NOT j_b_trip;

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

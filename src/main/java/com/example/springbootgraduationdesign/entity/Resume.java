package com.example.springbootgraduationdesign.entity;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class Resume {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int r_id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "r_s_id")
    private Student r_student;
    @NotNull
    @Column(length = 20)
    private String r_e_location;

    @NotNull
    @Column(length = 200)
    private String r_m_course;

    @NotNull
    private int r_p_count;

    @NotNull
    @Column(length = 50)
    private String r_paper;

    @NotNull
    private int r_s_count;

    @NotNull
    @Column(length = 200)
    private String r_skill;

    @NotNull
    private int r_c_count;

    @NotNull
    @Column(length = 50)
    private String r_certificate;

    @NotNull
    private int r_h_count;

    @NotNull
    @Column(length = 50)
    private String r_honor;

    @NotNull
    @Column(length = 200)
    private String r_career;

    @NotNull
    @Column(length = 200)
    private String r_p_experience;

    @NotNull
    @Column(length = 200)
    private String r_s_evaluate;

    @NotNull
    @Column(length = 200)
    private String r_remark;

    @NotNull
    private boolean posted;

    //学生是否对五险一金有要求（NO_REQUIREMENT:可以没有，有更好 YES：必须要有 or NO：完全不需要）
    @NotNull
    private EnumWarehouse.IF_IS_NEED_OR_NOT r_if_insurance;

    //学生是否对定期体检 有要求（NO_REQUIREMENT YES or NO）
    @NotNull
    private EnumWarehouse.IF_IS_NEED_OR_NOT r_if_check_up;

    //学生是否对年终奖 有要求（NO_REQUIREMENT YES or NO）
    @NotNull
    private EnumWarehouse.IF_IS_NEED_OR_NOT r_if_a_bonus;

    //学生是否对带薪年假 有要求（NO_REQUIREMENT YES or NO）
    @NotNull
    private EnumWarehouse.IF_IS_NEED_OR_NOT r_if_p_leave;

    //学生是否对加班补助 有要求（NO_REQUIREMENT YES or NO）
    @NotNull
    private EnumWarehouse.IF_IS_NEED_OR_NOT r_if_o_allowance;

    //学生是否对股票期权 有要求（NO_REQUIREMENT YES or NO）
    @NotNull
    private EnumWarehouse.IF_IS_NEED_OR_NOT r_if_stock;

    //学生是否对交通补贴 有要求（NO_REQUIREMENT YES or NO）
    @NotNull
    private EnumWarehouse.IF_IS_NEED_OR_NOT r_if_t_subside;

    //学生是否对住房补贴 有要求（NO_REQUIREMENT YES or NO）
    @NotNull
    private EnumWarehouse.IF_IS_NEED_OR_NOT r_if_h_subside;

    //学生是否愿意出差（YES or NO）
    @NotNull
    private EnumWarehouse.IF_IS_OR_NOT r_if_b_trip;

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

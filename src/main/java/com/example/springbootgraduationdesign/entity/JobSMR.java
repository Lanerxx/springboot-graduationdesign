package com.example.springbootgraduationdesign.entity;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

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
    private double smr_v_match;//匹配程度

    @NotNull
    private EnumWarehouse.SUCCESS_DEGREE smr_v_success;//成功等级

    @NotNull
    private double smr_v_average;//匹配程度与成功等级的调和平均值

    @NotNull
    private int smr_v_popularity;//匹配到的学生简历被企业选中的次数

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

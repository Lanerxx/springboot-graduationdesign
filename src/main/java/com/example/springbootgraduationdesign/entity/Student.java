package com.example.springbootgraduationdesign.entity;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int s_id;


    @NotNull
    @ManyToOne
    @JoinColumn(name = "s_profession")
    private Profession s_profession;


    @NotNull
    @Column(length = 20)
    private String s_name;

    @NotNull
    @Column(length = 70)
    private String s_password;

    @NotNull
    @Column(length = 20)
    private String s_birthday;

    @NotNull
    @Column(length = 20)
    private String s_college;

    @NotNull
    @Column(length = 30)
    private String s_place;

    @NotNull
    @Column(length = 20)
    private String s_g_time;

    @NotNull
    @Column(length = 20)
    private String s_telephone;

    @NotNull
    @Column(length = 20)
    private String s_email;

    @NotNull
    @Column(length = 30)
    private String s_w_province;

    @NotNull
    @Column(length = 20)
    private String s_company;

    @NotNull
    private double s_gpa;


    @NotNull
    private EnumWarehouse.GENDER s_gender;

    @NotNull
    private EnumWarehouse.C_LEVEL s_c_level;

    @NotNull
    private EnumWarehouse.E_HISTORY s_e_history;

    @NotNull
    private EnumWarehouse.E_LANGUAGE s_e_language;

    @NotNull
    private EnumWarehouse.F_LANGUAGE s_f_language;

    @NotNull
    private EnumWarehouse.S_RANGE s_s_range;

    @NotNull
    private EnumWarehouse.RANKING s_ranking;

    @NotNull
    private EnumWarehouse.IF_IS_OR_NOT s_if_work;

    //学生是否有实习/工作经验（YES or NO）
    @NotNull
    private EnumWarehouse.IF_IS_OR_NOT s_if_career;

    //学生是否有项目经验（YES or NO）
    @NotNull
    private EnumWarehouse.IF_IS_OR_NOT s_if_project_experience;

    //学生是否为应届生 （YES or NO）
    @NotNull
    private EnumWarehouse.IF_IS_OR_NOT s_if_fresh;
}

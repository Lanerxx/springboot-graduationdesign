package com.example.springbootgraduationdesign.entity;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    private int r_count;

    @NotNull
    private boolean posted;
}

package com.example.springbootgraduationdesign.entity;


import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int c_id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "c_i_id")
    private Industry c_industry;

    @NotNull
    @Column(length = 30)
    private String c_name;

    @NotNull
    @Column(length = 70)
    private String c_password;

    @NotNull
    @Column(length = 20)
    private String c_s_code;

    @NotNull
    @Column(length = 500)
    private String c_description;

    @NotNull
    @Column(length = 20)
    private String c_f_contact;

    @NotNull
    @Column(length = 20)
    private String c_f_telephone;

    @NotNull
    @Column(length = 20)
    private String c_s_contact;

    @NotNull
    @Column(length = 20)
    private String c_s_telephone;

    @NotNull
    @Column(length = 20)
    private String c_email;

    @NotNull
    @Column(length = 80)
    private String c_location;

    @NotNull
    @Column(length = 20)
    private String c_e_date;

    @NotNull
    private int c_scale;
}

package com.example.springbootgraduationdesign.entity;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
public class Admin {
    public enum A_TYPE{
        SYSTEM_ADMIN, NORMAL_ADMIN
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int a_id;

    @NotNull
    private A_TYPE a_type;

    @NotNull
    @Column(length = 20)
    private String a_name;

    @NotNull
    @Column(length = 72)
    private String a_password;




}

package com.example.springbootgraduationdesign.entity;

import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
public class JobDrector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int jd_id;

    @NotNull
    @Column(length = 20)
    private String jd_name;

    @NotNull
    @Column(length = 72)
    private String jd_password;

    @NotNull
    @Column(length = 20)
    private String jd_telephone;

    @NotNull
    @Column(length = 25)
    private String jd_email;

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

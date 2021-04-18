package com.example.springbootgraduationdesign.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Data
@Embeddable
public class JobResumePK implements Serializable {
    @OneToOne
    @JoinColumn(name = "j_id")
    private Job jr_job;

    @OneToOne
    @JoinColumn(name = "r_id")
    private Resume jr_resume;

}

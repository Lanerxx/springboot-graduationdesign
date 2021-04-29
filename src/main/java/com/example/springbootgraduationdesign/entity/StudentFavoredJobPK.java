package com.example.springbootgraduationdesign.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Data
@Embeddable
public class StudentFavoredJobPK implements Serializable {
    @OneToOne
    @JoinColumn(name = "s_id")
    private Student sfj_student;

    @OneToOne
    @JoinColumn(name = "j_id")
    private Job sfj_job;

}

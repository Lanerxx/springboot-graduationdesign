package com.example.springbootgraduationdesign.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
@Data
public class StudentResumePK implements Serializable {
    @OneToOne
    @JoinColumn(name = "s_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "r_id")
    private Resume resume;
}

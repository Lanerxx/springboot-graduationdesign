package com.example.springbootgraduationdesign.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Data
@Embeddable
public class StudentIndustryPK implements Serializable {
    @OneToOne
    @JoinColumn(name = "s_id")
    private Student student;

    @OneToOne
    @JoinColumn(name = "i_id")
    private Industry industry;

}

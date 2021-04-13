package com.example.springbootgraduationdesign.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
@Data
public class CompanyJobPk implements Serializable {
    @OneToOne
    @JoinColumn(name = "c_id")
    private Company c_id;

    @OneToOne
    @JoinColumn(name = "j_id")
    private Job j_id;
}

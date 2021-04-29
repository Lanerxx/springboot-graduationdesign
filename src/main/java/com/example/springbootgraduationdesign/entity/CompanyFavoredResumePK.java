package com.example.springbootgraduationdesign.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
@Data
public class CompanyFavoredResumePK implements Serializable {
    @OneToOne
    @JoinColumn(name = "c_id")
    private Company cfr_company;

    @OneToOne
    @JoinColumn(name = "r_id")
    private Resume cfr_resume;
}

package com.example.springbootgraduationdesign.entity;

import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Embeddable
@Data
public class JobProfessionPK implements Serializable {
    @OneToOne
    @JoinColumn(name = "j_id")
    private Job jp_job;

    @OneToOne
    @JoinColumn(name = "pr_id")
    private Profession jp_profession;
}

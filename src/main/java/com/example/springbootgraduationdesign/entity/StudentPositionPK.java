package com.example.springbootgraduationdesign.entity;


import lombok.Data;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Data
@Embeddable
public class StudentPositionPK implements Serializable {
    @OneToOne
    @JoinColumn(name = "s_id")
    private Student s_id;

    @OneToOne
    @JoinColumn(name = "po_id")
    private Position po_id;
}

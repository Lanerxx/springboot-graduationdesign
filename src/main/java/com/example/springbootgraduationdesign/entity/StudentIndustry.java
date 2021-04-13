package com.example.springbootgraduationdesign.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
@Data
@NoArgsConstructor
public class StudentIndustry {
    @EmbeddedId
    private StudentIndustryPK studentIndustryPK;
}

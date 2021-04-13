package com.example.springbootgraduationdesign.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Data
@NoArgsConstructor
@Entity
public class StudentPosition {
    @EmbeddedId
    private StudentPositionPK studentPositionPK;
}

package com.example.springbootgraduationdesign.component.vo;

import com.example.springbootgraduationdesign.entity.Resume;
import lombok.Data;

@Data
public class ResumeRecommendVo {
    private Resume rr_resume;

    private double rr_value;

    private int r_hot;
}

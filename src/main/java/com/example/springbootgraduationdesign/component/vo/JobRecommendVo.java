package com.example.springbootgraduationdesign.component.vo;

import com.example.springbootgraduationdesign.entity.Job;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class JobRecommendVo {

    private Job jr_job;

    private double jr_value;

    private int jr_hot;
}

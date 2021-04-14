package com.example.springbootgraduationdesign.component.vo;

import com.example.springbootgraduationdesign.entity.Job;
import com.example.springbootgraduationdesign.entity.Profession;
import lombok.Data;

import java.util.List;

@Data
public class JobVo {
    private Job job;
    private List<Profession> professions;
    private boolean post;
}

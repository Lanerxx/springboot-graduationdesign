package com.example.springbootgraduationdesign.component.vo;

import com.example.springbootgraduationdesign.entity.EnumWarehouse;
import com.example.springbootgraduationdesign.entity.Job;
import com.example.springbootgraduationdesign.entity.JobSMRBase;
import com.example.springbootgraduationdesign.entity.Resume;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class JobSMRPersonalizedVo {
    @NotNull
    private int smr_id;

    @NotNull
    private JobSMRBase smr_base;

    @NotNull
    private Job smr_job;

    @NotNull
    private Resume smr_resume;

    @NotNull
    private double smr_v_match;//匹配程度

    @NotNull
    private EnumWarehouse.SUCCESS_DEGREE smr_v_success;//成功等级

    @NotNull
    private double smr_v_average;//匹配程度与成功等级的调和平均值

    @NotNull
    private int smr_v_popularity;//匹配到的学生简历被企业选中的次数
}

package com.example.springbootgraduationdesign.component.vo;

import com.example.springbootgraduationdesign.entity.EnumWarehouse;
import com.example.springbootgraduationdesign.entity.Job;
import com.example.springbootgraduationdesign.entity.Resume;
import com.example.springbootgraduationdesign.entity.ResumeJMRBase;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResumeJMRPersonalizedVo {
    @NotNull
    private int jmr_id;

    @NotNull
    private ResumeJMRBase jmr_base;

    @NotNull
    private Resume jmr_resume;

    @NotNull
    private Job jmr_job;

    @NotNull
    private double jmr_v_match;//匹配程度

    @NotNull
    private EnumWarehouse.SUCCESS_DEGREE jmr_v_success;//成功等级

    @NotNull
    private double jmr_v_average;//匹配程度与成功等级的调和平均值

    @NotNull
    private int jmr_v_popularity;//匹配到的公司岗位被简历选中的次数
}

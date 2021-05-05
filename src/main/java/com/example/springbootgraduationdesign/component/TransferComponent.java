package com.example.springbootgraduationdesign.component;

import com.example.springbootgraduationdesign.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Component
public class TransferComponent {
    @Autowired
    private ValueComponent valueComponent;
    @Autowired
    private TransferComponent transferComponent;

    public static final int JOBSMRBASE_TO_ARRAY_FEATURE_COUNT = 19; //数据有多少个特征
    public static final int JOB_TO_ARRAY_FEATURE_COUNT = 11; //数据有多少个特征

    public static final int RESUMEJMRBASE_TO_ARRAY_FEATURE_COUNT = 17; //数据有多少个特征
    public static final int RESUME_TO_ARRAY_FEATURE_COUNT = 9; //数据有多少个特征

    //Job
    public double[] transferJobSMRBaseToArray(JobSMRBase jobSMRBase){
        double[] jobSMRArray = new double[JOBSMRBASE_TO_ARRAY_FEATURE_COUNT];
        jobSMRArray[0] = jobSMRBase.getSmr_b_a_bonus();
        jobSMRArray[1] = jobSMRBase.getSmr_b_p_leave();
        jobSMRArray[2] = jobSMRBase.getSmr_b_o_allowance();
        jobSMRArray[3] = jobSMRBase.getSmr_b_stock();
        jobSMRArray[4] = jobSMRBase.getSmr_b_t_subside();
        jobSMRArray[5] = jobSMRBase.getSmr_b_h_subside();
        jobSMRArray[6] = jobSMRBase.getSmr_b_b_trip();
        jobSMRArray[7] = jobSMRBase.getSmr_b_c_level();
        jobSMRArray[8] = jobSMRBase.getSmr_b_e_history();
        jobSMRArray[9] = jobSMRBase.getSmr_b_e_language();
        jobSMRArray[10] = jobSMRBase.getSmr_b_ranking();
        jobSMRArray[11] = jobSMRBase.getSmr_b_check_up();
        jobSMRArray[12] = jobSMRBase.getSmr_b_p_count();
        jobSMRArray[13] = jobSMRBase.getSmr_b_s_count();
        jobSMRArray[14] = jobSMRBase.getSmr_b_c_count();
        jobSMRArray[15] = jobSMRBase.getSmr_b_h_count();
        jobSMRArray[16] = jobSMRBase.getSmr_b_position();
        jobSMRArray[17] = jobSMRBase.getSmr_b_location();
        jobSMRArray[18] = jobSMRBase.getSmr_b_insurance();
        return jobSMRArray;
    }

    public double[] transferResumeAndStudentToArray(Resume resume){
        double[] resumeArray = new double[JOBSMRBASE_TO_ARRAY_FEATURE_COUNT];
        resumeArray[0] = resume.getR_if_a_bonus().ordinal();
        resumeArray[1] = resume.getR_if_p_leave().ordinal();
        resumeArray[2] = resume.getR_if_o_allowance().ordinal();
        resumeArray[3] = resume.getR_if_stock().ordinal();
        resumeArray[4] = resume.getR_if_t_subside().ordinal();
        resumeArray[5] = resume.getR_if_h_subside().ordinal();
        resumeArray[6] = resume.getR_if_b_trip().ordinal();
        resumeArray[7] = resume.getR_student().getS_c_level().ordinal();
        resumeArray[8] = resume.getR_student().getS_e_history().ordinal();
        resumeArray[9] = resume.getR_student().getS_e_language().ordinal();
        resumeArray[10] = resume.getR_student().getS_ranking().ordinal();
        resumeArray[11] = resume.getR_if_check_up().ordinal();
        resumeArray[12] = resume.getR_p_count();
        resumeArray[13] = resume.getR_h_count();
        resumeArray[14] = resume.getR_s_count();
        resumeArray[15] = resume.getR_c_count();
        resumeArray[16] = valueComponent.resumeTempt();
        resumeArray[17] = valueComponent.resumeTempt();
        resumeArray[18] = resume.getR_if_insurance().ordinal();
        return resumeArray;
    }

    public double[] transferJobToArray(Job job){
        double[] jobArray = new double[JOB_TO_ARRAY_FEATURE_COUNT];
        jobArray[1] = valueComponent.jobSalaryRange(job.getJ_s_range());
        jobArray[2] = valueComponent.jobWelfare(job.getJ_insurance());
        jobArray[3] = valueComponent.jobWelfare(job.getJ_check_up());
        jobArray[4] = valueComponent.jobWelfare(job.getJ_a_bonus());
        jobArray[5] = valueComponent.jobWelfare(job.getJ_p_leave());
        jobArray[6] = valueComponent.jobWelfare(job.getJ_o_allowance());
        jobArray[7] = valueComponent.jobWelfare(job.getJ_stock());
        jobArray[8] = valueComponent.jobWelfare(job.getJ_t_subside());
        jobArray[9] = valueComponent.jobWelfare(job.getJ_h_subside());
        jobArray[10] = valueComponent.jobScare(job.getJ_company().getC_scale());
        jobArray[0] = valueComponent.jobFRange(job.getJ_company().getC_f_stage());
        return jobArray;
    }

    public List<double[]> transferJobsToArray(List<Job> jobs){
        List<double[]> jobList = new ArrayList<>();
        jobs.forEach(job -> {
            jobList.add(transferComponent.transferJobToArray(job));
        });
        return jobList;
    }

    public List<double[]> transferJobsToArray(Job job, List<Job> jobs){
        List<double[]> jobList = new ArrayList<>();
        jobList.add(transferComponent.transferJobToArray(job));
        jobs.forEach(j -> {
            jobList.add(transferComponent.transferJobToArray(j));
        });
        return jobList;
    }


    //Resume
    public double[] transferResumeJMRBaseToArray(ResumeJMRBase resumeJMRBase){
        double[] resumeJMRArray = new double[RESUMEJMRBASE_TO_ARRAY_FEATURE_COUNT];
        resumeJMRArray[0] = resumeJMRBase.getJmr_b_c_scale();
        resumeJMRArray[1] = resumeJMRBase.getJmr_b_c_f_stage();
        resumeJMRArray[2] = resumeJMRBase.getJmr_b_c_level();
        resumeJMRArray[3] = resumeJMRBase.getJmr_e_history();
        resumeJMRArray[4] = resumeJMRBase.getJmr_b_e_language();
        resumeJMRArray[5] = resumeJMRBase.getJmr_b_s_range();
        resumeJMRArray[6] = resumeJMRBase.getJmr_b_position();
        resumeJMRArray[7] = resumeJMRBase.getJmr_b_location();
        resumeJMRArray[8] = resumeJMRBase.getJmr_b_insurance();
        resumeJMRArray[9] = resumeJMRBase.getJmr_b_check_up();
        resumeJMRArray[10] = resumeJMRBase.getJmr_b_a_bonus();
        resumeJMRArray[11] = resumeJMRBase.getJmr_b_p_leave();
        resumeJMRArray[12] = resumeJMRBase.getJmr_b_o_allowance();
        resumeJMRArray[13] = resumeJMRBase.getJmr_b_stock();
        resumeJMRArray[14] = resumeJMRBase.getJmr_b_t_subside();
        resumeJMRArray[15] = resumeJMRBase.getJmr_b_h_subside();
        resumeJMRArray[16] = resumeJMRBase.getJmr_b_b_trip();
        System.out.println("----resumeSMRArray----");
        System.out.println(Arrays.toString(resumeJMRArray));
        return resumeJMRArray;
    }

    public double[] transferJobAndCompanyToArray(Job job){
        double[] jobArray = new double[RESUMEJMRBASE_TO_ARRAY_FEATURE_COUNT];
        jobArray[0] = job.getJ_company().getC_scale();
        jobArray[1] = job.getJ_company().getC_f_stage().ordinal();
        jobArray[2] = job.getJ_c_level().ordinal();
        jobArray[3] = job.getJ_e_history().ordinal();
        jobArray[4] = job.getJ_e_language().ordinal();
        jobArray[5] = job.getJ_s_range().ordinal();
        jobArray[6] = valueComponent.resumeTempt();
        jobArray[7] = valueComponent.resumeTempt();
        jobArray[8] = job.getJ_insurance().ordinal();
        jobArray[9] = job.getJ_check_up().ordinal();
        jobArray[10] = job.getJ_a_bonus().ordinal();
        jobArray[11] = job.getJ_p_leave().ordinal();
        jobArray[12] = job.getJ_o_allowance().ordinal();
        jobArray[13] = job.getJ_stock().ordinal();
        jobArray[14] = job.getJ_t_subside().ordinal();
        jobArray[15] = job.getJ_h_subside().ordinal();
        jobArray[16] = job.getJ_b_trip().ordinal();
        return jobArray;
    }


    public double[] transferResumeToArray(Resume resume){
        double[] resumeArray = new double[RESUME_TO_ARRAY_FEATURE_COUNT];
        Student student = resume.getR_student();
        resumeArray[0] = valueComponent.resumeCLevel(student.getS_c_level());
        resumeArray[1] = valueComponent.resumeEHistory(student.getS_e_history());
        resumeArray[2] = valueComponent.resumeELanguage(student.getS_e_language());
        resumeArray[3] = valueComponent.resumeFLanguage(student.getS_f_language());
        resumeArray[4] = valueComponent.resumeRanking(student.getS_ranking());
        resumeArray[6] = valueComponent.resumeXCount(resume.getR_p_count());
        resumeArray[7] = valueComponent.resumeXCount(resume.getR_c_count());
        resumeArray[8] = valueComponent.resumeXCount(resume.getR_s_count());
        resumeArray[5] = valueComponent.resumeXCount(resume.getR_h_count());
        return resumeArray;
    }

    public List<double[]> transferResumesToArray(Resume resume, List<Resume> resumes){
        List<double[]> resumeList = new ArrayList<>();
        resumeList.add(transferComponent.transferResumeToArray(resume));
        resumes.forEach(r -> {
            resumeList.add(transferComponent.transferResumeToArray(r));
        });
        return resumeList;
    }


    //排名评级数值化（三级）
    public double getSuccessByDegree(EnumWarehouse.SUCCESS_DEGREE degree){
        if (degree.ordinal() == 0) return 0.9;
        else if (degree.ordinal() == 1) return 0.6;
        return 0.3;
    }

}

package com.example.springbootgraduationdesign.component;

import com.example.springbootgraduationdesign.entity.EnumWarehouse;
import com.example.springbootgraduationdesign.entity.Job;
import com.example.springbootgraduationdesign.entity.JobSMRBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class TransferComponent {
    @Autowired
    private ValueComponent valueComponent;
    @Autowired
    private TransferComponent transferComponent;

    public static final int JOBJMRBASE_TO_ARRAY_FEATURE_COUNT = 20; //数据有多少个特征
    public static final int JOB_TO_ARRAY_FEATURE_COUNT = 12; //数据有多少个特征


    public double[] transferJobJMRBaseToArray(JobSMRBase jobSMRBase){
//        System.out.println("----transferJobJMRBaseToArray----");
//        System.out.println("jobSMRBaseID" + jobSMRBase.getSmr_b_id() + " jobSMRBaseCLevel:" + jobSMRBase.getSmr_b_c_level()
//        + " jobSMRBaseHistory:" + jobSMRBase.getSmr_e_history() + " jobSMRBaseRanking:" + jobSMRBase.getSmr_b_ranking());
        double[] jobSMRArray = new double[JOBJMRBASE_TO_ARRAY_FEATURE_COUNT];
        jobSMRArray[0] = jobSMRBase.getSmr_b_a_bonus();
        jobSMRArray[1] = jobSMRBase.getSmr_b_p_leave();
        jobSMRArray[2] = jobSMRBase.getSmr_b_o_allowance();
        jobSMRArray[3] = jobSMRBase.getSmr_b_stock();
        jobSMRArray[4] = jobSMRBase.getSmr_b_t_subside();
        jobSMRArray[5] = jobSMRBase.getSmr_b_h_subside();
        jobSMRArray[6] = jobSMRBase.getSmr_b_b_trip();
        jobSMRArray[7] = jobSMRBase.getSmr_b_c_level();
        jobSMRArray[8] = jobSMRBase.getSmr_e_history();
        jobSMRArray[9] = jobSMRBase.getSmr_b_e_language();
        jobSMRArray[10] = jobSMRBase.getSmr_b_ranking();
        jobSMRArray[11] = jobSMRBase.getSmr_b_r_count();
        jobSMRArray[12] = jobSMRBase.getSmr_b_p_count();
        jobSMRArray[13] = jobSMRBase.getSmr_b_s_count();
        jobSMRArray[14] = jobSMRBase.getSmr_b_c_count();
        jobSMRArray[15] = jobSMRBase.getSmr_b_h_count();
        jobSMRArray[16] = jobSMRBase.getSmr_b_postion();
        jobSMRArray[17] = jobSMRBase.getSmr_b_location();
        jobSMRArray[18] = jobSMRBase.getSmr_b_insurance();
        jobSMRArray[19] = jobSMRBase.getSmr_b_check_up();
//        System.out.println("----jobSMRArray----");
//        System.out.println(Arrays.toString(jobSMRArray));
        return jobSMRArray;
    }

    public double[] transferJobToArray(Job job){
        double[] jobArray = new double[JOB_TO_ARRAY_FEATURE_COUNT];
        jobArray[0] = valueComponent.jobCount(job.getJ_count());
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
        jobArray[11] = valueComponent.jobFRange(job.getJ_company().getC_f_stage());
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

    //排名评级数值化（三级）
    public double getSuccessByDegree(EnumWarehouse.SUCCESS_DEGREE degree){
        if (degree.ordinal() == 0) return 0.9;
        else if (degree.ordinal() == 1) return 0.6;
        return 0.3;
    }

}

package com.example.springbootgraduationdesign.Service;

import com.example.springbootgraduationdesign.component.vo.*;
import com.example.springbootgraduationdesign.entity.*;
import com.example.springbootgraduationdesign.service.CompanyService;
import com.example.springbootgraduationdesign.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;
import java.util.Random;

@SpringBootTest
public class CompanyServiceTest {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private StudentService studentService;

    @Test
    public void getJobSMRTest(){
        companyService.getJobSMR_Match();
    }

    @Test
    public void getJobSMRTest_Personalized(){
        Random random = new Random();
        int jid = 2;

        PersonalizedSMRVo personalizedSMRVo = new PersonalizedSMRVo();
        personalizedSMRVo.setA_bonus(random.nextDouble());
        personalizedSMRVo.setB_trip(random.nextDouble());
        personalizedSMRVo.setC_count(random.nextDouble());
        personalizedSMRVo.setC_level(random.nextDouble());
        personalizedSMRVo.setE_language(random.nextDouble());
        personalizedSMRVo.setC_up(random.nextDouble());
        personalizedSMRVo.setE_history(random.nextDouble());
        personalizedSMRVo.setH_count(random.nextDouble());
        personalizedSMRVo.setH_subside(random.nextDouble());
        personalizedSMRVo.setInsurance(random.nextDouble());
        personalizedSMRVo.setLocation(random.nextDouble());
        personalizedSMRVo.setO_allowance(random.nextDouble());
        personalizedSMRVo.setP_count(random.nextDouble());
        personalizedSMRVo.setP_leave(random.nextDouble());
        personalizedSMRVo.setPosition(random.nextDouble());
        personalizedSMRVo.setR_count(random.nextDouble());
        personalizedSMRVo.setRanking(random.nextDouble());
        personalizedSMRVo.setS_range(random.nextDouble());
        personalizedSMRVo.setStock(random.nextDouble());
        personalizedSMRVo.setS_range(random.nextDouble());
        personalizedSMRVo.setS_count(random.nextDouble());
        personalizedSMRVo.setT_subside(random.nextDouble());
        List<JobSMRPersonalizedVo> JobSMRPersonalizedVos = companyService.getJobSMR_Match(jid,personalizedSMRVo);
        if (JobSMRPersonalizedVos.size() == 0)
            System.out.println("0");
        for (JobSMRPersonalizedVo jobSMRPersonalizedVo : JobSMRPersonalizedVos) {
            System.out.println(jobSMRPersonalizedVo.toString());
        }
        System.out.println("end");

//        及时执行，根据用户的自定义权重进行匹配
//        public List<JobSMR> getJobSMR_Match(int jid, PersonalizedSMRVo personalizedSMRVo){
//        return jobSMRs;

//        personalizedSMRVo.setA_bonus();
//        personalizedSMRVo.setB_trip();
//        personalizedSMRVo.setC_count();
//        personalizedSMRVo.setC_level();
//        personalizedSMRVo.getE_language();
//        personalizedSMRVo.setC_up();
//        personalizedSMRVo.setE_history();
//        personalizedSMRVo.setGpa();
//        personalizedSMRVo.setH_count();
//        personalizedSMRVo.setH_subside();
//        personalizedSMRVo.setIf_career();
//        personalizedSMRVo.setIf_experience();
//        personalizedSMRVo.setInsurance();
//        personalizedSMRVo.setLocation();
//        personalizedSMRVo.setO_allowance();
//        personalizedSMRVo.setP_count();
//        personalizedSMRVo.setP_leave();
//        personalizedSMRVo.setPosition();
//        personalizedSMRVo.setR_count();
//        personalizedSMRVo.setRanking();
//        personalizedSMRVo.setS_range();
//        personalizedSMRVo.setStock();
//        personalizedSMRVo.setS_range();
//        personalizedSMRVo.setS_count();
//        personalizedSMRVo.setT_subside();
    }

    @Test
    public void getJobSMR_MatchTest(){
        int jid = 12;
        List<JobSMR> jobSMRs = companyService.getJobSMR_Match(jid);
        if (jobSMRs.size() == 0){
            System.out.println("0");
        }else {
            for (JobSMR jobSMR : jobSMRs) {
                System.out.println(jobSMR);
            }
        }
    }

    @Test
    public void get(){
        String collegeName = "门头沟枚大学0";
        //获取需要跟踪的学生集合
        List<Student> students = studentService.getStudentsByCollege(collegeName);


        //倾向跟踪
//        List<StudentFavoredJob> studentFavoredJobs = studentService.getStudentFavoredJobsByStudents(students);
//        List<Job> jobsFavored = companyService.getJobsByStudentFavoredJobs(studentFavoredJobs);
//        JobListStatisticalFormVo jobListStatisticalFormVoFavored = trackService.getJobListStatisticalFormVo(jobsFavored);

        //单向跟踪
        List<JobResume> jobResumesOneWay = studentService.getJobResumesByStudents_ResumeToJob(students,true);
        List<Job> jobsOneWay = companyService.getJobsByJobResumes(jobResumesOneWay);
//        JobListStatisticalFormVo jobListStatisticalFormVoOneWay = trackService.getJobListStatisticalFormVo(jobsOneWay);

        //双向跟踪
        List<JobResume> jobResumesTwoWay = studentService.getJobResumesByStudents_ResumeToJob_JobToResume(students,true,true);
        List<Job> jobsTwoWay = companyService.getJobsByJobResumes(jobResumesTwoWay);
//        JobListStatisticalFormVo jobListStatisticalFormVoTwoWay = trackService.getJobListStatisticalFormVo(jobsTwoWay);

        System.out.println("jobResumesOneWay:");
        System.out.println("size():" + jobResumesOneWay.size());
        for (JobResume jobResume : jobResumesOneWay) {
            System.out.print("Job:" + jobResume.getJobResumePK().getJr_job().getJ_id()
                    + "/Resume:" + jobResume.getJobResumePK().getJr_resume().getR_id() + "  ");
        }
        System.out.println();
        for (JobResume jobResume : jobResumesOneWay) {
            if (jobResume.isJobToResume()) System.out.print("JtR:true/");
            else System.out.print("JtR:false/");
            if (jobResume.isResumeToJob()) System.out.print("RtJ:true/");
            else System.out.print("RtJ:false/");
            System.out.print("  ");

        }
        System.out.println();
        System.out.println("jobResumesTwoWay:");
        System.out.println("size():" + jobResumesTwoWay.size());
        for (JobResume jobResume : jobResumesTwoWay) {
            System.out.print("Job:" + jobResume.getJobResumePK().getJr_job().getJ_id()
                    + "/Resume:" + jobResume.getJobResumePK().getJr_resume().getR_id() + "  ");

        }
        System.out.println();
        for (JobResume jobResume : jobResumesTwoWay) {
            if (jobResume.isJobToResume()) System.out.print("JtR:true/");
            else System.out.print("JtR:false/");
            if (jobResume.isResumeToJob()) System.out.print("RtJ:true/");
            else System.out.print("RtJ:false/");
            System.out.print("  ");
        }




    }

}

package com.example.springbootgraduationdesign.service;

import com.example.springbootgraduationdesign.component.TransferComponent;
import com.example.springbootgraduationdesign.component.vo.JobRecommendVo;
import com.example.springbootgraduationdesign.component.vo.ResumeRecommendVo;
import com.example.springbootgraduationdesign.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Slf4j
@Transactional
public class RecommendService {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private StudentService studentService;

    @Autowired
    private TransferComponent transferComponent;

    public List<JobRecommendVo> getJobRecommends(int sid){
        Student student = studentService.getStudent(sid);
        List<Student> similarStudents = studentService.getSimilarStudentsByStudent(student);
        if (similarStudents.size() > 4){
            Random random = new Random();
            int n = random.nextInt(similarStudents.size() - 4);
            similarStudents = similarStudents.subList(n, n + 3);
        }
        List<StudentFavoredJob> studentFavoredJobs = studentService.getStudentFavoredJobsByStudents(similarStudents);
        List<Job> jobs = new ArrayList<>();
        for (StudentFavoredJob studentFavoredJob : studentFavoredJobs) {
            jobs.add(studentFavoredJob.getStudentFavoredJobPK().getSfj_job());
        }
        List<JobRecommendVo> jobRecommendVos = new ArrayList<>();
        JobSystemDefaultWeight jobSystemDefaultWeight = companyService.getJobSystemDefaultWeight();
        for (Job job : jobs) {
            JobRecommendVo jobRecommendVo = new JobRecommendVo();
            double sum = 0;
            double[] jobArr = transferComponent.transferJobAndCompanyToArray(job);
            sum += jobArr[0] * jobSystemDefaultWeight.getJsdw_c_scale();
            sum += jobArr[1] * jobSystemDefaultWeight.getJsdw_c_f_stage();
            sum += jobArr[5] * jobSystemDefaultWeight.getJsdw_s_range();
            sum += jobArr[6] * jobSystemDefaultWeight.getJsdw_location();
            sum += jobArr[7] * jobSystemDefaultWeight.getJsdw_position();
            sum += jobArr[8] * jobSystemDefaultWeight.getJsdw_insurance();
            sum += jobArr[9] * jobSystemDefaultWeight.getJsdw_check_up();
            sum += jobArr[10] * jobSystemDefaultWeight.getJsdw_a_bonus();
            sum += jobArr[11] * jobSystemDefaultWeight.getJsdw_p_leave();
            sum += jobArr[12] * jobSystemDefaultWeight.getJsdw_o_allowance();
            sum += jobArr[13] * jobSystemDefaultWeight.getJsdw_stock();
            sum += jobArr[14] * jobSystemDefaultWeight.getJsdw_t_subside();
            sum += jobArr[15] * jobSystemDefaultWeight.getJsdw_h_subside();
            sum += jobArr[16] * jobSystemDefaultWeight.getJsdw_b_trip();
            int hot = studentService.getJobResumeCountByJob(job.getJ_id(), true);
            jobRecommendVo.setJr_job(job);
            jobRecommendVo.setJr_value(sum);
            jobRecommendVo.setJr_hot(hot);
            jobRecommendVos.add(jobRecommendVo);
        }
        return jobRecommendVos;
    }

    public List<ResumeRecommendVo> getResumeRecommends(int cid){
        Company company = companyService.getCompany(cid);
        List<Company> similarCompanies = companyService.getSimilarCompaniesByCompany(company);
        if (similarCompanies.size() > 4){
            Random random = new Random();
            int n = random.nextInt(similarCompanies.size() - 4);
            similarCompanies = similarCompanies.subList(n, n + 3);
        }
        List<CompanyFavoredResume> companyFavoredResumes = companyService.getCompanyFavoredResumesByCompanies(similarCompanies);
        List<Resume> resumes = new ArrayList<>();
        for (CompanyFavoredResume companyFavoredResume : companyFavoredResumes) {
            resumes.add(companyFavoredResume.getCompanyFavoredResumePK().getCfr_resume());
        }
        List<ResumeRecommendVo> resumeRecommendVos = new ArrayList<>();
        ResumeSystemDefaultWeight resumeSystemDefaultWeight = studentService.getResumeSystemDefaultWeight();
        for (Resume resume : resumes){
            ResumeRecommendVo resumeRecommendVo = new ResumeRecommendVo();
            double sum = 0;
            double[] resumeArr = transferComponent.transferResumeAndStudentToArray(resume);
            sum += resumeArr[6] * resumeSystemDefaultWeight.getRsdw_b_trip();
            sum += resumeArr[7] * resumeSystemDefaultWeight.getRsdw_c_level();
            sum += resumeArr[8] * resumeSystemDefaultWeight.getRsdw_e_history();
            sum += resumeArr[9] * resumeSystemDefaultWeight.getRsdw_e_language();
            sum += resumeArr[10] * resumeSystemDefaultWeight.getRsdw_ranking();
            sum += resumeArr[12] * resumeSystemDefaultWeight.getRsdw_p_count();
            sum += resumeArr[13] * resumeSystemDefaultWeight.getRsdw_h_count();
            sum += resumeArr[14] * resumeSystemDefaultWeight.getRsdw_s_count();
            sum += resumeArr[15] * resumeSystemDefaultWeight.getRsdw_c_count();
            int hot = companyService.getJobResumeCountByResume(resume.getR_id(), true);
            resumeRecommendVo.setRr_resume(resume);
            resumeRecommendVo.setRr_value(sum);
            resumeRecommendVo.setR_hot(hot);
            resumeRecommendVos.add(resumeRecommendVo);
        }
        return resumeRecommendVos;
    }

}

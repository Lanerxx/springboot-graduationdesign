package com.example.springbootgraduationdesign.service;

import com.example.springbootgraduationdesign.component.EnumComponent;
import com.example.springbootgraduationdesign.entity.*;
import com.example.springbootgraduationdesign.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private CompanyJobRepository companyJobRepository;
    @Autowired
    private JobSMRBaseRepository jobSMRBaseRepository;
    @Autowired
    private JobSMRRepository jobSMRRepository;
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private StudentResumeRepository studentResumeRepository;
    @Autowired
    private EnumComponent enumComponent;


    /*---------------企业信息（Company）-----------------
    -------检索：管理员，公司，就业专员
    -------更新：管理员，公司
    -------创建：管理员，公司（注册-审核）
    -------删除：管理员，公司
    --------------------------------------------------*/
    public Company addCompany(Company company){
        companyRepository.save(company);
        return company;
    }
    public void deleteCompany(int cid){
        companyRepository.deleteById(cid);
    }
    public void deleteAllCompanies(){
        companyRepository.deleteAll();
    }
    public Company updateCompany(Company company){
        companyRepository.save(company);
        return company;
    }
    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }
    public List<Company> getCompaniesByName(String name){
        return companyRepository.getCompanyByName(name).orElse(new ArrayList<>());
    }
    public Company getCompanyByCode(String code){
        return companyRepository.getCompanyBySCode(code).orElse(null);
    }
    public Company getCompanyByTelephone(String telephone){
        return companyRepository.getCompanyByFTelephone(telephone).orElse(null);
    }
    public Company getCompany(int cid){
        return companyRepository.findById(cid).orElse(null);
    }

    /*-----------------职位信息（Job）-------------------
    -------检索：公司
    -------更新：公司
    -------创建：公司
    -------删除：公司
    --------------------------------------------------*/
    public Job addJob(Job job){
        jobRepository.save(job);
        return job;
    }
    public void deleteJob(int jid){
        jobRepository.deleteById(jid);
    }
    public void deleteAllJobs(){
        jobRepository.deleteAll();
    }
    public Job updateJob(Job job){
        jobRepository.save(job);
        return job;
    }
    public Job getJob(int jid){
        return jobRepository.findById(jid).orElse(null);
    }
    public List<Job> getJobsByCompany(int cid){
        return jobRepository.getJobsByCompany(cid).orElse(new ArrayList<>());
    }

    /*----------企业已发布职位信息（CompanyJob）-----------
    -------检索：管理员，公司，就业专员
    -------更新：管理员，公司
    -------创建：管理员，公司
    -------删除：管理员，公司
    --------------------------------------------------*/
    public CompanyJob addCompanyJob(CompanyJob company_job){
        companyJobRepository.save(company_job);
        return company_job;
    }
    public void deleteCompanyJob(int cid,int jid){
        companyJobRepository.deleteCompanyJobByCompanyAndJob(cid,jid);
    }
    public void deleteAllCompanyJobs(){
        companyJobRepository.deleteAll();
    }
    public void updateCompanyJob(CompanyJob companyJob){
        companyJobRepository.save(companyJob);
    }
    public List<CompanyJob> getAllCompanyJobs(){
        return companyJobRepository.findAll();
    }
    public List<CompanyJob> getCompanyJobs(String positionName){
        return companyJobRepository.getCompanyJobsByPositionName(positionName).orElse(new ArrayList<>());
    }
    public List<CompanyJob> getCompanyJobsByCompany(int cid){
        return companyJobRepository.getCompanyJobByCompany(cid).orElse(new ArrayList<>());
    }
    public CompanyJob getCompanyJobByCompanyAndJob(int cid, int jid){
        return companyJobRepository.getCompanyJobByCompanyAndJob(cid, jid).orElse(null);
    }

    /*---------企业匹配的学生信息的各项数值（JmrBase）---------
    -------检索：管理员，公司，就业专员
    -------更新：服务器
    -------创建：服务器
    -------删除：服务器
    --------------------------------------------------*/
    public JobSMRBase addSmrBase(JobSMRBase smr_base){
        jobSMRBaseRepository.save(smr_base);
        return smr_base;
    }

    /*------企业匹配的学生信息（StudentMatchResult）-------
    -------检索：管理员，公司，就业专员
    -------更新：服务器
    -------创建：服务器
    -------删除：服务器
    --------------------------------------------------*/
    public JobSMR addStudentMatchResult(JobSMR student_match_result){
        jobSMRRepository.save(student_match_result);
        return student_match_result;
    }
    public void deleteJobSMRByCompanyAndJob(int cid,int jid){
        jobSMRRepository.deleteJobSMRByCompanyAndJob(cid, jid);
    }
    public void deleteJobSMRByResume(int rid){
        jobSMRRepository.deleteJobSMRByResume(rid);
    }
    public List<JobSMR> getAllJobSMR(){
        return jobSMRRepository.findAll();
    }
    public List<JobSMR> getJobSMRByJob(int jid){
        return jobSMRRepository.getJobSMRsByJob(jid).orElse(new ArrayList<>());
    }
    public List<JobSMR> getStudentMatchResultByCompany(int cid){
        return jobSMRRepository.getJobSMRsByCompany(cid).orElse(new ArrayList<>());
    }


}

package com.example.springbootgraduationdesign.controller;

import com.example.springbootgraduationdesign.component.CheckIsNullComponent;
import com.example.springbootgraduationdesign.component.RequestComponent;
import com.example.springbootgraduationdesign.component.vo.*;
import com.example.springbootgraduationdesign.entity.*;
import com.example.springbootgraduationdesign.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/api/company/")
@Slf4j
public class CompanyController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private ProfessionService professionService;
    @Autowired
    private RecommendService recommendService;

    @Autowired
    private CheckIsNullComponent checkIsNullComponent;
    @Autowired
    private RequestComponent requestComponent;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("index")
    public Map getIndex(){
        Company company = companyService.getCompany(requestComponent.getUid());
        List<String> industries = industryService.listIndustriesName();
        return Map.of(
                "company",company,
                "industries",industries
        );
    }

    @PatchMapping("password")
    public Map updatePassword(@Valid @RequestBody PasswordVo passwordVo){
        Company c = companyService.getCompany(requestComponent.getUid());
        if (!encoder.matches(passwordVo.getOldPassword(), c.getC_password())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您输入旧密码错误");
        }else {
            c.setC_password(encoder.encode(passwordVo.getNewPassword()));
        }
        companyService.updateCompany(c);
        return Map.of(
                "company",c
        );
    }

    @PatchMapping("information")
    public Map updateCompanyInformation(@RequestBody Company company){
        int cid = requestComponent.getUid();
        company.setC_id(cid);
        Company companyOld = companyService.getCompany(cid);
        if (companyOld == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想修改的公司不存在！");
        }
        company.setC_password(companyOld.getC_password());
        company.setC_f_telephone(companyOld.getC_f_telephone());
        company.setC_f_contact(companyOld.getC_f_contact());
        company.setInsertTime(companyOld.getInsertTime());
        company.setUpdateTime(companyOld.getInsertTime());
        log.debug("{}", company);
        if (checkIsNullComponent.objCheckIsNull(company)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息，请完善信息后再提交！");
        }
        Industry industry = industryService.getIndustry(company.getC_industry().getI_id());
        if (industry == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您提交的行业不存在！");
        }
        company.setC_industry(industry);
        companyService.updateCompany(company);
        return Map.of(
                "company",company
        );
    }

    @PostMapping("job")
    public Map addJob(@RequestBody JobVo jobVo){
        System.out.println("@PostMapping(job)");
        //补全岗位中的企业信息和职位信息
        int cid = requestComponent.getUid();
        Company company = companyService.getCompany(cid);
        Job job = jobVo.getJob();
        job.setJ_company(company);
        Position po = positionService.getPosition(job.getJ_position().getPo_name());
        System.out.println("岗位名:" + job.getJ_position().getPo_name());
        if (po == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "该岗位名不存在！");
        }
        job.setJ_position(po);
        LocalDateTime localDateTime = LocalDateTime.now();
        job.setInsertTime(localDateTime);
        job.setUpdateTime(localDateTime);
        //判断用户提交的信息是否全面
        if (checkIsNullComponent.objCheckIsNull(job)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息，请完善信息后再提交");
        }
        //判断用户提交的专业信息是否存在
        List<String> professionsMName = jobVo.getProfessionsMName();
        List<Profession> professions = professionService.getProfessionsByProfessionsMName(professionsMName);
        if (professions.size() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "岗位需要的专业不存在！");
        }
        log.debug("1:  {}", jobVo.getJob().getJ_position().getPo_name());
        jobVo.setProfessions(professions);
        companyService.addJob(jobVo);
        List<Job> jobs = companyService.getJobsByCompany(requestComponent.getUid());
        return Map.of(
                "jobs",jobs
        );
    }

    @DeleteMapping("job/{jid}")
    public Map deleteJob(@PathVariable int jid){
        Job job = companyService.getJob(jid);
        if (job ==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想删除的岗位不存在");
        }
        companyService.deleteJobAndRelated(job);
        List<Job> jobs = companyService.getJobsByCompany(requestComponent.getUid());
        return Map.of(
                "jobs",jobs
        );
    }

    @PatchMapping("job")
    public Map updateJob(@RequestBody JobVo jobVo){
        System.out.println("@PatchMapping(job)");
        System.out.println("jobVo:" + jobVo);
        //检验岗位的发布状态信息
        if (jobVo.getJob().isPosted()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "请您收回已发布的职位后再做修改");
        }
        //检验岗位的是否存在
        Job job = jobVo.getJob();
        Job jobOld = companyService.getJob(job.getJ_id());
        if (jobOld == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想修改的岗位不存在！");
        }
        //补全岗位中的企业信息和职位信息
        int cid = requestComponent.getUid();
        Company company = companyService.getCompany(cid);
        job.setJ_company(company);
        System.out.println("岗位名:" + job.getJ_position().getPo_name());
        Position po = positionService.getPosition(job.getJ_position().getPo_name());
        if (po == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "该岗位名不存在！");
        }
        job.setJ_position(po);
        job.setInsertTime(jobOld.getInsertTime());
        job.setUpdateTime(jobOld.getInsertTime());
        //判断用户提交的信息是否全面
        if (checkIsNullComponent.objCheckIsNull(job)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息，请完善信息后再提交");
        }
        //判断用户提交的专业信息是否存在
        List<String> professionsMName = jobVo.getProfessionsMName();
        List<Profession> professions = professionService.getProfessionsByProfessionsMName(professionsMName);
        if (professions.size() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "岗位需要的专业不存在！");
        }
        //修改岗位到数据库，并拉取新数据
        jobVo.setProfessions(professions);
        companyService.updateJob(jobVo);
        List<Job> jobs = companyService.getJobsByCompany(requestComponent.getUid());
        return Map.of(
                "jobs",jobs
        );
    }

    @GetMapping("job/{jid}/jobprofession")
    public Map getJobProfession(@PathVariable int jid){
        System.out.println("jid:" + jid);
        List<String> jobProfessionMName = professionService.getProfessionsMNameByJob(jid);
        System.out.println(jobProfessionMName);
        return Map.of(
                "jobProfessionMName", jobProfessionMName
        );
    }

    @GetMapping("jobs")
    public Map getJobs(){
        List<Job> jobs = companyService.getJobsByCompany(requestComponent.getUid());
        List<Position> positions = positionService.getAllPositions();
        List<Profession> professions = professionService.getAllProfessions();
        List<String> positionsName = positionService.listPositionsName();
        Set<String> professionsMClass = professionService.getProfessionsMClass();
        return Map.of(
                "jobs",jobs,
                "positions",positions,
                "professions",professions,
                "positionsName",positionsName,
                "professionsMClass",professionsMClass
        );
    }

    @PostMapping("companyJob")
    public Map addCompanyJob(@RequestBody Job job){
        log.debug("{}", job.getJ_id());
        Job j = companyService.getJob(job.getJ_id());
        if (j == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想发布的岗位不存在");
        }
        Company company = companyService.getCompany(requestComponent.getUid());
        companyService.addCompanyJob(company,j);
        List<Job> jobs = companyService.getJobsByCompany(requestComponent.getUid());
        return Map.of(
                "jobs",jobs
        );
    }

    @DeleteMapping("companyJob/{jid}")
    public Map deleteCompanyJob(@PathVariable int jid){
        CompanyJob companyJob = companyService.getCompanyJobByJob(jid);
        if (companyJob ==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想回收的岗位不存在");
        }
        companyService.deleteCompanyJobAndRelatedByJob(companyJob.getCompanyJobPk().getCj_job());
        List<Job> jobs = companyService.getJobsByCompany(requestComponent.getUid());
        return Map.of(
                "jobs",jobs
        );
    }


    @PostMapping("smr/{jid}")
    public Map getJmr(@PathVariable int jid, @RequestBody PersonalizedSMRVo personalizedSMRVo){
        //calculate(...)
        List<JobSMR> jobSMRs = companyService.getJobSMRsByJob(jid);
        return Map.of(
                "jobSMRs", jobSMRs
        );
    }

    @GetMapping("smr/{jid}")
    public Map getSmr(@PathVariable int jid){
        CompanyJob companyJob = companyService.getCompanyJobByJob(jid);
        if (companyJob == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想匹配的岗位不存在或尚未发布");
        }
        List<JobSMR> jobSMRs = companyService.getJobSMR_Match(jid);
        return Map.of(
                "jobSMRs", jobSMRs
        );
    }


    @PostMapping("companyFavoredResume/{rid}")
    public Map addCompanyFavoredResume(@PathVariable int rid){
        Resume r = studentService.getResume(rid);
        if (r == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想收藏的简历不存在");
        }
        Company company = companyService.getCompany(requestComponent.getUid());
        CompanyFavoredResume companyFavoredResume = new CompanyFavoredResume();
        CompanyFavoredResumePK companyFavoredResumePK = new CompanyFavoredResumePK();
        companyFavoredResumePK.setCfr_company(company);
        companyFavoredResumePK.setCfr_resume(r);
        companyFavoredResume.setCompanyFavoredResumePK(companyFavoredResumePK);
        companyService.addCompanyFavoredResume(companyFavoredResume);

        List<CompanyFavoredResume> companyFavoredResumes = companyService.getCompanyFavoredResumesByCompany(requestComponent.getUid());
        return Map.of(
                "companyFavoredResumes",companyFavoredResumes
        );
    }

    @GetMapping("companyFavoredResume")
    public Map getCompanyFavoredResumes(){
        List<CompanyFavoredResume> companyFavoredResumes = companyService.getCompanyFavoredResumesByCompany(requestComponent.getUid());
        return Map.of(
                "companyFavoredResumes",companyFavoredResumes
        );
    }

    @DeleteMapping("companyFavoredResume/{rid}")
    public Map deleteCompanyFavoredResume(@PathVariable int rid){
        int cid = requestComponent.getUid();
        CompanyFavoredResume companyFavoredResume = companyService.getCompanyFavoredResumeByCompanyAndResume(cid,rid);
        if (companyFavoredResume == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想取消收藏的简历不存在");
        }
        companyService.deleteCompanyFavoredResumeByCompanyAndResume(cid,rid);
        List<CompanyFavoredResume> companyFavoredResumes = companyService.getCompanyFavoredResumesByCompany(requestComponent.getUid());
        return Map.of(
                "companyFavoredResumes",companyFavoredResumes
        );
    }


    @GetMapping("companyResumes")
    public Map getCompanyResumes(){
        int cid = requestComponent.getUid();
        List<JobResume> jobResumes = companyService.getJobResumesByCompany(cid);
//        List<JobResume> jobResumesOut = companyService.getJobResumesByCompany_JobToResume_ResumeToJob(cid, true,false);
//        List<JobResume> jobResumesTwoWay = companyService.getJobResumesByCompany_JobToResume_ResumeToJob(cid,true,true);
//        List<JobResume> jobResumesIn = companyService.getJobResumesByCompany_JobToResume_ResumeToJob(cid,false,true);
        return Map.of(
                "jobResumes",jobResumes
//                "jobResumesOut",jobResumesOut,
//                "jobResumesTwoWay",jobResumesTwoWay,
//                "jobResumesIn",jobResumesIn
        );
    }

    @PostMapping("companyResumes/jobResume/{jid}/{rid}")
    public Map addJobResume(@PathVariable int rid, @PathVariable int jid){
        int cid = requestComponent.getUid();
        Job job = companyService.getJob(jid);
        Resume resume = studentService.getResume(rid);
        if (job == null || resume == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您提交的简历或岗位信息不正确");
        }
        JobResume jobResume = companyService.getJobResumeByJobAndResume(jid,rid);
        if (jobResume == null){
            jobResume = new JobResume();
            JobResumePK jobResumePK = new JobResumePK();
            jobResume.setJobToResume(true);
            jobResume.setResumeToJob(false);
            jobResumePK.setJr_resume(resume);
            jobResumePK.setJr_job(job);
            jobResume.setJobResumePK(jobResumePK);
            companyService.addJobResume(jobResume);
        }else {
            jobResume.setJobToResume(true);
            studentService.updateJobResume(jobResume);
        }
        List<JobResume> jobResumes = companyService.getJobResumesByCompany(cid);
        return Map.of(
                "jobResumes",jobResumes
        );
    }

    @PostMapping("companyResumes/jobResume/{rid}")
    public Map addJobResume(@PathVariable int rid){
        int cid = requestComponent.getUid();
        Resume resume = studentService.getResume(rid);
        if (resume == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想招聘的简历不存在！");
        }
        List<Job> jobs = companyService.getJobsByCompany(cid);
        if (jobs.size() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "请至少发布一个岗位后再招聘！");
        }
        Job job = jobs.get(0);
        JobResume jobResume = companyService.getJobResumeByJobAndResume(job.getJ_id(),rid);
        if (jobResume == null){
            jobResume = new JobResume();
            JobResumePK jobResumePK = new JobResumePK();
            jobResume.setJobToResume(true);
            jobResume.setResumeToJob(false);
            jobResumePK.setJr_resume(resume);
            jobResumePK.setJr_job(job);
            jobResume.setJobResumePK(jobResumePK);
            companyService.addJobResume(jobResume);
        }else {
            jobResume.setJobToResume(true);
            companyService.updateJobResume(jobResume);
        }
        List<JobResume> jobResumes = companyService.getJobResumesByCompany(cid);
        return Map.of(
                "jobResumes",jobResumes
        );
    }


    @GetMapping("resumeRecommend")
    public Map getResumeRecommends(){
        int cid = requestComponent.getUid();
        List<ResumeRecommendVo> resumeRecommendVos = recommendService.getResumeRecommends(cid);
        return Map.of(
                "resumeRecommendVos",resumeRecommendVos
        );
    }

}

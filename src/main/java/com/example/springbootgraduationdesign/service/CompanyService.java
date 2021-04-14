package com.example.springbootgraduationdesign.service;

import com.example.springbootgraduationdesign.component.EncryptComponent;
import com.example.springbootgraduationdesign.component.EnumComponent;
import com.example.springbootgraduationdesign.component.vo.JobVo;
import com.example.springbootgraduationdesign.entity.*;
import com.example.springbootgraduationdesign.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private CompanyJobRepository companyJobRepository;
    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobProfessionRepository jobProfessionRepository;
    @Autowired
    private JobSMRRepository jobSMRRepository;
    @Autowired
    private JobSMRBaseRepository jobSMRBaseRepository;

    @Autowired
    private CompanyService companyService;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private ProfessionService professionService;
    @Autowired
    private StudentService studentService;

    @Autowired
    private EnumComponent enumComponent;
    @Autowired
    private PasswordEncoder encoder;


    /*---------------企业信息（Company）-----------------
    -------检索：管理员，公司，就业专员
    -------更新：管理员，公司
    -------创建：管理员，公司（注册-审核）
    -------删除：管理员，公司
    --------------------------------------------------*/
    public Company addCompany(Company company){
        String pw = encoder.encode(company.getC_password());
        company.setC_password(pw);
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
    public Company updateCompany(Company companyNew, Company companyOld){
        Industry industry = industryService.getIndustry(companyNew.getC_industry().getI_id());
        companyOld.setC_industry(industry);
        companyOld.setC_name(companyNew.getC_name());
        companyOld.setC_s_code(companyNew.getC_s_code());
        companyOld.setC_description(companyNew.getC_description());
        companyOld.setC_f_contact(companyNew.getC_f_contact());
        companyOld.setC_f_telephone(companyNew.getC_f_telephone());
        companyOld.setC_s_contact(companyNew.getC_s_contact());
        companyOld.setC_s_telephone(companyNew.getC_s_telephone());
        companyOld.setC_email(companyNew.getC_email());
        companyOld.setC_scale(companyNew.getC_scale());
        companyOld.setC_e_date(companyNew.getC_e_date());
        companyRepository.save(companyOld);
        return companyOld;
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
    public Job addJob(JobVo jobVo){
        Job job = jobVo.getJob();
        //存岗位要求的专业信息
        List<Profession> professions = jobVo.getProfessions();
        for( Profession pr : professions){
            Profession profession = professionService.getProfession(pr.getPr_id());
            JobProfession jobProfession = new JobProfession();
            JobProfessionPK jobProfessionPK = new JobProfessionPK();
            jobProfessionPK.setJp_job(job);
            jobProfessionPK.setJp_profession(profession);
            jobProfession.setJobProfessionPK(jobProfessionPK);
            companyService.addJobProfession(jobProfession);
        }
        //存岗位的发布状态信息
        if (jobVo.isPost()) {
            job.setPosted(true);
        } else {
            job.setPosted(false);
        }
        //存岗位到数据库
        companyService.addJob(job);
        return job;
    }
    public void deleteJob(int jid){
        jobRepository.deleteById(jid);
    }
    public void deleteJobAndRelated(Job job){
        int jid = job.getJ_id();
        if (job.isPosted()){
            studentService.deleteStudentJMRByJob(jid);
            companyService.deleteJobSMRsByJob(jid);
        }
        companyService.deleteCompanyJobByJob(jid);
        companyService.deleteJobProfessionsByJob(jid);
        companyService.deleteJob(jid);
    }
    public void deleteAllJobs(){
        jobRepository.deleteAll();
    }
    public Job updateJob(Job job){
        jobRepository.save(job);
        return job;
    }
    public Job updateJob(JobVo jobVo, Job jobOld){
        Job jobNew = jobVo.getJob();
        //修改岗位其他信息
        jobOld.setJ_postion(jobNew.getJ_postion());
        jobOld.setJ_t_subside(jobNew.getJ_t_subside());
        jobOld.setJ_stock(jobNew.getJ_stock());
        jobOld.setJ_s_range(jobNew.getJ_s_range());
        jobOld.setJ_remark(jobNew.getJ_remark());
        jobOld.setJ_p_require(jobNew.getJ_p_require());
        jobOld.setJ_p_leave(jobNew.getJ_p_leave());
        jobOld.setJ_o_allowance(jobNew.getJ_o_allowance());
        jobOld.setJ_location(jobNew.getJ_location());
        jobOld.setJ_insurance(jobNew.getJ_insurance());
        jobOld.setJ_h_subside(jobNew.getJ_h_subside());
        jobOld.setJ_gender(jobNew.getJ_gender());
        jobOld.setJ_f_language(jobNew.getJ_f_language());
        jobOld.setJ_expire(jobNew.getJ_expire());
        jobOld.setJ_e_language(jobNew.getJ_e_language());
        jobOld.setJ_e_history(jobNew.getJ_e_history());
        jobOld.setJ_count(jobNew.getJ_count());
        jobOld.setJ_company(jobNew.getJ_company());
        jobOld.setJ_check_up(jobNew.getJ_check_up());
        jobOld.setJ_c_level(jobNew.getJ_c_level());
        jobOld.setJ_b_trip(jobNew.getJ_b_trip());
        jobOld.setJ_a_bonus(jobNew.getJ_a_bonus());
        //删除现有关联专业信息
        companyService.deleteJobProfessionsByJob(jobOld.getJ_id());
        //存岗位要求的专业信息
        List<Profession> professions = jobVo.getProfessions();
        for( Profession pr : professions){
            Profession profession = professionService.getProfession(pr.getPr_id());
            JobProfession jobProfession = new JobProfession();
            JobProfessionPK jobProfessionPK = new JobProfessionPK();
            jobProfessionPK.setJp_job(jobOld);
            jobProfessionPK.setJp_profession(profession);
            jobProfession.setJobProfessionPK(jobProfessionPK);
            companyService.addJobProfession(jobProfession);
        }

        //存岗位到数据库
        companyService.updateJob(jobOld);
        return jobOld;
    }

    public Job getJob(int jid){
        return jobRepository.findById(jid).orElse(null);
    }
    public List<Job> getJobsByCompany(int cid){
        return jobRepository.getJobsByCompany(cid).orElse(new ArrayList<>());
    }

    public JobProfession addJobProfession(JobProfession jobProfession){
        jobProfessionRepository.save(jobProfession);
        return jobProfession;
    }
    public void deleteJobProfession(int jid, int pid){
        jobProfessionRepository.deleteJobProfession(jid, pid);
    }
    public void deleteJobProfessionsByJob(int jid){
        jobProfessionRepository.deleteJobProfessionsByJob(jid);
    }

    /*----------企业已发布职位信息（CompanyJob）-----------
    -------检索：管理员，公司，就业专员
    -------更新：管理员，公司
    -------创建：管理员，公司
    -------删除：管理员，公司
    --------------------------------------------------*/
    public CompanyJob addCompanyJob(CompanyJob companyJob){
        companyJobRepository.save(companyJob);
        return companyJob;
    }
    public void addCompanyJob(Company company,Job job){
        job.setPosted(true);
        CompanyJob companyJob = new CompanyJob();
        CompanyJobPk companyJobPk = new CompanyJobPk();
        companyJobPk.setCj_company(company);
        companyJobPk.setCj_job(job);
        companyJob.setCompanyJobPk(companyJobPk);
        companyService.updateJob(job);
        companyService.addCompanyJob(companyJob);
    }
    public void deleteCompanyJobByJob(int jid){
        companyJobRepository.deleteCompanyJobByJob(jid);
    }
    public void deleteCompanyJobAndRelatedByJob(Job job){
        int jid = job.getJ_id();
        studentService.deleteStudentJMRByJob(jid);
        companyService.deleteJobSMRsByJob(jid);
        companyService.deleteCompanyJobByJob(jid);
        job.setPosted(false);
        companyService.updateJob(job);
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
    public CompanyJob getCompanyJobByJob(int jid){
        return companyJobRepository.getCompanyJobByJob(jid).orElse(null);
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
    public JobSMR addJobSMR(JobSMR jobSMR){
        jobSMRRepository.save(jobSMR);
        return jobSMR;
    }
    public void deleteJobSMRsByJob(int jid){
        jobSMRRepository.deleteJobSMRsByJob(jid);
    }
    public void deleteJobSMRsByResume(int rid){
        jobSMRRepository.deleteJobSMRsByResume(rid);
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

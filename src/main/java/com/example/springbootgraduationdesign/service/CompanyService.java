package com.example.springbootgraduationdesign.service;

import com.example.springbootgraduationdesign.component.ValueComponent;
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
import java.util.stream.Collectors;

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
    private JobResumeRepository jobResumeRepository;

    @Autowired
    private CompanyService companyService;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private ProfessionService professionService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private PositionService positionService;

    @Autowired
    private ValueComponent valueComponent;
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
//    public Company updateCompany(Company companyNew, Company companyOld){
//        Industry industry = industryService.getIndustry(companyNew.getC_industry().getI_id());
//        companyOld.setC_industry(industry);
//        companyOld.setC_name(companyNew.getC_name());
//        companyOld.setC_s_code(companyNew.getC_s_code());
//        companyOld.setC_description(companyNew.getC_description());
//        companyOld.setC_s_contact(companyNew.getC_s_contact());
//        companyOld.setC_s_telephone(companyNew.getC_s_telephone());
//        companyOld.setC_email(companyNew.getC_email());
//        companyOld.setC_scale(companyNew.getC_scale());
//        companyOld.setC_e_date(companyNew.getC_e_date());
//        companyOld.setC_location(companyNew.getC_location());
//        companyRepository.save(companyOld);
//        return companyOld;
//    }

    public Company getCompanyByCode(String code){
        return companyRepository.getCompanyBySCode(code).orElse(null);
    }
    public Company getCompanyByTelephone(String telephone){
        return companyRepository.getCompanyByFTelephone(telephone).orElse(null);
    }
    public Company getCompany(int cid){
        return companyRepository.findById(cid).orElse(null);
    }
    public List<Company> getAllCompanies(){
        return companyRepository.findAll();
    }
    public List<Company> getCompaniesByName(String name){
        return companyRepository.getCompanyByName(name).orElse(new ArrayList<>());
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
        //存岗位到数据库
        companyService.addJob(job);
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
        return job;
    }

    public void deleteJob(int jid){
        jobRepository.deleteById(jid);
    }
    public void deleteJobAndRelated(Job job){
        int jid = job.getJ_id();
        if (job.isPosted()){
            studentService.deleteStudentJMRsByJob(jid);
            companyService.deleteJobSMRsByJob(jid);
            companyService.deleteCompanyJobByJob(jid);
        }
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
    public Job updateJob(JobVo jobVo){
        Job job = jobVo.getJob();
        //存岗位到数据库
        companyService.updateJob(job);
        //删除现有关联专业信息
        companyService.deleteJobProfessionsByJob(job.getJ_id());
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
        return job;
    }

    public Job getJob(int jid){
        return jobRepository.findById(jid).orElse(null);
    }
    public List<Job> getJobsByCompany(int cid){
        return jobRepository.getJobsByCompany(cid).orElse(new ArrayList<>());
    }
    public List<Job> getSimilarJobs(Job job){

        return null;
    }

    public JobProfession addJobProfession(JobProfession jobProfession){
        jobProfessionRepository.save(jobProfession);
        return jobProfession;
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
        studentService.deleteStudentJMRsByJob(jid);
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

    /*------企业匹配的学生信息（JobSMR）-------
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
    public JobSMR getJobSMRByJobAndResume(int jid, int rid){
        return jobSMRRepository.getJobSMRByJobAndResume(jid, rid).orElse(null);
    }

    //定时执行，匹配每一个学生相对于每个岗位的条件符合值
    public void getJobSMR(){
        jobSMRRepository.deleteAll();
        jobSMRBaseRepository.deleteAll();

        // 获取所有企业已发布的岗位，并遍历
        List<CompanyJob> companyJobs = companyService.getAllCompanyJobs();
        companyJobs.forEach(companyJob -> {
            Company company = companyJob.getCompanyJobPk().getCj_company();
            Job job = companyJob.getCompanyJobPk().getCj_job();
            int jid = job.getJ_id();

            //匹配学校和学历符合的学生
            EnumWarehouse.C_LEVEL jobLevel = job.getJ_c_level();
            EnumWarehouse.E_HISTORY jobHistory = job.getJ_e_history();
            List<Student> students = studentService.getStudentByCLevelAndHistory(jobLevel, jobHistory);

            //匹配性别、语言、薪资、工作经验、项目经验、应届符合的学生
            EnumWarehouse.GENDER jobGender = job.getJ_gender();
            EnumWarehouse.E_LANGUAGE jobELanguage = job.getJ_e_language();
            EnumWarehouse.F_LANGUAGE jobFLanguage = job.getJ_f_language();
            EnumWarehouse.S_RANGE jobRange = job.getJ_s_range();
            EnumWarehouse.IF_IS_OR_NOT jobIfCareer = job.getJ_if_career();
            EnumWarehouse.IF_IS_OR_NOT jobIfFresh = job.getJ_if_fresh();
            EnumWarehouse.IF_IS_OR_NOT jobIfProject = job.getJ_if_project_experience();
            students.stream()
                    .filter(s ->  (jobGender.ordinal() == 0) || s.getS_gender().ordinal() == jobGender.ordinal())
                    .filter(s -> ((jobELanguage.ordinal() == 0) || s.getS_e_language().ordinal() <= jobELanguage.ordinal()))
                    .filter(s -> ((jobFLanguage.ordinal() == 0) || s.getS_f_language().ordinal() <= jobFLanguage.ordinal()))
                    .filter(s -> s.getS_s_range().ordinal() <= jobRange.ordinal())
                    .filter(s -> s.getS_if_career().ordinal() <= jobIfCareer.ordinal())
                    .filter(s -> s.getS_if_fresh().ordinal() <= jobIfFresh.ordinal())
                    .filter(s -> s.getS_if_project_experience().ordinal() <= jobIfProject.ordinal())
                    .collect(Collectors.toList());

            //匹配行业、专业符合的学生
            Industry jobIndustry = company.getC_industry();
            List<Profession> jobProfessions = professionService.getProfessionsByJob(jid);
            List<Student> studentList = new ArrayList<>();
            students.forEach(student -> {
                boolean inFlag = false;
                boolean prFlag = false;

                List<Industry> industries = industryService.getIndustriesByStudent(student.getS_id());
                for (Industry industry : industries) {
                    if (industry == jobIndustry) {
                        inFlag = true;
                        break;
                    }
                }
                if (!inFlag) return;

                Profession profession = student.getS_profession();
                for (Profession jobProfession : jobProfessions) {
                    if (jobProfession == profession) {
                        prFlag = true;
                        break;
                    }
                }
                if (prFlag) studentList.add(student);
            });

            //获取符合要求的学生的已发布简历
            List<StudentResume> studentResumeList = studentService.getStudentResumesByStudents(studentList);

            //-----------(1)、计算匹配值 smr_v_match-------------
            studentResumeList.forEach(studentResume -> {
                Student student = studentResume.getStudentResumePK().getSr_student();
                Resume resume = studentResume.getStudentResumePK().getSr_resume();
                JobSMR jobSMR = new JobSMR();
                JobSMRBase jobSMRBase = new JobSMRBase();
                float smr_v_match = 0;

                jobSMR.setSmr_resume(resume);
                jobSMR.setSmr_job(job);
                jobSMR.setSmr_v_success(0);
                jobSMR.setSmr_v_average(0);
                jobSMR.setSmr_v_popularity(0);

                int valueTempt = 0;
                //3.分别计算20项加权项
                //3.1 专排百分比
                valueTempt = valueComponent.jobJmrBaseRanking(student.getS_ranking());
                jobSMRBase.setSmr_b_ranking(valueTempt);
                smr_v_match += valueTempt;
                //3.2
                //3.3 简历被要取次数
                valueTempt = valueComponent.jobJmrBaseRCount(resume.getR_count());
                jobSMRBase.setSmr_b_r_count(valueTempt);
                smr_v_match += valueTempt;
                //3.4 论文数
                valueTempt = valueComponent.jobJmrBaseXCount(resume.getR_p_count());
                jobSMRBase.setSmr_b_p_count(valueTempt);
                smr_v_match += valueTempt;
                //3.5 掌握技能
                valueTempt = valueComponent.jobJmrBaseXCount(resume.getR_s_count());
                jobSMRBase.setSmr_b_s_count(valueTempt);
                smr_v_match += valueTempt;
                //3.6 专业技能证书
                valueTempt = valueComponent.jobJmrBaseXCount(resume.getR_c_count());
                jobSMRBase.setSmr_b_c_count(valueTempt);
                smr_v_match += valueTempt;
                //3.7 获得荣誉/奖项
                valueTempt = valueComponent.jobJmrBaseXCount(resume.getR_h_count());
                jobSMRBase.setSmr_b_h_count(valueTempt);
                smr_v_match += valueTempt;
                //3.8
                //3.9 地区意向
                valueTempt = valueComponent.jobJmrBaseLocation(job.getJ_location(), resume.getR_e_location());
                jobSMRBase.setSmr_b_location(valueTempt);
                smr_v_match += valueTempt;
                //3.10 五险一金
                valueTempt = valueComponent.jobJmrBaseWelfare(job.getJ_insurance(), resume.getS_if_insurance());
                jobSMRBase.setSmr_b_insurance(valueTempt);
                smr_v_match += valueTempt;
                //3.11 定期体检
                valueTempt = valueComponent.jobJmrBaseWelfare(job.getJ_check_up(), resume.getS_if_check_up());
                jobSMRBase.setSmr_b_check_up(valueTempt);
                smr_v_match += valueTempt;
                //3.12 年终奖
                valueTempt = valueComponent.jobJmrBaseWelfare(job.getJ_a_bonus(), resume.getS_if_a_bonus());
                jobSMRBase.setSmr_b_a_bonus(valueTempt);
                smr_v_match += valueTempt;
                //3.13 带薪年假
                valueTempt = valueComponent.jobJmrBaseWelfare(job.getJ_p_leave(), resume.getS_if_p_leave());
                jobSMRBase.setSmr_b_p_leave(valueTempt);
                smr_v_match += valueTempt;
                //3.14 加班补助
                valueTempt = valueComponent.jobJmrBaseWelfare(job.getJ_o_allowance(), resume.getS_if_o_allowance());
                jobSMRBase.setSmr_b_o_allowance(valueTempt);
                smr_v_match += valueTempt;
                //3.15 股票期权
                valueTempt = valueComponent.jobJmrBaseWelfare(job.getJ_stock(), resume.getS_if_stock());
                jobSMRBase.setSmr_b_stock(valueTempt);
                smr_v_match += valueTempt;
                //3.16 交通补贴
                valueTempt = valueComponent.jobJmrBaseWelfare(job.getJ_t_subside(), resume.getS_if_t_subside());
                jobSMRBase.setSmr_b_t_subside(valueTempt);
                smr_v_match += valueTempt;
                //3.17 住房补贴
                valueTempt = valueComponent.jobJmrBaseWelfare(job.getJ_h_subside(), resume.getS_if_h_subside());
                jobSMRBase.setSmr_b_h_subside(valueTempt);
                smr_v_match += valueTempt;
                //3.18 岗位需要/学生愿意出差
                valueTempt = valueComponent.jobJmrBaseBTrip(job.getJ_b_trip(), resume.getS_if_b_trip());
                jobSMRBase.setSmr_b_b_trip(valueTempt);
                smr_v_match += valueTempt;
                //3.19 毕业学校等级
                valueTempt = valueComponent.jobJmrBaseCLevel(job.getJ_c_level(), student.getS_c_level());
                jobSMRBase.setSmr_b_c_level(valueTempt);
                smr_v_match += valueTempt;
                //3.20 学历
                valueTempt = valueComponent.jobJmrBaseEHistory(job.getJ_e_history(), student.getS_e_history());
                jobSMRBase.setSmr_e_history(valueTempt);
                smr_v_match += valueTempt;
                //3.21 英语水平
                valueTempt = valueComponent.jobJmrBaseELanguage(job.getJ_e_language(), student.getS_e_language());
                jobSMRBase.setSmr_b_e_language(valueTempt);
                smr_v_match += valueTempt;
                //3.22 期望薪资
                valueTempt = valueComponent.jobJmrBaseSRange(job.getJ_s_range(), student.getS_s_range());
                jobSMRBase.setSmr_b_s_range(valueTempt);
                smr_v_match += valueTempt;

                jobSMR.setSmr_v_match(smr_v_match);
                companyService.addJobSMRBase(jobSMRBase);
                jobSMR.setSmr_base(jobSMRBase);
                companyService.addJobSMR(jobSMR);

            });

            //-----------(2)、计算成功率 smr_v_success-------------
            List<JobSMR> jobSMRs = companyService.getJobSMRByJob(job.getJ_id());
            jobSMRs.forEach(jobSMR -> {
                List<Resume> similarResumes = studentService.getSimilarResumesByJobSMR(jobSMR, jobSMRs);// finished by * 欧式距离 *
                List<Job> qualifiedJRJobs = companyService.getJRJobsByResumes(similarResumes);// finished by JR
                float smr_v_success = companyService.getJobSMRSuccessValue(qualifiedJRJobs, job);
                // * classified to three classes, high, medium and low, by kMeans *
                jobSMR.setSmr_v_success(smr_v_success);
            });

            //-----------(3)、计算平均值 smr_v_average-------------
            //-----------(4)、计算热度 smr_v_popularity-------------

//
        });
    }

    public float getJobSMRSuccessValue(List<Job> jobs, Job job){
        return 0;
    }

    /*---------企业匹配的学生信息的各项数值（JobSMRBase）---------
    -------检索：管理员，公司，就业专员
    -------更新：服务器
    -------创建：服务器
    -------删除：服务器
    --------------------------------------------------*/
    public JobSMRBase addJobSMRBase(JobSMRBase jobSMRBase){
        jobSMRBaseRepository.save(jobSMRBase);
        return jobSMRBase;
    }

    /*---------（JobResume）---------
    --------------------------------------------------*/
    public JobResume getJobResumeByJob(int jid){
        return jobResumeRepository.getJobResumesByJob(jid).orElse(null);
    }
    public Resume getJRResumeByJob(int jid){
        return jobResumeRepository.getJRResumesByJob(jid).orElse(null);
    }
    public List<JobResume> getJobResumesByJobs(List<Job> jobs){
        List<JobResume> jobResumes = new ArrayList<>();
        jobs.forEach(j -> {
            JobResume jobResume = companyService.getJobResumeByJob(j.getJ_id());
            if (jobResume != null) jobResumes.add(jobResume);
        });
        return jobResumes;
    }
    public List<Resume> getJRResumesByJobs(List<Job> jobs){
        List<Resume> resumes = new ArrayList<>();
        jobs.forEach(j -> {
            Resume resume = companyService.getJRResumeByJob(j.getJ_id());
            if (resume != null) resumes.add(resume);
        });
        return resumes;
    }

    public JobResume getJobResumeByResume(int rid){
        return jobResumeRepository.getJobResumesByResume(rid).orElse(null);
    }
    public Job getJRJobByResume(int rid){
        return jobResumeRepository.getJRJobsByResume(rid).orElse(null);
    }
    public List<JobResume> getJobResumesByResumes(List<Resume> resumes){
        List<JobResume> jobResumes = new ArrayList<>();
        resumes.forEach(r -> {
            JobResume jobResume = companyService.getJobResumeByResume(r.getR_id());
            if (jobResume != null) jobResumes.add(jobResume);
        });
        return jobResumes;
    }
    public List<Job> getJRJobsByResumes(List<Resume> resumes){
        List<Job> jobs = new ArrayList<>();
        resumes.forEach(r -> {
            Job job = companyService.getJRJobByResume(r.getR_id());
            if (job != null) jobs.add(job);
        });
        return jobs;
    }
}

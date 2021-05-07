package com.example.springbootgraduationdesign.service;

import com.example.springbootgraduationdesign.component.EuclideanDistanceComponent;
import com.example.springbootgraduationdesign.component.KMeansComponent;
import com.example.springbootgraduationdesign.component.TransferComponent;
import com.example.springbootgraduationdesign.component.ValueComponent;
import com.example.springbootgraduationdesign.component.vo.JobSMRPersonalizedVo;
import com.example.springbootgraduationdesign.component.vo.JobVo;
import com.example.springbootgraduationdesign.component.vo.PersonalizedSMRVo;
import com.example.springbootgraduationdesign.entity.*;
import com.example.springbootgraduationdesign.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
    private CompanyFavoredResumeRepository companyFavoredResumeRepository;
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
    private JobSystemDefaultWeightRepository jobSystemDefaultWeightRepository;


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
    @Autowired
    private KMeansComponent kMeansComponent;
    @Autowired
    private TransferComponent transferComponent;
    @Autowired
    private EuclideanDistanceComponent euclideanDistanceComponent;


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
    public List<Company> getCompaniesByStudentFavoredJobs(List<StudentFavoredJob> studentFavoredJobs){
        List<Company> companies = new ArrayList<>();
        for (StudentFavoredJob studentFavoredJob : studentFavoredJobs) {
            companies.add(studentFavoredJob.getStudentFavoredJobPK().getSfj_job().getJ_company());
        }
        return companies;
    }
    public List<Company> getCompaniesByJobResumes(List<JobResume> jobResumes){
        List<Company> companies = new ArrayList<>();
        for (JobResume jobResume : jobResumes) {
            companies.add(jobResume.getJobResumePK().getJr_job().getJ_company());
        }
        return companies;
    }
    public List<Company> getCompaniesByFStage(EnumWarehouse.FINANCING_STAGE financingStage){
        return companyRepository.getCompaniesByFStage(financingStage).orElse(new ArrayList<>());
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
            studentService.deleteResumeJMRsByJob(jid);
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
    public List<Job> getAllJobs(){
        return jobRepository.findAll();
    }
    public List<Job> getJobsByCompany(int cid){
        return jobRepository.getJobsByCompany(cid).orElse(new ArrayList<>());
    }
    public List<Job> getSimilarJobsByJob(Job job){
        return null;
    }
    public List<Job> getJobsByCLevelAndHistory(EnumWarehouse.C_LEVEL level, EnumWarehouse.E_HISTORY history){
        return jobRepository.getJobsByCLevelAndHistory(level,history).orElse(new ArrayList<>());
    }
    public List<Job> getResumeJMRQualifiedJobsByStudentResume(StudentResume studentResume){
        Student student = studentResume.getStudentResumePK().getSr_student();

        //匹配学校和学历符合的岗位
        EnumWarehouse.C_LEVEL studentLevel = student.getS_c_level();
        EnumWarehouse.E_HISTORY studentHistory = student.getS_e_history();
        List<Job> jobs = companyService.getJobsByCLevelAndHistory(studentLevel,studentHistory);

        //匹配性别、语言、薪资、工作经验、项目经验、应届符合的岗位
        EnumWarehouse.GENDER studentGender = student.getS_gender();
        EnumWarehouse.E_LANGUAGE studentELanguage = student.getS_e_language();
        EnumWarehouse.S_RANGE studentRange = student.getS_s_range();
        EnumWarehouse.IF_IS_OR_NOT studentIfCareer = student.getS_if_career();
        EnumWarehouse.IF_IS_OR_NOT studentIfFresh = student.getS_if_fresh();
        EnumWarehouse.IF_IS_OR_NOT studentIfProject = student.getS_if_project_experience();

        jobs.stream()
                .filter(j -> (j.getJ_gender().ordinal() == 0 || studentGender.ordinal() == j.getJ_gender().ordinal()))
                .filter(j -> (j.getJ_e_language().ordinal() == 0 || studentELanguage.ordinal() <= j.getJ_e_language().ordinal()))
                .filter(j -> studentRange.ordinal() <= j.getJ_s_range().ordinal())
                .filter(j -> studentIfFresh.ordinal() <= j.getJ_if_fresh().ordinal())
                .filter( j -> studentIfCareer.ordinal() <= j.getJ_if_career().ordinal())
                .filter(j -> studentIfProject.ordinal() <= j.getJ_if_project_experience().ordinal())
                .collect(Collectors.toList());

        //匹配行业、专业符合的岗位
        List<Industry> studentIndustries = industryService.getIndustriesByStudent(student.getS_id());
        Profession studentProfession = student.getS_profession();
        List<Job> jobList = new ArrayList<>();

        jobs.forEach(job -> {
            boolean inFlag = false;
            boolean prFlag = false;

            Industry jobIndustry = job.getJ_company().getC_industry();
            for (Industry studentIndustry : studentIndustries){
                if (studentIndustry == jobIndustry){
                    inFlag = true;
                    break;
                }
            }
            if (!inFlag) return;

            List<Profession> jobProfessions = professionService.getProfessionsByJob(job.getJ_id());
            for (Profession jobProfession : jobProfessions){
                if (jobProfession == studentProfession){
                    prFlag = true;
                    break;
                }
            }
            if (prFlag) jobList.add(job);

        });
        return jobList;
    }
    public List<Job> getSimilarJobsByResumeJMR(ResumeJMR resumeJMR, List<ResumeJMR> resumeJMRs){
        List<Job> jobs = new ArrayList<>();
        int distanceStandard = 2;
        double[] resumeJMRArray = transferComponent.transferResumeJMRBaseToArray(resumeJMR.getJmr_base());
        resumeJMRs.forEach(rj -> {
            double distance = euclideanDistanceComponent.sim_distance(resumeJMRArray,
                    transferComponent.transferResumeJMRBaseToArray(rj.getJmr_base()));
            if (distance < distanceStandard) jobs.add(rj.getJmr_job());
        });
        return jobs;
    }
    public List<Job> getJobsByStudentFavoredJobs(List<StudentFavoredJob> studentFavoredJobs){
        List<Job> jobs = new ArrayList<>();
        for (StudentFavoredJob studentFavoredJob : studentFavoredJobs) {
            jobs.add(studentFavoredJob.getStudentFavoredJobPK().getSfj_job());
        }
        return jobs;
    }
    public List<Job> getJobsByJobResumes(List<JobResume> jobResumes){
        List<Job> jobs = new ArrayList<>();
        for (JobResume jobResume : jobResumes) {
            jobs.add(jobResume.getJobResumePK().getJr_job());
        }
        return jobs;
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
        studentService.deleteResumeJMRsByJob(jid);
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
    public List<CompanyJob> getCompanyJobsByJobs(List<Job> jobs){
        List<CompanyJob> companyJobs = new ArrayList<>();
        jobs.forEach(job -> {
            if (job.isPosted()) companyJobs.add(companyService.getCompanyJobByJob(job.getJ_id()));
        });
        return companyJobs;
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
    public List<JobSMR> getJobSMRsByJob(int jid){
        return jobSMRRepository.getJobSMRsByJob(jid).orElse(new ArrayList<>());
    }
    public JobSMR getJobSMRByJobAndResume(int jid, int rid){
        return jobSMRRepository.getJobSMRByJobAndResume(jid, rid).orElse(null);
    }

    //定时执行，匹配每一个学生相对于每个岗位的条件符合值
    public void getJobSMR_Match(){
        jobSMRRepository.deleteAll();
        jobSMRBaseRepository.deleteAll();

        // 获取所有企业已发布的岗位
        List<CompanyJob> companyJobs = companyService.getAllCompanyJobs();

        //只获取两个companyjob测试
        int COUNT = 2;
        List<CompanyJob> companyJobsTemp = new ArrayList<>();
        for (int i = 0; i < COUNT; i++){
            companyJobsTemp.add(companyJobs.get(i));
        }

        companyJobsTemp.forEach(companyJob -> {
            companyService.getOneJobSMR_Match(companyJob);
        });
    }

    //及时执行，执行系统默认权重岗位匹配
    public List<JobSMR> getJobSMR_Match(int jid){
        CompanyJob companyJob = companyService.getCompanyJobByJob(jid);
        List<JobSMR> jobSMRs = companyService.getJobSMRsByJob(jid);
        List<JobSMR> jobSMRsNew = new ArrayList<>();
        if (jobSMRs.size() == 0){
            companyService.getOneJobSMR_Match(companyJob);
            jobSMRs = companyService.getJobSMRsByJob(jid);
        }
        for (JobSMR jobSMR : jobSMRs) {
            ResumeSystemDefaultWeight resumeSystemDefaultWeight = studentService.getResumeSystemDefaultWeight();
            JobSMRBase jobSMRBase = jobSMR.getSmr_base();
            JobSMR jobSMRNew = new JobSMR();

            double valueTempt;
            double smr_v_match = 0;

            jobSMRNew.setSmr_id(jobSMR.getSmr_id());
            jobSMRNew.setSmr_base(jobSMRBase);
            jobSMRNew.setSmr_job(jobSMR.getSmr_job());
            jobSMRNew.setSmr_resume(jobSMR.getSmr_resume());
            jobSMRNew.setSmr_v_success(jobSMR.getSmr_v_success());
            jobSMRNew.setSmr_v_popularity(jobSMR.getSmr_v_popularity());

            System.out.println("resumeSystemDefaultWeight" + resumeSystemDefaultWeight);
            valueTempt = jobSMRBase.getSmr_b_ranking() * resumeSystemDefaultWeight.getRsdw_ranking();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_p_count() * resumeSystemDefaultWeight.getRsdw_p_count();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_s_count() * resumeSystemDefaultWeight.getRsdw_s_count();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_c_count() * resumeSystemDefaultWeight.getRsdw_c_count();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_h_count() * resumeSystemDefaultWeight.getRsdw_h_count();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_location() * resumeSystemDefaultWeight.getRsdw_location();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_insurance() * resumeSystemDefaultWeight.getRsdw_insurance();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_check_up() * resumeSystemDefaultWeight.getRsdw_check_up();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_a_bonus() * resumeSystemDefaultWeight.getRsdw_a_bonus();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_p_leave() * resumeSystemDefaultWeight.getRsdw_p_leave();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_o_allowance() * resumeSystemDefaultWeight.getRsdw_o_allowance();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_stock() * resumeSystemDefaultWeight.getRsdw_b_stock();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_t_subside() * resumeSystemDefaultWeight.getRsdw_t_subside();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_h_subside() * resumeSystemDefaultWeight.getRsdw_h_subside();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_b_trip() * resumeSystemDefaultWeight.getRsdw_b_trip();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_c_level() * resumeSystemDefaultWeight.getRsdw_c_level();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_e_history() * resumeSystemDefaultWeight.getRsdw_e_history();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_e_language() * resumeSystemDefaultWeight.getRsdw_e_language();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_s_range() * resumeSystemDefaultWeight.getRsdw_s_range();
            smr_v_match += valueTempt;

            jobSMRNew.setSmr_v_match(smr_v_match);
            System.out.println("smr_v_match:" + smr_v_match);
            int beautify = 1000;
            double matchBase = 19 * 2;
            double success = transferComponent.getSuccessByDegree(jobSMR.getSmr_v_success());
            double smr_v_average = valueComponent.getAverage(success, smr_v_match/matchBase) * beautify;
            jobSMRNew.setSmr_v_average(smr_v_average);
            jobSMRsNew.add(jobSMRNew);
            System.out.println("smr_v_average:" + smr_v_average);
        }
        return jobSMRsNew;
    }

    //及时执行，根据用户的自定义权重进行匹配
    public List<JobSMRPersonalizedVo> getJobSMR_Match(int jid, PersonalizedSMRVo personalizedSMRVo){
        List<JobSMR> jobSMRs = companyService.getJobSMRsByJob(jid);
        if (jobSMRs.size() == 0){
            companyService.getJobSMR_Match(jid);
            jobSMRs = companyService.getJobSMRsByJob(jid);
        }
        List<JobSMRPersonalizedVo> jobSMRPersonalizedVos = new ArrayList<>();
        for (JobSMR jobSMR : jobSMRs) {
            JobSMRPersonalizedVo jobSMRPersonalizedVo = new JobSMRPersonalizedVo();

            double valueTempt;
            double smr_v_match = 0;
            JobSMRBase jobSMRBase = jobSMR.getSmr_base();
            jobSMRPersonalizedVo.setSmr_id(jobSMR.getSmr_id());
            jobSMRPersonalizedVo.setSmr_base(jobSMRBase);
            jobSMRPersonalizedVo.setSmr_job(jobSMR.getSmr_job());
            jobSMRPersonalizedVo.setSmr_resume(jobSMR.getSmr_resume());
            jobSMRPersonalizedVo.setSmr_v_success(jobSMR.getSmr_v_success());
            jobSMRPersonalizedVo.setSmr_v_popularity(jobSMR.getSmr_v_popularity());

            valueTempt = jobSMRBase.getSmr_b_ranking() * personalizedSMRVo.getRanking();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_r_count() * personalizedSMRVo.getR_count();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_p_count() * personalizedSMRVo.getP_count();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_s_count() * personalizedSMRVo.getS_count();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_c_count() * personalizedSMRVo.getC_count();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_h_count() * personalizedSMRVo.getH_count();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_location() * personalizedSMRVo.getLocation();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_insurance() * personalizedSMRVo.getInsurance();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_check_up() * personalizedSMRVo.getC_up();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_a_bonus() * personalizedSMRVo.getA_bonus();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_p_leave() * personalizedSMRVo.getP_leave();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_o_allowance() * personalizedSMRVo.getO_allowance();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_stock() * personalizedSMRVo.getStock();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_t_subside() * personalizedSMRVo.getT_subside();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_h_subside() * personalizedSMRVo.getH_subside();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_b_trip() * personalizedSMRVo.getB_trip();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_c_level() * personalizedSMRVo.getC_level();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_e_history() * personalizedSMRVo.getE_history();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_e_language() * personalizedSMRVo.getE_language();
            smr_v_match += valueTempt;

            valueTempt = jobSMRBase.getSmr_b_s_range() * personalizedSMRVo.getS_range();
            smr_v_match += valueTempt;

            jobSMRPersonalizedVo.setSmr_v_match(smr_v_match);
            int beautify = 1000;
            double matchBase = 21 * 2;
            double success = transferComponent.getSuccessByDegree(jobSMR.getSmr_v_success());
            double smr_v_average = valueComponent.getAverage(success, smr_v_match/matchBase) * beautify;
            jobSMRPersonalizedVo.setSmr_v_average(smr_v_average);
            jobSMRPersonalizedVos.add(jobSMRPersonalizedVo);
        }
        return jobSMRPersonalizedVos;
    }

    //执行一个岗位的匹配
    public void getOneJobSMR_Match(CompanyJob companyJob){
        Job job = companyJob.getCompanyJobPk().getCj_job();
        List<Student> qualifiedStudents = studentService.getJobSMRQualifiedStudentsByCompanyJob(companyJob);
        List<StudentResume> qualifiedStudentResumes = studentService.getStudentResumesByStudents(qualifiedStudents);
        //-----------(1)、计算匹配值 smr_v_match-------------
        qualifiedStudentResumes.forEach(studentResume -> {
            Student student = studentResume.getStudentResumePK().getSr_student();
            Resume resume = studentResume.getStudentResumePK().getSr_resume();

            JobSMR jobSMR = new JobSMR();
            JobSMRBase jobSMRBase = new JobSMRBase();
            float smr_v_match = 0;

            jobSMR.setSmr_resume(resume);
            jobSMR.setSmr_job(job);
            jobSMR.setSmr_v_success(EnumWarehouse.SUCCESS_DEGREE.低);
            jobSMR.setSmr_v_average(0);
            jobSMR.setSmr_v_popularity(0);

            int valueTempt = 0;
            //3.分别计算20项加权项
            //3.1 专排百分比
            valueTempt = valueComponent.jobSmrBaseRanking(student.getS_ranking());
            jobSMRBase.setSmr_b_ranking(valueTempt);
            smr_v_match += valueTempt;
            //3.2
            //3.3 简历被要取次数
            //3.4 论文数
            valueTempt = valueComponent.jobSmrBaseXCount(resume.getR_p_count());
            jobSMRBase.setSmr_b_p_count(valueTempt);
            smr_v_match += valueTempt;
            //3.5 掌握技能
            valueTempt = valueComponent.jobSmrBaseXCount(resume.getR_s_count());
            jobSMRBase.setSmr_b_s_count(valueTempt);
            smr_v_match += valueTempt;
            //3.6 专业技能证书
            valueTempt = valueComponent.jobSmrBaseXCount(resume.getR_c_count());
            jobSMRBase.setSmr_b_c_count(valueTempt);
            smr_v_match += valueTempt;
            //3.7 获得荣誉/奖项
            valueTempt = valueComponent.jobSmrBaseXCount(resume.getR_h_count());
            jobSMRBase.setSmr_b_h_count(valueTempt);
            smr_v_match += valueTempt;
            //3.8
            //3.9 地区意向
            valueTempt = valueComponent.jobSmrBaseLocation(job.getJ_location(), resume.getR_e_location());
            jobSMRBase.setSmr_b_location(valueTempt);
            smr_v_match += valueTempt;
            //3.10 五险一金
            valueTempt = valueComponent.jobSmrBaseWelfare(job.getJ_insurance(), resume.getR_if_insurance());
            jobSMRBase.setSmr_b_insurance(valueTempt);
            smr_v_match += valueTempt;
            //3.11 定期体检
            valueTempt = valueComponent.jobSmrBaseWelfare(job.getJ_check_up(), resume.getR_if_check_up());
            jobSMRBase.setSmr_b_check_up(valueTempt);
            smr_v_match += valueTempt;
            //3.12 年终奖
            valueTempt = valueComponent.jobSmrBaseWelfare(job.getJ_a_bonus(), resume.getR_if_a_bonus());
            jobSMRBase.setSmr_b_a_bonus(valueTempt);
            smr_v_match += valueTempt;
            //3.13 带薪年假
            valueTempt = valueComponent.jobSmrBaseWelfare(job.getJ_p_leave(), resume.getR_if_p_leave());
            jobSMRBase.setSmr_b_p_leave(valueTempt);
            smr_v_match += valueTempt;
            //3.14 加班补助
            valueTempt = valueComponent.jobSmrBaseWelfare(job.getJ_o_allowance(), resume.getR_if_o_allowance());
            jobSMRBase.setSmr_b_o_allowance(valueTempt);
            smr_v_match += valueTempt;
            //3.15 股票期权
            valueTempt = valueComponent.jobSmrBaseWelfare(job.getJ_stock(), resume.getR_if_stock());
            jobSMRBase.setSmr_b_stock(valueTempt);
            smr_v_match += valueTempt;
            //3.16 交通补贴
            valueTempt = valueComponent.jobSmrBaseWelfare(job.getJ_t_subside(), resume.getR_if_t_subside());
            jobSMRBase.setSmr_b_t_subside(valueTempt);
            smr_v_match += valueTempt;
            //3.17 住房补贴
            valueTempt = valueComponent.jobSmrBaseWelfare(job.getJ_h_subside(), resume.getR_if_h_subside());
            jobSMRBase.setSmr_b_h_subside(valueTempt);
            smr_v_match += valueTempt;
            //3.18 岗位需要/学生愿意出差
            valueTempt = valueComponent.jobSmrBaseBTrip(job.getJ_b_trip(), resume.getR_if_b_trip());
            jobSMRBase.setSmr_b_b_trip(valueTempt);
            smr_v_match += valueTempt;
            //3.19 毕业学校等级
            valueTempt = valueComponent.jobSmrBaseCLevel(job.getJ_c_level(), student.getS_c_level());
            jobSMRBase.setSmr_b_c_level(valueTempt);
            smr_v_match += valueTempt;
            //3.20 学历
            valueTempt = valueComponent.jobSmrBaseEHistory(job.getJ_e_history(), student.getS_e_history());
            jobSMRBase.setSmr_b_e_history(valueTempt);
            smr_v_match += valueTempt;
            //3.21 英语水平
            valueTempt = valueComponent.jobSmrBaseELanguage(job.getJ_e_language(), student.getS_e_language());
            jobSMRBase.setSmr_b_e_language(valueTempt);
            smr_v_match += valueTempt;
            //3.22 期望薪资
            valueTempt = valueComponent.jobSmrBaseSRange(job.getJ_s_range(), student.getS_s_range());
            jobSMRBase.setSmr_b_s_range(valueTempt);
            smr_v_match += valueTempt;

            List<Position> studentPositions = positionService.getPositionsByStudent(student.getS_id());
            //岗位意向
            valueTempt = valueComponent.jobSmrBasePosition(job.getJ_position(), studentPositions);
            jobSMRBase.setSmr_b_position(valueTempt);
            smr_v_match += valueTempt;

            jobSMR.setSmr_v_match(smr_v_match);
            companyService.addJobSMRBase(jobSMRBase);
            jobSMR.setSmr_base(jobSMRBase);
            companyService.addJobSMR(jobSMR);
            System.out.println("smr_v_match:" + smr_v_match);
        });

        //-----------(2)、smr_v_success smr_v_average smr_v_popularity-------------
        //获取当前job匹配到的学生简历
        List<JobSMR> jobSMRs = companyService.getJobSMRsByJob(job.getJ_id());
        //遍历当前job匹配到的学生简历
        jobSMRs.forEach(jobSMR -> {
            //-----------(2)、计算成功率 smr_v_success-------------
            System.out.println("-----------(2)、计算成功率 smr_v_success-------------");
            //获取当前学生简历在 当前job匹配到的学生中 的相似简历 finished by * 欧式距离 *
            List<Resume> similarResumes = studentService.getSimilarResumesByJobSMR(jobSMR, jobSMRs);
            //获取这些相似简历曾经选中过的岗位 finished by JR
            List<Job> qualifiedJRJobs = companyService.getJobsByResumes_ResumeToJob(similarResumes);
            //计算当前岗位在 曾经选中过的岗位中 的等级 * classified to three classes, high, medium and low, by kMeans *
            EnumWarehouse.SUCCESS_DEGREE smr_v_success = companyService.getJobSMRSuccessDegree(qualifiedJRJobs, job);
            jobSMR.setSmr_v_success(smr_v_success);
            System.out.println("smr_v_success:" + smr_v_success);

            //-----------(3)、计算平均值 smr_v_average-------------
            System.out.println("-----------(3)、计算平均值 smr_v_average-------------");
            double matchBase = 21 * 2;
            //根据当前成功率等级获得成功率值
            double success = transferComponent.getSuccessByDegree(smr_v_success);
            double smr_v_average = valueComponent.getAverage(success, jobSMR.getSmr_v_match()/matchBase);
            jobSMR.setSmr_v_average(smr_v_average);
            System.out.println("smr_v_average:" + smr_v_average);

            //-----------(4)、计算热度 smr_v_popularity-------------
            System.out.println("-----------(4)、计算热度 smr_v_popularity-------------");
            Resume resume = jobSMR.getSmr_resume();
            int smr_v_popularity = getJobResumeCountByResume(resume.getR_id(),true);
            jobSMR.setSmr_v_popularity(smr_v_popularity);
            System.out.println("smr_v_popularity:" + smr_v_popularity);
        });
    }

    public EnumWarehouse.SUCCESS_DEGREE getJobSMRSuccessDegree(List<Job> jobs, Job job){
        int k = 3;
        int loopCount = 50;
        List<double[]> jobsList = transferComponent.transferJobsToArray(job, jobs);
        Map<String,List<double[]>> listMap = kMeansComponent.kMeansFunction(jobsList, k, loopCount);
        return kMeansComponent.getDegreeByKMeans(listMap, k,0);
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
    public JobSMRBase getJobSMRBaseById(int jmbid){
        return jobSMRBaseRepository.findById(jmbid).orElse(null);
    }

    /*---------（JobResume）---------
    --------------------------------------------------*/
    public JobResume addJobResume(JobResume jobResume){
        jobResumeRepository.save(jobResume);
        return jobResume;
    }

    public JobResume updateJobResume(JobResume jobResume){
        jobResumeRepository.save(jobResume);
        return jobResume;
    }

    public JobResume getJobResumeByJob(int jid){
        return jobResumeRepository.getJobResumesByJob(jid).orElse(null);
    }

    public List<JobResume> getJobResumesByJobs(List<Job> jobs){
        List<JobResume> jobResumes = new ArrayList<>();
        jobs.forEach(j -> {
            JobResume jobResume = companyService.getJobResumeByJob(j.getJ_id());
            if (jobResume != null) jobResumes.add(jobResume);
        });
        return jobResumes;
    }
    public JobResume getJobResumeByJobAndResume(int jid, int rid){
        return jobResumeRepository.getJobResumeByJobAndResume(jid,rid).orElse(null);
    }
    public List<JobResume> getJobResumesByResumes_ResumeToJob(List<Resume> resumes){
        List<JobResume> jobResumes = new ArrayList<>();
        resumes.forEach(r -> {
            List<JobResume> jrs = companyService.getJobResumesByResume_ResumeToJob(r.getR_id(), true);
            if (jrs.size() != 0){
                jobResumes.addAll(jrs);
            }
        });
        return jobResumes;
    }
    public List<Job> getJobsByResumes_ResumeToJob(List<Resume> resumes){
        List<Job> jobs = new ArrayList<>();
        List<JobResume> jobResumes = companyService.getJobResumesByResumes_ResumeToJob(resumes);
        if (jobResumes.size() != 0){
            for (JobResume jobResume : jobResumes) {
                jobs.add(jobResume.getJobResumePK().getJr_job());
            }
        }
        return jobs;
    }
    public List<JobResume> getJobResumesByJob_JobToResume(int jid, boolean jobToResume){
        return jobResumeRepository.getJobResumesByJob_JobToResume(jid,jobToResume).orElse(new ArrayList<>());
    }
    public List<JobResume> getJobResumesByJobs_JobToResume(List<Job> jobs, boolean jobToResume){
        List<JobResume> jobResumes = new ArrayList<>();
        for (Job job : jobs) {
            jobResumes.addAll(companyService.getJobResumesByJob_JobToResume(job.getJ_id(),jobToResume));
        }
        return jobResumes;
    }
    public List<JobResume> getJobResumesByCompany_JobToResume(int cid, boolean jobToResume){
        List<Job> jobs = companyService.getJobsByCompany(cid);
        return companyService.getJobResumesByJobs_JobToResume(jobs, jobToResume);
    }
    public List<JobResume> getJobResumesByCompanies_JobToResume(List<Company> companies, boolean jobToResume){
        List<JobResume> jobResumes = new ArrayList<>();
        for (Company company : companies) {
            jobResumes.addAll(companyService.getJobResumesByCompany_JobToResume(company.getC_id(), jobToResume));
        }
        return jobResumes;
    }
    public List<JobResume> getJobResumesByCompany_JobToResume_ResumeToJob(int cid, boolean jobToResume, boolean resumeToJob){
        List<JobResume> jobResumes = companyService.getJobResumesByCompany_JobToResume(cid, jobToResume);
        return  jobResumes.stream()
                .filter(jobResume -> jobResume.isResumeToJob() == resumeToJob)
                .collect(Collectors.toList());
    }
    public List<JobResume> getJobResumesByCompanies_JobToResume_ResumeToJob(List<Company> companies, boolean jobToResume, boolean resumeToJob){
        List<JobResume> jobResumes = companyService.getJobResumesByCompanies_JobToResume(companies, true);
        return  jobResumes.stream()
                .filter(jobResume -> jobResume.isResumeToJob())
                .collect(Collectors.toList());
    }
    public int getJobResumeCountByResume(int rid, boolean jobToResume){
        return companyService.getJobResumesByResume_JobToResume(rid,jobToResume).size();
    }
    public List<JobResume> getJobResumesByResume_JobToResume(int rid,boolean jobToResume){
        return jobResumeRepository.getJobResumesByResume_JobToResume(rid,jobToResume).orElse(new ArrayList<>());
    }
    public List<JobResume> getJobResumesByResume_ResumeToJob(int rid,boolean resumeToJob){
        return jobResumeRepository.getJobResumesByResume_ResumeToJob(rid,resumeToJob).orElse(new ArrayList<>());
    }
    public List<JobResume> getJobResumesByCompany(int cid){
        return jobResumeRepository.getJobResumesByCompany(cid).orElse(new ArrayList<>());
    }

     /*----------企业收藏夹（CompanyFavoredResume）-----------
    -------检索：管理员，公司，就业专员
    -------更新：管理员，公司
    -------创建：管理员，公司
    -------删除：管理员，公司
    --------------------------------------------------*/
     public CompanyFavoredResume addCompanyFavoredResume(CompanyFavoredResume companyFavoredResume){
         return companyFavoredResumeRepository.save(companyFavoredResume);
     }

     public void deleteCompanyFavoredResumeByCompanyAndResume(int cid, int rid){
         companyFavoredResumeRepository.deleteCompanyFavoredResumeByCompanyAndResume(cid, rid);
     }

     public List<CompanyFavoredResume> getCompanyFavoredResumesByCompany(int cid){
         return companyFavoredResumeRepository.getCompanyFavoredResumesByCompany(cid).orElse(new ArrayList<>());
     }
     public List<CompanyFavoredResume> getCompanyFavoredResumesByCompanies(List<Company> companies){
         List<CompanyFavoredResume> companyFavoredResumes = new ArrayList<>();
         for (Company company : companies) {
             companyFavoredResumes.addAll(companyService.getCompanyFavoredResumesByCompany(company.getC_id()));
         }
         return companyFavoredResumes;
     }
     public List<CompanyFavoredResume> getAllCompanyFavoredResumes(){
         return companyFavoredResumeRepository.findAll();
     }
     public List<Job> getAllFavoredJobs(){
         List<Job> jobs = new ArrayList<>();
         List<StudentFavoredJob> studentFavoredJobs = studentService.getAllStudentFavoredJobs();
         if (studentFavoredJobs.size() != 0){
             for (StudentFavoredJob studentFavoredJob : studentFavoredJobs) {
                 jobs.add(studentFavoredJob.getStudentFavoredJobPK().getSfj_job());
             }
         }
        return jobs;
    }
    public CompanyFavoredResume getCompanyFavoredResumeByCompanyAndResume(int cid, int rid){
         return companyFavoredResumeRepository.getCompanyFavoredResumeByCompanyAndResume(cid,rid).orElse(null);
    }


     /*----------（JobSystemDefaultWeightRepository）-----------
    -------提供给学生端使用，衡量job各项信息重要程度
    --------------------------------------------------*/
     public JobSystemDefaultWeight addJobSystemDefaultWeight(JobSystemDefaultWeight jobSystemDefaultWeight){
         return jobSystemDefaultWeightRepository.save(jobSystemDefaultWeight);
     }
     public JobSystemDefaultWeight addJobSystemDefaultWeight(List<Double> weights){
         JobSystemDefaultWeight jobSystemDefaultWeight = new JobSystemDefaultWeight();
         jobSystemDefaultWeight.setJsdw_c_scale(weights.get(0));
         jobSystemDefaultWeight.setJsdw_c_f_stage(weights.get(1));
         jobSystemDefaultWeight.setJsdw_c_level(weights.get(2));
         jobSystemDefaultWeight.setJsdw_c_e_history(weights.get(3));
         jobSystemDefaultWeight.setJsdw_e_language(weights.get(4));
         jobSystemDefaultWeight.setJsdw_s_range(weights.get(5));
         jobSystemDefaultWeight.setJsdw_position(weights.get(6));
         jobSystemDefaultWeight.setJsdw_location(weights.get(7));
         jobSystemDefaultWeight.setJsdw_insurance(weights.get(8));
         jobSystemDefaultWeight.setJsdw_check_up(weights.get(9));
         jobSystemDefaultWeight.setJsdw_a_bonus(weights.get(10));
         jobSystemDefaultWeight.setJsdw_p_leave(weights.get(11));
         jobSystemDefaultWeight.setJsdw_o_allowance(weights.get(12));
         jobSystemDefaultWeight.setJsdw_stock(weights.get(13));
         jobSystemDefaultWeight.setJsdw_t_subside(weights.get(14));
         jobSystemDefaultWeight.setJsdw_h_subside(weights.get(15));
         jobSystemDefaultWeight.setJsdw_b_trip(weights.get(16));
         companyService.addJobSystemDefaultWeight(jobSystemDefaultWeight);
         return jobSystemDefaultWeight;
     }

     public void deleteAllJobSystemDefaultWeights(){
         jobSystemDefaultWeightRepository.deleteAll();
     }

     public JobSystemDefaultWeight getJobSystemDefaultWeight(){
         JobSystemDefaultWeight jobSystemDefaultWeight = new JobSystemDefaultWeight();
        List<JobSystemDefaultWeight> jobSystemDefaultWeights = jobSystemDefaultWeightRepository.findAll();
        if (jobSystemDefaultWeights.size() != 0){
            jobSystemDefaultWeight =jobSystemDefaultWeights.get(0);
        }
        return jobSystemDefaultWeight;
    }

}




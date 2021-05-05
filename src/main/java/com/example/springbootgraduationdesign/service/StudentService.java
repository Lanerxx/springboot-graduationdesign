package com.example.springbootgraduationdesign.service;

import com.example.springbootgraduationdesign.component.EuclideanDistanceComponent;
import com.example.springbootgraduationdesign.component.KMeansComponent;
import com.example.springbootgraduationdesign.component.TransferComponent;
import com.example.springbootgraduationdesign.component.ValueComponent;
import com.example.springbootgraduationdesign.component.vo.*;
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
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private  StudentIndustryRepository studentIndustryRepository;
    @Autowired
    private StudentPositionRepository studentPositionRepository;
    @Autowired
    private StudentFavoredJobRepository studentFavoredJobRepository;
    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private StudentResumeRepository studentResumeRepository;
    @Autowired
    private ResumeJMRRepository resumeJMRRepository;
    @Autowired
    private ResumeJMRBaseRepository resumeJMRBaseRepository;
    @Autowired
    private JobResumeRepository jobResumeRepository;
    @Autowired
    private ResumeSystemDefaultWeightRepository resumeSystemDefaultWeightRepository;


    @Autowired
    private StudentService studentService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private ProfessionService professionService;

    @Autowired
    private ValueComponent valueComponent;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private EuclideanDistanceComponent euclideanDistanceComponent;
    @Autowired
    private TransferComponent transferComponent;
    @Autowired
    private KMeansComponent kMeansComponent;


    /*--------------学生信息（Student）---------------
    -------检索：管理员，学生，就业专员
    -------更新：管理员，学生
    -------创建：管理员，学生
    -------删除：管理员，学生（注销）
    --------------------------------------------------*/
    public Student addStudent(Student student){
        String pw = encoder.encode(student.getS_password());
        student.setS_password(pw);
        studentRepository.save(student);
        return student;
    }
    public List<Student> addStudents(List<Student> students){
        return null;
    }

    public void deleteStudent(int sid){
        studentRepository.deleteById(sid);
    }
    public void deleteAllStudents(){
        studentRepository.deleteAll();
    }

    public Student updateStudent(Student student){
        studentRepository.save(student);
        return student;
    }
    public Student updateStudent(StudentVo studentVo){
        Student student = studentVo.getStudent();
        studentIndustryRepository.deleteStudentIndustriesByStudent(student.getS_id());
        studentPositionRepository.deleteStudentPositionsByStudent(student.getS_id());
        studentService.updateStudent(student);

        List<Position> positions = studentVo.getPositions();
        List<Industry> industries = studentVo.getIndustries();
        for (Position po : positions){
            log.debug(po.getPo_name());
            StudentPosition studentPosition = new StudentPosition();
            StudentPositionPK studentPositionPK = new StudentPositionPK();
            studentPositionPK.setSp_position(po);
            studentPositionPK.setSp_student(student);
            studentPosition.setStudentPositionPK(studentPositionPK);
            studentPositionRepository.save(studentPosition);
        }
        for (Industry i : industries){
            StudentIndustry studentIndustry = new StudentIndustry();
            StudentIndustryPK studentIndustryPK = new StudentIndustryPK();
            studentIndustryPK.setSi_industry(i);
            studentIndustryPK.setSi_student(student);
            studentIndustry.setStudentIndustryPK(studentIndustryPK);
            studentIndustryRepository.save(studentIndustry);
        }
        return student;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }
    public Student getStudent(int sid){
        return studentRepository.findById(sid).orElse(null);
    }
    public List<Student> getStudentsByName(String name){
        return studentRepository.getStudentByName(name).orElse(new ArrayList<>());
    }
    public List<Student> getStudentsByCollege(String college){
        return studentRepository.getStudentByCollege(college).orElse(new ArrayList<>());
    }
    public Student getStudentByTelephone(String telephone){
        return studentRepository.getStudentByS_telephone(telephone).orElse(null);
    }
    public List<Student> getStudentsByCLevelAndHistory(EnumWarehouse.C_LEVEL level, EnumWarehouse.E_HISTORY history){
        return studentRepository.getStudentsByCLevelAndHistory(level, history).orElse(new ArrayList<>());
    }
    public List<Student> getJobSMRQualifiedStudentsByCompanyJob(CompanyJob companyJob){
        Company company = companyJob.getCompanyJobPk().getCj_company();
        Job job = companyJob.getCompanyJobPk().getCj_job();
        int jid = job.getJ_id();
        //匹配学校和学历符合的学生
        EnumWarehouse.C_LEVEL jobLevel = job.getJ_c_level();
        EnumWarehouse.E_HISTORY jobHistory = job.getJ_e_history();
        List<Student> students = studentService.getStudentsByCLevelAndHistory(jobLevel, jobHistory);

        //匹配性别、语言、薪资、工作经验、项目经验、应届符合的学生
        EnumWarehouse.GENDER jobGender = job.getJ_gender();
        EnumWarehouse.E_LANGUAGE jobELanguage = job.getJ_e_language();
        EnumWarehouse.S_RANGE jobRange = job.getJ_s_range();
        EnumWarehouse.IF_IS_OR_NOT jobIfCareer = job.getJ_if_career();
        EnumWarehouse.IF_IS_OR_NOT jobIfFresh = job.getJ_if_fresh();
        EnumWarehouse.IF_IS_OR_NOT jobIfProject = job.getJ_if_project_experience();
        students.stream()
                .filter(s ->  (jobGender.ordinal() == 0) || s.getS_gender().ordinal() == jobGender.ordinal())
                .filter(s -> ((jobELanguage.ordinal() == 0) || s.getS_e_language().ordinal() <= jobELanguage.ordinal()))
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
        return studentList;
    }


    private StudentIndustry addStudentIndustries(StudentIndustry studentIndustry) {
        studentIndustryRepository.save(studentIndustry);
        return studentIndustry;
    }
    private StudentPosition addStudentPositions(StudentPosition studentPosition) {
        studentPositionRepository.save(studentPosition);
        return studentPosition;
    }
    private void deleteStudentIndustriesByStudent(int sid) {
        studentIndustryRepository.deleteStudentIndustriesByStudent(sid);
    }
    private void deleteStudentPositionsByStudent(int sid) {
            studentPositionRepository.deleteStudentPositionsByStudent(sid);
    }


    /*------------简历信息（StudentResume）--------------
    -------检索：学生
    -------更新：学生
    -------创建：学生
    -------删除：学生
    --------------------------------------------------*/
    public Resume addResume(Resume resume){
        resumeRepository.save(resume);
        return resume;
    }

    public void deleteResume(int rid){
        resumeRepository.deleteById(rid);
    }
    public void deleteAllResumes(){
        resumeRepository.deleteAll();
    }
    public void deleteResumeAndRelated(Resume resume){
        int rid = resume.getR_id();
        if (resume.isPosted()){
            companyService.deleteJobSMRsByResume(rid);
            studentService.deleteResumeJMRsByResume(rid);
            studentService.deleteStudentResumeByResume(rid);
        }
        studentService.deleteResume(rid);
    }

    public Resume updateResume(Resume resume){
        resumeRepository.save(resume);
        return resume;
    }

    public Resume getResume(int rid){
        return resumeRepository.findById(rid).orElse(null);
    }
    public List<Resume> getResumesByStudentId(int sid){
        return resumeRepository.getResumesByStudent(sid).orElse(new ArrayList<>());
    }
    public List<Resume> getResumesByStudents(List<Student> students){
        List<Resume> resumes = new ArrayList<>();
        students.forEach(student -> {
            List<Resume> rs = studentService.getResumesByStudentId(student.getS_id());
            if (rs.size() != 0) resumes.addAll(rs);
        });
        return resumes;
    }
    public List<Resume> getSimilarResumesByJobSMR(JobSMR jobSMR, List<JobSMR> jobSMRs){
        List<Resume> resumes = new ArrayList<>();
        int distanceStandard = 2;
        double[] jobSMRArray = transferComponent.transferJobSMRBaseToArray(jobSMR.getSmr_base());
        jobSMRs.forEach(js -> {
            double distance = euclideanDistanceComponent.sim_distance(jobSMRArray,
                    transferComponent.transferJobSMRBaseToArray(js.getSmr_base()));
            if (distance < distanceStandard) resumes.add(js.getSmr_resume());

        });
        return resumes;
    }
    public List<Resume> getResumesByCompanyFavoredResumes(List<CompanyFavoredResume> companyFavoredResumes){
        List<Resume> resumes = new ArrayList<>();
        for (CompanyFavoredResume companyFavoredResume : companyFavoredResumes) {
            resumes.add(companyFavoredResume.getCompanyFavoredResumePK().getCfr_resume());
        }
        return resumes;
    }
    public List<Resume> getResumesByJobResumes(List<JobResume> jobResumes){
        List<Resume> resumes = new ArrayList<>();
        for (JobResume jobResume : jobResumes) {
            resumes.add(jobResume.getJobResumePK().getJr_resume());
        }
        return resumes;
    }


    /*--------学生已发布简历信息（StudentResume）----------
    -------检索：管理员，学生，就业专员
    -------更新：管理员，学生
    -------创建：管理员，学生
    -------删除：管理员，学生
    --------------------------------------------------*/
    public StudentResume addStudentResume(StudentResume studentResume){
        studentResumeRepository.save(studentResume);
        return studentResume;
    }
    public StudentResume addStudentResume(Student student, Resume resume){
        resume.setPosted(true);
        studentService.updateResume(resume);
        StudentResume studentResume = new StudentResume();
        StudentResumePK studentResumePK = new StudentResumePK();
        studentResumePK.setSr_student(student);
        studentResumePK.setSr_resume(resume);
        studentResume.setStudentResumePK(studentResumePK);
        studentService.addStudentResume(studentResume);
        return studentResume;
    }

    public void deleteStudentResumeByResume(int rid){
        studentResumeRepository.deleteStudentResumeByResume(rid);
    }
    public void deleteStudentResumeAndRelatedByResume(int rid){
        companyService.deleteJobSMRsByResume(rid);
        studentService.deleteResumeJMRsByResume(rid);
        studentService.deleteStudentResumeByResume(rid);
    }

    public StudentResume getStudentResumeByResume(int rid){
        return studentResumeRepository.getStudentResumeByResume(rid).orElse(null);
    }
    public List<StudentResume> getStudentResumes(int sid){
        return studentResumeRepository.getStudentResumesByStudent(sid).orElse(new ArrayList<>());
    }
    public List<StudentResume> getAllStudentResumes(){
        return studentResumeRepository.findAll();
    }
    public List<StudentResume> getStudentResumesByStudents(List<Student> students){
        List<StudentResume> studentResumes = new ArrayList<>();
        students.forEach(student -> {
            List<StudentResume> sr = studentService.getStudentResumesByStudent(student.getS_id());
            if (sr.size() != 0) studentResumes.addAll(sr);
        });
        return studentResumes.size() == 0 ? new ArrayList<>() : studentResumes;
    }
    public List<StudentResume> getStudentResumesByStudent(int sid){
        return studentResumeRepository.getStudentResumesByStudent(sid).orElse(new ArrayList<>());
    }

    /*---------学生匹配的企业信息（JobMatchResult）---------
    -------检索：管理员，学生，就业专员
    -------更新：服务器
    -------创建：服务器
    -------删除：服务器
    --------------------------------------------------*/
    public ResumeJMR addResumeJMR(ResumeJMR resumeJMR){
        resumeJMRRepository.save(resumeJMR);
        return resumeJMR;
    }

    public void deleteResumeJMRsByJob(int jid){
        resumeJMRRepository.deleteResumeJMRsByJob(jid);
    }
    public void deleteResumeJMRsByResume(int rid){
        resumeJMRRepository.deleteResumeJMRsByResume(rid);
    }

    public List<ResumeJMR> getAllJobMatchResults(){
        return resumeJMRRepository.findAll();
    }
    public List<ResumeJMR> getResumeJMRsByStudent(int sid){
        return resumeJMRRepository.getResumeJMRsByStudent(sid).orElse(new ArrayList<>());
    }
    public List<ResumeJMR> getResumeJMRsByResume(int rid){
        return resumeJMRRepository.getResumeJMRsByResume(rid).orElse(new ArrayList<>());
    }

    //定时执行，匹配每一个岗位相对于每个简历的条件符合值
    public void getResumeJMR_Match(){
        resumeJMRRepository.deleteAll();
        resumeJMRBaseRepository.deleteAll();

        // 获取所有企业已发布的岗位
        List<StudentResume> studentResumes = studentService.getAllStudentResumes();
        studentResumes.forEach(studentResume -> {
            studentService.getOneResumeJMR_Match(studentResume);
        });
    }

    //及时执行，根据系统默认权重进行匹配
    public List<ResumeJMR> getResumeJMR_Match(int rid){
        StudentResume studentResume = studentService.getStudentResumeByResume(rid);
        List<ResumeJMR> resumeJMRsNew = new ArrayList<>();
        List<ResumeJMR> resumeJMRs = studentService.getResumeJMRsByResume(rid);
        if (resumeJMRs.size() == 0){
            studentService.getOneResumeJMR_Match(studentResume);
            resumeJMRs = studentService.getResumeJMRsByResume(rid);
        }
        for (ResumeJMR resumeJMR : resumeJMRs) {
            JobSystemDefaultWeight jobSystemDefaultWeight = companyService.getJobSystemDefaultWeight();
            ResumeJMRBase resumeJMRBase = resumeJMR.getJmr_base();
            ResumeJMR resumeJMRNew = new ResumeJMR();

            resumeJMRNew.setJmr_id(resumeJMR.getJmr_id());
            resumeJMRNew.setJmr_v_success(resumeJMR.getJmr_v_success());
            resumeJMRNew.setJmr_job(resumeJMR.getJmr_job());
            resumeJMRNew.setJmr_resume(resumeJMR.getJmr_resume());
            resumeJMRNew.setJmr_v_popularity(resumeJMR.getJmr_v_popularity());
            resumeJMRNew.setJmr_base(resumeJMRBase);

            double valueTempt;
            double jmr_v_match = 0;
            valueTempt = resumeJMRBase.getJmr_b_c_scale() * jobSystemDefaultWeight.getJsdw_c_scale();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_c_f_stage() * jobSystemDefaultWeight.getJsdw_c_f_stage();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_c_level() * jobSystemDefaultWeight.getJsdw_c_level();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_e_history() * jobSystemDefaultWeight.getJsdw_c_e_history();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_e_language() * jobSystemDefaultWeight.getJsdw_e_language();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_position() * jobSystemDefaultWeight.getJsdw_position();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_location() * jobSystemDefaultWeight.getJsdw_location();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_insurance() * jobSystemDefaultWeight.getJsdw_insurance();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_check_up() * jobSystemDefaultWeight.getJsdw_check_up();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_a_bonus() * jobSystemDefaultWeight.getJsdw_a_bonus();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_p_leave() * jobSystemDefaultWeight.getJsdw_p_leave();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_o_allowance() * jobSystemDefaultWeight.getJsdw_o_allowance();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_stock() * jobSystemDefaultWeight.getJsdw_stock();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_t_subside() * jobSystemDefaultWeight.getJsdw_t_subside();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_h_subside() * jobSystemDefaultWeight.getJsdw_h_subside();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_b_trip() * jobSystemDefaultWeight.getJsdw_b_trip();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_s_range() * jobSystemDefaultWeight.getJsdw_s_range();
            jmr_v_match += valueTempt;

            resumeJMRNew.setJmr_v_match(jmr_v_match);
            double matchBase = 17 * 2;
            double success = transferComponent.getSuccessByDegree(resumeJMR.getJmr_v_success());
            double jmr_v_average = valueComponent.getAverage(success, jmr_v_match/matchBase);

            System.out.println("jmr_v_average pro :" + resumeJMR.getJmr_v_average());
            resumeJMRNew.setJmr_v_average(jmr_v_average);
            System.out.println("jmr_v_average now :" + jmr_v_average);
            resumeJMRsNew.add(resumeJMRNew);
        }
        return resumeJMRsNew;
    }

    //及时执行，根据用户的自定义权重进行匹配
    public List<ResumeJMRPersonalizedVo> getResumeJMR_Match(int rid, PersonalizedJMRVo personalizedJMRVo){
        List<ResumeJMR> resumeJMRs = studentService.getResumeJMRsByResume(rid);
        if (resumeJMRs.size() == 0){
            studentService.getResumeJMR_Match(rid);
            resumeJMRs = studentService.getResumeJMRsByResume(rid);
        }
        List<ResumeJMRPersonalizedVo> resumeJMRPersonalizedVos = new ArrayList<>();
        for (ResumeJMR resumeJMR : resumeJMRs){
            ResumeJMRPersonalizedVo resumeJMRPersonalizedVo = new ResumeJMRPersonalizedVo();

            double valueTempt;
            double jmr_v_match = 0;

            ResumeJMRBase resumeJMRBase = resumeJMR.getJmr_base();


            resumeJMRPersonalizedVo.setJmr_id(resumeJMR.getJmr_id());
            resumeJMRPersonalizedVo.setJmr_base(resumeJMRBase);
            resumeJMRPersonalizedVo.setJmr_resume(resumeJMR.getJmr_resume());
            resumeJMRPersonalizedVo.setJmr_job(resumeJMR.getJmr_job());
            resumeJMRPersonalizedVo.setJmr_v_success(resumeJMR.getJmr_v_success());
            resumeJMRPersonalizedVo.setJmr_v_popularity(resumeJMR.getJmr_v_popularity());

            valueTempt = resumeJMRBase.getJmr_b_c_scale() * personalizedJMRVo.getJmr_b_c_scale();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_c_f_stage() * personalizedJMRVo.getJmr_b_c_f_stage();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_c_level() * personalizedJMRVo.getJmr_b_c_level();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_e_history() * personalizedJMRVo.getJmr_e_history();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_e_language() * personalizedJMRVo.getJmr_b_e_language();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_j_count() * personalizedJMRVo.getJmr_b_j_count();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_position() * personalizedJMRVo.getJmr_b_position();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_location() * personalizedJMRVo.getJmr_b_location();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_insurance() * personalizedJMRVo.getJmr_b_insurance();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_check_up() * personalizedJMRVo.getJmr_b_check_up();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_a_bonus() * personalizedJMRVo.getJmr_b_a_bonus();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_p_leave() * personalizedJMRVo.getJmr_b_p_leave();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_o_allowance() * personalizedJMRVo.getJmr_b_o_allowance();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_stock() * personalizedJMRVo.getJmr_b_stock();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_t_subside() * personalizedJMRVo.getJmr_b_t_subside();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_h_subside() * personalizedJMRVo.getJmr_b_h_subside();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_b_trip() * personalizedJMRVo.getJmr_b_b_trip();
            jmr_v_match += valueTempt;

            valueTempt = resumeJMRBase.getJmr_b_s_range() * personalizedJMRVo.getJmr_b_s_range();
            jmr_v_match += valueTempt;

            resumeJMRPersonalizedVo.setJmr_v_match(jmr_v_match);
            double matchBase = 18 * 2;
            double success = transferComponent.getSuccessByDegree(resumeJMR.getJmr_v_success());
            double jmr_v_average = valueComponent.getAverage(success, jmr_v_match/matchBase);

            System.out.println("jmr_v_average pro :" + resumeJMR.getJmr_v_average());
            resumeJMRPersonalizedVo.setJmr_v_average(jmr_v_average);
            System.out.println("jmr_v_average now :" + jmr_v_average);
            resumeJMRPersonalizedVos.add(resumeJMRPersonalizedVo);
        }

        return resumeJMRPersonalizedVos;
    }

    //执行一个简历的匹配
    public void getOneResumeJMR_Match(StudentResume studentResume){
        Resume resume = studentResume.getStudentResumePK().getSr_resume();
        Student student = studentResume.getStudentResumePK().getSr_student();
        List<Job> qualifiedJobs = companyService.getResumeJMRQualifiedJobsByStudentResume(studentResume);
        List<CompanyJob> qualifiedCompanyJobs = companyService.getCompanyJobsByJobs(qualifiedJobs);
        if (qualifiedCompanyJobs.size() == 0) System.out.println(resume.getR_id() + ":该简历没有符合要求的岗位");

        //-----------(1)、计算匹配值 jmr_v_match-------------
        qualifiedCompanyJobs.forEach(companyJob -> {
            Company company = companyJob.getCompanyJobPk().getCj_company();
            Job job = companyJob.getCompanyJobPk().getCj_job();

            ResumeJMR resumeJMR = new ResumeJMR();
            ResumeJMRBase resumeJMRBase = new ResumeJMRBase();
            double jmr_v_match = 0;

            resumeJMR.setJmr_resume(resume);
            resumeJMR.setJmr_job(job);
            resumeJMR.setJmr_v_success(EnumWarehouse.SUCCESS_DEGREE.低);
            resumeJMR.setJmr_v_average(0);
            resumeJMR.setJmr_v_popularity(0);

            int valueTempt = 0;
            valueTempt = valueComponent.resumeJmrBaseCScale(company.getC_scale());
            resumeJMRBase.setJmr_b_c_scale(valueTempt);
            jmr_v_match += valueTempt;

            valueTempt = valueComponent.resumeJmrBaseCFStage(company.getC_f_stage());
            resumeJMRBase.setJmr_b_c_f_stage(valueTempt);
            jmr_v_match += valueTempt;

            valueTempt = valueComponent.resumeJmrBaseCLevel(job.getJ_c_level(),student.getS_c_level());
            resumeJMRBase.setJmr_b_c_level(valueTempt);
            jmr_v_match += valueTempt;

            valueTempt = valueComponent.resumeJmrBaseEHistory(job.getJ_e_history(),student.getS_e_history());
            resumeJMRBase.setJmr_e_history(valueTempt);
            jmr_v_match += valueTempt;

            valueTempt = valueComponent.resumeJmrBaseELanguage(job.getJ_e_language(),student.getS_e_language());
            resumeJMRBase.setJmr_b_e_language(valueTempt);
            jmr_v_match += valueTempt;

            //jmr_b_postion;
            List<Position> studentPositions = positionService.getPositionsByStudent(student.getS_id());
            valueTempt = valueComponent.resumeJmrBasePosition(job.getJ_position(),studentPositions);
            resumeJMRBase.setJmr_b_location(valueTempt);
            jmr_v_match += valueTempt;

            //jmr_b_location;
            valueTempt = valueComponent.resumeJmrBaseLocation(job.getJ_location(),resume.getR_e_location());
            resumeJMRBase.setJmr_b_location(valueTempt);
            jmr_v_match += valueTempt;

            valueTempt = valueComponent.resumeJmrBaseWelfare(job.getJ_insurance(),resume.getR_if_insurance());
            resumeJMRBase.setJmr_b_insurance(valueTempt);
            jmr_v_match += valueTempt;

            valueTempt = valueComponent.resumeJmrBaseWelfare(job.getJ_check_up(),resume.getR_if_check_up());
            resumeJMRBase.setJmr_b_check_up(valueTempt);
            jmr_v_match += valueTempt;

            valueTempt = valueComponent.resumeJmrBaseWelfare(job.getJ_a_bonus(),resume.getR_if_a_bonus());
            resumeJMRBase.setJmr_b_a_bonus(valueTempt);
            jmr_v_match += valueTempt;

            valueTempt = valueComponent.resumeJmrBaseWelfare(job.getJ_p_leave(),resume.getR_if_p_leave());
            resumeJMRBase.setJmr_b_p_leave(valueTempt);
            jmr_v_match += valueTempt;

            valueTempt = valueComponent.resumeJmrBaseWelfare(job.getJ_o_allowance(),resume.getR_if_o_allowance());
            resumeJMRBase.setJmr_b_o_allowance(valueTempt);
            jmr_v_match += valueTempt;

            valueTempt = valueComponent.resumeJmrBaseWelfare(job.getJ_stock(),resume.getR_if_stock());
            resumeJMRBase.setJmr_b_stock(valueTempt);
            jmr_v_match += valueTempt;

            valueTempt = valueComponent.resumeJmrBaseWelfare(job.getJ_t_subside(),resume.getR_if_t_subside());
            resumeJMRBase.setJmr_b_t_subside(valueTempt);
            jmr_v_match += valueTempt;

            valueTempt = valueComponent.resumeJmrBaseWelfare(job.getJ_h_subside(),resume.getR_if_h_subside());
            resumeJMRBase.setJmr_b_h_subside(valueTempt);
            jmr_v_match += valueTempt;

            valueTempt = valueComponent.resumeJmrBaseBTrip(job.getJ_b_trip(),resume.getR_if_b_trip());
            resumeJMRBase.setJmr_b_b_trip(valueTempt);
            jmr_v_match += valueTempt;

            valueTempt = valueComponent.resumeJmrBaseSRange(job.getJ_s_range(),student.getS_s_range());
            resumeJMRBase.setJmr_b_c_scale(valueTempt);
            jmr_v_match += valueTempt;

            resumeJMR.setJmr_v_match(jmr_v_match);
            studentService.addResumeJMRBase(resumeJMRBase);
            resumeJMR.setJmr_base(resumeJMRBase);
            studentService.addResumeJMR(resumeJMR);
            System.out.println("jmr_v_match:" + jmr_v_match);
        });

        //-----------(2)、jmr_v_success jmr_v_average jmr_v_popularity-------------
        //获取当前resume匹配到的公司岗位
        List<ResumeJMR> resumeJMRs = studentService.getResumeJMRsByResume(resume.getR_id());
        //遍历当前resume匹配到的公司岗位
        resumeJMRs.forEach(resumeJMR -> {
            //-----------(2)、计算成功率 jmr_v_success-------------
            System.out.println("-----------(2)、计算成功率 jmr_v_success-------------");
            //获取当前公司岗位在 当前resume匹配到的岗位中 的相似简历 finished by * 欧式距离 *
            List<Job> similarJobs = companyService.getSimilarJobsByResumeJMR(resumeJMR,resumeJMRs);
            //获取这些相似岗位曾经选中过的简历 finished by JR
            List<Resume> qualifiedJRResumes = studentService.getResumesByJobs_JobToResume(similarJobs);
            //计算当前简历在 曾经选中过的简历中 的等级 * classified to three classes, high, medium and low, by kMeans *
            EnumWarehouse.SUCCESS_DEGREE jmr_v_success = studentService.getResumeJMRSuccessDegree(qualifiedJRResumes,resume);
            resumeJMR.setJmr_v_success(jmr_v_success);
            System.out.println("jmr_v_success:" + jmr_v_success);
            //-----------(3)、计算平均值 jmr_v_average-------------
            System.out.println("-----------(3)、计算平均值 jmr_v_average-------------");
            double matchBase = 18 * 2;
            //根据当前成功率等级获得成功率值
            double success = transferComponent.getSuccessByDegree(jmr_v_success);
            double jmr_v_average = valueComponent.getAverage(success, resumeJMR.getJmr_v_match()/matchBase);
            resumeJMR.setJmr_v_average(jmr_v_average);
            System.out.println("jmr_v_average:" + jmr_v_average);
            //-----------(4)、计算热度 jmr_v_popularity-------------
            System.out.println("-----------(4)、计算热度 jmr_v_popularity-------------");
            Job job = resumeJMR.getJmr_job();
            int jmr_v_popularity = getJobResumeCountByJob(job.getJ_id(),true);
            resumeJMR.setJmr_v_popularity(jmr_v_popularity);
            System.out.println("jmr_v_popularity:" + jmr_v_popularity);
        });
    }

    public EnumWarehouse.SUCCESS_DEGREE getResumeJMRSuccessDegree(List<Resume> resumes, Resume resume){
        int k = 3;
        int loopCount = 50;
        List<double[]> resumesList = transferComponent.transferResumesToArray(resume, resumes);
        Map<String,List<double[]>> listMap = kMeansComponent.kMeansFunction(resumesList, k, loopCount);
        return kMeansComponent.getDegreeByKMeans(listMap, k,0);
    }


    /*---------学生匹配的企业信息的各项数值（JmrBase）---------
    -------检索：管理员，学生，就业专员
    -------更新：服务器
    -------创建：服务器
    -------删除：服务器
    --------------------------------------------------*/
    public ResumeJMRBase addResumeJMRBase(ResumeJMRBase resumeJMRBase){
        resumeJMRBaseRepository.save(resumeJMRBase);
        return resumeJMRBase;
    }

    /*--------学生意向行业（StudentIndustry）----------
    --------------------------------------------------*/
    public StudentIndustry addStudentIndustry(StudentIndustry studentIndustry){
        studentIndustryRepository.save(studentIndustry);
        return studentIndustry;
    }

    /*--------学生意向岗位（StudentPosition）----------
    --------------------------------------------------*/
    public StudentPosition addStudentPosition(StudentPosition studentPosition){
        studentPositionRepository.save(studentPosition);
        return studentPosition;
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

    public List<JobResume> getJobResumesByResume_ResumeToJob(int rid, boolean resumeToJob){
        return jobResumeRepository.getJobResumesByResume_ResumeToJob(rid,resumeToJob).orElse(new ArrayList<>());
    }
    public List<JobResume> getJobResumesByJob_ResumeToJob(int jid, boolean resumeToJob){
        return jobResumeRepository.getJobResumesByJob_ResumeToJob(jid,resumeToJob).orElse(new ArrayList<>());
    }
    public List<JobResume> getJobResumesByJob_JobToResume(int jid,boolean jobToResume){
        return jobResumeRepository.getJobResumesByJob_JobToResume(jid,jobToResume).orElse(new ArrayList<>());
    }
    public List<JobResume> getJobResumesByStudent_ResumeToJob(int sid, boolean resumeToJob){
        List<JobResume> jobResumes = new ArrayList<>();
        List<Resume> resumes = studentService.getResumesByStudentId(sid);
        for (Resume resume : resumes) {
            jobResumes.addAll(studentService.getJobResumesByResume_ResumeToJob(resume.getR_id(), true));
        }
        return jobResumes;
    }
    public List<JobResume> getJobResumesByStudents_ResumeToJob(List<Student> students, boolean resumeToJob){
        List<JobResume> jobResumes = new ArrayList<>();
        for (Student student : students) {
            jobResumes.addAll(studentService.getJobResumesByStudent_ResumeToJob(student.getS_id(), true));
        }
        return jobResumes;
    }
    public List<JobResume> getJobResumesByStudents_ResumeToJob_JobToResume(List<Student> students, boolean resumeToJob, boolean jobToResume){
        List<JobResume> jobResumes = studentService.getJobResumesByStudents_ResumeToJob(students, resumeToJob);
        return jobResumes.stream()
                .filter(jobResume -> jobResume.isJobToResume())
                .collect(Collectors.toList());
    }
    public List<JobResume> getJobResumesByJobs_JobToResume(List<Job> jobs){
        List<JobResume> jobResumes = new ArrayList<>();
        jobs.forEach(j -> {
            List<JobResume> jrs = studentService.getJobResumesByJob_JobToResume(j.getJ_id(),true);
            if (jrs.size() != 0) jobResumes.addAll(jrs);
        });
        return jobResumes;
    }
    public List<Resume> getResumesByJobs_JobToResume(List<Job> jobs){
        List<Resume> resumes = new ArrayList<>();
        List<JobResume> jobResumes = studentService.getJobResumesByJobs_JobToResume(jobs);
        if (jobResumes.size() != 0){
            for (JobResume jobResume : jobResumes) {
                resumes.add(jobResume.getJobResumePK().getJr_resume());
            }
        }
        return resumes;
    }
    public int getJobResumeCountByJob(int jid, boolean resumeToJob){
        return studentService.getJobResumesByJob_ResumeToJob(jid,resumeToJob).size();
    }

    /*---------学生收藏的岗位信息（StudentFavoredJob）---------
    -------检索：管理员，学生，就业专员
    -------更新：服务器
    -------创建：服务器
    -------删除：服务器
    --------------------------------------------------*/
    public StudentFavoredJob addStudentFavoredJob(StudentFavoredJob studentFavoredJob){
        return studentFavoredJobRepository.save(studentFavoredJob);
    }
    public List<StudentFavoredJob> getAllStudentFavoredJobs(){
        return studentFavoredJobRepository.findAll();
    }

    public List<StudentFavoredJob> getStudentFavoredJobsByStudent(int sid){
        return studentFavoredJobRepository.getStudentFavoredJobsByStudent(sid).orElse(new ArrayList<>());
    }
    public List<StudentFavoredJob> getStudentFavoredJobsByStudents(List<Student> students){
        List<StudentFavoredJob> studentFavoredJobs = new ArrayList<>();
        if (students.size() == 0) return studentFavoredJobs;
        for (Student student : students) {
            studentFavoredJobs.addAll(studentService.getStudentFavoredJobsByStudent(student.getS_id()));
        }
        return studentFavoredJobs;
    }
    public List<Resume> getAllFavoredResumes(){
        List<Resume> resumes = new ArrayList<>();
        List<CompanyFavoredResume> companyFavoredResumes = companyService.getAllCompanyFavoredResumes();
        if (companyFavoredResumes.size() != 0){
            companyFavoredResumes.forEach(companyFavoredResume -> {
                resumes.add(companyFavoredResume.getCompanyFavoredResumePK().getCfr_resume());
            });
        }
        return resumes;
    }

    /*----------（ResumeSystemDefaultWeightRepository）-----------
    -------提供给学生端使用，衡量resume各项信息重要程度
    --------------------------------------------------*/
    public ResumeSystemDefaultWeight addResumeSystemDefaultWeight(ResumeSystemDefaultWeight resumeSystemDefaultWeight){
        return resumeSystemDefaultWeightRepository.save(resumeSystemDefaultWeight);
    }
    public ResumeSystemDefaultWeight addResumeSystemDefaultWeight(List<Double> weights){
        ResumeSystemDefaultWeight resumeSystemDefaultWeight = new ResumeSystemDefaultWeight();
        resumeSystemDefaultWeight.setRsdw_a_bonus(weights.get(0));
        resumeSystemDefaultWeight.setRsdw_p_leave(weights.get(1));
        resumeSystemDefaultWeight.setRsdw_o_allowance(weights.get(2));
        resumeSystemDefaultWeight.setRsdw_b_stock(weights.get(3));
        resumeSystemDefaultWeight.setRsdw_t_subside(weights.get(4));
        resumeSystemDefaultWeight.setRsdw_h_subside(weights.get(5));
        resumeSystemDefaultWeight.setRsdw_b_trip(weights.get(6));
        resumeSystemDefaultWeight.setRsdw_c_level(weights.get(7));
        resumeSystemDefaultWeight.setRsdw_e_history(weights.get(8));
        resumeSystemDefaultWeight.setRsdw_e_language(weights.get(9));
        resumeSystemDefaultWeight.setRsdw_ranking(weights.get(10));
        resumeSystemDefaultWeight.setRsdw_check_up(weights.get(11));
        resumeSystemDefaultWeight.setRsdw_p_count(weights.get(12));
        resumeSystemDefaultWeight.setRsdw_h_count(weights.get(13));
        resumeSystemDefaultWeight.setRsdw_s_count(weights.get(14));
        resumeSystemDefaultWeight.setRsdw_c_count(weights.get(15));
        resumeSystemDefaultWeight.setRsdw_position(weights.get(16));
        resumeSystemDefaultWeight.setRsdw_location(weights.get(17));
        resumeSystemDefaultWeight.setRsdw_insurance(weights.get(18));
        studentService.addResumeSystemDefaultWeight(resumeSystemDefaultWeight);
        return resumeSystemDefaultWeight;
    }

    public void deleteAllResumeSystemDefaultWeights(){
        resumeSystemDefaultWeightRepository.deleteAll();
    }

    public ResumeSystemDefaultWeight getResumeSystemDefaultWeight(){
        ResumeSystemDefaultWeight resumeSystemDefaultWeight = new ResumeSystemDefaultWeight();
        List<ResumeSystemDefaultWeight> resumeSystemDefaultWeights = resumeSystemDefaultWeightRepository.findAll();
        if (resumeSystemDefaultWeights.size() != 0){
            resumeSystemDefaultWeight =resumeSystemDefaultWeights.get(0);
        }
        return resumeSystemDefaultWeight;
    }
}

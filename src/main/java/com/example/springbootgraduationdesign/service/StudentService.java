package com.example.springbootgraduationdesign.service;

import com.example.springbootgraduationdesign.component.EuclideanDistanceComponent;
import com.example.springbootgraduationdesign.component.TransferComponent;
import com.example.springbootgraduationdesign.component.ValueComponent;
import com.example.springbootgraduationdesign.component.vo.StudentVo;
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
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private  StudentIndustryRepository studentIndustryRepository;
    @Autowired
    private StudentPositionRepository studentPositionRepository;
    @Autowired
    private ResumeRepository resumeRepository;
    @Autowired
    private StudentResumeRepository studentResumeRepository;
    @Autowired
    private StudentJMRRepository studentJMRRepository;
    @Autowired
    private StudemtJMRBaseRepository studemtJMRBaseRepository;


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
//        studentService.deleteStudentPositionsByStudent(studentOld.getS_id());
        studentIndustryRepository.deleteStudentIndustriesByStudent(student.getS_id());
//        studentService.deleteStudentIndustriesByStudent(studentOld.getS_id());
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
//            studentService.addStudentPositions(studentPosition);
            studentPositionRepository.save(studentPosition);
        }
        for (Industry i : industries){
            StudentIndustry studentIndustry = new StudentIndustry();
            StudentIndustryPK studentIndustryPK = new StudentIndustryPK();
            studentIndustryPK.setSi_industry(i);
            studentIndustryPK.setSi_student(student);
            studentIndustry.setStudentIndustryPK(studentIndustryPK);
//            studentService.addStudentIndustries(studentIndustry);
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
    public List<Student> getStudentByCollege(String college){
        return studentRepository.getStudentByCollege(college).orElse(new ArrayList<>());
    }
    public Student getStudentByTelephone(String telephone){
        return studentRepository.getStudentByS_telephone(telephone).orElse(null);
    }
    public List<Student> getStudentByCLevelAndHistory(EnumWarehouse.C_LEVEL level, EnumWarehouse.E_HISTORY history){
        return studentRepository.getStudentByCLevelAndHistory(level, history).orElse(new ArrayList<>());
    }
    public List<Student> getJobSMRQualifiedStudentsByCompanyJob(CompanyJob companyJob){
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
            studentService.deleteStudentJMRsByResume(rid);
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
        double[] jobSMRArray = transferComponent.transferJobJMRBaseToArray(jobSMR.getSmr_base());
        jobSMRs.forEach(js -> {
            double distance = euclideanDistanceComponent.sim_distance(jobSMRArray,
                    transferComponent.transferJobJMRBaseToArray(js.getSmr_base()));
            if (distance < distanceStandard) resumes.add(js.getSmr_resume());

        });
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
        studentService.deleteStudentJMRsByResume(rid);
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
    public StudentJMR addStudentJMR(StudentJMR job_match_result){
        studentJMRRepository.save(job_match_result);
        return job_match_result;
    }

    public void deleteStudentJMRsByJob(int jid){
        studentJMRRepository.deleteStudentJMRsByJob(jid);
    }
    public void deleteStudentJMRsByResume(int rid){
        studentJMRRepository.deleteStudentJMRsByResume(rid);
    }

    public List<StudentJMR> getAllJobMatchResults(){
        return studentJMRRepository.findAll();
    }
    public List<StudentJMR> getStudentJMRsByStudent(int sid){
        return studentJMRRepository.getStudentJMRsByStudent(sid).orElse(new ArrayList<>());
    }


    /*---------学生匹配的企业信息的各项数值（JmrBase）---------
    -------检索：管理员，学生，就业专员
    -------更新：服务器
    -------创建：服务器
    -------删除：服务器
    --------------------------------------------------*/
    public StudentJMRBase addJmrBase(StudentJMRBase jmr_base){
        studemtJMRBaseRepository.save(jmr_base);
        return jmr_base;
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
}

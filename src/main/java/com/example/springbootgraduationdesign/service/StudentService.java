package com.example.springbootgraduationdesign.service;

import com.example.springbootgraduationdesign.component.EnumComponent;
import com.example.springbootgraduationdesign.component.vo.StudentVo;
import com.example.springbootgraduationdesign.entity.*;
import com.example.springbootgraduationdesign.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private EnumComponent enumComponent;
    @Autowired
    private PasswordEncoder encoder;


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
        studentService.updateStudent(student);
        studentService.deleteStudentPositionsByStudent(student.getS_id());
        studentService.deleteStudentIndustriesByStudent(student.getS_id());
        List<Position> positions = studentVo.getPositions();
        List<Industry> industries = studentVo.getIndustries();
        for (Position po : positions){
            StudentPosition studentPosition = new StudentPosition();
            StudentPositionPK studentPositionPK = new StudentPositionPK();
            studentPositionPK.setSp_position(po);
            studentPositionPK.setSp_student(student);
            studentPosition.setStudentPositionPK(studentPositionPK);
            studentService.addStudentPositions(studentPosition);
        }
        for (Industry i : industries){
            StudentIndustry studentIndustry = new StudentIndustry();
            StudentIndustryPK studentIndustryPK = new StudentIndustryPK();
            studentIndustryPK.setSi_industry(i);
            studentIndustryPK.setSi_student(student);
            studentIndustry.setStudentIndustryPK(studentIndustryPK);
            studentService.addStudentIndustries(studentIndustry);
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
//    public List<StudentResumeVo> getStudentResumeVoByStudent(int sid){
//        List<Resume> resumes = studentService.getResumesByStudentId(sid);
//        List<Student_Resume> student_resumes = studentService.getStudentResumes(sid);
//        List<StudentResumeVo> studentResumeVos = new ArrayList<>();
//        resumes.forEach(resume -> {
//            StudentResumeVo studentResumeVo = new StudentResumeVo();
//            studentResumeVo.setResume(resume);
//            for (Student_Resume student_resume : student_resumes){
//                if (student_resume.getStudent_resume_pk().getResume().getR_id() ==
//                        resume.getR_id()){
//                    studentResumeVo.setPosted(true);
//                    break;
//                }else {
//                    studentResumeVo.setPosted(false);
//                }
//            }
//            studentResumeVos.add(studentResumeVo);
//        });
//        return studentResumeVos;
//    }



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
}

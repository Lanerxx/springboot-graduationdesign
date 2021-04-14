package com.example.springbootgraduationdesign.controller;

import com.example.springbootgraduationdesign.component.CheckIsNullComponent;
import com.example.springbootgraduationdesign.component.RequestComponent;
import com.example.springbootgraduationdesign.component.vo.PasswordVo;
import com.example.springbootgraduationdesign.component.vo.StudentVo;
import com.example.springbootgraduationdesign.entity.*;
import com.example.springbootgraduationdesign.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/student/")
@Slf4j
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private ProfessionService professionService;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private CompanyService companyService;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CheckIsNullComponent checkIsNullComponent;
    @Autowired
    private RequestComponent requestComponent;

    @GetMapping("index")
    public Map getIndex(){
        Student student = studentService.getStudent(requestComponent.getUid());
        List<String> positionsName = positionService.listPositionsName();
        Set<String> professionsMClass = professionService.getProfessionsMClass();
        return Map.of(
                "student",student,
                "positions",positionsName,
                "professionsMClass",professionsMClass
        );
    }

    @PatchMapping("information")
    public Map updateStudentInformation(@RequestBody StudentVo studentVo){
        Student student = studentVo.getStudent();
        if (studentService.getStudent(student.getS_id()) == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "该学生不存在！");
        }
        Profession profession = professionService.getProfession(student.getS_profession().getPr_id());
        if (profession == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您填写的专业错误！");
        }
        student.setS_profession(profession);
        if (student.getS_if_work().equals(EnumWarehouse.IF_WORK.YES)){
            if (student.getS_w_province() == null || student.getS_company() == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "您还未填写已就业的城市或企业！");
            }
        }else {
            student.setS_w_province("NONE");
            student.setS_company("NONE");
        }
        List<Position> positions = studentVo.getPositions();
        List<Industry> industries = studentVo.getIndustries();
        if (positions == null || industries == null ||
                checkIsNullComponent.objCheckIsNull(student)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息！");
        }
        for (int i = 0; i < positions.size(); i++){
            Position position = positionService.getPosition(positions.get(i).getPo_id());
            if (position == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "您填写的期望岗位错误！");
            }else {
                positions.set(i, position);
            }
        }
        for (int i = 0; i < industries.size(); i++){
            Industry industry = industryService.getIndustry(industries.get(i).getI_id());
            if (industry == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "您填写的期望行业错误！");
            }else {
                industries.set(i, industry);
            }
        }
        studentService.updateStudent(studentVo);
        return Map.of(
                "student",student
        );
    }

    @PatchMapping("password")
    public Map updatePassword(@RequestBody PasswordVo passwordVo){
        Student s = studentService.getStudent(requestComponent.getUid());
        if (!encoder.matches(passwordVo.getOldPassword(), s.getS_password())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您输入旧密码错误");
        }else {
            s.setS_password(encoder.encode(passwordVo.getNewPassword()));
        }
        studentService.updateStudent(s);
        return Map.of(
                "student",s
        );
    }


    @PostMapping("resume")
    public Map addResume(@Valid @RequestBody Resume resume){
        int sid = requestComponent.getUid();
        Student student = studentService.getStudent(sid);
        resume.setR_student(student);
        if (checkIsNullComponent.objCheckIsNull(resume)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息，请完善信息后再提交");
        }
        studentService.addResume(resume);
        List<Resume> resumes = studentService.getResumesByStudentId(sid);
        return Map.of(
                "resumes",resumes
        );
    }

    @DeleteMapping("resume/{rid}")
    public Map deleteResume(@PathVariable int rid){
        Resume resume = studentService.getResume(rid);
        if (resume == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想删除的岗位不存在");
        }
        studentService.deleteResumeAndRelated(resume);

        List<Resume> resumes = studentService.getResumesByStudentId(requestComponent.getUid());
        return Map.of(
                "resumes",resumes
        );
    }

    @PatchMapping("resume")
    public Map updateResume(@RequestBody Resume resume){
        int sid = requestComponent.getUid();
        Student s = studentService.getStudent(sid);
        resume.setR_student(s);
        if (checkIsNullComponent.objCheckIsNull(resume)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息，请完善信息后再提交");
        }
        if (studentService.getResume(resume.getR_id()) == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想修改的简历不存在");
        }
        studentService.updateResume(resume);
        List<Resume> resumes = studentService.getResumesByStudentId(sid);
        return Map.of(
                "resumes",resumes
        );
    }

    @GetMapping("resumes")
    public Map getResumes(){
        List<Resume> resumes = studentService.getResumesByStudentId(requestComponent.getUid());
        return Map.of(
                "resumes",resumes
        );
    }


    @PostMapping("studentResume")
    public Map addStudentResume(@Valid @RequestBody int rid){
        Resume resume = studentService.getResume(rid);
        if (resume == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想发布的简历不存在");
        }
        int sid = requestComponent.getUid();
        Student student = studentService.getStudent(sid);
        studentService.addStudentResume(student,resume);
        List<Resume> resumes = studentService.getResumesByStudentId(sid);
        return Map.of(
                "resumes",resumes
        );
    }

    @DeleteMapping("studentResume/{rid}")
    public Map deleteStudentResume(@PathVariable int rid){
        StudentResume studentResume = studentService.getStudentResumeByResume(rid);
        if (studentResume ==null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想回收的简历不存在");
        }
        studentService.deleteStudentResumeAndRelatedByResume(rid);
        int sid = requestComponent.getUid();
        List<Resume> resumes = studentService.getResumesByStudentId(sid);
        return Map.of(
                "resumes",resumes
        );

    }


    @PostMapping("jmr")
    public Map getJmr(@RequestBody List<Map<String,Integer>> focus){
        return Map.of(
                "studentJMRs", "studentJMRs"
        );
    }

    @GetMapping("jmr")
    public Map getJmr(){
        List<StudentJMR> studentJMRs = studentService.getStudentJMRsByStudent(requestComponent.getUid());
        if (studentJMRs.size() == 0){
            //calculate...
            studentJMRs = studentService.getStudentJMRsByStudent(requestComponent.getUid());
        }
        return Map.of(
                "studentJMRs", studentJMRs
        );
    }

}

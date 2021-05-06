package com.example.springbootgraduationdesign.controller;

import com.example.springbootgraduationdesign.component.CheckIsNullComponent;
import com.example.springbootgraduationdesign.component.RequestComponent;
import com.example.springbootgraduationdesign.component.vo.PasswordVo;
import com.example.springbootgraduationdesign.component.vo.StudentVo;
import com.example.springbootgraduationdesign.entity.*;
import com.example.springbootgraduationdesign.service.*;
import javafx.geometry.Pos;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
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
        int sid = requestComponent.getUid();
        Student student = studentService.getStudent(sid);
        List<String> positionsName = positionService.listPositionsName();
        List<String> industriesName = industryService.listIndustriesName();
        List<String> studentPositionsName = positionService.listPositionNameByStudent(sid);
        List<String> studentIndustryName = industryService.listIndustryNameByStudent(sid);
        List<String> professionsSClass = professionService.getProfessionsSName();
        return Map.of(
                "student",student,
                "positionsName",positionsName,
                "studentPositionsName",studentPositionsName,
                "studentIndustryName",studentIndustryName,
                "industriesName",industriesName,
                "professionsSClass",professionsSClass
        );
    }

    @PatchMapping("information")
    public Map updateStudentInformation(@RequestBody StudentVo studentVo){
        int sid = requestComponent.getUid();
        Student student = studentVo.getStudent();
        student.setS_id(sid);
        Student studentOld = studentService.getStudent(sid);
        if (studentOld == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "该学生不存在！");
        }
        Profession profession = professionService.getProfessionBySClass(student.getS_profession().getPr_s_class());
        if (profession == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您填写的专业错误！");
        }
        student.setS_profession(profession);
        if (student.getS_if_work().equals(EnumWarehouse.IF_IS_OR_NOT.是)){
            if (student.getS_w_province() == null || student.getS_company() == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "您还未填写已就业的城市或企业！");
            }
        }else {
            student.setS_w_province("无");
            student.setS_company("无");
        }
        List<Position> positions = positionService.getPositionsByPositionsName(studentVo.getPositionsName());
        if (positions.size() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您填写的期望岗位错误！");
        }
        List<Industry> industries = industryService.getIndustriesByIndustriesName(studentVo.getIndustriesName());
        if (industries.size() == 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您填写的期望行业错误！");
        }

        student.setS_telephone(studentOld.getS_telephone());
        student.setS_password(studentOld.getS_password());
        LocalDateTime localDateTime = LocalDateTime.now();
        student.setInsertTime(localDateTime);
        student.setUpdateTime(localDateTime);
        if (checkIsNullComponent.objCheckIsNull(student)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息！");
        }
        studentVo.setPositions(positions);
        studentVo.setIndustries(industries);
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
    public Map addResume(@RequestBody Resume resume){
        int sid = requestComponent.getUid();
        Student student = studentService.getStudent(sid);
        resume.setR_student(student);
        LocalDateTime localDateTime = LocalDateTime.now();
        resume.setInsertTime(localDateTime);
        resume.setUpdateTime(localDateTime);
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
        LocalDateTime localDateTime = LocalDateTime.now();
        resume.setInsertTime(localDateTime);
        resume.setUpdateTime(localDateTime);
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
    public Map addStudentResume(@RequestBody Resume resume){
        int rid = resume.getR_id();
        Resume r = studentService.getResume(rid);
        if (r == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想发布的简历不存在");
        }
        int sid = requestComponent.getUid();
        Student student = studentService.getStudent(sid);
        studentService.addStudentResume(student,r);
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


//    @PostMapping("jmr")
//    public Map getJmr(@RequestBody List<Map<String,Integer>> focus){
//        return Map.of(
//                "studentJMRs", "studentJMRs"
//        );
//    }

//    @GetMapping("jmr")
//    public Map getJmr(){
//        List<ResumeJMR> resumeJMRS = studentService.getStudentJMRsByStudent(requestComponent.getUid());
//        if (resumeJMRS.size() == 0){
//            //calculate...
//            resumeJMRS = studentService.getStudentJMRsByStudent(requestComponent.getUid());
//        }
//        return Map.of(
//                "studentJMRs", resumeJMRS
//        );
//    }

}

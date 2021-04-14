package com.example.springbootgraduationdesign.controller;

import com.example.springbootgraduationdesign.component.CheckIsNullComponent;
import com.example.springbootgraduationdesign.component.RequestComponent;
import com.example.springbootgraduationdesign.entity.*;
import com.example.springbootgraduationdesign.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/jobDirector/")
@Slf4j
public class JobDirectorController {
    @Autowired
    private JobDirectorService jobDirectorService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private PositionService positionService;
    @Autowired
    private ProfessionService professionService;
    @Autowired
    private StudentService studentService;

    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CheckIsNullComponent checkIsNullComponent;
    @Autowired
    private RequestComponent requestComponent;

    @GetMapping("index")
    public Map getIndex(){
        JobDrector jobDirector = jobDirectorService.getJobDirector(requestComponent.getUid());
        return Map.of(
                "jobDirector",jobDirector
        );
    }

    @GetMapping("position")
    public Map getPosition(){
        List<String> positionsName = positionService.listPositionsName();
        return Map.of(
                "positions",positionsName
        );
    }

    @GetMapping("professionMClass")
    public Map getProfessionMClass(){
        Set<String> professionsMClass = professionService.getProfessionsMClass();
        return Map.of(
                "professionsMClass",professionsMClass
        );
    }

    @PostMapping("professionsSClass")
    public Map getIndex(@Valid @RequestBody Profession profession){
        Set<String> professionsSClass = professionService.getProfessionsSClassByMClass(profession.getPr_m_class());
        return Map.of(
                "professionsSClass",professionsSClass
        );
    }

    @GetMapping("students")
    public Map getStudents(){
        List<Student> students = studentService.getAllStudents();
        return Map.of(
                "students",students
        );
    }

    @GetMapping("student/{sid}")
    public Map getStudent(@PathVariable int sid){
        Student student = studentService.getStudent(sid);
        return Map.of(
                "student",student
        );
    }

//    @GetMapping("studentResume/{sid}")
//    public Map getStudentResumes(@PathVariable int sid){
//        List<StudentResumeVo> studentResumeVos = studentService.getStudentResumeVoByStudent(sid);
//        return Map.of(
//                "studentResumeVos",studentResumeVos
//        );
//    }

    @GetMapping("jmr/{sid}")
    public Map getJmr(@PathVariable int sid){
        List<StudentJMR> studentJMRs = studentService.getStudentJMRsByStudent(sid);
        return Map.of(
                "studentJMRs",studentJMRs
        );
    }

    @GetMapping("companies")
    public Map getCompanies(){
        List<Company> companies = companyService.getAllCompanies();
        return Map.of(
                "companies",companies
        );
    }

    @GetMapping("company/{cid}")
    public Map getCompany(@PathVariable int cid){
        Company company = companyService.getCompany(cid);
        return Map.of(
                "company",company
        );
    }

    @GetMapping("companyJob/{cid}")
    public Map getCompanyJobs(@PathVariable int cid){
        List<CompanyJob> companyJobs = companyService.getCompanyJobsByCompany(cid);
        return Map.of(
                "companyJobs",companyJobs
        );
    }

    @GetMapping("smr/{cid}")
    public Map getSmr(@PathVariable int cid){
        List<JobSMR> jobSMRs = companyService.getJobSMRsByCompany(cid);
        return Map.of(
                "jobSMRs",jobSMRs
        );
    }
}

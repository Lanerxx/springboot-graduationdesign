package com.example.springbootgraduationdesign.controller;

import com.example.springbootgraduationdesign.component.CheckIsNullComponent;
import com.example.springbootgraduationdesign.component.RequestComponent;
import com.example.springbootgraduationdesign.component.vo.StudentListStatisticalFormVo;
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
    private IndustryService industryService;
    @Autowired
    private TrackService trackService;

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

//    @GetMapping("jmr/{sid}")
//    public Map getJmr(@PathVariable int sid){
//        List<ResumeJMR> resumeJMRS = studentService.getStudentJMRsByStudent(sid);
//        return Map.of(
//                "studentJMRs", resumeJMRS
//        );
//    }

    @PostMapping("companies")
    public Map addCompanies(@RequestBody List<Company> companies){
        LocalDateTime localDateTime = LocalDateTime.now();
        for (Company company : companies) {
            company.setInsertTime(localDateTime);
            company.setUpdateTime(localDateTime);
            Industry industry = industryService.getIndustry(company.getC_industry().getI_name());
            if (industry == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "您输入的行业：" + company.getC_industry().getI_name() + "不存在！");
            }
            company.setC_industry(industry);
            if (checkIsNullComponent.objCheckIsNull(company)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "您还有未填写的信息！");
            }
            if (companyService.getCompanyByTelephone(company.getC_f_telephone())!=null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "该手机号：" + company.getC_f_telephone() + "已注册！");
            }
            companyService.addCompany(company);
            companies = companyService.getAllCompanies();
        }
        return Map.of(
                "companies",companies
        );
    }

    @DeleteMapping("company/{cid}")
    public Map deleteCompany(@PathVariable int cid){
        Company company = companyService.getCompany(cid);
        if (company == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想删除的企业不存在！");
        }
        companyService.deleteCompanyAndRelated(cid);
        List<Company> companies = companyService.getAllCompanies();
        return Map.of(
                "companies",companies
        );
    }

    @GetMapping("companies")
    public Map getCompanies(){
        List<Company> companies = companyService.getAllCompanies();
        List<String> industries = industryService.listIndustriesName();
        return Map.of(
                "companies",companies,
                "industries",industries
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

    @DeleteMapping("student/{sid}")
    public Map deleteStudent(@PathVariable int sid){
        Student student = studentService.getStudent(sid);
        if (student == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您想删除的学生不存在！");
        }
        studentService.deleteStudentAndRelated(sid);
        List<Student> students = studentService.getAllStudents();
        return Map.of(
                "students",students
        );
    }


//    @GetMapping("smr/{cid}")
//    public Map getSmr(@PathVariable int cid){
//        List<JobSMR> jobSMRs = companyService.getJobSMRsByCompany(cid);
//        return Map.of(
//                "jobSMRs",jobSMRs
//        );
//    }

    @GetMapping("trackStudent/{collegeName}")
    public Map getTrackStudentsByCollegeName(@PathVariable String collegeName){
        Map map = trackService.trackCollegesByCollegeName(collegeName);
        return Map.of("students",map.get("students"),
                "studentListStatisticalFormVo",map.get("studentListStatisticalFormVo"),
                "jobListStatisticalFormVoFavored",map.get("jobListStatisticalFormVoFavored"),
                "jobListStatisticalFormVoOneWay",map.get("jobListStatisticalFormVoOneWay"),
                "jobListStatisticalFormVoTwoWay",map.get("jobListStatisticalFormVoTwoWay")
        );

    }
}

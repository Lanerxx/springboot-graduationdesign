package com.example.springbootgraduationdesign.controller;

import com.example.springbootgraduationdesign.component.CheckIsNullComponent;
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
@RequestMapping("/api/register/")
@Slf4j
public class RegisterController {
    @Autowired
    private CompanyService companyService;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private ProfessionService professionService;
    @Autowired
    private PositionService positionService;

    @Autowired
    private CheckIsNullComponent checkIsNullComponent;
    @Autowired
    private PasswordEncoder encoder;

    @GetMapping("index")
    public Map getIndex(){
        List<String> positionsName = positionService.listPositionsName();
        Set<String> professionsMClass = professionService.getProfessionsMClass();
        return Map.of(
                "positions",positionsName,
                "professionsMClass",professionsMClass
        );
    }

    @PostMapping("index/professionsSClass")
    public Map getIndex(@Valid @RequestBody Profession profession){
        Set<String> professionsSClass = professionService.getProfessionsSClassByMClass(profession.getPr_s_class());
        return Map.of(
                "professionsSClass",professionsSClass
        );
    }

    @PostMapping("company")
    public Map registerCompany(@RequestBody Company company){
        Industry industry = industryService.getIndustry(company.getC_industry().getI_id());
        if( industry == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "行业信息不存在！");
        }
        company.setC_industry(industry);
        log.debug("{}","sadas");
        if (checkIsNullComponent.objCheckIsNull(company)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息！");
        }
        if (companyService.getCompanyByTelephone(company.getC_f_telephone())!=null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "该手机号已注册！");
        }
        companyService.addCompany(company);
        return Map.of(
                "company",company
        );
    }

    @PostMapping("student")
    public Map registerStudent(@RequestBody Student student){
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
        if (checkIsNullComponent.objCheckIsNull(student)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "您还有未填写的信息！");
        }
        if (studentService.getStudentByTelephone(student.getS_telephone())!=null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "该手机号已注册！");
        }
        studentService.addStudent(student);
        return Map.of(
                "student",student
        );
    }
}

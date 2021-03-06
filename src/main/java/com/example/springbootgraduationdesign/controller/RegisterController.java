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
import java.time.LocalDateTime;
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
        List<String> professionSClass = professionService.getProfessionsSName();
        List<String> industriesName = industryService.listIndustriesName();
        return Map.of(
                "positions",positionsName,
                "professionsMClass",professionsMClass,
                "professionsSClass",professionSClass,
                "industries",industriesName
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
        Industry industry = industryService.getIndustry(company.getC_industry().getI_name());
        if( industry == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "????????????????????????");
        }
        company.setC_industry(industry);
        LocalDateTime localDateTime = LocalDateTime.now();
        company.setInsertTime(localDateTime);
        company.setUpdateTime(localDateTime);
        if (checkIsNullComponent.objCheckIsNull(company)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "??????????????????????????????");
        }
        if (companyService.getCompanyByTelephone(company.getC_f_telephone())!=null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "????????????????????????");
        }
        companyService.addCompany(company);
        return Map.of(
                "company",company
        );
    }

    @PostMapping("student")
    public Map registerStudent(@RequestBody Student student){
        Profession profession = professionService.getProfessionBySClass(student.getS_profession().getPr_s_class());
        if (profession == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "???????????????????????????");
        }
        student.setS_profession(profession);
        if (student.getS_if_work().equals(EnumWarehouse.IF_IS_OR_NOT.???)){
            if (student.getS_w_province() == null || student.getS_company() == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "?????????????????????????????????????????????");
            }
        }else {
            student.setS_w_province("???");
            student.setS_company("???");
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        student.setInsertTime(localDateTime);
        student.setUpdateTime(localDateTime);
        System.out.println("student:" + student);
        if (checkIsNullComponent.objCheckIsNull(student)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "??????????????????????????????");
        }
        if (studentService.getStudentByTelephone(student.getS_telephone())!=null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "????????????????????????");
        }
        studentService.addStudent(student);
        return Map.of(
                "student",student
        );
    }
}

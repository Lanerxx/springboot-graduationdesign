package com.example.springbootgraduationdesign.controller;

import com.example.springbootgraduationdesign.component.CheckIsNullComponent;
import com.example.springbootgraduationdesign.entity.Company;
import com.example.springbootgraduationdesign.entity.Industry;
import com.example.springbootgraduationdesign.entity.Profession;
import com.example.springbootgraduationdesign.entity.Student;
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
    private PasswordEncoder encoder;
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

//    @PostMapping("student")
//    public Map registerStudent(@Valid @RequestBody Student student){
//        if (student.getS_name() == null || student.getS_password() == null ||
//                student.getS_sex() == null || student.getS_birthday() == null||
//                student.getS_college() == null || student.getS_c_level() == null ||
//                student.getS_profession().getP_s_class() == null ||
//                student.getS_e_history() == null || student.getS_n_province() == null||
//                student.getS_n_city() == null  ||
//                student.getS_s_range() == null || student.getS_e_position() == null ||
//                student.getS_e_city() == null || student.getS_g_time() == null ||
//                student.getS_telephone() == null || student.getS_email() == null ||
//                student.getS_if_work() == null
//        ){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    "您还有未填写的必填信息！");
//        }
//        if (studentService.getStudentByTelephone(student.getS_telephone())!=null){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    "该手机号已注册！");
//        }
//        if (student.getS_if_work().equals(EnumWarehouse.IF_WORK.已就业)){
//            if (student.getS_w_city() == null || student.getS_company() == null){
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                        "您还未填写已就业的城市或企业！");
//            }
//        }
//        Profession profession = professionService.getProfessionBySClass(student.getS_profession().getP_s_class());
//        if (profession == null){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    "您填写的专业错误！");
//        }
//        Position position = positionService.getPosition(student.getS_e_position().getP_name());
//        if (position == null){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                    "您填写的岗位错误！");
//        }
//        Student s = new Student();
//        s.setS_name(student.getS_name());
//        s.setS_password(encoder.encode(student.getS_password()));
//        if (student.getS_id_card() != null) s.setS_id_card(student.getS_id_card());
//        s.setS_sex(student.getS_sex());
//        s.setS_birthday(student.getS_birthday());
//        s.setS_college(student.getS_college());
//        s.setS_c_level(student.getS_c_level());
//        s.setS_profession(profession);
//        s.setS_e_history(student.getS_e_history());
//        s.setS_n_province(student.getS_n_province());
//        s.setS_n_city(student.getS_n_city());
//        if (student.getS_f_language()!=0) s.setS_f_language(student.getS_f_language());
//        s.setS_s_range(student.getS_s_range());
//        s.setS_e_position(position);
//        s.setS_e_city(student.getS_e_city());
//        s.setS_g_time(student.getS_g_time());
//        s.setS_telephone(student.getS_telephone());
//        s.setS_email(student.getS_email());
//        s.setS_if_work(student.getS_if_work());
//        if (student.getS_if_work().equals(EnumWarehouse.IF_WORK.已就业)){
//            s.setS_w_city(student.getS_w_city());
//            s.setS_company(student.getS_company());
//        }
//        studentService.addStudent(s);
//        return Map.of(
//                "student",s
//        );
//    }
}

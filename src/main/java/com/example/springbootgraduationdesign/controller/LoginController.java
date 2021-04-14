package com.example.springbootgraduationdesign.controller;

import com.example.springbootgraduationdesign.component.EncryptComponent;
import com.example.springbootgraduationdesign.component.MyToken;
import com.example.springbootgraduationdesign.component.vo.UserVo;
import com.example.springbootgraduationdesign.entity.Admin;
import com.example.springbootgraduationdesign.entity.Company;
import com.example.springbootgraduationdesign.entity.JobDrector;
import com.example.springbootgraduationdesign.entity.Student;
import com.example.springbootgraduationdesign.service.AdminService;
import com.example.springbootgraduationdesign.service.CompanyService;
import com.example.springbootgraduationdesign.service.JobDirectorService;
import com.example.springbootgraduationdesign.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/")
@Slf4j
public class LoginController {
    @Value("${my.systemAdmin}")
    private String roleSystemAdmin;
    @Value("${my.generalAdmin}")
    private String roleGeneralAdmin;
    @Value("${my.company}")
    private String roleCompany;
    @Value("${my.student}")
    private String roleStudent;
    @Value("${my.jobDirector}")
    private String roleJobDirector;

    @Autowired
    private AdminService adminService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private JobDirectorService jobDirectorService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private EncryptComponent encrypt;

    @PostMapping("login")
    public Map login(@RequestBody UserVo login, HttpServletResponse response) {
        String userPassword = login.getUserPassword();
        int userId = 0;
        MyToken token = new MyToken();
        String roleCode = "";
        if ( userPassword == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "密码不能为空。");
        }
        //管理员使用用户名和密码登陆
        if (login.isAdmin() == true){
            String userName = login.getUserName();
            if (userName == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "用户名不能为空。");
            }
            Admin admin = Optional.ofNullable(adminService.getAdmin(userName))
                    .filter(a -> encoder.matches(userPassword, a.getA_password()))
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "用户名和密码错误"));
            userId = admin.getA_id();
            if (Admin.A_TYPE.SYSTEM_ADMIN == admin.getA_type()){
                roleCode = roleSystemAdmin;
                token.setRole(MyToken.ROLES.SYSTEM_ADMIN);
            }
            else if (Admin.A_TYPE.NORMAL_ADMIN == admin.getA_type()){
                roleCode = roleGeneralAdmin;
                token.setRole(MyToken.ROLES.GENERAL_ADMIN);
            }
        }
        //学生、企业和就业专员使用电话号和密码登陆
        else {
            String userPhoneNumber = login.getUserPhoneNumber();
            if (userPhoneNumber == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "电话号不能为空。");
            }
            if(login.isStudent()){
                Student student = Optional.ofNullable(studentService.getStudentByTelephone(userPhoneNumber))
                        .filter(s -> encoder.matches(userPassword, s.getS_password()))
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "电话号和密码错误"));
                userId = student.getS_id();
                token.setRole(MyToken.ROLES.STUDENT);
                roleCode = roleStudent;
            }
            else if (login.isCompany()){
                Company company = Optional.ofNullable(companyService.getCompanyByTelephone(userPhoneNumber))
                        .filter(c -> encoder.matches(userPassword, c.getC_password()))
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "电话号和密码错误"));
                userId = company.getC_id();
                token.setRole(MyToken.ROLES.COMPANY);
                roleCode = roleCompany;
            }
            else if (login.isJobDirector()){
                JobDrector jobDirector = Optional.ofNullable(jobDirectorService.getJobDirectorByTelephone(userPhoneNumber))
                        .filter(jd -> encoder.matches(userPassword, jd.getJd_password()))
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "电话号和密码错误"));
                userId = jobDirector.getJd_id();
                token.setRole(MyToken.ROLES.JOB_DIRECTOR);
                roleCode = roleJobDirector;
            }
        }
        token.setId(userId);
        String auth = encrypt.encryptToken(token);
        response.setHeader(MyToken.AUTHORIZATION, auth);
        return Map.of(
                "role",roleCode
        );
    }
}

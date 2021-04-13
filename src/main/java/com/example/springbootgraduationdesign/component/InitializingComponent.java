package com.example.springbootgraduationdesign.component;

import com.example.springbootgraduationdesign.entity.Admin;
import com.example.springbootgraduationdesign.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class InitializingComponent implements InitializingBean {
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private AdminService adminService;

    @Override
    public void afterPropertiesSet() throws Exception {
        final String password = "systemAdmin1";
        final String name = "systemAdmin1";
        Admin admin = adminService.getAdmin(1);
        if (admin == null) {
            Admin a = new Admin();
            a.setA_name(name);
            String s = encoder.encode(password);
            a.setA_password(s);
            a.setA_type(Admin.A_TYPE.SYSTEM_ADMIN);
            adminService.addAdmin(a);
        }
    }
}

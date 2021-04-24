package com.example.springbootgraduationdesign.Service;

import com.example.springbootgraduationdesign.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CompanyServiceTest {
    @Autowired
    private CompanyService companyService;

    @Test
    public void getJobSMRTest(){
        companyService.getJobSMR();
    }
}

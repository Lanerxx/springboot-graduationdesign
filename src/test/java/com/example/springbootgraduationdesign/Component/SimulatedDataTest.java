package com.example.springbootgraduationdesign.Component;

import com.example.springbootgraduationdesign.component.SimulatedDataComponent;
import com.example.springbootgraduationdesign.entity.EnumWarehouse;
import com.example.springbootgraduationdesign.entity.Student;
import com.example.springbootgraduationdesign.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@SpringBootTest
public class SimulatedDataTest {
    @Autowired
    private SimulatedDataComponent simulatedDataComponent;
    @Autowired
    private CompanyService companyService;

    @Test
    public void test1(){
//        simulatedDataComponent.simulatedProfession();
//        simulatedDataComponent.simulatedIndustry();
//        simulatedDataComponent.simulatedPosition();
//        simulatedDataComponent.simulatedStudent();
//        simulatedDataComponent.simulatedStudentIndustry();
//        simulatedDataComponent.simulatedStudentPosition();
//        simulatedDataComponent.simulatedStudentResume();
//        simulatedDataComponent.simulatedCompany();
//        simulatedDataComponent.simulatedCompanyJob();
//        simulatedDataComponent.simulatedJobResume();
//        simulatedDataComponent.simulatedCompanyFavoredResumes();
        simulatedDataComponent.simulatedStudentFavoredJobs();
    }
}


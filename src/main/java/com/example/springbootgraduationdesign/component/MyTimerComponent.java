package com.example.springbootgraduationdesign.component;

import com.example.springbootgraduationdesign.service.CompanyService;
import com.example.springbootgraduationdesign.service.StudentService;
import com.example.springbootgraduationdesign.service.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MyTimerComponent {
    @Autowired
    private StudentService studentService;
    @Autowired
    private CompanyService companyService;
    @Autowired
    private TrackService trackService;


    @Scheduled(cron = "0 0 2 * * *")
    public void timingTracking(){
        trackService.getJobSMRSystemDefaultWeight();
        trackService.getResumeJMRSystemDefaultWeight();
    }

    @Scheduled(cron = "0 0 3 * * *")
    public void timingMatching(){
        studentService.getResumeJMR_Match();
        companyService.getJobSMR_Match();
    }

}

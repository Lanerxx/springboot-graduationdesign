package com.example.springbootgraduationdesign.Service;

import com.example.springbootgraduationdesign.entity.Company;
import com.example.springbootgraduationdesign.entity.EnumWarehouse;
import com.example.springbootgraduationdesign.service.TrackService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class TrackServiceTest {
    @Autowired
    private TrackService trackService;
    @Test
    public void trackCollegesByCollegeNameTest(){
        Map list = trackService.trackCollegesByCollegeName("门头沟枚大学0");
        System.out.println("StudentListStatisticalFormVo:" + list.get("StudentListStatisticalFormVo"));
        System.out.println("jobListStatisticalFormVoFavored:" + list.get("jobListStatisticalFormVoFavored"));
        System.out.println("jobListStatisticalFormVoOneWay:" + list.get("jobListStatisticalFormVoOneWay"));
        System.out.println("jobListStatisticalFormVoTwoWay:" + list.get("jobListStatisticalFormVoTwoWay"));
    }

    @Test
    public void trackCompaniesByFStageTest(){
        Map list = trackService.trackCompaniesByFStage(EnumWarehouse.FINANCING_STAGE.B轮);
        System.out.println("companyListStatisticalFormVo:" + list.get("companyListStatisticalFormVo"));
        System.out.println("resumeListStatisticalFormVoFavored:" + list.get("resumeListStatisticalFormVoFavored"));
        System.out.println("resumeListStatisticalFormVoOneWay:" + list.get("resumeListStatisticalFormVoOneWay"));
        System.out.println("resumeListStatisticalFormVoTwoWay:" + list.get("resumeListStatisticalFormVoTwoWay"));
    }

}

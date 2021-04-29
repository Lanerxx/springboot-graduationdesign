package com.example.springbootgraduationdesign.Service;

import com.example.springbootgraduationdesign.component.vo.JobSMRPersonalizedVo;
import com.example.springbootgraduationdesign.component.vo.PersonalizedSMRVo;
import com.example.springbootgraduationdesign.entity.JobSMR;
import com.example.springbootgraduationdesign.entity.JobSMRBase;
import com.example.springbootgraduationdesign.service.CompanyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

@SpringBootTest
public class CompanyServiceTest {
    @Autowired
    private CompanyService companyService;

    @Test
    public void getJobSMRTest(){
        companyService.getJobSMR_Match();
    }

    @Test
    public void getJobSMRTest_Personalized(){
        Random random = new Random();
        int jid = 2;

        PersonalizedSMRVo personalizedSMRVo = new PersonalizedSMRVo();
        personalizedSMRVo.setA_bonus(random.nextDouble());
        personalizedSMRVo.setB_trip(random.nextDouble());
        personalizedSMRVo.setC_count(random.nextDouble());
        personalizedSMRVo.setC_level(random.nextDouble());
        personalizedSMRVo.setE_language(random.nextDouble());
        personalizedSMRVo.setC_up(random.nextDouble());
        personalizedSMRVo.setE_history(random.nextDouble());
        personalizedSMRVo.setH_count(random.nextDouble());
        personalizedSMRVo.setH_subside(random.nextDouble());
        personalizedSMRVo.setInsurance(random.nextDouble());
        personalizedSMRVo.setLocation(random.nextDouble());
        personalizedSMRVo.setO_allowance(random.nextDouble());
        personalizedSMRVo.setP_count(random.nextDouble());
        personalizedSMRVo.setP_leave(random.nextDouble());
        personalizedSMRVo.setPosition(random.nextDouble());
        personalizedSMRVo.setR_count(random.nextDouble());
        personalizedSMRVo.setRanking(random.nextDouble());
        personalizedSMRVo.setS_range(random.nextDouble());
        personalizedSMRVo.setStock(random.nextDouble());
        personalizedSMRVo.setS_range(random.nextDouble());
        personalizedSMRVo.setS_count(random.nextDouble());
        personalizedSMRVo.setT_subside(random.nextDouble());
        List<JobSMRPersonalizedVo> JobSMRPersonalizedVos = companyService.getJobSMR_Match(jid,personalizedSMRVo);
        if (JobSMRPersonalizedVos.size() == 0)
            System.out.println("0");
        for (JobSMRPersonalizedVo jobSMRPersonalizedVo : JobSMRPersonalizedVos) {
            System.out.println(jobSMRPersonalizedVo.toString());
        }
        System.out.println("end");

//        及时执行，根据用户的自定义权重进行匹配
//        public List<JobSMR> getJobSMR_Match(int jid, PersonalizedSMRVo personalizedSMRVo){
//        return jobSMRs;

//        personalizedSMRVo.setA_bonus();
//        personalizedSMRVo.setB_trip();
//        personalizedSMRVo.setC_count();
//        personalizedSMRVo.setC_level();
//        personalizedSMRVo.getE_language();
//        personalizedSMRVo.setC_up();
//        personalizedSMRVo.setE_history();
//        personalizedSMRVo.setGpa();
//        personalizedSMRVo.setH_count();
//        personalizedSMRVo.setH_subside();
//        personalizedSMRVo.setIf_career();
//        personalizedSMRVo.setIf_experience();
//        personalizedSMRVo.setInsurance();
//        personalizedSMRVo.setLocation();
//        personalizedSMRVo.setO_allowance();
//        personalizedSMRVo.setP_count();
//        personalizedSMRVo.setP_leave();
//        personalizedSMRVo.setPosition();
//        personalizedSMRVo.setR_count();
//        personalizedSMRVo.setRanking();
//        personalizedSMRVo.setS_range();
//        personalizedSMRVo.setStock();
//        personalizedSMRVo.setS_range();
//        personalizedSMRVo.setS_count();
//        personalizedSMRVo.setT_subside();
    }
}

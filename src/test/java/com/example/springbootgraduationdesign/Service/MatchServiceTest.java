package com.example.springbootgraduationdesign.Service;

import com.example.springbootgraduationdesign.component.vo.PersonalizedJMRVo;
import com.example.springbootgraduationdesign.service.MatchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MatchServiceTest {
    @Autowired
    private MatchService matchService;

    @Test
    public void  transferJmrTest(){
        PersonalizedJMRVo personalizedJMRVo = new PersonalizedJMRVo();
        personalizedJMRVo.setJmr_b_c_scale(20);
        personalizedJMRVo.setJmr_b_c_f_stage(30);
        personalizedJMRVo.setJmr_b_c_level(40);
        personalizedJMRVo.setJmr_e_history(20);
        personalizedJMRVo.setJmr_b_e_language(20);
        personalizedJMRVo.setJmr_b_j_count(50);
        personalizedJMRVo.setJmr_b_position(20);
        personalizedJMRVo.setJmr_b_location(90);
        personalizedJMRVo.setJmr_b_insurance(20);
        personalizedJMRVo.setJmr_b_check_up(30);
        personalizedJMRVo.setJmr_b_a_bonus(20);
        personalizedJMRVo.setJmr_b_p_leave(40);
        personalizedJMRVo.setJmr_b_o_allowance(20);
        personalizedJMRVo.setJmr_b_stock(30);
        personalizedJMRVo.setJmr_b_t_subside(70);
        personalizedJMRVo.setJmr_b_h_subside(20);
        personalizedJMRVo.setJmr_b_b_trip(70);
        personalizedJMRVo.setJmr_b_s_range(20);
        matchService.transferPersonalizedJMRVoWeight(personalizedJMRVo);
        double sum  = 0;
        sum += personalizedJMRVo.getJmr_b_a_bonus();
        sum += personalizedJMRVo.getJmr_b_c_scale();
        sum += personalizedJMRVo.getJmr_b_c_f_stage();
        sum += personalizedJMRVo.getJmr_b_c_level();
        sum += personalizedJMRVo.getJmr_e_history();
        sum += personalizedJMRVo.getJmr_b_e_language();
        sum += personalizedJMRVo.getJmr_b_j_count();
        sum += personalizedJMRVo.getJmr_b_position();
        sum += personalizedJMRVo.getJmr_b_location();
        sum += personalizedJMRVo.getJmr_b_insurance();
        sum += personalizedJMRVo.getJmr_b_check_up();
        sum += personalizedJMRVo.getJmr_b_a_bonus();
        sum += personalizedJMRVo.getJmr_b_p_leave();
        sum += personalizedJMRVo.getJmr_b_o_allowance();
        sum += personalizedJMRVo.getJmr_b_stock();
        sum += personalizedJMRVo.getJmr_b_t_subside();
        sum += personalizedJMRVo.getJmr_b_h_subside();
        sum += personalizedJMRVo.getJmr_b_b_trip();
        sum += personalizedJMRVo.getJmr_b_s_range();
        System.out.println("sum:" + sum);

    }
}

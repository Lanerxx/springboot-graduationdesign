package com.example.springbootgraduationdesign.service;

import com.example.springbootgraduationdesign.component.vo.PersonalizedJMRVo;
import com.example.springbootgraduationdesign.component.vo.PersonalizedSMRVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class MatchService {
    public PersonalizedJMRVo transferPersonalizedJMRVoWeight(PersonalizedJMRVo personalizedJMRVo){
        System.out.println(personalizedJMRVo.toString());
        double sum = 0;
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
        personalizedJMRVo.setJmr_b_a_bonus((personalizedJMRVo.getJmr_b_c_scale()+0.0)/sum);
        personalizedJMRVo.setJmr_b_c_scale((personalizedJMRVo.getJmr_b_c_f_stage()+0.0)/sum);
        personalizedJMRVo.setJmr_b_c_f_stage((personalizedJMRVo.getJmr_b_c_level()+0.0)/sum);
        personalizedJMRVo.setJmr_b_c_level((personalizedJMRVo.getJmr_e_history()+0.0)/sum);
        personalizedJMRVo.setJmr_e_history((personalizedJMRVo.getJmr_b_e_language()+0.0)/sum);
        personalizedJMRVo.setJmr_b_e_language((personalizedJMRVo.getJmr_b_j_count()+0.0)/sum);
        personalizedJMRVo.setJmr_b_j_count((personalizedJMRVo.getJmr_b_position()+0.0)/sum);
        personalizedJMRVo.setJmr_b_position((personalizedJMRVo.getJmr_b_location()+0.0)/sum);
        personalizedJMRVo.setJmr_b_location((personalizedJMRVo.getJmr_b_insurance()+0.0)/sum);
        personalizedJMRVo.setJmr_b_insurance((personalizedJMRVo.getJmr_b_check_up()+0.0)/sum);
        personalizedJMRVo.setJmr_b_check_up((personalizedJMRVo.getJmr_b_a_bonus()+0.0)/sum);
        personalizedJMRVo.setJmr_b_a_bonus((personalizedJMRVo.getJmr_b_p_leave()+0.0)/sum);
        personalizedJMRVo.setJmr_b_p_leave((personalizedJMRVo.getJmr_b_o_allowance()+0.0)/sum);
        personalizedJMRVo.setJmr_b_o_allowance((personalizedJMRVo.getJmr_b_stock()+0.0)/sum);
        personalizedJMRVo.setJmr_b_stock((personalizedJMRVo.getJmr_b_t_subside()+0.0)/sum);
        personalizedJMRVo.setJmr_b_t_subside((personalizedJMRVo.getJmr_b_h_subside()+0.0)/sum);
        personalizedJMRVo.setJmr_b_h_subside((personalizedJMRVo.getJmr_b_b_trip()+0.0)/sum);
        personalizedJMRVo.setJmr_b_b_trip((personalizedJMRVo.getJmr_b_s_range()+0.0)/sum);
        personalizedJMRVo.setJmr_b_s_range((personalizedJMRVo.getJmr_b_s_range()+0.0)/sum);
        System.out.println(personalizedJMRVo.toString());
        return personalizedJMRVo;
    }

    public PersonalizedSMRVo transferPersonalizedSMRVoWeight(PersonalizedSMRVo personalizedSMRVo){
        double sum = 0;
        sum += personalizedSMRVo.getRanking();
        sum += personalizedSMRVo.getR_count();
        sum += personalizedSMRVo.getR_count();
        sum += personalizedSMRVo.getS_count();
        sum += personalizedSMRVo.getC_count();
        sum += personalizedSMRVo.getH_count();
        sum += personalizedSMRVo.getPosition();
        sum += personalizedSMRVo.getLocation();
        sum += personalizedSMRVo.getInsurance();
        sum += personalizedSMRVo.getC_up();
        sum += personalizedSMRVo.getA_bonus();
        sum += personalizedSMRVo.getP_leave();
        sum += personalizedSMRVo.getO_allowance();
        sum += personalizedSMRVo.getStock();
        sum += personalizedSMRVo.getT_subside();
        sum += personalizedSMRVo.getH_subside();
        sum += personalizedSMRVo.getB_trip();
        sum += personalizedSMRVo.getC_level();
        sum += personalizedSMRVo.getE_history();
        sum += personalizedSMRVo.getE_language();
        sum += personalizedSMRVo.getS_range();
        personalizedSMRVo.setRanking((personalizedSMRVo.getRanking()+0.0)/sum);
        personalizedSMRVo.setR_count((personalizedSMRVo.getR_count()+0.0)/sum);
        personalizedSMRVo.setR_count((personalizedSMRVo.getR_count()+0.0)/sum);
        personalizedSMRVo.setS_count((personalizedSMRVo.getS_count()+0.0)/sum);
        personalizedSMRVo.setC_count((personalizedSMRVo.getC_count()+0.0)/sum);
        personalizedSMRVo.setH_count((personalizedSMRVo.getH_count()+0.0)/sum);
        personalizedSMRVo.setPosition((personalizedSMRVo.getPosition()+0.0)/sum);
        personalizedSMRVo.setLocation((personalizedSMRVo.getLocation()+0.0)/sum);
        personalizedSMRVo.setInsurance((personalizedSMRVo.getInsurance()+0.0)/sum);
        personalizedSMRVo.setC_up((personalizedSMRVo.getC_up()+0.0)/sum);
        personalizedSMRVo.setA_bonus((personalizedSMRVo.getA_bonus()+0.0)/sum);
        personalizedSMRVo.setP_leave((personalizedSMRVo.getP_leave()+0.0)/sum);
        personalizedSMRVo.setO_allowance((personalizedSMRVo.getO_allowance()+0.0)/sum);
        personalizedSMRVo.setStock((personalizedSMRVo.getStock()+0.0)/sum);
        personalizedSMRVo.setT_subside((personalizedSMRVo.getT_subside()+0.0)/sum);
        personalizedSMRVo.setH_subside((personalizedSMRVo.getH_subside()+0.0)/sum);
        personalizedSMRVo.setB_trip((personalizedSMRVo.getB_trip()+0.0)/sum);
        personalizedSMRVo.setC_level((personalizedSMRVo.getC_level()+0.0)/sum);
        personalizedSMRVo.setE_history((personalizedSMRVo.getE_history()+0.0)/sum);
        personalizedSMRVo.setE_language((personalizedSMRVo.getE_language()+0.0)/sum);
        personalizedSMRVo.setS_range((personalizedSMRVo.getS_range()+0.0)/sum);
        return personalizedSMRVo;
    }
}

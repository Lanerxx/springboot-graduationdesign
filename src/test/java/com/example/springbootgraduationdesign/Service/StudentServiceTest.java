package com.example.springbootgraduationdesign.Service;

import com.example.springbootgraduationdesign.component.vo.PersonalizedJMRVo;
import com.example.springbootgraduationdesign.component.vo.ResumeJMRPersonalizedVo;
import com.example.springbootgraduationdesign.entity.Resume;
import com.example.springbootgraduationdesign.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Random;

@SpringBootTest
public class StudentServiceTest {
    @Autowired
    private StudentService studentService;

    @Test
    public void getResumesByStudent(){
        int sid = 898;
        List<Resume> resumes = studentService.getResumesByStudentId(sid);
        if (resumes.size() == 0) System.out.println("无");
        else {
            resumes.forEach(resume -> {
                System.out.println(resume.getR_student().getS_name());
            });
        }
    }

    @Test
    public void getResumeSMRTest(){
        studentService.getResumeJMR_Match();
    }

    @Test
    public void getResumeSMRTest_Personalized(){
        Random random = new Random();
        int rid = 5;

        PersonalizedJMRVo personalizedJMRVo = new PersonalizedJMRVo();
        personalizedJMRVo.setJmr_b_a_bonus(random.nextDouble());
        personalizedJMRVo.setJmr_b_b_trip(random.nextDouble());
        personalizedJMRVo.setJmr_b_c_f_stage(random.nextDouble());
        personalizedJMRVo.setJmr_b_c_level(random.nextDouble());
        personalizedJMRVo.setJmr_b_c_scale(random.nextDouble());
        personalizedJMRVo.setJmr_b_check_up(random.nextDouble());
        personalizedJMRVo.setJmr_b_e_language(random.nextDouble());
        personalizedJMRVo.setJmr_b_h_subside(random.nextDouble());
        personalizedJMRVo.setJmr_b_insurance(random.nextDouble());
        personalizedJMRVo.setJmr_b_j_count(random.nextDouble());
        personalizedJMRVo.setJmr_b_location(random.nextDouble());
        personalizedJMRVo.setJmr_b_o_allowance(random.nextDouble());
        personalizedJMRVo.setJmr_b_p_leave(random.nextDouble());
        personalizedJMRVo.setJmr_b_position(random.nextDouble());
        personalizedJMRVo.setJmr_b_s_range(random.nextDouble());
        personalizedJMRVo.setJmr_b_stock(random.nextDouble());
        personalizedJMRVo.setJmr_b_t_subside(random.nextDouble());
        personalizedJMRVo.setJmr_e_history(random.nextDouble());

        List<ResumeJMRPersonalizedVo> resumeJMRPersonalizedVos = studentService.getResumeJMR_Match(rid,personalizedJMRVo);
        if (resumeJMRPersonalizedVos.size() == 0)
            System.out.println("0");
        for (ResumeJMRPersonalizedVo resumeJMRPersonalizedVo : resumeJMRPersonalizedVos){
            System.out.println(resumeJMRPersonalizedVo.toString());
        }

        System.out.println("end");
    }
}

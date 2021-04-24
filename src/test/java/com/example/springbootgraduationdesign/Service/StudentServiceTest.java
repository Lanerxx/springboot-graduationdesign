package com.example.springbootgraduationdesign.Service;

import com.example.springbootgraduationdesign.entity.Resume;
import com.example.springbootgraduationdesign.entity.Student;
import com.example.springbootgraduationdesign.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

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
    public void getStudent(){
//        Resume resume = studentService.getResume(7988);
//        System.out.println(resume.toString());
//        if (resume.getR_if_insurance().ordinal() == 0){
//            System.out.println("0");
//        }else if (resume.getR_if_insurance().ordinal() == 1) {
//            System.out.println("1");
//        }
//        Resume resume = studentService.getResume(12113);
//        System.out.println(resume.toString());
//        if (resume.getR_if_b_trip().equals("NO_REQUIREMENT")){
//            System.out.println("NO_REQUIREMENT");
//        }else {
//            System.out.println("equals 不行");
//        }
    }
}

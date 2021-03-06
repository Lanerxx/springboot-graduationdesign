package com.example.springbootgraduationdesign.service;

import com.example.springbootgraduationdesign.entity.Industry;
import com.example.springbootgraduationdesign.entity.Position;
import com.example.springbootgraduationdesign.entity.StudentIndustry;
import com.example.springbootgraduationdesign.repository.IndustryRepository;
import com.example.springbootgraduationdesign.repository.StudentIndustryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class IndustryService {
    @Autowired
    private IndustryRepository industryRepository;
    @Autowired
    private StudentIndustryRepository studentIndustryRepository;

    @Autowired
    private IndustryService industryService;

    /*---------------行业信息（Position）----------------
    -------检索：管理员
    -------更新：管理员
    -------创建：管理员
    -------删除：管理员
    --------------------------------------------------*/
    public Industry addIndustry(Industry industry){
        industryRepository.save(industry);
        return industry;
    }
    public List<Industry> addIndustry(List<Industry> industrys){
        return null;
    }

    public void deleteIndustry(int pid){
        industryRepository.deleteById(pid);
    }
    public void deleteAllIndustries(){
        industryRepository.deleteAll();
    }

    public Industry updateIndustry(Industry industry){
        industryRepository.save(industry);
        return industry;
    }


    public List<Industry> getIndustriesByIndustriesName(List<String> industriesName){
        System.out.println("industriesName:" + industriesName);
        List<Industry> industries = new ArrayList<>();
        for (String s : industriesName) {
            Industry industry = industryService.getIndustry(s);
            System.out.print("industry:" + industry + "/");
            if (industry != null) industries.add(industry);
        }
        System.out.println();
        return industries;
    }
    public List<Industry> getAllIndustries(){
        return industryRepository.findAll();
    }
    public Industry getIndustry(int iid){
        return industryRepository.findById(iid).orElse(null);
    }
    public Industry getIndustry(String name){
        return industryRepository.getIndustryByName(name).orElse(null);
    }
    public List<String> listIndustriesName(){
        List<Industry> industries = industryRepository.findAll();
        List<String> industriesName = new ArrayList<>();
        industries.forEach(industry -> {
            industriesName.add(industry.getI_name());
        });
        return industriesName;
    }
    public List<String> listIndustryNameByStudent(int sid){
        return studentIndustryRepository.listIndustryNameByStudent(sid).orElse(new ArrayList<>());
    }
    public List<Industry> getIndustriesByStudent(int sid){
        List<StudentIndustry> studentIndustries = studentIndustryRepository.getStudentIndustriesByStudent(sid).orElse(new ArrayList<>());
        List<Industry> industries = new ArrayList<>();
        if (studentIndustries.size() != 0){
            for (StudentIndustry si : studentIndustries){
                industries.add(si.getStudentIndustryPK().getSi_industry());
            }
        }
        return industries;
    }
}

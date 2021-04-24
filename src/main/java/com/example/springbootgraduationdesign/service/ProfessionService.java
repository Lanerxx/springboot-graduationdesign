package com.example.springbootgraduationdesign.service;

import com.example.springbootgraduationdesign.entity.JobProfession;
import com.example.springbootgraduationdesign.entity.Profession;
import com.example.springbootgraduationdesign.repository.JobProfessionRepository;
import com.example.springbootgraduationdesign.repository.ProfessionRepository;
import com.example.springbootgraduationdesign.repository.StudentIndustryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@Transactional
public class ProfessionService {
    @Autowired
    private ProfessionRepository professtionRepository;
    @Autowired
    private JobProfessionRepository jobProfessionRepository;


    @Autowired
    private ProfessionService professionService;
    /*--------------专业信息（Profession）---------------
    -------检索：管理员
    -------更新：管理员
    -------创建：管理员
    -------删除：管理员
    --------------------------------------------------*/
    public Profession addProfession(Profession profession){
        professtionRepository.save(profession);
        return profession;
    }
    public List<Profession> addProfessions(List<Profession> professions){
        return null;
    }

    public void deleteProfession(int pid){
        professtionRepository.deleteById(pid);
    }
    public void deleteAllProfessions(){
        professtionRepository.deleteAll();
    }
    public void deleteJobProfession(int jid, int pid){
        jobProfessionRepository.deleteById(pid);
    }

    public Profession updateProfession(Profession profession){
        professtionRepository.save(profession);
        return profession;
    }

    public List<Profession> getAllProfessions(){
        return professtionRepository.findAll();
    }
    public Set<String> getProfessionsMClass(){
        Set<String> professionMClasses = new HashSet<>();
        List<Profession> professions = professionService.getAllProfessions();
        professions.forEach(profession -> {
            professionMClasses.add(profession.getPr_m_class());
        });

        return professionMClasses;
    }

//    public List<ProfessionMClassVo> getProfessionsMClassVo() {
//        List<ProfessionMClassVo> professionMClassVos = new ArrayList<>();
//        Set<String> professionsMClasses = professionService.getProfessionsMClass();
//        professionsMClasses.forEach(professionsMClass -> {
//            int professionsSClassCount = professionService.getProfessionsSClassByMClass(professionsMClass).size();
//            ProfessionMClassVo professionMClassVo = new ProfessionMClassVo();
//            professionMClassVo.setProfessionMClass(professionsMClass);
//            professionMClassVo.setProfessionSClassCount(professionsSClassCount);
//            List<Profession> professions = professionService.getProfessionsByMClass(professionsMClass);
//            professionMClassVo.setP_id(professions.get(0).getP_id());
//            professionMClassVos.add(professionMClassVo);
//        });
//        return professionMClassVos;
//    }
    public Set<String> getProfessionsSClassByMClass(String mClass){
        Set<String> professionSClasses = new HashSet<>();
        List<Profession> professions = professionService.getProfessionsByMClass(mClass);
        professions.forEach(profession -> {
            professionSClasses.add(profession.getPr_s_class());
        });
        return professionSClasses;
    }
    public Profession getProfession(int pid){
        return professtionRepository.findById(pid).orElse(null);
    }
    public Profession getProfessionBySClass(String sClass){
        return professtionRepository.getProfessionBySClass(sClass).orElse(null);
    }
    public List<Profession> getProfessionsByMClass(String mClass){
        return professtionRepository.getProfessionByMClass(mClass).orElse(new ArrayList<>());
    }
    public List<Profession> getProfessionsByJob(int jid){
        List<JobProfession> jobProfessions = jobProfessionRepository.getJobProfessionsByJob(jid).orElse(new ArrayList<>());
        List<Profession> professions = new ArrayList<>();
        if (jobProfessions.size() != 0){
            for (JobProfession jp : jobProfessions){
                professions.add(jp.getJobProfessionPK().getJp_profession());
            }
        }
        return professions;
    }



}

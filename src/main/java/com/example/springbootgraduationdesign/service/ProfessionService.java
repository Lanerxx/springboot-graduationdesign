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
    private ProfessionRepository professionRepository;
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
        professionRepository.save(profession);
        return profession;
    }
    public List<Profession> addProfessions(List<Profession> professions){
        return null;
    }

    public void deleteProfession(int pid){
        professionRepository.deleteById(pid);
    }
    public void deleteAllProfessions(){
        professionRepository.deleteAll();
    }
    public void deleteJobProfession(int jid, int pid){
        jobProfessionRepository.deleteById(pid);
    }

    public Profession updateProfession(Profession profession){
        professionRepository.save(profession);
        return profession;
    }

    public List<Profession> getAllProfessions(){
        return professionRepository.findAll();
    }
    public List<Profession> getProfessionsByJobProfessions(List<JobProfession> jobProfessions){
        List<Profession> professions = new ArrayList<>();
        for (JobProfession jobProfession : jobProfessions) {
            professions.add(jobProfession.getJobProfessionPK().getJp_profession());
        }
        return professions;
    }
    public List<String> getProfessionsMNameByJobProfessions(List<JobProfession> jobProfessions){
        List<String> professionsMName = new ArrayList<>();
        for (JobProfession jobProfession : jobProfessions) {
            professionsMName.add(jobProfession.getJobProfessionPK().getJp_profession().getPr_m_class());
        }
        return professionsMName;
    }

    public List<String> getProfessionsMSNameByJobProfessions(List<JobProfession> jobProfessions){
        List<String> professionsMSName = new ArrayList<>();
        for (JobProfession jobProfession : jobProfessions) {
            professionsMSName.add(jobProfession.getJobProfessionPK().getJp_profession().getPr_m_class()
                    + jobProfession.getJobProfessionPK().getJp_profession().getPr_s_class());
        }
        return professionsMSName;
    }
    public List<String> getProfessionsMNameByJob(int jid){
        List<JobProfession> jobProfessions = jobProfessionRepository.getJobProfessionsByJob(jid).orElse(new ArrayList<>());
        List<String> professionsMName = professionService.getProfessionsMNameByJobProfessions(jobProfessions);
        return professionsMName;
    }
    public Set<String> getProfessionsMClass(){
        Set<String> professionMClasses = new HashSet<>();
        List<Profession> professions = professionService.getAllProfessions();
        professions.forEach(profession -> {
            professionMClasses.add(profession.getPr_m_class());
        });

        return professionMClasses;
    }
    public List<Profession> getProfessionsByProfessionsMName(List<String> professionsMName){
        List<Profession> professions = new ArrayList<>();
        for (String professionMName : professionsMName) {
           Profession profession = professionService.getProfessionByProfessionMName(professionMName);
           if (profession != null) professions.add(profession);
        }
        return professions;
    }
    public Profession getProfessionByProfessionMName(String professionMName){
        Profession profession = new Profession();
        List<Profession> professions = professionService.getProfessionsByMClass(professionMName);
        if (professions.size() != 0) profession = professions.get(0);
        return profession;
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
        return professionRepository.findById(pid).orElse(null);
    }
    public Profession getProfessionBySClass(String sClass){
        return professionRepository.getProfessionBySClass(sClass).orElse(null);
    }
    public List<Profession> getProfessionsByMClass(String mClass){
        return professionRepository.getProfessionByMClass(mClass).orElse(new ArrayList<>());
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

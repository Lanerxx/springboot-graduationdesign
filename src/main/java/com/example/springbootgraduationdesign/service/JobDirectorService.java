package com.example.springbootgraduationdesign.service;

import com.example.springbootgraduationdesign.entity.JobDrector;
import com.example.springbootgraduationdesign.repository.JobDirectorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional
public class JobDirectorService {
    @Autowired
    private JobDirectorRepository jobDirectorRepository;

    /*-------------就业专员信息（JobDirector）------------
    -------检索：管理员，就业专员
    -------更新：管理员，就业专员
    -------创建：管理员
    -------删除：管理员
    --------------------------------------------------*/
    public JobDrector addJobDirector(JobDrector job_director){
        jobDirectorRepository.save(job_director);
        return job_director;
    }
    public void deleteJobDirector(int jbid){
        jobDirectorRepository.deleteById(jbid);
    }
    public void deleteAllJobDirectors(){
        jobDirectorRepository.deleteAll();
    }
    public JobDrector updateJobDirector(JobDrector job_director){
        jobDirectorRepository.save(job_director);
        return job_director;
    }
    public List<JobDrector> getAllJobDirectors(){
        return jobDirectorRepository.findAll();
    }
    public JobDrector getJobDirector(int jdid){
        return jobDirectorRepository.findById(jdid).orElse(null);
    }
    public List<JobDrector> getJobDirectorsByName(String name){
        return jobDirectorRepository.getJobDirectorByName(name).orElse(new ArrayList<>());
    }
    public JobDrector getJobDirectorByTelephone(String telephone){
        return jobDirectorRepository.getJobDirectorByTelephone(telephone).orElse(null);
    }
}

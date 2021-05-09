package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.JobSMRBase;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSMRBaseRepository extends BaseRepository<JobSMRBase, Integer>{
}

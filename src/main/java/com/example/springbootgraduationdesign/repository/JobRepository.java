package com.example.springbootgraduationdesign.repository;

import com.example.springbootgraduationdesign.entity.Job;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobRepository extends BaseRepository<Job,Integer>{
    @Query("SELECT j FROM Job  j WHERE j.j_company.c_id=:cid")
    Optional<List<Job>> getJobsByCompany (@Param("cid")int cid);
}
